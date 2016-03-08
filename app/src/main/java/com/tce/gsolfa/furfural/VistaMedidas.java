package com.tce.gsolfa.furfural;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tce.gsolfa.furfural.SQLite.CalibradosDataSource;
import com.tce.gsolfa.furfural.SQLite.Medidas;
import com.tce.gsolfa.furfural.mates.GenerarPDF;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
@SuppressLint("SimpleDateFormat")
public class VistaMedidas extends Plantilla implements OnClickListener{
	
	private Button info;
	private Button back;
	
	private Button nuevaMedida;
	private Button save;
	private Button delete;
	private Button pdf;
	
	private TextView titulo;
	private TextView resultados;
	
	private ImageView imagen;
	
	private CalibradosDataSource dataSource;
	private Medidas medida;
	private SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private boolean medidaGuardada=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vistamedida);
		
		info=(Button) findViewById(R.id.BotonInfo);
		info.setVisibility(INVISIBLE);
		back=(Button) findViewById(R.id.BotonBack);
		back.setOnClickListener(this);
		nuevaMedida=(Button) findViewById(R.id.botonNuevaMedida);
		nuevaMedida.setOnClickListener(this);
		save=(Button) findViewById(R.id.botonSave);
		save.setOnClickListener(this);
		delete=(Button) findViewById(R.id.botonDelete);
		delete.setOnClickListener(this);
		pdf=(Button) findViewById(R.id.botonPDF);
		pdf.setOnClickListener(this);
		
		medida=SeleccionarImagen.medida;
		dataSource = new CalibradosDataSource(this);
        dataSource.open();
		
        titulo=(TextView) findViewById(R.id.tituloInicio);
		titulo.setText(sdf.format(new Date(medida.getFecha())));
		resultados=(TextView) findViewById(R.id.textoDatosMedida);
		resultados.setText(medida.toString());
		imagen=(ImageView)findViewById(R.id.imagengRecorteFoto);
		imagen.setImageBitmap(getBitmap(medida.getImagen()));
		
		medidaGuardada=getIntent().getBooleanExtra(KEY_MEDIDAGUARDADA, false);
		
		//Si vengo de hacer la medida
		if(!medidaGuardada){
			delete.setVisibility(INVISIBLE);
			delete.setClickable(false);
			pdf.setVisibility(INVISIBLE);
			pdf.setClickable(false);
		}
		
		
	}
	
	
	@Override
	public void onClick(View v) {
		Intent i;
		AlertDialog.Builder builder;
		
		switch (v.getId()) {
		//Con el boton back y con save y delete voy a lista de medidas.
		//Con new measure voy insertsamplecode
		case R.id.BotonBack:
			i=new Intent(this, ListaMedidas.class);
			startActivity(i);
			break;
		case R.id.botonNuevaMedida:
			if(SeleccionarImagen.calibrado==null){
				Toast toast = Toast.makeText(this,"Please select a calibrate before you start the measure", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show(); 
			}else{
				i=new Intent(this, InsertarSampleCode.class);
				startActivity(i);
			}
			break;
		case R.id.botonSave:
			builder = new AlertDialog.Builder(this)
	        .setTitle("Save Measure")
	        .setMessage("¿Do you wish to save this measure?")
	        .setPositiveButton("Accept",
	                new DialogInterface.OnClickListener() {
	
	                    @Override
	                    public void onClick(DialogInterface arg0, int arg1) {
	                        
	                    	dataSource.crearMedida(medida.getFecha(), medida.getFechaDelCalibrado(), medida.getSampleCode(), medida.getUser(), medida.getConcentracionFurfural(), medida.getImagen());
	                    	
	                    	medidaGuardada=true;
	                    	save.setClickable(false);
	                    	save.setVisibility(INVISIBLE);
	        				delete.setVisibility(VISIBLE);
	        				delete.setClickable(true);
	        				pdf.setVisibility(VISIBLE);
	        				pdf.setClickable(true);
	        				
	                        
	                    }
	            })
	
	        .setNegativeButton("Cancel",
	                new DialogInterface.OnClickListener() {
	
	                    @Override
	                    public void onClick(DialogInterface dialog,
	                            int which) {
	                        
	                        return;
	                    }
	            });
			builder.show();
			break;
		case R.id.botonDelete:
			builder = new AlertDialog.Builder(this)
	        .setTitle("Delete Measure")
	        .setMessage("¿Do you wish to delete this measure?")
	        .setPositiveButton("Accept",
	                new DialogInterface.OnClickListener() {
	
	                    @Override
	                    public void onClick(DialogInterface arg0, int arg1) {
	                        
	                    	dataSource.borrarMedida(medida.getFecha());
	                    	delete.setVisibility(INVISIBLE);
	        				delete.setClickable(false);
	                    	
	                    }
	            })
	
	        .setNegativeButton("Cancel",
	                new DialogInterface.OnClickListener() {
	
	                    @Override
	                    public void onClick(DialogInterface dialog,
	                            int which) {
	                        
	                        return;
	                    }
	            });
			builder.show();
			break;
		case R.id.botonPDF:
			Toast toast;
			boolean pdfGenerado=new GenerarPDF().crearPDF(medida);
			if(pdfGenerado){
				toast = Toast.makeText(this,"PDF Generated", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}else{
				toast = Toast.makeText(this,"Error generating PDF", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			break;
		}
		
	}
	
	//metodo convertir byte[] en bitmap
	public Bitmap getBitmap(byte[] bitmap) {
	    return BitmapFactory.decodeByteArray(bitmap , 0, bitmap.length);
	}
}
