package com.example.classcon.classcon;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lluvia on 28/3/2017.
 */

public class FragmentEstadisticas extends Fragment {
    static Seccion secc;
    public FragmentEstadisticas() {

    }

    public static FragmentEstadisticas newInstance(Seccion s) {
        secc = s;
        return new FragmentEstadisticas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_estadisticas, container, false);
        return rootView;
    }
}
