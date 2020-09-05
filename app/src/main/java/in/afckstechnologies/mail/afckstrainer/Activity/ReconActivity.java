package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.ProgressDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Adapter.BankReconcililationListAdpter;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Models.ReconDAO;
import in.afckstechnologies.mail.afckstrainer.Models.ReconciliationBankDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;

public class ReconActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    String reconResponse="",reconListResponse="";
    ArrayList<ReconDAO> reconDAOArrayList;
    JSONArray jsonArray;
    String recon_id="";
    List<ReconciliationBankDAO> data;
    BankReconcililationListAdpter bankReconcililationListAdpter;
    private RecyclerView mbankList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recon);
        mbankList = (RecyclerView) findViewById(R.id.reconbankList);
        new  initReconBankSpinner().execute();

    }

    //
    private class initReconBankSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ReconActivity.this);
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
            reconResponse = serviceAccess.SendHttpPost(Config.URL_GETALLRECONBANKDETAILS, jsonLeadObj);
            Log.i("resp", "leadListResponse" + reconResponse);

            if (reconResponse.compareTo("") != 0) {
                if (isJSONValid(reconResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                reconDAOArrayList = new ArrayList<>();
                                // reconDAOArrayList.add(new ReconDAO("0", "All"));
                                //  strings.add("All");
                                JSONArray LeadSourceJsonObj = new JSONArray(reconResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    reconDAOArrayList.add(new ReconDAO(json_data.getString("id"), json_data.getString("bank_name")));

                                }

                                jsonArray = new JSONArray(reconResponse);

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
            if (reconResponse.compareTo("") != 0) {


                Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBankMode);
                ArrayAdapter<ReconDAO> adapter = new ArrayAdapter<ReconDAO>(ReconActivity.this, android.R.layout.simple_spinner_dropdown_item, reconDAOArrayList);

                spinnerCustom.setAdapter(adapter);
                spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        ReconDAO LeadSource = (ReconDAO) parent.getSelectedItem();
                        // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getBank_name(), Toast.LENGTH_SHORT).show();
                        recon_id = LeadSource.getBank_name();
                        new getReconList().execute();
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
    private class getReconList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ReconActivity.this);
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
                        put("bank_id", recon_id);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            reconListResponse = serviceAccess.SendHttpPost(Config.URL_GETALLBANKDETAILSRECONCILILATION, jsonLeadObj);
            Log.i("resp", "reconListResponse" + reconListResponse);
            if (reconListResponse.compareTo("") != 0) {
                if (isJSONValid(reconListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseReconciliationBankList(reconListResponse);
                                jsonArray = new JSONArray(reconListResponse);

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
            if (reconListResponse.compareTo("") != 0) {
                bankReconcililationListAdpter = new BankReconcililationListAdpter(ReconActivity.this, data);
                mbankList.setAdapter(bankReconcililationListAdpter);
                mbankList.setLayoutManager(new LinearLayoutManager(ReconActivity.this));
                bankReconcililationListAdpter.notifyDataSetChanged();
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
