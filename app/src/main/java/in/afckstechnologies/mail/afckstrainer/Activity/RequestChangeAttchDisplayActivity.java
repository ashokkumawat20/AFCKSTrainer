package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Adapter.RequestChangeAttachListAdpter;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Models.RequestChangeAttachListDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;

public class RequestChangeAttchDisplayActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj, jsonLeadObj1;
    JSONArray jsonArray;
    List<RequestChangeAttachListDAO> data;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String displayRequestChangeAttachResponse = "";
    RequestChangeAttachListAdpter requestChangeAttachListAdpter;
    private RecyclerView mrequestchangeattachList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_change_attch_display);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        mrequestchangeattachList = (RecyclerView) findViewById(R.id.requestAttchChangeList);
        new getRequestChangeAttachList().execute();
    }
    //
    private class getRequestChangeAttachList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(RequestChangeAttchDisplayActivity.this);
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
                        put("user_id", preferences.getString("assign_to_user_id", ""));
                        put("reqest_change_id", preferences.getString("reqest_change_id", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            displayRequestChangeAttachResponse = serviceAccess.SendHttpPost(Config.URL_GETALLREQUESTCHANGEATTACHBYUSERID, jsonLeadObj);
            Log.i("resp", "displayRequestChangeResponse" + displayRequestChangeAttachResponse);
            if (displayRequestChangeAttachResponse.compareTo("") != 0) {
                if (isJSONValid(displayRequestChangeAttachResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseRequestChangeAttachList(displayRequestChangeAttachResponse);
                                jsonArray = new JSONArray(displayRequestChangeAttachResponse);

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
            if (data.size()>0) {
                requestChangeAttachListAdpter = new RequestChangeAttachListAdpter(RequestChangeAttchDisplayActivity.this, data);
                mrequestchangeattachList.setAdapter(requestChangeAttachListAdpter);
                mrequestchangeattachList.setLayoutManager(new LinearLayoutManager(RequestChangeAttchDisplayActivity.this));
                requestChangeAttachListAdpter.notifyDataSetChanged();
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

