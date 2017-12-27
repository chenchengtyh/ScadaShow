package com.nti56.scadashow.scadashow.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.bean.Alarm;
import com.nti56.scadashow.scadashow.interfaces.ScadaApplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by chencheng on 2017/8/8.
 */
public class FaultFragment extends Fragment {

    Spinner spinner;
    List<String> list;
    ScadaApplication sapps;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        sapps = (ScadaApplication) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sumbit, container,
                false);
        LinkedList<Alarm> alarms = sapps.getData();
        list = new ArrayList<>();
        spinner = (Spinner) view.findViewById(R.id.spinner);
        list.add("历史处理方法");
        for(int i=0;i<alarms.size();i++){
            list.add(alarms.get(i).GetMETHOD());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.myspinner,list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return view;
    }
}
