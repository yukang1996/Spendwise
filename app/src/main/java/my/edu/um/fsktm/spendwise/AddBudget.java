package my.edu.um.fsktm.spendwise;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddBudget extends AppCompatActivity {
    private EditText editTextSalary;
    private SeekBar sb_Clothes, sb_Food, sb_Transport, sb_Entertainment, sb_Others;
    private int per_clothes, per_food, per_transport, per_entertainment, per_others;
    private TextView tv_p_clothes, tv_p_food, tv_p_transport, tv_p_entertainment, tv_p_others, tv_p_total;
    private int total;

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
        tv_p_total = findViewById(R.id.current_total);
        tv_p_clothes = findViewById(R.id.tv_percentage_clothes);
        tv_p_food = findViewById(R.id.tv_percentage_food);
        tv_p_transport = findViewById(R.id.tv_percentage_transport);
        tv_p_entertainment = findViewById(R.id.tv_percentage_entertainment);
        tv_p_others = findViewById(R.id.tv_percentage_others);


        sb_Clothes = findViewById(R.id.seekBarClothes);
        sb_Clothes.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress * 10;
                per_clothes = progress;
                tv_p_clothes.setText(progress+"%");
                calcTotal();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_Food = findViewById(R.id.seekBarFood);
        sb_Food.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress * 10;
                per_food = progress;
                tv_p_food.setText(progress+"%");
                calcTotal();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_Transport = findViewById(R.id.seekBarTransport);
        sb_Transport.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress * 10;
                per_transport = progress;
                tv_p_transport.setText(progress+"%");
                calcTotal();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_Entertainment = findViewById(R.id.seekBarEntertainment);
        sb_Entertainment.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress * 10;
                per_entertainment = progress;
                tv_p_entertainment.setText(progress+"%");
                calcTotal();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_Others = findViewById(R.id.seekBarOthers);
        sb_Others.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress * 10;
                per_others = progress;
                tv_p_others.setText(progress+"%");
                calcTotal();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void saveBudget(View v){
        editTextSalary = findViewById(R.id.editTextSalary);

        String salary = editTextSalary.getText().toString();

        if(salary.isEmpty()){
            editTextSalary.setError("Please enter Salary");
            return;
        }
        total = per_clothes + per_food + per_entertainment + per_transport + per_others;
        if(total > 100){
            Toast.makeText(AddBudget.this,"The total percentage should not exceed 100%",  Toast.LENGTH_SHORT).show();
            return;
        }


        al.clear();
        al.add(salary);
        al.add(String.valueOf(per_clothes));
        al.add(String.valueOf(per_food));
        al.add(String.valueOf(per_transport));
        al.add(String.valueOf(per_entertainment));
        al.add(String.valueOf(per_others));

        final MediaPlayer mp = new MediaPlayer().create(this, R.raw.mario_coin);
        mp.start();

        Log.d("AddBudget_budget", al.toString());



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

    public void calcTotal(){
        total = per_clothes + per_food + per_entertainment + per_transport + per_others;
        tv_p_total.setText("Total Percentage: "+ total + "%");
    }
}
