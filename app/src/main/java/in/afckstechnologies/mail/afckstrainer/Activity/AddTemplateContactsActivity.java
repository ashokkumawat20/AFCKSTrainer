package in.afckstechnologies.mail.afckstrainer.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;

public class AddTemplateContactsActivity extends AppCompatActivity {
    String title = "";
    String templateText = "";
    EditText add_title,add_Tag, msgData;
    Button saveTemplate;
    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;

    String addTemplateResponse = "";

    String msg = "",tag="";
    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template_contacts);
        add_title = (EditText) findViewById(R.id.add_Suject);
        add_Tag = (EditText) findViewById(R.id.add_Tag);
        msgData = (EditText) findViewById(R.id.msgData);
        saveTemplate = (Button) findViewById(R.id.saveTemplate);
        saveTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    templateText = msgData.getText().toString().trim();
                    title = add_title.getText().toString().trim();
                    tag = add_Tag.getText().toString().trim();
                    if (!templateText.equals("")) {
                        new initAddTemplate().execute();
                    } else {

                        Toast.makeText(getApplicationContext(), "Please enter value for template message", Toast.LENGTH_SHORT).show();

                    }
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private class initAddTemplate extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(AddTemplateContactsActivity.this);
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
                        put("Subject", title);
                        put("tag", tag);
                        put("Template_Text", templateText);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj);
            addTemplateResponse = serviceAccess.SendHttpPost(Config.URL_ADDTEMPLATESCONTACTS, jsonLeadObj);
            Log.i("resp", "addTemplateResponse" + addTemplateResponse);

            if (addTemplateResponse.compareTo("") != 0) {
                if (isJSONValid(addTemplateResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                JSONObject jsonObject = new JSONObject(addTemplateResponse);
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
            if (addTemplateResponse.compareTo("") != 0) {

                if (status) {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddTemplateContactsActivity.this);
                    alertDialog.setTitle("Add Template");
                    alertDialog.setMessage("Do you want add more Template ");


                    // alertDialog.setIcon(R.drawable.msg_img);
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });

                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(AddTemplateContactsActivity.this, TemplateDisplayActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            });

                    alertDialog.show();
                    mProgressDialog.dismiss();
                } else {

                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent setIntent = new Intent(AddTemplateContactsActivity.this, TemplateDisplayActivity.class);
        startActivity(setIntent);
    }
}
