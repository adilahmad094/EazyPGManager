package net.eazypg.eazypgmanager.DetailList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.eazypg.eazypgmanager.DetailsClasses.GuestDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;

public class HostFriendDetailList extends RecyclerView.Adapter<HostFriendDetailList.MyHolder> {

    List<GuestDetails> guestDetailsList;

    private Context context;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.host_friend_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {

        final GuestDetails guestDetails = guestDetailsList.get(position);

        StorageReference storageReference = storage.getReferenceFromUrl("gs://eazypg-3dcb6.appspot.com").child("Guest").child(guestDetails.guestImageName);

        final long ONE_MEGABYTE = 1024 * 1024;

        /*storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.guestImageView.setImageBitmap(bitmap);
            }
        });*/

        holder.dateTextView.setText(guestDetails.date);
        holder.guestContactTextView.setText(guestDetails.guestContact);
       // holder.tenantContactTextView.setText(guestDetails.tenantPhone);
        holder.guestToTimeTextView.setText(guestDetails.guestToTime);
        holder.guestFromTimeTextView.setText(guestDetails.guestFromTime);
        holder.tenantNameTextView.setText(guestDetails.tenantName);
        holder.guestNameTextView.setText(guestDetails.guestName);
        holder.roomNumberTextView.setText(guestDetails.room);
        holder.callLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+ guestDetails.tenantPhone));
                    context.startActivity(callIntent);
                }
                catch (ActivityNotFoundException activityException) {
                    Toast.makeText(context, "Call failed", Toast.LENGTH_SHORT).show();
                }
                catch (SecurityException e) {
                    Toast.makeText(context, "Call failed!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return guestDetailsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

      //  public ImageView guestImageView;
        public TextView dateTextView;
        public TextView guestNameTextView;
        public TextView tenantNameTextView;
        public TextView guestFromTimeTextView;
        public TextView guestToTimeTextView;
        public TextView guestContactTextView;
        public TextView roomNumberTextView;
      //  public TextView tenantContactTextView;
        public LinearLayout callLL;

        public MyHolder(View itemView) {
            super(itemView);

           // guestImageView = itemView.findViewById(R.id.guestImageView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            guestNameTextView = itemView.findViewById(R.id.guestNameTextView);
            tenantNameTextView = itemView.findViewById(R.id.tenantNameTextView);
            guestFromTimeTextView = itemView.findViewById(R.id.guestFromTimeTextView);
            guestToTimeTextView = itemView.findViewById(R.id.guestToTimeTextView);
            guestContactTextView = itemView.findViewById(R.id.guestContactTextView);
            roomNumberTextView = itemView.findViewById(R.id.tenantRoomNumberTextView);

          //  tenantContactTextView = itemView.findViewById(R.id.tenantContactTextView);
            callLL = itemView.findViewById(R.id.hostAFriendCallLL);
        }
    }

    public HostFriendDetailList(List<GuestDetails> guestDetailsList , Context context) {
        this.guestDetailsList = guestDetailsList;
        this.context = context;
    }


}
