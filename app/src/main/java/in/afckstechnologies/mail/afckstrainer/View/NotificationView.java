package in.afckstechnologies.mail.afckstrainer.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.afckstechnologies.mail.afckstrainer.Activity.BuzzHistoryViewActivity;
import in.afckstechnologies.mail.afckstrainer.Activity.RequestChangeDisplayActivity;
import in.afckstechnologies.mail.afckstrainer.Activity.RequestPendingDisplayActivity;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.AppStatus;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.Utils.Constant;


/**
 * Created by Ashok on 4/29/2017.
 */

public class NotificationView extends DialogFragment {


    TextView invites, buzz, messagenotification, completedtask, taskdonefeedback, pendingtickets, pendingTask, buzzCount, leaveTask, authorisationTask;
    LinearLayout myPendingTickets, buzzNotification, myPendingLeaves, myAuthorisation;
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    JSONObject jsonObj, jsonObj1, jsonLeadObj;
    Boolean status;
    String message;
    String user_id;
    boolean click = true;
    int count = 0;
    View registerView;
    String pendingTaskResponse = "", buzzCountResponse = "", leavependingCountResponse = "", leavePendingCount = "", authorisationCountResponse = "";
    JSONObject jsonObject;
    String msg = "", task_count = "", buzz_count = "", authorisation_count = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        registerView = inflater.inflate(R.layout.dialog_notification, null);
        context = getActivity();
        Window window = getDialog().getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.CENTER | Gravity.CENTER);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();

        params.y = 50;
        window.setAttributes(params);
        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();

        invites = (TextView) registerView.findViewById(R.id.invites);
        buzz = (TextView) registerView.findViewById(R.id.buzz);
        messagenotification = (TextView) registerView.findViewById(R.id.messagenotification);

        completedtask = (TextView) registerView.findViewById(R.id.completedtask);
        taskdonefeedback = (TextView) registerView.findViewById(R.id.taskdonefeedback);
        pendingtickets = (TextView) registerView.findViewById(R.id.pendingtickets);
        pendingtickets = (TextView) registerView.findViewById(R.id.pendingtickets);
        pendingTask = (TextView) registerView.findViewById(R.id.pendingTask);
        buzzCount = (TextView) registerView.findViewById(R.id.buzzCount);
        leaveTask = (TextView) registerView.findViewById(R.id.leaveTask);
        authorisationTask = (TextView) registerView.findViewById(R.id.authorisationTask);
        myPendingTickets = (LinearLayout) registerView.findViewById(R.id.myPendingTickets);
        buzzNotification = (LinearLayout) registerView.findViewById(R.id.buzzNotification);
        myPendingLeaves = (LinearLayout) registerView.findViewById(R.id.myPendingLeaves);
        myAuthorisation = (LinearLayout) registerView.findViewById(R.id.myAuthorisation);
        if (preferences.getString("trainer_user_id", "").equals("RS")) {
            myPendingLeaves.setVisibility(View.VISIBLE);
            myAuthorisation.setVisibility(View.VISIBLE);
        }
        if (preferences.getString("trainer_user_id", "").equals("AK")) {
            myPendingLeaves.setVisibility(View.VISIBLE);
            myAuthorisation.setVisibility(View.VISIBLE);
        }
        new taskAvailable().execute();
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //Hide your keyboard here!!!
                    Toast.makeText(getActivity(), "PLease enter your information to get us connected with you.", Toast.LENGTH_LONG).show();
                    return true; // pretend we've processed it
                } else
                    return false; // pass on to be processed as normal
            }
        });

        buzzNotification.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                if (AppStatus.getInstance(context).isOnline()) {
                    Intent intent = new Intent(getActivity(), BuzzHistoryViewActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }


            }
        });
        myPendingTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppStatus.getInstance(getActivity()).isOnline()) {
                    prefEditor.putString("flag", "1");
                    prefEditor.commit();
                    Intent intent = new Intent(getActivity(), RequestPendingDisplayActivity.class);
                    startActivity(intent);
                    dismiss();
                } else {

                    Toast.makeText(getActivity(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }


            }
        });

        myAuthorisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getActivity()).isOnline()) {
                    Intent intent = new Intent(getActivity(), RequestChangeDisplayActivity.class);
                    startActivity(intent);
                    dismiss();
                } else {

                    Toast.makeText(getActivity(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return registerView;
    }


    @Override
    public void onResume() {
        super.onResume();

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //This is the filter
                    if (event.getAction() != KeyEvent.ACTION_DOWN) {
                        // Toast.makeText(getActivity(), "Your information is valuable for us and won't be misused.", Toast.LENGTH_SHORT).show();
                        count++;
                        if (count >= 1) {
                            dismiss();
                        }
                        return true;
                    } else {
                        //Hide your keyboard here!!!!!!
                        return true; // pretend we've processed it
                    }
                } else
                    return false; // pass on to be processed as normal
            }
        });
    }

    private class taskAvailable extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //  mProgressDialog = new ProgressDialog(TrainerVerfiyActivity.this);
            // Set progressdialog title
            // mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            // mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //   mProgressDialog.show();
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
            jsonObj = new JSONObject() {
                {
                    try {
                        put("user_id", preferences.getString("trainer_user_id", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            jsonObj1 = new JSONObject() {
                {
                    try {
                        put("user_id", preferences.getString("trainer_user_id", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonObj);
            pendingTaskResponse = serviceAccess.SendHttpPost(Config.URL_CHECKEMPLOYEEPENDINGTASK, jsonObj);
            Log.i("resp", "pendingTaskResponse" + pendingTaskResponse);

            Log.i("json", "json" + jsonLeadObj);
            leavependingCountResponse = serviceAccess.SendHttpPost(Config.URL_GETCOUNTPENDINGLEAVEUSERS, jsonLeadObj);
            Log.i("resp", "leavependingCountResponse" + leavependingCountResponse);

            Log.i("json", "json" + jsonObj);
            buzzCountResponse = serviceAccess.SendHttpPost(Config.URL_CHECKBUZZCOUNT, jsonObj1);
            Log.i("resp", "buzzCountResponse" + buzzCountResponse);

            Log.i("json", "json" + jsonObj);
            authorisationCountResponse = serviceAccess.SendHttpPost(Config.URL_AUTHORISATIONCOUNT, jsonObj1);
            Log.i("resp", "authorisationCountResponse" + authorisationCountResponse);

            if (pendingTaskResponse.compareTo("") != 0) {
                if (isJSONValid(pendingTaskResponse)) {


                    try {

                        jsonObject = new JSONObject(pendingTaskResponse);
                        msg = jsonObject.getString("message");
                        status = jsonObject.getBoolean("status");
                        task_count = jsonObject.getString("id");

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }

                if (isJSONValid(leavependingCountResponse)) {
                    try {


                        JSONObject jsonObject = new JSONObject(leavependingCountResponse);
                        leavePendingCount = jsonObject.getString("totalleavepending");


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }
                if (isJSONValid(buzzCountResponse)) {


                    try {

                        jsonObject = new JSONObject(buzzCountResponse);

                        buzz_count = jsonObject.getString("buzz_count");


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }

                if (isJSONValid(authorisationCountResponse)) {


                    try {

                        jsonObject = new JSONObject(authorisationCountResponse);

                        authorisation_count = jsonObject.getString("authorisation_count");


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(getActivity(), "Please check your webservice", Toast.LENGTH_LONG).show();
                }
            } else {

                Toast.makeText(getActivity(), "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {


            if (status) {
                pendingTask.setText(task_count);
                buzzCount.setText(buzz_count);
                authorisationTask.setText(authorisation_count);
            } else {
                pendingTask.setText("0");
                buzzCount.setText(buzz_count);
                authorisationTask.setText(authorisation_count);
            }
            leaveTask.setText(leavePendingCount);

            // Close the progressdialog
            //  mProgressDialog.dismiss();

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