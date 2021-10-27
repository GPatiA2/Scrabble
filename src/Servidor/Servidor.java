package Servidor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Command.ComandoColocarFicha;
import Command.ComandoPasarTurno;
import Command.ComandoQuitarFicha;
import Command.Command;
import Excepciones.CommandExecuteException;
import controlador.Controller;
import modelo.AdminTurnos;
import modelo.Game;
import modelo.GeneradorDiccionario;
import modelo.GeneradorMazo;
import modelo.Integrante;
import modelo.Jugador;
import modelo.JugadorObserver;
import modelo.Scrabble;
import utils.Coordenadas;
/**
 * Clase Servidor
 *
 */
public class Servidor implements Runnable{
	
	public static void main (String[] args) {
		Servidor gs = new Servidor();
	}
	
	//ATRIBUTOS
	/**
	 * Puerto libre por el que se va a establecer la conexion. Por defecto 5000
	 */
	private static final int PORT= 5000;
	/**
	 * socket del servidor
	 */
	private ServerSocket socket;
	/**
	 * Lista de jugadores conectados al servidor
	 */
	private List<JugadorConectado> listaJugadoresConectados;
	/**
	 * Lobby
	 */
	private Lobby lobby;
	/**
	 * Hebra en la que se ejecuta el servidor
	 */
	private Thread serverThread;
	/**
	 * Controlador
	 */
	private Controller ctrl;
	/**
	 * Modelo
	 */
	private Scrabble sB;
	/**
	 * Constructor sin parametros del servidor	
	 * Construye la {@link #listaJugadoresConectados}, el {@link #socket}, {@link #lobby} y a�ade
	 * a este el traductor como observador. Despues inicializa la hebra.
	 * @see #initSocket()
	 */
	public Servidor() {
		System.out.println(ProtocoloComunicacion.SERVIDOR_INFO + " Servidor Conectado");
		listaJugadoresConectados = new ArrayList<JugadorConectado>();
		try {
			this.socket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		lobby = new Lobby();
		lobby.addObserver(TraductorServidor.getTraductor());
		TraductorServidor.getTraductor().setServer(this);
		this.initThread();
	}
	/**
	 * Inicializa la hebra del servidor. 
	 */
	private void initThread() {
		System.out.println(ProtocoloComunicacion.SOCKET_SERVIDOR+ " Socket servidor escuchando... Puerto:  " + PORT);
		
		this.serverThread = new Thread(this);
		serverThread.start();
	}
	/**
	 * Desconecta a un jugador del servidor. Lo elimina de {@link #listaJugadoresConectados} y 
	 * del {@link #lobby}
	 * @param j
	 *  @return <ul>
     *  <li>true: el jugador se encontraba en la lista</li>
     *  <li>false: el jugador no estaba en la lista</li>
     *  </ul>
	 * @throws IOException
	 */
	private boolean desconectar(JugadorConectado j) throws IOException {	
		//Elimino al jugador de la lista de conectados y su socket de la lista de sockets
		
		if(listaJugadoresConectados.contains(j)) {
			System.out.println(j.getNick() + " se ha desconectado del servidor.");
			listaJugadoresConectados.remove(j);
			lobby.removeJugador(j);
			return true;
		}
		return false;
	}
	/**
	 * Sobreescribe al metodo run de Runnable.
	 * Espera a que algun jugador le envie un mensaje y entonces crea una hebra para atender 
	 * su peticion haciendo uso del traductor.
	 */
	@Override
	public void run() {
		try {
			while (true) {
				
				//Espero a que algun cliente contacte con el servidor
				Socket clientSocket = this.socket.accept();
				
				System.out.println(ProtocoloComunicacion.SOCKET_SERVIDOR + " Se ha conectado un jugador desde " + clientSocket.getInetAddress());
				
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							//Para comunicarnos con el cliente
							// Entrada del cliente
							BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
							
							// Salida del cliente
							PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
							
							JugadorConectado j = new JugadorConectado(clientOutput);
							addJugadorConectado(j);
												
							while (clientSocket.isConnected()) {
								
								String mensajeEnviado = clientInput.readLine();
								
								if (mensajeEnviado == null) {
									desconectar(j);
									return;
								}
								
								System.out.println("Cliente envio: " + mensajeEnviado);
								TraductorServidor.getTraductor().Translate(j, mensajeEnviado);
																
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
				
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * A�ade un jugador a {@link #listaJugadoresConectados}
	 * @param j jugador que quiero a�adir
	 */
	private void addJugadorConectado(JugadorConectado j) {
		listaJugadoresConectados.add(j);
	}
	/**
	 * Loggea un jugador en el lobby y le pone nombre al jugador
	 * @param j jugador conectado que quiere loggearse
	 * @param nick nombre con el que se loggea
	 */
	public void login(JugadorConectado j,String nick) {
		j.setNick(nick);
		lobby.login(j);	
	}
	/**
	 * Desconecta a un jugador de {@link #listaJugadoresConectados}, le informa de que 
	 * se ha realizado correctamente y refresca el {@link #lobby}
	 * @param j
	 */
	public void logout(JugadorConectado j) {
		try {
			desconectar(j);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TraductorServidor.getTraductor().logoutCorrect(j);
		refresh();
	}
	/**
	 * Refresca el lobby
	 */
	public void refresh() {
		lobby.refresh();
	}
	/**
	 * Pide permiso el jugador para poder establecer el numero maximo de jugadores de lobby
	 * @param j jugador que pide permiso
	 */	
	public void NumJugadores(JugadorConectado j) {
		lobby.numJugadores(j);
	}
	/**
	 * Establece el numero maximo de jugadores del {@link #lobby}
	 * @param numJugadores numero maximo de jugadores
	 */
	public void setNumJugadores(int numJugadores) {
		lobby.setMaxNumJugadores(numJugadores);
		lobby.pedirDificultad();
	}
	/**
	 * Un jugador pide permiso para comenzar la partida. Si puede comenzar, se crea el controlador
	 * y se le pasan los jugadores que quieren jugadar. Luego se le a�ade al controlador el traductor
	 * como observador.
	 * @param j jugador que quiere comenzar la partida
	 */
	public void play(JugadorConectado j) {
		if(lobby.play(j)) {		
			List<Integrante> listaJugadores = new ArrayList<Integrante>();
			for(JugadorConectado jugador : this.lobby.getListaJugadores()) {
				listaJugadores.add(new Jugador(jugador.getNick()));
			}
			
			Thread t = new Thread (new Runnable() {

				@Override
				public void run() {

					try {
						sB = new Scrabble (listaJugadores);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					ctrl = new Controller(sB);					
					ctrl.addGameObserver(TraductorServidor.getTraductor());
					ctrl.addTManagerObserver(TraductorServidor.getTraductor());
					ctrl.addJugadorObserver(TraductorServidor.getTraductor());		
					
					
				}
				
			});
			t.start();
		}
	}
	/**
	 * Permite ejecutar un comando en el controlador
	 * @param command
	 * @throws FileNotFoundException
	 * @throws CommandExecuteException
	 */
	public void executeCommand(Command command) throws FileNotFoundException, CommandExecuteException {
		ctrl.runCommand(command);
	}
	/**
	 * M�todo que establece la dificultad de las maquinas
	 * @param nivel
	 */
	public void dificultadMaquinas(String nivel) {
		this.lobby.setDificultad(nivel);
	}
	/**
	 * M�todo para cargar una partida
	 * @param input
	 * @throws FileNotFoundException 
	 */
	public void load(File fichero) throws FileNotFoundException {
		ctrl.load(fichero);
	}
	
	public void save(String fichero) throws FileNotFoundException {
		ctrl.save(fichero);
	}
	public void removeJugadorObserver(JugadorObserver ob) {
		this.ctrl.removeJugadorObserver(ob);
	}
	public void addJugadorObserver(JugadorObserver ob) {
		this.ctrl.addJugadorObserver(ob);
	}
}
