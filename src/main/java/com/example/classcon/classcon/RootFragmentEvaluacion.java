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

public class RootFragmentEvaluacion extends Fragment {
    static Seccion secc;
    public RootFragmentEvaluacion(){
    }
    public static RootFragmentEvaluacion newInstance(Seccion s) {
        secc = s;
        return new RootFragmentEvaluacion();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.root_fragment_ev, container, false);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_frameE, FragmentEvaluacion.newInstance(secc));
        transaction.commit();
        return view;
    }
}