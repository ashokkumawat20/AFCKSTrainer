package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import in.afckstechnologies.mail.afckstrainer.Adapter.BuzzListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.RequestChangeCreateListAdpter;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Models.BuzzViewDAO;
import in.afckstechnologies.mail.afckstrainer.Models.RequestChangeListDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;

public class BuzzHistoryViewActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj, jsonLeadObj1;
    JSONArray jsonArray;
    List<BuzzViewDAO> data;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String displayRequestChangeResponse = "";
    BuzzListAdpter buzzListAdpter;
    private RecyclerView mrequestbuzzList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzz_history_view);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        mrequestbuzzList = (RecyclerView) findViewById(R.id.mrequestbuzzList);
        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            new getRequestChangeList().execute();
        } else {

            Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
        }
    }

    //
    private class getRequestChangeList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(BuzzHistoryViewActivity.this);
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

                        put("assign_to_user_id", preferences.getString("trainer_user_id", ""));

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            displayRequestChangeResponse = serviceAccess.SendHttpPost(Config.URL_GETALLBUZZBYUSERID, jsonLeadObj);
            Log.i("resp", "displayRequestChangeResponse" + displayRequestChangeResponse);
            if (displayRequestChangeResponse.compareTo("") != 0) {
                if (isJSONValid(displayRequestChangeResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseRequestBuzzList(displayRequestChangeResponse);
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
                buzzListAdpter = new BuzzListAdpter(BuzzHistoryViewActivity.this, data);
                mrequestbuzzList.setAdapter(buzzListAdpter);
                mrequestbuzzList.setLayoutManager(new LinearLayoutManager(BuzzHistoryViewActivity.this));
                buzzListAdpter.notifyDataSetChanged();

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
