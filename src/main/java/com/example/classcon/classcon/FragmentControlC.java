package com.example.classcon.classcon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by Lluvia on 28/3/2017.
 */

public class FragmentControlC extends Fragment {
    static Seccion secc;
    public FragmentControlC() {
    }

    public static FragmentControlC newInstance(Seccion s) {
        secc = s;
        return new FragmentControlC();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_controlc, container, false);
        Button alumnos = (Button) rootView.findViewById(R.id.bAlumnosC);
        alumnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.root_frame, FragmentManAlumnos.newInstance(secc));
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        Button encuadre = (Button) rootView.findViewById(R.id.bEncuadreC);
        encuadre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.root_frame, FragmentEncuadre.newInstance(secc));
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });
        Button estadisticas = (Button) rootView.findViewById(R.id.bEstadisticasC);
        estadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.root_frame, FragmentEstadisticas.newInstance(secc));
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return rootView;
    }
}
