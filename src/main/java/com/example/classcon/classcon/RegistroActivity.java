package com.example.classcon.classcon;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistroActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.classcon.MESSAGE";
    private EditText etUsuario, etPassword, etCorreo, etConfPass;
    adminBD db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Intent intent = getIntent();
        String usuario = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        db = adminBD.getInstance(this.getApplicationContext());
        etUsuario = (EditText) findViewById(R.id.regUsuario);
        etUsuario.setText(usuario);
        etPassword = (EditText) findViewById(R.id.regPassword);
        etCorreo = (EditText) findViewById(R.id.regCorreo);
        etConfPass = (EditText) findViewById(R.id.confPass);
        Button registrar = (Button)findViewById(R.id.regBoton);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!intentarRegistrar()){
                    new AlertDialog.Builder(RegistroActivity.this)
                            .setTitle("Error")
                            .setMessage(R.string.errorReg)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    abrirMenuP();
                }
            }
        });
    }
    private void abrirMenuP(){
        Intent menuP = new Intent(this, MenuPrActivity.class);
        menuP.putExtra(EXTRA_MESSAGE, etUsuario.getText().toString());
        startActivity(menuP);
    }
    private boolean intentarRegistrar(){
        etUsuario.setError(null);
        etPassword.setError(null);
        etCorreo.setError(null);
        String u = etUsuario.getText().toString();
        if (u.length() < 1){
            etUsuario.setError(getString(R.string.error_field_required));
            return false;
        }
        String p = etPassword.getText().toString();
        if(p.length() < 1){
            etPassword.setError(getString(R.string.error_invalid_password));
            return false;
        }
        String e = etCorreo.getText().toString();
        if(e.length() < 1 || !e.contains("@")){
            etCorreo.setError(getString(R.string.correoInvalido));
            return false;
        }
        String pconf = etConfPass.getText().toString();
        if(!p.equals(pconf)){
            etConfPass.setError(getString(R.string.noCoinc));
            return false;
        }
        DatosPersonales dp = db.getDatosPersonales();
        if (dp == null) {
            dp = new DatosPersonales("-", "-", "-", 1);
            db.ingresarDatosPersonales(dp);
        }
        Usuario usu = new Usuario(u,p,e,dp.getId());
        return db.ingresarUsuario(usu);
    }
}
