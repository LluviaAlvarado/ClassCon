package com.example.classcon.classcon;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class menuSecciones extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.classcon.MESSAGE";
    adminBD bd;
    Seccion secc;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_secciones);
        Intent intent = getIntent();
        String nomSeccion = intent.getStringExtra(MenuPrActivity.EXTRA_MESSAGE);
        bd = adminBD.getInstance(this.getApplicationContext());
        int indice = nomSeccion.indexOf('-');
        String id = nomSeccion.substring(0, indice-1);
        String nM = nomSeccion.substring(indice+2,nomSeccion.length());
        Materia mat = bd.getMateriaN(nM);
        //obtener la seccion con la que se va a trabajar
        secc = bd.getSeccion(id,mat.getClave());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(nomSeccion);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_secciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this,ActivityConfigurarSeccion.class);
                String s = secc.getId()+"-"+secc.getIdMat();
                intent.putExtra(EXTRA_MESSAGE, s);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return FragmentAsistencia.newInstance(secc);
                case 1:
                    return RootFragmentEvaluacion.newInstance(secc);
                case 2:
                    return FragmentClase.newInstance(secc);
                case 3:
                    return RootFragmentControlC.newInstance(secc);
                case 4:
                    return FragmentReportes.newInstance(secc);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Asistencia";
                case 1:
                    return "Evaluación";
                case 2:
                    return "Clase";
                case 3:
                    return "Control";
                case 4:
                    return "Reportes";
            }
            return null;
        }
    }
}
