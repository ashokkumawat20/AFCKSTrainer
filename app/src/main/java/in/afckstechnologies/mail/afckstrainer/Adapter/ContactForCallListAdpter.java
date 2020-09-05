package in.afckstechnologies.mail.afckstrainer.Adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


import in.afckstechnologies.mail.afckstrainer.Models.ContactCallingListDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.View.AddContactForCallingView;


/**
 * Created by Ashok on 5/30/2017.
 */

public class ContactForCallListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String centerListResponse = "";
    boolean status;

    String msg = "";
    String user_id = "";
    private static SmsListener mListener;

    private Context context;
    private LayoutInflater inflater;
    List<ContactCallingListDAO> data;
    ContactCallingListDAO current;
    int currentPos = 0;
    String id, id1;
    String centerId;
    int ID;
    int number = 1;

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String sms_type = "";
    String message = "Hi Ashok";
    public static  String username = "";
    public static String password = "";
    //GMailSender sender;

    String subject = "AFCKST";

    String emailid = "ashok.kumawat@afcks.com";

    static String sms_user="";
    static String sms_pass="";
    // create constructor to innitilize context and data sent from MainActivity
    public ContactForCallListAdpter(Context context, List<ContactCallingListDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        sms_user=preferences.getString("sms_username","");
        sms_pass=preferences.getString("sms_password","");
        username = preferences.getString("mail_username", "");
        password = preferences.getString("mail_password", "");
       // sender = new GMailSender(username, password);
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_contact_call_details, parent, false);
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
        myHolder.name.setText(current.getFirst_name()+" "+current.getLast_name());
        myHolder.name.setTag(position);
        myHolder.startdate.setText(current.getStart_date());
        myHolder.startdate.setTag(position);
        myHolder.enddate.setText(current.getEnd_date());
        myHolder.enddate.setTag(position);
        myHolder.comments.setText(current.getNotes());
        myHolder.comments.setTag(position);

        myHolder.mailingButton.setTag(position);
        myHolder.messageButton.setTag(position);
        myHolder.mobile_no.setText(current.getMobile_no());
        myHolder.mobile_no.setTag(position);
        myHolder.contactLinView.setTag(position);
        myHolder.mobile_no.setText(current.getMobile_no());
        myHolder.whatsappeButton.setTag(position);
        myHolder.copyContent.setTag(position);
        myHolder.callingoff.setTag(position);

         if(current.getStatus().equals("0"))
         {
             myHolder.callingoff.setVisibility(View.VISIBLE);
         }
        if(current.getStatus().equals("1"))
        {
            myHolder.whatsappeButton.setVisibility(View.VISIBLE);
        }

        myHolder.comments.setText(current.getNotes());
        myHolder.comments.setTag(position);
        myHolder.mailingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Log.i("call ph no", "" + current.getMobile_no());

             /*   prefEditor.putString("callingno", current.getMobile_no());
                prefEditor.putString("notes", current.getNotes());
                prefEditor.commit();
                CallingDetailsView callingDetailsView = new CallingDetailsView();
                callingDetailsView.show(((FragmentActivity) context).getSupportFragmentManager(), "callingDetailsView");
*/
                   callIntent.setData(Uri.parse("tel:" + current.getMobile_no()));
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
                Log.i("call ph no", "" + current.getMobile_no());
                String phoneNumber = "9999999999";
                String smsBody = "";

// Add the phone number in the data
                Uri uri = Uri.parse("smsto:" + current.getMobile_no());
// Create intent with the action and data
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
// smsIntent.setData(uri); // We just set the data in the constructor above
// Set the message
                smsIntent.putExtra("sms_body", smsBody);

                context.startActivity(smsIntent);
            }
        });

        myHolder.whatsappeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                id=current.getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to Close Call ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {

                                    new upDateCallForUser().execute();

                                } catch (Exception ex) {
                                    Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
                                }
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Closing Call");
                alert.show();
            }
        });

        myHolder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                id=current.getId();
                prefEditor.putString("flag","update");
                prefEditor.putString("id", current.getId());
                prefEditor.putString("notes", current.getNotes());
                prefEditor.putString("startDate", current.getStart_date());
                prefEditor.putString("endDate", current.getEnd_date());
                prefEditor.commit();
                AddContactForCallingView addContactForCallingView = new AddContactForCallingView();
                addContactForCallingView.show(((FragmentActivity) context).getSupportFragmentManager(), "addContactForCallingView");


            }
        });

    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView name, mobile_no;
        TextView startdate,enddate,comments;
        ImageView mailingButton, messageButton, whatsappeButton, copyContent,callingoff;
        public CheckBox chkSelected;
        LinearLayout contactLinView;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            startdate = (TextView) itemView.findViewById(R.id.startdate);
            enddate = (TextView) itemView.findViewById(R.id.enddate);
            comments = (TextView) itemView.findViewById(R.id.comments);
            mailingButton = (ImageView) itemView.findViewById(R.id.mailingButton);
            // chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
            mobile_no = (TextView) itemView.findViewById(R.id.mobile_no);
            messageButton = (ImageView) itemView.findViewById(R.id.messageButton);
            whatsappeButton = (ImageView) itemView.findViewById(R.id.whatsappeButton);
            callingoff= (ImageView) itemView.findViewById(R.id.callingoff);
            contactLinView = (LinearLayout) itemView.findViewById(R.id.contactLinView);
            copyContent = (ImageView) itemView.findViewById(R.id.copyContent);
        }

    }

    // method to access in activity after updating selection
    public List<ContactCallingListDAO> getSservicelist() {
        return data;
    }



    //
    private class upDateCallForUser extends AsyncTask<Void, Void, Void> {
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

            jsonLeadObj = new JSONObject() {
                {
                    try {

                        put("id", id);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEUSERCALLINGDETAILS, jsonLeadObj);
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
                mListener.messageReceived(message);

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

    //
    public static void bindListener(SmsListener listener) {
        mListener = listener;
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

    public static String sendSms1(String tempMobileNumber, String message) {
        String sResult = null;
        try {
// Construct data
            //String phonenumbers = "9657816221";
            String data = "user=" + URLEncoder.encode(sms_user, "UTF-8");
            data += "&password=" + URLEncoder.encode(sms_pass, "UTF-8");
            data += "&message=" + URLEncoder.encode(message, "UTF-8");
            data += "&sender=" + URLEncoder.encode("AFCKST", "UTF-8");
            data += "&mobile=" + URLEncoder.encode(tempMobileNumber, "UTF-8");
            data += "&type=" + URLEncoder.encode("3", "UTF-8");
// Send data
            URL url = new URL("http://login.bulksmsgateway.in/sendmessage.php?" + data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
// Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult1 = "";
            while ((line = rd.readLine()) != null) {
// Process line...
                sResult1 = sResult1 + line + " ";
            }
            wr.close();
            rd.close();
            return sResult1;
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
    }




}
