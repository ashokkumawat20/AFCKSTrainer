package in.afckstechnologies.mail.afckstrainer.Adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


import in.afckstechnologies.mail.afckstrainer.Models.UserFeedbacksDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
;

/**
 * Created by admin on 3/18/2017.
 */

public class UserFeedbackApprovalListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<UserFeedbacksDAO> data;
    UserFeedbacksDAO current;
    int currentPos = 0;
    String id, id1;
    String centerId;
    int ID;
    int number = 1;

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String sms_type = "";


    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String userFeedbackUpdateResponse = "",userFeedbackApprovalResponse="",userFeedbackRejectResponse="";
    boolean status;
    String message = "";
    String msg = "";
    String user_id = "";
    String feedback_id = "";
    String feedback="";
    public static SmsListener mListener;

    // create constructor to innitilize context and data sent from MainActivity
    public UserFeedbackApprovalListAdpter(Context context, List<UserFeedbacksDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_user_feedback_approval_details, parent, false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;
        // Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
        myHolder.name.setText(current.getStudent_name());
        myHolder.name.setTag(position);
        myHolder.callingButton.setTag(position);
        myHolder.messageButton.setTag(position);
        myHolder.numbers.setTag(position);
        myHolder.whatsappeButton.setTag(position);
        myHolder.numbers.setText(current.getNumbers());
        myHolder.feedbackEdtTxt.setText(current.getFeedback());
        myHolder.feedbackEdtTxt.setTag(position);
        myHolder.approve.setTag(position);
        myHolder.reject.setTag(position);
        myHolder.update.setTag(position);

        myHolder.callingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Log.i("call ph no", "" + current.getMobile_no());

                callIntent.setData(Uri.parse("tel:" + current.getMobile_no()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(callIntent);


            }
        });
        myHolder.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Log.i("call ph no", "" + current.getMobile_no());
                String phoneNumber = "9999999999";
                String smsBody = "";

// Add the phone number in the data
                Uri uri = Uri.parse("smsto:" + current.getMobile_no());
// Create intent with the action and data
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
// smsIntent.setData(uri); // We just set the data in the constructor above
// Set the message
                smsIntent.putExtra("sms_body", smsBody);

                context.startActivity(smsIntent);

            }
        });

        myHolder.whatsappeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Log.i("call ph no", "" + current.getMobile_no());
                String phoneNumber = "7057326842";
                String smsBody = "";

                try {
                    Uri uri = Uri.parse("smsto:" + current.getMobile_no());
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                    sendIntent.setPackage("com.whatsapp");
                    context.startActivity(sendIntent);
                } catch (Exception e) {
                    Toast.makeText(context, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        });

        myHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                feedback_id=current.getId();
                user_id=current.getUser_id();
                feedback= myHolder.feedbackEdtTxt.getText().toString();
                new updateFeedback().execute();



            }
        });

        myHolder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                feedback_id=current.getId();
                user_id=current.getUser_id();


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to Approval Feedback ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {

                                    new approveFeedback().execute();

                                } catch (Exception ex) {
                                    Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
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
                alert.setTitle("Approval Feedback");
                alert.show();
            }
        });
        myHolder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);

                feedback_id=current.getId();
                user_id=current.getUser_id();


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to Reject Feedback ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {

                                    new rejectFeedback().execute();

                                } catch (Exception ex) {
                                    Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show();
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
                alert.setTitle("Reject Feedback");
                alert.show();

            }
        });

    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView name, numbers;
        EditText feedbackEdtTxt;
        Button update, approve, reject;
        ImageView callingButton, messageButton, whatsappeButton, takeBookSeat, app_status, user_status, mobile_status, infoButton, commentButton;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            // mail_id = (TextView) itemView.findViewById(R.id.mail_id);
            callingButton = (ImageView) itemView.findViewById(R.id.callingButton);
            messageButton = (ImageView) itemView.findViewById(R.id.messageButton);
            numbers = (TextView) itemView.findViewById(R.id.numbers);
            whatsappeButton = (ImageView) itemView.findViewById(R.id.whatsappeButton);
            feedbackEdtTxt = (EditText) itemView.findViewById(R.id.feedbackEdtTxt);
            update = (Button) itemView.findViewById(R.id.update);
            approve = (Button) itemView.findViewById(R.id.approve);
            reject = (Button) itemView.findViewById(R.id.reject);
        }

    }

    //
    private class updateFeedback extends AsyncTask<Void, Void, Void> {
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

                        put("user_id", user_id);
                        put("id",feedback_id);
                        put("feedback", feedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            userFeedbackUpdateResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEUSERFEEDBACKS, jsonLeadObj);
            Log.i("resp", "leadListResponse" + userFeedbackUpdateResponse);


            if (userFeedbackUpdateResponse.compareTo("") != 0) {
                if (isJSONValid(userFeedbackUpdateResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(userFeedbackUpdateResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(userFeedbackUpdateResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(context, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                // Close the progressdialog
                mProgressDialog.dismiss();
                mListener.messageReceived(message);
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
    //
    private class approveFeedback extends AsyncTask<Void, Void, Void> {
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

                        put("user_id", user_id);
                        put("id",feedback_id);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            userFeedbackApprovalResponse = serviceAccess.SendHttpPost(Config.URL_APPROVEUSERFEEDBACKS, jsonLeadObj);
            Log.i("resp", "leadListResponse" + userFeedbackUpdateResponse);


            if (userFeedbackApprovalResponse.compareTo("") != 0) {
                if (isJSONValid(userFeedbackApprovalResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(userFeedbackApprovalResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(userFeedbackApprovalResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(context, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                // Close the progressdialog
                mProgressDialog.dismiss();
                mListener.messageReceived(message);
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    //
    private class rejectFeedback extends AsyncTask<Void, Void, Void> {
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

                        put("user_id", user_id);
                        put("id",feedback_id);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            userFeedbackRejectResponse = serviceAccess.SendHttpPost(Config.URL_DELETEUSERSFEEDBACK, jsonLeadObj);
            Log.i("resp", "leadListResponse" + userFeedbackRejectResponse);


            if (userFeedbackRejectResponse.compareTo("") != 0) {
                if (isJSONValid(userFeedbackRejectResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(userFeedbackRejectResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(userFeedbackRejectResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(context, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                // Close the progressdialog
                mProgressDialog.dismiss();
                mListener.messageReceived(message);
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
    //
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
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

}
