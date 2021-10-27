package Servidor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Command.Command;
import Excepciones.CommandExecuteException;
import Servidor.Interprete.Interpreter;
import Servidor.InterpreterFactoria.InterpreterFactory;
import Servidor.InterpreterFactoria.InterpreterFactoryInterface;
import Servidor.InterpreterFactoria.InterpreterFactory.Estado;
import controlador.Controller;
import controlador.ControllerLocal;
import modelo.Integrante;
import modelo.Jugador;
import modelo.Maquina;
import modelo.Scrabble;
/**
 * Clase Servidor
 *
 */
public class Servidor implements Runnable{
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
	 * Interprete de los comandos que llegan por el socket
	 */
	private Interpreter interprete;
	/**
	 * Estado del servidor para elegir el interprete correspondiente
	 */
	private Estado estado;
	/**
	 * Fabrica para crear los interpreters
	 */
	private InterpreterFactoryInterface factoria_inter;
	/**
	 * Traductor
	 */
	private TraductorServidor t;
	/**
	 * Constructor sin parametros del servidor	
	 * Construye la {@link #listaJugadoresConectados}, el {@link #socket}, {@link #lobby} y a�ade
	 * a este el traductor como observador. Despues inicializa la hebra.
	 * @see #initSocket()
	 */
	public Servidor() {
		t = new TraductorServidor();
		System.out.println("------Servidor Conectado-----");
		try {
			this.socket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		lobby = new Lobby();
		lobby.addObserver(t);


		factoria_inter = new InterpreterFactory(lobby,Servidor.this);
		this.initThread();
	}
	/**
	 * Inicializa la hebra del servidor. 
	 */
	private void initThread() {
		System.out.println("------Socket servidor escuchando... Puerto:  " + PORT + "------");
		
		this.serverThread = new Thread(this);
		serverThread.start();
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
				
				System.out.println("-----Se ha conectado un jugador desde " + clientSocket.getInetAddress() + "-----");
				
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
												
							while (clientSocket.isConnected()) {
								
								String mensajeEnviado = clientInput.readLine();
								
								
								System.out.println("Cliente envio: " + mensajeEnviado);

								try {
									Estado e = InterpreterFactory.Estado.isState(mensajeEnviado.split(" ")[0]);
									if(e!=null && !e.equals(estado)) {
										estado = e;
										cambiarEstado(factoria_inter.buildInterpreter(e));
									}
									
									interprete.execute(mensajeEnviado.substring(5),j);
									
								} catch (Exception e) {
									j.getOutputStream().println(ProtocoloComunicacion.ERROR +" "+ e.getMessage());
									e.printStackTrace();
									clientSocket.close();
									lobby.removeJugador(j);
								}
								
																
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
			int maquinas = lobby.getMaxJugadores()- lobby.getNumJugadoresJoined();
			for(int i = 0; i < maquinas; i++) {
				try {
					listaJugadores.add(new Maquina(lobby.getDificultad(), i));
				} catch (Exception e) {
					//Esta excepcion no deberia darse ahora que no se escribe por consola
					e.printStackTrace();
				}
			}
			
			Thread t = new Thread (new Runnable() {

				@Override
				public void run() {
					
					try {
					Scrabble sB = new Scrabble (listaJugadores);
					
					
					ctrl = new ControllerLocal(sB);					
					Servidor.this.t.registerOn(ctrl);	
					
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					
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
	 * Metodo para cargar una partida
	 * @param input
	 * @throws FileNotFoundException 
	 */
	public void load(File fichero) throws FileNotFoundException {
		ctrl.load(fichero);
	}
	/**
	 * Metodo para guardar una partida
	 * @param fichero
	 * @throws FileNotFoundException
	 */
	public void save(String fichero) throws FileNotFoundException {
		ctrl.save(fichero);
	}
	/**
	 * Permite cambiar el estado del servidor
	 * @param e
	 */
	private void cambiarEstado(Interpreter e) {
		this.interprete = e;
	}
}
