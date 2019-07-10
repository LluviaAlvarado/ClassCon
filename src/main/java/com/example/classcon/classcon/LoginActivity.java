package com.example.classcon.classcon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Loader;
import android.database.Cursor;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    public static final String EXTRA_MESSAGE = "com.example.classcon.MESSAGE";
    adminBD baseDatos;

    // UI references.
    private AutoCompleteTextView mUsuarioView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    static final String usuarioActual = "";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            mUsuarioView.setText(usuarioActual);
            abrirMenuPr();
        }
        baseDatos = adminBD.getInstance(this.getApplicationContext());
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsuarioView = (AutoCompleteTextView) findViewById(R.id.user);
        populateAutoComplete();
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    if(attemptLogin()){
                        savedInstanceState.putString(usuarioActual, mUsuarioView.getText().toString());
                        abrirMenuPr();
                    }
                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               if(attemptLogin()){
                   abrirMenuPr();
               }
            }
        });
        Button registrarse = (Button) findViewById(R.id.reg_in_button);
        registrarse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRegistro();
            }
        });
        Usuario u = baseDatos.getUsuario();
        if (u != null){
            registrarse.setVisibility(View.GONE);
        }else{
            mSignInButton.setVisibility(View.GONE);
        }
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }

    private void abrirMenuPr(){
        Intent menuP = new Intent(this, MenuPrActivity.class);
        menuP.putExtra(EXTRA_MESSAGE, mUsuarioView.getText().toString());
        startActivity(menuP);
    }
    private void abrirRegistro(){
        Intent reg = new Intent(this, RegistroActivity.class);
        reg.putExtra(EXTRA_MESSAGE, mUsuarioView.getText().toString());
        startActivity(reg);
    }
    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    private boolean attemptLogin() {
        // Reset errors.
        mUsuarioView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String usuario = mUsuarioView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false, valido = false;
        View focusView = null;
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid usuario.
        if (TextUtils.isEmpty(usuario)) {
            mUsuarioView.setError(getString(R.string.error_field_required));
            focusView = mUsuarioView;
            cancel = true;
        }
        if(!cancel){
            Usuario usu = baseDatos.getUsuario();
            if(usu != null) {
                if(usuario.equals(usu.getNombre())){
                    if(password.equals(usu.getPassword())){
                        valido = true;
                    }else{
                        mPasswordView.setError(getString(R.string.error_incorrect_password));

                    }
                }else{
                    mUsuarioView.setError(getString(R.string.error_invalid_usuario));
                }
            }else{
                abrirRegistro();
            }
        }
        return valido;
    }



    private boolean isPasswordValid(String password) {
        return password.length() > 0;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //guardar para que no tenga que volver a ingresar datos
        savedInstanceState.putString(usuarioActual, mUsuarioView.getText().toString());
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

}

