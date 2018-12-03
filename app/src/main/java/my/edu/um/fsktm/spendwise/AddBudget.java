package my.edu.um.fsktm.spendwise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class AddBudget extends AppCompatActivity {
    private EditText editTextSalary, editTextClothes, editTextFood, editTextTransport, editTextEntertainment,
    editTextOthers;
    private ArrayList<String> al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbudget);
        Intent intent = getIntent();
        this.al = intent.getStringArrayListExtra("salary");
        if(this.al == null){
            al = new ArrayList<>();
        }
    }

    public void saveBudget(View v){
        editTextSalary = findViewById(R.id.editTextSalary);
        editTextClothes = findViewById(R.id.editTextClothes);
        editTextEntertainment = findViewById(R.id.editTextEntertainment);
        editTextFood = findViewById(R.id.editTextFood);
        editTextTransport = findViewById(R.id.editTextTransport);
        editTextOthers = findViewById(R.id.editTextOthers);

        String salary, clothes, entertainment, food, transport, others;

        salary = editTextSalary.getText().toString();

        if(salary.isEmpty()){
            editTextSalary.setError("Please enter Salary");
            return;
        }

        clothes = editTextSalary.getText().toString();

        if(clothes.isEmpty()){
            editTextClothes.setError("Please enter Clothes");
            return;
        }

        food = editTextFood.getText().toString();

        if(food.isEmpty()){
            editTextFood.setError("Please enter Food");
            return;
        }

        transport = editTextTransport.getText().toString();

        if(transport.isEmpty()){
            editTextTransport.setError("Please enter Transport");
            return;
        }

        others = editTextOthers.getText().toString();

        if(others.isEmpty()){
            editTextOthers.setError("Please enter Others");
            return;
        }

        entertainment = editTextEntertainment.getText().toString();

        if(entertainment.isEmpty()){
            editTextEntertainment.setError("Please enter Entertainment");
            return;
        }

        String line = salary + "," + clothes + "," + food + "," + transport + "," + entertainment + "," + others;
        al.clear();
        al.add(line);
        Intent intent = new Intent();
        intent.putExtra("salary", al);
        setResult(RESULT_OK, intent);
        this.finish();
    }

    public void Cancel(View v){
        Intent intent = new Intent();
        intent.putExtra("salary", al);
        setResult(RESULT_OK, intent);
        this.finish();
    }
}
