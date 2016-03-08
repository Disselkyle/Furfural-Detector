package com.tce.gsolfa.furfural.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "Furfural";
    private static final int DATABASE_VERSION = 1;
    
    public static class TablaCalibrados{
        public static String TABLA_CALIBRADOS = "calibrados";
        public static String COLUMNA_ID = "_id";
        public static String COLUMNA_FECHA = "fecha";
        public static String COLUMNA_PUNTOSCALIBRADO = "puntosCalibrado";
    }
    
    public static class TablaMedidas{
        public static String TABLA_MEDIDAS = "medidas";
        public static String COLUMNA_ID = "_id";
        public static String COLUMNA_FECHA = "fecha";
        public static String COLUMNA_FECHACALIBRADO = "fechacalibrado";
        public static String COLUMNA_SAMPLECODE = "samplecode";
        public static String COLUMNA_USER = "user";
        public static String COLUMNA_CONCENTRACIONFURFURAL = "concentracionfurufral";
        public static String COLUMNA_IMAGEN = "imagen";
    }
    
    private static final String DATABASE_CREATE_TABLACALIBRADOS = "create table "
            + TablaCalibrados.TABLA_CALIBRADOS 
            + "(" + TablaCalibrados.COLUMNA_ID + " integer primary key autoincrement, " 
    		+TablaCalibrados.COLUMNA_FECHA + " long not null, "
            +TablaCalibrados.COLUMNA_PUNTOSCALIBRADO+" text no null);";
	
    private static final String DATABASE_CREATE_TABLAMEDIDAS = "create table "
            + TablaMedidas.TABLA_MEDIDAS 
            + "(" + TablaMedidas.COLUMNA_ID + " integer primary key autoincrement, " 
    		+TablaMedidas.COLUMNA_FECHA + " long not null, "
            +TablaMedidas.COLUMNA_FECHACALIBRADO + " long not null, "
    		+TablaMedidas.COLUMNA_SAMPLECODE + " text no null, "
            +TablaMedidas.COLUMNA_USER + " text no null, "
    		+TablaMedidas.COLUMNA_CONCENTRACIONFURFURAL + " integer not null, "
            +TablaMedidas.COLUMNA_IMAGEN+" blob no null);";
	
    
    
    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(DATABASE_CREATE_TABLACALIBRADOS);
		db.execSQL(DATABASE_CREATE_TABLAMEDIDAS);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("delete table if exists " + TablaCalibrados.TABLA_CALIBRADOS);
		db.execSQL("delete table if exists " + TablaMedidas.TABLA_MEDIDAS);
	    onCreate(db);

	}

}
