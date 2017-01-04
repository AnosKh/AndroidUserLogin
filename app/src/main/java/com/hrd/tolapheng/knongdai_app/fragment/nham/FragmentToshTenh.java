package com.hrd.tolapheng.knongdai_app.fragment.nham;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hrd.tolapheng.knongdai_app.R;

public class FragmentToshTenh extends Fragment {

    public FragmentToshTenh(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_fragment_tosh_tenh, container, false);
    }
}
