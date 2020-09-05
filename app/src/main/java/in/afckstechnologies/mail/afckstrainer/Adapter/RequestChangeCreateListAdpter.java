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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Activity.EditRequestChangeActivity;
import in.afckstechnologies.mail.afckstrainer.Activity.RequestChangeAttchDisplayActivity;
import in.afckstechnologies.mail.afckstrainer.Activity.RequestChangeCompleteActivity;
import in.afckstechnologies.mail.afckstrainer.Activity.RequestPendingDisplayActivity;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.Models.RequestChangeListDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstrainer.Utils.Config;
import in.afckstechnologies.mail.afckstrainer.View.BuzzHistoryDetailsView;
import in.afckstechnologies.mail.afckstrainer.View.StudentFeesEntryView;
import in.afckstechnologies.mail.afckstrainer.View.UpdateRequestAssignNewUserView;


/**
 * Created by admin on 3/18/2017.
 */

public class RequestChangeCreateListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<RequestChangeListDAO> data;
    RequestChangeListDAO current;
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
    String centerListResponse = "", addRequestChangeResponse = "";
    boolean status;
    String message = "";
    String msg = "";
    String user_id = "";
    String batch_id = "";
    String reqest_change_id = "";
    String assign_to_user_id = "";
    String create_to_user_id = "";
    String assign_to_user_name = "";
    String create_to_user_name = "";
    String pri_status = "", r_id = "";
    String dis_msg = "";
    String rs = "", rb = "";
    int clickFlag = 0;
    private static FeesListener mListener;
    boolean undoOn; // is undo on, you can turn it on from the toolbar menu
    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    List<RequestChangeListDAO> itemsPendingRemoval = new ArrayList<>();

    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<RequestChangeListDAO, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be


    // create constructor to innitilize context and data sent from MainActivity
    public RequestChangeCreateListAdpter(Context context, List<RequestChangeListDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_request_create_change_details, parent, false);
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
        myHolder.requestTypeName.setText(current.getRequest_type_name());
        myHolder.requestTypeName.setTag(position);
        myHolder.codeCommentLay.setTag(position);


        myHolder.exdatetime.setTag(position);

        rs = current.getRequest_subject();
        if (!current.getRequest_body().equals("")) {

            //  rb="<a> <font color='white'>:-</font></a>"+current.getRequest_body();
            rb = "<a>:-<font color='black'> " + current.getRequest_body() + "</font> </a>";
        } else {
            rb = "";
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            String str = "<a>" + rs + "  " + rb + "</a>";
            myHolder.requestComments.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT));
            myHolder.requestComments.setTag(position);
        } else {


            //String str = "<a>"+rs+" <font color='black'> "+rb+"</font> </a>";
            String str = "<a>" + rs + "  " + rb + "</a>";
            myHolder.requestComments.setText(Html.fromHtml(str));
            myHolder.requestComments.setTag(position);
        }

        if (!current.getTicket_comments().equals("null")) {
            myHolder.codeCommentLay.setVisibility(View.VISIBLE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                String str = "Reply  " + "<a>:-<font color='black'> " + current.getTicket_comments() + " , " + formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", current.getTicket_close_date()) + "</font> </a>";
                myHolder.doneComments.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT));
                myHolder.doneComments.setTag(position);

            } else {
                //String str = "<a>"+rs+" <font color='black'> "+rb+"</font> </a>";
                String str = "Reply  " + "<a>:-<font color='black'> " + current.getTicket_comments() + " , " + formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", current.getTicket_close_date()) + "</font> </a>";
                myHolder.doneComments.setText(Html.fromHtml(str));
                myHolder.doneComments.setTag(position);


            }

        } else {
            myHolder.codeCommentLay.setVisibility(View.GONE);

        }
        // myHolder.requestComments.setText(rs+" "+rb);
        // myHolder.requestComments.setTag(position);
        myHolder.requestAssignUserName.setText(current.getCreate_user_name() + " => " + current.getAssign_user_name());
        myHolder.requestAssignUserName.setTag(position);
        myHolder.seqNol.setTag(position);
        myHolder.seqNom.setTag(position);
        myHolder.seqNoh.setTag(position);
        myHolder.requestDate.setText(current.getDate_time());
        myHolder.requestDate.setTag(position);
        myHolder.notify.setTag(position);
        myHolder.notnotify.setTag(position);

        if (current.getRead_status().equals("2")) {
            myHolder.notnotify.setVisibility(View.GONE);
            myHolder.notify.setVisibility(View.VISIBLE);
        } else {
            myHolder.notify.setVisibility(View.GONE);
            myHolder.notnotify.setVisibility(View.VISIBLE);
        }
        myHolder.longClick.setTag(position);
        myHolder.layout_quotation.setTag(position);


        if (current.getTicket_priority_status().equals("1")) {
            myHolder.seqNom.setVisibility(View.GONE);
            myHolder.seqNoh.setVisibility(View.GONE);
            myHolder.seqNol.setVisibility(View.VISIBLE);
            myHolder.seqNol.setText(current.getNumbers());

        }
        if (current.getTicket_priority_status().equals("2")) {
            myHolder.seqNol.setVisibility(View.GONE);
            myHolder.seqNoh.setVisibility(View.GONE);
            myHolder.seqNom.setVisibility(View.VISIBLE);
            myHolder.seqNom.setText(current.getNumbers());

        }
        if (current.getTicket_priority_status().equals("3")) {
            myHolder.seqNol.setVisibility(View.GONE);
            myHolder.seqNom.setVisibility(View.GONE);
            myHolder.seqNoh.setVisibility(View.VISIBLE);
            myHolder.seqNoh.setText(current.getNumbers());

        }


        myHolder.takeReminderG.setTag(position);
        myHolder.takeReminderR.setTag(position);
        myHolder.takeReminderGN.setTag(position);
        myHolder.pendding.setTag(position);
        myHolder.complete.setTag(position);
        myHolder.fileAttch.setTag(position);
        myHolder.whatsapp.setTag(position);
        myHolder.calling.setTag(position);

        myHolder.completeFlag.setTag(position);


        myHolder.notstart.setTag(position);
        if (!current.getAttachment_count().equals("0")) {
            myHolder.fileAttch.setVisibility(View.VISIBLE);


        } else {
            myHolder.fileAttch.setVisibility(View.GONE);


        }

        if (current.getStatus().equals("1")) {
            myHolder.notstart.setVisibility(View.VISIBLE);
            myHolder.pendding.setVisibility(View.GONE);
            myHolder.complete.setVisibility(View.GONE);
            if (!current.getPending_days().equals("null") && !current.getPending_days().equals("0")) {

                myHolder.notstart.setText(current.getPending_days());
                myHolder.notstart.setTag(position);
            } else {
                myHolder.notstart.setText("");
                myHolder.notstart.setTag(position);
            }

        }
        if (current.getStatus().equals("2")) {
            myHolder.pendding.setVisibility(View.VISIBLE);
            myHolder.complete.setVisibility(View.GONE);
            myHolder.notstart.setVisibility(View.GONE);
            if (!current.getPending_days().equals("null") && !current.getPending_days().equals("0")) {

                myHolder.pendding.setText(current.getPending_days());
                myHolder.pendding.setTag(position);
            } else {
                myHolder.pendding.setText("");
                myHolder.pendding.setTag(position);
            }
        }
        if (current.getStatus().equals("3")) {
            myHolder.pendding.setVisibility(View.GONE);
            myHolder.complete.setVisibility(View.VISIBLE);
            myHolder.notstart.setVisibility(View.GONE);

            if (!current.getPending_days().equals("null") && !current.getPending_days().equals("0")) {

                myHolder.complete.setText(current.getPending_days());
                myHolder.complete.setTag(position);
            } else {
                myHolder.complete.setText("");
                myHolder.complete.setTag(position);
            }
        }

        if (current.getStatus().equals("3")) {
            myHolder.takeReminderG.setVisibility(View.GONE);
            myHolder.takeReminderR.setVisibility(View.GONE);
            myHolder.takeReminderGN.setVisibility(View.GONE);


        } else {
            if (current.getBuzz_count().equals("0")) {
                myHolder.takeReminderR.setVisibility(View.GONE);
                myHolder.takeReminderGN.setVisibility(View.GONE);
                myHolder.takeReminderG.setVisibility(View.VISIBLE);
            } else {
                if (current.getRead_status().equals("1")) {
                    myHolder.takeReminderG.setVisibility(View.GONE);
                    myHolder.takeReminderGN.setVisibility(View.GONE);
                    myHolder.takeReminderR.setVisibility(View.VISIBLE);
                } else {
                    myHolder.takeReminderG.setVisibility(View.GONE);
                    myHolder.takeReminderR.setVisibility(View.GONE);
                    myHolder.takeReminderGN.setVisibility(View.VISIBLE);

                }
            }


        }


        String date = "";
        String time = "";
        if (!current.getExpected_date().equals("null") && !current.getExpected_date().equals("0000-00-00")) {
            myHolder.exdatetime.setVisibility(View.VISIBLE);
            date = formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", current.getExpected_date());

        }

        if (!current.getExpected_time().equals("null") && !current.getExpected_time().equals("00:00:00")) {
            myHolder.exdatetime.setVisibility(View.VISIBLE);
            time = current.getExpected_time();
        }
        if (date.equals("") && time.equals("")) {
            myHolder.exdatetime.setVisibility(View.GONE);
        }
        String finaldtime = "Expected : " + date + " " + time;
        myHolder.expdatetime.setText(finaldtime);
        myHolder.expdatetime.setTag(position);


        myHolder.takeReminderG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                reqest_change_id = current.getId();
                assign_to_user_id = current.getAssign_to_user_id();
                create_to_user_id = current.getUser_id();
                assign_to_user_name = current.getAssign_user_name();
                create_to_user_name = current.getCreate_user_name();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to send Buzz ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                myHolder.takeReminderG.setVisibility(View.GONE);
                                myHolder.takeReminderGN.setVisibility(View.GONE);
                                myHolder.takeReminderR.setVisibility(View.VISIBLE);

                                new sendReminder().execute();
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
                alert.setTitle("Sending Buzz");
                alert.show();


            }
        });
        myHolder.takeReminderR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                reqest_change_id = current.getId();
                assign_to_user_id = current.getAssign_to_user_id();
                create_to_user_id = current.getUser_id();
                assign_to_user_name = current.getAssign_user_name();
                create_to_user_name = current.getCreate_user_name();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to send Buzz ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                new sendReminder().execute();
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
                alert.setTitle("Sending Buzz");
                alert.show();


            }
        });
        myHolder.takeReminderGN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                reqest_change_id = current.getId();
                assign_to_user_id = current.getAssign_to_user_id();
                create_to_user_id = current.getUser_id();
                assign_to_user_name = current.getAssign_user_name();
                create_to_user_name = current.getCreate_user_name();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to send Buzz ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                myHolder.takeReminderG.setVisibility(View.GONE);
                                myHolder.takeReminderGN.setVisibility(View.GONE);
                                myHolder.takeReminderR.setVisibility(View.VISIBLE);
                                new sendReminder().execute();
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
                alert.setTitle("Sending Buzz");
                alert.show();


            }
        });
        myHolder.takeReminderR.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                reqest_change_id = current.getId();
                prefEditor.putString("emp_task_id", current.getId());
                prefEditor.putString("emp_assign_name", current.getAssign_user_name());
                prefEditor.commit();
                BuzzHistoryDetailsView buzzHistoryDetailsView = new BuzzHistoryDetailsView();
                buzzHistoryDetailsView.show(((FragmentActivity) context).getSupportFragmentManager(), "buzzHistoryDetailsView");

                //  Toast.makeText(context, reqest_change_id, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        myHolder.takeReminderGN.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                reqest_change_id = current.getId();
                prefEditor.putString("emp_task_id", current.getId());
                prefEditor.putString("emp_assign_name", current.getAssign_user_name());
                prefEditor.commit();
                myHolder.notify.setVisibility(View.GONE);
                myHolder.notnotify.setVisibility(View.VISIBLE);
                BuzzHistoryDetailsView buzzHistoryDetailsView = new BuzzHistoryDetailsView();
                buzzHistoryDetailsView.show(((FragmentActivity) context).getSupportFragmentManager(), "buzzHistoryDetailsView");

                //  Toast.makeText(context, reqest_change_id, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        myHolder.fileAttch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("reqest_change_id", current.getId());
                prefEditor.putString("assign_to_user_id", current.getAssign_to_user_id());
                prefEditor.putString("reqest_change_pending", "1");
                prefEditor.commit();
                Intent intent = new Intent(context, RequestChangeAttchDisplayActivity.class);
                context.startActivity(intent);
            }
        });

        myHolder.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                PackageManager packageManager = context.getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);
                try {
                    String url = "https://api.whatsapp.com/send?phone=" + "91" + current.getAssign_user_mobile_no() + "&text=" + URLEncoder.encode("", "UTF-8");
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
            }
        });
        myHolder.calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Log.i("call ph no", "" + current.getAssign_user_mobile_no());
                callIntent.setData(Uri.parse("tel:" + current.getAssign_user_mobile_no()));
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

        myHolder.seqNol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                myHolder.seqNol.setVisibility(View.GONE);
                myHolder.seqNom.setText(current.getNumbers());
                myHolder.seqNom.setVisibility(View.VISIBLE);
                myHolder.seqNoh.setVisibility(View.GONE);
                pri_status = "2";
                r_id = current.getId();
                dis_msg = "Set priority medium successfully";
                new updateRequestpriStatus().execute();
                // Toast.makeText(context,""+current.getId(),Toast.LENGTH_SHORT).show();
            }
        });
        myHolder.seqNom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                myHolder.seqNol.setVisibility(View.GONE);
                myHolder.seqNom.setVisibility(View.GONE);
                myHolder.seqNoh.setText(current.getNumbers());
                myHolder.seqNoh.setVisibility(View.VISIBLE);
                pri_status = "3";
                r_id = current.getId();
                dis_msg = "Set priority high successfully";
                new updateRequestpriStatus().execute();
                //  Toast.makeText(context,""+current.getId(),Toast.LENGTH_SHORT).show();
            }
        });
        myHolder.seqNoh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                myHolder.seqNol.setText(current.getNumbers());
                myHolder.seqNol.setVisibility(View.VISIBLE);
                myHolder.seqNom.setVisibility(View.GONE);
                myHolder.seqNoh.setVisibility(View.GONE);
                pri_status = "1";
                r_id = current.getId();
                dis_msg = "Set priority normal successfully";
                new updateRequestpriStatus().execute();
                // Toast.makeText(context,""+current.getId(),Toast.LENGTH_SHORT).show();
            }
        });

        myHolder.requestAssignUserName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                // prefEditor.putString("r_id", current.getId());
                // prefEditor.commit();
                pri_status = current.getTicket_priority_status();
                prefEditor.putString("reqest_change_id", current.getId());
                prefEditor.putString("reqest_change_pending", "2");
                prefEditor.commit();
                Intent intent = new Intent(context, EditRequestChangeActivity.class);
                intent.putExtra("r_id", current.getId());
                intent.putExtra("user_name", current.getUser_name());
                intent.putExtra("subject", current.getRequest_subject());
                intent.putExtra("body", current.getRequest_body());
                intent.putExtra("priority_status", pri_status);
                intent.putExtra("Assign_to_user_id", current.getAssign_to_user_id());
                intent.putExtra("Create_to_user_id", current.getUser_id());
                intent.putExtra("expected_date", current.getExpected_date());
                intent.putExtra("expected_time", current.getExpected_time());
                intent.putExtra("department_id", current.getDepartment_id());
                context.startActivity(intent);


                return true;
            }
        });

        myHolder.layout_quotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clickFlag == 0) {
                    clickFlag = 1;
                    myHolder.completeFlag.setVisibility(View.VISIBLE);
                } else {
                    clickFlag = 0;
                    myHolder.completeFlag.setVisibility(View.GONE);
                }
            }
        });


        myHolder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                r_id = current.getId();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to reassign task ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                myHolder.notstart.setVisibility(View.VISIBLE);
                                myHolder.pendding.setVisibility(View.GONE);
                                myHolder.complete.setVisibility(View.GONE);
                                new updateRequestReAssign().execute();
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
                alert.setTitle("Reassign task");
                alert.show();
            }
        });

        myHolder.notstart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                assign_to_user_id = current.getAssign_to_user_id();
                reqest_change_id = current.getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to complete task ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                new addRequestChangeDataDetails().execute();

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
                alert.setTitle("Completing task");
                alert.show();

                return true;
            }
        });

        myHolder.pendding.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                assign_to_user_id = current.getAssign_to_user_id();
                reqest_change_id = current.getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to complete task ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                new addRequestChangeDataDetails().execute();

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
                alert.setTitle("Completing task");
                alert.show();

                return true;
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
        id = current.getId();
        ID = position;
        prefEditor.putInt("position", position - 1);
        prefEditor.commit();
        //  Toast.makeText(context, "Remove id" + id, Toast.LENGTH_LONG).show();

        if (itemsPendingRemoval.contains(current)) {
            itemsPendingRemoval.remove(current);
        }
        if (data.contains(current)) {
            data.remove(position);
            notifyItemRemoved(position);
        }
        new deleteRequest().execute();
    }

    public void restoreItem(RequestChangeListDAO item, int position) {
        data.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public boolean isPendingRemoval(int position) {
        current = data.get(position);
        return itemsPendingRemoval.contains(current);
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView requestTypeName, requestComments, pendding, notstart, complete, requestDate, requestAssignUserName, seqNol, seqNom, seqNoh, doneComments, expdatetime, doneDate;
        ImageView takeReminderG, takeReminderR, takeReminderGN, fileAttch, whatsapp, calling, notfileAttch, nottakeReminder, notify, notnotify;
        LinearLayout longClick, completeFlag, layout_quotation, codeCommentLay, exdatetime;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);

            requestTypeName = (TextView) itemView.findViewById(R.id.requestTypeName);

            requestComments = (TextView) itemView.findViewById(R.id.requestComments);
            requestDate = (TextView) itemView.findViewById(R.id.requestDate);
            requestAssignUserName = (TextView) itemView.findViewById(R.id.requestAssignUserName);
            seqNol = (TextView) itemView.findViewById(R.id.seqNol);
            seqNom = (TextView) itemView.findViewById(R.id.seqNom);
            seqNoh = (TextView) itemView.findViewById(R.id.seqNoh);
            doneComments = (TextView) itemView.findViewById(R.id.doneComments);
            notify = (ImageView) itemView.findViewById(R.id.notify);
            notnotify = (ImageView) itemView.findViewById(R.id.notnotify);
            expdatetime = (TextView) itemView.findViewById(R.id.expdatetime);
            takeReminderG = (ImageView) itemView.findViewById(R.id.takeReminderG);
            takeReminderR = (ImageView) itemView.findViewById(R.id.takeReminderR);
            takeReminderGN = (ImageView) itemView.findViewById(R.id.takeReminderGN);
            notstart = (TextView) itemView.findViewById(R.id.notstart);
            pendding = (TextView) itemView.findViewById(R.id.pendding);
            complete = (TextView) itemView.findViewById(R.id.complete);
            fileAttch = (ImageView) itemView.findViewById(R.id.fileAttch);
            whatsapp = (ImageView) itemView.findViewById(R.id.whatsapp);
            calling = (ImageView) itemView.findViewById(R.id.calling);

            longClick = (LinearLayout) itemView.findViewById(R.id.longClick);
            completeFlag = (LinearLayout) itemView.findViewById(R.id.completeFlag);
            layout_quotation = (LinearLayout) itemView.findViewById(R.id.layout_quotation);
            codeCommentLay = (LinearLayout) itemView.findViewById(R.id.codeCommentLay);
            exdatetime = (LinearLayout) itemView.findViewById(R.id.exdatetime);


        }

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

    //

    private class updateRequestpriStatus extends AsyncTask<Void, Void, Void> {
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
                        put("id", r_id);
                        put("pri_status", pri_status);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEREQUESTTICKETPRI, jsonLeadObj);
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
                //  removeAt(ID);
                Toast.makeText(context, dis_msg, Toast.LENGTH_SHORT).show();
                // Close the progressdialog
                mProgressDialog.dismiss();


            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    private class updateRequestReAssign extends AsyncTask<Void, Void, Void> {
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
                        put("id", r_id);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEREQUESTTICKETREASSIGN, jsonLeadObj);
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
                //  removeAt(ID);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                // Close the progressdialog
                mProgressDialog.dismiss();


            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    private class deleteRequest extends AsyncTask<Void, Void, Void> {
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
                        put("request_id", id);
                        put("ticket_delete_status", "0");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            //  centerListResponse = serviceAccess.SendHttpPost(Config.URL_DELETEREQUESTTICKET, jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEREQUESTTICKETDELETESTATUS, jsonLeadObj);
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
                //  removeAt(ID);
                //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                // Close the progressdialog
                //   mListener.messageReceived(message);
                mProgressDialog.dismiss();


            } else {
                // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    private class addRequestChangeDataDetails extends AsyncTask<Void, Void, Void> {
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
                        put("request_type_id", reqest_change_id);
                        put("user_id", assign_to_user_id);
                        put("ticket_comments", "Done");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj);
            addRequestChangeResponse = serviceAccess.SendHttpPost(Config.URL_UPDATEREQUESTCHANGEDETAILS, jsonLeadObj);
            Log.i("resp", "addRequestChangeResponse" + addRequestChangeResponse);


            if (addRequestChangeResponse.compareTo("") != 0) {
                if (isJSONValid(addRequestChangeResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(addRequestChangeResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        id = jObject.getString("id");
                        jsonArray = new JSONArray(addRequestChangeResponse);

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

    private class sendReminder extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Sending Reminder...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {


                        put("task_id", reqest_change_id);
                        put("assign_to_user_name", assign_to_user_name);
                        put("create_to_user_name", create_to_user_name);
                        put("create_to_user_id", create_to_user_id);
                        put("assign_to_user_id", assign_to_user_id);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_SENDBUZZNOTIFICATION, jsonLeadObj);
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
                //  removeAt(ID);
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

    // method to access in activity after updating selection
    public List<RequestChangeListDAO> getSservicelist() {
        return data;
    }

    public static void bindListener(FeesListener listener) {
        mListener = listener;
    }

}
