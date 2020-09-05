package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.afckstechnologies.mail.afckstrainer.Adapter.StudentFeedBackListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.StudentListAdpter;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.Models.BatchDAO;

import in.afckstechnologies.mail.afckstrainer.Models.FeedbackBatchDAO;
import in.afckstechnologies.mail.afckstrainer.Models.FeedbackDateDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsFeedbackListDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;


public class StudentFeedbackActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    AutoCompleteTextView autoCompleteTextViewBatch;
    String studentBatchResponse = "";
    String studentDateResponse = "";
    public List<FeedbackBatchDAO> batchArrayList;
    public List<FeedbackDateDAO> dateArrayList;
    public ArrayAdapter<BatchDAO> aAdapter;
    public ArrayAdapter<StudentsFeedbackListDAO> studentListDAOArrayAdapter;
    public FeedbackBatchDAO batchDAO;
    public FeedbackDateDAO dateDAO;
    String batch_id = "";
    private JSONObject jsonSchedule;
    private JSONObject jsonLeadObj, jsonLeadObj1;
    ProgressDialog mProgressDialog;
    String studentListResponse = "";
    JSONArray jsonArray;
    List<StudentsFeedbackListDAO> data;
    StudentFeedBackListAdpter studentListAdpter;
    private RecyclerView mstudentList;
    private FloatingActionButton fab;
    String newTextBatch, newTextStudent;
    AutoCompleteTextView autoCompleteTextViewStudent;
    ImageView clear_batch, clearDate;
    public List<StudentsFeedbackListDAO> studentArrayList;
    String student_id = "";
    public StudentsFeedbackListDAO studentListDAO;
    String addStudentRespone = "";
    boolean status;
    String message = "";
    String msg = "";


    RelativeLayout footer;


    String flag = "";

    private EditText dpicker;
    String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_feedback);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        mstudentList = (RecyclerView) findViewById(R.id.studentsList);
        studentArrayList = new ArrayList<StudentsFeedbackListDAO>();
        batchArrayList = new ArrayList<FeedbackBatchDAO>();
        dateArrayList = new ArrayList<FeedbackDateDAO>();
        autoCompleteTextViewStudent = (AutoCompleteTextView) findViewById(R.id.SearchStudent);
        footer = (RelativeLayout) findViewById(R.id.footer);
        autoCompleteTextViewBatch = (AutoCompleteTextView) findViewById(R.id.SearchBatch);
        dpicker = (EditText) findViewById(R.id.datePickerCal);
        getBatchSelect();
    }


    public void getBatchSelect() {

        jsonSchedule = new JSONObject() {
            {
                try {
                    put("faculty_id", preferences.getString("trainer_user_id", ""));
                    //put("faculty_id", "RC");

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
                studentBatchResponse = serviceAccess.SendHttpPost(Config.URL_GETALLBACTHCODEOFFEEDBACK, jsonSchedule);
                Log.i("resp", "loginResponse" + studentBatchResponse);


                try {
                    JSONArray callArrayList = new JSONArray(studentBatchResponse);
                    batchArrayList.clear();
                    // user_id="";

                    batchArrayList.add(new FeedbackBatchDAO("All", ""));
                    for (int i = 0; i < callArrayList.length(); i++) {
                        batchDAO = new FeedbackBatchDAO();
                        JSONObject json_data = callArrayList.getJSONObject(i);
                        batchArrayList.add(new FeedbackBatchDAO(json_data.getString("BatchID"), json_data.getString("faculty_id")));
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {


                        Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                        ArrayAdapter<FeedbackBatchDAO> adapter = new ArrayAdapter<FeedbackBatchDAO>(StudentFeedbackActivity.this, android.R.layout.simple_spinner_dropdown_item, batchArrayList);
                        // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                        spinnerCustom.setAdapter(adapter);
                        spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                                FeedbackBatchDAO feedbackBatchDAO = (FeedbackBatchDAO) parent.getSelectedItem();
                                //  Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getBranch_name(), Toast.LENGTH_SHORT).show();
                                batch_id = feedbackBatchDAO.getBatchID();
                                getDateSelect();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }


                        });
                    }
                });


            }
        });

        objectThread.start();

    }

    public void getDateSelect() {

        jsonSchedule = new JSONObject() {
            {
                try {
                    put("BatchID", batch_id);
                    //put("faculty_id", "RC");

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
                studentDateResponse = serviceAccess.SendHttpPost(Config.URL_GETALLFEEDBACKDATEOFFEEDBACK, jsonSchedule);
                Log.i("resp", "loginResponse" + studentDateResponse);


                try {
                    JSONArray callArrayList = new JSONArray(studentDateResponse);
                    dateArrayList.clear();
                    // user_id="";

                    dateArrayList.add(new FeedbackDateDAO("", "0", "All"));
                    for (int i = 0; i < callArrayList.length(); i++) {

                        JSONObject json_data = callArrayList.getJSONObject(i);
                        String date_after = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", json_data.getString("feedback_date"));
                        dateArrayList.add(new FeedbackDateDAO(json_data.getString("BatchID"), json_data.getString("feedback_date"), date_after));
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {


                        Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerdatePicker);
                        ArrayAdapter<FeedbackDateDAO> adapter = new ArrayAdapter<FeedbackDateDAO>(StudentFeedbackActivity.this, android.R.layout.simple_spinner_dropdown_item, dateArrayList);
                        spinnerCustom.setAdapter(adapter);
                        spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                                FeedbackDateDAO feedbackBatchDAO = (FeedbackDateDAO) parent.getSelectedItem();
                                //  Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getBranch_name(), Toast.LENGTH_SHORT).show();
                                //   flag = LeadSource.getId();
                                date = feedbackBatchDAO.getFeedback_date();
                                if (date.equals("0")) {
                                    date = "";
                                }
                                new getStudentList().execute();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }


                        });
                    }
                });


            }
        });

        objectThread.start();

    }

    //
    private class getStudentList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(StudentFeedbackActivity.this);
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

            if (batch_id.equals("All")) {
                batch_id = "";
            }
            {

            }

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("batch_date", date);
                        put("batch_id", batch_id);
                        put("user_id", preferences.getString("trainer_user_id", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj);
            studentListResponse = serviceAccess.SendHttpPost(Config.URL_DISPLAY_FEEDBACK_STUDENTS, jsonLeadObj);
            Log.i("resp", "batchesListResponse" + studentListResponse);
            if (studentListResponse.compareTo("") != 0) {
                if (isJSONValid(studentListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseStudentFeedbackDetailsList(studentListResponse);
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
            if (data.size() > 0) {
                studentListAdpter = new StudentFeedBackListAdpter(StudentFeedbackActivity.this, data);
                mstudentList.setAdapter(studentListAdpter);
                mstudentList.setLayoutManager(new LinearLayoutManager(StudentFeedbackActivity.this));
                studentListAdpter.notifyDataSetChanged();
                 mProgressDialog.dismiss();
            } else {
                studentListAdpter = new StudentFeedBackListAdpter(StudentFeedbackActivity.this, data);
                mstudentList.setAdapter(studentListAdpter);
                mstudentList.setLayoutManager(new LinearLayoutManager(StudentFeedbackActivity.this));
                studentListAdpter.notifyDataSetChanged();
                Toast.makeText(StudentFeedbackActivity.this, "Empty Data...", Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
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
