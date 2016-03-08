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
import android.widget.Toast;

public class InsertarNumeroPuntosCalibrado extends Plantilla implements OnClickListener, OnTouchListener{
	
	
	Button info;
	Button back;
	Button start;
	EditText numeroPuntos;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insertarnumeropuntoscalibrado);
		
		info=(Button) findViewById(R.id.BotonInfo);
		info.setVisibility(GONE);
		back=(Button) findViewById(R.id.BotonBack);
		back.setOnClickListener(this);
		start=(Button) findViewById(R.id.botonStart3);
		start.setOnClickListener(this);
		start.setClickable(false);
		numeroPuntos=(EditText) findViewById(R.id.editNumeroPuntos);
		numeroPuntos.setOnTouchListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {
		case R.id.BotonBack:
			finish();
			break;
		case R.id.botonStart3:
			//Implementar pasando parametros : numero de puntos
			int numeroMuestras=convertirNumeroPuntosAInt(numeroPuntos.getText().toString());
			if(numeroMuestras>0){
				
				i=new Intent(InsertarNumeroPuntosCalibrado.this, SeleccionarImagen.class);
				i.putExtra(SeleccionarImagen.KEY_NUMEROPUNTOS, numeroMuestras);
				i.putExtra(SeleccionarImagen.KEY_CALIBRANDO, true);
				startActivity(i);
			}else{
				Toast toast = Toast.makeText(this,
				R.string.no_points_label, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			
			break;
		
		}
	}
	
	private int convertirNumeroPuntosAInt(String numP){
		int numero;
		try{
			numero=Integer.parseInt(numP);
		}catch(Exception e){
			numero=-1;
		}
		
		return numero;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		start.setClickable(true);
		return false;
	}
	
	
}
