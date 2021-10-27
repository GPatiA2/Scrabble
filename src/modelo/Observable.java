package modelo;
/**
 * Agrupa el comportamiento de las objetos observables por los objetos de tipo T
 * @author Grupo 5
 * @param <T> Tipo de los objetos que pueden observar al que implementa esta interfaz
 */
public interface Observable<T extends ModelObserver> {
	/**
	 * Registra al objeto que recibe como parametro como observador de quien implementa esta interfaz
	 * @param o Objeto a registrar como observador
	 */
	public void addObserver(T o);
	/**
	 * Desuscribe al objeto recibido como parametro de quien implementa esta interfaz
	 * @param o Objeto a desuscribir de quien implementa la interfaz
	 */
	public void removeObserver(T o);
}
