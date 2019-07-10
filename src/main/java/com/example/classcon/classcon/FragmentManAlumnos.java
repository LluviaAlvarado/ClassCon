package com.example.classcon.classcon;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Lluvia on 28/3/2017.
 */

public class FragmentManAlumnos extends Fragment {
    static Seccion secc;
    static adminBD bd;
    public FragmentManAlumnos() {

    }
    public static FragmentManAlumnos newInstance(Seccion s) {
        secc = s;
        return new FragmentManAlumnos();
    }
    public void recrear(){this.getActivity().recreate();}
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_man_alumnos, container, false);
        bd = adminBD.getInstance(this.getActivity().getApplicationContext());
        final ArrayList<Alumno> alumnos = bd.dameAlumnosSeccion(secc);
        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.contLA);
        ArrayList<Button> botones = new ArrayList<>();
        for(int i = 0; i < alumnos.size(); i++) {
            final Button b = new Button(this.getActivity());
            b.setText(alumnos.get(i).getNombre());
            b.setId(i);
            if(alumnos.get(i).getStatus()=='E') {
                b.setBackgroundColor(Color.rgb(249, 209, 157));
            }
            else if(alumnos.get(i).getStatus()=='S'){
                b.setBackgroundColor(Color.rgb(249, 157, 157));
            }
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    infoAlumnos(alumnos.get(b.getId()));
                }
            });
            botones.add(b);
            layout.addView(b);
        }
        android.support.design.widget.FloatingActionButton agregar = (android.support.design.widget.FloatingActionButton) rootView.findViewById(R.id.agregarAlumno);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarAlumno();
            }
        });
        return rootView;
    }

    public void infoAlumnos(Alumno alumno){
        final Dialog InfAl = new Dialog(getActivity());
        InfAl.setContentView(R.layout.dialog_info_alumno);
        InfAl.setTitle(alumno.getNombre());
        String tx = "Nombre:\n"+alumno.getNombre();
        TextView nombre = (TextView) InfAl.findViewById(R.id.nAlumno);
        nombre.setText(tx);
        TextView codigo = (TextView) InfAl.findViewById(R.id.cAlumno);
        tx = "Código: "+alumno.getCodigo();
        codigo.setText(tx);
        InfAl.show();
    }

    public void agregarAlumno(){
        final Dialog agAlu = new Dialog(getActivity());
        agAlu.setContentView(R.layout.dialog_agregar_alumno);
        agAlu.setTitle("Agregar Alumno");
        Button agregar = (Button) agAlu.findViewById(R.id.agregarAl);
        final EditText nombre = (EditText) agAlu.findViewById(R.id.nombreAl);
        final EditText codigo = (EditText) agAlu.findViewById(R.id.codAl);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alumno a = new Alumno(nombre.getText().toString(),-1,codigo.getText().toString());
                if(bd.agregarAlumno(a,secc)){
                    new AlertDialog.Builder(FragmentManAlumnos.this.getActivity())
                            .setTitle("Exito")
                            .setMessage("¡Alumno agregado!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    recrear();
                                    agAlu.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }else{
                    new AlertDialog.Builder(FragmentManAlumnos.this.getActivity())
                            .setTitle("Error")
                            .setMessage("No se puede agregar este Alumno")
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
        agAlu.show();
    }
}
