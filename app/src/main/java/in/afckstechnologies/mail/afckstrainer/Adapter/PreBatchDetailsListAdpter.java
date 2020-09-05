package in.afckstechnologies.mail.afckstrainer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


import in.afckstechnologies.mail.afckstrainer.Activity.StudentList;
import in.afckstechnologies.mail.afckstrainer.Listener.FeesListener;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsInBatchListDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.View.AttendanceDetailsView;


/**
 * Created by admin on 3/18/2017.
 */

public class PreBatchDetailsListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<StudentsInBatchListDAO> data;
    StudentsInBatchListDAO current;
    int currentPos = 0;
    String id, id1;
    String centerId;
    int ID;

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    private static FeesListener mListener;

    // create constructor to innitilize context and data sent from MainActivity
    public PreBatchDetailsListAdpter(Context context, List<StudentsInBatchListDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();

    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_pre_batch_students_details, parent, false);
        PreBatchDetailsListAdpter.MyHolder holder = new PreBatchDetailsListAdpter.MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;
        // Get current position of item in recyclerview to bind data and assign values from list
        PreBatchDetailsListAdpter.MyHolder myHolder = (PreBatchDetailsListAdpter.MyHolder) holder;
        current = data.get(position);
        myHolder.view_courses_name.setText(current.getCourse_name());
        myHolder.view_courses_name.setTag(position);
        myHolder.hidePreAttendance.setTag(position);
        myHolder.hidePreReason.setTag(position);
        myHolder.lead_Layout.setTag(position);

        if (!current.getPrevious_attendance().equals("")) {
            myHolder.hidePreAttendance.setVisibility(View.VISIBLE);
            myHolder.preAttendance.setText(current.getPrevious_attendance());
            myHolder.preAttendance.setTag(position);
        } else {
            myHolder.hidePreAttendance.setVisibility(View.GONE);
        }
        myHolder.view_batch_code.setText(current.getBatch_code());
        myHolder.view_batch_code.setTag(position);
        myHolder.view_fees.setText(current.getFees());
        myHolder.view_fees.setTag(position);
        myHolder.view_StartBatch.setText(current.getStart_date());
        myHolder.view_StartBatch.setTag(position);
        myHolder.basefees.setText("B:" + current.getBaseFees());
        myHolder.basefees.setTag(position);

        myHolder.view_PaidFees.setText("" + (Integer.parseInt(current.getBaseFees())));
        myHolder.view_PaidFees.setTag(position);
        myHolder.i_prebatch.setTag(position);
        if (current.getStatus().equals("0")) {
            myHolder.i_prebatch.setBackgroundColor(context.getResources().getColor(R.color.color_rbutton));
        }
        if (current.getStatus().equals("1")) {
            myHolder.i_prebatch.setBackgroundColor(context.getResources().getColor(R.color.color_sbutton));
        }

        if (!current.getDiscontinue_reason().equals("null")) {
            myHolder.hidePreReason.setVisibility(View.VISIBLE);
            myHolder.preReason.setText(current.getDiscontinue_reason());
            myHolder.preReason.setTag(position);
        } else {
            myHolder.hidePreReason.setVisibility(View.GONE);
        }
        myHolder.preAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);

                prefEditor.putString("id_student_p", preferences.getString("user_id", ""));
                prefEditor.putString("batchId", current.getBatch_code());
                prefEditor.putString("a_student_name", preferences.getString("st_first_name", "") + " " + preferences.getString("st_last_name", ""));
                prefEditor.commit();
                AttendanceDetailsView attendanceDetailsView = new AttendanceDetailsView();
                attendanceDetailsView.show(((FragmentActivity) context).getSupportFragmentManager(), "attendanceDetailsView");

            }
        });

        myHolder.lead_Layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                /*prefEditor.putString("prebatchcall", "1");
                prefEditor.commit();
                Intent intent = new Intent(context, StudentList.class);
                intent.putExtra("batch_id", current.getBatch_code());
                context.startActivity(intent);
                ((Activity) context).finish();*/
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, current.getBatch_code());
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, "Share text to.."));

                return true;
            }
        });

    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView view_courses_name, view_batch_code, view_fees, view_StartBatch, basefees, feesmos, feesigst, feessgst, feescgst, view_PaidFees, preAttendance, preReason;
        LinearLayout i_prebatch, hidePreAttendance, hidePreReason, lead_Layout;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            view_courses_name = (TextView) itemView.findViewById(R.id.view_courses_name);
            preReason = (TextView) itemView.findViewById(R.id.preReason);
            preAttendance = (TextView) itemView.findViewById(R.id.preAttendance);
            view_batch_code = (TextView) itemView.findViewById(R.id.view_batch_code);
            view_fees = (TextView) itemView.findViewById(R.id.view_fees);
            view_StartBatch = (TextView) itemView.findViewById(R.id.view_StartBatch);
            basefees = (TextView) itemView.findViewById(R.id.basefees);
            feesmos = (TextView) itemView.findViewById(R.id.feesmos);
            feesigst = (TextView) itemView.findViewById(R.id.feesigst);
            feessgst = (TextView) itemView.findViewById(R.id.feessgst);
            feescgst = (TextView) itemView.findViewById(R.id.feescgst);
            view_PaidFees = (TextView) itemView.findViewById(R.id.view_PaidFees);
            i_prebatch = (LinearLayout) itemView.findViewById(R.id.i_prebatch);
            hidePreAttendance = (LinearLayout) itemView.findViewById(R.id.hidePreAttendance);
            hidePreReason = (LinearLayout) itemView.findViewById(R.id.hidePreReason);
            lead_Layout = (LinearLayout) itemView.findViewById(R.id.lead_Layout);
        }

    }

    public static void bindListener(FeesListener listener) {
        mListener = listener;
    }
}
