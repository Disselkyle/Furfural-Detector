package com.tce.gsolfa.furfural;




import com.tce.gsolfa.furfural.mates.Constantes;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class Plantilla extends Activity implements Constantes{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.plantilla);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.barratitulo);
	}
}
