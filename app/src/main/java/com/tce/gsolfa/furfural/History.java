package com.tce.gsolfa.furfural;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class History extends Plantilla implements OnClickListener{
	
	
	Button info;
	Button back;
	Button calibrates;
	Button measures;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		
		info=(Button) findViewById(R.id.BotonInfo);
		info.setVisibility(GONE);
		back=(Button) findViewById(R.id.BotonBack);
		back.setOnClickListener(this);
		calibrates=(Button) findViewById(R.id.botonCalibrados);
		calibrates.setOnClickListener(this);
		measures=(Button) findViewById(R.id.botonMedidas);
		measures.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {
		case R.id.BotonBack:
			i=new Intent(this, InicioActivity.class);
			startActivity(i);
			break;
		case R.id.botonCalibrados:
			i=new Intent(this, ListaCalibrados.class);
			startActivity(i);
			break;
		case R.id.botonMedidas:
			i=new Intent(this, ListaMedidas.class);
			startActivity(i);
			break;
		}
		
	}
}
