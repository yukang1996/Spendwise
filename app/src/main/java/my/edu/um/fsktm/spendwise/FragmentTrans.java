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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    Integer[] imgid;
    public static int delete_position;
    String new_amount[];
    String new_transaction_type[];
    Integer new_img_id[];
    String new_date[];
    TransactionRecordAdapter adapter;





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
        Log.d("TL", String.valueOf(al.size()));
        for (int i = 0; i < al.size(); i++){
            Log.d("TL2", al.get(i).toString());
            translist[i] = al.get(i).toString().split(",");
            date[i] = translist[i][0];
            transaction_type[i] = translist[i][1];
            category[i] = translist[i][2];
            amount[i] = translist[i][3];
            notes[i] = translist[i][4];
        }
        ArrayList<Integer> temp_pos = new ArrayList<>();
        for (int i = 0; i < date.length; i++){
            if(MainActivity.month_position == 13){
                temp_pos.add(i);
            }
            else{
                String[] line = date[i].split("-");
                Log.d("CompareYear", line[2]+" vs " + MainActivity.pos_year);
                if(Integer.parseInt(line[2]) == MainActivity.pos_year){
                    Log.d("Compare", line[1]+" vs " + MainActivity.pos_month);
                    if(Integer.parseInt(line[1]) == MainActivity.pos_month){
                        temp_pos.add(i);
                    }
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
        list = (ListView) getActivity().findViewById(R.id.list);
        list.setOnItemClickListener(this);
        list.setAdapter(adapter);
        list.setItemsCanFocus(true);


        Log.d("Return", "return here?");
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = String.valueOf(position);
        //confirm the position of want to delete object in the whole list.
        for (int i = 0;i < date.length; i++){
            Log.d("Compare", date[i] +"vs"+new_date[position]);
            if(date[i] == new_date[position]){
                Log.d("Compare2", transaction_type[i] +"vs"+new_transaction_type[position]);
                if(transaction_type[i] == new_transaction_type[position]){
                    Log.d("Compare3", amount[i] +"vs"+new_amount[position]);
                    if(amount[i] == new_amount[position]){
                        delete_position = i;
                        Log.d("Deleteposi: ", String.valueOf(delete_position));
                    }
                }
            }
            Log.d("Finsih", "finisssssss");
        }
        Log.d("Selecting", selectedItem);
        String temp_pos = String.valueOf(position);
        Intent intent = new Intent(getContext(), UpdateTransaction.class);
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
