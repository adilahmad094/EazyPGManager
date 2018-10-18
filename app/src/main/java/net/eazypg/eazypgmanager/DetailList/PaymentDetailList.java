package net.eazypg.eazypgmanager.DetailList;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import net.eazypg.eazypgmanager.DetailsClasses.PaymentDetails;
import net.eazypg.eazypgmanager.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentDetailList extends ArrayAdapter<PaymentDetails> {

    private Activity context;
    private List<PaymentDetails> paymentList;

    public PaymentDetailList(Activity context, List<PaymentDetails> paymentList) {

        super(context, R.layout.payment_row, paymentList);

        this.context = context;
        this.paymentList = paymentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final LayoutInflater inflater = context.getLayoutInflater();
        View listViewPayment = inflater.inflate(R.layout.payment_row, null, true);
        final PaymentDetails paymentDetails = paymentList.get(position);

        final TextView first = listViewPayment.findViewById(R.id.firstTextViewPayment);
        final TextView second = listViewPayment.findViewById(R.id.secondTextViewPayment);
        final TextView third = listViewPayment.findViewById(R.id.thirdTextViewPayment);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ArrayList<String> list = fetchData(paymentDetails.getPayId());
                    //Toast.makeText(context, "Test " + list.get(0), Toast.LENGTH_SHORT).show();
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            first.setText(list.get(0));
                            second.setText(list.get(1));
                            third.setText(list.get(2));
                        }
                    });
                } catch (RazorpayException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        return listViewPayment;
    }

    public ArrayList<String> fetchData(String payId) throws RazorpayException {
        ArrayList<String> list = new ArrayList<>();
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_sAUX9CKy38r5OA","qgqr52bFjPFQhE9AZDcs2UGp");
        Payment payment = razorpayClient.Payments.fetch(payId);
        list.add(payment.get("amount").toString());
        list.add(payment.get("id").toString());
        list.add(payment.get("created_at").toString());
        return list;
    }
}
