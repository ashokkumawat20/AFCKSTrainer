package in.afckstechnologies.mail.afckstrainer.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Adapter.ContactListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.StCenterListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.StCoursesListAdpter;
import in.afckstechnologies.mail.afckstrainer.Fragments.TabsFragmentActivity;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Models.BatchesDAO;
import in.afckstechnologies.mail.afckstrainer.Models.ContactListDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StCenterDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StCoursesDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentListDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.View.BookSeatView;
import in.afckstechnologies.mail.afckstrainer.View.RegistrationView;

public class SearchStudentEditPreActivity extends AppCompatActivity {
    LinearLayout takeChangeLocation, takeChangeCourses, takeChangedayprefrence, takeTemplate, takeCreateUser, takeBookSeat, takeEditUser, takeDeleteUser;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String newText;
    AutoCompleteTextView autoCompleteTextViewStudent;
    String studentResponse = "";
    public List<StudentListDAO> studentArrayList;
    public ArrayAdapter<StudentListDAO> aAdapter;
    public StudentListDAO callListDAO;
    private JSONObject jsonSchedule;
    boolean status;
    String message = "";
    String userDeleteResponse = "";
    ProgressDialog mProgressDialog;

    String user_id = "";
    JSONArray jsonArray;
    ImageView add_student, clear, dummy;

    JSONObject jsonCenterObj, jsonobject, jsonLeadObj, jsonLeadObj1;
    JSONArray jsonarray;
    String centerResponse = "";
    String centerListResponse = "";
    StCenterDAO centerDAO;
    private RecyclerView mleadList;
    //
    List<StCenterDAO> data;
    StCenterListAdpter centerListAdpter;
    ArrayList<String> centerIdArrayList;

    private RecyclerView cmleadList;
    //
    List<StCoursesDAO> cdata;
    StCoursesListAdpter coursesListAdpter;
    String courseListResponse = "";

    List<ContactListDAO> contactListDAOArrayList = new ArrayList<ContactListDAO>();
    ContactListDAO contactListDAO;
    ContactListAdpter contactListAdpter;
    private RecyclerView mcontactList;
    String dName = "";

    //
    ArrayList<BatchesDAO> batcheslist;
    String flag = "0";
    String course_id = "";
    String branch_id = "";
    String batch_id = "";
    String coursesResponse = "";

    private FloatingActionButton fab;
    String dir = null;
    TextView showContacts;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_search_student_pre_layout);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        centerIdArrayList = new ArrayList<>();
        mleadList = (RecyclerView) findViewById(R.id.centerList);
        cmleadList = (RecyclerView) findViewById(R.id.coursesList);
        mcontactList = (RecyclerView) findViewById(R.id.contactsList);
        autoCompleteTextViewStudent = (AutoCompleteTextView) findViewById(R.id.SearchStudent);
        clear = (ImageView) findViewById(R.id.clear);
        showContacts = (TextView) findViewById(R.id.showContacts);
        dummy = (ImageView) findViewById(R.id.dummy);
        // add_student = (ImageView) findViewById(R.id.add_student);
        studentArrayList = new ArrayList<StudentListDAO>();
        takeCreateUser = (LinearLayout) findViewById(R.id.takeCreateUser);
        takeChangeLocation = (LinearLayout) findViewById(R.id.takeChangeLocation);
        takeChangedayprefrence = (LinearLayout) findViewById(R.id.takeChangedayprefrence);
        takeChangeCourses = (LinearLayout) findViewById(R.id.takeChangeCourses);
        takeTemplate = (LinearLayout) findViewById(R.id.takeTemplate);
        prefEditor.putString("book_seat", "student_pre");
        prefEditor.commit();
        if (preferences.getString("quantity_for_contacts", "").equals("")) {
            prefEditor.putString("quantity_for_contacts", "5");
            prefEditor.commit();
        }

        showContacts.setText(preferences.getString("quantity_for_contacts", ""));
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefEditor.putString("student_name", newText);
                preferences.edit().remove("st_mobile_no").commit();
                preferences.edit().remove("emil_id").commit();
                preferences.edit().remove("student_last_name").commit();
                prefEditor.commit();
                RegistrationView registrationView = new RegistrationView();
                registrationView.show(getSupportFragmentManager(), "registrationView");


            }
        });

        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#223458")));


        takeCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefEditor.putString("student_name", newText);
                preferences.edit().remove("st_mobile_no").commit();
                preferences.edit().remove("emil_id").commit();
                preferences.edit().remove("student_last_name").commit();
                prefEditor.commit();
                RegistrationView registrationView = new RegistrationView();
                registrationView.show(getSupportFragmentManager(), "registrationView");

            }
        });

/*

        autoCompleteTextViewStudent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newText = s.toString();

                clear.setVisibility(View.VISIBLE);
                dummy.setVisibility(View.GONE);
                //   add_student.setVisibility(View.VISIBLE);
                getchannelPartnerSelect(newText);
            }

            @Override
            public void afterTextChanged(Editable s) {
               */
/* newText = s.toString();

                clear.setVisibility(View.VISIBLE);
                dummy.setVisibility(View.GONE);
                //   add_student.setVisibility(View.VISIBLE);
                getchannelPartnerSelect(newText);*//*

            }
        });
*/
        autoCompleteTextViewStudent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(autoCompleteTextViewStudent.getText())) {
                        clear.setVisibility(View.VISIBLE);
                        dummy.setVisibility(View.GONE);
                        newText = autoCompleteTextViewStudent.getText().toString();
                        getchannelPartnerSelect(newText);

                    }
                }
                return false;
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextViewStudent.setText("");
                clear.setVisibility(View.GONE);
                dummy.setVisibility(View.VISIBLE);
                //  add_student.setVisibility(View.GONE);
                mcontactList.setVisibility(View.VISIBLE);
                takeCreateUser.setVisibility(View.GONE);
                //  takeEditUser.setVisibility(View.GONE);
                // takeDeleteUser.setVisibility(View.GONE);
                user_id = "";
                if (centerResponse.compareTo("") != 0) {
                    data.clear(); // this list which you hava passed in Adapter for your listview
                    centerListAdpter.notifyDataSetChanged(); // notify to listview for refresh
                }
                if (courseListResponse.compareTo("") != 0) {
                    cdata.clear(); // this list which you hava passed in Adapter for your listview
                    coursesListAdpter.notifyDataSetChanged(); // notify to listview for refresh
                }
            }
        });


        RegistrationView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                //Toast.makeText(getApplicationContext(), messageText, Toast.LENGTH_LONG).show();
                autoCompleteTextViewStudent.setText(messageText);
                getchannelPartnerSelect(messageText);

            }
        });


        showContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (contactListDAOArrayList.size() > 0) {
                    contactListDAOArrayList.clear(); // this list which you hava passed in Adapter for your listview
                    contactListAdpter.notifyDataSetChanged(); // notify to listview for refresh
                }
                String lastcall = LastCall();
                Log.d("Last call", lastcall);
                new getContactList().execute();
            }
        });

        showContacts.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchStudentEditPreActivity.this);
                alertDialog.setMessage("Enter Quantity for display contacts");

                final EditText input = new EditText(SearchStudentEditPreActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                // alertDialog.setIcon(R.drawable.msg_img);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String entity = input.getText().toString();
                                showContacts.setText("" + entity);
                                prefEditor.putString("quantity_for_contacts", entity);
                                prefEditor.commit();
                                if (contactListDAOArrayList.size() > 0) {
                                    contactListDAOArrayList.clear(); // this list which you hava passed in Adapter for your listview
                                    contactListAdpter.notifyDataSetChanged(); // notify to listview for refresh
                                }
                                String lastcall = LastCall();
                                Log.d("Last call", lastcall);
                                new getContactList().execute();


                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
                return true;
            }
        });


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
            if (i < Integer.parseInt(preferences.getString("quantity_for_contacts", ""))) {
                String uName = managedCursor.getString(name);
                // String emailAddress = managedCursor.getString(managedCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString(duration);


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
                        break;
                }

                sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                        + dir + " \nCall Date:--- " + callDayTime
                        + " \nCall duration in sec :--- " + callDuration
                        + " \nUser Details " + getContactDetails(phNumber));
                sb.append("\n----------------------------------");
                i++;

            }

        }
        managedCursor.close();
        return sb.toString();
    }

    public String getContactDetails(String phoneNumber1) {
        String searchNumber = phoneNumber1;

        StringBuffer sb = new StringBuffer();
        // Cursor c =  getContentResolver().query(contactData, null, null, null, null);
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(searchNumber));
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        if (c.moveToFirst()) {

            String phoneNumber = "", emailAddress = "";
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
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

            sb.append("\nUser Name:--- " + name + " \nCall Type:--- "
                    + " \nMobile Number:--- " + phoneNumber
                    + " \nEmail Id:--- " + emailAddress);
            sb.append("\n----------------------------------");

            if (dName.equals("") || !dName.equals(phoneNumber)) {
                contactListDAO = new ContactListDAO();
                contactListDAO.setName(name);
                contactListDAO.setMobileNumber(phoneNumber);
                contactListDAO.setEmailId(emailAddress);
                contactListDAO.setCallType(dir);
                contactListDAOArrayList.add(contactListDAO);
                dName = phoneNumber;
            }


// add elements to al, including duplicates


            Log.d("curs", name + " num" + phoneNumber + " " + "mail" + emailAddress);
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
            mProgressDialog = new ProgressDialog(SearchStudentEditPreActivity.this);
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
                        // Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (contactListDAOArrayList.size() > 0) {
                contactListAdpter = new ContactListAdpter(SearchStudentEditPreActivity.this, contactListDAOArrayList);
                mcontactList.setAdapter(contactListAdpter);
                mcontactList.setLayoutManager(new LinearLayoutManager(SearchStudentEditPreActivity.this));
                contactListAdpter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }


    public void getchannelPartnerSelect(final String channelPartnerSelect) {
        // Toast.makeText(getBaseContext(), "dhiraj" + channelPartnerSelect, Toast.LENGTH_LONG).show();
        jsonSchedule = new JSONObject() {
            {
                try {
                    put("Prefixtext", channelPartnerSelect);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("json exception", "json exception" + e);
                }
            }
        };


        Thread objectThread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();
                String baseURL = "http://sales.pibm.net:86/service.svc/CallListService/GetChannelPartner";
                Log.i("json", "json" + jsonSchedule);
                //SEND RESPONSE
                // studentResponse = serviceAccess.SendHttpPost(baseURL, jsonSchedule);
                studentResponse = serviceAccess.SendHttpPost(Config.URL_GETALLSTUDENTSBYPREFIX, jsonSchedule);
                Log.i("resp", "loginResponse" + studentResponse);


                try {
                    JSONArray callArrayList = new JSONArray(studentResponse);
                    studentArrayList.clear();
                    // user_id="";
                    for (int i = 0; i < callArrayList.length(); i++) {
                        callListDAO = new StudentListDAO();
                        JSONObject cityJsonObject = callArrayList.getJSONObject(i);
                        studentArrayList.add(new StudentListDAO(cityJsonObject.getString("Details"), cityJsonObject.getString("id"), cityJsonObject.getString("first_name"), cityJsonObject.getString("last_name"), cityJsonObject.getString("mobile_no"), cityJsonObject.getString("email_id")));

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        aAdapter = new ArrayAdapter<StudentListDAO>(getApplicationContext(), R.layout.item, studentArrayList);
                        autoCompleteTextViewStudent.setAdapter(aAdapter);

                        autoCompleteTextViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                                // String s = parent.getItemAtPosition(i).toString();
                                StudentListDAO student = (StudentListDAO) parent.getAdapter().getItem(i);
                                user_id = student.getId();
                                Log.d("Id---->", user_id + "" + student.getId());
                                prefEditor.putString("user_id", user_id);
                                prefEditor.putString("st_first_name", student.getFirst_Name());
                                prefEditor.putString("st_last_name", student.getLast_Name());
                                prefEditor.putString("student_mob_sms", student.getMobile_No());
                                prefEditor.putString("student_mail_id", student.getStudent_mail());

                                prefEditor.commit();
                                // add_student.setVisibility(View.GONE);
                                // mleadList.setVisibility(View.VISIBLE);
                                //   cmleadList.setVisibility(View.VISIBLE);
                                // mcontactList.setVisibility(View.GONE);
                                // takeCreateUser.setVisibility(View.VISIBLE);
                                // takeEditUser.setVisibility(View.VISIBLE);
                                //  takeDeleteUser.setVisibility(View.VISIBLE);

                                //
                                finish();
                                prefEditor.putString("flag_edit_pre", "2");
                                prefEditor.putString("temp_color_change", "");
                                prefEditor.putString("temp_color_change_center", "");
                                prefEditor.commit();
                                Intent intent = new Intent(SearchStudentEditPreActivity.this, DisplayStudentEditPreActivity.class);
                                startActivity(intent);
                                //
                                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                //  new getLocationList().execute();


                            }
                        });
                        aAdapter.notifyDataSetChanged();

                    }
                });


            }
        });

        objectThread.start();

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

}