package com.example.classcon.classcon;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Lluvia on 28/3/2017.
 */

public class FragmentAsistencia extends Fragment {
    static Seccion secc;

    private ListView listView = null;
    private ArrayList<Alumno> alumnos = new ArrayList<>();
    ArrayAdapter adaptador;
    adminBD db ;
    Date fechaSeleccionada;

    private static SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    public FragmentAsistencia() {
    }

    public static FragmentAsistencia newInstance(Seccion s) {
        secc = s;
        return new FragmentAsistencia();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        db = adminBD.getInstance(this.getActivity().getApplicationContext());
        setRetainInstance(true);
        Calendar c = Calendar.getInstance();
        fechaSeleccionada = c.getTime();
        View rootView = inflater.inflate(R.layout.fragment_asistencia, container, false);
        final CompactCalendarView fecha = (CompactCalendarView) rootView.findViewById(R.id.fechaAsistencia);
        ArrayList<Event> clases = db.getFechasAsistencia(secc);
        for(int i = 0; i < clases.size(); i++){
            fecha.addEvent(clases.get(i),true);
        }
        fecha.invalidate();
        final TextView mesAnio = (TextView) rootView.findViewById(R.id.mesAnioAsistencia);
        mesAnio.setText(dateFormatForMonth.format(fecha.getFirstDayOfCurrentMonth()));
        fecha.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                // TODO: 28/3/2017 pop-up si es una fecha con algo importante 
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mesAnio.setText(dateFormatForMonth.format(fecha.getFirstDayOfCurrentMonth()));
            }
        });
        LinearLayout layaout =  (LinearLayout) rootView.findViewById(R.id.listaAlumnos);
        alumnos = db.dameAlumnosSeccion(secc);
        ArrayList<CheckBox> alumnosChBx = new ArrayList<>();
        for(int i=0;i<alumnos.size();i++){
            CheckBox check = new CheckBox(this.getActivity());
            check.setText(alumnos.get(i).getNombre());
            check.setChecked(true);
            alumnosChBx.add(check);
            layaout.addView(alumnosChBx.get(i));
        }

        Button botonAgregar = (Button) rootView.findViewById(R.id.aceptarAsis);
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(FragmentAsistencia.this.getActivity())
                        .setTitle("Exito!")
                        .setMessage("Las asistencias fueron capturadas")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });


        return rootView;
    }




}
