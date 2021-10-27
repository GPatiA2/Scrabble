package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONObject;
import org.json.JSONTokener;

public class ModeloDAO {

	public void guardarPartida(Memento m, String fichero) throws FileNotFoundException {
		fichero += ".json";
		fichero = "Saves/" + fichero;
		
		OutputStream out;
		
		out = new FileOutputStream(new File(fichero));
		PrintStream ps = new PrintStream(out);	
		
		ps.println(m.getState().toString(3));
		ps.close();
		
		System.out.println("Se ha guardado la partida en el fichero " + fichero + " con exito");
	}
	
	public Memento cargarPartida(File f) throws FileNotFoundException {
		Memento m = new Memento();
	
		InputStream input = new FileInputStream(f);
		JSONObject jo = new JSONObject(new JSONTokener(input));
		m.setState(jo);
	
		return m;
	}
}
