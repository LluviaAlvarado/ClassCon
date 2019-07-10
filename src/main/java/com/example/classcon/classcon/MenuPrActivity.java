package com.example.classcon.classcon;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MenuPrActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.classcon.MESSAGE";
    adminBD bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pr);
        Intent intent = getIntent();
        String usuario = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView usu = (TextView) findViewById(R.id.usuarioMenuPr);
        usu.setText(usuario);
        //carga de secciones
        final ArrayList<Button> botones = new ArrayList<>();
        NestedScrollView v = (NestedScrollView) findViewById(R.id.contMenuPr);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        bd = adminBD.getInstance(this.getApplicationContext());
        final ArrayList<Seccion> secciones = bd.getSecciones();
        if(!secciones.isEmpty()) {
            String nom, mat = "";
            //buscando materias para poner su nombre
            ArrayList<Materia> materias = bd.getMaterias();
            for (int i = 0; i < secciones.size(); i++) {
                for(int j = 0; j < materias.size(); j++){
                    if(materias.get(j).getClave().equals(secciones.get(i).getIdMat())){
                        mat = materias.get(j).getNombre();
                    }
                }
                String horario = "";
                for(int x = 0; x < secciones.get(i).getDiasClase().length(); x++){
                    if(x != secciones.get(i).getDiasClase().length()-1) {
                        horario += secciones.get(i).getDiasClase().toCharArray()[x] + "-";
                    }else{
                        horario += secciones.get(i).getDiasClase().toCharArray()[x];
                    }
                }
                horario+=" "+secciones.get(i).getHoraInicio()+"-"+secciones.get(i).getHoraFin();
                nom = secciones.get(i).getId() + " - " + mat + "\n\nHorario: " + horario + "\n";
                final Button boton = new Button(this);
                boton.setText(nom);
                final Drawable secc, alert;
                if (android.os.Build.VERSION.SDK_INT < 21) {
                    secc = getResources().getDrawable(R.drawable.boton);
                    alert = getResources().getDrawable(R.drawable.alerta);
                } else {
                    secc = getResources().getDrawable(R.drawable.boton, null);
                    alert = getResources().getDrawable(R.drawable.alerta, null);
                }
                //para que este del ancho del boton
                int ancho = Resources.getSystem().getDisplayMetrics().widthPixels;
                Bitmap bitmap = ((BitmapDrawable) secc).getBitmap();
                Drawable scc = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, ancho - 50, secc.getIntrinsicHeight(), true));
                //ver si no nombro asistencia
                boton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, scc, null, null);
                if(checarFechas(secciones.get(i))){
                    boton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, scc, null, alert);
                    enviarNotificacion(secciones.get(i).getId()+" - "+mat);
                }
                boton.setPadding(5, 5, 5, 5);
                boton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                boton.getBackground().setColorFilter(0x607d8b, PorterDuff.Mode.MULTIPLY);
                boton.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), menuSecciones.class);
                        String seccion = ((Button) v).getText().toString();
                        int indice = seccion.indexOf('\n');
                        seccion = seccion.substring(0, indice);
                        intent.putExtra(EXTRA_MESSAGE, seccion);
                        startActivity(intent);
                    }
                });
                //para eliminar
                final int finalI = i;
                boton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new AlertDialog.Builder(MenuPrActivity.this)
                                .setTitle("Confirmación")
                                .setMessage(R.string.confElS)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        if(bd.eliminarSeccion(secciones.get(finalI).getId(),secciones.get(finalI).getIdMat())){
                                            recreate();
                                        }else{
                                            new AlertDialog.Builder(MenuPrActivity.this)
                                                    .setTitle("Error")
                                                    .setMessage(R.string.eElS)
                                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.cancel();

                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_info)
                                                    .show();
                                        }
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        return false;
                    }
                });
                botones.add(boton);
                layout.addView(botones.get(i));
            }
        }else{
            TextView tx = new TextView(this);
            tx.setText(getString(R.string.vacio));
            tx.setTextSize(24);
            tx.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT));
            tx.setGravity(Gravity.CENTER);
            layout.addView(tx);
        }
        v.addView(layout);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.cuentaUsuario:
                Intent intent = new Intent(this,CuentaActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void agregarSeccion(View view){
        final Dialog agSecc = new Dialog(this);
        String materiaS = "";
        agSecc.setContentView(R.layout.dialog_agregar_seccion);
        agSecc.setTitle("Agregar Sección");
        final Spinner ciclos = (Spinner) agSecc.findViewById(R.id.ciclo);
        ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(this, R.array.ciclos,android.R.layout.simple_spinner_item );
        //llenando el spinner de materias
        final Spinner spinner = (Spinner) agSecc.findViewById(R.id.materia);
        ArrayList<Materia> mat = bd.getMaterias();
        ArrayList<String> nombres = new ArrayList<>();
        if(!mat.isEmpty()) {
            for (int i = 0; i < mat.size(); i++){
                nombres.add(mat.get(i).getNombre());
            }
        }
        nombres.add("Nueva");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ciclos.setAdapter(adapt);
        final EditText nueva = (EditText) agSecc.findViewById(R.id.editTextNueva);
        nueva.setVisibility(View.GONE);
        final EditText idNueva = (EditText) agSecc.findViewById(R.id.claveNueva);
        idNueva.setVisibility(View.GONE);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id){
                Object item = parent.getItemAtPosition(pos);
                if(item.toString().compareTo("Nueva") == 0){
                    nueva.setVisibility(View.VISIBLE);
                    idNueva.setVisibility(View.VISIBLE);
                }else{
                    nueva.setVisibility(View.GONE);
                    idNueva.setVisibility(View.GONE);
                }
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
        spinner.setAdapter(adapter);
        final EditText id = (EditText) agSecc.findViewById(R.id.editTextSecc);
        final EditText horaI = (EditText) agSecc.findViewById(R.id.editTextI);
        final EditText horaF = (EditText) agSecc.findViewById(R.id.editTextF);
        final CheckBox lunes = (CheckBox) agSecc.findViewById(R.id.lunes);
        final CheckBox martes = (CheckBox) agSecc.findViewById(R.id.martes);
        final CheckBox miercoles = (CheckBox) agSecc.findViewById(R.id.miercoles);
        final CheckBox jueves = (CheckBox) agSecc.findViewById(R.id.jueves);
        final CheckBox viernes = (CheckBox) agSecc.findViewById(R.id.viernes);
        final CheckBox sabado = (CheckBox) agSecc.findViewById(R.id.sabado);
        Button agregarB = (Button) agSecc.findViewById(R.id.agregar);
        agregarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idMat = "";
                if(spinner.getSelectedItem().toString().equals("Nueva")){
                    Materia mat = new Materia(idNueva.getText().toString(), nueva.getText().toString());
                    if(!bd.agregarMateria(mat)){
                        new AlertDialog.Builder(MenuPrActivity.this)
                                .setTitle("Error")
                                .setMessage(R.string.errorMat)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    idMat = mat.getClave();
                }else{
                    idMat = bd.getMateriaN(spinner.getSelectedItem().toString()).getClave();
                }
                String dias = "";
                if (lunes.isChecked()){
                    dias+="l";
                }
                if (martes.isChecked()){
                    dias+="m";
                }
                if (miercoles.isChecked()){
                    dias+="i";
                }
                if (jueves.isChecked()){
                    dias+="j";
                }
                if (viernes.isChecked()){
                    dias+="v";
                }
                if (sabado.isChecked()){
                    dias+="s";
                }
                Calendar c = Calendar.getInstance();
                int anio = c.get(Calendar.YEAR);
                String cicloS = Integer.toString(anio)+"-"+ciclos.getSelectedItem().toString();
                Seccion secc = new Seccion(id.getText().toString(), horaI.getText().toString(), horaF.getText().toString(), dias, cicloS, idMat);
                if(!bd.agregarSeccion(secc)){
                    new AlertDialog.Builder(MenuPrActivity.this)
                            .setTitle("Error")
                            .setMessage(R.string.errorSecc)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    new AlertDialog.Builder(MenuPrActivity.this)
                            .setTitle("Exito")
                            .setMessage(R.string.exitoSecc)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    recreate();
                                    agSecc.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();

                }
            }
        });
        agSecc.show();
    }
    public boolean checarFechas(Seccion secc){
        return bd.faltaAsistencia(secc);
    }
    public void enviarNotificacion(String secc){
        android.support.v4.app.NotificationCompat.Builder not = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.alerta)
                .setContentTitle("Nombrar Asistencia")
                .setContentText("Nombrar Asistencia para la sección "+secc);
        //abrir asistencias
        Intent asis = new Intent(this, menuSecciones.class);
        asis.putExtra(EXTRA_MESSAGE, secc);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(menuSecciones.class);
        stackBuilder.addNextIntent(asis);
        PendingIntent asisP = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        not.setContentIntent(asisP);
        NotificationManager notM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notM.notify(1, not.build());

    }
}
