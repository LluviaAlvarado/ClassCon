package com.example.classcon.classcon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;
import android.widget.Switch;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lluvia on 5/4/2017.
 */

public class adminBD extends SQLiteOpenHelper {
    private static final String TAG = "ERROR";
    private static adminBD sInstance;
    public static final String DATABASE_NAME = "classCon.db";
    private static final int DATABASE_VERSION = 14;
    public static synchronized adminBD getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new adminBD(context.getApplicationContext());
        }
        return sInstance;
    }
    public adminBD(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Materia(idMateria TEXT NOT NULL, nomMateria TEXT NOT NULL, PRIMARY KEY(idMateria));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Alumno(nombreAl TEXT NOT NULL, codigoAl TEXT NOT NULL,  PRIMARY KEY(codigoAl))");
        db.execSQL("CREATE TABLE IF NOT EXISTS DatosPersonales(nombreDts TEXT NOT NULL, codigoDts TEXT NOT NULL, departamentoDts TEXT NOT NULL, idDts INTEGER NOT NULL, PRIMARY KEY(idDts));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Seccion(idSecc TEXT NOT NULL, horaI TEXT NOT NULL, horaF TEXT NOT NULL, diasC TEXT NOT NULL, cicloEsc TEXT NOT NULL, idMS TEXT NOT NULL, PRIMARY KEY(idSecc, idMS), FOREIGN KEY (idMS) REFERENCES Materia (idMateria));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Clase(fechaClase TEXT NOT NULL, nombreClase TEXT NOT NULL, descripcionClase TEXT NOT NULL, idSec TEXT NOT NULL, idSMC TEXT NOT NULL, PRIMARY KEY(fechaClase, idSec, idSMC), FOREIGN KEY (idSec, idSMC) REFERENCES Seccion (idSecc, idMS));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Tema(idMat TEXT NOT NULL, nombreTema TEXT NOT NULL, descripcionTema TEXT NOT NULL, PRIMARY KEY(nombreTema, idMat), FOREIGN KEY (idMat) REFERENCES Materia(idMateria));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Usuario(nombreU TEXT NOT NULL,password TEXT NOT NULL,correo TEXT NOT NULL,idDP INTEGER NOT NULL,CHECK (correo LIKE ('%@%')),PRIMARY KEY(nombreU),FOREIGN KEY (idDP) REFERENCES DatosPersonales (idDts));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Criterio(nombreCr TEXT NOT NULL, porcentajeCr REAL NOT NULL, idS TEXT NOT NULL, idSMa TEXT NOT NULL, PRIMARY KEY(nombreCr, idS, idSMa), FOREIGN KEY (idS, idSMa) REFERENCES Seccion (idSecc, idMS));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Asistencias(fechaAs TEXT NOT NULL, codigoA TEXT NOT NULL, idSecA TEXT NOT NULL, idMA TEXT NOT NULL, PRIMARY KEY(fechaAs, codigoA,idSecA, idMA), FOREIGN KEY (idSecA, idMA) REFERENCES Seccion (idSecc, idMS), FOREIGN KEY (codigoA) REFERENCES Alumno (codigoAl));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Secc_Al(idSCA TEXT NOT NULL, codigoAS TEXT NOT NULL, idMSA TEXT NOT NULL, equipoAl INTEGER NOT NULL, PRIMARY KEY(idSCA, codigoAS, idMSA), FOREIGN KEY (idSCA, idMSA) REFERENCES Seccion (idSecc, idMS), FOREIGN KEY (codigoAS) REFERENCES Alumno (codigoAl));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Actividad(fechaAct TEXT NOT NULL, tipoAct TEXT NOT NULL, nombreAct TEXT NOT NULL, descripAct TEXT, ptAct FLOAT NOT NULL, idSAct TEXT NOT NULL,idMSa TEXT NOT NULL, PRIMARY KEY(nombreAct, idSAct, idMSa), FOREIGN KEY (idSAct, idMSa) REFERENCES Seccion (idSecc, idMS));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Act_Al(codigoAA TEXT NOT NULL, nombreAA TEXT NOT NULL,idSAA TEXT NOT NULL, ptAA REAL NOT NULL, idMs TEXT NOT NULL, PRIMARY KEY(codigoAA, nombreAA, idSAA, idMs), FOREIGN KEY (codigoAA) REFERENCES Alumno (codigoAl), FOREIGN KEY (nombreAA, idSAA, idMs) REFERENCES Actividad (nombreAct, idSAct, idMSa));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Act_T(nombreAT TEXT NOT NULL, idSAT TEXT NOT NULL, nTema TEXT NOT NULL,idMat TEXT NOT NULL, PRIMARY KEY(nombreAT, idSAT, nTema, idMat), FOREIGN KEY (nombreAT, idSAT, idMat) REFERENCES Actividad (nombreAct, idSAct, idMSa), FOREIGN KEY (nTema, idMat) REFERENCES Tema (nombreTema, idMat));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Cla_T(fClase TEXT NO NULL, idSCT TEXT NOT NULL, nomT TEXT NOT NULL,idMCT TEXT NOT NULL, PRIMARY KEY (fClase, idSCT, nomT, idMCT), FOREIGN KEY (fClase, idSCT, idMCT) REFERENCES Clase (fechaClase, idSec, idSMC), FOREIGN KEY (idSCT, idMCT) REFERENCES Seccion (idSecc, idMS), FOREIGN KEY (nomT, idMCT) REFERENCES Tema(nombreTema, idMat));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Material(idMaterial INTEGER NOT NULL, nombreMaterial TEXT NOT NULL, PRIMARY KEY(idMaterial));");
        db.execSQL("CREATE TABLE IF NOT EXISTS Cla_M(fClass TEXT NOT NULL, idSC TEXT NOT NULL, idMcm TEXT NOT NULL, idMatCl INTEGER NOT NULL, PRIMARY KEY (fClass, idSC, idMcm, idMatCl), FOREIGN KEY (fClass, idSC, idMcm) REFERENCES Clase (fechaClase, idSec, idSMC), FOREIGN KEY (idMatCl) REFERENCES Material (idMaterial));");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Cla_M");
            db.execSQL("DROP TABLE IF EXISTS Material");
            db.execSQL("DROP TABLE IF EXISTS Cla_T");
            db.execSQL("DROP TABLE IF EXISTS Act_T");
            db.execSQL("DROP TABLE IF EXISTS Act_Al");
            db.execSQL("DROP TABLE IF EXISTS Actividad");
            db.execSQL("DROP TABLE IF EXISTS Secc_Al");
            db.execSQL("DROP TABLE IF EXISTS Asistencias");
            db.execSQL("DROP TABLE IF EXISTS Criterio");
            db.execSQL("DROP TABLE IF EXISTS Tema");
            db.execSQL("DROP TABLE IF EXISTS Clase");
            db.execSQL("DROP TABLE IF EXISTS Usuario");
            db.execSQL("DROP TABLE IF EXISTS DatosPersonales");
            db.execSQL("DROP TABLE IF EXISTS Alumno");
            db.execSQL("DROP TABLE IF EXISTS Seccion");
            db.execSQL("DROP TABLE IF EXISTS Materia");
            onCreate(db);
        }
    }
    //metodos de datos personales
    public boolean ingresarDatosPersonales(DatosPersonales dp){
        boolean exito = false;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues valores = new ContentValues();
            valores.put("nombreDts",dp.getNomCompleto());
            valores.put("codigoDts",dp.getCodigo());
            valores.put("departamentoDts",dp.getDepartamento());
            valores.put("idDts",dp.getId());
            db.insertOrThrow("DatosPersonales", null, valores);
            db.setTransactionSuccessful();
            exito = true;
        }catch (Exception e){
            exito = false;
            Log.d("Error","Error al insertar datos personales");
        }finally {
            db.endTransaction();
            db.close();
        }
        return exito;
    }
    public DatosPersonales getDatosPersonales(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from DatosPersonales",null);
        DatosPersonales dp = null;
        try{
            if(resultSet.moveToFirst()){
                do{
                    dp = new DatosPersonales(resultSet.getString(0), resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3));
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer datos personales");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
                db.close();
            }
        }
        return dp;
    }
    //Metodos del usuario
    public boolean ingresarUsuario(Usuario usu){
        boolean exito = false;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues valores = new ContentValues();
            valores.put("nombreU",usu.getNombre());
            valores.put("password",usu.getPassword());
            valores.put("correo",usu.getCorreo());
            valores.put("idDP",usu.getIdDP());
            db.insertOrThrow("Usuario", null, valores);
            db.setTransactionSuccessful();
            exito = true;
        }catch (Exception e){
            exito = false;
            Log.d("Error","Error al insertar usuario");
        }finally {
            db.endTransaction();
            db.close();
        }
        return exito;
    }

    public Usuario getUsuario(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from Usuario",null);
        Usuario usu = null;
        try{
            if(resultSet.moveToFirst()){
                do{
                    usu = new Usuario(resultSet.getString(0), resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3));
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer usuarios");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
                db.close();
            }
        }
        return usu;
    }
    //metodos de materias
    public ArrayList<Materia> getMaterias(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from Materia;",null);
        ArrayList<Materia> materias = new ArrayList<>();
        Materia mat;
        try{
            if(resultSet.moveToFirst()){
                do{
                    mat = new Materia(resultSet.getString(0), resultSet.getString(1));
                    materias.add(mat);
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer secciones");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
                db.close();
            }
        }
        return materias;
    }
    public boolean agregarMateria(Materia m){
        boolean exito = false;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues valores = new ContentValues();
            valores.put("idMateria",m.getClave());
            valores.put("nomMateria",m.getNombre());
            db.insertOrThrow("Materia", null, valores);
            db.setTransactionSuccessful();
            exito = true;
        }catch (Exception e){
            exito = false;
            Log.d("Error","Error al insertar materia");
        }finally {
            db.endTransaction();
            db.close();
        }
        return exito;
    }
    public Materia getMateriaN(String nom){
        Materia mat = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from Materia Where nomMateria='"+nom+"';",null);
        try{
            if(resultSet.moveToFirst()){
                do{
                    mat = new Materia(resultSet.getString(0), resultSet.getString(1));
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer materia");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
                db.close();
            }
        }
        return mat;
    }
    public Materia getMateriaI(String id){
        Materia mat = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from Materia Where idMateria = '"+id+"';",null);
        try{
            if(resultSet.moveToFirst()){
                do{
                    mat = new Materia(resultSet.getString(0), resultSet.getString(1));
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer materia");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
                db.close();
            }
        }
        return mat;
    }
    //metodos de secciones

    public ArrayList<Event> getFechasAsistencia(Seccion seccion){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select fechaAs from Asistencias Where idSecA='"+seccion.getId()+"' AND idMA='"+seccion.getIdMat()+"';",null);
        ArrayList<Event> clases = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try{
            if(resultSet.moveToFirst()){
                do{
                    String fecha = resultSet.getString(0);
                    Date d = formatter.parse(fecha);
                    Event event = new Event(Color.BLUE,d.getTime());
                    clases.add(event);
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer asistencias");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
            }
        }
        return clases;
    }
    //metodos de alumnos
    public boolean existeAlumno(Alumno a){
        boolean existe = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select codigoAl from Alumno;",null);
        try{
            if(resultSet.moveToFirst()){
                do{
                    if(a.getCodigo().equals(resultSet.getString(0))){
                        existe = true;
                    }
                }while (resultSet.moveToNext());
            }
        }catch (Exception e){
            Log.d("Error","Error al leer alumnos");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
            }
        }
        return existe;
    }
    public boolean agregarAlumno(Alumno a, Seccion secc){
        boolean exito = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues valores = new ContentValues();
            if(!existeAlumno(a)) {
                valores.put("nombreAl", a.getNombre());
                valores.put("codigoAl", a.getCodigo());
                db.insertOrThrow("Alumno", null, valores);
                valores = new ContentValues();
            }
            valores.put("idSCA",secc.getId());
            valores.put("codigoAS", a.getCodigo());
            valores.put("idMSA", secc.getIdMat());
            valores.put("equipoAl", a.getEquipo());
            db.insertOrThrow("Secc_Al",null,valores);
            db.setTransactionSuccessful();
            exito = true;
        }catch(Exception e){
            Log.d(TAG, "Error al agregar Alumnos");
        }finally {
            db.endTransaction();
        }
        return exito;
    }

    public ArrayList<Alumno> dameAlumnosSeccion(Seccion secc){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Alumno> alumnos = new ArrayList<>();
        ArrayList<String> codigos = new ArrayList<>();
        ArrayList<Integer> equipos = new ArrayList<>();
        Cursor codigosAlumno = db.rawQuery("Select * from Secc_Al Where idSCA= '"+secc.getId()+"' AND idMSA='"+secc.getIdMat()+"';",null);
        try{
            if(codigosAlumno.moveToFirst()){
                do{
                    codigos.add(codigosAlumno.getString(1));
                    equipos.add(codigosAlumno.getInt(3));
                }while(codigosAlumno.moveToNext());

                for (int i=0;i<codigos.size();i++){
                    Cursor alumno = db.rawQuery("Select * from Alumno Where codigoAl = '"+codigos.get(i)+"'",null);
                    if(alumno.moveToFirst()){
                        Alumno a = new Alumno(alumno.getString(0), equipos.get(i), alumno.getString(1));
                        alumnos.add(a);
                    }
                    alumno.close();
                }

            }
        }catch(Exception e){
            Log.d(TAG, "Error al cargar Alumnos");
        }finally {
            codigosAlumno.close();
            db.close();
        }

        return alumnos;
    }

    public boolean registrarAsistencias(String fecha, String seccion, String materia, ArrayList alumnos){
        boolean exito = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try{
            for(int i=0;i<alumnos.size();i++){
                Cursor resultAl = db.rawQuery("Select codigoAl from Alumno Where nombreAl = '" + alumnos.get(i) + "';",null);
                if(resultAl.moveToFirst()) {
                    ContentValues valores = new ContentValues();
                    valores.put("idSCA", seccion);
                    valores.put("codigoAS",resultAl.getString(0));
                    valores.put("idMSA", materia);
                    db.insertOrThrow("Secc_Al", null, valores);
                    db.setTransactionSuccessful();
                }
            }
        }catch(Exception e){
            Log.d(TAG, "Error al cargar Alumnos");
        }finally {
            db.endTransaction();
            exito = true;
        }

        return exito;
    }

    public Seccion getSeccion(String id, String mat){
        Seccion secc = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from Seccion Where idSecc = '"+id+"' AND idMS='"+mat+"';",null);
        try{
            if(resultSet.moveToFirst()){
                do{
                    secc = new Seccion(resultSet.getString(0), resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5));
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer seccion");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
                db.close();
            }
        }
        return secc;
    }
    public boolean agregarSeccion(Seccion secc){
        boolean exito = false;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues valores = new ContentValues();
            valores.put("idSecc", secc.getId());
            valores.put("horaI", secc.getHoraInicio());
            valores.put("horaF", secc.getHoraFin());
            valores.put("diasC", secc.getDiasClase());
            valores.put("cicloEsc", secc.getCicloEsc());
            valores.put("idMS", secc.getIdMat());
            db.insertOrThrow("Seccion", null, valores);
            db.setTransactionSuccessful();
            exito = true;
        }catch (Exception e){
            exito = false;
            Log.d("Error","Error al insertar seccion");
        }finally {
            db.endTransaction();
            db.close();
        }
        return exito;
    }
    public boolean eliminarSeccion(String id, String mat){
        boolean exito = false;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            //eliminar t-odo relacionado a esa seccion
            //Cla_T
            String count = "SELECT count(*) FROM Cla_T";
            Cursor mcursor = db.rawQuery(count, null);
            mcursor.moveToFirst();
            int icount = mcursor.getInt(0);
            if(icount > 0) {
                db.delete("Cla_T", "idSCT='" + id + "' AND idMCT='" + mat + "'", null);
            }
            //Cla_M
            count = "SELECT count(*) FROM Cla_M";
            mcursor = db.rawQuery(count, null);
            mcursor.moveToFirst();
            icount = mcursor.getInt(0);
            if(icount > 0) {
                db.delete("Cla_M", "idSC='" + id + "' AND idMcm='" + mat + "'", null);
            }
            //clases
            count = "SELECT count(*) FROM Clase";
            mcursor = db.rawQuery(count, null);
            mcursor.moveToFirst();
            icount = mcursor.getInt(0);
            if (icount > 0) {
                db.delete("Clase", "idSec='" + id + "' AND idSMC='" + mat + "'", null);
            }
            //criterios
            count = "SELECT count(*) FROM Criterio";
            mcursor = db.rawQuery(count, null);
            mcursor.moveToFirst();
            icount = mcursor.getInt(0);
            if ( icount > 0){
                db.delete("Criterio", "idS='" + id + "' AND idSMa='" + mat + "'", null);
            }
            //Asistencias
            count = "SELECT count(*) FROM Asistencias";
            mcursor = db.rawQuery(count, null);
            mcursor.moveToFirst();
            icount = mcursor.getInt(0);
            if (icount > 0) {
                db.delete("Asistencias", "idSA='" + id + "' AND idMA='" + mat + "'", null);
            }
            //Secc_Al
            count = "SELECT count(*) FROM Secc_Al";
            mcursor = db.rawQuery(count, null);
            mcursor.moveToFirst();
            icount = mcursor.getInt(0);
            if (icount > 0) {
                db.delete("Secc_Al", "idSCA='" + id + "' AND idMSA='" + mat + "'", null);
            }
            //Act_Al
            count = "SELECT count(*) FROM Act_Al";
            mcursor = db.rawQuery(count, null);
            mcursor.moveToFirst();
            icount = mcursor.getInt(0);
            if (icount > 0) {
                db.delete("Act_Al", "idSAA='" + id + "' AND idMs='" + mat + "'", null);
            }
            //Act_T
            count = "SELECT count(*) FROM Act_T";
            mcursor = db.rawQuery(count, null);
            mcursor.moveToFirst();
            icount = mcursor.getInt(0);
            if (icount > 0) {
                db.delete("Act_T", "idSAT='" + id + "' AND idMat='" + mat + "'", null);
            }
            //Actividades
            count = "SELECT count(*) FROM Actividad";
            mcursor = db.rawQuery(count, null);
            mcursor.moveToFirst();
            icount = mcursor.getInt(0);
            if (icount > 0) {
                db.delete("Actividad", "idSAct='" + id + "' AND idMSa='" + mat + "'", null);
            }
            //la seccion en si
            if (db.delete("Seccion", "idSecc='" + id + "' AND idMS='" + mat + "'", null) > 0) {
                db.setTransactionSuccessful();
                exito = true;
            }
        }catch (Exception e){
            exito = false;
            Log.d("Error","Error al eliminar seccion");
        }finally {
            db.endTransaction();
            db.close();
        }
        return exito;
    }
    public boolean faltaAsistencia(Seccion secc){
        boolean exito = false;
        SQLiteDatabase db = getReadableDatabase();
        db.beginTransaction();
        try{
            //checar fecha de hoy
            Calendar c = Calendar.getInstance();
            int dia = c.get(Calendar.DAY_OF_WEEK);
            char nDia;
            switch(dia) {
                case 1: nDia='d';break;
                case 2: nDia='l';break;
                case 3: nDia='m';break;
                case 4: nDia='i';break;
                case 5: nDia='j';break;
                case 6: nDia='v';break;
                case 7: nDia='s';break;
                default:nDia='x';
            }
            int hora = c.get(Calendar.HOUR_OF_DAY);
            int indice = secc.getHoraInicio().indexOf(':');
            String h = secc.getHoraInicio().substring(0, indice);
            int hC = Integer.parseInt(h);
            int minA = c.get(Calendar.MINUTE);
            for (int i = 0; i < secc.getDiasClase().length(); i++){
                if(nDia == secc.getDiasClase().charAt(i)){
                    if(hora > hC || (hora == hC && minA >= 15)){
                        String diaActual = Integer.toString(c.get(Calendar.DAY_OF_MONTH))+"-"+Integer.toString(c.get(Calendar.MONTH))+"-"+Integer.toString(c.get(Calendar.YEAR));
                        Cursor asis = db.rawQuery("SELECT count(*) FROM Asistencias WHERE fechaAs='"+diaActual+"'",null);
                        asis.moveToFirst();
                        int hay = asis.getInt(0);
                        if(hay < 1){
                            exito = true;
                        }
                    }
                }
            }
            //checar demás días
            db.setTransactionSuccessful();
        }catch (Exception e){
            exito = false;
            Log.d("Error","Error al recuperar fechas de clase");
        }finally {
            db.endTransaction();
            db.close();
        }
        return exito;
    }
    public ArrayList<Seccion> getSecciones(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from Seccion;",null);
        ArrayList<Seccion> secciones = new ArrayList<>();
        Seccion secc;
        try{
            if(resultSet.moveToFirst()){
                do{
                    secc = new Seccion(resultSet.getString(0), resultSet.getString(1),resultSet.getString(2),resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
                    secciones.add(secc);
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer secciones");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
                db.close();
            }
        }
        return secciones;
    }
    //metodos de Temas
    public boolean agregarTema(Tema tema){
        boolean exito = false;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues valores = new ContentValues();
            valores.put("idMat", tema.getIdMateria());
            valores.put("nombreTema", tema.getNombre());
            valores.put("descripcionTema", tema.getDescripcion());
            db.insertOrThrow("Tema", null, valores);
            db.setTransactionSuccessful();
            exito = true;
        }catch (Exception e){
            exito = false;
            Log.d("Error","Error al insertar tema");
        }finally {
            db.endTransaction();
            db.close();
        }
        return exito;
    }
    public ArrayList<Tema> getTemasMateria(Materia mat){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from Tema Where idMat='"+mat.getClave()+"';",null);
        ArrayList<Tema> temas = new ArrayList<>();
        Tema tema;
        try{
            if(resultSet.moveToFirst()){
                do{
                    tema = new Tema(resultSet.getString(0), resultSet.getString(1), resultSet.getString(2));
                    temas.add(tema);
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer temas de la materia");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
                db.close();
            }
        }
        return temas;
    }

    //metodos de materials
    public ArrayList<Material> getMateriales(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from Material;",null);
        ArrayList<Material> materiales = new ArrayList<>();
        Material mat;
        try{
            if(resultSet.moveToFirst()){
                do{
                    mat = new Material(resultSet.getInt(0), resultSet.getString(1));
                    materiales.add(mat);
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer materiales");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
                db.close();
            }
        }
        return materiales;
    }
    public int getNextIDMaterial(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select idMaterial from Material;",null);
        int id = 1;
        try{
            if(resultSet.moveToFirst()){
                do{
                    id = resultSet.getInt(0);
                }while(resultSet.moveToNext());
                id++;
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer materiales");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
            }
        }
        return id;
    }
    public boolean agregarMaterial(Material mat){
        boolean exito = false;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            mat.setId(getNextIDMaterial());
            ContentValues valores = new ContentValues();
            valores.put("idMaterial", mat.getId());
            valores.put("nombreMaterial", mat.getNombre());
            db.insertOrThrow("Material", null, valores);
            db.setTransactionSuccessful();
            exito = true;
        }catch (Exception e){
            exito = false;
            Log.d("Error","Error al insertar material");
        }finally {
            db.endTransaction();
            db.close();
        }
        return exito;
    }
    //metodos de clase
    public Clase getClase(Seccion secc, String fecha){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from Clase Where fechaClase='"+fecha+"' AND idSec='"+secc.getId()+"' AND idSMC='"+secc.getIdMat()+"';",null);
        Clase clase = null;
        try{
            if(resultSet.moveToFirst()){
                do{
                    ArrayList<Tema> temas = new ArrayList<>();
                    Cursor resultTemas = db.rawQuery("Select nomT from Cla_T Where fClase='"+fecha+"' AND idSCT='"+secc.getId()+"' AND idMCT='"+secc.getIdMat()+"';",null);
                    if (resultTemas.moveToFirst()){
                        do{
                            Tema t = new Tema(secc.getIdMat(),resultTemas.getString(0),"");
                            temas.add(t);
                        }while (resultTemas.moveToNext());
                    }
                    resultTemas.close();
                    ArrayList<Material> materiales = new ArrayList<>();
                    Cursor resultM = db.rawQuery("Select idMatCl from Cla_M Where fClass='"+fecha+"' AND idSC='"+secc.getId()+"' AND idMcm='"+secc.getIdMat()+"';",null);
                    if (resultM.moveToFirst()){
                        do{
                            Material t = new Material(resultM.getInt(0),"");
                            materiales.add(t);
                        }while (resultM.moveToNext());
                    }
                    resultM.close();
                    clase = new Clase(resultSet.getString(0),resultSet.getString(1),resultSet.getString(2),temas, materiales);
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer clase");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
            }
        }
        return clase;
    }
    public ArrayList<Event> getFechasClase(Seccion secc){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select fechaClase from Clase Where idSec='"+secc.getId()+"' AND idSMC='"+secc.getIdMat()+"';",null);
        ArrayList<Event> clases = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try{
            if(resultSet.moveToFirst()){
                do{
                    String fecha = resultSet.getString(0);
                    Date d = formatter.parse(fecha);
                    Event event = new Event(Color.BLUE,d.getTime());
                    clases.add(event);
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer clase");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
            }
        }
        return clases;
    }
    public boolean agregarClase(Seccion secc, Clase clase){
        boolean exito = false;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues valores = new ContentValues();
            valores.put("fechaClase", clase.getFecha());
            valores.put("nombreClase", clase.getNombre());
            valores.put("descripcionClase", clase.getDescrp());
            valores.put("idSec", secc.getId());
            valores.put("idSMC", secc.getIdMat());
            db.insertOrThrow("Clase", null, valores);
            //insertando en sus relaciones
            for(int i = 0; i < clase.getTemas().size(); i++) {
                valores = new ContentValues();
                valores.put("fClase", clase.getFecha());
                valores.put("idSCT", secc.getId());
                valores.put("nomT", clase.getTemas().get(i).getNombre());
                valores.put("idMCT", secc.getIdMat());
                db.insertOrThrow("Cla_T", null, valores);
            }
            for(int i = 0; i < clase.getMateriales().size(); i++) {
                valores = new ContentValues();
                valores.put("fClass", clase.getFecha());
                valores.put("idSC", secc.getId());
                valores.put("idMcm", secc.getIdMat());
                valores.put("idMatCl", clase.getMateriales().get(i).getId());
                db.insertOrThrow("Cla_M", null, valores);
            }
            db.setTransactionSuccessful();
            exito = true;
        }catch (Exception e){
            exito = false;
            Log.d("Error","Error al insertar clase");
        }finally {
            db.endTransaction();
            db.close();
        }
        return exito;
    }
    //metodos de actividades
    public boolean agregarActividad(Seccion secc, Actividad act){
        boolean exito = false;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues valores = new ContentValues();
            valores.put("fechaAct", act.getFecha());
            valores.put("tipoAct", act.getTipo());
            valores.put("nombreAct", act.getNombre());
            valores.put("descripAct", act.getDescrp());
            valores.put("ptAct", act.getPt());
            valores.put("idSAct",secc.getId());
            valores.put("idMSa", secc.getIdMat());
            db.insertOrThrow("Actividad", null, valores);
            //relacion con temas
            for (int i = 0; i < act.getTemas().size(); i++) {
                valores = new ContentValues();
                valores.put("nombreAT", act.getNombre());
                valores.put("idSAT", secc.getId());
                valores.put("nTema", act.getTemas().get(i).getNombre());
                valores.put("idMat", secc.getIdMat());
                db.insertOrThrow("Act_T", null, valores);
            }
            db.setTransactionSuccessful();
            exito = true;
        }catch (Exception e){
            exito = false;
            Log.d("Error","Error al insertar actividad");
        }finally {
            db.endTransaction();
            db.close();
        }
        return exito;
    }
    public double calcularPt(String tipo, Seccion secc){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from Actividad Where idSAct='"+secc.getId()+"' AND idMSa='"+secc.getIdMat()+"' AND tipoAct='"+tipo+"';",null);
        Cursor porcentajeTipo = db.rawQuery("Select * from Criterio Where idS='"+secc.getId()+"' AND idSMa='"+secc.getIdMat()+"' AND nombreCr='"+tipo+"';",null);
        Double pt = 0.0;
        int nAct = 1;
        try{
            porcentajeTipo.moveToFirst();
            pt = porcentajeTipo.getDouble(1);
            if(resultSet.moveToFirst()){
                do{
                    nAct++;
                } while(resultSet.moveToNext());
                pt = pt/nAct;
                resultSet.moveToFirst();
                do{
                    ContentValues valores = new ContentValues();
                    valores.put("ptAct", pt);
                    if(db.update("Actividad",valores, "nombreAct='"+resultSet.getString(2)+"' AND tipoAct='" + tipo + "' AND idSAct='" + secc.getId() + "' AND idMSa='" + secc.getIdMat() + "'", null) > 0){
                        nAct--;
                    }
                }while (resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer actividades");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
            }
        }
        return pt;
    }
    public ArrayList<Actividad> getActividades(Seccion secc){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from Actividad Where idSAct='"+secc.getId()+"' AND idMSa='"+secc.getIdMat()+"';",null);
        ArrayList<Actividad> actividades = new ArrayList<>();
        try{
            if(resultSet.moveToFirst()){
                do{
                    Cursor t = db.rawQuery("Select * from Act_T Where nombreAT='"+resultSet.getString(2)+"' AND idSAT='"+secc.getId()+"' AND idMat='"+secc.getIdMat()+"';",null);
                    t.moveToFirst();
                    ArrayList<Tema> temas = new ArrayList<>();
                    do{
                        Tema tm = new Tema(secc.getIdMat(),t.getString(2),"");
                        temas.add(tm);
                    }while (t.moveToNext());
                    Actividad act = new Actividad(resultSet.getString(0), resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4), temas);
                    actividades.add(act);
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer actividades");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
            }
        }
        return actividades;
    }

    public boolean eliminarCriterio(Seccion secc, Criterio cr){
        boolean exito = false;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            if (db.delete("Criterio", "nombreCr='" + cr.getNombre() + "' AND idS='" + secc.getId() + "' AND idSMa='" + secc.getIdMat() + "'", null) > 0) {
                db.setTransactionSuccessful();
                exito = true;
            }
        }catch (Exception e){
            exito = false;
            Log.d("Error","Error al eliminar criterio");
        }finally {
            db.endTransaction();
            db.close();
        }
        return exito;
    }
    public boolean modificarCriterio(Seccion secc, Criterio cr){
        boolean exito = false;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues valores = new ContentValues();
            valores.put("porcentajeCr", cr.getPorcentaje());
            if(db.update("Criterio",valores, "nombreCr='" + cr.getNombre() + "' AND idS='" + secc.getId() + "' AND idSMa='" + secc.getIdMat() + "'", null) > 0){
                db.setTransactionSuccessful();
                exito = true;
            }
        }catch (Exception e){
            exito = false;
            Log.d("Error","Error al insertar criterio");
        }finally {
            db.endTransaction();
            db.close();
        }
        return exito;
    }

    public boolean agregarCriterio(Seccion secc, Criterio cr){
        boolean exito = false;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues valores = new ContentValues();
            valores.put("nombreCr", cr.getNombre());
            valores.put("porcentajeCr", cr.getPorcentaje());
            valores.put("idS", secc.getId());
            valores.put("idSMa", secc.getIdMat());
            db.insertOrThrow("Criterio", null, valores);
            db.setTransactionSuccessful();
            exito = true;
        }catch (Exception e){
            exito = false;
            Log.d("Error","Error al insertar criterio");
        }finally {
            db.endTransaction();
            db.close();
        }
        return exito;
    }

    public ArrayList<Criterio> getCriterios(Seccion secc){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from Criterio Where idS='"+secc.getId()+"' AND idSMa='"+secc.getIdMat()+"';",null);
        ArrayList<Criterio> criterios = new ArrayList<>();
        try{
            if(resultSet.moveToFirst()){
                do{
                    Criterio ct = new Criterio(resultSet.getString(0), resultSet.getDouble(1));
                    criterios.add(ct);
                }while(resultSet.moveToNext());
            }
        }catch (Exception e) {
            Log.d(TAG, "Error al leer criterios");
        }finally {
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
            }
        }
        return criterios;
    }

}
