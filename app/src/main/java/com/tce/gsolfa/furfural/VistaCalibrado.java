package com.tce.gsolfa.furfural;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import com.androidplot.series.XYSeries;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;
import com.tce.gsolfa.furfural.SQLite.Calibrado;
import com.tce.gsolfa.furfural.SQLite.CalibradosDataSource;

import com.tce.gsolfa.furfural.mates.GenerarPDF;
import com.tce.gsolfa.furfural.mates.MatesyPixeles;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("SimpleDateFormat")
public class VistaCalibrado extends Plantilla implements OnClickListener{
	
	private Button info;
	private Button back;
	private Button select;
	private Button save;
	private Button delete;
	private Button pdf;
	
	private TextView titulo;
	private TextView resultados;
	
	private XYPlot mySimpleXYPlot;
	
	
	private Calibrado calibrado;
	private CalibradosDataSource dataSource;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private MatesyPixeles m;
	private boolean calibradoGuardado=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vistacalibrado);
		
		info=(Button) findViewById(R.id.BotonInfo);
		info.setVisibility(INVISIBLE);
		back=(Button) findViewById(R.id.BotonBack);
		back.setOnClickListener(this);
		select=(Button) findViewById(R.id.botonSelect);
		select.setOnClickListener(this);
		save=(Button) findViewById(R.id.botonSave);
		save.setOnClickListener(this);
		delete=(Button) findViewById(R.id.botonDelete);
		delete.setOnClickListener(this);
		pdf=(Button) findViewById(R.id.botonPDF);
		pdf.setOnClickListener(this);
		
		titulo=(TextView)findViewById(R.id.tituloInicio);
		resultados=(TextView)findViewById(R.id.textoDatosCalibrado);
		
		mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
		m=new MatesyPixeles();
		int keyID=getIntent().getIntExtra(KEY_ID, ID_CALIBRADO);
		calibrado=SeleccionarImagen.calibrado;
		dataSource = new CalibradosDataSource(this);
        dataSource.open();
		
		if(keyID<0){
			calibradoGuardado=false;
			select.setText("Cancel");
			delete.setVisibility(INVISIBLE);
			delete.setClickable(false);
			pdf.setVisibility(INVISIBLE);
			pdf.setClickable(false);
			
			
		}
		else{
			calibradoGuardado=true;
			save.setVisibility(INVISIBLE);
			save.setClickable(false);
		}
		
		titulo.setText(sdf.format(new Date(calibrado.getFecha())));
		resultados.setText(calibrado.toString());
		dibujarGrafica(calibrado);
		
	}
	
	
	@Override
	public void onClick(View v) {
		Intent i;
		AlertDialog.Builder builder;
		
		switch (v.getId()) {
		case R.id.BotonBack:
			i=new Intent(this, ListaCalibrados.class);
			startActivity(i);
			break;
		case R.id.botonSelect:
			
			if(calibradoGuardado){
				
				i=new Intent(this, InicioActivity.class);
				startActivity(i);
			}
			else{
				i=new Intent(this, ListaCalibrados.class);
				startActivity(i);
			}
			
			break;
		case R.id.botonSave:
			
			builder = new AlertDialog.Builder(this)
	        .setTitle("Save Calibrate")
	        .setMessage("¿Do you wish to save this calibrate?")
	        .setPositiveButton("Accept",
	                new DialogInterface.OnClickListener() {
	
	                    @Override
	                    public void onClick(DialogInterface arg0, int arg1) {
	                        
	                    	
	                    	dataSource.crearCalibrado(calibrado.getFecha(), calibrado.convertirArrayPuntosEnString(calibrado.getPuntosCal()));
	                    	calibradoGuardado=true;
	                    	save.setClickable(false);
	                    	save.setVisibility(INVISIBLE);
	        				select.setText(R.string.botonSelect);
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
	        .setTitle("Delete Calibrate")
	        .setMessage("¿Do you wish to delete this calibrate?")
	        .setPositiveButton("Accept",
	                new DialogInterface.OnClickListener() {
	
	                    @Override
	                    public void onClick(DialogInterface arg0, int arg1) {
	                        
	                    	dataSource.borrarCalibrado(calibrado.getFecha());
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
			boolean pdfGenerado=new GenerarPDF().crearPDFCalibrado(calibrado);
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
	
	@SuppressWarnings("deprecation")
	private void dibujarGrafica(Calibrado cal){
		
		ArrayList<String> listaCons=cal.getPuntosCal();
		
		Number[] rgb=new Number[cal.getNumeroPuntos()];
        Number[] concentracion=new Number[cal.getNumeroPuntos()];
		for(int i=0; i<listaCons.size();i++){
			String[] dos=listaCons.get(i).split(";");
			rgb[i]=Double.parseDouble(dos[0]);
			concentracion[i]=Double.parseDouble(dos[1]);
		}
		
		XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(concentracion),  	// Array de datos
                Arrays.asList(rgb), 			// Sólo valores verticales-- otro array
                ""); 							// Nombre de la primera serie
		
		LineAndPointFormatter series1Format = new LineAndPointFormatter(
	            null,                   		// Color de la línea si pones null no hay linea
	            Color.rgb(0, 100, 0),           // Color del punto
	            null);              			// Relleno
	 
        // Una vez definida la serie (datos y estilo), la añadimos al panel
        mySimpleXYPlot.addSeries(series1, series1Format);
        Number[] lineaX={0, 500};
        Number[] lineaY={cal.getOrdenadaOrigen(), m.calcularRGBaPartirdeConcentracion(500, cal)};
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(lineaX), Arrays.asList(lineaY), "");
        mySimpleXYPlot.addSeries(series2, new LineAndPointFormatter(Color.rgb(0, 0, 100), null, null));
        
        
        mySimpleXYPlot.setDomainLabel("[Furfural] (µg/L)");
        mySimpleXYPlot.setRangeLabel("RGB");
        mySimpleXYPlot.setTitle("");
        mySimpleXYPlot.setTicksPerRangeLabel(1);
        mySimpleXYPlot.setTicksPerDomainLabel(1);
        
        mySimpleXYPlot.setDomainValueFormat(new DecimalFormat("0"));
        mySimpleXYPlot.setRangeValueFormat(new DecimalFormat("0.00"));
        mySimpleXYPlot.setDomainBoundaries(0, 600, BoundaryMode.FIXED);
        
        mySimpleXYPlot.setDomainStep(XYStepMode.SUBDIVIDE, 7);
        
        mySimpleXYPlot.getLegendWidget().setSize(new SizeMetrics(0, SizeLayoutType.ABSOLUTE, 0, SizeLayoutType.ABSOLUTE));
        mySimpleXYPlot.setPlotPadding(10, 10, 10, 10);
        mySimpleXYPlot.setDomainLabelWidget(null);
        
	}
}
