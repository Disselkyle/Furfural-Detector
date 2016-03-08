package com.tce.gsolfa.furfural.mates;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.tce.gsolfa.furfural.SQLite.Calibrado;
import com.tce.gsolfa.furfural.SQLite.Medidas;
import android.os.Environment;


public class GenerarPDF {
	private static final String REPORTS="images";
	
	
	public GenerarPDF() {
		super();
		
	}

	//Crear PDF
	public boolean crearPDF(Medidas medida) {
		
			// Creamos el documento.
			Document documento = new Document();
			// Creamos el fichero con el nombre que deseemos.
			
			String cod=medida.getSampleCode()+".pdf";
			String resultado=medida.toString();
			File f;
			try {
				f = crearFichero(cod);
				// Creamos el flujo de datos de salida para el fichero donde guardaremos el pdf.
				FileOutputStream ficheroPdf = new FileOutputStream(f.getAbsolutePath());
				 
				// Asociamos el flujo que acabamos de crear al documento.
				PdfWriter.getInstance(documento, ficheroPdf);
				 
				// Abrimos el documento.
				documento.open();
				
				// Añadimos un título con una fuente personalizada.
				Font font = FontFactory.getFont(FontFactory.HELVETICA, 28,Font.BOLD);
				documento.add(new Paragraph("Results Report", font));
				// Añadimos un título con la fuente por defecto.
				
				documento.add(new Paragraph("---------------------------------------------------------------------------------"));
				// Insertamos una imagen que se encuentra en el imagebutton.
				Image imagen = Image.getInstance(medida.getImagen());
				imagen.scaleAbsolute(150, 150);
				documento.add(imagen);
				documento.add(new Paragraph("---------------------------------------------------------------------------------"));
				documento.add(new Paragraph(resultado));
				documento.add(new Paragraph("---------------------------------------------------------------------------------"));
				// Cerramos el documento.
				
				documento.close();
				return true;
				
			} catch (IOException e) {
				return false;
			} catch (DocumentException e) {
				return false;
			}

		
				 
		
	}
	
	public boolean crearPDFCalibrado(Calibrado calibrado) {
		
			// Creamos el documento.
			Document documento = new Document();
			// Creamos el fichero con el nombre que deseemos.
			String cod="Calibrado"+calibrado.getId()+".pdf";
			
			File f;
			try {
				f = crearFichero(cod);
				// Creamos el flujo de datos de salida para el fichero donde guardaremos el pdf.
				FileOutputStream ficheroPdf = new FileOutputStream(f.getAbsolutePath());
				// Asociamos el flujo que acabamos de crear al documento.
				PdfWriter.getInstance(documento, ficheroPdf);
				// Abrimos el documento.
				documento.open();
				// Añadimos un título con una fuente personalizada.
				Font font = FontFactory.getFont(FontFactory.HELVETICA, 28,Font.BOLD);
				documento.add(new Paragraph("Calibrate Report", font));
				documento.add(new Paragraph("---------------------------------------------------------------------------------"));
				
				// Añadimos un título con la fuente por defecto.
				documento.add(new Paragraph(calibrado.toString()));
				documento.add(new Paragraph("---------------------------------------------------------------------------------"));
				// Cerramos el documento.
				
				documento.close();
				return true;
				
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} catch (DocumentException e) {
				e.printStackTrace();
				return false;
			}
	}
	
	public static File crearFichero(String nombreFichero) throws IOException {
	    File ruta = getRuta();
	    File fichero = null;
	    if (ruta != null)
	        fichero = new File(ruta, nombreFichero);
	    return fichero;
	}
	 
	public static File getRuta() {
		File ruta = null;
	    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
	        ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),REPORTS);
	        if (ruta != null) {
	            if (!ruta.mkdirs()) {
	                if (!ruta.exists()) {
	                    return null;
	                }
	            }
	        }
	    } 
	 
	    return ruta;
	}
	
	
	
}
