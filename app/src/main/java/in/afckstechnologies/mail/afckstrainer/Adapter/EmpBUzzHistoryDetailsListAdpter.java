package in.afckstechnologies.mail.afckstrainer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.afckstechnologies.mail.afckstrainer.Models.EmpBuzzHistroyDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.Models.StudentsAttendanceDetailsDAO;
import in.afckstechnologies.mail.afckstrainer.R;


/**
 * Created by admin on 3/18/2017.
 */

public class EmpBUzzHistoryDetailsListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<EmpBuzzHistroyDetailsDAO> data;
    EmpBuzzHistroyDetailsDAO current;
    int flagClick = 0;

    // create constructor to innitilize context and data sent from MainActivity
    public EmpBUzzHistoryDetailsListAdpter(Context context, List<EmpBuzzHistroyDetailsDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_emp_buzz_history_details, parent, false);
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
        myHolder.txt_date.setText(current.getBuzz_dates());
        myHolder.txt_date.setTag(position);
        myHolder.layout_quotation.setTag(position);
        myHolder.sno.setText(current.getNumbers());
        myHolder.sno.setTag(position);
        myHolder.layout_quotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                if (!current.getRemarks().equals("null")) {
                    if (flagClick == 0) {
                        flagClick = 1;
                        myHolder.remarks.setVisibility(View.VISIBLE);

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            String str = "Remarks  " + "<a>:-<font color='black'> " + current.getRemarks() + "</font> </a>";
                            myHolder.remarks.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT));
                            myHolder.remarks.setTag(position);
                        } else {
                            //String str = "<a>"+rs+" <font color='black'> "+rb+"</font> </a>";
                            String str = "Remarks  " + "<a>:-<font color='black'> " + current.getRemarks() + "</font> </a>";
                            myHolder.remarks.setText(Html.fromHtml(str));
                            myHolder.remarks.setTag(position);
                        }
                    } else {
                        flagClick = 0;
                        myHolder.remarks.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(context, "Remarks not available", Toast.LENGTH_SHORT).show();
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

        TextView txt_date, sno, remarks;
        LinearLayout layout_quotation;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            sno = (TextView) itemView.findViewById(R.id.sno);
            remarks = (TextView) itemView.findViewById(R.id.remarks);
            layout_quotation = (LinearLayout) itemView.findViewById(R.id.layout_quotation);


        }

    }


}
