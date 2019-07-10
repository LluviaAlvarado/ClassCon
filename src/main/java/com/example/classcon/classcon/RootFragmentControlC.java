package com.example.classcon.classcon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lluvia on 28/3/2017.
 */

public class RootFragmentControlC extends Fragment {
    static Seccion secc;
    public RootFragmentControlC(){
    }
    public static RootFragmentControlC newInstance(Seccion s) {
        secc = s;
        return new RootFragmentControlC();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.root_fragment, container, false);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_frame, FragmentControlC.newInstance(secc));
        transaction.commit();

        return view;
    }
}
