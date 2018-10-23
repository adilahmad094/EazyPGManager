package net.eazypg.eazypgmanager.DetailList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.eazypg.eazypgmanager.DetailsClasses.CashflowDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;

public class AllPassbookDetailList extends RecyclerView.Adapter<AllPassbookDetailList.MyHolder>{

    List<CashflowDetails> allPassbookDetailList;
    Context context;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    FirebaseStorage storage;

    final long ONE_MEGABYTE = 1024 * 1024;

    public AllPassbookDetailList(List<CashflowDetails> allPassbookDetailList, Context context) {
        this.allPassbookDetailList = allPassbookDetailList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return allPassbookDetailList.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.passbook_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        CashflowDetails cashflowDetails = allPassbookDetailList.get(position);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://eazypg-3dcb6.appspot.com").child("Expense");

        holder.amountTextView.setText(cashflowDetails.amount);
        holder.categoryTextView.setText(cashflowDetails.category);
        holder.timestampTextView.setText(cashflowDetails.date);
        holder.paidTextView.setText(cashflowDetails.paidBy);
        holder.paidToTextView.setText(cashflowDetails.paidTo);

        storageReference.child(firebaseUser.getUid()).child(cashflowDetails.expenseId).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.billImageView.setImageBitmap(bitmap);
            }
        });


        if(cashflowDetails.inout){
            holder.plusMinusImage.setImageResource(R.drawable.plus);
        }else{
            holder.plusMinusImage.setImageResource(R.drawable.remove);

        }
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
            plusMinusImage = itemView.findViewById(R.id.plusMinusImage);
            billImageView = itemView.findViewById(R.id.billImageView);
        }
    }
}
