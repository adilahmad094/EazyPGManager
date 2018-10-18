package net.eazypg.eazypgmanager.DetailList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.eazypg.eazypgmanager.DetailsClasses.ComplaintDetails;
import net.eazypg.eazypgmanager.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecentComplaintDetailList extends RecyclerView.Adapter<RecentComplaintDetailList.MyViewHolder> {

    List<ComplaintDetails> complaintDetailsList;
    DateFormat dateFormat;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTextView, complaintIdTextView, categoryTextView, subCategoryTextView, subSubCategoryTextView, tenantNameTextView, roomNumberTenantTextView, descriptionTextView;

        public MyViewHolder(View view) {
            super(view);

            dateTextView = view.findViewById(R.id.dateTextView);
            complaintIdTextView = view.findViewById(R.id.complaintIdTextView);
            categoryTextView = view.findViewById(R.id.categoryTextView);
            subCategoryTextView = view.findViewById(R.id.subCategoryTextView);
            subSubCategoryTextView = view.findViewById(R.id.subSubCategoryTextView);
            tenantNameTextView = view.findViewById(R.id.tenantNameTextView);
            roomNumberTenantTextView = view.findViewById(R.id.roomNumberTenantTextView);
            descriptionTextView = view.findViewById(R.id.descriptionTextView);

        }
    }

    public RecentComplaintDetailList(List<ComplaintDetails> complaintDetailsList) {
        this.complaintDetailsList = complaintDetailsList;
    }

    @Override
    public RecentComplaintDetailList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_complaint_row, parent, false);

        return new RecentComplaintDetailList.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecentComplaintDetailList.MyViewHolder holder, int position) {

        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        ComplaintDetails complaintDetails = complaintDetailsList.get(position);

        holder.dateTextView.setText(dateFormat.format(complaintDetails.date));
        holder.complaintIdTextView.setText(complaintDetails.complaintId);
        holder.categoryTextView.setText(complaintDetails.firstLevel);
        holder.subCategoryTextView.setText(complaintDetails.secondLevel);
        holder.subSubCategoryTextView.setText(complaintDetails.thirdLevel);
        holder.tenantNameTextView.setText(complaintDetails.name);
        holder.roomNumberTenantTextView.setText(complaintDetails.roomNo);
        holder.descriptionTextView.setText(complaintDetails.description);


    }

    @Override
    public int getItemCount() {
        return complaintDetailsList.size();
    }
}
