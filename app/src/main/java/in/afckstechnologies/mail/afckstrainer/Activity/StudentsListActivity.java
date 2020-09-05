package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Adapter.AdminStudentListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.StudentListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.ViewAccessoriesListAdpter;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.Models.AdminStudentsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.BatchesDAO;
import in.afckstechnologies.mail.afckstrainer.Models.LocationDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.ViewAccessoriesListDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.View.BatchModifyView;
import in.afckstechnologies.mail.afckstrainer.View.IdentificationEntryView;
import in.afckstechnologies.mail.afckstrainer.View.MultipleCommentAddView;

public class StudentsListActivity extends AppCompatActivity {
    String centerResponse = "";
    String coursesResponse = "";
    String studentListResponse = "";
    ProgressDialog mProgressDialog;
    ArrayList<LocationDAO> locationlist;
    ArrayList<BatchesDAO> batcheslist;
    private JSONObject jsonLeadObj, jsonLeadObj1;
    JSONArray jsonArray;
    List<AdminStudentsDAO> data;
    AdminStudentListAdpter studentListAdpter;
    private RecyclerView mstudentList;
    String flag = "0";
    String course_id = "";
    String branch_id = "";
    ImageView msg_img, mail_img;
    ArrayList<String> studentMailIdArrayList;
    ArrayList<String> studentMobileNoArrayList;
    ArrayList<String> nameArrayList;
    String start_date = "";
    String course_name = "";
    String notes = "";
    String timings = "";
    String frequency = "";
    String duration = "";
    String branch_name = "";
    String fees = "";
    ArrayList<String> strings;

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    //sendind data to next activity
    Button sendData;
    public EditText search;
    ImageView serach_hide, clear, edit_batch;
    String batch_id = "";
    Button showItem, hideItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        search = (EditText) findViewById(R.id.search);
        serach_hide = (ImageView) findViewById(R.id.serach_hide);
        clear = (ImageView) findViewById(R.id.clear);
        edit_batch = (ImageView) findViewById(R.id.edit_batch);
        showItem = (Button) findViewById(R.id.showItem);
        hideItem = (Button) findViewById(R.id.hideItem);

        prefEditor.putString("mail_a_flag", "send_details");
        prefEditor.commit();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        sendData = (Button) findViewById(R.id.sendData);
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String data1 = "";
                String data2 = "";
                String data3 = "";
                studentMobileNoArrayList = new ArrayList<>();
                nameArrayList = new ArrayList<>();
                studentMailIdArrayList = new ArrayList<>();
                List<AdminStudentsDAO> stList = ((AdminStudentListAdpter) studentListAdpter).getSservicelist();

                for (int i = 0; i < stList.size(); i++) {
                    AdminStudentsDAO serviceListDAO = stList.get(i);
                    if (serviceListDAO.isSelected() == true) {
                        data1 = serviceListDAO.getMobile_no().toString();
                        data2 = serviceListDAO.getFirst_name().toString();
                        data3 = serviceListDAO.getEmail_id().toString();
                        studentMobileNoArrayList.add(data1);
                        nameArrayList.add(data2);
                        studentMailIdArrayList.add(data3);
                    } else {
                        System.out.println("not selected");
                    }
                }

                if (studentMobileNoArrayList.size() > 0) {
                    Intent intent = new Intent(StudentsListActivity.this, WriteSendSmsActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) studentMobileNoArrayList);
                    args.putSerializable("ARRAYLIST1", (Serializable) nameArrayList);
                    args.putSerializable("ARRAYLIST2", (Serializable) studentMailIdArrayList);
                    intent.putExtra("BUNDLE", args);
                    intent.putExtra("start_date", start_date);
                    intent.putExtra("course_name", course_name);
                    intent.putExtra("notes", notes);
                    intent.putExtra("timings", timings);
                    intent.putExtra("frequency", frequency);
                    intent.putExtra("duration", duration);
                    intent.putExtra("branch_name", branch_name);
                    intent.putExtra("fees", fees);
                    startActivity(intent);
                    //finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }


            }
        });

        //
        edit_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!batch_id.equals("")) {
                    prefEditor.putString("batch_mofiy", batch_id);
                    prefEditor.commit();
                    BatchModifyView batchModifyView = new BatchModifyView();
                    batchModifyView.show(getSupportFragmentManager(), "batchModifyView");
                } else {
                    Toast.makeText(getApplication(), "Please select batch ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mstudentList = (RecyclerView) findViewById(R.id.studentsList);
        mail_img = (ImageView) findViewById(R.id.mail_img);
        msg_img = (ImageView) findViewById(R.id.msg_img);


        // initBranchSpinner();
        new initBranchSpinner().execute();

        /** Getting reference to checkbox available in the main.xml layout */
        CheckBox chkAll = (CheckBox) findViewById(R.id.chkAllSelected);
        /** Setting a click listener for the checkbox **/
        chkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox cb = (CheckBox) v;
                //Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                if (cb.isChecked()) {
                    List<AdminStudentsDAO> list = ((AdminStudentListAdpter) studentListAdpter).getSservicelist();
                    for (AdminStudentsDAO workout : list) {
                        workout.setSelected(true);

                    }

                    ((AdminStudentListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
                } else {
                    List<AdminStudentsDAO> list = ((AdminStudentListAdpter) studentListAdpter).getSservicelist();
                    for (AdminStudentsDAO workout : list) {
                        workout.setSelected(false);

                    }

                    ((AdminStudentListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
                }
            }
        });

        serach_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                clear.setVisibility(View.VISIBLE);
                serach_hide.setVisibility(View.GONE);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setText("");
                serach_hide.setVisibility(View.VISIBLE);
                clear.setVisibility(View.GONE);

            }
        });
        addTextListener();
        DisplayStudentEditPreActivity.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                new getStudentList().execute();
            }
        });

        AdminStudentListAdpter.bindListener(new FeesListener() {
            @Override
            public void messageReceived(String messageText) {
                new getStudentList().execute();
            }
        });
        MultipleCommentAddView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                // Toast.makeText(getApplicationContext(), messageText, Toast.LENGTH_SHORT).show();
                List<AdminStudentsDAO> list = ((AdminStudentListAdpter) studentListAdpter).getSservicelist();
                for (AdminStudentsDAO workout : list) {
                    if (preferences.getString("user_id", "").equals(workout.getUser_id())) {
                        workout.setNotes(messageText);
                    }
                }
                ((AdminStudentListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
            }
        });
        showItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefEditor.putString("showFlag", "1");
                prefEditor.commit();
                List<AdminStudentsDAO> list = ((AdminStudentListAdpter) studentListAdpter).getSservicelist();
                ((AdminStudentListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
                showItem.setVisibility(View.GONE);
                hideItem.setVisibility(View.VISIBLE);

            }
        });
        hideItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefEditor.putString("showFlag", "0");
                prefEditor.commit();
                List<AdminStudentsDAO> list = ((AdminStudentListAdpter) studentListAdpter).getSservicelist();
                ((AdminStudentListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
                hideItem.setVisibility(View.GONE);
                showItem.setVisibility(View.VISIBLE);
            }
        });

    }

    //
    public void addTextListener() {

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();
                final List<AdminStudentsDAO> filteredList = new ArrayList<AdminStudentsDAO>();
                if (data != null) {
                    if (data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {

                            String identification1 = data.get(i).getStudent_Name().toLowerCase();
                            String identification2 = data.get(i).getMobile_no().toLowerCase();


                            if (identification1.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (identification2.contains(query)) {

                                filteredList.add(data.get(i));
                            }

                        }
                    }
                }

                mstudentList.setLayoutManager(new LinearLayoutManager(StudentsListActivity.this));
                studentListAdpter = new AdminStudentListAdpter(StudentsListActivity.this, filteredList);
                mstudentList.setAdapter(studentListAdpter);
                studentListAdpter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    //
    //
    private class initBranchSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(StudentsListActivity.this);
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
                        //  put("user_id", "" + preferences.getInt("user_id", 0));
                        // put("branch_id", flag);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj);
            centerResponse = serviceAccess.SendHttpPost(Config.URL_DISPLAY_CENTER, jsonLeadObj);
            Log.i("resp", "leadListResponse" + centerResponse);

            if (centerResponse.compareTo("") != 0) {
                if (isJSONValid(centerResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                locationlist = new ArrayList<>();
                                locationlist.add(new LocationDAO("0", "All"));

                                strings = new ArrayList<String>();
                                strings.add("All");
                                JSONArray LeadSourceJsonObj = new JSONArray(centerResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    locationlist.add(new LocationDAO(json_data.getString("id"), json_data.getString("branch_name")));
                                    strings.add(json_data.getString("branch_name"));
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
            if (centerResponse.compareTo("") != 0) {

                Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                ArrayAdapter<LocationDAO> adapter = new ArrayAdapter<LocationDAO>(StudentsListActivity.this, android.R.layout.simple_spinner_dropdown_item, locationlist);
                //MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                spinnerCustom.setAdapter(adapter);
                spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));


                        LocationDAO LeadSource = (LocationDAO) parent.getSelectedItem();
                        //  Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getBranch_name(), Toast.LENGTH_SHORT).show();
                        flag = LeadSource.getId();
                        new initBatchSpinner().execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }

    //
    private class initBatchSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(StudentsListActivity.this);
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
                        put("batch_type", "1");

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj1);
            coursesResponse = serviceAccess.SendHttpPost(Config.URL_DISPLAY_COURSES, jsonLeadObj1);
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
                ArrayAdapter<BatchesDAO> adapter = new ArrayAdapter<BatchesDAO>(StudentsListActivity.this, android.R.layout.simple_spinner_dropdown_item, batcheslist);
                //  CustomSpinnerAdapter adapter=new CustomSpinnerAdapter(StudentsListActivity.this,locationlist);
                spinnerCustom.setAdapter(adapter);
                spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        BatchesDAO LeadSource = (BatchesDAO) parent.getSelectedItem();
                        String date_after = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", LeadSource.getStart_date());
                        //  Toast.makeText(getApplicationContext(), "Source ID: " +date_after+"   "+ LeadSource.getId() + ",  Source Name : " + LeadSource.getBatch_code(), Toast.LENGTH_SHORT).show();
                        start_date = date_after;
                        course_name = LeadSource.getCourse_name();
                        notes = LeadSource.getNotes();
                        timings = LeadSource.getTimings();
                        duration = LeadSource.getDuration();
                        fees = LeadSource.getFees();
                        frequency = LeadSource.getFrequency();
                        branch_name = LeadSource.getBranch_name();

                        course_id = LeadSource.getCourse_id();
                        branch_id = LeadSource.getBranch_id();
                        batch_id = LeadSource.getId();
                        prefEditor.putString("curr_batch_id", LeadSource.getId());
                        prefEditor.putString("template_start_date_copy", start_date);
                        prefEditor.putString("template_course_name_copy", course_name);
                        prefEditor.putString("template_notes_copy", notes);
                        prefEditor.putString("template_timings_copy", timings);
                        prefEditor.putString("template_duration_copy", duration);
                        prefEditor.putString("template_fees_copy", fees);
                        prefEditor.putString("template_frequency_copy", frequency);
                        prefEditor.putString("template_branch_name_copy", branch_name);
                        prefEditor.commit();
                        new getStudentList().execute();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
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

    //
    private class getStudentList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(StudentsListActivity.this);
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
                        put("course_id", course_id);
                        put("branch_id", branch_id);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            studentListResponse = serviceAccess.SendHttpPost(Config.URL_GETALLSTUDENTSBYID, jsonLeadObj);
            Log.i("resp", "batchesListResponse" + studentListResponse);
            if (studentListResponse.compareTo("") != 0) {
                if (isJSONValid(studentListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseAdminStudentList(studentListResponse);
                                jsonArray = new JSONArray(studentListResponse);

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
            if (studentListResponse.compareTo("") != 0) {
                studentListAdpter = new AdminStudentListAdpter(StudentsListActivity.this, data);
                mstudentList.setAdapter(studentListAdpter);
                mstudentList.setLayoutManager(new LinearLayoutManager(StudentsListActivity.this));
                mstudentList.setHasFixedSize(true);
                setUpItemTouchHelperCourse();
                setUpAnimationDecoratorHelperCourse();
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
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


    //sms api


    public static void sendSMS(String reciever, String message) {
        URLConnection myURLConnection = null;
        URL myURL = null;
        BufferedReader reader = null;
        try {
            //prepare connection
            myURL = new URL(buildRequestString(reciever, message));
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader = new BufferedReader(new
                    InputStreamReader(myURLConnection.getInputStream()));

            //reading response
            String response;
            while ((response = reader.readLine()) != null)
                //print response
                Log.d("RESPONSE", "" + response);
            //finally close connection
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                xMark = ContextCompat.getDrawable(StudentsListActivity.this, R.drawable.ic_delete_black_24dp);
                xMark.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) StudentsListActivity.this.getResources().getDimension(R.dimen.ic_clear_margin);
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
                AdminStudentListAdpter testAdapter = (AdminStudentListAdpter) recyclerView.getAdapter();
                if (testAdapter.isUndoOn() && testAdapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                AdminStudentListAdpter adapter = (AdminStudentListAdpter) mstudentList.getAdapter();
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
        mItemTouchHelper.attachToRecyclerView(mstudentList);
    }

    private void setUpAnimationDecoratorHelperCourse() {
        mstudentList.addItemDecoration(new RecyclerView.ItemDecoration() {

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

    private static String buildRequestString(String reciever, String message) {
        //encoding message
        String encoded_message = URLEncoder.encode(message);
        /*ring data = "user=" + URLEncoder.encode("AfcksTechnologies", "UTF-8");
        data += "&password=" + URLEncoder.encode("skzq5sjd", "UTF-8");
        data += "&message=" + URLEncoder.encode("hi hello", "UTF-8");
        data += "&sender=" + URLEncoder.encode("AFCKST", "UTF-8");
        data += "&mobile=" + URLEncoder.encode(phonenumbers, "UTF-8");
        data += "&type=" + URLEncoder.encode("3", "UTF-8");
        http://login.bulksmsgateway.in/sendmessage.php?*/
        //Send SMS API
        //String mainUrl="http://123.63.33.43/blank/sms/user/urlsms.php?username=marketinapp@gmail.com&pass=wavarmark@1234&senderid=WAVARM&dest_mobileno="+reciever+"&message="+encoded_message+"&response=Y";
        String mainUrl = "http://login.bulksmsgateway.in/sendmessage.php?user=AfcksTechnologies&password=skzq5sjd&sender=AFCKST&mobile=" + reciever + "&message=" + encoded_message + "&type=3";
        //String mainUrl="http://login.bulksmsgateway.in/sendmessage.php?user=AfcksTechnologies&password=skzq5sjd&sender=AFCKST&mobile=%22+9657816221+%22&message=%22+helo+%22&type=3";

        return mainUrl;


    }


}
