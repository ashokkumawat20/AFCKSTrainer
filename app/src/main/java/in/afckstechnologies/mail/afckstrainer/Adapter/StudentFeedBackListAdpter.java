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

import android.support.v4.app.ActivityCompat;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.List;


import in.afckstechnologies.mail.afckstrainer.Activity.SMSSendingActivity;

import in.afckstechnologies.mail.afckstrainer.Models.StudentsFeedbackListDAO;
import in.afckstechnologies.mail.afckstrainer.R;


/**
 * Created by admin on 3/18/2017.
 */

public class StudentFeedBackListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<StudentsFeedbackListDAO> data;
    StudentsFeedbackListDAO current;
    int currentPos = 0;
    String id, id1;
    String centerId;
    int ID;
    int number = 1;

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String sms_type = "";


    // create constructor to innitilize context and data sent from MainActivity
    public StudentFeedBackListAdpter(Context context, List<StudentsFeedbackListDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_student_feedback_details, parent, false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;
        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
        myHolder.name.setText(current.getFirst_name() + " " + current.getLast_name());
        myHolder.name.setTag(position);


        myHolder.mobile_no.setTag(position);
        myHolder.mobile_no.setText(current.getMobile_no());
        myHolder.numbers.setTag(position);

        myHolder.numbers.setText(current.getNumbers());
        myHolder.mailid.setText(current.getEmail_id());
        myHolder.mailid.setTag(position);
        String date_after = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", current.getFeedback_date());
        myHolder.feedBackDate.setText(date_after);
        myHolder.feedBackDate.setTag(position);
        myHolder.ratingBar1.setRating(Integer.parseInt(current.getQ1()));
        myHolder.ratingBar1.setTag(position);
        myHolder.ratingBar2.setRating(Integer.parseInt(current.getQ2()));
        myHolder.ratingBar2.setTag(position);
        myHolder.ratingBar3.setRating(Integer.parseInt(current.getQ3()));
        myHolder.ratingBar3.setTag(position);
        myHolder.feedbackButton.setTag(position);
        myHolder.batchcode.setText(current.getBatchID());
        myHolder.batchcode.setTag(position);
        myHolder.callingButton.setTag(position);
        myHolder.whatsappeButton.setTag(position);


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

                //  String number=getNoFromWatsApp(current.getMobile_no());
                //Toast.makeText(context, "Error/n" + number, Toast.LENGTH_SHORT).show();

            }
        });
        myHolder.feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                if(!current.getFeedback().equals("")) {
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("Feedback")
                            .setCancelable(false)
                            .setMessage(current.getFeedback())
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Your code when user clicked on Cancel
                                }
                            }).create();
                    dialog.show();
                }
                else
                {

                    Toast.makeText(context,"Feedback not available",Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView name, mobile_no, numbers, mailid, feedBackDate, feedback, batchcode;
        TextView mail_id;
        RatingBar ratingBar1, ratingBar2, ratingBar3;
        ImageView callingButton, feedbackButton, whatsappeButton;
        LinearLayout value_feedback;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            batchcode = (TextView) itemView.findViewById(R.id.batchcode);
            mobile_no = (TextView) itemView.findViewById(R.id.mobile_no);
            numbers = (TextView) itemView.findViewById(R.id.numbers);
            mailid = (TextView) itemView.findViewById(R.id.mailid);
            feedBackDate = (TextView) itemView.findViewById(R.id.feedBackDate);
            ratingBar1 = (RatingBar) itemView.findViewById(R.id.ratingBar1);
            ratingBar2 = (RatingBar) itemView.findViewById(R.id.ratingBar2);
            ratingBar3 = (RatingBar) itemView.findViewById(R.id.ratingBar3);
            whatsappeButton = (ImageView) itemView.findViewById(R.id.whatsappeButton);
            callingButton = (ImageView) itemView.findViewById(R.id.callingButton);
            feedbackButton = (ImageView) itemView.findViewById(R.id.feedbackButton);
        }

    }

    // method to access in activity after updating selection
    public List<StudentsFeedbackListDAO> getSservicelist() {
        return data;
    }


    //
    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            //LOGE(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }
}
