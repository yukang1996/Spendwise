package my.edu.um.fsktm.spendwise;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.validation.Validator;

public class UpdateTransaction extends AppCompatActivity {
    private TextView tv_transaction_type;
    private Spinner spinner_category;
    private CalendarView calender_view;
    private Validator nonempty_validate;
    private EditText editTextDate,editTextAmount, editTextNote, editTextPicture;
    private ArrayList<String> al;
    private String transact_type ;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private int position;
    String cur_date = "";
    String date[];
    String transaction_type[];
    String category[];
    String amount[];
    String notes[];
    String picture[];

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent intent = getIntent();
        this.al = intent.getStringArrayListExtra("array");
        Log.d("Pass", this.al.toString());
        this.transact_type = intent.getStringExtra("t_type");
        Log.d("Type", String.valueOf(this.transact_type));
        this.position = Integer.parseInt(intent.getStringExtra("position"));
        Log.d("Position", String.valueOf(this.position));
        process_Info();
        tv_transaction_type = findViewById(R.id.tv_transaction_type);
        tv_transaction_type.setText("Transaction Type: "+transact_type);
        spinner_category = findViewById(R.id.sp_category);
        String[] type_of_category = {};
        int list_position = 0;
        if(transact_type.equalsIgnoreCase("Income")){
            type_of_category = new String[]{"Salary", "Others"};
            if(category[position].equalsIgnoreCase("Salary")){
                list_position = 0;
            }
            else if(category[position].equalsIgnoreCase("Others")){
                list_position = 1;
            }
        }
        else{
            type_of_category = new String[]{"Clothes", "Food", "Transport", "Entertainment", "Others"};
            if(category[position].equalsIgnoreCase("Clothes")){
                list_position = 0;
            }
            else if(category[position].equalsIgnoreCase("Food")){
                list_position = 1;
            }
            else if(category[position].equalsIgnoreCase("Transport")){
                list_position = 2;
            }
            else if(category[position].equalsIgnoreCase("Entertainment")){
                list_position = 3;
            }
            else if(category[position].equalsIgnoreCase("Others")){
                list_position = 4;
            }
        }
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type_of_category);
        spinner_category.setAdapter(adapterCategory);
        spinner_category.setSelection(list_position);
        calender_view = new CalendarView(this);
        calender_view = findViewById(R.id.calendarView);
        Log.d("Oldd", date[position]);
        long ms_date = 0;
        try{
            Date d = sdf.parse(date[position]);
            ms_date = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calender_view.setDate(ms_date);
        calender_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                cur_date = dayOfMonth+"-"+ month + 1 +"-"+ year;
                Log.d("Dateeee", cur_date);
            }
        });

//        editTextTransaction_type = (EditText) findViewById(R.id.editTextTransaction_type);

//        editTextCategory = (EditText) findViewById(R.id.editTextCategory);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        editTextNote = (EditText) findViewById(R.id.editTextNote);
        editTextPicture = (EditText) findViewById(R.id.editTextPicture);
        editTextAmount.setText(amount[position]);
        editTextNote.setText(notes[position]);
        editTextPicture.setText(picture[position]);
    }

    private void process_Info() {
        String[][] translist = new String[al.size()][];
        this.date = new String[al.size()];
        this.transaction_type = new String[al.size()];
        this.category = new String[al.size()];
        this.amount = new String[al.size()];
        this.notes = new String[al.size()];
        this.picture = new String[al.size()];
        Log.d("TLxxx", String.valueOf(al.size()));
        for (int i = 0; i < al.size(); i++){
            Log.d("TL2xxx", al.get(i).toString());
            translist[i] = al.get(i).toString().split(",");
            date[i] = translist[i][0];
            transaction_type[i] = translist[i][1];
            category[i] = translist[i][2];
            amount[i] = translist[i][3];
            notes[i] = translist[i][4];
            picture[i] = translist[i][5];
        }
    }

    public void saveRecord(View v){
        String amount, note, picture;

        String spValue_category = spinner_category.getSelectedItem().toString();



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

        picture = editTextPicture.getText().toString();

        String line = cur_date + "," + transact_type + "," + spValue_category + "," + amount + "," + note + "," + picture;
        Log.d("LINE", line);
        al.remove(position);
        al.add(line);
        Log.d("NewArray", al.toString());

        final MediaPlayer mp = new MediaPlayer().create(this, R.raw.mario_coin);
        mp.start();
        
        Intent intent = new Intent();
        intent.putExtra("newarray", al);
        setResult(RESULT_OK, intent);
        this.finish();

    }

    public void Cancel(View v){
        Intent intent = new Intent();
        intent.putExtra("newarray", al);
        setResult(RESULT_OK, intent);
        this.finish();
    }

    public void deleteRecord(View v){
        Log.d("Delete", "Deleted row:"+position);
        al.remove(position);
        Intent intent = new Intent();
        intent.putExtra("newarray", al);
        setResult(RESULT_OK, intent);
        this.finish();
    }
}
