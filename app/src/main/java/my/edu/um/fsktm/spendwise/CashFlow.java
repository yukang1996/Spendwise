package my.edu.um.fsktm.spendwise;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class CashFlow extends AppCompatActivity {

    private static final String TAG = "Cash Flow";
    ArrayList<TransactionRow> transaction_list = new ArrayList<>();


    LineChartView lineChartView;
    String[] axisData;

    Button catButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_flow);


        // Set window fullscreen and remove title bar, and force landscape orientation
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent intent = getIntent();
        transaction_list = intent.getParcelableArrayListExtra("transaction list");
        Log.d(TAG,"Transaction list size: "+transaction_list.size());

        createLineChart(MainActivity.pos_year);

        catButton = findViewById(R.id.switchCatLineGraph);
        catButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        backToCat();
                    }
                }
        );
    }

    public void backToCat () {
        this.finish();
    }

    public void createLineChart (int year){
        lineChartView = findViewById(R.id.chartLineGraph);

        List axisValues = new ArrayList();
        axisData = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
                "Oct", "Nov", "Dec"};

        List ExpenseValues = new ArrayList();
        List IncomeValues = new ArrayList();

        ArrayList<Double> exp_values = new ArrayList<>();
        ArrayList<Double> in_values = new ArrayList<>();

        //i = 0 = january
        for (int i = 0; i < axisData.length; i++){
            exp_values.add(0.0);
            in_values.add(0.0);
        }

        Line Expenseline = new Line(ExpenseValues).setColor(Color.parseColor("#b02626"));
        Line Incomeline = new Line(IncomeValues).setColor(Color.parseColor("#26b056"));

        for (int i = 0; i < axisData.length; i++) {
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
            Log.d(TAG, "axis data: "+i+" : "+axisData[i]);
        }

        for (int i = 0; i < transaction_list.size(); i++){
            TransactionRow tr = transaction_list.get(i);
            String tr_year = tr.date.split("-")[2];
            int tr_month = Integer.parseInt(tr.date.split("-")[1]);
            if (Integer.parseInt(tr_year) == year && tr.transaction_type.equalsIgnoreCase("expense")){
                double month_value = exp_values.get(tr_month-1);
                month_value+=tr.amount;
                exp_values.set(tr_month-1, month_value);
                Log.d(TAG, "Expense row: "+tr.amount);

            } else if (Integer.parseInt(tr_year) == year && tr.transaction_type.equalsIgnoreCase("income")){
                double month_value = in_values.get(tr_month-1);
                month_value+=tr.amount;
                in_values.set(tr_month-1, month_value);
                Log.d(TAG, "Income row: "+tr.amount);
            }
        }

//        ExpenseValues.add(new PointValue(tr_month-1, (float)tr.amount));
//        IncomeValues.add(new PointValue(tr_month-1, (float)tr.amount));
//        List lines = new ArrayList();
//        lines.add(Expenseline);
//        lines.add(Incomeline);
//        LineChartData data = new LineChartData();
//        data.setLines(lines);

        List<PointValue> Expense_values = new ArrayList<PointValue>();
        for (int i = 0; i < exp_values.size(); i++){
            double d = exp_values.get(i);
            Expense_values.add(new PointValue(i+1, (float)d));
        }

        List<PointValue> Income_values = new ArrayList<PointValue>();
        for (int i = 0; i < in_values.size(); i++){
            double d = in_values.get(i);
            Income_values.add(new PointValue(i+1, (float)d));
        }

        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(Expense_values);
        line.setColor(Color.parseColor("#b02626"));
        Line line2 = new Line(Income_values);
        line2.setColor(Color.parseColor("#26b056"));
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);
        lines.add(line2);
        //testing

        LineChartData data = new LineChartData();
        data.setLines(lines);
        lineChartView.setLineChartData(data);

//        Axis axis = new Axis();
//        axis.setValues(axisValues);
//        Log.d(TAG, "Axis Value: "+axisValues);
//        axis.setTextSize(16);
//        axis.setTextColor(Color.parseColor("#03A9F4"));
//        data.setAxisXBottom(axis);
//
//        Axis yAxis = new Axis();
//        yAxis.setName("Value");
//        yAxis.setTextColor(Color.parseColor("#03A9F4"));
//        yAxis.setTextSize(16);
//        data.setAxisYLeft(yAxis);
//
//        lineChartView.setLineChartData(data);
//        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
//        viewport.top = 110;
//        lineChartView.setMaximumViewport(viewport);
//        lineChartView.setCurrentViewport(viewport);
    }
}
