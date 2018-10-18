package net.eazypg.eazypgmanager.DetailList;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.eazypg.eazypgmanager.DetailsClasses.RoomApplianceDetails;
import net.eazypg.eazypgmanager.R;

import java.util.List;

public class RoomApplianceDetailList extends ArrayAdapter<RoomApplianceDetails> {

    private Activity context;
    private List<RoomApplianceDetails> roomApplianceList;

    public RoomApplianceDetailList(Activity context, List<RoomApplianceDetails> roomApplianceList){
        super(context, R.layout.room_appliance_detail_row, roomApplianceList);

        this.context = context;
        this.roomApplianceList = roomApplianceList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        View listItemViewRoomApplianceDialog = inflater.inflate(R.layout.room_appliance_detail_row, null, true);
        final RoomApplianceDetails roomApplianceDetails = roomApplianceList.get(position);

        TextView first = listItemViewRoomApplianceDialog.findViewById(R.id.firstTextView);
        TextView second = listItemViewRoomApplianceDialog.findViewById(R.id.secondTextView);
        TextView third = listItemViewRoomApplianceDialog.findViewById(R.id.thirdTextView);

        first.setText(roomApplianceDetails.getType());
        second.setText(roomApplianceDetails.getBrand());
        third.setText(roomApplianceDetails.getTimeSinceInstallation());

        return listItemViewRoomApplianceDialog;
    }
}
