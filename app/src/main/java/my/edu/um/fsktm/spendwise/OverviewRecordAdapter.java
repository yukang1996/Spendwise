package my.edu.um.fsktm.spendwise;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class OverviewRecordAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] category;
    private final String [] amount;


    public OverviewRecordAdapter(Activity context, String[] category, String[] amount) {
        super(context, R.layout.list_fragment_overview, category);
        this.context = context;
        this.category = category;
        this.amount = amount;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_fragment_overview, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        TextView txtAmount = rowView.findViewById(R.id.textView2);

//        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);


        txtTitle.setText(category[position]);
        txtAmount.setText("RM" + amount[position]);
//        extratxt.setText("Description "+category[position]);

        //set progress pivot....
//        if(category[position].equalsIgnoreCase("clothes")){
//            Log.d("Message", "in???");
//
//
//        }
//        else if(category[position].equalsIgnoreCase("food")){
//        }
        return rowView;

    };
}
