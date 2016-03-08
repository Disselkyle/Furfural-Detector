package com.tce.gsolfa.furfural.mates;

public interface Constantes {
	public static final String KEY_NUMEROPUNTOS="com.tce.gsolfa.furfural.numberofpoints";
	public static final String KEY_CONCENTRACION="com.tce.gsolfa.furfural.concentracion";
	public static final String KEY_CALIBRANDO="com.tce.gsolfa.furfural.calibrando";
	public static final String KEY_SAMPLECODE="com.tce.gsolfa.furfural.samplecode";
	public static final String KEY_USER="com.tce.gsolfa.furfural.user";
	public static final String KEY_ID="com.tce.gsolfa.furfural.id";
	public static final String KEY_MEDIDAGUARDADA="com.tce.gsolfa.furfural.medidaGuardada";
	
	public static final int NUMBER_OF_POINTS=5;
	public static final double CONCENTRACION_DEFECTO=0;
	public static final boolean CALIBRANDO=false;
	public static final int ID_CALIBRADO=0;
	
	public static final int SELECT_GALLERY=0;
	public static final int SELECT_CAMERA=1;
	public static final int TAKE_PICTURE = 1;
	public static final int SELECT_PICTURE = 2;
	public static final int PIC_CROP = 3;
	public static final int PIC_CROP2=4;
	public static final int CONCENTRACION_SELECCIONADA=5;
	
	public static final int GONE=8; //Para que no se vea un boton pasarle este parametro a setvisibility()
	public static final int INVISIBLE=4;
	public static final int VISIBLE=0;
}
