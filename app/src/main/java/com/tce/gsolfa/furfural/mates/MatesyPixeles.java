package com.tce.gsolfa.furfural.mates;




import com.tce.gsolfa.furfural.SQLite.Calibrado;
import android.graphics.Bitmap;
import android.graphics.Color;


public class MatesyPixeles {
	private final int MODPUNTO=10;
	
	//Obtengo el verde, rojo y azul promedio del centro de un bitmap
	private int verdePromedio(Bitmap bitmap){
		int sumaV=0,verdePromedio, greenValue=0;
        int[] pixels=new int[MODPUNTO*MODPUNTO];
        int x=(bitmap.getWidth()/2)-(MODPUNTO/2);
        int y=(bitmap.getHeight()/2)-(MODPUNTO/2);
        try{
			bitmap.getPixels(pixels, 0, 10,x ,y , MODPUNTO, MODPUNTO);
			for(int i=0;i<pixels.length;i++){
        		greenValue = Color.green(pixels[i]);
    	        sumaV+=greenValue;
        	}
        	verdePromedio=(sumaV/pixels.length);
        }catch(IllegalArgumentException e){
        	verdePromedio=-1;
        }
		return verdePromedio;
	}
	
	private int azulPromedio(Bitmap bitmap){
		int sumaA=0,azulPromedio, azulValue=0;
        int[] pixels=new int[MODPUNTO*MODPUNTO];
        int x=(bitmap.getWidth()/2)-(MODPUNTO/2);
        int y=(bitmap.getHeight()/2)-(MODPUNTO/2);
        try{
			bitmap.getPixels(pixels, 0, 10,x ,y , MODPUNTO, MODPUNTO);
			for(int i=0;i<pixels.length;i++){
        		azulValue = Color.blue(pixels[i]);
    	        sumaA+=azulValue;
        	}
        	azulPromedio=(sumaA/pixels.length);
        }catch(IllegalArgumentException e){
        	azulPromedio=-1;
        }
		return azulPromedio;
	}
	
	private int rojoPromedio(Bitmap bitmap){
		int sumaR=0,rojoPromedio, rojoValue=0;
        int[] pixels=new int[MODPUNTO*MODPUNTO];
        int x=(bitmap.getWidth()/2)-(MODPUNTO/2);
        int y=(bitmap.getHeight()/2)-(MODPUNTO/2);
        try{
			bitmap.getPixels(pixels, 0, 10,x ,y , MODPUNTO, MODPUNTO);
			for(int i=0;i<pixels.length;i++){
        		rojoValue = Color.red(pixels[i]);
    	        sumaR+=rojoValue;
        	}
        	rojoPromedio=(sumaR/pixels.length);
        }catch(IllegalArgumentException e){
        	rojoPromedio=-1;
        }
		return rojoPromedio;
	}
	
	//Calculo de (R-G)/B 
	public double promedioRGB(Bitmap bitmap){
		double verde=verdePromedio(bitmap)+1;
		double rojo=rojoPromedio(bitmap)+1;
		double azul=azulPromedio(bitmap)+1;

		return (rojo-verde)/azul;
	}
	
	//Calculo de la concentracion en funcion del (R-G)/B
	public int aplicarFormula(double pRGB, Calibrado calibrado){
		
		double	concentracionFurfural=(pRGB-calibrado.getOrdenadaOrigen())/(calibrado.getPendiente());
		
		int cc=(int) concentracionFurfural;
		return cc;
		
	}
	
	public double calcularRGBaPartirdeConcentracion(int concentracion, Calibrado cal){
		double rgb=(cal.getPendiente()*concentracion)+cal.getOrdenadaOrigen();
		return rgb;
		
		
	}

	

}
