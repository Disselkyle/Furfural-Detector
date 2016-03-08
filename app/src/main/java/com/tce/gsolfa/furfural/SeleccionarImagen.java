package com.tce.gsolfa.furfural;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.tce.gsolfa.furfural.SQLite.Calibrado;
import com.tce.gsolfa.furfural.SQLite.Medidas;
import com.tce.gsolfa.furfural.mates.MatesyPixeles;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

public class SeleccionarImagen extends Plantilla {
	
	//Controlamos si vamos a calibrar o no con este booleano
	private boolean calibre=false;
	
	private Uri selectedImage; 
	private Bitmap bitmap;
	
	
	private double concentracionPunto; //Aqui almaceno la concentracion introducida por el usuario
	private double rgb; 				//Aqui almaceno el valor rgb de cada punto
	private ArrayList<String> puntos;	//Aqui almaceno los puntos como strings
	private MatesyPixeles m;			//Este lo uso para gestionar el color y obtener rgb
	public static Medidas medida;
	public static Calibrado calibrado;
	
	private int numeroMedidas=0;		//Con este controlo el bucle de crop
	private String concentraciones;
	private StringBuilder st;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seleccionarimagen);
		
		
		//Primero obtengo el booleano que me va a indicar si estoy calibrando o midiendo
		calibre=getIntent().getBooleanExtra(KEY_CALIBRANDO, CALIBRANDO);
		
		//Inicializo la clase pixeles
		m=new MatesyPixeles();
		st=new StringBuilder("Inserted values:");
		//Si estoy calibrando, inicializo el ArrayList de puntos y el calibrado
		if(calibre){
			puntos=new ArrayList<String>();
			calibrado=new Calibrado();
			//Recojo el dato aportado por el usuario de numero de puntos y lo guardo en el calibrado
			calibrado.setNumeroPuntos(getIntent().getIntExtra(KEY_NUMEROPUNTOS, NUMBER_OF_POINTS));
		}
		//Inicializo medidas y guardo los datos de user y sampleCode aportados por el usuario
		else{
			
			medida=new Medidas();
			medida.setSampleCode(getIntent().getStringExtra(KEY_SAMPLECODE));
			medida.setUser(getIntent().getStringExtra(KEY_USER));
			
		}
		//Inicio la gestion de la foto
		takeImageDialog();
	}
	
	private void takeImageDialog() {
		new AlertDialog.Builder(this).setTitle(R.string.title_TakeFoto).setItems(R.array.select_image, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialoginterface, int i) {
				takeImage(i);
			}
		}).show();
	}
	
	private void takeImage(int img){
		Intent intent=  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	int code = TAKE_PICTURE;
		
		switch (img) {
		case SELECT_CAMERA:
			break;
		case SELECT_GALLERY:
			intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
    	    code = SELECT_PICTURE;
			break;
		
		}
		startActivityForResult(intent, code);
	}
	
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Intent i;
		if(resultCode==RESULT_OK){
			if(requestCode==TAKE_PICTURE||requestCode==SELECT_PICTURE){
				selectedImage = data.getData();
				if(calibre){
					calibrar();
				}
				else{
					performCrop();
				}
				
			}
			else if(requestCode==CONCENTRACION_SELECCIONADA){
				concentracionPunto=data.getDoubleExtra(KEY_CONCENTRACION, CONCENTRACION_DEFECTO);
				performCrop();
			}
			else if(requestCode==PIC_CROP){
				
				//get the returned data
				Bundle extras = data.getExtras();
				//get the cropped bitmap
				bitmap = extras.getParcelable("data");
				
				rgb=m.promedioRGB(bitmap);
				
				medida.setFecha(System.currentTimeMillis());
				medida.setConcentracionFurfural(m.aplicarFormula(rgb, calibrado));
				medida.setImagen(getByteArray(bitmap));
				medida.setFechaDelCalibrado(calibrado.getFecha());
				
				
				
				i=new Intent(SeleccionarImagen.this, VistaMedidas.class);
				i.putExtra(KEY_MEDIDAGUARDADA, false);
				startActivity(i);
				

			}
			else if(requestCode==PIC_CROP2){
				
				//get the returned data
				Bundle extras = data.getExtras();
				//get the cropped bitmap
				bitmap = extras.getParcelable("data");
				rgb=m.promedioRGB(bitmap);
				
				puntos.add(rgb+";"+concentracionPunto);
				numeroMedidas++;
				st.append(" "+concentracionPunto);
				concentraciones=st.toString();
				if(numeroMedidas<calibrado.getNumeroPuntos()){
					calibrar();
				}
				else{
					
					calibrado.setFecha(System.currentTimeMillis());
					calibrado.setPuntosCal(puntos);
					calibrado.minimosCuadrados3();
					
					i=new Intent(SeleccionarImagen.this, VistaCalibrado.class);
					i.putExtra(KEY_ID, -1);
					startActivity(i);
					
				}
			}
		}else{
			i=new Intent(SeleccionarImagen.this, InicioActivity.class);
			startActivity(i);
		}
	}
	
	private void performCrop(){
		int valor=PIC_CROP;
		if(calibre){
			valor=PIC_CROP2;
		}
		try {
			 //call the standard crop action intent (the user device may not support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			    //indicate image type and Uri
			cropIntent.setDataAndType(selectedImage, "image/*");
			    //set crop properties
			cropIntent.putExtra("crop", "true");
			    //indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			    //indicate output X and Y
			cropIntent.putExtra("outputX", 150);
			cropIntent.putExtra("outputY", 150);
			    //retrieve data on return
			cropIntent.putExtra("return-data", true);
			    //start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, valor);
			
		}
		catch(ActivityNotFoundException anfe){
		    //display an error message
		    String errorMessage = "Whoops - your device doesn't support the crop action!";
		    Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
		    toast.show();
		}
	
	}
	
	private void calibrar(){
		Intent i=new Intent(SeleccionarImagen.this, InsertarConcentracion.class);
		i.putExtra("concentraciones", concentraciones);
		startActivityForResult(i, CONCENTRACION_SELECCIONADA);
		
	}
	
	public byte[] getByteArray(Bitmap bitmap) {
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    bitmap.compress(CompressFormat.PNG, 0, bos);
	    return bos.toByteArray();
	}
}
