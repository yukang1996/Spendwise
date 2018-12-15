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

import lecho.lib.hellocharts.formatter.AxisValueFormatter;
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
        //size = 12
        axisData = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
                "Oct", "Nov", "Dec"};

        List ExpenseValues = new ArrayList();
        List IncomeValues = new ArrayList();

        ArrayList<Double> exp_values = new ArrayList<>();
        ArrayList<Double> in_values = new ArrayList<>();

        //i = 0 = january
        //size = 13
        for (int i = 0; i < axisData.length+1; i++){
            exp_values.add(0.0);
            in_values.add(0.0);
        }

        //size = 1 + 12
        axisValues.add(0, new AxisValue(0).setLabel(""));
        for (int i = 0; i < axisData.length; i++) {
            axisValues.add(i+1, new AxisValue(i+1).setLabel(axisData[i]));
            Log.d(TAG, "axis data: "+(i+1)+" : "+axisData[i]);
        }

        for (int i = 0; i < transaction_list.size(); i++){
            TransactionRow tr = transaction_list.get(i);
            String tr_year = tr.date.split("-")[2];
            int tr_month = Integer.parseInt(tr.date.split("-")[1]);
            if (Integer.parseInt(tr_year) == year && tr.transaction_type.equalsIgnoreCase("expense")){
                double month_value = exp_values.get(tr_month);
                month_value+=tr.amount;
                exp_values.set(tr_month, month_value);
                Log.d(TAG, "Expense row: "+tr.amount);

            } else if (Integer.parseInt(tr_year) == year && tr.transaction_type.equalsIgnoreCase("income")){
                double month_value = in_values.get(tr_month);
                month_value+=tr.amount;
                in_values.set(tr_month, month_value);
                Log.d(TAG, "Income row: "+tr.amount);
            }
        }

        //1 - 12
        List<PointValue> Expense_values = new ArrayList<PointValue>();
        for (int i = 1; i < exp_values.size(); i++){
            double d = exp_values.get(i);
            Expense_values.add(new PointValue(i, (float)d));
        }

        List<PointValue> Income_values = new ArrayList<PointValue>();
        for (int i = 1; i < in_values.size(); i++){
            double d = in_values.get(i);
            Income_values.add(new PointValue(i, (float)d));
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

        Axis axis = new Axis();
        axis.setValues(axisValues);
        Log.d(TAG, "Axis Value: "+axisValues);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#03A9F4"));
        data.setAxisXBottom(axis);
//
        Axis yAxis = new Axis();
        //yAxis.setName("Value");
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(16);
        //yAxis.setMaxLabelChars(3);
        yAxis.setFormatter(new axisFormatSetter());
        Log.d(TAG, "FORMATTTTTTTTT: "+yAxis.getFormatter().toString());
        data.setAxisYLeft(yAxis);
//
//        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
//        viewport.top = 110;
//        lineChartView.setMaximumViewport(viewport);
//        lineChartView.setCurrentViewport(viewport);



        lineChartView.setLineChartData(data);
    }

    public class axisFormatSetter implements AxisValueFormatter {

        @Override
        public int formatValueForManualAxis(char[] formattedValue, AxisValue axisValue) {
            return 0;
        }

        /**
         * Used only for auto-generated axes. If you are not going to use your implementation for aut-generated axes you can
         * skip implementation of this method and just return 0. SFormats values with given number of digits after
         * decimal separator. Result is stored in given array. Method returns number of chars for formatted value. The
         * formatted value starts at index [formattedValue.length - charsNumber] and ends at index [formattedValue
         * .length-1].
         */
        @Override
        public int formatValueForAutoGeneratedAxis(char[] formattedValue, float value, int autoDecimalDigits) {
            Log.d(TAG, "input value: "+value);
            Log.d(TAG, "input formatted value: "+String.valueOf(formattedValue));
            Log.d(TAG, "input auto digit: "+autoDecimalDigits);
            String temp = "";
            if (autoDecimalDigits > 3 && autoDecimalDigits < 7){
                value/=1000;
                temp = String.valueOf(value) + "k";

            } else if (autoDecimalDigits > 6){
                value /= 1000000;
                temp = String.valueOf(value) + "m";
            } else {
                temp = String.valueOf(value);
            }
            Log.d(TAG, "String temp: "+temp);
            char [] v_char = temp.toCharArray();
            Log.d(TAG,"to char array output: "+String.valueOf(v_char));
            for (int i = v_char.length; i < 0; i--){
                formattedValue [formattedValue.length - i] = v_char[v_char.length - i];
            }
            Log.d(TAG, "What 7 is this: "+String.valueOf(formattedValue));
            return temp.length();
        }
    }
}
