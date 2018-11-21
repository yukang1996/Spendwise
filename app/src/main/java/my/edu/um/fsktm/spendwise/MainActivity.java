package my.edu.um.fsktm.spendwise;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.TB);
        myToolbar.setTitle("October");
        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if(view == findViewById(R.id.transaction)){
//                    fragment = new F
                }
                else if(view == findViewById(R.id.budget)){
                    fragment = new FragmentBudget();
                }
                else if(view == findViewById(R.id.analyze)){

                }
                else{
                    fragment = new FragmentOverview();
                }
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.FragOutput, fragment);
                transaction.commit();
            }
        };

        Button budget = (Button) findViewById(R.id.budget);
        budget.setOnClickListener(listener);
        Button overview = (Button) findViewById(R.id.overview);
        overview.setOnClickListener(listener);



    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch(item.getItemId()){
            case R.id.action_add:
                return true;
            case R.id.action_category:
                return true;
            case R.id.action_option:
                return true;
            case R.id.action_account:
                return true;
            default: return super.onOptionsItemSelected(item);

        }
    }

    public void Budget(View view) {
        Intent budgetIntent = new Intent(this, FragmentBudget.class);
        startActivity(budgetIntent);
    }
}
