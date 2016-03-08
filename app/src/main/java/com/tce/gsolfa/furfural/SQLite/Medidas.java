package com.tce.gsolfa.furfural.SQLite;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class Medidas {
	
	private SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private long id;
    private long fecha;
    private long fechaDelCalibrado;
    private String sampleCode;
    private String user;
    private int concentracionFurfural;
    private byte[] imagen; 
	
    public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getFecha() {
		return fecha;
	}
	public void setFecha(long fecha) {
		this.fecha = fecha;
	}
	public long getFechaDelCalibrado() {
		return fechaDelCalibrado;
	}
	public void setFechaDelCalibrado(long fechaDelCalibrado) {
		this.fechaDelCalibrado = fechaDelCalibrado;
	}
	public String getSampleCode() {
		return sampleCode;
	}
	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getConcentracionFurfural() {
		return concentracionFurfural;
	}
	public void setConcentracionFurfural(int concentracionFurfural) {
		this.concentracionFurfural = concentracionFurfural;
	}
	@Override
	public String toString() {
		
		return "\nSample code: "+sampleCode+"\nUser: "+user+"\nCalibrate: "+sdf.format(new Date(fechaDelCalibrado))+"\nFurfural concentration: "+concentracionFurfural+" µg/L";
	}
	
	
}
