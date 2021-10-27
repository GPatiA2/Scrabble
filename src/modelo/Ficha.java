package modelo;

import org.json.JSONObject;

/**
 * Clase Ficha
 * 
 * Contiene informacion relativa a las fichas
 * del juego.
 * 
 * @author Grupo 5
 *
 */
public class Ficha implements Originator{
	/**
	 * Numero identificador
	 */
	private static int idNumber = 0;
	/**
	 * Letra de la ficha
	 */
	private char letra;
	/**
	 * Puntos de la ficha
	 */
	private int puntos;
	/**
	 * Identificador unico de la ficha
	 */
	private String id;
	
	/**
	 * Constructor con parametros
	 * @param letra
	 * @param puntos
	 */
	public Ficha(char letra, int puntos) {
		this.letra = letra;
		this.puntos = puntos;
		id = generateID();
	}
	
	/**
	 * Constructor sin parametros
	 * usado para cargar una partida
	 */
	public Ficha() { 
		
	}
	
	//--------------------METODOS AUXILIARES, GETTERS, SETTERS...------------------------

	public void setFicha(char letra,int puntos) {
		this.letra = letra;
		this.puntos = puntos;
	}
	
	private static String generateID() {
		idNumber++;
		return "f_"+ idNumber;
	}
	
	public static void resetID() {
		idNumber = 0;
	}
	
	public int getPuntos() {return puntos;}
	
	public char getLetra() {return this.letra;}
	
	public boolean igual_letra(char f) {
		return Character.toLowerCase(f) == Character.toLowerCase(this.letra);
	}
	
	public boolean esComodin() {
		return this.igual_letra('*');
	}
	
	public boolean igual_id(String id) {
		return this.id.equals(id);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String ficha) {
		this.id = ficha;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ficha other = (Ficha) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String toString() {return letra+"["+puntos+"]";}

	//--------------------METODOS DE LA INTERFAZ ORIGINATOR------------------------

	@Override
	public void setMemento(Memento m) { 
		letra = (char) m.getState().getInt("letra");
		puntos = m.getState().getInt("puntos");
		id = m.getState().getString("id");
	}

	@Override
	public Memento createMemento() {
		Memento memento = new Memento(); 
		JSONObject jsonFicha = new JSONObject();
		
		jsonFicha.put("letra", this.letra);
		jsonFicha.put("puntos", this.puntos);
		jsonFicha.put("id", this.id);
		
		memento.setState(jsonFicha);
		
		return memento;
	}
}
