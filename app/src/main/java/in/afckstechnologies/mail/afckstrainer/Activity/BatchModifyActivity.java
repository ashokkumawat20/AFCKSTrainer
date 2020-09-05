package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.afckstechnologies.mail.afckstrainer.Models.BatchDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;

public class BatchModifyActivity extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextViewBatch;
    ImageView clear_batch;
    String newTextBatch;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;



    String studentResponse = "";
    public List<BatchDAO> batchArrayList;
    public ArrayAdapter<BatchDAO> aAdapter;

    public BatchDAO batchDAO;
    String batch_id = "";
    private JSONObject jsonSchedule;
    private JSONObject jsonLeadObj, jsonLeadObj1;
    ProgressDialog mProgressDialog;

    JSONArray jsonArray;



    boolean status;
    String message = "";
    String msg = "";



    String start_date = "",end_date = "0000-00-00";
    String notes = "";
    String timings = "";
    String frequency = "";
    String duration = "";
    String fees = "";

    RelativeLayout footer;
    LinearLayout batchData;
    String updateBatchResponse="";
    private EditText dpicker,timingsEdtTxt,frequencyEdtTxt,feesEdtTxt,durationEdtTxt,commentEdtTxt,datePickerEndBatch;
    String date="";
    Button sendData;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_modify);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        autoCompleteTextViewBatch = (AutoCompleteTextView) findViewById(R.id.SearchBatch);
        footer=(RelativeLayout)findViewById(R.id.footer);
        batchData=(LinearLayout)findViewById(R.id.batchData);
        clear_batch = (ImageView) findViewById(R.id.clear_batch);
        sendData=(Button)findViewById(R.id.sendData);
        batchArrayList = new ArrayList<BatchDAO>();

        dpicker = (EditText)findViewById(R.id.datePickerCal);
        datePickerEndBatch = (EditText)findViewById(R.id.datePickerEndBatch);
        timingsEdtTxt = (EditText)findViewById(R.id.timingsEdtTxt);
        frequencyEdtTxt = (EditText)findViewById(R.id.frequencyEdtTxt);
        feesEdtTxt = (EditText)findViewById(R.id.feesEdtTxt);
        durationEdtTxt = (EditText)findViewById(R.id.durationEdtTxt);
        commentEdtTxt = (EditText)findViewById(R.id.commentEdtTxt);

        dpicker.setOnClickListener(new View.OnClickListener() {
            private int mYear, mMonth, mDay;
            private SimpleDateFormat dateFormatter;

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                DatePickerDialog mDatePicker = new DatePickerDialog(BatchModifyActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        mcurrentDate.set(selectedyear, selectedmonth, selectedday);
                        dpicker.setText(dateFormatter.format(mcurrentDate.getTime()));
                        date = format.format(mcurrentDate.getTime());
                        start_date=date;
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        datePickerEndBatch.setOnClickListener(new View.OnClickListener() {
            private int mYear, mMonth, mDay;
            private SimpleDateFormat dateFormatter;

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                DatePickerDialog mDatePicker = new DatePickerDialog(BatchModifyActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        mcurrentDate.set(selectedyear, selectedmonth, selectedday);
                        datePickerEndBatch.setText(dateFormatter.format(mcurrentDate.getTime()));
                        date = format.format(mcurrentDate.getTime());
                        end_date=date;
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
        autoCompleteTextViewBatch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newTextBatch = s.toString();
                clear_batch.setVisibility(View.VISIBLE);
                getBatchSelect(newTextBatch);


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        clear_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextViewBatch.setText("");
                datePickerEndBatch.setText("");
                clear_batch.setVisibility(View.GONE);
                footer.setVisibility(View.GONE);
                batchData.setVisibility(View.GONE);


            }
        });

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    timings=timingsEdtTxt.getText().toString().trim();
                    frequency=frequencyEdtTxt.getText().toString().trim();
                    fees= feesEdtTxt.getText().toString().trim();
                    duration=durationEdtTxt.getText().toString().trim();
                    notes=commentEdtTxt.getText().toString().trim();
                    if (validate(start_date, timings, frequency,fees,duration)) {
                        new initBatchUpdate().execute();

                    }

                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getBatchSelect(final String channelPartnerSelect) {

        jsonSchedule = new JSONObject() {
            {
                try {
                    put("Prefixtext", channelPartnerSelect);
                    put("user_id", preferences.getString("trainer_user_id", ""));
                    //put("user_id", "RS");

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
                studentResponse = serviceAccess.SendHttpPost(Config.URL_GETALLBATCHMODIFYBYPREFIX, jsonSchedule);
                Log.i("resp", "loginResponse" + studentResponse);


                try {
                    JSONArray callArrayList = new JSONArray(studentResponse);
                    batchArrayList.clear();
                    // user_id="";
                    for (int i = 0; i < callArrayList.length(); i++) {
                        batchDAO = new BatchDAO();
                        JSONObject json_data = callArrayList.getJSONObject(i);
                        batchArrayList.add(new BatchDAO(json_data.getString("id"), json_data.getString("course_id"), json_data.getString("branch_id"), json_data.getString("batch_code"), json_data.getString("start_date"), json_data.getString("timings"), json_data.getString("Notes"), json_data.getString("frequency"), json_data.getString("fees"), json_data.getString("duration"), json_data.getString("course_name"), json_data.getString("branch_name"), json_data.getString("batchtype"), json_data.getString("completion_status"), json_data.getString("batch_end_date"), json_data.getString("email_id"), json_data.getString("mobile_no"),json_data.getString("faculty_Name")));

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        aAdapter = new ArrayAdapter<BatchDAO>(getApplicationContext(), R.layout.item, batchArrayList);
                        autoCompleteTextViewBatch.setAdapter(aAdapter);

                        autoCompleteTextViewBatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                                // String s = parent.getItemAtPosition(i).toString();
                                BatchDAO student = (BatchDAO) parent.getAdapter().getItem(i);
                                footer.setVisibility(View.VISIBLE);
                                batchData.setVisibility(View.VISIBLE);
                                start_date=student.getStart_date();
                                dpicker.setText(formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", student.getStart_date()));
                                timingsEdtTxt.setText(student.getTimings());
                                frequencyEdtTxt.setText(student.getFrequency());
                                feesEdtTxt.setText(student.getFees());
                                durationEdtTxt.setText(student.getDuration());
                                commentEdtTxt.setText(student.getNotes());
                                if(!student.getBtach_end_date().equals("null") ||!student.getBtach_end_date().equals("0000-00-00")) {
                                    end_date=student.getBtach_end_date();
                                    datePickerEndBatch.setText(formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", student.getBtach_end_date()));
                                }
                                batch_id = student.getId();
                                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
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


    private class initBatchUpdate extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(BatchModifyActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Updating Batch...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("id", batch_id);
                        put("start_date", start_date);
                        put("timings", timings);
                        put("frequency", frequency);
                        put("fees", fees);
                        put("duration", duration);
                        put("notes", notes);
                        put("end_date", end_date);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj);
            updateBatchResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEBATCHDETAILS, jsonLeadObj);
            Log.i("resp", "addTemplateResponse" + updateBatchResponse);

            if (updateBatchResponse.compareTo("") != 0) {
                if (isJSONValid(updateBatchResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                JSONObject jsonObject = new JSONObject(updateBatchResponse);
                                msg = jsonObject.getString("message");
                                status = jsonObject.getBoolean("status");

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
            if (updateBatchResponse.compareTo("") != 0) {

                if (status) {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                    // finish();
                    mProgressDialog.dismiss();
                } else {

                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
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

    public boolean validate(String start_date, String timings, String frequency, String fees,String duration) {

        boolean isValidate = false;
        if (start_date.equals("")) {
            Toast.makeText(getApplicationContext(), "Please select Batch Starting Date.", Toast.LENGTH_LONG).show();
        } else if (timings.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter batch timings.", Toast.LENGTH_LONG).show();
        } else if (frequency.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter  Frequency", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (fees.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter  Fees", Toast.LENGTH_LONG).show();
            isValidate = false;

        }
        else if (duration.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter  Duration", Toast.LENGTH_LONG).show();
            isValidate = false;

        }else {
            isValidate = true;
        }
        return isValidate;
    }
}

