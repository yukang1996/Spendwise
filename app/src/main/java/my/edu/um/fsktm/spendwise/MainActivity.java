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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> arraylist;
    public ArrayList<String> budgetlist;
    public boolean trigger = false;
    public static int month_position;
    public static String fragment_type = "";
    public static int pos_month;
    public static int pos_year;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.TB);
        final TextView setTv_year = (TextView)findViewById(R.id.tv_year);
        final Spinner MonthSpinner = findViewById(R.id.tb_spinner);
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
        DateFormat dateFormat1 = new SimpleDateFormat(("YYYY"));
        Date date1 = new Date();
        pos_year = Integer.parseInt(dateFormat1.format(date1));
        pos_month = Integer.parseInt(dateFormat.format(date));
        ImageButton btn_back = findViewById(R.id.button_back);
        Log.d("Going to Entered:", String.valueOf(pos_month));
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos_month == 1){
                    pos_year -= 1;
                    pos_month = 12;
                }
                else{
                    pos_month -= 1;
                }
                Log.d("Entered:", String.valueOf(pos_month));
                MonthSpinner.setSelection(pos_month-1);
                setTv_year.setText(String.valueOf(pos_year));
                Fragment fragment = new FragmentIntro();
                Bundle bundle = new Bundle();
                if (fragment_type.equalsIgnoreCase("transaction")) {
                    fragment = new FragmentTrans();
                    if (bundle != null) {
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (fragment_type.equalsIgnoreCase("budget")) {
                    if (trigger == true) {
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
                    if (bundle != null) {
                        bundle.putStringArrayList("budget", budgetlist);
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (fragment_type.equalsIgnoreCase("overview")) {
                    fragment = new FragmentOverview();
                    if (bundle != null) {
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (fragment_type.equalsIgnoreCase("analyze")) {
                    if (bundle != null) {
                        bundle.putStringArrayList("budget", budgetlist);
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.FragOutput, fragment);
                transaction.commitAllowingStateLoss();

            }
        });
        ImageButton btn_next = findViewById(R.id.button_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos_month == 12){
                    pos_year += 1;
                    pos_month = 1;
                }
                else{
                    pos_month += 1;
                }
                Log.d("Entered:", String.valueOf(pos_month));
                MonthSpinner.setSelection(pos_month-1);
                setTv_year.setText(String.valueOf(pos_year));
                Fragment fragment = new FragmentIntro();
                Bundle bundle = new Bundle();
                if (fragment_type.equalsIgnoreCase("transaction")) {
                    fragment = new FragmentTrans();
                    if (bundle != null) {
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (fragment_type.equalsIgnoreCase("budget")) {
                    if (trigger == true) {
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
                    if (bundle != null) {
                        bundle.putStringArrayList("budget", budgetlist);
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (fragment_type.equalsIgnoreCase("overview")) {
                    fragment = new FragmentOverview();
                    if (bundle != null) {
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (fragment_type.equalsIgnoreCase("analyze")) {
                    if (bundle != null) {
                        bundle.putStringArrayList("budget", budgetlist);
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.FragOutput, fragment);
                transaction.commitAllowingStateLoss();
            }

        });

        String[] type_of_months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December","All"};
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, type_of_months);
        MonthSpinner.setAdapter(adapterMonth);
        MonthSpinner.setSelection(pos_month-1);
        setTv_year.setText(String.valueOf(pos_year));
        MonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MonthSpinner", "Main:Enter here "+position);
                month_position = position + 1;
                if(month_position != 13){
                    pos_month = month_position;
                }
                Fragment fragment = new FragmentIntro();
                Bundle bundle = new Bundle();
                if (fragment_type.equalsIgnoreCase("transaction")) {
                    fragment = new FragmentTrans();
                    if (bundle != null) {
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (fragment_type.equalsIgnoreCase("budget")) {
                    if (trigger == true) {
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
                    if (bundle != null) {
                        bundle.putStringArrayList("budget", budgetlist);
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (fragment_type.equalsIgnoreCase("overview")) {
                    fragment = new FragmentOverview();
                    if (bundle != null) {
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (fragment_type.equalsIgnoreCase("analyze")) {
                    if (bundle != null) {
                        bundle.putStringArrayList("budget", budgetlist);
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.FragOutput, fragment);
                transaction.commitAllowingStateLoss();
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
                    fragment_type = "transaction";
                    fragment = new FragmentTrans();
                    if(bundle != null) {
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (view == findViewById(R.id.budget)) {
                    fragment_type = "budget";
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
                    fragment_type = "analyze";
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
                    fragment_type = "overview";
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

        Button budget = (Button) findViewById(R.id.budget);
        budget.setOnClickListener(listener);
        Button transac = (Button) findViewById(R.id.transaction);
        transac.setOnClickListener(listener);
        Button overview = findViewById(R.id.overview);
        overview.setOnClickListener(listener);

        Button analyze = (Button) findViewById(R.id.analyze);
        analyze.setOnClickListener(listener);

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Main", "qer" + requestCode);
        Fragment fragment = new FragmentIntro();
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                this.arraylist = data.getStringArrayListExtra("array");
                Log.d("Array", this.arraylist.toString());
                Bundle bundle = new Bundle();
                if (fragment_type.equalsIgnoreCase("transaction")) {
                    fragment = new FragmentTrans();
                    if (bundle != null) {
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (fragment_type.equalsIgnoreCase("budget")) {
                    if (trigger == true) {
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
                    if (bundle != null) {
                        bundle.putStringArrayList("budget", budgetlist);
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (fragment_type.equalsIgnoreCase("overview")) {
                    fragment = new FragmentOverview();
                    if (bundle != null) {
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                } else if (fragment_type.equalsIgnoreCase("analyze")) {
                    if (bundle != null) {
                        bundle.putStringArrayList("budget", budgetlist);
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.FragOutput, fragment);
                transaction.commitAllowingStateLoss();

            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                this.budgetlist = data.getStringArrayListExtra("salary");
                Log.d("Salary", this.budgetlist.toString());
                Bundle bundle = new Bundle();
                if (fragment_type.equalsIgnoreCase("budget")) {
                    if (trigger == true) {
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
                    if (bundle != null) {
                        bundle.putStringArrayList("budget", budgetlist);
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.FragOutput, fragment);
                    transaction.commitAllowingStateLoss();
                }
            }
        }
        if (requestCode == 3) {
            Log.d("M3", "in ma?");
            if (resultCode == RESULT_OK) {
                this.arraylist = data.getStringArrayListExtra("newarray");
                Log.d("New array", arraylist.toString());

                Bundle bundle = new Bundle();
                if (fragment_type.equalsIgnoreCase("transaction")) {
                    fragment = new FragmentTrans();
                    if (bundle != null) {
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.FragOutput, fragment);
                    transaction.commitAllowingStateLoss();
                }
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
                case R.id.action_editbudget:
                    Intent intent = new Intent(getApplicationContext(), AddBudget.class);
                    intent.putExtra("salary", budgetlist);
                    startActivityForResult(intent, 2);
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


