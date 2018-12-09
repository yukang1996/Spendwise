package my.edu.um.fsktm.spendwise;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.*;

public class FragmentOverview extends Fragment implements AdapterView.OnItemClickListener {
    ListView listViewRecords;
    ArrayList<String> al;
    ListView list;
    String date[];
    String transaction_type[];
    String category[];
    String amount[];
    String notes[];
    String picture[];
    HashMap<String, String> map = new HashMap<>();
    Double income = 0.00, expenses = 0.00, balance = 0.00;
    TextView et_income, et_expenses, et_balance;

    public FragmentOverview() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getArguments() != null){
            al = getArguments().getStringArrayList("array"); }
        else {
            al = new ArrayList();
        }
        View v = inflater.inflate(R.layout.fragment_overview, container, false);
        processInformation();
        calculateIncomenExpense();
        calculateBalance();
        et_income = v.findViewById(R.id.tv_setIncome);
        et_income.setText(String.format("%.2f", income));
        et_expenses = v.findViewById(R.id.tv_setExpense);
        et_expenses.setText(String.format("%.2f", expenses));
        et_balance = v.findViewById(R.id.tv_setBalance);
        et_balance.setText(String.format("%.2f", balance));

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

        return v;
    }





    private void calculateIncomenExpense() {
        for (int i = 0; i < al.size(); i++) {
            if(MainActivity.month_position == 13){
                if (transaction_type[i].equalsIgnoreCase("Income")) {
                    income = income + Double.parseDouble(amount[i]);
                }
                else{
                    expenses = expenses + Double.parseDouble(amount[i]);
                }
            }
            else{
                String []line = date[i].split("-");
                Log.d("Over Compare", line[1]+" vs " + MainActivity.month_position);
                if(Integer.parseInt(line[1]) == MainActivity.month_position){
                    if (transaction_type[i].equalsIgnoreCase("Income")) {
                        income = income + Double.parseDouble(amount[i]);
                    }
                    else{
                        expenses = expenses + Double.parseDouble(amount[i]);
                    }
                }
            }


        }
    }


    private void calculateBalance(){
        balance = income - expenses;
    }

    private void processInformation() {
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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        for(int i = 0; i < category.length; i++){
            if(transaction_type[i].equalsIgnoreCase("Income")){

            }
            else {
                if(MainActivity.month_position == 13){
                    if (map.containsKey(category[i])) {
                        double value = Integer.parseInt(map.get(category[i]));
                        value += Double.parseDouble(amount[i]);
                        map.put(category[i], valueOf(value));
                    } else {
                        map.put(category[i], amount[i]);
                    }
                }
                else{
                    String[] line = date[i].split("-");
                    Log.d("Over2 compare", line[1] + " vs " + MainActivity.month_position);
                    if (Integer.parseInt(line[1]) == MainActivity.month_position) {
                        if (map.containsKey(category[i])) {
                            double value = Integer.parseInt(map.get(category[i]));
                            value += Double.parseDouble(amount[i]);
                            map.put(category[i], valueOf(value));
                        } else {
                            map.put(category[i], amount[i]);
                        }
                    }

                }

            }

        }
        String final_category[] = new String[map.size()];
        String temp_amount[] = new String[map.size()];
        int j = 0;
        for(Map.Entry m: map.entrySet()){
            Log.d("HashMap",m.getKey()+" "+m.getValue());
            final_category[j] = (String) m.getKey();
            temp_amount[j] = (String) m.getValue();
            j++;
        }




        OverviewRecordAdapter adapter = new OverviewRecordAdapter(getActivity(), final_category, temp_amount);
        list = (ListView) getActivity().findViewById(R.id.list);
        list.setAdapter(adapter);



    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}



