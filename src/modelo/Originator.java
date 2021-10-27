package modelo;

public interface Originator {
	/**
	 * Recibe un objeto que encapsula el estado de este objeto en un momento determinado
	 * y actualiza el estado de este objeto al recibido
	 * @param m El estado que se va a restaurar
	 * @see Originator
	 */
	public void setMemento(Memento m);
	/**
	 * Genera un objeto que encapsula el estado de este objeto en e momento de la ejecucion
	 * del metodo
	 * @see Originator
	 * @return memento objeto que encapsula el estado del que llama a este metodo
	 */
	public Memento createMemento();
}
