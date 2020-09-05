package in.afckstechnologies.mail.afckstrainer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;


import in.afckstechnologies.mail.afckstrainer.Models.BatchesForStudentsDAO;
import in.afckstechnologies.mail.afckstrainer.R;


/**
 * Created by admin on 3/18/2017.
 */

public class BatchesDetailsListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<BatchesForStudentsDAO> data;
    BatchesForStudentsDAO current;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    int clickFlag = 0;
    int ID;

    // create constructor to innitilize context and data sent from MainActivity
    public BatchesDetailsListAdpter(Context context, List<BatchesForStudentsDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();

    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_batches_details, parent, false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;
        // Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
        myHolder.txt_date.setText(current.getStart_date());
        myHolder.txt_date.setTag(position);
        myHolder.batchCode.setText(current.getId());
        myHolder.batchCode.setTag(position);
        myHolder.days.setText(current.getFrequency());
        myHolder.days.setTag(position);
        myHolder.timings.setText(current.getTimings());
        myHolder.timings.setTag(position);

        myHolder.clickLayout.setTag(position);
        myHolder.showLayout.setTag(position);

        myHolder.message.setTag(position);
        myHolder.whatsapp.setTag(position);

        String batchtype = current.getBatchtype().toString().trim();
        String status = "";

        if (batchtype.equalsIgnoreCase("1")) {
            //status = "Confirmed";
            myHolder.clickLayout.setBackgroundColor(Color.parseColor("#70AD47"));

        } else if (batchtype.equalsIgnoreCase("2")) {
            // status = "Tentative";
            myHolder.clickLayout.setBackgroundColor(Color.parseColor("#4472C4"));
        }

        myHolder.clickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickFlag == 0) {
                    clickFlag = 1;
                    myHolder.showLayout.setVisibility(View.VISIBLE);
                } else {
                    clickFlag = 0;
                    myHolder.showLayout.setVisibility(View.GONE);

                }
            }
        });
        myHolder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                String batchtype = current.getBatchtype().toString().trim();
                String status = "";

                if (batchtype.equalsIgnoreCase("1")) {
                    status = "Confirmed";

                } else if (batchtype.equalsIgnoreCase("2")) {
                    status = "Tentative";

                }


                String shareData = "Hi " + preferences.getString("st_first_name", "") + "," + "\n" +
                        context.getResources().getString(R.string.Afcksnew) + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.course_name) + " : " + current.getCourse_name() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.start_date) + " : " + current.getStart_date() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Frequency) + " : " + current.getFrequency() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Status) + " : " + status + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.location) + " : " + current.getBranch_name() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Fees) + " : " + current.getFees() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Trainer) + " : " + current.getFaculty_Name() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Timing) + " : " + current.getTimings() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Duration) + " : " + current.getDuration() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Notes) + " : " + current.getNotes() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Mobile) + " : " + "9762118718" + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Mailid) + " : " + "mohammed.raza@afcks.com" + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Site) + " : " + "www.afcks.com" + System.getProperty("line.separator");
                sendSMS(preferences.getString("student_mob_sms", ""), shareData);


            }


        });

        myHolder.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);

                String batchtype = current.getBatchtype().toString().trim();
                String status = "";

                if (batchtype.equalsIgnoreCase("1")) {
                    status = "Confirmed";

                } else if (batchtype.equalsIgnoreCase("2")) {
                    status = "Tentative";

                }


                String shareData = "Hi " + preferences.getString("st_first_name", "") + "," + "\n" +
                        context.getResources().getString(R.string.Afcks) + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.course_name) + " : " + "*" + current.getCourse_name() + "*" + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.start_date) + " : " + "*" + current.getStart_date() + "*" + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Frequency) + " : " + current.getFrequency() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Status) + " : " + status + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.location) + " : " + current.getBranch_name() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Fees) + " : " + current.getFees() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Trainer) + " : " + current.getFaculty_Name() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Timing) + " : " + current.getTimings() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Duration) + " : " + current.getDuration() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Notes) + " : " + current.getNotes() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Mobile) + " : " + "9762118718" + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Mailid) + " : " + "mohammed.raza@afcks.com" + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Site) + " : " + "www.afcks.com" + System.getProperty("line.separator");


                int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(shareData);
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("myLabel", shareData);
                    clipboard.setPrimaryClip(clip);
                }


                try {
                    Uri uri = Uri.parse("smsto:" + preferences.getString("student_mob_sms", ""));
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                    sendIntent.setPackage("com.whatsapp");
                    context.startActivity(sendIntent);
                } catch (Exception e) {
                    Toast.makeText(context, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            // smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            ArrayList<String> parts = smsManager.divideMessage(msg);
            smsManager.sendMultipartTextMessage(phoneNo, null, parts, null, null);

            Toast.makeText(context, "Message Sent", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView batchCode, txt_date, days, timings;
        ImageView message, whatsapp;
        LinearLayout clickLayout, showLayout;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            batchCode = (TextView) itemView.findViewById(R.id.batchCode);
            days = (TextView) itemView.findViewById(R.id.days);
            timings = (TextView) itemView.findViewById(R.id.timings);
            clickLayout = (LinearLayout) itemView.findViewById(R.id.clickLayout);
            showLayout = (LinearLayout) itemView.findViewById(R.id.showLayout);
            message = (ImageView) itemView.findViewById(R.id.message);
            whatsapp = (ImageView) itemView.findViewById(R.id.whatsapp);


        }

    }


}
