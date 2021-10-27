package Cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import Servidor.ProtocoloComunicacion;
import controlador.Sustituto;
import modelo.Casilla;
import modelo.Ficha;
import modelo.Jugador;
import utils.Coordenadas;
import vista.GamePanel;
import vista.MainWindow;
/**
 * Clase Cliente. Encargada de recibir el mensaje a través de su socket y mandarlo a traducir al traductor
 * del cliente
 * @author crist
 *
 */
public class Cliente {
	/**
	 * Entrada del cliente
	 */
	private BufferedReader in;
	/**
	 * Salida del cliente
	 */
    private PrintWriter out;
    /**
     * Socket del cliente
     */
    private Socket socket;
    /**
     * Estado del cliente
     */
    private EstadoCliente estado;
    /**
     * Ventana principal del cliente
     */
    private MainWindow ui;
    
	public static void main(String[] args) {
		new Cliente("localhost", 5000);
	}
	
	/**
	 * Constructor del cliente
	 * Inicializa sus atributos y construye el traductor del cliente
	 * @param ip ip del cliente
	 * @param port puerto por el cual se va a establecer la conexion
	 */
	public Cliente(String ip, int port) {
		try {
			estado = EstadoCliente.START;
			socket = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    out = new PrintWriter(socket.getOutputStream(), true);
		    Sustituto s = new Sustituto();
		    TraductorCliente.getTraductor().setDatos(this,this.out,s);
			ui= new MainWindow(s);
			ui.setVista(EstadoCliente.LOGIN);
			ui.setVisible(true);
			while (true) {
				    	
				String input = in.readLine();
					
				System.out.println("Server returned:" + input);
				    		
				TraductorCliente.getTraductor().Translate(input);
					    
			}

		
			}
		 catch(Exception e) {
			 System.out.println(e.getMessage());
		 }
		finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
	
	}
	
	public void LoginCorrect(String nick) {
		estado = EstadoCliente.LOBBY;
		ui.setVista((estado));	
		TraductorCliente.getTraductor().numJugadoresRequest();			
		
	}
	public void pedirNumJugadores() {
		ui.PedirNumJugadores();
	}
	public void refresh(String listaJugadores) {
		ui.mostrarLista(listaJugadores);
	}
	public void startGame() {
		estado = EstadoCliente.GAME;
		ui.setVista(estado);
	}

	public void startGame_error() {
		ui.mostrar("Solo puede comenzar la partida el creador del lobby");		
	}
	public void Error(String datos) {
		ui.mostrar(datos);
	}
	public void exit() {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	System.exit(0);
	}
	public void pedirDificultad() {
		ui.PedirDificultadMaquinas();
	}
}