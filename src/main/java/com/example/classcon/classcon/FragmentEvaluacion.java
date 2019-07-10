package com.example.classcon.classcon;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.classcon.classcon.R.id.descClase;
import static com.example.classcon.classcon.R.id.nombreAct;

/**
 * Created by Lluvia on 28/3/2017.
 */

public class FragmentEvaluacion extends Fragment {
    static Seccion secc;
    adminBD bd;
    private static SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private static SimpleDateFormat dateFormatForBD = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());
    public FragmentEvaluacion() {

    }

    public static FragmentEvaluacion newInstance(Seccion s) {
        secc = s;
        return new FragmentEvaluacion();
    }
    public void recrear(){
        this.getActivity().recreate();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_evaluacion, container, false);
        bd = adminBD.getInstance(this.getActivity().getApplicationContext());
        LinearLayout listaAct = (LinearLayout) rootView.findViewById(R.id.listaActividades);
        final ArrayList<Actividad> actividades = bd.getActividades(secc);
        TextView noAct = (TextView) rootView.findViewById(R.id.noActReg);
        noAct.setVisibility(View.GONE);
        android.support.design.widget.FloatingActionButton agregar = (android.support.design.widget.FloatingActionButton) rootView.findViewById(R.id.agrActividad);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarActividad();
            }
        });
        if(actividades.isEmpty()){
            noAct.setVisibility(View.VISIBLE);
        }else {
            ArrayList<Criterio> criterios = bd.getCriterios(secc);
            //llenando la lista de actividades
            for (int i = 0; i < criterios.size(); i++) {
                TextView tipo = new TextView(this.getActivity());
                tipo.setText("Tipo: " + criterios.get(i).getNombre());
                listaAct.addView(tipo);
                for (int j = 0; j < actividades.size(); j++) {
                    if (actividades.get(j).getTipo().equals(criterios.get(i).getNombre())) {
                        Button act = new Button(this.getActivity());
                        act.setText(actividades.get(j).getNombre());
                        final int finalJ = j;
                        act.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.root_frameE, FragmentActividad.newInstance(secc,actividades.get(finalJ)));
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        });
                        listaAct.addView(act);
                    }
                }
            }
        }

        return rootView;
    }

    public void agregarActividad() {
        final Dialog agAct = new Dialog(this.getActivity());
        agAct.setContentView(R.layout.dialog_agregar_actividad);
        agAct.setTitle("Agregar Actividad");
        Calendar c = Calendar.getInstance();
        final Date[] fechaSeleccionada = new Date[1];
        fechaSeleccionada[0] = c.getTime();
        final CompactCalendarView fecha = (CompactCalendarView) agAct.findViewById(R.id.fechaEvaluacion);
        final TextView mesAnio = (TextView) agAct.findViewById(R.id.mesAnioEvaluacion);
        mesAnio.setText(dateFormatForMonth.format(fecha.getFirstDayOfCurrentMonth()));
        //spinner de tipo act
        final Spinner tipo = (Spinner) agAct.findViewById(R.id.tipoAct);
        ArrayList<String> nomTipos = new ArrayList<>();
        ArrayList<Criterio> tipos = bd.getCriterios(secc);
        for(int i = 0; i < tipos.size(); i++){
            nomTipos.add(tipos.get(i).getNombre());
        }
        if(tipos.size() == 0){
            new AlertDialog.Builder(FragmentEvaluacion.this.getActivity())
                    .setTitle("Error")
                    .setMessage("Porfavor agregue criterios al encuadre en ControlC.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            return;
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        ArrayAdapter<String> adaptertipos = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, nomTipos);
        adaptertipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(adaptertipos);
        //spinners de temas
        final Spinner tema1 = (Spinner) agAct.findViewById(R.id.temasAct);
        final Spinner tema2 = (Spinner) agAct.findViewById(R.id.temasAct2);
        final Spinner tema3 = (Spinner) agAct.findViewById(R.id.temasAct3);
        final Spinner tema4 = (Spinner) agAct.findViewById(R.id.temasAct4);
        final Spinner tema5 = (Spinner) agAct.findViewById(R.id.temasAct5);
        ArrayList<String> nombreTemas = new ArrayList<>();
        nombreTemas.add("---");
        Materia materia = bd.getMateriaI(secc.getIdMat());
        final ArrayList<Tema> temas = bd.getTemasMateria(materia);
        for(int i = 0; i < temas.size(); i++){
            nombreTemas.add(temas.get(i).getNombre());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, nombreTemas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tema1.setAdapter(adapter);
        tema2.setAdapter(adapter);
        tema3.setAdapter(adapter);
        tema4.setAdapter(adapter);
        tema5.setAdapter(adapter);
        tema2.setVisibility(View.GONE);
        tema3.setVisibility(View.GONE);
        tema4.setVisibility(View.GONE);
        tema5.setVisibility(View.GONE);
        //mostrar spinners
        tema1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    tema2.setVisibility(View.VISIBLE);
                }else{
                    tema2.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tema2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    tema3.setVisibility(View.VISIBLE);
                }else{
                    tema3.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tema3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    tema4.setVisibility(View.VISIBLE);
                }else{
                    tema4.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tema4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    tema5.setVisibility(View.VISIBLE);
                }else{
                    tema5.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fecha.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                fechaSeleccionada[0] = dateClicked;
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mesAnio.setText(dateFormatForMonth.format(fecha.getFirstDayOfCurrentMonth()));
            }
        });
        final EditText nombreAct = (EditText) agAct.findViewById(R.id.nombreAct);
        final EditText descAct = (EditText) agAct.findViewById(R.id.descAct);
        Button agregar = (Button) agAct.findViewById(R.id.agregarAct);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tema1.getSelectedItemPosition() == 0){
                    new AlertDialog.Builder(FragmentEvaluacion.this.getActivity())
                            .setTitle("Error")
                            .setMessage("Elije un tema!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else {
                    String fecha = dateFormatForBD.format(fechaSeleccionada[0]);
                    String t = tipo.getSelectedItem().toString();
                    String nombre = nombreAct.getText().toString();
                    String desc = descAct.getText().toString();
                    Double pt = bd.calcularPt(t, secc);
                    ArrayList<Tema> temas = new ArrayList<>();
                    Tema tm = new Tema(secc.getIdMat(),tema1.getSelectedItem().toString(),"");
                    temas.add(tm);
                    if(tema2.getSelectedItemPosition() != 0){
                        if(!tema2.getSelectedItem().toString().equals(tm.getNombre())){
                            tm = new Tema(secc.getIdMat(),tema2.getSelectedItem().toString(),"");
                            temas.add(tm);
                        }
                    }
                    if(tema3.getSelectedItemPosition() != 0){
                        if(!tema3.getSelectedItem().toString().equals(tm.getNombre())&&!tema3.getSelectedItem().toString().equals(temas.get(0).getNombre())){
                            tm = new Tema(secc.getIdMat(),tema3.getSelectedItem().toString(),"");
                            temas.add(tm);
                        }
                    }
                    if(tema4.getSelectedItemPosition() != 0){
                        if(!tema4.getSelectedItem().toString().equals(tm.getNombre())
                                && !tema4.getSelectedItem().toString().equals(temas.get(0).getNombre())
                                && !tema4.getSelectedItem().toString().equals(temas.get(1).getNombre())){
                            tm = new Tema(secc.getIdMat(),tema2.getSelectedItem().toString(),"");
                            temas.add(tm);
                        }
                    }
                    if(tema5.getSelectedItemPosition() != 0){
                        if(!tema5.getSelectedItem().toString().equals(tm.getNombre())
                                && !tema5.getSelectedItem().toString().equals(temas.get(0).getNombre())
                                && !tema5.getSelectedItem().toString().equals(temas.get(1).getNombre())
                                && !tema5.getSelectedItem().toString().equals(temas.get(2).getNombre())){
                            tm = new Tema(secc.getIdMat(),tema2.getSelectedItem().toString(),"");
                            temas.add(tm);
                        }
                    }
                    Actividad act = new Actividad(fecha, t, nombre, desc, pt, temas);
                    if(bd.agregarActividad(secc,act)){
                        new AlertDialog.Builder(FragmentEvaluacion.this.getActivity())
                                .setTitle("Exito")
                                .setMessage("Actividad Agregada!")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        recrear();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                    }else{
                        new AlertDialog.Builder(FragmentEvaluacion.this.getActivity())
                                .setTitle("Error")
                                .setMessage("No se pudo agregar esta actividad.")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
            }
        });
        agAct.show();
    }
}
