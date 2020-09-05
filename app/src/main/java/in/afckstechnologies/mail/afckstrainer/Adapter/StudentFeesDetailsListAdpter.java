package in.afckstechnologies.mail.afckstrainer.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Models.StudentsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsFeesDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.R;
import in.afckstechnologies.mail.afckstrainer.View.FeesDetailsView;
import in.afckstechnologies.mail.afckstrainer.View.StudentFeesEntryView;


/**
 * Created by admin on 3/18/2017.
 */

public class StudentFeesDetailsListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<StudentsFeesDetailsDAO> data;
    StudentsFeesDetailsDAO current;


    // create constructor to innitilize context and data sent from MainActivity
    public StudentFeesDetailsListAdpter(Context context, List<StudentsFeesDetailsDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_fees_details, parent, false);
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

        // String strCurrentDate = "Wed, 18 Apr 2012 07:55:29 +0000";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date newDate = null;
        try {
            newDate = format.parse(current.getDateTimeOfEntry());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("dd-MMM-yyyy");
        String date = format.format(newDate);
        myHolder.txt_date.setText(date);
        myHolder.txt_date.setTag(position);


        myHolder.notes.setText(current.getNote());
        myHolder.notes.setTag(position);

        myHolder.amount.setText(current.getFees());
        myHolder.amount.setTag(position);
        myHolder.ReceivedBy.setText(current.getReceivedBy());
        myHolder.ReceivedBy.setTag(position);
        myHolder.UserName.setText(current.getUserName());
        myHolder.UserName.setTag(position);
        myHolder.feesMode.setText(current.getFeeMode());
        myHolder.feesMode.setTag(position);

    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView txt_date, notes, amount, ReceivedBy, UserName, feesMode;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            notes = (TextView) itemView.findViewById(R.id.notes);
            amount = (TextView) itemView.findViewById(R.id.amount);
            ReceivedBy = (TextView) itemView.findViewById(R.id.ReceivedBy);
            UserName = (TextView) itemView.findViewById(R.id.UserName);
            feesMode = (TextView) itemView.findViewById(R.id.feesMode);

        }

    }


}
