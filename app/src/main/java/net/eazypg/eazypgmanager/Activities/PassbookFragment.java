package net.eazypg.eazypgmanager.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.DetailList.AllPassbookDetailList;
import net.eazypg.eazypgmanager.DetailList.ExpensePassbookDetailList;
import net.eazypg.eazypgmanager.DetailList.IncomePassbookDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.CashflowDetails;
import net.eazypg.eazypgmanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class PassbookFragment extends Fragment {

    View view;

    ListView listView;
    List<CashflowDetails> allDetailsList = new ArrayList<>();
    List<CashflowDetails> incomeDetailsList = new ArrayList<>();
    List<CashflowDetails> expensesDetailsList = new ArrayList<>();

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    TextView monthTextView, incomeTextView, expensesTextView, balanceTextView;

    Button allButton, incomeButton, expensesButton;

    RecyclerView allRecyclerView, expensesRecyclerView, incomeRecyclerView;

    AllPassbookDetailList allPassbookDetailList;
    ExpensePassbookDetailList expensePassbookDetailList;
    IncomePassbookDetailList incomePassbookDetailList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_passbook, container, false);

        final Context context = getContext();

        firebaseUser = firebaseAuth.getCurrentUser();
        Fabric.with(context, new Crashlytics());


        monthTextView = view.findViewById(R.id.currentMonthTextView);
        incomeTextView = view.findViewById(R.id.incomeTextView);
        expensesTextView = view.findViewById(R.id.expensesTextView);
        balanceTextView = view.findViewById(R.id.balanceTextView);

        allButton = view.findViewById(R.id.allButton);
        expensesButton = view.findViewById(R.id.expenseButton);
        incomeButton = view.findViewById(R.id.incomeButton);

        allRecyclerView = view.findViewById(R.id.allRecyclerView);
        expensesRecyclerView = view.findViewById(R.id.expenseRecyclerView);
        incomeRecyclerView= view.findViewById(R.id.incomeRecyclerView);

        expensesRecyclerView.setVisibility(View.GONE);
        incomeRecyclerView.setVisibility(View.GONE);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dateString = dateFormat.format(date);

        String monthString = dateString.substring(3,5);
        String month = monthString;

        switch (monthString) {
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "March";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "July";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "December";
                break;
        }

        monthTextView.setText(month);

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Cashflow/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allDetailsList.clear();
                incomeDetailsList.clear();
                expensesDetailsList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    CashflowDetails cashflowDetails = snapshot.getValue(CashflowDetails.class);
                    allDetailsList.add(cashflowDetails);

                    if(cashflowDetails.inout){
                        incomeDetailsList.add(cashflowDetails);
                    }else if(!cashflowDetails.inout){
                        expensesDetailsList.add(cashflowDetails);
                    }
                }

                int income = 0, expenses = 0, balance = 0;

                for(int i = 0; i < incomeDetailsList.size(); i++){
                    income += Float.parseFloat(incomeDetailsList.get(i).amount);
                }

                for(int i = 0; i < expensesDetailsList.size(); i++){
                    expenses += Float.parseFloat(expensesDetailsList.get(i).amount);
                }

                balance = income - expenses;

                incomeTextView.setText(Integer.toString(income));
                expensesTextView.setText(Integer.toString(expenses));
                balanceTextView.setText(Integer.toString(balance));

                allPassbookDetailList = new AllPassbookDetailList(allDetailsList, context);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                allRecyclerView.setLayoutManager(layoutManager);
                allRecyclerView.setItemAnimator(new DefaultItemAnimator());
                Collections.sort(allDetailsList, Collections.<CashflowDetails>reverseOrder());
                allRecyclerView.setAdapter(allPassbookDetailList);

                expensePassbookDetailList = new ExpensePassbookDetailList(expensesDetailsList, context);
                RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(context);
                expensesRecyclerView.setLayoutManager(layoutManager2);
                expensesRecyclerView.setItemAnimator(new DefaultItemAnimator());
                Collections.sort(expensesDetailsList, Collections.<CashflowDetails>reverseOrder());
                expensesRecyclerView.setAdapter(expensePassbookDetailList);

                incomePassbookDetailList = new IncomePassbookDetailList(incomeDetailsList, context);
                RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(context);
                incomeRecyclerView.setLayoutManager(layoutManager3);
                incomeRecyclerView.setItemAnimator(new DefaultItemAnimator());
                Collections.sort(incomeDetailsList, Collections.<CashflowDetails>reverseOrder());
                incomeRecyclerView.setAdapter(incomePassbookDetailList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeRecyclerView.setVisibility(View.GONE);
                expensesRecyclerView.setVisibility(View.GONE);
                allRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allRecyclerView.setVisibility(View.GONE);
                expensesRecyclerView.setVisibility(View.GONE);
                incomeRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allRecyclerView.setVisibility(View.GONE);
                incomeRecyclerView.setVisibility(View.GONE);
                expensesRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}
