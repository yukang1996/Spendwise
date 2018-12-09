package my.edu.um.fsktm.spendwise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FragmentTrans extends Fragment implements AdapterView.OnItemClickListener {
    ArrayList al;
    ListView list;
    String date[];
    String transaction_type[];
    String category[];
    String amount[];
    String notes[];
    String picture[];
    Integer[] imgid;
    boolean allowRefresh = false;
    String new_amount[];
    String new_transaction_type[];
    Integer new_img_id[];
    String new_date[];
    TransactionRecordAdapter adapter;
    private boolean trans_trigger = false;





    public FragmentTrans(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if(getArguments() != null){
            al = getArguments().getStringArrayList("array");
        }
        else{
            al = new ArrayList();
        }
        Log.d("Array", al.toString());

        Spinner month_spinner = getActivity().findViewById(R.id.tb_spinner);
        month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MonthSpinner", "Enter here "+position);
                MainActivity.month_position = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        String[][] translist = new String[al.size()][];
        this.date = new String[al.size()];
        this.transaction_type = new String[al.size()];
        this.category = new String[al.size()];
        this.amount = new String[al.size()];
        this.notes = new String[al.size()];
        this.picture = new String[al.size()];
        Log.d("TL", String.valueOf(al.size()));
        for (int i = 0; i < al.size(); i++){
            Log.d("TL2", al.get(i).toString());
            translist[i] = al.get(i).toString().split(",");
            date[i] = translist[i][0];
            transaction_type[i] = translist[i][1];
            category[i] = translist[i][2];
            amount[i] = translist[i][3];
            notes[i] = translist[i][4];
            picture[i] = translist[i][5];
        }
        ArrayList<Integer> temp_pos = new ArrayList<>();
        for (int i = 0; i < date.length; i++){
            if(MainActivity.month_position == 13){
                temp_pos.add(i);
            }
            else {
                String[] line = date[i].split("-");
                Log.d("Compare", line[1]+" vs " + MainActivity.month_position);
                if(Integer.parseInt(line[1]) == MainActivity.month_position){
                    temp_pos.add(i);
                }
            }


        }
        Log.d("FFFF", temp_pos.toString());

        imgid = new Integer[category.length];
        for(int i = 0;i < category.length; i++){
            if(category[i].equalsIgnoreCase("Salary")){
                imgid[i] = R.drawable.salary;
            }
            else if(category[i].equalsIgnoreCase("Clothes")){
                imgid[i] = R.drawable.clothes;
            }
            else if(category[i].equalsIgnoreCase("Food")){
                imgid[i] = R.drawable.food;
            }
            else if(category[i].equalsIgnoreCase("Transport")){
                imgid[i] = R.drawable.transport;
            }
            else if(category[i].equalsIgnoreCase("Entertainment")){
                imgid[i] = R.drawable.entertainment;
            }
            else if(category[i].equalsIgnoreCase("Others")){
                imgid[i] = R.drawable.others;
            }
            else{
                imgid[i] = R.drawable.ic_launcher_background;
            }
        }
        new_amount = new String[temp_pos.size()];
        new_date = new String[temp_pos.size()];
        new_img_id = new Integer[temp_pos.size()];
        new_transaction_type = new String[temp_pos.size()];
        for (int i = 0; i < temp_pos.size(); i++){
            new_amount[i] = amount[temp_pos.get(i)];
            new_date[i] = date[temp_pos.get(i)];
            new_img_id[i] = imgid[temp_pos.get(i)];
            new_transaction_type[i] = transaction_type[temp_pos.get(i)];

        }




        adapter = new TransactionRecordAdapter(getActivity(), new_amount, new_img_id, new_transaction_type, new_date);
        trans_trigger = true;
        list = (ListView) getActivity().findViewById(R.id.list);
        list.setOnItemClickListener(this);
        list.setAdapter(adapter);
        list.setItemsCanFocus(true);


        Log.d("Return", "return here?");
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = String.valueOf(position);
        Log.d("Selecting", selectedItem);
        Toast.makeText(getActivity(), selectedItem, Toast.LENGTH_SHORT).show();
        String temp_pos = String.valueOf(position);
        Intent intent = new Intent(getContext(), UpdateTransaction.class);
        this.allowRefresh = true;
        Log.d("Changed", String.valueOf(allowRefresh));
        if(transaction_type[position].equalsIgnoreCase("Income")){
            Log.d("INcome","incomeee");
            intent.putExtra("array", al);
            intent.putExtra("t_type","Income");
            intent.putExtra("position", temp_pos);
            getActivity().startActivityForResult(intent, 3);

        }
        else if(transaction_type[position].equalsIgnoreCase("Expense")){
            Log.d("Expense","expenseeee");
            intent.putExtra("array", al);
            intent.putExtra("t_type","Expense");
            intent.putExtra("position", temp_pos);
            getActivity().startActivityForResult(intent, 3);
        }


    }








}
