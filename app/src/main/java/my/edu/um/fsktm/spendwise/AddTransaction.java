package my.edu.um.fsktm.spendwise;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.validation.Validator;

public class AddTransaction extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView tv_transaction_type;
    private Spinner spinner_category;
    private CalendarView calender_view;
    private EditText editTextAmount, editTextNote;
    private ArrayList<String> arrayList;
    private String transact_type = "Expense";
    private String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        this.arrayList = intent.getStringArrayListExtra("array");
        tv_transaction_type = findViewById(R.id.tv_transaction_type);
        tv_transaction_type.setText("Transaction Type: "+transact_type);
        spinner_category = findViewById(R.id.sp_category);
        String[] type_of_category = {"Clothes", "Food", "Transport", "Entertainment", "Others"};
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type_of_category);
        spinner_category.setAdapter(adapterCategory);
        calender_view = new CalendarView(this);
        calender_view = findViewById(R.id.calendarView);
        calender_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;
                date = dayOfMonth+"-"+ month+"-"+ year;
                Log.d("Dateeee", date);
            }
        });
    }

    public void saveRecord(View v){



//        editTextTransaction_type = (EditText) findViewById(R.id.editTextTransaction_type);
        String spValue_category = spinner_category.getSelectedItem().toString();
//        editTextCategory = (EditText) findViewById(R.id.editTextCategory);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        editTextNote = (EditText) findViewById(R.id.editTextNote);

        String  amount, note;




//        transaction_type = editTextTransaction_type.getText().toString();
//
//        if(transaction_type.isEmpty()){
//            editTextTransaction_type.setError("Please enter transaction type");
//            return;
//        }
//
//        category = editTextCategory.getText().toString();
//
//        if(category.isEmpty()){
//            editTextCategory.setError("Please enter category");
//            return;
//        }

        amount = editTextAmount.getText().toString();

        if(amount.isEmpty()){
            editTextAmount.setError("Please enter amount");
            return;
        }

        note = editTextNote.getText().toString();


        String line = date + "," + transact_type + "," + spValue_category + "," + amount + "," + note;
        Log.d("LINE", line);
        arrayList.add(line);

        final MediaPlayer mp = new MediaPlayer().create(this, R.raw.mario_coin);
        mp.start();

        Intent intent = new Intent();
        intent.putExtra("array", arrayList);
        setResult(RESULT_OK, intent);
        this.finish();


    }

    public void Cancel(View v){
        Intent intent = new Intent();
        intent.putExtra("array", arrayList);
        setResult(RESULT_OK, intent);
        this.finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        ((TextView)parent.getSelectedView()).setError("None Selected");
        return;
    }
}
