package modelo.diccionario;


import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Clase Diccionario
 * 
 * Esta clase es la encargada de implementar el
 * diccionario del juego, el cual se toma como referencia
 * a la hora de verificar las palabras que se colocan
 * sobre el tablero
 * 
 * @author Grupo 5
 *
 */
public class Diccionario {

	 /** 
	  * Numero maximo de letras que puede contener una palabra valida para las maquinas
	  */
	private static final int longitud_maxima = 7;

	/**
	 * Instancia utilizada para aplicar un Singleton para el Diccionario
	 */
	private static final Diccionario instance = new Diccionario();
    
	/* En esta clase interna se definen los nodos de la estructura Trie que vamos a utilizar para iplementar el arbol
	 */
    static private class TrieNode 
    { 
    	//Array de nodos para los hijos de este nodo. Tamanio maximo = numero de letras que tiene el abecedario
        private Map<Character, TrieNode> hijos;
        /* Este booleano indica si el conjunto de caracteres almacenados en el camino que va desde la raiz del diccionario 
         * hata este nodo forman una palabra valida
         */
        private boolean finFalabra; 
        
        //Esta es la letra almacenada en este nodo
        private char letra;
          
        //constructor 
        public TrieNode(char letra) { 
        	finFalabra = false; 
        	this.letra = letra;
        	hijos = new TreeMap<Character, TrieNode>(); 
        } 
        
        boolean contieneLetra(char letra) {
        	return hijos.containsKey(letra);
        }
        
        TrieNode get_hjo(char letra) {
        	return hijos.get(letra);
        }
    }
    
    /**
     * Esta es la raiz del diccionario, su char sera un caracter vacio
     */
    private TrieNode raiz;
    
    /**
     * Constructor del diccionario
     */
    private Diccionario() {
    	
    	raiz = new TrieNode(' ');
    	//Generamos el diccionario
    	try {
			this.generar();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
    /**
     * Devuelve la instancia del Diccionario
     * @return
     */
    public static Diccionario getInstance(){
        return instance;
    }
    
    /**
     * Inserta un nuevo nodo en la lista de hijos del TrieNode nodo con el caracter letra
     * @param letra
     * @param nodo
     */
    protected void insertar_Nodo(char letra, TrieNode nodo) {
    	nodo.hijos.put(letra, new TrieNode(letra));
    }
    
    /**
     * Cambia el booleano finPalabra del TrieNode n a true
     * @param n
     */
    protected void incluirPalabra(TrieNode n) {
    	n.finFalabra = true;
    }
    
    /**
     * Inserta una palabra en el diccionario
     * @param palabra
     */
    protected void insertar_Palabra(String palabra) {
    	
    	/*
    	 * Este metodo va insertando en el nivel correspondiente del Trie cada letra de la palabra
    	 * Para hacerlo utiliza el metodo de los nodos que mira si esta contenida la letra en la lista de hijos
    	 * Si no esta lo incluye
    	 * Por ultimo, para la ultima letra, hace la comprobacion y luego utiliza incluirPalabra para modificar el booleano finPalabra del nodo
    	 * para que figure la palabra como palabra valida
    	 */
    	
    	char[] cadena = palabra.toCharArray();
    	TrieNode nodo = this.raiz;
    	for (int i = 0; i<cadena.length-1; i++) {
    		if (!nodo.contieneLetra(cadena[i])) {
    			insertar_Nodo(cadena[i], nodo);
    		}
    		nodo = nodo.get_hjo(cadena[i]);
    	}
    	
    	if (!nodo.contieneLetra(cadena[cadena.length-1])) {
    		insertar_Nodo(cadena[cadena.length-1], nodo);
    	}
    	nodo = nodo.get_hjo(cadena[cadena.length-1]);
    	
    	incluirPalabra(nodo);
    	
    }
    
    /**
     * Este metodo genera el diccionario a partir del documento diccionario.txt contenido en el paquete ArchivoCarga
     * @throws IOException
     */
    private void generar() throws IOException {
		FileReader f = new FileReader("src/ArchivoCarga/diccionarioRevisado.txt");
	    BufferedReader b = new BufferedReader(f);
	      
	    String palabra;
	      
	    while ((palabra = b.readLine()) != null) {
	    	//Quitamos las tildes
	    	palabra = Normalizer.normalize(palabra.toUpperCase(), Normalizer.Form.NFD);
	    	if(palabra.length()>=2 && palabra.length() <= 7) {
	    		this.insertar_Palabra(palabra);
	    	}
	    }
	      
	    b.close();
    }

    /**
     * Devuelve una lista de String que contiene todas las posibles combinaciones validas que se pueden obtener con la lista de letras
     * @param lista_letras
     * @return
     */
	public Map<Integer, List<String>> obtenerCombinaciones(List<Character> lista_letras, char pivote, boolean primera) {
		
		Map<Integer, List<String>> lista = new HashMap<Integer, List<String>>();
		
		boolean[] marcador = {false, false, false, false, false, false, false, false};

		this.backTrack(lista_letras, this.raiz, "", primera, 0, lista, marcador, pivote);
		
		return lista;
	}
	
	/** Algoritmo de vuelta atras para generar todas las palabras que se pueden formar a partir de 
	 * una lista de letras dada.
	 * Condiciones adicionales:
	 * Si el booleano primera es true, significa que es la primera palabra y no es obligatorio que la palabra contenga el pivote
	 * Si no, en las condiciones para incluir la palabra a la lista de posibilidades, se tiene en cuenta si contiene el pivote o no
	 */
	private void backTrack(List<Character> lista_letras, TrieNode nodo, String actual, boolean primera, int colocadas, Map<Integer, List<String>> palabras, boolean[] marcador, char pivote) {
		
		if (colocadas == longitud_maxima) {
			if (nodo.finFalabra) {
				if (primera) {
					if (!palabras.containsKey(colocadas)) {
						palabras.put(colocadas, new ArrayList<String>());
					}
					palabras.get(colocadas).add(actual);
				}
				else if (!primera && this.contienePivote(actual, pivote)) {
					if (!palabras.containsKey(colocadas)) {
						palabras.put(colocadas, new ArrayList<String>());
					}
					palabras.get(colocadas).add(actual);
				}
			}
			return;
		}
		
		if (nodo.finFalabra ) {
			if (primera) {
				if (!palabras.containsKey(colocadas)) {
					palabras.put(colocadas, new ArrayList<String>());
				}
				palabras.get(colocadas).add(actual);
			}
			else if (!primera && this.contienePivote(actual, pivote)) {
				if (!palabras.containsKey(colocadas)) {
					palabras.put(colocadas, new ArrayList<String>());
				}
				palabras.get(colocadas).add(actual);
			}
		}
		
		int size = lista_letras.size();
		for (int i = 0; i<size; i++) {
			
			if (!marcador[i] && nodo.hijos.containsKey(lista_letras.get(i))) {
				
				actual += lista_letras.get(i);
				colocadas++;
				marcador[i] = true;
				TrieNode aux = nodo;
				
				backTrack(lista_letras, nodo.get_hjo(lista_letras.get(i)), actual, primera, colocadas, palabras, marcador, pivote);
				
				nodo = aux;
				colocadas--;
				marcador[i] = false;
				actual = actual.substring(0, colocadas);
			}
		}
	}
	
	/**
	 * Comprueba si una palabra contiene el caracter del pivote
	 * @param palabra
	 * @param pivote
	 * @return
	 */
	private boolean contienePivote(String palabra, char pivote) {
		if (pivote == ' ') {return true;}
		char[] cadena = palabra.toCharArray();
		for (char c : cadena) {
			if (c == pivote) {return true;}
		}
		return false;
	}
	
	 /** Comprueba si el diccionario contiene la palabra
	 * @param palabra
	 * @return
	 */
	public boolean contains(String palabra) {
		
		char[] cadena = palabra.toCharArray();
		TrieNode nodo = raiz;
		for (char c : cadena) {
			if (nodo.contieneLetra(c)) {
				nodo = nodo.get_hjo(c);
			}
			else {
				return false;
			}
		}
		return nodo.finFalabra;
	}
}
