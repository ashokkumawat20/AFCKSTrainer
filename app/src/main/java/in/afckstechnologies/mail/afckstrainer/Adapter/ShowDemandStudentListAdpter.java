package in.afckstechnologies.mail.afckstrainer.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.ContactsContract;
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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import in.afckstechnologies.mail.afckstrainer.Activity.DisplayStudentEditPreActivity;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.Models.AdminStudentsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.View.CommentAddView;

/**
 * Created by admin on 3/18/2017.
 */

public class ShowDemandStudentListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<StudentsDAO> data;
    StudentsDAO current;
    int currentPos = 0;
    String id, id1;
    String centerId;
    int ID;
    int number = 1, clickflag = 1;
    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String centerListResponse = "";
    boolean status;
    String message = "";
    String msg = "";
    String user_id = "";
    String batch_id = "";
    String courseListResponse = "";
    private static FeesListener mListener;

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    boolean undoOn; // is undo on, you can turn it on from the toolbar menu
    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    List<StudentsDAO> itemsPendingRemoval = new ArrayList<>();

    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<StudentsDAO, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be


    // create constructor to innitilize context and data sent from MainActivity
    public ShowDemandStudentListAdpter(Context context, List<StudentsDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_show_demand_student_details, parent, false);
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
        myHolder.name.setText(current.getStudents_Name());
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
        myHolder.numbers.setText(current.getNumbers());
        myHolder.whatsappeButton.setTag(position);
        myHolder.infoButton.setTag(position);
        myHolder.commentButton.setTag(position);
        myHolder.layout_quotation.setTag(position);
        myHolder.showBtn.setTag(position);
        myHolder.app_status.setTag(position);
        myHolder.user_status.setTag(position);
        myHolder.mobile_status.setTag(position);

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
        myHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                StudentsDAO contact = (StudentsDAO) cb.getTag();

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
                PackageManager packageManager = context.getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);
                try {
                    String url = "https://api.whatsapp.com/send?phone=" + "91" + current.getMobile_no() + "&text=" + URLEncoder.encode("", "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        context.startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*String name = getContactDetails(current.getMobile_no());
                if (!name.equals("")) {
                    try {
                        Uri uri = Uri.parse("smsto:" + current.getMobile_no());
                        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                        sendIntent.setPackage("com.whatsapp");
                        context.startActivity(sendIntent);
                    } catch (Exception e) {
                        Toast.makeText(context, "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                    }
                } else {

                    Intent i = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                    i.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                    i.putExtra(ContactsContract.Intents.Insert.NAME, current.getStudents_Name());
                    i.putExtra(ContactsContract.Intents.Insert.PHONE, current.getMobile_no());
                    i.putExtra(ContactsContract.Intents.Insert.EMAIL, current.getEmail_id());
                    context.startActivity(i);
                }*/


            }
        });

        myHolder.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("flag_edit_pre", "3");
                prefEditor.putString("user_id", current.getUser_id());
                prefEditor.putString("student_name", current.getStudent_Name());
                prefEditor.putString("student_moblile_no", current.getMobile_no());
                prefEditor.putString("student_mob_sms", current.getMobile_no());
                prefEditor.putString("st_first_name", current.getStudents_Name());
                prefEditor.putString("st_last_name", "");
                prefEditor.putString("st_notes", current.getNotes());
                prefEditor.commit();
                // Intent intent = new Intent(context, StudentEditPreActivity.class);
                Intent intent = new Intent(context, DisplayStudentEditPreActivity.class);
                intent.putExtra("user_id", current.getUser_id());
                context.startActivity(intent);
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
        myHolder.layout_quotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickflag == 1) {
                    clickflag = 2;
                    myHolder.showBtn.setVisibility(View.VISIBLE);
                } else {
                    clickflag = 1;
                    myHolder.showBtn.setVisibility(View.GONE);
                }
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
        id = current.getCourse_id();
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
        LinearLayout notesl, layout_quotation, showBtn;
        ImageView callingButton, messageButton, whatsappeButton, infoButton, commentButton, app_status, user_status, mobile_status;
        public CheckBox chkSelected;


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
            infoButton = (ImageView) itemView.findViewById(R.id.infoButton);
            commentButton = (ImageView) itemView.findViewById(R.id.commentButton);
            notesl = (LinearLayout) itemView.findViewById(R.id.notesl);
            layout_quotation = (LinearLayout) itemView.findViewById(R.id.layout_quotation);
            showBtn = (LinearLayout) itemView.findViewById(R.id.showBtn);
            app_status = (ImageView) itemView.findViewById(R.id.app_status);
            user_status = (ImageView) itemView.findViewById(R.id.user_status);
            mobile_status = (ImageView) itemView.findViewById(R.id.mobile_status);
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
    public List<StudentsDAO> getSservicelist() {
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
