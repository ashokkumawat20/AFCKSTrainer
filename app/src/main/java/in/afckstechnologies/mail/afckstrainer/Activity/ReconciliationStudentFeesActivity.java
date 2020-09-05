package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Adapter.StudentReconcililationListAdpter;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Models.FeeModeDAO;
import in.afckstechnologies.mail.afckstrainer.Models.ReconciliationDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;

public class ReconciliationStudentFeesActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    ArrayList<FeeModeDAO> leadCatList;
    String feeMode_value="";
    String studentListResponse = "";
    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    List<ReconciliationDAO> data;
    JSONArray jsonArray;
    StudentReconcililationListAdpter studentReconcililationListAdpter;
    private RecyclerView mstudentList;
    TextView amount,bank,narration;
    String unique_id="";
    String user_id="";
    String fees_id="";
    String batch_id="";
    boolean status;
    String message = "",reconListResponse="";
    String msg = "";

    //sendind data to next activity
    Button sendData;
    ArrayList<String> studentFeesIdArrayList;
    ArrayList<String> studentUserIdArrayList;
    ArrayList<String> studentBatchIdArrayList;
    ArrayList<String> studentFeesArrayList;
    String fees="";
    int temp=0,temp_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconciliation_student_fees);
        preferences = getSharedPreferences("Prefrence", MODE_PRIVATE);
        prefEditor = preferences.edit();
        mstudentList = (RecyclerView) findViewById(R.id.studentsList);
        amount=(TextView)findViewById(R.id.amount);
        bank=(TextView)findViewById(R.id.bank);
        narration=(TextView)findViewById(R.id.narration);
        amount.setText(preferences.getString("amount",""));
        unique_id=preferences.getString("unique_id","");
        bank.setText(preferences.getString("bank_name",""));
        narration.setText(preferences.getString("narration",""));
        initCategorySpinner();
        /** Getting reference to checkbox available in the main.xml layout */
        CheckBox chkAll = (CheckBox) findViewById(R.id.chkAllSelected);
        /** Setting a click listener for the checkbox **/
        chkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox cb = (CheckBox) v;
                //Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                if (cb.isChecked()) {
                    List<ReconciliationDAO> list = ((StudentReconcililationListAdpter) studentReconcililationListAdpter).getSservicelist();
                    for (ReconciliationDAO workout : list) {
                        workout.setSelected(true);

                    }

                    ((StudentReconcililationListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
                } else {
                    List<ReconciliationDAO> list = ((StudentReconcililationListAdpter) studentReconcililationListAdpter).getSservicelist();
                    for (ReconciliationDAO workout : list) {
                        workout.setSelected(false);

                    }

                    ((StudentReconcililationListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
                }
            }
        });

        sendData = (Button) findViewById(R.id.sendData);
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String data1 = "";
                String data2 = "";
                String data3 = "";
                String data4 = "";
                studentFeesIdArrayList = new ArrayList<>();
                studentUserIdArrayList = new ArrayList<>();
                studentBatchIdArrayList = new ArrayList<>();
                studentFeesArrayList = new ArrayList<>();
                List<ReconciliationDAO> stList = ((StudentReconcililationListAdpter) studentReconcililationListAdpter).getSservicelist();

                for (int i = 0; i < stList.size(); i++) {
                    ReconciliationDAO serviceListDAO = stList.get(i);
                    if (serviceListDAO.isSelected() == true) {
                        data1 = serviceListDAO.getUser_id().toString();
                        data2 = serviceListDAO.getBatchNo().toString();
                        data3 = serviceListDAO.getFees().toString();
                        data4 = serviceListDAO.getId().toString();
                        studentUserIdArrayList.add(data1);
                        studentBatchIdArrayList.add(data2);
                        studentFeesArrayList.add(data3);
                        studentFeesIdArrayList.add(data4);

                    } else {
                        System.out.println("not selected");
                    }
                }

                if (studentFeesArrayList.size() > 0) {
                    int totalfees=0;
                    for (int i = 0; i < studentFeesArrayList.size(); i++) {
                        totalfees+=Integer.parseInt(studentFeesArrayList.get(i));

                    }

                    fees=""+totalfees;
                    temp_size=studentFeesArrayList.size();
                    if(preferences.getString("amount","").equals(fees)) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ReconciliationStudentFeesActivity.this);
                        builder.setMessage("Do you want to update fees details ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        try {
                                            dialog.cancel();
                                            for (int i = 0; i < studentFeesArrayList.size(); i++) {
                                                temp++;
                                                user_id=studentUserIdArrayList.get(i);
                                                fees_id=studentFeesIdArrayList.get(i);
                                                batch_id=studentBatchIdArrayList.get(i);
                                                try {
                                                    new  submitReconData().execute();
                                                    Thread.sleep(5000);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                // Toast.makeText(getApplicationContext(), "totalfees " + fees, Toast.LENGTH_LONG).show();
                                            }


                                        } catch (Exception ex) {
                                            Toast.makeText(ReconciliationStudentFeesActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
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
                        alert.setTitle("Updating Fees Entry");
                        alert.show();




                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "does not match total fees", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private void initCategorySpinner() {


        leadCatList = new ArrayList<>();
        leadCatList.add(new FeeModeDAO("0", "All"));
        leadCatList.add(new FeeModeDAO("1", "EFT"));
        leadCatList.add(new FeeModeDAO("2", "Cheque"));
        leadCatList.add(new FeeModeDAO("3", "Paytm AFCKS"));

        Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerFeeMode);
        ArrayAdapter<FeeModeDAO> adapter = new ArrayAdapter<FeeModeDAO>(ReconciliationStudentFeesActivity.this, android.R.layout.simple_spinner_dropdown_item, leadCatList);
        spinnerCustom.setAdapter(adapter);
        spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                FeeModeDAO LeadAssignedTo = (FeeModeDAO) parent.getSelectedItem();
                feeMode_value = LeadAssignedTo.getName();
                //  Toast.makeText(getApplicationContext(), "Assigned To ID: " + LeadAssignedTo.getId() + ",  Assigned To Name : " + LeadAssignedTo.getName(), Toast.LENGTH_SHORT).show();
                new getStudentList().execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }


        });


    }

    //
    private class getStudentList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ReconciliationStudentFeesActivity.this);
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
                        put("FeeMode", feeMode_value);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            studentListResponse = serviceAccess.SendHttpPost(Config.URL_GETALLSTUDENTFORFEERECONCILILATION, jsonLeadObj);
            Log.i("resp", "batchesListResponse" + studentListResponse);
            if (studentListResponse.compareTo("") != 0) {
                if (isJSONValid(studentListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseReconciliationStudentList(studentListResponse);
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
                studentReconcililationListAdpter = new StudentReconcililationListAdpter(ReconciliationStudentFeesActivity.this, data);
                mstudentList.setAdapter(studentReconcililationListAdpter);
                mstudentList.setLayoutManager(new LinearLayoutManager(ReconciliationStudentFeesActivity.this));
                studentReconcililationListAdpter.notifyDataSetChanged();
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

    private class submitReconData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //   mProgressDialog = new ProgressDialog(StudentList.this);
            // Set progressdialog title
            //  mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            //  mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            final String date = format.format(cal.getTime());
            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("batch_id",batch_id);
                        put("user_id",user_id);
                        put("fees_id", fees_id);
                        put("unique_id", unique_id);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            reconListResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEFEESRECONSTUDENT, jsonLeadObj);
            Log.i("resp", "reconListResponse" + reconListResponse);


            if (reconListResponse.compareTo("") != 0) {
                if (isJSONValid(reconListResponse)) {


                    try {

                        JSONObject jsonObject = new JSONObject(reconListResponse);
                        status = jsonObject.getBoolean("status");
                        msg = jsonObject.getString("message");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {


                    // Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();


                }
            } else {

                //  Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                if(temp==temp_size) {
                    finish();
                    Intent intent = new Intent(ReconciliationStudentFeesActivity.this, ReconActivity.class);
                    startActivity(intent);
                }
                // Close the progressdialog
                // mProgressDialog.dismiss();


            } else {
                // Close the progressdialog
                // mProgressDialog.dismiss();

            }
        }
    }
}

