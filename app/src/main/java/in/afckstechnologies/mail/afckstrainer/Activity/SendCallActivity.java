package in.afckstechnologies.mail.afckstrainer.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Adapter.ContactForSendCallListAdpter;
import in.afckstechnologies.mail.afckstrainer.Models.ContactListDAO;
import in.afckstechnologies.mail.afckstrainer.R;

public class SendCallActivity extends AppCompatActivity {
    List<ContactListDAO> contactListDAOArrayList = new ArrayList<ContactListDAO>();
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    ProgressDialog mProgressDialog;
    ContactListDAO contactListDAO;
    ContactForSendCallListAdpter contactListAdpter;
    private RecyclerView mcontactList;
    String dName = "";
    String u_name="",phoneNumber = "",emailAddress = "";
    String temp_no="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_call);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        mcontactList = (RecyclerView) findViewById(R.id.contactsList);
        String lastcall = LastCall();
        Log.d("Last call", lastcall);
        new getContactList().execute();
    }
    public String LastCall() {

        StringBuffer sb = new StringBuffer();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return TODO;
        }
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, android.provider.CallLog.Calls.DATE + " DESC");

        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);


        sb.append("Call Details :");

        int i = 0;
        while (managedCursor.moveToNext()) {
            if (i < 10) {
                String uName = managedCursor.getString(name);
                // String emailAddress = managedCursor.getString(managedCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString(duration);
                String dir = null;


                int dircode = Integer.parseInt(callType);
                switch (dircode) {

                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        Log.d("miss call only",phNumber);
                        break;
                }

                sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                        + dir + " \nCall Date:--- " + callDayTime
                        + " \nCall duration in sec :--- " + callDuration
                        + " \nUser Details " );
                sb.append("\n----------------------------------");
//&& dir.equals("MISSED")
                if(phNumber.length()>=10  && !temp_no.equals(phNumber)) {
                    getContactDetails(phNumber);
                    contactListDAO = new ContactListDAO();
                    contactListDAO.setName(u_name);
                    contactListDAO.setMobileNumber(phNumber);
                    contactListDAO.setDateTime(""+callDayTime);
                    contactListDAO.setEmailId(emailAddress);
                    contactListDAO.setCallType(dir);
                    contactListDAOArrayList.add(contactListDAO);
                    temp_no=phNumber;

                }
                i++;

            }

        }
        managedCursor.close();
        return sb.toString();
    }

    public String getContactDetails(String phoneNumber1) {
        String searchNumber = phoneNumber1;
        u_name="";
        StringBuffer sb = new StringBuffer();
        // Cursor c =  getContentResolver().query(contactData, null, null, null, null);
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(searchNumber));
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        if (c.moveToFirst()) {


            u_name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            //http://stackoverflow.com/questions/866769/how-to-call-android-contacts-list   our upvoted answer

            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if (hasPhone.equalsIgnoreCase("1"))
                hasPhone = "true";
            else
                hasPhone = "false";

            if (Boolean.parseBoolean(hasPhone)) {
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                while (phones.moveToNext()) {
                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phones.close();
            }

            // Find Email Addresses
            Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
            while (emails.moveToNext()) {
                emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            }
            emails.close();

            //mainActivity.onBackPressed();
            // Toast.makeText(mainactivity, "go go go", Toast.LENGTH_SHORT).show();

            //  tvname.setText("Name: "+name);
            //tvphone.setText("Phone: "+phoneNumber);
            //tvmail.setText("Email: "+emailAddress);

            sb.append("\nUser Name:--- " + u_name + " \nCall Type:--- "
                    + " \nMobile Number:--- " + phoneNumber
                    + " \nEmail Id:--- " + emailAddress);
            sb.append("\n----------------------------------");


             /*   contactListDAO = new ContactListDAO();
                contactListDAO.setName(name);
                contactListDAO.setMobileNumber(phoneNumber);
                contactListDAO.setEmailId(emailAddress);
                contactListDAOArrayList.add(contactListDAO);*/




// add elements to al, including duplicates


            Log.d("curs", u_name + " num" + phoneNumber + " " + "mail" + emailAddress);
        }
        c.close();
        return sb.toString();
    }

    //
    private class getContactList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SendCallActivity.this);
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


            if (contactListDAOArrayList.size() > 0) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {


                    }
                });


            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (contactListDAOArrayList.size() > 0) {
                contactListAdpter = new ContactForSendCallListAdpter(SendCallActivity.this, contactListDAOArrayList);
                mcontactList.setAdapter(contactListAdpter);
                mcontactList.setLayoutManager(new LinearLayoutManager(SendCallActivity.this));
                contactListAdpter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }
}

