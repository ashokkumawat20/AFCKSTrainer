package in.afckstechnologies.mail.afckstrainer.Adapter;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


import in.afckstechnologies.mail.afckstrainer.Models.ReconciliationDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;


/**
 * Created by admin on 3/18/2017.
 */

public class StudentReconcililationListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<ReconciliationDAO> data;
    ReconciliationDAO current;
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
    public StudentReconcililationListAdpter(Context context, List<ReconciliationDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_student_recon_details, parent, false);
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
        myHolder.name.setText(current.getStudent_Name());
        myHolder.name.setTag(position);
        myHolder.callingButton.setTag(position);
        myHolder.messageButton.setTag(position);
        myHolder.mobile_no.setTag(position);
        myHolder.mobile_no.setText(current.getMobileNo());


        myHolder.fees.setText(current.getFees());
        myHolder.fees.setTag(position);
        myHolder.date.setText(current.getDateTimeOfEntry());
        myHolder.date.setTag(position);

        myHolder.whatsappeButton.setTag(position);
        myHolder.app_status.setTag(position);
        myHolder.user_status.setTag(position);


        myHolder.mobile_status.setTag(position);

        myHolder.chkSelected.setTag(position);
        myHolder.chkSelected.setChecked(data.get(position).isSelected());
        myHolder.chkSelected.setTag(data.get(position));
        myHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                ReconciliationDAO contact = (ReconciliationDAO) cb.getTag();

                contact.setSelected(cb.isChecked());
                data.get(pos).setSelected(cb.isChecked());
                // Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();

            }
        });

        myHolder.callingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Log.i("call ph no", "" + current.getMobileNo());

                callIntent.setData(Uri.parse("tel:" + current.getMobileNo()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(callIntent);


            }
        });
        myHolder.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Log.i("call ph no", "" + current.getMobileNo());
                String phoneNumber = "9999999999";
                String smsBody = "";

// Add the phone number in the data
                Uri uri = Uri.parse("smsto:" + current.getMobileNo());
// Create intent with the action and data
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
// smsIntent.setData(uri); // We just set the data in the constructor above
// Set the message
                smsIntent.putExtra("sms_body", smsBody);

                context.startActivity(smsIntent);
               /* final CharSequence[] items = {" AFCKST ", " Mobile SMS "};
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Select The SMS Mode")
                        .setCancelable(false)
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {


                                switch (item) {
                                    case 0:
                                        // Your code when first option seletced
                                        sms_type = "AFCKST";
                                        break;
                                    case 1:
                                        // Your code when 2nd  option seletced
                                        sms_type = "Mobile_SMS";
                                        break;


                                }
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //  Your code when user clicked on OK
                                //  You can write the code  to save the selected item here
                                Log.d("sms_type", sms_type);
                                if (!sms_type.equals("")) {
                                    //sms sending
                                    Intent intent = new Intent(context, SMSSendingActivity.class);
                                    intent.putExtra("mobile_no", current.getMobile_no());
                                    intent.putExtra("sms_type", sms_type);
                                    context.startActivity(intent);
                                } else {

                                    Toast.makeText(context, "Please select SMS Mode!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //  Your code when user clicked on Cancel
                            }
                        }).create();
                dialog.show();*/

            }
        });

        myHolder.whatsappeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Log.i("call ph no", "" + current.getMobileNo());
                String phoneNumber = "7057326842";
                String smsBody = "";

                try {
                    Uri uri = Uri.parse("smsto:" + current.getMobileNo());
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                    sendIntent.setPackage("com.whatsapp");
                    context.startActivity(sendIntent);
                } catch (Exception e) {
                    Toast.makeText(context, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        });





    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView name, mobile_no, numbers,fees,date;
        TextView mail_id;
        ImageView callingButton, messageButton, whatsappeButton, takeBookSeat, app_status, user_status, mobile_status, infoButton, commentButton;
        public CheckBox chkSelected;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            fees = (TextView) itemView.findViewById(R.id.fees);
            date = (TextView) itemView.findViewById(R.id.date);
            callingButton = (ImageView) itemView.findViewById(R.id.callingButton);
            chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
            mobile_no = (TextView) itemView.findViewById(R.id.mobile_no);
            messageButton = (ImageView) itemView.findViewById(R.id.messageButton);
            whatsappeButton = (ImageView) itemView.findViewById(R.id.whatsappeButton);
            app_status = (ImageView) itemView.findViewById(R.id.app_status);
            user_status = (ImageView) itemView.findViewById(R.id.user_status);
            mobile_status = (ImageView) itemView.findViewById(R.id.mobile_status);

        }

    }

    //
    private class bookSeat extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            final String transaction = "Book seat with Zero fees";
            // String date = dpicker.getText().toString();
            final String amount = "0";

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final String datezero = df.format(c.getTime());
            jsonLeadObj = new JSONObject() {
                {
                    try {

                        put("user_id", user_id);
                        put("batch_id", preferences.getString("curr_batch_id", ""));
                        put("trans_id", transaction);
                        put("pay_date", datezero);
                        put("amount", amount);
                        put("status", 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_BOOKING_BATCH, jsonLeadObj);
            Log.i("resp", "leadListResponse" + centerListResponse);


            if (centerListResponse.compareTo("") != 0) {
                if (isJSONValid(centerListResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(centerListResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(centerListResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(context, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                // Close the progressdialog
                mProgressDialog.dismiss();
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    //
    protected boolean isJSONValid(String callReoprtResponse2) {
        // TODO Auto-generated method stub
        try {
            new JSONObject(callReoprtResponse2);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(callReoprtResponse2);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    // method to access in activity after updating selection
    public List<ReconciliationDAO> getSservicelist() {
        return data;
    }



}
