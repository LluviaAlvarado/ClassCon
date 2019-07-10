package com.example.classcon.classcon;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.example.classcon.classcon.LoginActivity.usuarioActual;

/**
 * Created by Lluvia on 28/3/2017.
 */

public class FragmentEncuadre extends Fragment {
    static Seccion secc;
    MiListView listaC;
    adminBD bd;
    public FragmentEncuadre() {

    }
    public static FragmentEncuadre newInstance(Seccion s) {
        secc = s;
        return new FragmentEncuadre();
    }
    public void recrear(){
        this.getActivity().recreate();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_encuadre, container, false);
        bd = adminBD.getInstance(this.getActivity().getApplicationContext());
        ArrayList<Criterio> criterios = bd.getCriterios(secc);
        ArrayList<String> nom = new ArrayList<>();
        Double tot = 0.0;
        for(int i = 0; i < criterios.size(); i++){
            nom.add(criterios.get(i).getNombre());
            tot+=criterios.get(i).getPorcentaje();
        }
        MiListView listaCrit = (MiListView) rootView.findViewById(R.id.listaCriterios);
        final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this.getContext(), criterios,nom);
        listaCrit.setAdapter(adapter);
        listaC = listaCrit;
        TextView total = (TextView) rootView.findViewById(R.id.ptTotal);
        total.setText("Puntaje Total: " + tot.toString());
        android.support.design.widget.FloatingActionButton agregar = (android.support.design.widget.FloatingActionButton) rootView.findViewById(R.id.agrCrit);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarCriterio();
            }
        });
        return rootView;
    }
    void agregarCriterio(){
        final Dialog agCrit = new Dialog(this.getActivity());
        agCrit.setContentView(R.layout.dialog_agregar_criterio);
        agCrit.setTitle("Agregar Criterio");
        Button aceptar = (Button) agCrit.findViewById(R.id.agrActEnc);
        final EditText nomC = (EditText) agCrit.findViewById(R.id.nomNCrit);
        final EditText ptC = (EditText) agCrit.findViewById(R.id.ptNCrit);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double pt = Double.parseDouble(ptC.getText().toString());
                Criterio crit = new Criterio(nomC.getText().toString(),pt);
                if(!bd.agregarCriterio(secc, crit)){
                    new AlertDialog.Builder(FragmentEncuadre.this.getActivity())
                            .setTitle("Error")
                            .setMessage("No se pudo agregar el criterio")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    new AlertDialog.Builder(FragmentEncuadre.this.getActivity())
                            .setTitle("Exito")
                            .setMessage("Criterio agregado!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    recrear();
                                    agCrit.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();

                }
            }
        });
        agCrit.show();
    }

    void eliminarCriterio(View v){
        TextView nom = (TextView) v.findViewById(R.id.nomCrit);
        EditText pt = (EditText) v.findViewById(R.id.ptCrit);
        Criterio cr = new Criterio(nom.getText().toString(),Double.parseDouble(pt.getText().toString()));
        if(!bd.eliminarCriterio(secc, cr)){
            new AlertDialog.Builder(FragmentEncuadre.this.getActivity())
                    .setTitle("Error")
                    .setMessage("No se pudo eliminar el criterio")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            recrear();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else{
            new AlertDialog.Builder(FragmentEncuadre.this.getActivity())
                    .setTitle("Exito")
                    .setMessage("Criterio eliminado!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            recrear();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();

        }
    }

    private class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final ArrayList<Criterio> values;

        public MySimpleArrayAdapter(Context context, ArrayList<Criterio> values, ArrayList<String> nom) {
            super(context, -1, nom);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.row_criterio, parent, false);
            final TextView textView = (TextView) rowView.findViewById(R.id.nomCrit);
            final EditText editText = (EditText) rowView.findViewById(R.id.ptCrit);
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                    if (id == R.id.cambiarPt || id == EditorInfo.IME_NULL) {
                            Criterio cr = new Criterio(textView.getText().toString(),Double.parseDouble(editText.getText().toString()));
                            if(bd.modificarCriterio(secc,cr)){
                                new AlertDialog.Builder(FragmentEncuadre.this.getActivity())
                                        .setTitle("Exito")
                                        .setMessage("Criterio modificado!")
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                recrear();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .show();
                            }else{
                                new AlertDialog.Builder(FragmentEncuadre.this.getActivity())
                                        .setTitle("Error")
                                        .setMessage("No se pudo modificar el criterio")
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                recrear();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        return true;
                    }
                    return false;
                }
            });
            Button eliminar = (Button) rowView.findViewById(R.id.eliminarCrit);
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eliminarCriterio(rowView);
                }
            });
            textView.setText(values.get(position).getNombre());
            editText.setText(Double.toString(values.get(position).getPorcentaje()));
            return rowView;
        }
    }
}


