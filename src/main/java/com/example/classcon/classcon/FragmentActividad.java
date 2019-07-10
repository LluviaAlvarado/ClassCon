package com.example.classcon.classcon;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Lluvia on 12/5/2017.
 */

public class FragmentActividad extends Fragment{
    static Seccion secc;
    static Actividad act;
    adminBD bd;
    private static SimpleDateFormat dateFormatForBD = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());
    public FragmentActividad() {}

    public static FragmentActividad newInstance(Seccion s, Actividad a) {
        secc = s;
        act = a;
        return new FragmentActividad();
    }

    public void recrear(){
            this.getActivity().recreate();
        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_actividad, container, false);
        bd = adminBD.getInstance(this.getActivity().getApplicationContext());

        TextView nomActividad = (TextView) rootView.findViewById(R.id.nombreActividad);
        String nA = "Actividad: "+act.getNombre();
        nomActividad.setText(nA);
        TextView descAct = (TextView) rootView.findViewById(R.id.descripcionAct);
        nA = "Descripción: "+act.getDescrp();
        descAct.setText(nA);
        TextView tipoAct = (TextView) rootView.findViewById(R.id.tipoActividad);
        nA = "Tipo: "+act.getTipo();
        tipoAct.setText(nA);
        TextView ptAct = (TextView) rootView.findViewById(R.id.puntActividad);
        nA = "Puntaje: "+Double.toString(act.getPt());
        ptAct.setText(nA);
        LinearLayout listaTemas = (LinearLayout) rootView.findViewById(R.id.layoutTAct);
        for (int i = 0; i < act.getTemas().size(); i++){
            TextView tema = new TextView(this.getActivity());
            String tx = "-"+act.getTemas().get(i).getNombre();
            tema.setText(tx);
            tema.setTextSize(20);
            listaTemas.addView(tema);
        }
        TextView fechaAct = (TextView) rootView.findViewById(R.id.fechaActividad);
        nA = "Fecha: "+act.getFecha();
        fechaAct.setText(nA);
        ArrayList<Alumno> alumnos = bd.dameAlumnosSeccion(secc);
        ArrayList<String> nom = new ArrayList<>();
        for (int i = 0; i < alumnos.size(); i++){
            nom.add(alumnos.get(i).getNombre());
        }
        MiListView listaAlumnos = (MiListView) rootView.findViewById(R.id.listaAlumnosEv);
        final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this.getContext(), alumnos, nom);
        listaAlumnos.setAdapter(adapter);
        return rootView;
    }
    private class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final ArrayList<Alumno> values;

        public MySimpleArrayAdapter(Context context, ArrayList<Alumno> values, ArrayList<String> nom) {
            super(context, -1, nom);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.row_ev_al, parent, false);
            final TextView textView = (TextView) rowView.findViewById(R.id.nombreAlumno);
            final EditText editText = (EditText) rowView.findViewById(R.id.ptActividad);
            textView.setText(values.get(position).getNombre());
            editText.setText("0.0");
            return rowView;
        }
    }
}
