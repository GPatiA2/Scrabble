package controlador;

import java.io.File;
import java.io.FileNotFoundException;

import Command.Command;
import Excepciones.CommandExecuteException;
import modelo.TableroObserver;
import modelo.Jugador;
import modelo.JugadorObserver;
import modelo.Scrabble;
import modelo.TManagerObserver;
import modelo.Tablero;
import modelo.Diccionario.Diccionario;



public class Controller implements Runnable,Registrador{
	
	private Diccionario diccionario_nuevo;
	private Game game;
	private Scanner in;
	private AdminTurnos tManager;
	private int numJugs;
	private int numMaquinas;
	private String dificultad;
	private List<Integrante> listaJugs;
	private Jugador jug;


public class Controller implements Registrador{
	
	private Scrabble scrabble;

	public Controller(int jugadores, int maquinas, String dificultad, List<Integrante> listaJugadores ) throws IOException {

		numJugs = jugadores;
		numMaquinas = maquinas;
		this.dificultad = dificultad;
		in = new Scanner(System.in);

		//Se crea el juego
		this.game = new Game();		
		
		this.diccionario_nuevo = Diccionario.getInstance();
	
		initMaquinas();
		this.listaJugs.addAll(listaJugadores);		
		
		
		//Se crea el administrador de turnos, con el juego y la fuente de la entrada
		tManager = new AdminTurnos(game, listaJugs, in);
			
		
	}
	public Controller() throws IOException { 
		numJugs = 0;
		numMaquinas = 0;
		this.dificultad = null;
		in = new Scanner(System.in);
		
		//Se inicializa la lista de jugadores
		initMaquinas();
		
		//Se crea el diccionario
		diccionario_nuevo = Diccionario.getInstance();
		
		//Se crea el juego
		this.game = new Game();
		
		//Se crea el administrador de turnos, con el juego y la fuente de la entrada
		tManager = new AdminTurnos(game, listaJugs, in);
				
	}

	private void initMaquinas(){
		this.listaJugs = new ArrayList<>();
		try {
			for (int i = 0; i<numMaquinas; i++) {
				Maquina m = new Maquina(dificultad, i, null, null, diccionario_nuevo);
				m.setGame(this.game);
				m.setTablero(this.game.getTablero());
				this.listaJugs.add(m);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("No se ha creado la maquina correctamente");
		}
	}
	public Controller(Scrabble scrabble) {
		this.scrabble = scrabble;
	}	
	
	public void addJugador(Jugador j) {
		
	}	
	
	@Override
	public void addGameObserver(TableroObserver ob) {
		scrabble.addGameObserver(ob);
	}
	@Override
	public void addTManagerObserver(TManagerObserver ob) {
		scrabble.addTManagerObserver(ob);
	}
	@Override
	public void removeGameObserver(TableroObserver ob) {
		scrabble.removeGameObserver(ob);
	}
	@Override
	public void removeTManagerObserver(TManagerObserver ob) {
		scrabble.removeTManagerObserver(ob);
	}
	
	@Override
	public void runCommand(Command c) throws FileNotFoundException, CommandExecuteException {
		scrabble.runCommand(c);
	}

	@Override
	public void addJugadorObserver(JugadorObserver ob) {
		scrabble.addJugadorObserver(ob);
	}


	@Override
	public void removeJugadorObserver(JugadorObserver ob) {
		scrabble.removeJugadorObserver(ob);
	}

	
	@Override
	public void save(String fichero) throws FileNotFoundException {
		scrabble.save(fichero);
	}


	@Override
	public void load(File fichero) throws FileNotFoundException {
		scrabble.load(fichero);
	}

}
