package my.edu.um.fsktm.spendwise;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> arraylist;
    public ArrayList<String> budgetlist;
    public boolean trigger = false;
    public String month;
    public static int month_position;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseFunction databaseFunction = new DatabaseFunction();
        this.arraylist = databaseFunction.readTransFromFile("transaction.txt", this);
        if(this.arraylist == null){
            Log.d("TransactionMessage", "Create new");
            this.arraylist = new ArrayList<>();
        }
        this.budgetlist = databaseFunction.readBudgFromFile("budget.txt", this);
        if(this.budgetlist == null){
            trigger = true;
            Log.d("BudgetMessage", "FIrst time user");
            Intent intent = new Intent(getApplicationContext(), AddBudget.class);
            intent.putExtra("salary", budgetlist);
            startActivityForResult(intent, 2);
        }
        else{
            Log.d("BudgetMessage", String.valueOf(budgetlist));
        }
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        int pos_month = Integer.parseInt(dateFormat.format(date));


        Toolbar myToolbar = (Toolbar) findViewById(R.id.TB);
        Spinner MonthSpinner = findViewById(R.id.tb_spinner);
        String[] type_of_months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type_of_months);
        MonthSpinner.setAdapter(adapterMonth);
        MonthSpinner.setSelection(pos_month-1);
        MonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MonthSpinner", "Main:Enter here "+position);
                MainActivity.month_position = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Fragment fragment = null;
                if (view == findViewById(R.id.transaction)) {
                    fragment = new FragmentTrans();
                    if(bundle != null) {
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (view == findViewById(R.id.budget)) {
                    if(trigger == true) {
                        Log.d("Trigger", String.valueOf(trigger));
                        trigger = false;
                        String line = budgetlist.get(0);

                        String temp[] = line.split(",");
                        budgetlist = new ArrayList<>();
                        for (int i = 0; i < temp.length; i++) {
                            Log.d("Temp[i]", temp[i]);
                            budgetlist.add(temp[i]);
                        }
                    }
                    Log.d("Here", String.valueOf(budgetlist));
                    fragment = new FragmentBudget();
                    if(bundle != null){
                        bundle.putStringArrayList("budget", budgetlist);
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);

                } else if (view == findViewById(R.id.analyze)) {
                    if(trigger = true) {
                        trigger = false;
                        String line = budgetlist.get(0);

                        String temp[] = line.split(",");
                        budgetlist = new ArrayList<>();
                        for (int i = 0; i < temp.length; i++) {
                            Log.d("Temp[i]", temp[i]);
                            budgetlist.add(temp[i]);
                        }
                    }
                    fragment = new FragmentAnalysis();
                    if(bundle != null){
                        bundle.putStringArrayList("budget", budgetlist);
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);



                } else if (view == findViewById(R.id.overview)){
                    fragment = new FragmentOverview();
                    if(bundle != null){
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);

                }
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.FragOutput, fragment);
                transaction.commit();
            }
        };
        Button editBudget = (Button) findViewById(R.id.editBudget);
        editBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddBudget.class);
                intent.putExtra("salary", budgetlist);
                startActivityForResult(intent, 2);

            }
        });
        Button budget = (Button) findViewById(R.id.budget);
        budget.setOnClickListener(listener);
        Button transac = (Button) findViewById(R.id.transaction);
        transac.setOnClickListener(listener);
        Button overview = findViewById(R.id.overview);
        overview.setOnClickListener(listener);
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        AddTransaction.class);
                intent.putExtra("array", arraylist);
                startActivityForResult(intent, 1);
                Log.d("Message", "after that");
            }
        });
        FloatingActionButton fabAddIncome = (FloatingActionButton) findViewById(R.id.fabAddIncome);
        fabAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        AddTransactionIncome.class);
                intent.putExtra("array", arraylist);
                startActivityForResult(intent, 1);
                Log.d("Message", "after that");
            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FragOutput, new FragmentIntro());
        transaction.commit();

    }
        public void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d("Main","qer"+requestCode);
            if(requestCode == 1){
                if(resultCode == RESULT_OK){
                    this.arraylist = data.getStringArrayListExtra("array");
                    Log.d("Array", this.arraylist.toString());
                }
            }
            if(requestCode == 2){
                if(resultCode == RESULT_OK){
                    this.budgetlist = data.getStringArrayListExtra("salary");
                    Log.d("Salary", this.budgetlist.toString());
                }
            }
            if(requestCode == 3){
                Log.d("M3", "in ma?");
                if(resultCode == RESULT_OK){
                    this.arraylist = data.getStringArrayListExtra("newarray");
                    Log.d("New array", arraylist.toString());
                }
            }


        }


        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }

        public void onDestroy() {
            String database = "";
            for(int i = 0; i < arraylist.size(); i++){
                database += arraylist.get(i) + "\n";
            }
            DatabaseFunction databaseFunction = new DatabaseFunction();
            databaseFunction.writeToFile("transaction.txt", database, this);

            String budgetline = "";
            for(int i = 0; i < budgetlist.size(); i++){
                budgetline += budgetlist.get(i) +",";
            }
            databaseFunction.writeToFile("budget.txt", budgetline, this);
            Log.d("Close", "Safe and close.");
            super.onDestroy();
        }

        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case R.id.action_add:
                    return true;
                case R.id.action_category:
                    return true;
                case R.id.action_option:
                    return true;
                case R.id.action_account:
                    return true;
                default:
                    return super.onOptionsItemSelected(item);

            }
        }







    }


