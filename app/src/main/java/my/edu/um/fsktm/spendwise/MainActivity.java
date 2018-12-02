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
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> arraylist;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseFunction databaseFunction = new DatabaseFunction();
        this.arraylist = databaseFunction.readFromFile("transaction.txt", this);
        checkArrayList();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.TB);
        myToolbar.setTitle("October");

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
                    fragment = new FragmentBudget();
                    if(bundle != null){
                        bundle.putStringArrayList("array", arraylist);
                    }
                    fragment.setArguments(bundle);

                } else if (view == findViewById(R.id.analyze)) {

                } else {
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

    }
        public void onActivityResult(int requestCode, int resultCode, Intent data){
            if(requestCode == 1){
                if(resultCode == RESULT_OK){
                    this.arraylist = data.getStringArrayListExtra("array");
                    Log.d("Array", this.arraylist.toString());
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

        public void checkArrayList(){
            if(this.arraylist == null){
                this.arraylist = new ArrayList<>();
            }

        }



    }


