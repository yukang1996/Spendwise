package my.edu.um.fsktm.spendwise;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public FragmentOverview() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if(getArguments() != null){
                al = getArguments().getStringArrayList("array");
            }
            else {
                al = new ArrayList();
            }
        return inflater.inflate(R.layout.fragment_overview, container, false);
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
            if(map.containsKey(category[i])){
                double value = Integer.parseInt(map.get(category[i]));
                value += Double.parseDouble(amount[i]);
                map.put(category[i], String.valueOf(value));
            }
            else{
                map.put(category[i], amount[i]);
            }

        }
        String final_category[] = new String[map.size()];
        int percentage[] = new int[map.size()];
        String temp_percentage[] = new String[map.size()];
        int j = 0;
        for(Map.Entry m: map.entrySet()){
            Log.d("HashMap",m.getKey()+" "+m.getValue());
            final_category[j] = (String) m.getKey();
            temp_percentage[j] = (String) m.getValue();
            j++;
        }




        OverviewRecordAdapter adapter = new OverviewRecordAdapter(getActivity(), final_category, temp_percentage);
        list = (ListView) getActivity().findViewById(R.id.list);
        list.setAdapter(adapter);



    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}



