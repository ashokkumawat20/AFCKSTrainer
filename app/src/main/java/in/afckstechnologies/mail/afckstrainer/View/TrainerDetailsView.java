package in.afckstechnologies.mail.afckstrainer.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import in.afckstechnologies.mail.afckstrainer.Activity.RequestChangeActivity;
import in.afckstechnologies.mail.afckstrainer.Activity.StudentList;
import in.afckstechnologies.mail.afckstrainer.Adapter.StudentFeesDetailsListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.StudentListAdpter;
import in.afckstechnologies.mail.afckstrainer.Adapter.TrainersDetailsListAdpter;
import in.afckstechnologies.mail.afckstrainer.JsonUtils.JsonHelper;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsFeesDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.TrainersDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;


public class TrainerDetailsView extends DialogFragment {


    Context context;
    SharedPreferences preferences;
    Editor prefEditor;
    String trainerListResponse = "";
    JSONObject jsonObj;
    Boolean status;
    int count = 0;
    View registerView;
    private JSONObject jsonLeadObj;
    ProgressDialog mProgressDialog;
    JSONArray jsonArray;
    TrainersDetailsListAdpter trainersDetailsListAdpter;
    private RecyclerView mstudentList;
    List<TrainersDAO> data;
    TrainersDAO trainersDAO;
    ImageView closeFeesDetails;
    LinearLayout feesDetails;
    TextView assignAny;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        registerView = inflater.inflate(R.layout.dialog_trainer_details, null);

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
        mstudentList = (RecyclerView) registerView.findViewById(R.id.feesdetailsList);
        closeFeesDetails = (ImageView) registerView.findViewById(R.id.closeFeesDetails);
        feesDetails = (LinearLayout) registerView.findViewById(R.id.feesDetails);
        assignAny = (TextView) registerView.findViewById(R.id.assignAny);

        new getTrainerDetailsList().execute();
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);

        closeFeesDetails.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        getDialog().setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //Hide your keyboard here!!!
                    //Toast.makeText(getActivity(), "PLease enter your information to get us connected with you.", Toast.LENGTH_LONG).show();
                    return true; // pretend we've processed it
                } else
                    return false; // pass on to be processed as normal
            }
        });

        assignAny.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Random rand = new Random();
                int n = rand.nextInt(data.size());
                trainersDAO = data.get(n);
              //  Toast.makeText(getActivity(), trainersDAO.getFirst_name(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, RequestChangeActivity.class);
                intent.putExtra("user_name", trainersDAO.getFirst_name());
                intent.putExtra("user_id", trainersDAO.getId());
                intent.putExtra("share_data", "");
                startActivity(intent);
            }
        });
        return registerView;
    }


    //
    private class getTrainerDetailsList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
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
                        put("depart_id", preferences.getString("depart_id", ""));


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            Log.i("json", "json" + jsonLeadObj);
            trainerListResponse = serviceAccess.SendHttpPost(Config.URL_GETALLEMPLOYEEBYID, jsonLeadObj);
            Log.i("resp", "trainerListResponse" + trainerListResponse);
            if (trainerListResponse.compareTo("") != 0) {
                if (isJSONValid(trainerListResponse)) {

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseTrainerDetailsList(trainerListResponse);
                                jsonArray = new JSONArray(trainerListResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Please check your webservice", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (data.size() > 1) {
                trainersDetailsListAdpter = new TrainersDetailsListAdpter(getActivity(), data);
                mstudentList.setAdapter(trainersDetailsListAdpter);
                mstudentList.setLayoutManager(new LinearLayoutManager(getActivity()));
                trainersDetailsListAdpter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            }
            if (data.size() == 1) {
                //Toast.makeText(getActivity(), "one record", Toast.LENGTH_LONG).show();
                String user_name = "", user_id = "";
                for (TrainersDAO workout : data) {
                    user_name = workout.getFirst_name();
                    user_id = workout.getId();

                }
                Intent intent = new Intent(getActivity(), RequestChangeActivity.class);
                intent.putExtra("user_name", user_name);
                intent.putExtra("user_id", user_id);
                intent.putExtra("share_data", "");
                startActivity(intent);
                dismiss();
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }

    private void update() {
        Intent intent = new Intent(context, StudentList.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //This is the filter
                    if (event.getAction() != KeyEvent.ACTION_DOWN) {
                        //Toast.makeText(getActivity(), "Your information is valuable for us and won't be misused.", Toast.LENGTH_SHORT).show();
                        count++;
                        if (count >= 1) {
                            // update();
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