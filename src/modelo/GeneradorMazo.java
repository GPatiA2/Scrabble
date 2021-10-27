package modelo;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Clase GeneradorMazo
 * 
 * Esta clase genera el mazo de fichas
 * a partir de un archivo json con la 
 * informacion de las distintas fichas del
 * juego
 * 
 * @author Grupo5
 *
 */
public class GeneradorMazo{
	
	private List<Ficha> f;
	public static int NUM_FICHAS = 0;
	/**
	 * Lugar desde el cual se va a cargar el mazo
	 */
	private InputStream in;
	/**
	 * Constructor por defecto
	 * @param in Via por la que entran los datos referentes al mazo
	 * @throws IOException
	 */
	public GeneradorMazo() throws IOException {
		this.f = new ArrayList<Ficha>();
		this.in = new FileInputStream(new File("src" + File.separator +"ArchivoCarga" + File.separator +"mazo.json"));
	}
	
	/**
	 * Inicializa la lista de fichas. La cantidad de fichas de cada
	 * letra depende de la frecuencia de la misma, a mayor frecuencia,
	 * mayor numero de fichas de esa letra habra en el mazo.
	 * 
	 * @param in
	 * @throws IOException
	 */
	private void initList(InputStream in) throws IOException {
		/*
		 * Este metodo lee del jsonObject el mazo
		 */
		
		JSONObject jo = new JSONObject(new JSONTokener(in));
		try {
			int frecuenciaLetra, puntos;
			char letra;
			
			JSONArray mazo = jo.getJSONArray("mazo");				
			
			for (int i = 0; i<mazo.length(); i++) {
				frecuenciaLetra = mazo.getJSONObject(i).getJSONObject("data").getInt("frecuencia");
				puntos = mazo.getJSONObject(i).getJSONObject("data").getInt("puntos");
				letra = mazo.getJSONObject(i).getString("letra").charAt(0);
				
				//Aniado letras segun su frecuencia
				while(frecuenciaLetra>0) {
					f.add(new Ficha(letra,puntos));
					frecuenciaLetra--;				
				}
			}
		}
		catch(JSONException e) {
			throw new IOException("Error al inicializar la lista");
		}
	}
	
	public void mezclar() {
		/*
		 * Metodo que mezcla las fichas/letras 
		 */
		 Collections.shuffle(f, new Random(System.currentTimeMillis()));
	}
	
	public Collection<Ficha> getRandomStack(){
		return f;
	}
	
	/**
     * Devuelve una copia de la lista no modificable
     * @return List<Ficha> 
     */
	public List<Ficha> getF() {
		return Collections.unmodifiableList(f);
	}
	
	public Mazo generate() {
		try {
			initList(in);
			mezclar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Mazo(f);
	}

}
