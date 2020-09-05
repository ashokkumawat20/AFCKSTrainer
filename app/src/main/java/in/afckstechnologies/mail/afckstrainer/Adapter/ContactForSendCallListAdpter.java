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


import in.afckstechnologies.mail.afckstrainer.MailAPI.GMailSender;
import in.afckstechnologies.mail.afckstrainer.MailAPI.GMailSender1;
import in.afckstechnologies.mail.afckstrainer.Models.ContactListDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.View.AddContactForCallingView;


/**
 * Created by Ashok on 5/30/2017.
 */

public class ContactForSendCallListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String centerListResponse = "";
    boolean status;

    String msg = "";
    String user_id = "";
    String mobile_no = "";
    String first_name="";
    String last_name="";
    String email_id="";

    private Context context;
    private LayoutInflater inflater;
    List<ContactListDAO> data;
    ContactListDAO current;
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
    GMailSender1 sender;

    String subject = "AFCKST";

    String emailid = "ashok.kumawat@afcks.com";
    static String sms_user="";
    static String sms_pass="";


    // create constructor to innitilize context and data sent from MainActivity
    public ContactForSendCallListAdpter(Context context, List<ContactListDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        sender = new GMailSender1(username, password);
        sms_user=preferences.getString("sms_username","");
        sms_pass=preferences.getString("sms_password","");

        username = preferences.getString("mail_username", "");
        password = preferences.getString("mail_password", "");
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_contact_send_call_details, parent, false);
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

        if(!current.getName().equals(""))
        {
            myHolder.name_id.setVisibility(View.VISIBLE);
            myHolder.name.setText(current.getName());
            myHolder.name.setTag(position);
        }
        myHolder.mail_id.setText(current.getEmailId());
        myHolder.mail_id.setTag(position);
        myHolder.calldatetime.setText(current.getDateTime());
        myHolder.calldatetime.setTag(position);
        myHolder.mailingButton.setTag(position);
        myHolder.messageButton.setTag(position);
        myHolder.mobile_no.setTag(position);
        myHolder.contactLinView.setTag(position);
        myHolder.mobile_no.setText(current.getMobileNumber());
        myHolder.whatsappeButton.setTag(position);
        myHolder.copyContent.setTag(position);
        myHolder.name_id.setTag(position);
        if(current.getCallType().equals("OUTGOING")) {
            myHolder.outgoing.setVisibility(View.VISIBLE);
            myHolder.missed.setVisibility(View.GONE);
            myHolder.incoming.setVisibility(View.GONE);
            myHolder.outgoing.setTag(position);
        }
        if(current.getCallType().equals("MISSED")) {
            myHolder.missed.setVisibility(View.VISIBLE);
            myHolder.incoming.setVisibility(View.GONE);
            myHolder.outgoing.setVisibility(View.GONE);
            myHolder.missed.setTag(position);
        }
        if(current.getCallType().equals("INCOMING")) {
            myHolder.incoming.setVisibility(View.VISIBLE);
            myHolder.outgoing.setVisibility(View.GONE);
            myHolder.missed.setVisibility(View.GONE);
            myHolder.incoming.setTag(position);
        }


        myHolder.mailingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Log.i("call ph no", "" + current.getMobileNumber());

                callIntent.setData(Uri.parse("tel:" + current.getMobileNumber()));
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
                Log.i("call ph no", "" + current.getMobileNumber());
                String phoneNumber = "9999999999";
                String smsBody = "";

// Add the phone number in the data
                Uri uri = Uri.parse("smsto:" + current.getMobileNumber());
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
                email_id=current.getEmailId();
                if(!current.getName().equals("")) {
                    String columns[] = current.getName().split(" ");

                    first_name = columns[0];
                    String name1 = columns[1];
                    String columns2[] = name1.split(",");
                    last_name = columns2[0];
                }

                if (current.getMobileNumber().length() == 10) {

                    mobile_no = current.getMobileNumber();

                }
                if (current.getMobileNumber().length() == 11) {
                    //Toast.makeText(context, "" + current.getMobileNumber().substring(3, 13), Toast.LENGTH_SHORT).show();
                    mobile_no = current.getMobileNumber().substring(1, 11);

                }
                if (current.getMobileNumber().length() == 13) {
                    //Toast.makeText(context, "" + current.getMobileNumber().substring(3, 13), Toast.LENGTH_SHORT).show();
                    mobile_no = current.getMobileNumber().substring(3, 13);

                }
                prefEditor.putString("sc_mobile_no", mobile_no);
                prefEditor.putString("sc_first_name", first_name);
                prefEditor.putString("sc_last_name", last_name);
                prefEditor.putString("sc_email_id", email_id);
                prefEditor.putString("flag","add");
                prefEditor.commit();
                AddContactForCallingView addContactForCallingView = new AddContactForCallingView();
                addContactForCallingView.show(((FragmentActivity) context).getSupportFragmentManager(), "addContactForCallingView");

            }
        });

        myHolder.copyContent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                String textToCopy = "";
                if (current.getMobileNumber().length() == 10) {

                    textToCopy = current.getMobileNumber();

                } else if (current.getMobileNumber().length() == 11) {

                    textToCopy = current.getMobileNumber().substring(1, 11);

                } else {

                    textToCopy = current.getMobileNumber().substring(3, 13);

                }
                Toast.makeText(context, "Copy mobile Number : " + textToCopy, Toast.LENGTH_SHORT).show();
                int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(textToCopy);
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("myLabel", textToCopy);
                    clipboard.setPrimaryClip(clip);
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

        TextView name, mobile_no,calldatetime;
        TextView mail_id;
        ImageView mailingButton, messageButton, whatsappeButton, copyContent,outgoing,missed,incoming;
        public CheckBox chkSelected;
        LinearLayout contactLinView,name_id;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            mail_id = (TextView) itemView.findViewById(R.id.mail_id);
            mailingButton = (ImageView) itemView.findViewById(R.id.mailingButton);
            // chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
            mobile_no = (TextView) itemView.findViewById(R.id.mobile_no);
            messageButton = (ImageView) itemView.findViewById(R.id.messageButton);
            whatsappeButton = (ImageView) itemView.findViewById(R.id.whatsappeButton);
            contactLinView = (LinearLayout) itemView.findViewById(R.id.contactLinView);
            copyContent = (ImageView) itemView.findViewById(R.id.copyContent);
            calldatetime= (TextView) itemView.findViewById(R.id.calldatetime);
            name_id=(LinearLayout) itemView.findViewById(R.id.name_id);
            outgoing = (ImageView) itemView.findViewById(R.id.outgoing);
            missed = (ImageView) itemView.findViewById(R.id.missed);
            incoming = (ImageView) itemView.findViewById(R.id.incoming);
        }

    }

    // method to access in activity after updating selection
    public List<ContactListDAO> getSservicelist() {
        return data;
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

    class MyAsyncClass extends AsyncTask<Void, Void, Void> {


        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... mApi) {
            try {
                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail(subject, message, username, emailid);


            } catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();

            Toast.makeText(context, "Email send", Toast.LENGTH_LONG).show();
        }


    }


}
