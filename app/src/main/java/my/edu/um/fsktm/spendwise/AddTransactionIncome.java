package my.edu.um.fsktm.spendwise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import javax.xml.validation.Validator;

public class AddTransactionIncome extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView tv_transaction_type;
    private Spinner spinner_category;
    private Validator nonempty_validate;
    private EditText editTextDate,editTextAmount, editTextNote, editTextPicture;
    private ArrayList<String> arrayList;
    private String transact_type = "Income";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        this.arrayList = intent.getStringArrayListExtra("array");
        tv_transaction_type = findViewById(R.id.tv_transaction_type);
        tv_transaction_type.setText("Transaction Type: "+transact_type);
        spinner_category = findViewById(R.id.sp_category);
        String[] type_of_category = {"Salary", "Others"};
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type_of_category);
        spinner_category.setAdapter(adapterCategory);
    }

    public void saveRecord(View v){
        editTextDate = (EditText) findViewById(R.id.editTextDate);
//        editTextTransaction_type = (EditText) findViewById(R.id.editTextTransaction_type);
        String spValue_category = spinner_category.getSelectedItem().toString();
//        editTextCategory = (EditText) findViewById(R.id.editTextCategory);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        editTextNote = (EditText) findViewById(R.id.editTextNote);
        editTextPicture = (EditText) findViewById(R.id.editTextPicture);

        String date, amount, note, picture;

        date = editTextDate.getText().toString();

        if(date.isEmpty()){
            editTextDate.setError("Please enter date");
            return;
        }

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

        String line = date + "," + transact_type + "," + spValue_category + "," + amount + "," + note + "," + picture;
        Log.d("LINE", line);
        arrayList.add(line);
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
