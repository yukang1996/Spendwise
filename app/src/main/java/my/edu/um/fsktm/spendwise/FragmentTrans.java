package my.edu.um.fsktm.spendwise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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

        TransactionRecordAdapter adapter = new TransactionRecordAdapter(getActivity(), amount, imgid, transaction_type, date);
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
