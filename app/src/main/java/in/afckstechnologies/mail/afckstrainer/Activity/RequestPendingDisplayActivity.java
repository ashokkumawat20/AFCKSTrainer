package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Adapter.RequestChangeAssignListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.RequestChangeCreateListAdpter;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.Models.RequestChangeListDAO;
import in.afckstechnologies.mail.afckstrainer.Models.RequestChangeUsersNameDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;

public class RequestPendingDisplayActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj, jsonLeadObj1;
    JSONArray jsonArray;
    List<RequestChangeListDAO> data;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String displayRequestChangeResponse = "";
    RequestChangeAssignListAdpter requestChangeAssignListAdpter;
    private RecyclerView mrequestchangeList;
    RadioGroup radioGroup;
    int pos;
    int Status_id=1;
    Spinner usersName;
    String usersResponse = "";
    String user_id = "";
    ArrayList<RequestChangeUsersNameDAO> userslist;
    LinearLayout usersLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_pending_display);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        prefEditor.putString("RequestChange","RequestChangePending");
        prefEditor.commit();
        mrequestchangeList = (RecyclerView) findViewById(R.id.requestChangeList);
        usersLayout=(LinearLayout)findViewById(R.id.usersLayout);
        usersName = (Spinner) findViewById(R.id.spinnerUsers);//|| preferences.getString("trainer_user_id", "").equals("AK")
      /* if(preferences.getString("trainer_user_id", "").equals("RS") ) {
            usersLayout.setVisibility(View.VISIBLE);
            new initUsersSpinner().execute();
        }
        else
        {
            user_id=preferences.getString("trainer_user_id", "");
            new getRequestChangeList().execute();
        }*/
        user_id=preferences.getString("trainer_user_id", "");
        new getRequestChangeList().execute();
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                pos = radioGroup.indexOfChild(findViewById(checkedId));
                switch (pos) {
                    case 1:

                        Status_id = 3;
                        new getRequestChangeList().execute();
                        // Toast.makeText(getApplicationContext(), "1"+Status_id,Toast.LENGTH_SHORT).show();
                        break;


                    default:
                        //The default selection is RadioButton 1
                        Status_id = 1;
                        new getRequestChangeList().execute();
                        //   Toast.makeText(getApplicationContext(), "3"+Status_id,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        RequestChangeAssignListAdpter.bindListener(new FeesListener() {
            @Override
            public void messageReceived(String messageText) {
                new getRequestChangeList().execute();
            }
        });

    }
    //
    private class initUsersSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //   mProgressDialog = new ProgressDialog(FixedAssetsActivity.this);
            // Set progressdialog title
            //   mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            // mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //  mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                      put("user_id",preferences.getString("trainer_user_id", ""));

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj);
            usersResponse = serviceAccess.SendHttpPost(Config.URL_GETALLREQUESTPENDDINGTICKETUSERNAME, jsonLeadObj);
            Log.i("resp", "leadListResponse" + usersResponse);

            if (usersResponse.compareTo("") != 0) {
                if (isJSONValid(usersResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                userslist = new ArrayList<>();
                                userslist.add(new RequestChangeUsersNameDAO("0", "Select User","",""));
                                JSONArray LeadSourceJsonObj = new JSONArray(usersResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    userslist.add(new RequestChangeUsersNameDAO(json_data.getString("id"), json_data.getString("CName"),"",""));
                                }

                                jsonArray = new JSONArray(usersResponse);

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
            if (usersResponse.compareTo("") != 0) {

                // Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                ArrayAdapter<RequestChangeUsersNameDAO> adapter = new ArrayAdapter<RequestChangeUsersNameDAO>(RequestPendingDisplayActivity.this, android.R.layout.simple_spinner_dropdown_item, userslist);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                usersName.setAdapter(adapter);
                usersName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        RequestChangeUsersNameDAO LeadSource = (RequestChangeUsersNameDAO) parent.getSelectedItem();
                        // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getLocation_name(), Toast.LENGTH_SHORT).show();
                        user_id = LeadSource.getId();
                        if (!user_id.equals("0")) {
                            new getRequestChangeList().execute();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                //  mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                // mProgressDialog.dismiss();
            }
        }
    }
    //
    private class getRequestChangeList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(RequestPendingDisplayActivity.this);
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
                        put("status", Status_id);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            displayRequestChangeResponse = serviceAccess.SendHttpPost(Config.URL_GETALLREQUESTPENDDINGBYUSERID, jsonLeadObj);
            Log.i("resp", "displayRequestChangeResponse" + displayRequestChangeResponse);
            if (displayRequestChangeResponse.compareTo("") != 0) {
                if (isJSONValid(displayRequestChangeResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseRequestChangeList(displayRequestChangeResponse);
                                jsonArray = new JSONArray(displayRequestChangeResponse);

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
            if (displayRequestChangeResponse.compareTo("") != 0) {
                requestChangeAssignListAdpter = new RequestChangeAssignListAdpter(RequestPendingDisplayActivity.this, data);
                mrequestchangeList.setAdapter(requestChangeAssignListAdpter);
                mrequestchangeList.setLayoutManager(new LinearLayoutManager(RequestPendingDisplayActivity.this));
                requestChangeAssignListAdpter.notifyDataSetChanged();
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
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();
        Intent setIntent = new Intent(RequestPendingDisplayActivity.this,RequestChangeDisplayActivity.class);
        startActivity(setIntent);
    }
}
