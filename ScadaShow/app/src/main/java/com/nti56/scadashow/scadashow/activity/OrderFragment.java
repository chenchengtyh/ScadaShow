package com.nti56.scadashow.scadashow.activity;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.utils.LinkWebService;

import uk.co.imallan.jellyrefresh.JellyRefreshLayout;
import uk.co.imallan.jellyrefresh.PullToRefreshLayout;

import static com.nti56.scadashow.scadashow.R.id.typeinfoGroupOrder_bt1;

/**
 * Created by chencheng on 2017/7/27.
 */
public class OrderFragment extends Fragment {

    private String data;
    //private Button order_bt1;
    LinkWebService linkWebService;
    private RadioGroup radioGroupOrder;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private JellyRefreshLayout mJellyLayout;
    private RadioButton typeinfoGroupOrder_bt1;
    private RadioButton typeinfoGroupOrder_bt2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container,
                false);

        typeinfoGroupOrder_bt1 = (RadioButton) view.findViewById(R.id.typeinfoGroupOrder_bt1);
        typeinfoGroupOrder_bt2 = (RadioButton) view.findViewById(R.id.typeinfoGroupOrder_bt2);

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        NowOrderFragment no = new NowOrderFragment();
        Bundle nof = new Bundle();
        nof.putString("PageData", "NowOrderFragment");
        no.setArguments(nof);
        ft.replace(R.id.frame_container_order, no, "NowOrderFragment");
        ft.commit();

        radioGroupOrder = (RadioGroup) view.findViewById(R.id.radioGroupOrder);
        radioGroupOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.typeinfoGroupOrder_bt1:
                        //typeinfoGroupOrder_bt1.setBackgroundResource(R.drawable.select_up_line);
                        //typeinfoGroupOrder_bt2.setBackgroundResource(R.drawable.unselect_up_line);
                        ft = fm.beginTransaction();
                        HisOrderFragment ho = new HisOrderFragment();
                        Bundle hof = new Bundle();
                        hof.putString("PageData", "HisOrderFragment");
                        ho.setArguments(hof);
                        ft.replace(R.id.frame_container_order, ho, "HisOrderFragment");
                        ft.commit();
                        break;
                    case R.id.typeinfoGroupOrder_bt2:
                        //typeinfoGroupOrder_bt1.setBackgroundResource(R.drawable.unselect_up_line);
                        //typeinfoGroupOrder_bt2.setBackgroundResource(R.drawable.select_up_line);
                        ft = fm.beginTransaction();
                        NowOrderFragment no = new NowOrderFragment();
                        Bundle nof = new Bundle();
                        nof.putString("PageData", "NowOrderFragment");
                        no.setArguments(nof);
                        ft.replace(R.id.frame_container_order, no, "NowOrderFragment");
                        ft.commit();
                        break;
                    default:
                        break;
                }
            }
        });

        linkWebService = new LinkWebService();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        data = getArguments().getString("PageData");
    }
}
