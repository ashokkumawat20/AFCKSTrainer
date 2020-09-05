package in.afckstechnologies.mail.afckstrainer.Activity;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Adapter.BankDetailsListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.TemplateListAdpter;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;

import in.afckstechnologies.mail.afckstrainer.Models.BankingdetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.TemplatesContactDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;
import in.afckstechnologies.mail.afckstrainer.View.BankingDetailsAddView;


public class BankingDetailsActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    private FloatingActionButton fab;
    private ProgressDialog mProgressDialog, dialog;
    private JSONObject jsonLeadObj;
    private JSONArray jsonArray;
    String bankingListResponse = "";
    List<BankingdetailsDAO> data;
    BankDetailsListAdpter bankDetailsListAdpter;
    RecyclerView bankingReceiptList;
    public EditText search;
    ImageView serach_hide, clear;
    TextView lastUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_banking_details_layout);
        preferences = getSharedPreferences("Prefrence", MODE_PRIVATE);
        prefEditor = preferences.edit();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        search = (EditText) findViewById(R.id.search);
        serach_hide = (ImageView) findViewById(R.id.serach_hide);
        clear = (ImageView) findViewById(R.id.clear);
        lastUpdated = (TextView) findViewById(R.id.lastUpdated);
        bankingReceiptList = (RecyclerView) findViewById(R.id.bankingReceiptList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BankingDetailsAddView bankingDetailsAddView = new BankingDetailsAddView();
                bankingDetailsAddView.show(getSupportFragmentManager(), "bankingDetailsAddView");
            }
        });

        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            new getBankingDetailsList().execute();

        } else {

            Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
        }
        if (!preferences.getString("recipetDateEntry", "").equals("")) {
            lastUpdated.setText(preferences.getString("recipetDateEntry", ""));
        }

        BankingDetailsAddView.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    if (!preferences.getString("recipetDateEntry", "").equals("")) {
                        lastUpdated.setText(preferences.getString("recipetDateEntry", ""));
                    }
                    new getBankingDetailsList().execute();

                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        BankDetailsListAdpter.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    if (!preferences.getString("recipetDateEntry", "").equals("")) {
                        lastUpdated.setText(preferences.getString("recipetDateEntry", ""));
                    }
                    new getBankingDetailsList().execute();

                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
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
    }

    //
    public void addTextListener() {

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {
                clear.setVisibility(View.VISIBLE);
                serach_hide.setVisibility(View.GONE);

                query = query.toString().toLowerCase();
                final List<BankingdetailsDAO> filteredList = new ArrayList<BankingdetailsDAO>();
                if (data != null) {
                    if (data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {

                            String subject = data.get(i).getTrans_date().toLowerCase();
                            String tag = data.get(i).getTrans_ref().toLowerCase();
                            String msg_txt = data.get(i).getTrans_type().toLowerCase();
                            if (subject.contains(query)) {
                                filteredList.add(data.get(i));
                            } else if (tag.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (msg_txt.contains(query)) {

                                filteredList.add(data.get(i));
                            }
                        }
                    }
                }

                bankDetailsListAdpter = new BankDetailsListAdpter(BankingDetailsActivity.this, filteredList);
                bankingReceiptList.setAdapter(bankDetailsListAdpter);
                bankingReceiptList.setLayoutManager(new LinearLayoutManager(BankingDetailsActivity.this));
                bankDetailsListAdpter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    //
    private class getBankingDetailsList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(BankingDetailsActivity.this);
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


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            bankingListResponse = serviceAccess.SendHttpPost(Config.URL_GETALLBANKDETAILS, jsonLeadObj);
            Log.i("resp", "bankingListResponse" + bankingListResponse);
            if (bankingListResponse.compareTo("") != 0) {
                if (isJSONValid(bankingListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseBankingDetailsList(bankingListResponse);
                                jsonArray = new JSONArray(bankingListResponse);

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
            if (bankingListResponse.compareTo("") != 0) {
                bankDetailsListAdpter = new BankDetailsListAdpter(BankingDetailsActivity.this, data);
                bankingReceiptList.setAdapter(bankDetailsListAdpter);
                bankingReceiptList.setLayoutManager(new LinearLayoutManager(BankingDetailsActivity.this));
                bankDetailsListAdpter.notifyDataSetChanged();  // data set changed
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