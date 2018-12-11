package net.eazypg.eazypgmanager.DetailList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.eazypg.eazypgmanager.DetailsClasses.CashflowDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;

public class ExpensePassbookDetailList extends RecyclerView.Adapter<ExpensePassbookDetailList.MyHolder>{

    List<CashflowDetails> expensesPassbookDetailList;
    Context context;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    FirebaseStorage storage;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    final long ONE_MEGABYTE = 1024 * 1024;

    public ExpensePassbookDetailList(List<CashflowDetails> expensesPassbookDetailList, Context context) {
        this.expensesPassbookDetailList = expensesPassbookDetailList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return expensesPassbookDetailList.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.passbook_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final CashflowDetails cashflowDetails = expensesPassbookDetailList.get(position);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://eazypg-3dcb6.appspot.com").child("Expense");


        holder.amountTextView.setText(cashflowDetails.amount);
        holder.categoryTextView.setText(cashflowDetails.category);
        holder.timestampTextView.setText(cashflowDetails.date);
        holder.paidTextView.setText(cashflowDetails.paidBy);
        holder.paidToTextView.setText(cashflowDetails.paidTo);

        holder.plusMinusImage.setVisibility(View.GONE);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Expense");
                builder.setMessage("Are you sure you want to delete expense of Rs." + holder.amountTextView.getText().toString() + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Cashflow/");
                        databaseReference.child(cashflowDetails.expenseId).setValue(null);
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();

                return true;
            }
        });

        holder.billImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog builder = new Dialog(context);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));

                ImageView imageView = new ImageView(context);

                holder.billImageView.buildDrawingCache(true);

                Bitmap bitmap = Bitmap.createBitmap(holder.billImageView.getDrawingCache(true));
                imageView.setImageBitmap(bitmap);
                builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                        1000,
                        1000));
                builder.setCancelable(true);

                builder.show();
            }
        });

        storageReference.child(firebaseUser.getUid()).child(cashflowDetails.expenseId).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.billImageView.setImageBitmap(bitmap);
            }
        });

    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView amountTextView, timestampTextView, categoryTextView, paidTextView, paidToTextView;
        public ImageView plusMinusImage, billImageView;

        public MyHolder(View itemView){
            super(itemView);

            amountTextView = itemView.findViewById(R.id.amountTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            paidTextView = itemView.findViewById(R.id.paidForTextView);
            paidToTextView = itemView.findViewById(R.id.paidToTextView);

            billImageView = itemView.findViewById(R.id.billImageView);

            plusMinusImage = itemView.findViewById(R.id.plusMinusImage);
        }
    }
}