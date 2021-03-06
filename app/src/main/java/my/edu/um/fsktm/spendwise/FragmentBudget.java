package my.edu.um.fsktm.spendwise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
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
    int [] plan_percentage;
    int [] final_plan_percentage;
    TextView editSalary;
    double salary;
    Integer[] imgid;
    String new_amount[];
    String new_transaction_type[];
    Integer new_img_id[];
    String new_date[];

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
        for (int i = 0; i < al.size(); i++){
            translist[i] = al.get(i).toString().split(",");
            date[i] = translist[i][0];
            transaction_type[i] = translist[i][1];
            category[i] = translist[i][2];
            amount[i] = translist[i][3];
            notes[i] = translist[i][4];
        }

//        ArrayList<Integer> temp_pos = new ArrayList<>();
//        for (int i = 0; i < date.length; i++) {
//            if(MainActivity.month_position == 13){
//                temp_pos.add(i);
//            }
//            else{
//                String[] line = date[i].split("-");
//                Log.d("Compare", line[1] + " vs " + MainActivity.month_position);
//                if (Integer.parseInt(line[1]) == MainActivity.month_position) {
//                    temp_pos.add(i);
//                }
//            }
//
//        }

        for(int i = 0; i < category.length; i++){
            if(transaction_type[i].equalsIgnoreCase("Income")){

            }
            else{
                if(MainActivity.month_position == 13){
                    if(map.containsKey(category[i])){
                        double value = Integer.parseInt(map.get(category[i]));
                        value += Double.parseDouble(amount[i]);
                        map.put(category[i], String.valueOf(value));
                    }
                    else{
                        map.put(category[i], amount[i]);
                    }
                }
                else{
                    String[] line = date[i].split("-");
                    Log.d("Budget compareYear", line[2] + " vs " + MainActivity.pos_year);
                    if(Integer.parseInt(line[2]) == MainActivity.pos_year){
                        Log.d("Budget compare", line[1] + " vs " + MainActivity.pos_month);
                        if (Integer.parseInt(line[1]) == MainActivity.pos_month) {
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


                }

            }


        }
        //add everything into hashmap
        String []budget_category = {"Clothes", "Food", "Transport", "Entertainment", "Others"};
        for (int i = 0; i < budget_category.length; i++){
            if(!map.containsKey(budget_category[i])){
                map.put(budget_category[i], "0");
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
        double value[] = new double[final_category.length];
        for(int i = 0; i < final_category.length; i++){
            Log.d("Final: ", final_category[i] + " and "+ use_percentage[i]);
            double temp = Double.parseDouble(temp_percentage[i]);
            value[i] = temp;
            temp = (temp / salary) * 100;
            use_percentage[i] = (int) temp;
        }

        imgid = new Integer[final_category.length];
        double value_budget[] = new double[plan_percentage.length];
        final_plan_percentage = new int[plan_percentage.length];
        for(int i = 0;i < final_category.length; i++){
            if(final_category[i].equalsIgnoreCase("Clothes")){
                imgid[i] = R.drawable.clothes;
                value_budget[i] = salary * plan_percentage[0]/100;
                final_plan_percentage[i] = plan_percentage[0];
            }
            else if(final_category[i].equalsIgnoreCase("Food")){
                imgid[i] = R.drawable.food;
                value_budget[i] = salary * plan_percentage[1]/100;
                final_plan_percentage[i] = plan_percentage[1];
            }
            else if(final_category[i].equalsIgnoreCase("Transport")){
                imgid[i] = R.drawable.transport;
                value_budget[i] = salary * plan_percentage[2]/100;
                final_plan_percentage[i] = plan_percentage[2];
            }
            else if(final_category[i].equalsIgnoreCase("Entertainment")){
                imgid[i] = R.drawable.entertainment2;
                value_budget[i] = salary * plan_percentage[3]/100;
                final_plan_percentage[i] = plan_percentage[3];
            }
            else if(final_category[i].equalsIgnoreCase("Others")){
                imgid[i] = R.drawable.others;
                value_budget[i] = salary * plan_percentage[4]/100;
                final_plan_percentage[i] = plan_percentage[4];
            }
            else{
                imgid[i] = R.drawable.ic_launcher_background;
                value_budget[i] = 0;
                final_plan_percentage[i] = 0;
            }
        }





        String value_to_string[] = new String[value.length];
        for (int i = 0; i <value.length; i++){

            String temp = String.format("%.2f",value[i]);
            String temp2 = String.format("%.2f", value_budget[i]);
            value_to_string[i] = "RM" + temp + " / " + temp2;
        }





        BudgetRecordAdapter adapter = new BudgetRecordAdapter(getActivity(), value_to_string, imgid, use_percentage, final_plan_percentage);
        list = (ListView) getActivity().findViewById(R.id.list);
        list.setOnItemClickListener(this);
        list.setAdapter(adapter);

        ImageButton editBudget = (ImageButton) getView().findViewById(R.id.edit_budget);
        editBudget.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddBudget.class);
                intent.putExtra("budget", budgetlist);
                getActivity().startActivityForResult(intent, 2);
                Log.d("Budget", "after that");
            }
        });



    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = amount[+position];
        Log.d("Position", String.valueOf(position));
        Toast.makeText(getActivity(), selectedItem, Toast.LENGTH_SHORT).show();
    }

    public void processBudgetList(){
        Log.d("Budget size", String.valueOf(budgetlist.size()));
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
