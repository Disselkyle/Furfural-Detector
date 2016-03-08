package com.tce.gsolfa.furfural;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InsertarConcentracion extends Plantilla implements OnClickListener, OnTouchListener{
	
	Button info;
	Button back;
	Button continuar;
	EditText concentracion;
	TextView inserted;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insertarconcentracion);
		
		info=(Button) findViewById(R.id.BotonInfo);
		info.setVisibility(GONE);
		back=(Button) findViewById(R.id.BotonBack);
		back.setOnClickListener(this);
		continuar=(Button) findViewById(R.id.botonContinue);
		continuar.setOnClickListener(this);
		continuar.setClickable(false);
		concentracion=(EditText) findViewById(R.id.editConcentracion);
		concentracion.setOnTouchListener(this);
		inserted=(TextView) findViewById(R.id.inserted);
		String concentraciones=getIntent().getStringExtra("concentraciones");
		inserted.setText(concentraciones);
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.BotonBack:
			Intent i=new Intent(this, ListaCalibrados.class);
			startActivity(i);
			break;
		case R.id.botonContinue:
			//Implementar pasando parametros : numero de puntos
			double cc=convertirConcentracionADouble(concentracion.getText().toString());
			if(cc>-1){
				Intent returnIntent = new Intent();
				returnIntent.putExtra(SeleccionarImagen.KEY_CONCENTRACION,cc);
				setResult(RESULT_OK,returnIntent);     
				finish();
				
			}else{
				String fallo="Insert the concentration please";
				Toast toast = Toast.makeText(this,
				fallo, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			
			break;
		
		}
	}
	
	private double convertirConcentracionADouble(String numP){
		double numero;
		try{
			numero=Double.parseDouble(numP);
		}catch(Exception e){
			numero=-1;
		}
		
		return numero;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		continuar.setClickable(true);
		return false;
	}
}
