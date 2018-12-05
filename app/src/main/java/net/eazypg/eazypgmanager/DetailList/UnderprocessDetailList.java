package net.eazypg.eazypgmanager.DetailList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.katepratik.msg91api.MSG91;

import net.eazypg.eazypgmanager.DetailsClasses.TenantDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;


public class UnderprocessDetailList extends RecyclerView.Adapter<UnderprocessDetailList.MyHolder> {

    List<TenantDetails> underprocessTenantsList;
    Context context;

    public UnderprocessDetailList(List<TenantDetails> underprocessTenantsList, Context context) {
        this.underprocessTenantsList = underprocessTenantsList;
        this.context = context;
    }

    @Override
    public UnderprocessDetailList.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.under_process_tenant_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UnderprocessDetailList.MyHolder holder, final int position) {

        holder.tenantNameTextView.setText(underprocessTenantsList.get(position).name);
        holder.tenantRoomTextView.setText(underprocessTenantsList.get(position).room);
        holder.contactNumberTextView.setText(underprocessTenantsList.get(position).phone);

    }

    @Override
    public int getItemCount() {
        return underprocessTenantsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        public TextView tenantNameTextView, tenantRoomTextView, contactNumberTextView;

        public MyHolder(View itemView){
            super(itemView);

            tenantNameTextView = itemView.findViewById(R.id.tenantNameTextView);
            tenantRoomTextView = itemView.findViewById(R.id.roomNoTextView);
            contactNumberTextView = itemView.findViewById(R.id.contactTenantTextView);
        }
    }

}
