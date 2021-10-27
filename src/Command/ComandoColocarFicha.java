package Command;


import java.util.Scanner;


import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;
import modelo.Ficha;
import modelo.Game;
import modelo.Integrante;
import modelo.Mazo;
import modelo.Tablero;
import modelo.Turno;
import utils.Coordenadas;

/**
 * Clase ComandoColocarFicha
 * 
 * Esta clase permite a un jugador colocar
 * una ficha en el tablero durante su turno.
 * 
 * @author Grupo 5
 *
 */
public class ComandoColocarFicha extends Command{
	/**
	 * Informacion de ayuda sobre el comando colocar una ficha
	 */
	private static final String help = "Este comando permite colocar una ficha en el tablero.";
	/**
	 * Identificador de la ficha a colocar
	 */
	private String id_ficha;
	/**
	 * Coordenadas de la ficha a colocar
	 */
	private Coordenadas coor;
	/**
	 * Letra de la ficha comodin
	 */
	private char letra_comodin;
	
	
	public ComandoColocarFicha(String id_ficha, int coorX, int coorY,char letra_comodin) {
		super("colocar", ProtocoloComunicacion.COLOCAR_FICHA,"c", "colocar <ficha><coorX><corrY>", help);
		this.id_ficha = id_ficha;
		coor = new Coordenadas(coorX,coorY);
		this.letra_comodin = letra_comodin;
	}
	
	@Override
	public Command parse(String[] comandoCompleto) throws CommandParseException {
		//Llamo a matchCommandName para comprobar si el comando introducido
		//corresponde con este comando, si lo es devuelvo una objeto de 
		//este comando, si no, devuelvo null
		ComandoColocarFicha c_f = null;
		
		if(matchCommandName(comandoCompleto[0])) {
			try {
				
				if(comandoCompleto.length !=4 && comandoCompleto.length !=5) {
					throw new CommandParseException("numero de argumentos invalidos");
				}
		
				int x,y;
				x = Integer.parseInt(comandoCompleto[2]);
				y = Integer.parseInt(comandoCompleto[3]);
				
				if(Coordenadas.checkCommand(x-1, y-1)) { //Comprobar que las coordenadas son correctas
					if(comandoCompleto.length == 4 && comandoCompleto[1].equals("*")){
						String letra= this.pedirComodin();
						c_f = new ComandoColocarFicha(comandoCompleto[1],x,y,letra.charAt(0));
					}
					else if(comandoCompleto.length == 5) {
						c_f = new ComandoColocarFicha(comandoCompleto[1],x,y,comandoCompleto[4].trim().charAt(0));
					}
					else {
						c_f = new ComandoColocarFicha(comandoCompleto[1],x,y,'0');
					}
				}
				else {
					throw new CommandParseException("Casilla fuera de rango.");
				}	
				
				
			}
			catch(NumberFormatException nfe) {
				throw new CommandParseException("Los argumentos coorX, coorY deben ser numeros");
			}
		}
		
		return c_f;
	}

	/**
	 * El execute de colocar una ficha primero comprueba que la casilla en la que se quiere
	 * colocar la ficha esta disponible. En caso de estarlo se verifica la existencia de dicha 
	 * ficha en la mano del jugador y si todo ha salido bien se elimina la ficha de la mano del
	 * jugador y se annade al tablero.
	 */
	@Override
	public boolean execute(Tablero t, Mazo m, Integrante j) throws CommandExecuteException {
		try {
	
			Ficha ficha_colocar = null;
			
			
			if(j.size() > 0){ //Si el jugador tiene en su mano mas de una ficha
				
				//Una ficha esta bien colocada en el tablero si la casilla esta disponible y vacia
				boolean bienColocada = t.esDisponible(coor.getFila(), coor.getColumna())
									&& t.emptyCasilla(coor.getFila(), coor.getColumna());
				
				
				/*Comprueba que existe la ficha en la mano del jugador y la elimina de su mano
				* o no dependiendo de si la ficha esta bien colocada. Si no esta bien colocada 
				* notifico a los observadores para que la ficha vuelva a aparecer en el atril.
				* En caso de no existir la ficha en la mano se lanza excepcion. */
				ficha_colocar = j.ExisteFicha(id_ficha, bienColocada);
				
				if(!bienColocada) { //Lanzar excepcion
					throw new CommandExecuteException("Solo puedes poner una ficha al lado de una casilla que contenga otra, o si es el primer turno, en el centro");
				}
				
				if(ficha_colocar != null) { //Si se ha podido colocar la ficha
				
					if(letra_comodin != '0') {
						ficha_colocar.setFicha(letra_comodin, 0);
					}
					
					//Añado la ficha al tablero en las coordenadas correspondientes
					t.aniadeFicha(ficha_colocar, coor.getFila(), coor.getColumna());
				}
				
				return true;				
			}
			else { //En caso de tener la mano vacia
				
				throw new CommandExecuteException("Debes tener una ficha en la mano para poder colocarla");
				
			}
			
		} 	catch(Exception e) {
			
			throw new CommandExecuteException(e.getMessage());
			
		}
	}

	/**
	 * Antes de ejecutar el comando se corrigen la diferencia de enumeracion
	 * en el tablero entre el usuario y la aplicacion
	 */
	@Override
	protected boolean posible(Turno t) {
		this.coor.corregir(); //Corrige diferencia de enumeracion entre usuario y tablero
		return true;
	}

	/**
	 * Tras finalizar el comando se notifica al turno de los cambios realizados.
	 */
	@Override
	protected void addCambios(Turno t) {
		t.addFichaPuesta(coor);
		System.out.println("Añadi: "+coor);
	}

	@Override
	public String toString() {
		return (this.nombreProtocolo+ " " + id_ficha + " " + coor+" " +this.letra_comodin);
	}
	/**
	 * Permite al jugadro en activo emplear un comodin.
	 * Pide al jugador una letra por la GUI y crea una ficha con la letra
	 * elegida que devuelve
	 * @param f  
	 * @return f La ficha con la letra elegida por el jugador
	 * @see Ficha
	 */
	private String pedirComodin() {
		String s;			
			do{
				System.out.println("Introduzca letra para el comodin");
				Scanner in  = new Scanner(System.in);
				s = in.nextLine().trim().toUpperCase();
				
			}while(s.isEmpty()||s.length()>1|| s.equals(" ")|| Game.isNumeric(s));
			
			//Pido la letra mientras que sea la entrada vacia, un espacio, varias letras
			//o sea un numero
		
		return s.trim();
		
	}
}
