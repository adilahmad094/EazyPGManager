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
                    income += Integer.parseInt(incomeDetailsList.get(i).amount);
                }

                for(int i = 0; i < expensesDetailsList.size(); i++){
                    expenses += Integer.parseInt(expensesDetailsList.get(i).amount);
                }

                balance = income - expenses;

                incomeTextView.setText(Integer.toString(income));
                expensesTextView.setText(Integer.toString(expenses));
                balanceTextView.setText(Integer.toString(balance));

                allPassbookDetailList = new AllPassbookDetailList(allDetailsList, context);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                allRecyclerView.setLayoutManager(layoutManager);
                allRecyclerView.setItemAnimator(new DefaultItemAnimator());
                allRecyclerView.setAdapter(allPassbookDetailList);

                expensePassbookDetailList = new ExpensePassbookDetailList(expensesDetailsList, context);
                RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(context);
                expensesRecyclerView.setLayoutManager(layoutManager2);
                expensesRecyclerView.setItemAnimator(new DefaultItemAnimator());
                expensesRecyclerView.setAdapter(expensePassbookDetailList);

                incomePassbookDetailList = new IncomePassbookDetailList(incomeDetailsList, context);
                RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(context);
                incomeRecyclerView.setLayoutManager(layoutManager3);
                incomeRecyclerView.setItemAnimator(new DefaultItemAnimator());
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

        /*Toolbar toolbar = findViewById(R.id.passbookToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView = findViewById(R.id.listViewPassbook);
        emptyList = findViewById(R.id.emptyListPassbook);
        listView.setEmptyView(emptyList);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Cashflow/");

        inflater = getLayoutInflater();

        listView = findViewById(R.id.listViewPassbook);
        addInflow = findViewById(R.id.inflow);
        addOutflow = findViewById(R.id.outflow);
        view1 = findViewById(R.id.passbooklayout);

        passbookDetailsList = new ArrayList<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                passbookDetailsList.clear();

                for(DataSnapshot dataSnapshotExpense : dataSnapshot.getChildren()){
                    CashflowDetails passbookDetails = dataSnapshotExpense.getValue(CashflowDetails.class);
                    passbookDetailsList.add(passbookDetails);
                }

                PassbookDetailList adapter = new PassbookDetailList(PassbookActivity.this, passbookDetailsList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addOutflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = inflater.inflate(R.layout.dialog_passbook_outflow, null);

                amount = viewDialog.findViewById(R.id.expenseAmountEditText);
                category = viewDialog.findViewById(R.id.categoryEditText);
                description = viewDialog.findViewById(R.id.descriptionEditText);
                paidBy = viewDialog.findViewById(R.id.paidByEditText);
                paidTo = viewDialog.findViewById(R.id.paidToEditText);

                final View titleView = inflater.inflate(R.layout.custom_titleoutflow, null);
                custom_title = titleView.findViewById(R.id.titleOutflow);

                AlertDialog.Builder builder = new AlertDialog.Builder(PassbookActivity.this);
                builder.setCustomTitle(custom_title);

                builder.setView(viewDialog);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final ProgressDialog progressDialog = ProgressDialog.show(PassbookActivity.this, "", "Saving...", true);

                        String amountString = amount.getText().toString();
                        String categoryString = category.getText().toString();
                        String descriptionString = description.getText().toString();
                        String paidByString = paidBy.getText().toString();
                        String paidToString = paidTo.getText().toString();
                        String uidOutflow = databaseReference.push().getKey();

                        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                        if(amountString.isEmpty()){
                            progressDialog.dismiss();
                            Toast.makeText(PassbookActivity.this, "Amount Required!", Toast.LENGTH_SHORT).show();
                        }else {
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            Date date = new Date();
                            String dateString = dateFormat.format(date);

                            CashflowDetails passbookDetails1 = new CashflowDetails(uidOutflow, amountString, categoryString, descriptionString, paidByString, paidToString, dateString, false);
                            databaseReference.child("Cashflow").child(uidOutflow).setValue(passbookDetails1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(PassbookActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(PassbookActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    }
                });

                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });

        addInflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = inflater.inflate(R.layout.dialog_passbook_inflow, null);

                amount = viewDialog.findViewById(R.id.inflowAmountEditText);
                category = viewDialog.findViewById(R.id.inflowCategoryEditText);
                description = viewDialog.findViewById(R.id.inflowDescriptionEditText);
                paidBy = viewDialog.findViewById(R.id.inflowPaidByEditText);

                final View titleView = inflater.inflate(R.layout.custom_titleinflow, null);
                custom_titleInflow = titleView.findViewById(R.id.custom_titleinflow);

                AlertDialog.Builder builder = new AlertDialog.Builder(PassbookActivity.this);
                builder.setCustomTitle(custom_titleInflow);

                builder.setView(viewDialog);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final ProgressDialog progressDialog = ProgressDialog.show(PassbookActivity.this, "", "Saving...", true);

                        String amountString = amount.getText().toString();
                        String categoryString = category.getText().toString();
                        String descriptionString = description.getText().toString();
                        String paidByString = paidBy.getText().toString();

                        String uidInflow = databaseReference.push().getKey();

                        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

                        if(amountString.isEmpty()){
                            progressDialog.dismiss();
                            Toast.makeText(PassbookActivity.this, "Amount Required!", Toast.LENGTH_SHORT).show();
                        }else {
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy\tHH:mm");
                            Date date = new Date();
                            String dateString = dateFormat.format(date);

                            CashflowDetails passbookDetails2 = new CashflowDetails(uidInflow, amountString, categoryString, descriptionString, paidByString, dateString, true);
                            databaseReference.child("Cashflow").child(uidInflow).setValue(passbookDetails2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(PassbookActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(PassbookActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });*/




        return view;
    }
}
