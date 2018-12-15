package my.edu.um.fsktm.spendwise;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class FragmentAnalysis extends Fragment {

    ArrayList al;
    ArrayList<String> budgetlist;

    ListView list;

    int[] plan_percentage = new int[5];
    String [] plan_names = new String[]{"Clothes","Food","Transport","Entertainment","Others"};
    ArrayList <Integer> pie_colors = new ArrayList<>(Arrays.asList(Color.BLUE, Color.RED, Color.MAGENTA, Color.GRAY, Color.CYAN));
    int total_budget;

    TextView editSalary;
    Integer[] imgid;

    ArrayList<TransactionRow> transaction_list = new ArrayList<>();
    PieChartView pieChartView;


    View frag_view;
    String TAG = "frag anala";
    int current_month;
    Button switchSpend;
    Button switchBudget;
    TextView labelText;
    Button switchCat;
    Button switchFlow;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (getArguments() != null) {
            al = getArguments().getStringArrayList("array");
            budgetlist = getArguments().getStringArrayList("budget");

        }
        else {
            al = new ArrayList();
        }

        View v = inflater.inflate(R.layout.fragment_analysis, container, false);

        frag_view = v;

        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        for (int i = 0; i <al.size(); i++){
            String [] temp = al.get(i).toString().split(",");
            if (temp.length == 5)
                transaction_list.add(new TransactionRow(temp[0],temp[1],temp[2],Double.parseDouble(temp[3]),temp[4]));
        }

        Log.d(TAG,"Budget list passed into fragment: "+budgetlist.size());

        for (int i = 0; i < budgetlist.size(); i++){
            if (i == 0) total_budget = Integer.parseInt(budgetlist.get(i));
            else {
                Log.d(TAG,"budget list . get i: "+budgetlist.get(i));
                plan_percentage[i-1] = Integer.parseInt(budgetlist.get(i));
            }
        }

        switchSpend = frag_view.findViewById(R.id.switchSpend);
        switchBudget = frag_view.findViewById(R.id.switchBudget);

        switchSpend.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createPieChart(frag_view, MainActivity.pos_month, MainActivity.pos_year, "Spending");
                    }
                }
        );

        switchBudget.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createPieChart(frag_view, MainActivity.pos_month, MainActivity.pos_year, "Budget");
                    }
                }
        );

        current_month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        //TO DO show all when pos month = 13
        createPieChart(frag_view, MainActivity.pos_month, MainActivity.pos_year, "Spending");

        switchCat = frag_view.findViewById(R.id.switchC);
        switchFlow = frag_view.findViewById(R.id.switchCF);

        switchFlow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), CashFlow.class);
                        intent.putExtra("transaction list", transaction_list);
                        Log.d(TAG, "running cash flow act");
                        startActivity(intent);
                    }
                }
        );

    }

    public void createPieChart (View v, int month, int year, String mode) {
        //create pie chart based on type and month

        double total_spending = 0.0;
        double [] pie_values = new double[plan_names.length];

        labelText = v.findViewById(R.id.labelText);

        if (mode.equalsIgnoreCase("Spending")){

            Log.d(TAG, "mode = spending ----- "+transaction_list.size());

            for (int i = 0; i < transaction_list.size(); i++){
                TransactionRow tr = transaction_list.get(i);

                String [] tr_date = tr.date.split("-");
                if (Integer.parseInt(tr_date[1]) == month && Integer.parseInt(tr_date[2]) == year && tr.transaction_type.equalsIgnoreCase("Expense")){

                    for (int n = 0; n < plan_names.length; n ++){
                        if (plan_names[n].equalsIgnoreCase(tr.category)){
                            pie_values[n] += tr.amount;
                            total_spending += tr.amount;
                        }
                    }

                }
            }

            labelText.setText("Total "+mode+": "+total_spending);

        } else {



            for (int i = 0; i < plan_percentage.length; i++){
                pie_values[i] = plan_percentage[i];
                Log.d(TAG, "mode = budget: --- plan percent"+plan_percentage[i]);
                Log.d(TAG, "mode = budget: --- value"+pie_values[i]);
            }

            Log.d(TAG, "mode = budget ----- "+pie_values.length);

            labelText.setText("Total "+mode+":"+total_budget);
        }



        pieChartView = v.findViewById(R.id.chart11);
        Log.d("BudgetListaaaaaaa",budgetlist.toString());
        List<SliceValue> pieData = new ArrayList<>();
        for (int i = 0; i < pie_values.length; i++) {
            if (pie_values[i] > 0)
                pieData.add(new SliceValue((float) pie_values[i], pie_colors.get(i)).setLabel(plan_names[i]));
        }

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1(mode).setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));

        //pieChartData.setHasLabelsOutside(true);
        pieChartData.setSlicesSpacing(10);
        pieChartView.setPieChartData(pieChartData);

        createPieChartTable(v, month, mode);
    }

    public void createPieChartTable (View v, int month, String mode) {
        //create pie description table of pie chart based on type and data

        TableLayout tl = (TableLayout) v.findViewById(R.id.table1);
        tl.removeAllViewsInLayout();

        TableRow tr_head = new TableRow(this.getContext());
        tr_head.setBackgroundColor(Color.GRAY);

        TextView label_date = new TextView(this.getContext());
        label_date.setText("CATEGORY");
        label_date.setTextColor(Color.WHITE);
        //label_date.setWidth(700);
        label_date.setLayoutParams(
                new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 6f));

        tr_head.addView(label_date);// add the column to the table row here

        TextView label_weight_kg = new TextView(this.getContext());
        label_weight_kg.setText("PERCENT"); // set the text for the header
        label_weight_kg.setTextColor(Color.WHITE); // set the color
        //label_weight_kg.setWidth(150);
        label_weight_kg.setLayoutParams(
                new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
        tr_head.addView(label_weight_kg); // add the column to the table row here

        TextView label_RM = new TextView(this.getContext());
        label_RM.setText("RM");
        label_RM.setTextColor(Color.WHITE);
        //label_RM.setWidth(150);
        label_RM.setLayoutParams(
                new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
        tr_head.addView(label_RM);

        tl.addView(tr_head);

        double [] RMs= new double[plan_names.length];
        double [] percents = new double [plan_names.length];
        double total_spending = 0;

        if (mode.equalsIgnoreCase("Spending")) {

            for (int i = 0; i < transaction_list.size(); i++){
                TransactionRow tr = transaction_list.get(i);

                if (Integer.parseInt(tr.date.split("-")[1]) == month && tr.transaction_type.equalsIgnoreCase("Expense")){

                    for (int n = 0; n < plan_names.length; n ++){
                        if (plan_names[n].equalsIgnoreCase(tr.category)){
                            RMs[n] += tr.amount;
                            total_spending += tr.amount;

                        }
                    }

                }
            }

            for (int i = 0; i < percents.length; i++){
                percents[i] += Math.round(RMs[i]/(double)total_spending * 10000) / 100;
            }

        } else {

            for (int i = 0; i < plan_percentage.length; i++){
                double d = (double)total_budget*((double)plan_percentage[i]/100.0);
                RMs[i] = Math.round(d * 100) / 100;
                percents[i] = (double)plan_percentage[i];
            }

        }

        for (int i = 0; i < plan_names.length; i++){

            if (RMs[i] == 0){
                continue;
            }

            TableRow tr1 = new TableRow(this.getContext());
            TextView tv1 = new TextView(this.getContext());
            tv1.setText(plan_names[i]);
            tv1.setLayoutParams(
                    new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 6f));
            tr1.addView(tv1);

            TextView tv2 = new TextView(this.getContext());
            tv2.setText(String.valueOf(percents[i]) + "%");
            tv2.setLayoutParams(
                    new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
            tr1.addView(tv2);

            TextView tv3 = new TextView(this.getContext());
            tv3.setText(String.valueOf(RMs[i]));
            tv3.setLayoutParams(
                    new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
            tr1.addView(tv3);

            tl.addView(tr1);
        }

    }


}
