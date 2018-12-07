package my.edu.um.fsktm.spendwise;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

public class BudgetRecordAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] category;
    private final Integer[] imgid;
    private final int [] use_percentage;
    private final int [] plan_percentage;


    public BudgetRecordAdapter(Activity context, String[] category, Integer[] imgid, int[] use_percentage, int[] plan_percentage) {
        super(context, R.layout.list_fragment_budget, category);
        this.context = context;
        this.category = category;
        this.imgid = imgid;
        this.use_percentage = use_percentage;
        this.plan_percentage = plan_percentage;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_fragment_budget, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
//        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
        ProgressBar progressBar = (ProgressBar) rowView.findViewById(R.id.progressbar);

        try{
            txtTitle.setText(category[position]);
            imageView.setImageResource(imgid[position]);
//        extratxt.setText("Description "+category[position]);
            progressBar.setProgress(use_percentage[position]);
            progressBar.setSecondaryProgress(plan_percentage[position]);
        }catch (Exception e){

        }




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

