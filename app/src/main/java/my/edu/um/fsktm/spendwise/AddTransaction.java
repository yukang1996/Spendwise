package my.edu.um.fsktm.spendwise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import javax.xml.validation.Validator;

public class AddTransaction extends AppCompatActivity {
    private Validator nonempty_validate;
    private EditText editTextDate, editTextTransaction_type, editTextCategory,
            editTextAmount, editTextNote, editTextPicture;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        this.arrayList = intent.getStringArrayListExtra("array");

    }

    public void saveRecord(View v){
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextTransaction_type = (EditText) findViewById(R.id.editTextTransaction_type);
        editTextCategory = (EditText) findViewById(R.id.editTextCategory);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        editTextNote = (EditText) findViewById(R.id.editTextNote);
        editTextPicture = (EditText) findViewById(R.id.editTextPicture);

        String date, transaction_type, category, amount, note, picture;

        date = editTextDate.getText().toString();

        if(date.isEmpty()){
            editTextDate.setError("Please enter date");
            return;
        }

        transaction_type = editTextTransaction_type.getText().toString();

        if(transaction_type.isEmpty()){
            editTextTransaction_type.setError("Please enter transaction type");
            return;
        }

        category = editTextCategory.getText().toString();

        if(category.isEmpty()){
            editTextCategory.setError("Please enter category");
            return;
        }

        amount = editTextAmount.getText().toString();

        if(amount.isEmpty()){
            editTextAmount.setError("Please enter amount");
            return;
        }

        note = editTextNote.getText().toString();

        picture = editTextPicture.getText().toString();
        String line = date + "," + transaction_type + "," + category + "," + amount + "," + note + "," + picture;
        arrayList.add(line);
        Intent intent = new Intent();
        intent.putExtra("array", arrayList);
        setResult(RESULT_OK, intent);
        this.finish();

    }

}
