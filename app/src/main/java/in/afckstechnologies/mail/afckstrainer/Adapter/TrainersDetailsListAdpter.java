package in.afckstechnologies.mail.afckstrainer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Activity.RequestChangeActivity;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsFeesDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.TrainersDAO;
import in.afckstechnologies.mail.afckstrainer.R;


/**
 * Created by admin on 3/18/2017.
 */

public class TrainersDetailsListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<TrainersDAO> data;
    TrainersDAO current;


    // create constructor to innitilize context and data sent from MainActivity
    public TrainersDetailsListAdpter(Context context, List<TrainersDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_trainerss_details, parent, false);
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
        myHolder.txt_name.setText(current.getFirst_name() + "(" + current.getId() + ")");
        myHolder.txt_name.setTag(position);
        myHolder.sno.setText(current.getNumbers());
        myHolder.sno.setTag(position);

        myHolder.txt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = (Integer) v.getTag();
                current = data.get(id);
                //  Toast.makeText(context, current.getFirst_name(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, RequestChangeActivity.class);
                intent.putExtra("user_name", current.getFirst_name());
                intent.putExtra("user_id", current.getId());
                intent.putExtra("share_data", "");
                context.startActivity(intent);
            }
        });
    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView txt_name, sno;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            sno = (TextView) itemView.findViewById(R.id.sno);

        }

    }

    // method to access in activity after updating selection
    public List<TrainersDAO> getSservicelist() {
        return data;
    }

}
