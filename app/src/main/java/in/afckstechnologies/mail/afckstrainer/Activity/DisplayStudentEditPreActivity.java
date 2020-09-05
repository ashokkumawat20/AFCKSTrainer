package in.afckstechnologies.mail.afckstrainer.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Adapter.ContactListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.PreBatchDetailsListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.StCenterListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.StCoursesListAdpter;
import in.afckstechnologies.mail.afckstrainer.Fragments.TabsFragmentActivity;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Models.BatchesDAO;
import in.afckstechnologies.mail.afckstrainer.Models.ContactListDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StCenterDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StCoursesDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentListDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsInBatchListDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.View.BookSeatView;
import in.afckstechnologies.mail.afckstrainer.View.CommentAddView;
import in.afckstechnologies.mail.afckstrainer.View.CommentsDetailsView;
import in.afckstechnologies.mail.afckstrainer.View.MultipleCommentAddView;
import in.afckstechnologies.mail.afckstrainer.View.RegistrationView;

public class DisplayStudentEditPreActivity extends AppCompatActivity {
    LinearLayout takeChangeLocation, takeChangeCourses, takeChangedayprefrence, takeTemplate, takeCreateComment, takeBookSeat, takeEditUser, takeDeleteUser;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    TextView autoCompleteTextViewStudent;
    String studentResponse = "";
    public List<StudentListDAO> studentArrayList;
    public ArrayAdapter<StudentListDAO> aAdapter;
    public StudentListDAO callListDAO;
    private JSONObject jsonSchedule;
    boolean status;
    String message = "";
    String userDeleteResponse = "", preBatchDetailsResponse = "";
    ProgressDialog mProgressDialog;
    String user_id = "";
    JSONArray jsonArray;
    ImageView  clear, whatsapp, calling;
    JSONObject jsonCenterObj, jsonobject, jsonLeadObj, jsonLeadObj1, jsonObj1;
    JSONArray jsonarray;
    String centerResponse = "";
    String  getContinueDiscontinueCountusersResponse = "";
    private RecyclerView mleadList;
    //
    List<StCenterDAO> data;
    List<StudentsInBatchListDAO> pre_batch_data;
    StCenterListAdpter centerListAdpter;
    ArrayList<String> centerIdArrayList;

    private RecyclerView cmleadList;
    private RecyclerView preBatchStudentsDetailsList;
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
    String studentcommentListResponse = "";
    TextView commentEdtTxt;
    //
    Button userPreferences, userbatches;
    PreBatchDetailsListAdpter preBatchDetailsListAdpter;
    public static SmsListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_student_edit_pre);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();

        Intent intent = getIntent();
        user_id = preferences.getString("user_id", "");
        centerIdArrayList = new ArrayList<>();
        mleadList = (RecyclerView) findViewById(R.id.centerList);
        cmleadList = (RecyclerView) findViewById(R.id.coursesList);
        mcontactList = (RecyclerView) findViewById(R.id.contactsList);
        preBatchStudentsDetailsList = (RecyclerView) findViewById(R.id.preBatchStudentsDetailsList);
        autoCompleteTextViewStudent = (TextView) findViewById(R.id.SearchStudent);
        userPreferences = (Button) findViewById(R.id.userPreferences);
        userbatches = (Button) findViewById(R.id.userbatches);
        autoCompleteTextViewStudent.setText(preferences.getString("st_first_name", "") + " " + preferences.getString("st_last_name", ""));
        clear = (ImageView) findViewById(R.id.clear);
        // add_student = (ImageView) findViewById(R.id.add_student);
        whatsapp = (ImageView) findViewById(R.id.whatsapp);
        calling = (ImageView) findViewById(R.id.calling);
        studentArrayList = new ArrayList<StudentListDAO>();
        takeCreateComment = (LinearLayout) findViewById(R.id.takeCreateComment);
        takeChangeLocation = (LinearLayout) findViewById(R.id.takeChangeLocation);
        takeChangedayprefrence = (LinearLayout) findViewById(R.id.takeChangedayprefrence);
        takeChangeCourses = (LinearLayout) findViewById(R.id.takeChangeCourses);
        takeTemplate = (LinearLayout) findViewById(R.id.takeTemplate);
        commentEdtTxt = (TextView) findViewById(R.id.commentEdtTxt);
        prefEditor.putString("book_seat", "student_pre");
        prefEditor.commit();
        MultipleCommentAddView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                commentEdtTxt.setText(messageText);
                //  new getStudentCommentDetailsList().execute();
            }
        });

        CommentAddView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                commentEdtTxt.setText(messageText);
                //  new getStudentCommentDetailsList().execute();
            }
        });
        takeBookSeat = (LinearLayout) findViewById(R.id.takeBookSeat);
        takeBookSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preferences.getString("user_id", "").equals("")) {
                    BookSeatView bookSeatView = new BookSeatView();
                    bookSeatView.show(getSupportFragmentManager(), "registrationView");
                } else {

                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }

            }
        });
        takeCreateComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MultipleCommentAddView commentAddView = new MultipleCommentAddView();
                commentAddView.show(getSupportFragmentManager(), "commentAddView");

            }
        });

        takeCreateComment.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                prefEditor.putString("da", preferences.getString("st_first_name", "") + " " + preferences.getString("st_last_name", ""));
                prefEditor.commit();
                CommentsDetailsView commentAddView = new CommentsDetailsView();
                commentAddView.show(getSupportFragmentManager(), "commentAddView");
                return true;
            }
        });

        commentEdtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefEditor.putString("temp_type_sms", "1");
                prefEditor.commit();
                CommentAddView commentAddView = new CommentAddView();
                commentAddView.show(getSupportFragmentManager(), "commentAddView");
            }
        });

        takeEditUser = (LinearLayout) findViewById(R.id.takeEditUser);
        takeEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preferences.getString("user_id", "").equals("")) {
                    prefEditor.putString("edit_u_p_flag", "dfu");
                    prefEditor.commit();
                    Intent intent = new Intent(DisplayStudentEditPreActivity.this, Activity_User_Profile.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }

            }
        });
        takeDeleteUser = (LinearLayout) findViewById(R.id.takeDeleteUser);
        takeDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preferences.getString("user_id", "").equals("")) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(DisplayStudentEditPreActivity.this);
                    builder.setMessage("Do you want to delete user ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new deleteUser().execute();
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
                    alert.setTitle("Deleting user");
                    alert.show();

                } else {

                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }

            }
        });
        takeChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preferences.getString("user_id", "").equals("")) {
                    //finish();
                    prefEditor.putString("temp_type_sms", "1");
                    prefEditor.commit();
                    Intent intent = new Intent(DisplayStudentEditPreActivity.this, Activity_Location_Details.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }


            }
        });
        takeChangeCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preferences.getString("user_id", "").equals("")) {
                    // finish();
                    prefEditor.putString("temp_type_sms", "1");
                    prefEditor.commit();
                    Intent intent = new Intent(DisplayStudentEditPreActivity.this, TabsFragmentActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }


            }
        });


        takeChangedayprefrence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preferences.getString("user_id", "").equals("")) {
                    // finish();
                    prefEditor.putString("temp_type_sms", "1");
                    prefEditor.commit();
                    Intent intent = new Intent(DisplayStudentEditPreActivity.this, Activity_DayPrefrence_Display.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }


            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextViewStudent.setText("");
                clear.setVisibility(View.GONE);
                //  add_student.setVisibility(View.GONE);
                mcontactList.setVisibility(View.VISIBLE);
                takeCreateComment.setVisibility(View.GONE);
                takeEditUser.setVisibility(View.GONE);
                takeDeleteUser.setVisibility(View.GONE);
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

        takeTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!preferences.getString("user_id", "").equals("")) {
                    // finish();
                    prefEditor.putString("temp_type_sms", "1");
                    prefEditor.commit();
                    Intent intent = new Intent(DisplayStudentEditPreActivity.this, TemplateDisplayActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }
            }
        });

        new initBatchSpinner().execute();
        RegistrationView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                //Toast.makeText(getApplicationContext(), messageText, Toast.LENGTH_LONG).show();
                autoCompleteTextViewStudent.setText(messageText);
                getchannelPartnerSelect(messageText);

            }
        });

        ContactListAdpter.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                user_id = messageText;
                //Toast.makeText(getApplicationContext(), messageText, Toast.LENGTH_LONG).show();
                mleadList.setVisibility(View.VISIBLE);
                cmleadList.setVisibility(View.VISIBLE);
                mcontactList.setVisibility(View.GONE);
                new getLocationList().execute();

            }
        });

        userPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPreferences.setBackgroundColor(getResources().getColor(R.color.color_sbutton));
                userbatches.setBackgroundColor(getResources().getColor(R.color.color_ubutton));
                preBatchStudentsDetailsList.setVisibility(View.GONE);
                new initBatchSpinner().execute();
            }
        });

        userbatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userbatches.setBackgroundColor(getResources().getColor(R.color.color_sbutton));
                userPreferences.setBackgroundColor(getResources().getColor(R.color.color_ubutton));
                if (centerResponse.compareTo("") != 0) {
                    data.clear(); // this list which you hava passed in Adapter for your listview
                    centerListAdpter.notifyDataSetChanged(); // notify to listview for refresh
                }
                if (courseListResponse.compareTo("") != 0) {
                    cdata.clear(); // this list which you hava passed in Adapter for your listview
                    coursesListAdpter.notifyDataSetChanged(); // notify to listview for refresh
                }
                preBatchStudentsDetailsList.setVisibility(View.VISIBLE);
                new initBatchDetails().execute();

            }
        });

        calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);

                callIntent.setData(Uri.parse("tel:" + preferences.getString("student_mob_sms", "")));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);


            }
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageManager packageManager = getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);
                try {
                    String url = "https://api.whatsapp.com/send?phone=" + "91" + preferences.getString("student_mob_sms", "") + "&text=" + URLEncoder.encode("", "UTF-8");

                    if (preferences.getString("trainer_user_id", "").equals("AT")) {
                        i.setPackage("com.whatsapp.w4b");
                    } else {
                        i.setPackage("com.whatsapp");
                    }
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        getUserContinueDiscontinueCount();
    }

    //
    public void getUserContinueDiscontinueCount() {


        jsonObj1 = new JSONObject() {
            {
                try {
                    put("user_id", user_id);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread objectThread = new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();
                getContinueDiscontinueCountusersResponse = serviceAccess.SendHttpPost(Config.URL_GETCONDISCOUNTUSERS, jsonObj1);
                Log.i("getRoleusersResponse", getContinueDiscontinueCountusersResponse);
                final Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() { // This thread runs in the UI
                            @Override
                            public void run() {
                                if (getContinueDiscontinueCountusersResponse.compareTo("") == 0) {

                                } else {

                                    try {
                                        JSONObject jObject = new JSONObject(getContinueDiscontinueCountusersResponse);
                                        status = jObject.getBoolean("status");
                                        userbatches.setText("History" + jObject.getString("count"));

                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                };

                new Thread(runnable).start();
            }
        });
        objectThread.start();
    }

    //
    private class initBatchSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DisplayStudentEditPreActivity.this);
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

            jsonLeadObj1 = new JSONObject() {
                {
                    try {
                        //  put("user_id", "" + preferences.getInt("user_id", 0));
                        put("branch_id", flag);
                        //  put("batch_type", "1");

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj1);
            coursesResponse = serviceAccess.SendHttpPost(Config.URL_DISPLAY_GETALLBATCHESBYID, jsonLeadObj1);
            Log.i("resp", "leadListResponse" + coursesResponse);

            if (coursesResponse.compareTo("") != 0) {
                if (isJSONValid(coursesResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                batcheslist = new ArrayList<>();
                                JSONArray LeadSourceJsonObj = new JSONArray(coursesResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    batcheslist.add(new BatchesDAO(json_data.getString("id"), json_data.getString("course_id"), json_data.getString("branch_id"), json_data.getString("batch_code"), json_data.getString("start_date"), json_data.getString("timings"), json_data.getString("Notes"), json_data.getString("frequency"), json_data.getString("fees"), json_data.getString("duration"), json_data.getString("course_name"), json_data.getString("branch_name"), json_data.getString("batchtype")));
                                }

                                jsonArray = new JSONArray(centerResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
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
            if (coursesResponse.compareTo("") != 0) {
                Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBatch);
                ArrayAdapter<BatchesDAO> adapter = new ArrayAdapter<BatchesDAO>(DisplayStudentEditPreActivity.this, android.R.layout.simple_spinner_dropdown_item, batcheslist) {
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View v = super.getView(position, convertView, parent);

                        ((TextView) v).setTextSize(16);
                        ((TextView) v).setTextColor(
                                getResources().getColorStateList(R.color.white)
                        );

                        return v;
                    }

                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View v = super.getDropDownView(position, convertView, parent);
                        //v.setBackgroundResource(R.drawable.spinner_bg);
                        BatchesDAO item = batcheslist.get(position);
                        String test = item.getBatchtype();
                        Log.d("test ", test);
                        if (test.equals("1")) {
                            ((TextView) v).setTextColor(getResources().getColorStateList(R.color.confirm_color));
                        } else {
                            ((TextView) v).setTextColor(getResources().getColorStateList(R.color.white));
                        }

                        //((TextView) v).setTypeface(fontStyle);
                        ((TextView) v).setGravity(Gravity.CENTER);

                        return v;
                    }
                };
                //  CustomSpinnerAdapter adapter=new CustomSpinnerAdapter(StudentsListActivity.this,locationlist);
                spinnerCustom.setAdapter(adapter);
                spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        BatchesDAO LeadSource = (BatchesDAO) parent.getSelectedItem();
                        course_id = LeadSource.getCourse_id();
                        batch_id = LeadSource.getId();
                        prefEditor.putString("batch_id", batch_id);
                        prefEditor.commit();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                mProgressDialog.dismiss();

                //String lastcall = LastCall();
                //  Log.d("Last call", lastcall);
                // new getContactList().execute();

                mleadList.setVisibility(View.VISIBLE);
                cmleadList.setVisibility(View.VISIBLE);
                mcontactList.setVisibility(View.GONE);
                new getLocationList().execute();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
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
            if (i < 20) {
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
            mProgressDialog = new ProgressDialog(DisplayStudentEditPreActivity.this);
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
                contactListAdpter = new ContactListAdpter(DisplayStudentEditPreActivity.this, contactListDAOArrayList);
                mcontactList.setAdapter(contactListAdpter);
                mcontactList.setLayoutManager(new LinearLayoutManager(DisplayStudentEditPreActivity.this));
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
                studentResponse = serviceAccess.SendHttpPost(Config.URL_GETALLSTUDENTSBYID, jsonSchedule);
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

                    }
                });


            }
        });

        objectThread.start();

    }

    //


    //
    private class getLocationList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DisplayStudentEditPreActivity.this);
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

            jsonCenterObj = new JSONObject() {
                {
                    try {
                        put("user_id", user_id);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonCenterObj);
            centerResponse = serviceAccess.SendHttpPost(Config.URL_GETALLLOCATIONSBYUSERID, jsonCenterObj);
            Log.i("resp", "centerResponse" + centerResponse);
            // leadListDAO = new RawLeadListDAO();
            data = new ArrayList<>();
            if (centerResponse.compareTo("") != 0) {
                if (isJSONValid(centerResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseStCenterList(centerResponse);
                                jsonArray = new JSONArray(centerResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
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
            if (centerResponse.compareTo("") != 0) {

                centerListAdpter = new StCenterListAdpter(DisplayStudentEditPreActivity.this, data);
                mleadList.setAdapter(centerListAdpter);
                mleadList.setLayoutManager(new LinearLayoutManager(DisplayStudentEditPreActivity.this));
                mleadList.setHasFixedSize(true);
                setUpItemTouchHelper();
                setUpAnimationDecoratorHelper();
                // Close the progressdialog
                mProgressDialog.dismiss();
                new getCoursesList().execute();
            } else {
                Toast.makeText(getApplicationContext(), "No Location were found", Toast.LENGTH_LONG).show();

                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }

    //
    private class getCoursesList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DisplayStudentEditPreActivity.this);
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
                        put("user_id", user_id);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            courseListResponse = serviceAccess.SendHttpPost(Config.URL_GETALLCOURSESBYUSERID, jsonLeadObj);
            Log.i("resp", "leadListResponse" + courseListResponse);
            // leadListDAO = new RawLeadListDAO();
            cdata = new ArrayList<>();
            if (courseListResponse.compareTo("") != 0) {
                if (isJSONValid(courseListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                JsonHelper jsonHelper = new JsonHelper();
                                cdata = jsonHelper.parseStCoursesList(courseListResponse);
                                jsonArray = new JSONArray(courseListResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
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
            if (courseListResponse.compareTo("") != 0) {

                coursesListAdpter = new StCoursesListAdpter(DisplayStudentEditPreActivity.this, cdata);
                cmleadList.setAdapter(coursesListAdpter);
                cmleadList.setLayoutManager(new LinearLayoutManager(DisplayStudentEditPreActivity.this));
                cmleadList.setHasFixedSize(true);
                setUpItemTouchHelperCourse();
                setUpAnimationDecoratorHelperCourse();
                // Close the progressdialog
                mProgressDialog.dismiss();
                new getStudentCommentDetailsList().execute();
            } else {

                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }


    private class deleteUser extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DisplayStudentEditPreActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Deleting User...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("user_id", preferences.getString("user_id", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj);
            userDeleteResponse = serviceAccess.SendHttpPost(Config.URL_STUDENT_DELETE, jsonLeadObj);
            Log.i("resp", "userDeleteResponse" + userDeleteResponse);
            if (userDeleteResponse.compareTo("") != 0) {
                if (isJSONValid(userDeleteResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(userDeleteResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(userDeleteResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                // removeAt(ID);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                autoCompleteTextViewStudent.setText("");
                mProgressDialog.dismiss();
            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

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


    private void setUpItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.parseColor("#E6E6DC"));
                xMark = ContextCompat.getDrawable(DisplayStudentEditPreActivity.this, R.drawable.ic_delete_black_24dp);
                xMark.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) DisplayStudentEditPreActivity.this.getResources().getDimension(R.dimen.ic_clear_margin);
                initiated = true;
            }

            // not important, we don't want drag & drop
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                StCenterListAdpter testAdapter = (StCenterListAdpter) recyclerView.getAdapter();
                if (testAdapter.isUndoOn() && testAdapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                StCenterListAdpter adapter = (StCenterListAdpter) mleadList.getAdapter();
                boolean undoOn = adapter.isUndoOn();
                if (undoOn) {
                    adapter.pendingRemoval(swipedPosition);
                } else {
                    adapter.remove(swipedPosition);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(mleadList);
    }

    private void setUpAnimationDecoratorHelper() {
        mleadList.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
    }

    private void setUpItemTouchHelperCourse() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.parseColor("#E6E6DC"));
                xMark = ContextCompat.getDrawable(DisplayStudentEditPreActivity.this, R.drawable.ic_delete_black_24dp);
                xMark.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) DisplayStudentEditPreActivity.this.getResources().getDimension(R.dimen.ic_clear_margin);
                initiated = true;
            }

            // not important, we don't want drag & drop
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                StCoursesListAdpter testAdapter = (StCoursesListAdpter) recyclerView.getAdapter();
                if (testAdapter.isUndoOn() && testAdapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                StCoursesListAdpter adapter = (StCoursesListAdpter) cmleadList.getAdapter();
                boolean undoOn = adapter.isUndoOn();
                if (undoOn) {
                    adapter.pendingRemoval(swipedPosition);
                } else {
                    adapter.remove(swipedPosition);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(cmleadList);
    }

    private void setUpAnimationDecoratorHelperCourse() {
        cmleadList.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
    }

    private class getStudentCommentDetailsList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DisplayStudentEditPreActivity.this);
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
                        put("user_id", preferences.getString("user_id", ""));


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            Log.i("json", "json" + jsonLeadObj);
            studentcommentListResponse = serviceAccess.SendHttpPost(Config.URL_GETUSERCOMMENTDETAILS, jsonLeadObj);
            Log.i("resp", "studentfeesListResponse" + studentcommentListResponse);
            if (studentcommentListResponse.compareTo("") != 0) {
                if (isJSONValid(studentcommentListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                JSONObject jsonObject = new JSONObject(studentcommentListResponse);
                                message = jsonObject.getString("Notes");
                                jsonArray = new JSONArray(studentcommentListResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
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
            if (studentcommentListResponse.compareTo("") != 0) {
                //  Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                if (message.equals("null") || message.equals("")) {
                    commentEdtTxt.setVisibility(View.GONE);
                    mProgressDialog.dismiss();
                } else {

                    commentEdtTxt.setVisibility(View.VISIBLE);
                    commentEdtTxt.setText(message);
                    mProgressDialog.dismiss();
                }
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }

    //

    //
    private class initBatchDetails extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DisplayStudentEditPreActivity.this);
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

            jsonCenterObj = new JSONObject() {
                {
                    try {
                        put("user_id", user_id);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonCenterObj);
            preBatchDetailsResponse = serviceAccess.SendHttpPost(Config.URL_GETPRESTUDENTSDETAILS, jsonCenterObj);
            Log.i("resp", "preBatchDetailsResponse" + preBatchDetailsResponse);
            // leadListDAO = new RawLeadListDAO();
            pre_batch_data = new ArrayList<>();
            if (preBatchDetailsResponse.compareTo("") != 0) {
                if (isJSONValid(preBatchDetailsResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                JsonHelper jsonHelper = new JsonHelper();
                                pre_batch_data = jsonHelper.parseBatchStudentList(preBatchDetailsResponse);
                                jsonArray = new JSONArray(preBatchDetailsResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
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
            if (preBatchDetailsResponse.compareTo("") != 0) {

                preBatchDetailsListAdpter = new PreBatchDetailsListAdpter(DisplayStudentEditPreActivity.this, pre_batch_data);
                preBatchStudentsDetailsList.setAdapter(preBatchDetailsListAdpter);
                preBatchStudentsDetailsList.setLayoutManager(new LinearLayoutManager(DisplayStudentEditPreActivity.this));
                // Close the progressdialog
                mProgressDialog.dismiss();

            } else {
                Toast.makeText(getApplicationContext(), "No Location were found", Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        this.finish();
        if (preferences.getString("flag_edit_pre", "").equals("1")) {
            this.finish();
        }
        if (preferences.getString("flag_edit_pre", "").equals("2")) {
            Intent setIntent = new Intent(DisplayStudentEditPreActivity.this, SearchStudentEditPreActivity.class);
            startActivity(setIntent);
            finish();
        }
        if (preferences.getString("flag_edit_pre", "").equals("3")) {
            Intent setIntent = new Intent(DisplayStudentEditPreActivity.this, ShowDemandActivity.class);
            startActivity(setIntent);
            finish();
        }
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
