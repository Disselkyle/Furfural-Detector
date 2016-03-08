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

public class InsertarSampleCode extends Plantilla implements OnClickListener, OnTouchListener{
	
	
	Button info;
	Button back;
	Button start;
	EditText sampleCode;
	EditText user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insertarsamplecode);
		
		info=(Button) findViewById(R.id.BotonInfo);
		info.setVisibility(GONE);
		back=(Button) findViewById(R.id.BotonBack);
		back.setOnClickListener(this);
		start=(Button) findViewById(R.id.botonStart2);
		start.setClickable(false);
		start.setOnClickListener(this);
		sampleCode=(EditText) findViewById(R.id.editSampleCode);
		sampleCode.setOnTouchListener(this);
		user=(EditText) findViewById(R.id.editUser);
	}

	@Override
	public void onClick(View v) {
		
		Intent i;
		switch (v.getId()) {
		case R.id.BotonBack:
			i=new Intent(this, InicioActivity.class);
			startActivity(i);
			break;
		case R.id.botonStart2:
			String codigo=sampleCode.getText().toString();
			String usuario=user.getText().toString();
			
			//Implementar pasando parametros : user y sample code
			if(codigo.length()>0&&usuario.length()>0){
				i=new Intent(this, SeleccionarImagen.class);
				i.putExtra(SeleccionarImagen.KEY_CALIBRANDO, false);
				i.putExtra(SeleccionarImagen.KEY_SAMPLECODE, codigo);
				i.putExtra(SeleccionarImagen.KEY_USER, usuario);
				startActivity(i);
			}
			else{
				String fallo="Insert the sample code and the user please";
				Toast toast = Toast.makeText(this,
				fallo, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			
			break;
		
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		start.setClickable(true);
		return false;
	}
}
