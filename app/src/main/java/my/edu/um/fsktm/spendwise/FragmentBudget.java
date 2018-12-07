package my.edu.um.fsktm.spendwise;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentBudget extends Fragment implements AdapterView.OnItemClickListener {
    ArrayList al;
    ArrayList<String> budgetlist;
    ListView list;
    String date[];
    String transaction_type[];
    String category[];
    String amount[];
    String notes[];
    String picture[];
    int [] plan_percentage;
    TextView editSalary;
    double salary;
    Integer[] imgid;

    HashMap<String, String> map = new HashMap<>();




    public FragmentBudget(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        if(getArguments() != null){
            al = getArguments().getStringArrayList("array");
            budgetlist = getArguments().getStringArrayList("budget");

        }
        else{
            al = new ArrayList();
        }
        Log.d("Check", String.valueOf(budgetlist));
        processBudgetList();
        View v = inflater.inflate(R.layout.fragment_budget, container, false);
        editSalary = (TextView) v.findViewById(R.id.tv_salary);
        editSalary.setText(String.format("Salary: RM %.2f",this.salary));
        return v;



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
        for (int i = 0; i < al.size(); i++){
            translist[i] = al.get(i).toString().split(",");
            date[i] = translist[i][0];
            transaction_type[i] = translist[i][1];
            category[i] = translist[i][2];
            amount[i] = translist[i][3];
            notes[i] = translist[i][4];
            picture[i] = translist[i][5];
        }
        for(int i = 0; i < category.length; i++){
            if(transaction_type[i].equalsIgnoreCase("Income")){

            }
            else{
                if(map.containsKey(category[i])){
                    double value = Integer.parseInt(map.get(category[i]));
                    value += Double.parseDouble(amount[i]);
                    map.put(category[i], String.valueOf(value));
                }
                else{
                    map.put(category[i], amount[i]);
                }
            }


        }
        String final_category[] = new String[map.size()];
        int use_percentage[] = new int[map.size()];
        String temp_percentage[] = new String[map.size()];
        int j = 0;
        for(Map.Entry m: map.entrySet()){
            Log.d("HashMap",m.getKey()+" "+m.getValue());
            final_category[j] = (String) m.getKey();
            temp_percentage[j] = (String) m.getValue();
            j++;
        }
        for(int i = 0; i < final_category.length; i++){
            Log.d("Final: ", final_category[i] + " and "+ use_percentage[i]);
            double temp = Double.parseDouble(temp_percentage[i]);
            temp = (temp / salary) * 100;
            use_percentage[i] = (int) temp;
        }

        imgid = new Integer[final_category.length];
        for(int i = 0;i < final_category.length; i++){
            if(final_category[i].equalsIgnoreCase("Clothes")){
                imgid[i] = R.drawable.clothes;
            }
            else if(final_category[i].equalsIgnoreCase("Food")){
                imgid[i] = R.drawable.food;
            }
            else if(final_category[i].equalsIgnoreCase("Transport")){
                imgid[i] = R.drawable.transport;
            }
            else if(final_category[i].equalsIgnoreCase("Entertainment")){
                imgid[i] = R.drawable.entertainment;
            }
            else if(final_category[i].equalsIgnoreCase("Others")){
                imgid[i] = R.drawable.others;
            }
            else{
                imgid[i] = R.drawable.ic_launcher_background;
            }
        }

        BudgetRecordAdapter adapter = new BudgetRecordAdapter(getActivity(), final_category, imgid, use_percentage, plan_percentage);
        list = (ListView) getActivity().findViewById(R.id.list);
        list.setOnItemClickListener(this);
        list.setAdapter(adapter);



    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = amount[+position];
        Log.d("Position", String.valueOf(position));
        Toast.makeText(getActivity(), selectedItem, Toast.LENGTH_SHORT).show();
    }

    public void processBudgetList(){
        plan_percentage = new int[budgetlist.size()-1];
        String temp = budgetlist.get(0).toString();
        Log.d("Temp", temp);
        this.salary = Double.parseDouble(temp);
        Log.d("Salary", String.valueOf(this.salary));
        for(int i = 0; i < budgetlist.size()-1 ; i++){
            plan_percentage[i] = Integer.parseInt(budgetlist.get(i+1).toString()) ;
            Log.d("Plan", String.valueOf(plan_percentage[i]));
        }
    }
}
