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

import CommandClient.CommandClient;
import CommandClient.CommandClientGenerator;
import Servidor.ProtocoloComunicacion;
import Servidor.InterpreterFactoria.InterpreterFactory;
import controlador.ControllerCliente;
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
public class Cliente{
	/**
	 * Entrada del cliente
	 */
	private BufferedReader in;
	/**
	 * Salida del cliente
	 */
    private static PrintWriter out;
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
  	/**
	 * Constructor del cliente
	 * Inicializa sus atributos y construye el traductor del cliente
	 * @param ip ip del cliente
	 * @param port puerto por el cual se va a establecer la conexion
	 */
	public Cliente(String ip, int port) {
		try {
			estado = EstadoCliente.LOGIN;
			socket = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    out = new PrintWriter(socket.getOutputStream(), true);
		    ControllerCliente controller = new ControllerCliente(Cliente.out, new LobbyClient(),
		    		new TableroClient(), new TManagerClient(),new JugadorClient());
			ui= new MainWindow(controller);
			ui.setVisible(true);
			ui.setVista(estado);
			while (true) {
				    	
				String mensaje = in.readLine();
					
				System.out.println("Servidor envio:" + mensaje);
				try {
					CommandClient c = CommandClientGenerator.parseCommand(mensaje.split(" "));
					controller.executeCommandClient(c, mensaje);
					
				}catch(Exception e) {
					 System.out.println(e.getMessage());
				}
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
}