package com.example.classcon.classcon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityConfigurarSeccion extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.classcon.MESSAGE";
    Seccion secc;
    adminBD bd;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_seccion);
        Intent intent = getIntent();
        s = intent.getStringExtra(menuSecciones.EXTRA_MESSAGE);
        bd = adminBD.getInstance(this.getApplicationContext());
        int indice = s.indexOf("-");
        secc =  bd.getSeccion(s.substring(0,indice),s.substring(indice+1,s.length()));
        setTitle(s);
    }

    public void abrirTemario(View view) {
        Intent intent = new Intent(this,TemarioActivity.class);
        int indice = s.indexOf("-");
        String nomMateria = s.substring(indice+1, s.length());
        intent.putExtra(EXTRA_MESSAGE, nomMateria);
        this.startActivity(intent);
    }
}
