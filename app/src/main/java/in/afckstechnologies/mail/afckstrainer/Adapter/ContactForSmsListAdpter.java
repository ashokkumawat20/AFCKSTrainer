package in.afckstechnologies.mail.afckstrainer.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


import in.afckstechnologies.mail.afckstrainer.MailAPI.GMailSender;
import in.afckstechnologies.mail.afckstrainer.Models.ContactListDAO;
import in.afckstechnologies.mail.afckstrainer.R;


/**
 * Created by Ashok on 5/30/2017.
 */

public class ContactForSmsListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    GMailSender sender;

    String subject = "AFCKST";

    String emailid = "ashok.kumawat@afcks.com";
    String fileName="";
    static String sms_user="";
    static String sms_pass="";
    // create constructor to innitilize context and data sent from MainActivity
    public ContactForSmsListAdpter(Context context, List<ContactListDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        sender = new GMailSender(username, password);
        sms_user=preferences.getString("sms_username","");
        sms_pass=preferences.getString("sms_password","");

        username = preferences.getString("mail_username", "");
        password = preferences.getString("mail_password", "");
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_contact_student_details, parent, false);
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
        myHolder.name.setText(current.getName());
        myHolder.name.setTag(position);
        myHolder.mail_id.setText(current.getEmailId());
        myHolder.mail_id.setTag(position);
        myHolder.mailingButton.setTag(position);
        myHolder.messageButton.setTag(position);
        myHolder.mobile_no.setTag(position);
        myHolder.contactLinView.setTag(position);
        myHolder.mobile_no.setText(current.getMobileNumber());
        myHolder.whatsappeButton.setTag(position);
        myHolder.copyContent.setTag(position);

        myHolder.mailingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                emailid = current.getEmailId();
                String columns[] = current.getName().split(" ");
                String name = columns[0];
                message = preferences.getString("contact_MsgData", "").replace("[first_name]", name);
                subject = preferences.getString("contact_Subject", "");
                if (!emailid.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to send Mail ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    try {

                                        new MyAsyncClass().execute();

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
                    alert.setTitle("Sending Mail");
                    alert.show();
                } else {
                    Toast.makeText(context, "Don't have Email id", Toast.LENGTH_LONG).show();
                }
            }
        });
        myHolder.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                String columns[] = current.getName().split(" ");
                String name = columns[0];
                message = preferences.getString("contact_MsgData", "").replace("[first_name]", name);
                subject = preferences.getString("contact_Subject", "");
                final CharSequence[] items = {" AFCKST ", " Mobile SMS "};
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
                                    if (sms_type.equals("Mobile_SMS")) {
                                        sendSMS(current.getMobileNumber(), message);

                                    } else if (sms_type.equals("AFCKST")) {
                                        Log.d("msg", current.getMobileNumber() + "  " + message);
                                        System.out.println("message--->" + message);
                                        String result = sendSms1(current.getMobileNumber(), message);
                                        System.out.println("result :" + result);
                                    }

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
                dialog.show();

            }
        });

        myHolder.whatsappeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Log.i("call ph no", "" + current.getMobileNumber());
                String columns[] = current.getName().split(" ");
                String name = columns[0];
                message = preferences.getString("contact_MsgData", "").replace("[first_name]", name);
                String textToCopy = message;
                int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(textToCopy);
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("myLabel", textToCopy);
                    clipboard.setPrimaryClip(clip);
                }

               /* try {
                    Uri uri = Uri.parse("smsto:" + current.getMobileNumber());
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                    sendIntent.setPackage("com.whatsapp");
                    context.startActivity(sendIntent);
                } catch (Exception e) {
                    Toast.makeText(context, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                }*/

                String name1=getContactDetails(current.getMobileNumber());
                if(!name1.equals(""))
                {
                    try {
                        Uri uri = Uri.parse("smsto:" + current.getMobileNumber());
                        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                        sendIntent.setPackage("com.whatsapp");
                        context.startActivity(sendIntent);
                    } catch (Exception e) {
                        Toast.makeText(context, "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                    }}
                else
                {

                    Intent i = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                    i.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                    i.putExtra(ContactsContract.Intents.Insert.NAME,current.getName());
                    i.putExtra(ContactsContract.Intents.Insert.PHONE,current.getMobileNumber());
                    i.putExtra(ContactsContract.Intents.Insert.EMAIL,current.getEmailId());
                    context.startActivity(i);
                }


            }
        });
       /* myHolder.contactLinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                //  message = preferences.getString("contact_MsgData", "");
                String columns[] = current.getName().split(" ");
                String name = columns[0];
                message = preferences.getString("contact_MsgData", "").replace("[first_name]", name);
                subject = preferences.getString("contact_Subject", "");

                final CharSequence[] items = {" AFCKST ", " Mobile SMS "};
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
                                    if (sms_type.equals("Mobile_SMS")) {
                                        sendSMS(current.getMobileNumber(), message);

                                    } else if (sms_type.equals("AFCKST")) {
                                        Log.d("msg", current.getMobileNumber() + "  " + message);
                                        System.out.println("message--->" + message);
                                        String result = sendSms1(current.getMobileNumber(), message);
                                        System.out.println("result :" + result);
                                    }

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
                dialog.show();


            }
        });*/

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

        TextView name, mobile_no;
        TextView mail_id;
        ImageView mailingButton, messageButton, whatsappeButton, copyContent;
        public CheckBox chkSelected;
        LinearLayout contactLinView;


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
        }

    }

    // method to access in activity after updating selection
    public List<ContactListDAO> getSservicelist() {
        return data;
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
                sender.sendMail(subject, message, username, emailid,fileName);


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

    public String getContactDetails(String phoneNumber1) {
        String searchNumber = phoneNumber1;
        String phoneNumber = "", emailAddress = "",name="";
        StringBuffer sb = new StringBuffer();
        // Cursor c =  getContentResolver().query(contactData, null, null, null, null);
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(searchNumber));
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        if (c.moveToFirst()) {


            name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            //http://stackoverflow.com/questions/866769/how-to-call-android-contacts-list   our upvoted answer

            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if (hasPhone.equalsIgnoreCase("1"))
                hasPhone = "true";
            else
                hasPhone = "false";

            if (Boolean.parseBoolean(hasPhone)) {
                Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                while (phones.moveToNext()) {
                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phones.close();
            }

            // Find Email Addresses
            Cursor emails = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
            while (emails.moveToNext()) {
                emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            }
            emails.close();

            //mainActivity.onBackPressed();
            // Toast.makeText(mainactivity, "go go go", Toast.LENGTH_SHORT).show();

            //  tvname.setText("Name: "+name);
            //tvphone.setText("Phone: "+phoneNumber);
            //tvmail.setText("Email: "+emailAddress);

            sb.append("\nUser Name:--- " + name + " \nCall Type:--- "
                    + " \nMobile Number:--- " + phoneNumber
                    + " \nEmail Id:--- " + emailAddress);
            sb.append("\n----------------------------------");




// add elements to al, including duplicates


            Log.d("curs", name + " num" + phoneNumber + " " + "mail" + emailAddress);
        }
        c.close();
        return name;
    }


}
