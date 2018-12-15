package my.edu.um.fsktm.spendwise;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TransactionRecordAdapter extends ArrayAdapter<String> {
    private Activity context;
    private String[] amount;
    private Integer[] imgid;
    private String[] date;
    private String[] transaction_type;


    public TransactionRecordAdapter(Activity context, String[] amount, Integer[] imgid, String[] transaction_type, String[] date){
        super(context, R.layout.list_fragment_trans, amount);
        this.context = context;
        this.amount = amount;
        this.imgid = imgid;
        this.transaction_type = transaction_type;
        this.date = date;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_fragment_trans, null,true);


        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
        TextView extratxt2 = (TextView) rowView.findViewById(R.id.textView2);
        double temp = Double.parseDouble(amount[position]);
        txtTitle.setText(String.format("RM%.2f",temp));
        imageView.setImageResource(imgid[position]);
        extratxt.setText("Description "+transaction_type[position]);
        extratxt2.setText("Date: "+date[position]);
        return rowView;

    };


}
