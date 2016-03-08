package com.tce.gsolfa.furfural;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class InicioActivity extends Plantilla implements OnClickListener{
	
	Button info;
	Button back;
	Button start;
	Button history;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
		
		info=(Button) findViewById(R.id.BotonInfo);
		info.setOnClickListener(this);
		back=(Button) findViewById(R.id.BotonBack);
		back.setVisibility(INVISIBLE);
		back.setOnClickListener(this);
		start=(Button) findViewById(R.id.botonStart);
		start.setOnClickListener(this);
		history=(Button) findViewById(R.id.botonHistory);
		history.setOnClickListener(this);
		
        
	}

	
	@Override
	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {
		case R.id.BotonInfo:
			i = new Intent(this, About.class);
			startActivity(i);
			break;
		
		case R.id.botonStart:
			if(SeleccionarImagen.calibrado==null){
				Toast toast = Toast.makeText(this,"Please select a calibrate before you start the measure", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show(); 
			}else{
				i=new Intent(this, InsertarSampleCode.class);
				startActivity(i);
			}
			
			break;
		case R.id.botonHistory:
			i=new Intent(this, History.class);
			startActivity(i);
			break;
		}
		
	}

	

}
