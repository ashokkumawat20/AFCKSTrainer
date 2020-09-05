package in.afckstechnologies.mail.afckstrainer.Adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Activity.DisplayStudentEditPreActivity;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.Models.AdminStudentsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.CampaignStudentsDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.View.AddContactForCallingView;
import in.afckstechnologies.mail.afckstrainer.View.CommentAddPreferenceView;
import in.afckstechnologies.mail.afckstrainer.View.CommentAddView;
import in.afckstechnologies.mail.afckstrainer.View.CommentsDetailsView;
import in.afckstechnologies.mail.afckstrainer.View.MultipleCommentAddView;


public class CampaignStudentListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<CampaignStudentsDAO> data;
    CampaignStudentsDAO current;
    int currentPos = 0;
    String id, id1;
    String centerId;
    int ID;
    int number = 1;
    int position1;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String sms_type = "";
    int clickflag = 1;
    String m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11;

    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String centerListResponse = "";
    boolean status;
    String message = "";
    String msg = "";
    String user_id = "";
    String batch_id = "";
    String courseListResponse = "", confirm_status = "";
    private static FeesListener mListener;
    boolean undoOn; // is undo on, you can turn it on from the toolbar menu
    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    List<CampaignStudentsDAO> itemsPendingRemoval = new ArrayList<>();

    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<CampaignStudentsDAO, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be


    // create constructor to innitilize context and data sent from MainActivity
    public CampaignStudentListAdpter(Context context, List<CampaignStudentsDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_campaign_student_details, parent, false);
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
        myHolder.name.setText(current.getStudent_Name());
        myHolder.name.setTag(position);
        // myHolder.mail_id.setText(current.getEmail_id());
        // myHolder.mail_id.setTag(position);
        myHolder.callingButton.setTag(position);
        myHolder.messageButton.setTag(position);
        myHolder.mobile_no.setTag(position);
        myHolder.mobile_no.setText(current.getMobile_no());
        myHolder.chkSelected.setTag(position);
        myHolder.chkSelected.setChecked(data.get(position).isSelected());
        myHolder.chkSelected.setTag(data.get(position));
        myHolder.numbers.setTag(position);
        myHolder.whatsappeButton.setTag(position);
        myHolder.numbers.setText(current.getNumbers());
        myHolder.takeBookSeat.setTag(position);
        myHolder.app_status.setTag(position);
        myHolder.user_status.setTag(position);
        myHolder.infoButton.setTag(position);
        myHolder.commentButton.setTag(position);
        myHolder.mobile_status.setTag(position);
        myHolder.send_calllater.setTag(position);
        myHolder.clickForShowBtn.setTag(position);
        myHolder.clickLayout.setTag(position);
        myHolder.takeComments.setTag(position);
        myHolder.pendingStatus.setTag(position);
        myHolder.confirmStatus.setTag(position);

        Log.d("status", current.getSourse());
        if (current.getSourse().equals("Mobile")) {
            myHolder.mobile_status.setVisibility(View.VISIBLE);
            myHolder.user_status.setVisibility(View.GONE);
            myHolder.app_status.setVisibility(View.GONE);
        } else if (current.getSourse().equals("Admin")) {
            myHolder.mobile_status.setVisibility(View.GONE);
            myHolder.user_status.setVisibility(View.VISIBLE);
            myHolder.app_status.setVisibility(View.GONE);
        } else if (current.getSourse().equals("App")) {
            myHolder.mobile_status.setVisibility(View.GONE);
            myHolder.user_status.setVisibility(View.GONE);
            myHolder.app_status.setVisibility(View.VISIBLE);
        } else {
            myHolder.mobile_status.setVisibility(View.GONE);
            myHolder.user_status.setVisibility(View.GONE);
            myHolder.app_status.setVisibility(View.GONE);
        }

        if (current.getNotes().equals("null") || current.getNotes().equals("")) {
            myHolder.notesl.setVisibility(View.GONE);
        } else {

            myHolder.notesl.setVisibility(View.VISIBLE);
            myHolder.notes.setText(current.getNotes());
            myHolder.notes.setTag(position);
        }
        if (current.getStarred().equals("1")) {
            myHolder.pendingStatus.setVisibility(View.GONE);
            myHolder.confirmStatus.setVisibility(View.VISIBLE);
        } else {
            myHolder.confirmStatus.setVisibility(View.GONE);
            myHolder.pendingStatus.setVisibility(View.VISIBLE);
        }
        myHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                CampaignStudentsDAO contact = (CampaignStudentsDAO) cb.getTag();

                contact.setSelected(cb.isChecked());
                data.get(pos).setSelected(cb.isChecked());
                // Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();

            }
        });

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
                String temp_message = "";
                String msgData1 = preferences.getString("campaign_txt_copy", "");
                boolean fristName = msgData1.contains("[first_name]");
                if (fristName) {
                    m1 = msgData1.replace("[first_name]", current.getFirst_name());
                    temp_message = m1;
                }



                //  System.out.println("message--->" + temp_message);
                //Toast.makeText(context, temp_message, Toast.LENGTH_SHORT).show();
                if (preferences.getString("attach_campaign_file_path", "").equals("")) {
                    PackageManager packageManager = context.getPackageManager();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    try {
                        String url = "https://api.whatsapp.com/send?phone=" + "91" + current.getMobile_no() + "&text=" + URLEncoder.encode(temp_message, "UTF-8");

                        if (preferences.getString("trainer_user_id", "").equals("AT")) {
                            i.setPackage("com.whatsapp.w4b");
                        } else {
                            i.setPackage("com.whatsapp");
                        }
                        i.setData(Uri.parse(url));
                        if (i.resolveActivity(packageManager) != null) {
                            context.startActivity(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {

                        File f = new File(preferences.getString("attach_campaign_file_path", ""));
                        Uri imageUri = Uri.parse("file://" + f.getAbsolutePath());
                        String toNumber = "91" + current.getMobile_no(); // contains spaces.
                        Intent sendIntent = new Intent("android.intent.action.MAIN");
                        sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
                        sendIntent.putExtra(Intent.EXTRA_TEXT, temp_message);
                        sendIntent.setAction(Intent.ACTION_SEND);
                        if (preferences.getString("trainer_user_id", "").equals("AT")) {
                            sendIntent.setPackage("com.whatsapp.w4b");
                        } else {
                            sendIntent.setPackage("com.whatsapp");
                        }
                        sendIntent.setType("image/png");
                        context.startActivity(sendIntent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                  /*
                 toNumber = toNumber.replace("+", "").replace(" ", "");
               String toNumber = "919762118718";
                  Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, temp_message);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/jpeg");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(Intent.createChooser(shareIntent, "Share to "));
*/


            }
        });

        myHolder.takeBookSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                user_id = current.getUser_id();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to Book Seat ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {

                                    new bookSeat().execute();

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
                alert.setTitle("Booking Seat");
                alert.show();
            }
        });

        myHolder.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("flag_edit_pre", "1");
                prefEditor.putString("user_id", current.getUser_id());
                prefEditor.putString("student_name", current.getStudent_Name());
                prefEditor.putString("student_moblile_no", current.getMobile_no());
                prefEditor.putString("student_mob_sms", current.getMobile_no());
                prefEditor.putString("st_first_name", current.getStudent_Name());
                prefEditor.putString("st_last_name", "");
                prefEditor.putString("st_notes", current.getNotes());
                prefEditor.commit();
                // Intent intent = new Intent(context, StudentEditPreActivity.class);
                Intent intent = new Intent(context, DisplayStudentEditPreActivity.class);
                intent.putExtra("user_id", current.getUser_id());
                context.startActivity(intent);
            }
        });

        myHolder.infoButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("user_id", current.getUser_id());
                prefEditor.commit();
                CommentAddPreferenceView commentAddView = new CommentAddPreferenceView();
                commentAddView.show(((FragmentActivity) context).getSupportFragmentManager(), "commentAddView");
                return true;
            }
        });

        myHolder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("user_id", current.getUser_id());
                prefEditor.commit();
                CommentAddView commentAddView = new CommentAddView();
                commentAddView.show(((FragmentActivity) context).getSupportFragmentManager(), "commentAddView");
            }
        });
        myHolder.send_calllater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                String columns[] = current.getStudent_Name().split(" ");
                String first_name = columns[0];
                String last_name = columns[1];
                prefEditor.putString("sc_mobile_no", current.getMobile_no());
                prefEditor.putString("sc_first_name", current.getFirst_name());
                prefEditor.putString("sc_last_name", last_name);
                prefEditor.putString("sc_email_id", current.getEmail_id());
                prefEditor.putString("flag", "add");
                prefEditor.commit();
                AddContactForCallingView addContactForCallingView = new AddContactForCallingView();
                addContactForCallingView.show(((FragmentActivity) context).getSupportFragmentManager(), "addContactForCallingView");

            }
        });
        myHolder.clickForShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickflag == 1) {
                    clickflag = 2;
                    myHolder.clickLayout.setVisibility(View.VISIBLE);

                } else {
                    clickflag = 1;
                    myHolder.clickLayout.setVisibility(View.GONE);

                }
            }
        });

        if (preferences.getString("showFlag", "").equals("1")) {
            myHolder.clickLayout.setVisibility(View.VISIBLE);
        } else {
            myHolder.clickLayout.setVisibility(View.GONE);
        }
        myHolder.takeComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("user_id", current.getUser_id());
                prefEditor.commit();
                MultipleCommentAddView commentAddView = new MultipleCommentAddView();
                commentAddView.show(((FragmentActivity) context).getSupportFragmentManager(), "commentAddView");
            }
        });
        myHolder.takeComments.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("user_id", current.getUser_id());
                prefEditor.putString("da", current.getStudent_Name());
                prefEditor.commit();
                CommentsDetailsView commentAddView = new CommentsDetailsView();
                commentAddView.show(((FragmentActivity) context).getSupportFragmentManager(), "commentAddView");
                return true;
            }
        });
        myHolder.pendingStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                position1 = data.indexOf(current);
                // Toast.makeText(context, "" + current.getUser_id(), Toast.LENGTH_LONG).show();
                user_id = current.getUser_id();
                confirm_status = "1";
                current.setStarred("1");
                new updateJobStatus().execute();

            }
        });
        myHolder.confirmStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                position1 = data.indexOf(current);
                user_id = current.getUser_id();
                confirm_status = "0";
                current.setStarred("0");
                new updateJobStatus().execute();
                // Toast.makeText(context, "" + current.getUser_id(), Toast.LENGTH_LONG).show();

            }
        });


    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setUndoOn(boolean undoOn) {
        this.undoOn = undoOn;
    }

    public boolean isUndoOn() {
        return undoOn;
    }

    public void pendingRemoval(int position) {
        current = data.get(position);
        if (!itemsPendingRemoval.contains(current)) {
            itemsPendingRemoval.add(current);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the item
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {

                    remove(data.indexOf(current));

                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(current, pendingRemovalRunnable);
        }
    }

    public void remove(int position) {
        current = data.get(position);
        user_id = current.getUser_id();
        ID = position;
        // Toast.makeText(context, "Remove id" + id, Toast.LENGTH_LONG).show();

        if (itemsPendingRemoval.contains(current)) {
            itemsPendingRemoval.remove(current);
        }
        if (data.contains(current)) {
            data.remove(position);
            notifyItemRemoved(position);
        }
        new deleteSale().execute();
    }

    public boolean isPendingRemoval(int position) {
        current = data.get(position);
        return itemsPendingRemoval.contains(current);
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView name, mobile_no, numbers, notes;
        TextView mail_id;
        LinearLayout notesl;
        ImageView callingButton, messageButton, whatsappeButton, takeBookSeat, app_status, user_status, mobile_status, infoButton, commentButton, send_calllater, takeComments, pendingStatus, confirmStatus;
        public CheckBox chkSelected;
        LinearLayout clickForShowBtn, clickLayout;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            notes = (TextView) itemView.findViewById(R.id.notes);
            callingButton = (ImageView) itemView.findViewById(R.id.callingButton);
            chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
            mobile_no = (TextView) itemView.findViewById(R.id.mobile_no);
            messageButton = (ImageView) itemView.findViewById(R.id.messageButton);
            numbers = (TextView) itemView.findViewById(R.id.numbers);
            whatsappeButton = (ImageView) itemView.findViewById(R.id.whatsappeButton);
            takeBookSeat = (ImageView) itemView.findViewById(R.id.takeBookSeat);
            app_status = (ImageView) itemView.findViewById(R.id.app_status);
            user_status = (ImageView) itemView.findViewById(R.id.user_status);
            mobile_status = (ImageView) itemView.findViewById(R.id.mobile_status);
            infoButton = (ImageView) itemView.findViewById(R.id.infoButton);
            commentButton = (ImageView) itemView.findViewById(R.id.commentButton);
            send_calllater = (ImageView) itemView.findViewById(R.id.send_calllater);
            takeComments = (ImageView) itemView.findViewById(R.id.takeComments);
            notesl = (LinearLayout) itemView.findViewById(R.id.notesl);
            pendingStatus = (ImageView) itemView.findViewById(R.id.pendingStatus);
            confirmStatus = (ImageView) itemView.findViewById(R.id.confirmStatus);
            clickForShowBtn = (LinearLayout) itemView.findViewById(R.id.clickForShowBtn);
            clickLayout = (LinearLayout) itemView.findViewById(R.id.clickLayout);
        }

    }

    //
    private class bookSeat extends AsyncTask<Void, Void, Void> {
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
            final String transaction = "Book seat with Zero fees";
            // String date = dpicker.getText().toString();
            final String amount = "0";

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final String datezero = df.format(c.getTime());
            jsonLeadObj = new JSONObject() {
                {
                    try {

                        put("user_id", user_id);
                        put("batch_id", preferences.getString("curr_batch_id", ""));
                        put("trans_id", transaction);
                        put("pay_date", datezero);
                        put("amount", amount);
                        put("status", 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_BOOKING_BATCH, jsonLeadObj);
            Log.i("resp", "leadListResponse" + centerListResponse);


            if (centerListResponse.compareTo("") != 0) {
                if (isJSONValid(centerListResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(centerListResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(centerListResponse);

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
    private class deleteSale extends AsyncTask<Void, Void, Void> {
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
                        put("course_id", id);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            courseListResponse = serviceAccess.SendHttpPost(Config.URL_DELETE_COURSE, jsonLeadObj);
            Log.i("resp", "leadListResponse" + courseListResponse);


            if (courseListResponse.compareTo("") != 0) {
                if (isJSONValid(courseListResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(courseListResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(courseListResponse);

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
                // removeAt(ID);
                // Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mListener.messageReceived(message);
                mProgressDialog.dismiss();
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    private class updateJobStatus extends AsyncTask<Void, Void, Void> {
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
                        put("job_status", confirm_status);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            courseListResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEJOBSTATUS, jsonLeadObj);
            Log.i("resp", "leadListResponse" + courseListResponse);


            if (courseListResponse.compareTo("") != 0) {
                if (isJSONValid(courseListResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(courseListResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(courseListResponse);

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
                notifyItemChanged(position1);
                mProgressDialog.dismiss();
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

    public static void bindListener(FeesListener listener) {
        mListener = listener;
    }

    // method to access in activity after updating selection
    public List<CampaignStudentsDAO> getSservicelist() {
        return data;
    }

    public String getContactDetails(String phoneNumber1) {
        String searchNumber = phoneNumber1;
        String phoneNumber = "", emailAddress = "", name = "";
        StringBuffer sb = new StringBuffer();
        // Cursor c =  getContentResolver().query(contactData, null, null, null, null);
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(searchNumber));
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        if (c.moveToFirst()) {


            name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            //http://stackoverflow.com/questions/866769/how-to-call-android-contacts-list   our upvoted answer

            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if (hasPhone.equalsIgnoreCase("1"))
                hasPhone = "true";
            else
                hasPhone = "false";

            if (Boolean.parseBoolean(hasPhone)) {
                Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                while (phones.moveToNext()) {
                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phones.close();
            }

            // Find Email Addresses
            Cursor emails = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
            while (emails.moveToNext()) {
                emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            }
            emails.close();

            //mainActivity.onBackPressed();
            // Toast.makeText(mainactivity, "go go go", Toast.LENGTH_SHORT).show();

            //  tvname.setText("Name: "+name);
            //tvphone.setText("Phone: "+phoneNumber);
            //tvmail.setText("Email: "+emailAddress);

            sb.append("\nUser Name:--- " + name + " \nCall Type:--- "
                    + " \nMobile Number:--- " + phoneNumber
                    + " \nEmail Id:--- " + emailAddress);
            sb.append("\n----------------------------------");


// add elements to al, including duplicates


            Log.d("curs", name + " num" + phoneNumber + " " + "mail" + emailAddress);
        }
        c.close();
        return name;
    }


}
