package in.afckstechnologies.mail.afckstrainer.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import in.afckstechnologies.mail.afckstrainer.Activity.ReconciliationStudentFeesActivity;
import in.afckstechnologies.mail.afckstrainer.Models.ReconciliationBankDAO;
import in.afckstechnologies.mail.afckstrainer.R;

/**
 * Created by admin on 3/18/2017.
 */

public class BankReconcililationListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<ReconciliationBankDAO> data;
    ReconciliationBankDAO current;
    int currentPos = 0;
    String id, id1;
    String centerId;
    int ID;
    int number = 1;

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String sms_type = "";


    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String centerListResponse = "";
    boolean status;
    String message = "";
    String msg = "";
    String user_id = "";
    String batch_id = "";


    // create constructor to innitilize context and data sent from MainActivity
    public BankReconcililationListAdpter(Context context, List<ReconciliationBankDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_reconbank_details, parent, false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;
        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
        myHolder.unique_id.setText(current.getUnique_id());
        myHolder.unique_id.setTag(position);
        myHolder.t_date.setText(formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy",current.getT_date()));
        myHolder.t_date.setTag(position);
        myHolder.narration.setText(current.getNarration());
        myHolder.narration.setTag(position);
        myHolder.layout_quotation.setTag(position);
        myHolder.amount.setText(current.getAmount());
        myHolder.amount.setTag(position);

        myHolder.layout_quotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("unique_id",current.getUnique_id());
                prefEditor.putString("amount",current.getAmount());
                prefEditor.putString("bank_name",current.getBank_id());
                prefEditor.putString("narration",current.getNarration());
                prefEditor.commit();
                Intent intent=new Intent(context, ReconciliationStudentFeesActivity.class);
                context.startActivity(intent);


            }
        });


    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView unique_id, t_date, narration,amount;
        LinearLayout layout_quotation;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            unique_id = (TextView) itemView.findViewById(R.id.unique_id);
            t_date = (TextView) itemView.findViewById(R.id.t_date);
            narration = (TextView) itemView.findViewById(R.id.narration);
            amount = (TextView) itemView.findViewById(R.id.amount);
            layout_quotation= (LinearLayout) itemView.findViewById(R.id.layout_quotation);

        }

    }



    // method to access in activity after updating selection
    public List<ReconciliationBankDAO> getSservicelist() {
        return data;
    }
    //
    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            //LOGE(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }


}
