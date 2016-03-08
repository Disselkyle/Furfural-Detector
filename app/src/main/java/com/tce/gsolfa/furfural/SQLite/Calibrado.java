package com.tce.gsolfa.furfural.SQLite;


import java.text.DecimalFormat;
import java.util.ArrayList;



public class Calibrado {
	
	
	private DecimalFormat dc=new DecimalFormat("#0.0000");
	
	
	private long fecha;
	private long id;
	private ArrayList<String> puntosCalibrado;
    
    private double pendiente;
    private double ordenadaOrigen;
    private double r;
    private int numeroPuntos;
	
    
    
    
   public String convertirArrayPuntosEnString(ArrayList<String> puntos) {
		StringBuilder puntosCalibrado=new StringBuilder();
		
		for(int i=0;i<puntos.size();i++){
			if(i==0){
				puntosCalibrado.append(puntos.get(i));
			}
			else{
				puntosCalibrado.append("p"+puntos.get(i));
			}
			
		}
		return puntosCalibrado.toString();
	}
    
    public ArrayList<String> pasardeStringaArray(String puntos){
		
    	String[] valores=puntos.split("p");
		ArrayList<String> puntoenArray=new ArrayList<String>();
		for(int i=0;i<valores.length;i++){
			puntoenArray.add(valores[i]);
		}
		return puntoenArray;
	}
    
    
    public void minimosCuadrados3() {
		double Sx=0, Sy=0, Sxx=0, Syy=0, Sxy=0;
		int N=0;
		
		ArrayList<String> listaCons=this.puntosCalibrado;
		for(String con:listaCons){
			String[] dos=con.split(";");
			String pRGBString=dos[0];
			String concentracion=dos[1];
			
			double ccFloat=0;
			double pRGBDouble=0;
			
			try{
				ccFloat=Float.parseFloat(concentracion);
				pRGBDouble=Double.parseDouble(pRGBString);
			}catch(Exception e){
				
			}
			Sx+=ccFloat;
			Sy+=pRGBDouble;
			Sxx+=Math.pow(ccFloat, 2);
			Syy+=Math.pow(pRGBDouble, 2);
			Sxy+=(ccFloat*pRGBDouble);
			
			N++;
		}
		
		double p=((N*Sxy)-(Sx*Sy))/((N*Sxx)-(Sx*Sx));
		double oo=((Sxx*Sy)-(Sx*Sxy))/((N*Sxx)-(Sx*Sx));
		double r=((N*Sxy)-(Sx*Sy))/(Math.sqrt(((N*Sxx)-(Sx*Sx)))*Math.sqrt(((N*Syy)-(Sy*Sy))));
		this.pendiente=p;
		this.ordenadaOrigen=oo;
		this.r=r;
		this.numeroPuntos=N;
    
    }
    
    public void setNumeroPuntos(int numeroPuntos) {
		this.numeroPuntos = numeroPuntos;
	}

	public double getPendiente() {
		return pendiente;
	}

	public double getOrdenadaOrigen() {
		return ordenadaOrigen;
	}

	public double getR() {
		return r;
	}

	public int getNumeroPuntos() {
		return numeroPuntos;
	}

	public long getFecha() {
		return fecha;
	}
	public void setFecha(long fecha) {
		this.fecha = fecha;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ArrayList<String> getPuntosCal() {
		return puntosCalibrado;
	}
	public void setPuntosCal(ArrayList<String> puntosCal) {
		this.puntosCalibrado = puntosCal;
	}
    
	@Override
	public String toString() {
		
		return "\nRGB = a * [Furfural] + b\na = "+dc.format(pendiente)+"\nb = "+ dc.format(ordenadaOrigen)+"\nr = " + new DecimalFormat("#0.00000").format(r)+ "\nN = " + numeroPuntos;
	}
    
	
}
