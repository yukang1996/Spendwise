package my.edu.um.fsktm.spendwise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.TB);
        myToolbar.setTitle("October");



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
}
