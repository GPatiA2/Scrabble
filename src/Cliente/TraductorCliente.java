package Cliente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import Command.Command;
import Excepciones.CommandExecuteException;
import Servidor.ProtocoloComunicacion;
import controlador.Registrador;
import controlador.Sustituto;
import modelo.Casilla;
import modelo.Ficha;
import modelo.TableroObserver;
import modelo.TManagerObserver;
import utils.Coordenadas;
/**
 * Traductor del servidor
 * Traduce los mensajes enviados pos el servidor para el cliente.
 * @see Cliente
 */
public class TraductorCliente{
	/**
	 * Instancia estática del traductor
	 */
	private static TraductorCliente t;
	/**
	 * Salida del cliente para poder enviar mensajes al servidor
	 */
    private PrintWriter out;
    /**
     * Cliente
     * @see Cliente
     */
    private Cliente c;
    /**
     * Sustituto 
     */
    private Sustituto s;
    /**
     * Método que devuelve {@link #t}
     * @return #t traductor
     */
	public static TraductorCliente getTraductor() {
			if(t== null) t = new TraductorCliente();
			return t;
	}
	/**
	 * Método para establecer los objetos necesarios para
	 * el correcto funcionamiento del traductor
	 * @param c cliente
	 * @param out salida del cliente
	 * @param s sustituto
	 */
	public void setDatos(Cliente c, PrintWriter out,Sustituto s) {
		this.c = c;
		this.out = out;
		this.s = s;
	}
	/**
	 * Método que traduce los mensajes para el cliente.
	 * Hace uso del ProtocoloComunicacion
	 * @see ProtocoloComunicacion
	 * @param message mensaje a traducir
	 */
	public void Translate(String message) {
		if (message.startsWith(ProtocoloComunicacion.LOGIN_CORRECTO)) {
			String username = message.substring(ProtocoloComunicacion.LOGIN_CORRECTO.length() + 1);
			c.LoginCorrect(username);
	    }
		else if (message.equals(ProtocoloComunicacion.LOGOUT_CORRECT)) {
	    	c.exit();
	    }
		else if (message.startsWith(ProtocoloComunicacion.LOGIN_ERROR)) {
	    	c.Error("Nick ya registrado");
	    } 
		
		else if (message.startsWith(ProtocoloComunicacion.NUM_JUGADORES_ACCEPT)) {
	    	c.pedirNumJugadores();	
	    }	
		else if (message.startsWith(ProtocoloComunicacion.REFRESH_JUGADORES_SUCCESS)) {
			String jugadoreslista = message.substring(ProtocoloComunicacion.REFRESH_JUGADORES_SUCCESS.length() + 1);
			c.refresh(jugadoreslista);
		}  
		else if (message.startsWith(ProtocoloComunicacion.GAME_START)) {
			c.startGame();
    	}
		else if (message.startsWith(ProtocoloComunicacion.GAME_START_ERROR)) {
			c.startGame_error();
    	}		
		else if (message.startsWith(ProtocoloComunicacion.ANIADIR_FICHA_MANO)) {
			String[] aniadir = message.substring(ProtocoloComunicacion.ANIADIR_FICHA_MANO.length() + 1).split(" ");
			int puntos = aniadir[1].charAt(2) - '0';
			Ficha f = new Ficha(aniadir[1].charAt(0), puntos);
			f.setId(aniadir[0]);
			s.fichaRobada(f, null);			
	    }
		else if (message.startsWith(ProtocoloComunicacion.BORRAR_FICHA_MANO)) {
			String[] s = message.substring(ProtocoloComunicacion.BORRAR_FICHA_MANO.length() + 1).split(" ");
			Ficha f =new Ficha(); f.setId(s[0]);
			this.s.borrarFichaMano(f,null, s[1].equals("true"));
		}
		else if (message.startsWith(ProtocoloComunicacion.ACTUALIZAR_CASILLA)){

			String[] s = message.substring(ProtocoloComunicacion.ANIADIR_FICHA_MANO.length() + 1).split(" ");
			int coordX = Integer.parseInt(s[0]);
			int coordY = Integer.parseInt(s[1]);
			Casilla casilla;
			
			if (s.length==4) {
				int puntos = s[2].charAt(2) - '0';
				
				casilla= new Casilla(new Ficha(s[2].charAt(0), puntos),Integer.parseInt(s[3]));
			}
			else {casilla = new Casilla(Integer.parseInt(s[2]));}
			
			this.s.actualizaCasilla(new Coordenadas(coordX,coordY), casilla);

		}
		else if (message.startsWith(ProtocoloComunicacion.LOBBY_FULL)) {
			c.Error("Lobby full, intentelo en unos minutos");
		}
		else if (message.startsWith(ProtocoloComunicacion.MOSTRAR_TURNOS)) {
			s.mostrarTurnos(message.split(" " )[1], message.split(" " )[2]);
		}
		else if (message.startsWith(ProtocoloComunicacion.DIFICULTAD_MAQUINAS_REQUEST)) {
			c.pedirDificultad();
		}
		else if (message.startsWith(ProtocoloComunicacion.MOSTRAR_PUNTOS)) {
			s.mostrarPuntos(Integer.parseInt(message.split(" ")[1]), message.split(" ")[2]);
		}
		else if (message.startsWith(ProtocoloComunicacion.MOSTRAR_MONEDAS)) {
			s.mostrarMonedas(Integer.parseInt(message.split(" ")[1]), message.split(" ")[2]);
		}
		else if(message.startsWith(ProtocoloComunicacion.ERROR)) {
			c.Error(message.substring(ProtocoloComunicacion.ERROR.length()));			
		}
		else if (message.startsWith(ProtocoloComunicacion.ON_REGISTER)) {
			String m[] = message.split(" ");
			String nick = m[1];
			int puntosJugador = Integer.parseInt(m[2]);	
			int monedasJugador = Integer.parseInt(m[3]);
			this.s.mostrarPuntos(puntosJugador, nick);
			
			List<Ficha> mano = new ArrayList<>();
			for (int i =4 ; i <m.length;i++) {
				int puntos = m[i].charAt(2)- '0';
				Ficha f = new Ficha(m[i].charAt(0), puntos);
				f.setId(m[++i]);
				mano.add(f);
			}
			s.onRegister(nick, puntosJugador, monedasJugador, mano);
			
		}
		
	}
	/**
	 * Envía del maximo numero de jugadores del lobby al 
	 * servidor
	 * @param n numero maximo de jugadores del lobby
	 */
	public void NumJugadores(int n) {
		out.println(ProtocoloComunicacion.NUM_JUGADORES + " " + n);
		
	}
	/**
	 * Envía al servidor una petición para comenzar el juego
	 */
	public void gameRequest(){
		out.println(ProtocoloComunicacion.GAME_REQUEST);
	}
	/**
	 * Envía al servidor una petición para establecer el numero maximo
	 * del jugadores
	 */
	public void numJugadoresRequest() {
		out.println(ProtocoloComunicacion.NUM_JUGADORES_REQUEST);
	}
	/**
	 * Envía al servidor una peticion para refrescar su lobby con los jugadores
	 * actuales
	 */
	public void RefreshRequest() {
		out.println(ProtocoloComunicacion.REFRESH_JUGADORES_request);
	}
	/**
	 * Envía al servidor una peticion de login con su nick correspondiente
	 * @param username nombre
	 */
	public void LoginRequest(String username) {
		out.println(ProtocoloComunicacion.LOGIN_REQUEST + " " + username);		
	}
	/**
	 * Envía al servidor una petición para desconectarse  correctamente del juego
	 */
	public void LogoutRequest() {
		out.println(ProtocoloComunicacion.LOGOUT_REQUEST);			
	}
	/**
	 * Envío del nivel de dificultad de las IAs
	 * @param nivel 
	 */
	public void dificultadMaquinas(String nivel) {
		out.println (ProtocoloComunicacion.DIFICULTAD_MAQUINAS + " " + nivel);
	}
	/**
	 * Envío del comando a ejecutarse en el servidor
	 * @param c
	 */
	public void runCommand(Command c) {
		out.println(c.toString());
	}
	/**
	 * Envío de la peticion de cargar una partida con el nombre del archivo a cargar
	 * @param fichero nombre del archivo
	 */
	public void load(File fichero) {
		out.println(ProtocoloComunicacion.LOAD + " " + fichero.getPath());
	}
	
	/**
	 * Envío de la peticion de guardar una partida con el nombre del archivo a cargar
	 * @param fichero nombre del archivo
	 */
	public void save(String fichero) {
		out.println(ProtocoloComunicacion.GUARDAR_PARTIDA + " "+fichero.toString());
	}
}
