package com.example.classcon.classcon;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TemarioActivity extends AppCompatActivity {
    Materia materia;
    adminBD bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temario);
        Intent intent = getIntent();
        String nomMat = intent.getStringExtra(menuSecciones.EXTRA_MESSAGE);
        bd = adminBD.getInstance(this.getApplicationContext());
        materia = bd.getMateriaI(nomMat);
        setTitle("Materia: "+materia.getNombre());
        //android.support.design.widget.CoordinatorLayout rootLayout = (android.support.design.widget.CoordinatorLayout) findViewById(R.id.rootTemas);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutTemas);
        final ArrayList<Tema> temas = bd.getTemasMateria(materia);
        for(int i = 0; i < temas.size(); i++){
            Button boton = new Button(this);
            boton.setText(temas.get(i).getNombre());
            final int finalI = i;
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarDesc(temas.get(finalI));
                }
            });
            layout.addView(boton);
        }
    }
    public void mostrarDesc(Tema t){
        final Dialog descTema = new Dialog(this);
        descTema.setContentView(R.layout.dialog_desc_tema);
        descTema.setTitle("Descripción del Tema");
        TextView nombreM = (TextView) descTema.findViewById(R.id.nomMatTema);
        nombreM.setText(materia.getNombre());
        TextView nombre = (TextView) descTema.findViewById(R.id.nomTema);
        nombre.setText(t.getNombre());
        TextView desc = (TextView) descTema.findViewById(R.id.descTema);
        desc.setText(t.getDescripcion());
        descTema.show();
    }
    public void agregarTema(View v){
        final Dialog agTema = new Dialog(this);
        agTema.setContentView(R.layout.dialog_agregar_tema);
        agTema.setTitle("Agregar Tema");
        Button agregar = (Button) agTema.findViewById(R.id.agregarTema);
        final EditText nombre = (EditText) agTema.findViewById(R.id.nomTema);
        final EditText desc = (EditText) agTema.findViewById(R.id.descTema);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tema tema = new Tema(materia.getClave(),nombre.getText().toString(),desc.getText().toString());
                if(bd.agregarTema(tema)){
                    new AlertDialog.Builder(TemarioActivity.this)
                            .setTitle("Exito")
                            .setMessage("¡Tema agregado!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    recreate();
                                    agTema.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }else{
                    new AlertDialog.Builder(TemarioActivity.this)
                            .setTitle("Error")
                            .setMessage("No se puede agregar este Tema")
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
        agTema.show();
    }

}
