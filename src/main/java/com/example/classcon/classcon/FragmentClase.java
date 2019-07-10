package com.example.classcon.classcon;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.CompoundButton;
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
import java.util.List;
import java.util.Locale;

/**
 * Created by Lluvia on 28/3/2017.
 */

public class FragmentClase extends Fragment {
    static Seccion secc;
    adminBD bd;
    Date fechaSeleccionada;
    private static SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private static SimpleDateFormat dateFormatForBD = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());
    public FragmentClase() {
    }

    public static FragmentClase newInstance(Seccion s) {
        secc = s;
        return new FragmentClase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bd = adminBD.getInstance(this.getActivity().getApplicationContext());
        Calendar c = Calendar.getInstance();
        fechaSeleccionada = c.getTime();
        View rootView = inflater.inflate(R.layout.fragment_clase, container, false);
        final Spinner spinnerTemas = (Spinner) rootView.findViewById(R.id.tema1Clase);
        final Spinner spinnerTemas2 = (Spinner) rootView.findViewById(R.id.tema2Clase);
        spinnerTemas2.setVisibility(View.GONE);
        final Spinner spinnerTemas3 = (Spinner) rootView.findViewById(R.id.tema3Clase);
        spinnerTemas3.setVisibility(View.GONE);
        ArrayList<String> nombreTemas = new ArrayList<>();
        nombreTemas.add("---");
        Materia materia = bd.getMateriaI(secc.getIdMat());
        final ArrayList<Tema> temas = bd.getTemasMateria(materia);
        for(int i = 0; i < temas.size(); i++){
            nombreTemas.add(temas.get(i).getNombre());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, nombreTemas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTemas.setAdapter(adapter);
        spinnerTemas2.setAdapter(adapter);
        spinnerTemas3.setAdapter(adapter);
        //máximo agregar 3 temas a una clase
        spinnerTemas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    spinnerTemas2.setVisibility(View.VISIBLE);
                }else{
                    spinnerTemas2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerTemas2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    spinnerTemas3.setVisibility(View.VISIBLE);
                }else{
                    spinnerTemas3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final EditText otroMaterial = (EditText) rootView.findViewById(R.id.otroMat);
        otroMaterial.setVisibility(View.GONE);
        final Button botonAgregarMat = (Button) rootView.findViewById(R.id.agregarMat);
        botonAgregarMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarMaterial(otroMaterial.getText().toString());
            }
        });
        botonAgregarMat.setVisibility(View.GONE);
        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.layoutMateriales);
        final ArrayList<Material> materiales = bd.getMateriales();
        final ArrayList<CheckBox> chMat = new ArrayList<>();
        //agregando materiales
        for (int i = 0; i < materiales.size(); i++){
            CheckBox ch = new CheckBox(this.getContext());
            ch.setText(materiales.get(i).getNombre());
            chMat.add(ch);
            layout.addView(chMat.get(i));
        }
        CheckBox otro = (CheckBox) rootView.findViewById(R.id.chBOtroM);
        otro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    otroMaterial.setVisibility(View.VISIBLE);
                    botonAgregarMat.setVisibility(View.VISIBLE);
                }else{
                    otroMaterial.setVisibility(View.GONE);
                    botonAgregarMat.setVisibility(View.GONE);
                }
            }
        });
        final CompactCalendarView fecha = (CompactCalendarView) rootView.findViewById(R.id.fechaClase);
        ArrayList<Event> clases = bd.getFechasClase(secc);
        for(int i = 0; i < clases.size(); i++){
            fecha.addEvent(clases.get(i),true);
        }
        fecha.invalidate();
        final TextView mesAnio = (TextView) rootView.findViewById(R.id.mesAnioClase);
        final EditText nombreClase = (EditText) rootView.findViewById(R.id.nomClase);
        final EditText descClase = (EditText) rootView.findViewById(R.id.descClase);
        mesAnio.setText(dateFormatForMonth.format(fecha.getFirstDayOfCurrentMonth()));

        fecha.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                fechaSeleccionada = dateClicked;
                String fecha = dateFormatForBD.format(fechaSeleccionada);
                Clase clase = bd.getClase(secc,fecha);
                //limpiar clase
                nombreClase.setText("");
                descClase.setText("");
                for(int i = 0; i < chMat.size(); i++){
                    chMat.get(i).setChecked(false);
                }
                spinnerTemas.setSelection(0);
                spinnerTemas2.setSelection(0);
                spinnerTemas3.setSelection(0);
                //cargar si existe
                if(clase != null){
                    nombreClase.setText(clase.getNombre());
                    descClase.setText(clase.getDescrp());
                    for (int i = 0; i < clase.getTemas().size(); i++){
                        int pos = 0;
                        for(int j = 0; j < temas.size(); j++){
                            if(temas.get(j).getNombre().equals(clase.getTemas().get(i).getNombre())){
                                pos = j+1;
                            }
                        }
                        if(i == 0){
                            spinnerTemas.setSelection(pos);
                        }else if(i == 1){
                            spinnerTemas2.setSelection(pos);
                        }else {
                            spinnerTemas3.setSelection(pos);
                        }
                    }
                    for (int i = 0; i < clase.getMateriales().size(); i++){
                        for (int j = 0; j < materiales.size(); j++){
                            if(materiales.get(j).getId() == clase.getMateriales().get(i).getId()){
                                chMat.get(j).setChecked(true);
                            }
                        }
                    }
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mesAnio.setText(dateFormatForMonth.format(fecha.getFirstDayOfCurrentMonth()));
            }
        });
        Button aceptarClase = (Button) rootView.findViewById(R.id.aceptarClase);
        aceptarClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fechaClase = dateFormatForBD.format(fechaSeleccionada);
                ArrayList<Tema> tClase = new ArrayList<>();
                Tema t;
                if(spinnerTemas.getSelectedItemPosition() > 0){
                    t = temas.get(spinnerTemas.getSelectedItemPosition()-1);
                    tClase.add(t);
                }
                if(spinnerTemas2.getSelectedItemPosition() > 0){
                    if(spinnerTemas.getSelectedItemPosition() != spinnerTemas2.getSelectedItemPosition()) {
                        t = temas.get(spinnerTemas2.getSelectedItemPosition() - 1);
                        tClase.add(t);
                    }
                }
                if(spinnerTemas3.getSelectedItemPosition() > 0){
                    if((spinnerTemas3.getSelectedItemPosition() != spinnerTemas2.getSelectedItemPosition())&&spinnerTemas3.getSelectedItemPosition() != spinnerTemas.getSelectedItemPosition()) {
                        t = temas.get(spinnerTemas3.getSelectedItemPosition() - 1);
                        tClase.add(t);
                    }
                }
                ArrayList<Material> matClase = new ArrayList<>();
                for(int i = 0; i < chMat.size(); i++){
                    if(chMat.get(i).isChecked()){
                        matClase.add(materiales.get(i));
                    }
                }
                Clase clase = new Clase(fechaClase,nombreClase.getText().toString(),descClase.getText().toString(), tClase, matClase);
                if(bd.agregarClase(secc, clase)){
                    new AlertDialog.Builder(FragmentClase.this.getActivity())
                            .setTitle("Exito")
                            .setMessage("Clase Agregada!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }else{
                    new AlertDialog.Builder(FragmentClase.this.getActivity())
                            .setTitle("Error")
                            .setMessage("Error al agregar Clase")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
        return rootView;
    }
    public void agregarClase(View v){
        Clase clase;

    }
    public void guardarMaterial(String nom){
        //da igual el numero de id, sqlite lo autoincrementa
        Material mat = new Material(1, nom);
        if(bd.agregarMaterial(mat)){
            this.getActivity().recreate();
        }else{
            new AlertDialog.Builder(FragmentClase.this.getActivity())
                    .setTitle("Error")
                    .setMessage("Error al agregar Material")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
    public void cargarDatosFecha(){

    }
}