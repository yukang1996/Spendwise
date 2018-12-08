package my.edu.um.fsktm.spendwise;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentIntro extends Fragment {

    public FragmentIntro(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){



        View v = inflater.inflate(R.layout.fragment_intro, container, false);

        return v;



    }
}
