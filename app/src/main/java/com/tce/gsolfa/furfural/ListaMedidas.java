package com.tce.gsolfa.furfural;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.tce.gsolfa.furfural.SQLite.CalibradosDataSource;
import com.tce.gsolfa.furfural.SQLite.Medidas;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class ListaMedidas extends Plantilla implements OnClickListener{
	
	Button info;
	Button back;
	
	private int requestCode = 1;
	private CalibradosDataSource dataSource;
	private TableLayout tabla;
    private TableRow.LayoutParams layoutFila;
    private SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listamedidas);
		
		info=(Button) findViewById(R.id.BotonInfo);
		info.setVisibility(INVISIBLE);
		back=(Button) findViewById(R.id.BotonBack);
		back.setOnClickListener(this);
		
		// Instanciamos CalibradosDataSource para
        // poder realizar acciones con la base de datos
        dataSource = new CalibradosDataSource(this);
        dataSource.open();
        
     // Instanciamos los elementos
        //lvCalibrados = (ListView) findViewById(R.id.lvCalibrados);
        tabla = (TableLayout)findViewById(R.id.tablaMedidas);
        layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
        
        
        // Cargamos la lista de notas disponibles
        List<Medidas> listaMedidas = dataSource.getAllMedidas();
        agregarFilasTabla(listaMedidas);
	}

	@Override
	public void onClick(View v) {
		Intent i=new Intent(this, History.class);
		startActivity(i);
		
	}
	

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode && resultCode == RESULT_OK) {
            // Actualizar el Adapter
            dataSource.open();
            refrescarLista();
        }
    }
	
	private void refrescarLista() {
		List<Medidas> listaMedidas = dataSource.getAllMedidas();
        agregarFilasTabla(listaMedidas);

    }
	
	@Override
    protected void onPause() {
        // TODO Auto-generated method stub
        dataSource.close();
        super.onPause();
    }
 
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        dataSource.open();
        super.onResume();
    }
    
    public void agregarFilasTabla(List<Medidas> listaMedidas){
    	
        TableRow fila;
        tabla.removeAllViews();
        
        for(Medidas med:listaMedidas){
            
        	fila=crearFilatabla(sdf.format(new Date(med.getFecha())), med.getUser(), med.getSampleCode(), (int) med.getId());
        	tabla.addView(fila);
        }
        
        fila=crearFilatabla("", "", "", -1);
        tabla.addView(fila);
    }
    
    private TableRow crearFilatabla(String fecha, String user, String sampleCode, int id){
    	final int ids=id;
    	TableRow fila = new TableRow(this);
        fila.setLayoutParams(layoutFila);
        fila.setBackgroundResource(R.drawable.tabla_celda);
        TextView txtFecha = new TextView(this);
        TextView txtUser = new TextView(this);
        TextView txtSampleCode= new TextView(this);
        TextView select=new TextView(this);
        
        select.setText(">>");
        select.setTextAppearance(this, R.style.etiquetaBoton);
        select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SeleccionarImagen.medida=dataSource.seleccionarMedidaPorID(ids);
				Intent i=new Intent(ListaMedidas.this, VistaMedidas.class);
				i.putExtra(KEY_MEDIDAGUARDADA, true);
				startActivity(i);
			}
		});
        
        txtFecha.setText(fecha);
        
        txtFecha.setGravity(Gravity.LEFT);
        txtFecha.setTextAppearance(this,R.style.etiqueta);
        txtFecha.setBackgroundResource(R.drawable.tabla_celda);
        
        txtUser.setText(user);
        txtUser.setGravity(Gravity.LEFT);
        txtUser.setTextAppearance(this,R.style.etiqueta);
        txtUser.setBackgroundResource(R.drawable.tabla_celda);

        txtSampleCode.setText(sampleCode);
        txtSampleCode.setGravity(Gravity.LEFT);
        txtSampleCode.setTextAppearance(this,R.style.etiqueta);
        txtSampleCode.setBackgroundResource(R.drawable.tabla_celda);
        

        fila.addView(txtFecha);
        fila.addView(txtUser);
        fila.addView(txtSampleCode);
        if(id>0){
        	fila.addView(select);
        }
        return fila;
    }
}
