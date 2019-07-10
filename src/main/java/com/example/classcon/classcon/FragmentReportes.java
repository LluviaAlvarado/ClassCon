package com.example.classcon.classcon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Lluvia on 28/3/2017.
 */

public class FragmentReportes extends Fragment {
    static Seccion secc;
    public FragmentReportes() {

    }

    public static FragmentReportes newInstance(Seccion s) {
        secc = s;
        return new FragmentReportes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reportes, container, false);
        Spinner spinner = (Spinner) rootView.findViewById(R.id.tiposReporte);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.reportesDummy, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return rootView;
    }
}
