package com.tce.gsolfa.furfural.SQLite;

import java.util.ArrayList;
import java.util.List;

import com.tce.gsolfa.furfural.SQLite.MySQLiteOpenHelper.TablaCalibrados;
import com.tce.gsolfa.furfural.SQLite.MySQLiteOpenHelper.TablaMedidas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CalibradosDataSource {
	private SQLiteDatabase db;
    private MySQLiteOpenHelper dbHelper;
    private String[] columnas = { TablaCalibrados.COLUMNA_ID,
    		TablaCalibrados.COLUMNA_FECHA, TablaCalibrados.COLUMNA_PUNTOSCALIBRADO};
    
    private String[] columnasMedidas = { TablaMedidas.COLUMNA_ID,
    		TablaMedidas.COLUMNA_FECHA, TablaMedidas.COLUMNA_FECHACALIBRADO, 
    		TablaMedidas.COLUMNA_SAMPLECODE, TablaMedidas.COLUMNA_USER, 
    		TablaMedidas.COLUMNA_CONCENTRACIONFURFURAL, TablaMedidas.COLUMNA_IMAGEN};
    
    public CalibradosDataSource(Context context) {
        dbHelper = new MySQLiteOpenHelper(context);
    }
    
    public void open() {
        db = dbHelper.getWritableDatabase();
    }
 
    public void close() {
        dbHelper.close();
    }
    
    public void crearCalibrado(long fecha, String puntosCalibrado) {
        ContentValues values = new ContentValues();
        values.put(TablaCalibrados.COLUMNA_FECHA, fecha);
        values.put(TablaCalibrados.COLUMNA_PUNTOSCALIBRADO, puntosCalibrado);
        db.insert(TablaCalibrados.TABLA_CALIBRADOS, null, values);
    }
    
    public void crearMedida(long fecha, long fechaCalibrado, String sampleCode, String user, int concentracion, byte[] imagen) {
        ContentValues values = new ContentValues();
        values.put(TablaMedidas.COLUMNA_FECHA, fecha);
        values.put(TablaMedidas.COLUMNA_FECHACALIBRADO, fechaCalibrado);
        values.put(TablaMedidas.COLUMNA_SAMPLECODE, sampleCode);
        values.put(TablaMedidas.COLUMNA_USER, user);
        values.put(TablaMedidas.COLUMNA_CONCENTRACIONFURFURAL, concentracion);
        values.put(TablaMedidas.COLUMNA_IMAGEN, imagen);
        db.insert(TablaMedidas.TABLA_MEDIDAS, null, values);
    }
    
    public List<Calibrado> getAllCalibrados() {
        List<Calibrado> listaCalibrados = new ArrayList<Calibrado>();
 
        Cursor cursor = db.query(TablaCalibrados.TABLA_CALIBRADOS, columnas, null, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Calibrado nuevoCalibrado = cursorToCalibrado(cursor);
            listaCalibrados.add(nuevoCalibrado);
            cursor.moveToNext();
        }
 
        cursor.close();
        return listaCalibrados;
    }
    
    public List<Medidas> getAllMedidas() {
        List<Medidas> listaMedidas = new ArrayList<Medidas>();
 
        Cursor cursor = db.query(TablaMedidas.TABLA_MEDIDAS, columnasMedidas, null, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Medidas nuevaMedida = cursorToMedida(cursor);
            listaMedidas.add(nuevaMedida);
            cursor.moveToNext();
        }
 
        cursor.close();
        return listaMedidas;
    }
    
    public void borrarCalibrado(Calibrado calibrado) {
        long id = calibrado.getId();
        db.delete(TablaCalibrados.TABLA_CALIBRADOS, TablaCalibrados.COLUMNA_ID + " = " + id,
                null);
    }
    
    public void borrarCalibrado(int id) {
      
        db.delete(TablaCalibrados.TABLA_CALIBRADOS, TablaCalibrados.COLUMNA_ID + " = " + id,
                null);
    }
    public void borrarCalibrado(long fecha) {
        
        db.delete(TablaCalibrados.TABLA_CALIBRADOS, TablaCalibrados.COLUMNA_FECHA + " = " + fecha,
                null);
    }
    
    public void borrarMedida(long fecha) {
        
        db.delete(TablaMedidas.TABLA_MEDIDAS, TablaMedidas.COLUMNA_FECHA + " = " + fecha,
                null);
    }
    
    public void borrarMedida(Medidas medida) {
        long id = medida.getId();
        db.delete(TablaMedidas.TABLA_MEDIDAS, TablaMedidas.COLUMNA_ID + " = " + id,
                null);
    }
    
    public void borrarMedida(int id) {
      
    	db.delete(TablaMedidas.TABLA_MEDIDAS, TablaMedidas.COLUMNA_ID + " = " + id,
                null);
    }
    
    public Calibrado seleccionarCalibradoPorID(int id) {
    	Calibrado nuevoCalibrado= new Calibrado();
    	Cursor cursor = db.query(TablaCalibrados.TABLA_CALIBRADOS, columnas, TablaCalibrados.COLUMNA_ID + " = " + id, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            nuevoCalibrado = cursorToCalibrado(cursor);
            cursor.moveToNext();
        }
 
        cursor.close();
        return nuevoCalibrado;
    	
    }
    
    public Medidas seleccionarMedidaPorID(int id) {
    	Medidas nuevamedida= new Medidas();
    	Cursor cursor = db.query(TablaMedidas.TABLA_MEDIDAS, columnasMedidas, TablaMedidas.COLUMNA_ID + " = " + id, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	nuevamedida = cursorToMedida(cursor);
            cursor.moveToNext();
        }
 
        cursor.close();
        return nuevamedida;
    	
    }
    
    private Calibrado cursorToCalibrado(Cursor cursor) {
        Calibrado calibrado = new Calibrado();
        calibrado.setId(cursor.getLong(0));
        calibrado.setFecha(cursor.getLong(1));
        calibrado.setPuntosCal(calibrado.pasardeStringaArray(cursor.getString(2)));
        calibrado.minimosCuadrados3();
        return calibrado;
    }
    
    private Medidas cursorToMedida(Cursor cursor) {
    	Medidas nuevamedida= new Medidas();
    	nuevamedida.setId(cursor.getLong(0));
    	nuevamedida.setFecha(cursor.getLong(1));
    	nuevamedida.setFechaDelCalibrado(cursor.getLong(2));
    	nuevamedida.setSampleCode(cursor.getString(3));
    	nuevamedida.setUser(cursor.getString(4));
    	nuevamedida.setConcentracionFurfural(cursor.getInt(5));
    	nuevamedida.setImagen(cursor.getBlob(6));
        return nuevamedida;
    }
    
   
}
