package net.eazypg.eazypgmanager.DetailList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.eazypg.eazypgmanager.DetailsClasses.GuestDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;

public class HostFriendDetailList extends RecyclerView.Adapter<HostFriendDetailList.MyHolder> {

    List<GuestDetails> guestDetailsList;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.host_friend_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {

        GuestDetails guestDetails = guestDetailsList.get(position);

        StorageReference storageReference = storage.getReferenceFromUrl("gs://eazypg-3dcb6.appspot.com").child("Guest").child(guestDetails.guestImageName);

        final long ONE_MEGABYTE = 1024 * 1024;

        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.guestImageView.setImageBitmap(bitmap);
            }
        });

        holder.dateTextView.setText(guestDetails.date);
        holder.guestContactTextView.setText(guestDetails.guestContact);
        holder.tenantContactTextView.setText(guestDetails.tenantPhone);
        holder.guestToTimeTextView.setText(guestDetails.guestToTime);
        holder.guestFromTimeTextView.setText(guestDetails.guestFromTime);
        holder.tenantNameTextView.setText(guestDetails.tenantName);
        holder.guestNameTextView.setText(guestDetails.guestName);

    }

    @Override
    public int getItemCount() {
        return guestDetailsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public ImageView guestImageView;
        public TextView dateTextView;
        public TextView guestNameTextView;
        public TextView tenantNameTextView;
        public TextView guestFromTimeTextView;
        public TextView guestToTimeTextView;
        public TextView guestContactTextView;
        public TextView tenantContactTextView;

        public MyHolder(View itemView) {
            super(itemView);

            guestImageView = itemView.findViewById(R.id.guestImageView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            guestNameTextView = itemView.findViewById(R.id.guestNameTextView);
            tenantNameTextView = itemView.findViewById(R.id.tenantNameTextView);
            guestFromTimeTextView = itemView.findViewById(R.id.guestFromTimeTextView);
            guestToTimeTextView = itemView.findViewById(R.id.guestToTimeTextView);
            guestContactTextView = itemView.findViewById(R.id.guestContactTextView);
            tenantContactTextView = itemView.findViewById(R.id.tenantContactTextView);
        }
    }

    public HostFriendDetailList(List<GuestDetails> guestDetailsList) {
        this.guestDetailsList = guestDetailsList;
    }


}
