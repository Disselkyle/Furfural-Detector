package com.tce.gsolfa.furfural;



import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tce.gsolfa.furfural.SQLite.Calibrado;
import com.tce.gsolfa.furfural.SQLite.CalibradosDataSource;
import com.tce.gsolfa.furfural.mates.Constantes;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


@SuppressLint("SimpleDateFormat")
public class ListaCalibrados extends Plantilla implements OnClickListener {


    Button info;
    Button back;
    Button nuevoCalibrado;

    private int requestCode = 1;
    private CalibradosDataSource dataSource;
    private TableLayout tabla;
    private TableRow.LayoutParams layoutFila;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private DecimalFormat dc = new DecimalFormat("#0.0000");
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listacalibrados);

        info = (Button) findViewById(R.id.BotonInfo);
        info.setVisibility(INVISIBLE);
        back = (Button) findViewById(R.id.BotonBack);
        back.setOnClickListener(this);
        nuevoCalibrado = (Button) findViewById(R.id.botonNuevoCalibrado);
        nuevoCalibrado.setOnClickListener(this);

        // Instanciamos CalibradosDataSource para
        // poder realizar acciones con la base de datos
        dataSource = new CalibradosDataSource(this);
        dataSource.open();

        // Instanciamos los elementos
        //lvCalibrados = (ListView) findViewById(R.id.lvCalibrados);
        tabla = (TableLayout) findViewById(R.id.tablaCalibrados);
        layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);


        // Cargamos la lista de notas disponibles
        List<Calibrado> listaCalibrados = dataSource.getAllCalibrados();
        agregarFilasTabla(listaCalibrados);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.BotonBack:
                i = new Intent(this, History.class);
                startActivity(i);
                break;

            case R.id.botonNuevoCalibrado:
                i = new Intent(this, InsertarNumeroPuntosCalibrado.class);
                startActivity(i);
                break;

        }

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
        List<Calibrado> listaCalibrados = dataSource.getAllCalibrados();
        agregarFilasTabla(listaCalibrados);

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

    public void agregarFilasTabla(List<Calibrado> listaCalibrados) {

        TableRow fila;
        tabla.removeAllViews();

        for (Calibrado cal : listaCalibrados) {

            fila = crearFilatabla(sdf.format(new Date(cal.getFecha())), "r=" + dc.format(cal.getR()), "points: " + String.valueOf(cal.getNumeroPuntos()), (int) cal.getId());
            tabla.addView(fila);
        }

        fila = crearFilatabla("", "", "", -1);
        tabla.addView(fila);
    }

    private TableRow crearFilatabla(String fecha, String r, String numP, int id) {
        final int ids = id;
        TableRow fila = new TableRow(this);
        fila.setLayoutParams(layoutFila);
        fila.setBackgroundResource(R.drawable.tabla_celda);
        TextView txtFecha = new TextView(this);
        TextView txtR = new TextView(this);
        TextView txtNumPoints = new TextView(this);
        TextView select = new TextView(this);

        select.setText(">>");
        select.setTextAppearance(this, R.style.etiquetaBoton);
        select.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                SeleccionarImagen.calibrado = dataSource.seleccionarCalibradoPorID(ids);
                Intent i = new Intent(ListaCalibrados.this, VistaCalibrado.class);
                i.putExtra(KEY_ID, ids);
                startActivity(i);

            }
        });

        txtFecha.setText(fecha);

        txtFecha.setGravity(Gravity.LEFT);
        txtFecha.setTextAppearance(this, R.style.etiqueta);
        txtFecha.setBackgroundResource(R.drawable.tabla_celda);

        txtR.setText(r);

        txtR.setGravity(Gravity.LEFT);
        txtR.setTextAppearance(this, R.style.etiqueta);
        txtR.setBackgroundResource(R.drawable.tabla_celda);

        txtNumPoints.setText(numP);

        txtNumPoints.setGravity(Gravity.LEFT);
        txtNumPoints.setTextAppearance(this, R.style.etiqueta);
        txtNumPoints.setBackgroundResource(R.drawable.tabla_celda);


        fila.addView(txtFecha);
        fila.addView(txtR);
        fila.addView(txtNumPoints);
        if (id > 0) {
            fila.addView(select);


        }
        return fila;
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ListaCalibrados Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.tce.gsolfa.furfural/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ListaCalibrados Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.tce.gsolfa.furfural/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
