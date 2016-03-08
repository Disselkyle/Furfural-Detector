package com.tce.gsolfa.furfural;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;



public class About extends Activity implements OnClickListener {
	
	Button cerrar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		cerrar=(Button) findViewById(R.id.closeBoton);
		cerrar.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		finish();		
	}

	

}
