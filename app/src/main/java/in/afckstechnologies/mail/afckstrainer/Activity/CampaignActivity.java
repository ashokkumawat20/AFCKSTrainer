package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Adapter.AdminStudentListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.CampaignStudentListAdpter;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Models.AdminStudentsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.CampaignDAO;
import in.afckstechnologies.mail.afckstrainer.Models.CampaignStudentsDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;

public class CampaignActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;

    private JSONObject jsonLeadObj, jsonLeadObj1;
    JSONArray jsonArray;
    ProgressDialog mProgressDialog;
    String campaignResponse = "", campaign_id = "";
    String studentListResponse = "";
    ArrayList<CampaignDAO> campaignlist;
    List<CampaignStudentsDAO> data;
    CampaignStudentListAdpter campaignStudentListAdpter;
    private RecyclerView mstudentList;

    public EditText search;
    ImageView serach_hide, clear, edit_batch;
    Button showItem, hideItem;
    Button sendData;


    ArrayList<String> studentMailIdArrayList;
    ArrayList<String> studentMobileNoArrayList;
    ArrayList<String> nameArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        mstudentList = (RecyclerView) findViewById(R.id.studentsList);
        search = (EditText) findViewById(R.id.search);
        serach_hide = (ImageView) findViewById(R.id.serach_hide);
        clear = (ImageView) findViewById(R.id.clear);
        showItem = (Button) findViewById(R.id.showItem);
        hideItem = (Button) findViewById(R.id.hideItem);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

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
        new initCampaignSpinner().execute();

        /** Getting reference to checkbox available in the main.xml layout */
        CheckBox chkAll = (CheckBox) findViewById(R.id.chkAllSelected);
        /** Setting a click listener for the checkbox **/
        chkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox cb = (CheckBox) v;
                //Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                if (cb.isChecked()) {
                    List<CampaignStudentsDAO> list = ((CampaignStudentListAdpter) campaignStudentListAdpter).getSservicelist();
                    for (CampaignStudentsDAO workout : list) {
                        workout.setSelected(true);

                    }

                    ((CampaignStudentListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
                } else {
                    List<CampaignStudentsDAO> list = ((CampaignStudentListAdpter) campaignStudentListAdpter).getSservicelist();
                    for (CampaignStudentsDAO workout : list) {
                        workout.setSelected(false);

                    }

                    ((CampaignStudentListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
                }
            }
        });
        showItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefEditor.putString("showFlag", "1");
                prefEditor.commit();
                List<CampaignStudentsDAO> list = ((CampaignStudentListAdpter) campaignStudentListAdpter).getSservicelist();
                ((CampaignStudentListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
                showItem.setVisibility(View.GONE);
                hideItem.setVisibility(View.VISIBLE);

            }
        });
        hideItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefEditor.putString("showFlag", "0");
                prefEditor.commit();
                List<CampaignStudentsDAO> list = ((CampaignStudentListAdpter) campaignStudentListAdpter).getSservicelist();
                ((CampaignStudentListAdpter) mstudentList.getAdapter()).notifyDataSetChanged();
                hideItem.setVisibility(View.GONE);
                showItem.setVisibility(View.VISIBLE);
            }
        });

        sendData = (Button) findViewById(R.id.sendData);
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CampaignActivity.this, WriteCampaignActivity.class);
                startActivity(intent);
               /* String data1 = "";
                String data2 = "";
                String data3 = "";
                studentMobileNoArrayList = new ArrayList<>();
                nameArrayList = new ArrayList<>();
                studentMailIdArrayList = new ArrayList<>();
                List<CampaignStudentsDAO> stList = ((CampaignStudentListAdpter) campaignStudentListAdpter).getSservicelist();

                for (int i = 0; i < stList.size(); i++) {
                    CampaignStudentsDAO serviceListDAO = stList.get(i);
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
                    Intent intent = new Intent(CampaignActivity.this, WriteCampaignActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) studentMobileNoArrayList);
                    args.putSerializable("ARRAYLIST1", (Serializable) nameArrayList);
                    args.putSerializable("ARRAYLIST2", (Serializable) studentMailIdArrayList);
                    intent.putExtra("BUNDLE", args);
                   *//* intent.putExtra("start_date", start_date);
                    intent.putExtra("course_name", course_name);
                    intent.putExtra("notes", notes);*//*

                    startActivity(intent);
                    //finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Please select student from list", Toast.LENGTH_LONG).show();
                }*/


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
                final List<CampaignStudentsDAO> filteredList = new ArrayList<CampaignStudentsDAO>();
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

                mstudentList.setLayoutManager(new LinearLayoutManager(CampaignActivity.this));
                campaignStudentListAdpter = new CampaignStudentListAdpter(CampaignActivity.this, filteredList);
                mstudentList.setAdapter(campaignStudentListAdpter);
                campaignStudentListAdpter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    //
    private class initCampaignSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(CampaignActivity.this);
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


            Log.i("json", "json" + jsonLeadObj);
            campaignResponse = serviceAccess.SendHttpPost(Config.URL_GETALL_CAMPAIGN, jsonLeadObj);
            Log.i("resp", "campaignResponse" + campaignResponse);

            if (campaignResponse.compareTo("") != 0) {
                if (isJSONValid(campaignResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                campaignlist = new ArrayList<>();
                                campaignlist.add(new CampaignDAO("0", "Select Campaign", "", "", ""));
                                JSONArray LeadSourceJsonObj = new JSONArray(campaignResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    campaignlist.add(new CampaignDAO(json_data.getString("id"), json_data.getString("campaign_name"), json_data.getString("campaign_description"), json_data.getString("campaign_start_date"), json_data.getString("campaign_end_date")));

                                }

                                jsonArray = new JSONArray(campaignResponse);

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
            if (campaignResponse.compareTo("") != 0) {

                Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerCampaign);
                ArrayAdapter<CampaignDAO> adapter = new ArrayAdapter<CampaignDAO>(CampaignActivity.this, android.R.layout.simple_spinner_dropdown_item, campaignlist);
                //MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                spinnerCustom.setAdapter(adapter);
                spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        CampaignDAO LeadSource = (CampaignDAO) parent.getSelectedItem();
                        // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getCampaign_name(), Toast.LENGTH_SHORT).show();
                        campaign_id = LeadSource.getId();
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
    private class getStudentList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(CampaignActivity.this);
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
                        put("campaign_id", campaign_id);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            studentListResponse = serviceAccess.SendHttpPost(Config.URL_GETALLCAMPAIGNSTUDENTSBYID, jsonLeadObj);
            Log.i("resp", "batchesListResponse" + studentListResponse);
            if (studentListResponse.compareTo("") != 0) {
                if (isJSONValid(studentListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseCampaignStudentList(studentListResponse);
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
                campaignStudentListAdpter = new CampaignStudentListAdpter(CampaignActivity.this, data);
                mstudentList.setAdapter(campaignStudentListAdpter);
                mstudentList.setLayoutManager(new LinearLayoutManager(CampaignActivity.this));
                mstudentList.setHasFixedSize(true);
                // setUpItemTouchHelperCourse();
                // setUpAnimationDecoratorHelperCourse();
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
}
