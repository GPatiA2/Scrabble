import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import Cliente.EstadoCliente;
import Excepciones.CommandExecuteException;
import controlador.Controller;
import modelo.AdminTurnos;
import modelo.GeneradorDiccionario;
import modelo.GeneradorMazo;
import modelo.Instrucciones;
import modelo.Integrante;
import modelo.Jugador;
import modelo.Maquina;
import modelo.Scrabble;
import vista.MainWindow;


public class Main {
	
	private static Scanner res;
	private static final String NEWGAME = "NEW GAME";
	private static final String LOADGAME = "LOAD GAME";
	private static final String LEADERBOARD = "LEADER BOARD";
	private static final String INSTRUCCIONES = "INSTRUCCIONES";
	private static final String EXIT = "EXIT";
	private static final String GUI = "GUI";
	private static final String CONSOLA = "CONSOLA";
	
	public static void main(String[] args) {
		
		res = new Scanner(System.in); // Scanner para elegir opcion
		boolean salir = false; //Para comprobar si el usuario introduce una opcion valida en el menu		
		
		while (!salir) {
			
			String modo = ModeMenu(); //Devuelve el modo de juego
			
			if(modo.contentEquals(CONSOLA)) {
				
				String respuesta = Menu1();
			
				if (respuesta.contentEquals(NEWGAME)) {
					try { 
						for (int i = 0; i < 100; i++) {System.out.println();} // Sirve para "limpiar" la consola
						if(Menu2()) {
							newGame();		
						}
						for (int i = 0; i < 100; i++) {System.out.println();}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if (respuesta.contentEquals(LOADGAME)) {
					
//					try { 
//						for (int i = 0; i < 100; i++) {System.out.println();} // Sirve para "limpiar" la consola
//						if(Menu2()) {
//							Controller c = new Controller();
//						
//							System.out.println("Introduce el nombre de la partida para cargar:");
//							String inFile = res.nextLine();
//							
//							InputStream input = new FileInputStream("Saves/" + inFile + ".json");
//							c.load(input);
//							c.run();
//						}
//						for (int i = 0; i < 100; i++) {System.out.println();}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
				
				}
				else if (respuesta.contentEquals(LEADERBOARD)) {
					System.out.println("TODAVIA NO SE PUEDE MOSTRAR LA LISTA, LE DEVOLVEREMOS AL MENU");
					// Se incluira la lista de los diez mejores jugadores ordenados por puntuacion
				
					// En caso de no disponer de ningun jugador se mostrara un mensaje avisando de lo ocurrido
		
					// Opcion de volver al menu o de cerrar la aplicacion
					res = new Scanner (System.in);
					System.out.println ("Introduzca volver para volver al menu :");
				
					System.out.println();
					res.nextLine().trim().toUpperCase();
				
				}
				else if(respuesta.contentEquals(INSTRUCCIONES)) {
					Instrucciones();
				}
				else if(respuesta.contentEquals(EXIT)) {
					salir = true;
				}
				else { // Si no coincide con ninguna opcion, vuelve a mostrar el menu
				
					for (int i = 0; i < 100; i++) {System.out.println();} // Sirve para "limpiar" la consola
					System.out.println();
					System.out.println("Opcion no valida,"+ respuesta +", no coincide con nada");
					System.out.println();
				}
			
			}
			else if(modo.contentEquals(GUI)) {
				Controller ctrl;
				//Esto esta aqui como placeholder antes de que metamos pedir los jugadores por GUI
				try {
					boolean numOk = false;
					int numJugs = 0;
					int numMaquinas = 0;
					String dificultad = null;
					while(!numOk) {
						try {
							numOk = true;
							System.out.println("Introduzca el numero de jugadores: ");
							numJugs = Integer.parseInt(res.nextLine().trim());
							System.out.println("Introduzca el numero de jugadores controlados por la maquina: ");
							numMaquinas = Integer.parseInt(res.nextLine().trim());
							if (numMaquinas != 0) {
								System.out.println("Selecciona el nivel de dificultad de las maquinas (EASY, MEDIUM, HARD)");
								dificultad = res.nextLine().trim().toUpperCase();
							}
							if(numJugs == 0) {
								System.out.println("Como minimo debe haber un jugador");
								numOk = false;
							}
							
						}
						catch(Exception e){
							numOk = false;
							System.err.println("Introduzca un numero");
						}
					}
					
					System.out.println("pulsa 1 para modo cargar");
					int cargar = Integer.parseInt(res.nextLine().trim());
					
					if(cargar == 1) {
						List<Integrante> j = initJugadores(res,numJugs,numMaquinas);
						//AdminTurnos tm = new AdminTurnos(new GeneradorMazo(), new GeneradorDiccionario(), j);
						Scrabble sc = new Scrabble(j);
						ctrl = new Controller(sc);
						ctrl.load(new File("TESTEO2"));
						
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								MainWindow m= new MainWindow(ctrl);
								m.setVista(EstadoCliente.GAME);
								m.setVisible(true);
							}
							
						});		
					}
					else {
						List<Integrante> j = initJugadores(res,numJugs,numMaquinas);
						//AdminTurnos tm = new AdminTurnos(new GeneradorMazo(), new GeneradorDiccionario(), j);
						Scrabble sc = new Scrabble(j);
						ctrl = new Controller(sc);
						
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								MainWindow m= new MainWindow(ctrl);
								m.setVista(EstadoCliente.GAME);
								m.setVisible(true);
							}
							
						});						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(modo.contentEquals(EXIT)){ 
				salir = true;
			}
		}
		
		
	
}
	
	//Este metodo muestra un menu para seleccionar el modo de juego
	private static String ModeMenu() {
		String r;
		
		do {
			System.out.println ("ELIGE UN MODO DE JUEGO\r\n" + "1. CONSOLA\r\n" + 
					"2. GUI\r\n" + "3. EXIT\r\n" + "\r\n" + "Selecciona una opcion:");
			r = res.nextLine().toUpperCase();
			
		} while(!(r.contentEquals(CONSOLA) || r.contentEquals(GUI) || r.contentEquals(EXIT)));
		
		return r;
	}

	private static String Menu1(){
		/*
		 * Metodo que imprime el menu y devuelve a opcion seleccionada por el jugador
		 */
		System.out.println ("BIENVENIDO A SCRABBLE");
		System.out.println ();
		System.out.println ("*************** MENU ***************");
		System.out.println ();
		System.out.println ( "1. " + NEWGAME);       // Comienza una partida nueva
		System.out.println ( "2. "+ LOADGAME);       // Carga una partida a medias
		System.out.println ( "3. " + LEADERBOARD);   // Muestra una tabla con los mejores jugadores
		System.out.println ( "4. " + INSTRUCCIONES); // Instrucciones
		System.out.println ( "5. " + EXIT);  	     // Salir
		System.out.println ();
		System.out.println ("************************************");
		System.out.println ("Seleccione una opcion: ");
		return res.nextLine().toUpperCase();		
		
	}
	
	private static boolean Menu2() {
		System.out.println("****************GAME****************");
		System.out.println ("               START ");
		System.out.println ("               VOLVER");
		System.out.println("****************************************");
		System.out.println ();
		
		//SELECCIONA UNA OPCION
		System.out.println ("Introduzca su opcion: ");
		res = new Scanner (System.in);
		
		String respuesta = res.nextLine().toUpperCase(); // RESPUESTA
		if (respuesta.contentEquals("START")) {
			return true;
		}
		else {
			return false;
		}
	}
	

	private static void newGame() throws IOException, CommandExecuteException {
		//Se introduce el numero de jugadores que van a jugar
		boolean numOk = false;
		int numJugs = 0;
		int numMaquinas = 0;
		String dificultad = null;
		while(!numOk) {
			try {
				numOk = true;
				System.out.println("Introduzca el numero de jugadores: ");
				numJugs = Integer.parseInt(res.nextLine().trim());
				System.out.println("Introduzca el numero de jugadores controlados por la maquina: ");
				numMaquinas = Integer.parseInt(res.nextLine().trim());
				if (numMaquinas != 0) {
					System.out.println("Selecciona el nivel de dificultad de las maquinas (EASY, MEDIUM, HARD)");
					dificultad = res.nextLine().trim().toUpperCase();
				}
				if(numJugs == 0) {
					System.out.println("Como minimo debe haber un jugador");
					numOk = false;
				}
				
			}
			catch(Exception e){
				numOk = false;
				System.err.println("Introduzca un numero");
			}
		}
		List<Integrante> j = initJugadores(res,numJugs,numMaquinas);
		//Se crea el controller pasando el game y el numero de jugadores que van a jugar
		//AdminTurnos tm = new AdminTurnos(new GeneradorMazo(), new GeneradorDiccionario(), j);
		Scrabble sc = new Scrabble(j);
		Controller c = new Controller (sc);
		//Esto esta aqui de momento, si queremos jugar por consola tenemos que diseñar una vista
		//  que use los toString, no es dificil
		MainWindow mw = new MainWindow (c);
		mw.setVista(EstadoCliente.GAME);
	}

	private static void Instrucciones() {
		Instrucciones i = new Instrucciones();
		System.out.println(i.toString());
	}
	
	private static List<Integrante> initJugadores(Scanner in,int numJugs, int numMaquinas){
		
		List<Integrante> j = new ArrayList<Integrante>();
		
		for(int i = 0; i < numJugs - numMaquinas; i++) {
			System.out.println("Introduce el nick del jugador " + (int)(i+1) );
			String nombre = in.nextLine();
			j.add(new Jugador(nombre));
		}		
		
		
		String afirma; 
		
		if(numJugs != 0) { // Si se han introducido jugadores 
			
			do { // Este bucle sirve para decidir si se va a eliminar algun jugador de la lista o no
		
				System.out.println("");
				System.out.println("Quieres quitar a alguno: SI o NO");

				Scanner respuesta = new Scanner(System.in);
				afirma = respuesta.nextLine().toUpperCase().trim(); // Mete SI o NO

				if(afirma.contentEquals("SI")) {
					// Me muestra todos los jugadores en la partida
					int i = 1;
					for (Integrante jug: j) {
						System.out.println();
						System.out.println(i +". " + jug.getNick());
						i++;
					}
				
					System.out.println();
					System.out.println("A quien [nº que ocupa el jugador]:");
					int c = respuesta.nextInt(); // Determina que usuario eliminar mediante su posicion
					j.remove(c - 1);
					for (int k = 0; k < j.size(); k++) {
						System.out.println(k + 1 +". "+ j.get(k).getNick());
						System.out.println("");
					}
				}
				else if (afirma.contentEquals("NO")) {
					System.out.println ("-------------------- COMIENZA LA PARTIDA --------------------");
				}
				else {
					afirma = "SI";
				}
			} while (afirma.contentEquals("SI"));
		}
		return j;
		
	}
}
