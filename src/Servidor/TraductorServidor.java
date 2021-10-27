package Servidor;

import java.awt.Point;
import java.io.File;
import java.util.List;

import Cliente.Cliente;
import Command.Command;
import Command.CommandGenerator;
import Excepciones.CommandParseException;
import controlador.Controller;
import controlador.Registrador;
import modelo.Casilla;
import modelo.Ficha;
import modelo.TableroObserver;
import modelo.Integrante;
import modelo.Jugador;
import modelo.JugadorObserver;
import modelo.ModelObserver;
import modelo.TManagerObserver;
import modelo.Tablero;
import utils.Coordenadas;
/**
 * Clase especializada que se encarga de traducir los mensajes que recibe de un jugador
 * conectado al servidor y a traducir los mensajes del servidor a el formato del socket.
 *
 * Utiliza patron Singleton para asegurarnos de que solo existe un traductor y que sea global
 */
public class TraductorServidor implements TableroObserver,TManagerObserver,JugadorObserver, LobbyObserver{

	private List<JugadorConectado> l;
	/**
	 * Instancia est�tica del traductorServidor
	 * @see TraductorServidor
	 */
	private static TraductorServidor t;
	/**
	 * Servidor
	 * @see Servidor
	 */
	private Servidor s;
	/**
	 * M�todo que devuelve la instancia est�tica del traductor que es
	 * atributo de esta clase
	 * @return {@link #t} traductor
	 */
	public static TraductorServidor getTraductor() {
		if(t== null) t = new TraductorServidor();
		return t;
	}
	/**
	 * M�todo para asignar una instancia al atributo servidor de esta clase
	 * 
	 * @param s Servidor
	 * @see Servidor
	 */
	public void setServer(Servidor s) {
		this.s = s;
	}
	/**
	 * M�todo que traduce un mensaje recibido en el servidor y le informa de la acci�n
	 * a realizar sobre el modelo
	 * Recibe un JugadorConectado y el mensaje a traducir
	 * @param j JugadorConectado
	 * @param message Mensaje a traducir
	 * @see JugadorConectado
	 */
	public void Translate(JugadorConectado j, String message) {
		try {
			if (message.startsWith(ProtocoloComunicacion.LOGIN_REQUEST)) {									
				String[] nombre = message.split(" ");
				String nick = nombre[1]; 
				s.login(j, nick);					
			}
			else if (message.equals(ProtocoloComunicacion.LOGOUT_REQUEST)) {		
				Command c = CommandGenerator.parseCommand(message.split(" "));
				if (c!=null) {
					s.executeCommand(c);					
				}
				s.logout(j);							
			}
			else if (message.equals(ProtocoloComunicacion.REFRESH_JUGADORES_request)) {			
				s.refresh();
			}
			else if (message.startsWith(ProtocoloComunicacion.NUM_JUGADORES_REQUEST)){
				s.NumJugadores(j);
			}
			else if (message.startsWith(ProtocoloComunicacion.NUM_JUGADORES)){
				String[] num = message.split(" ");
				s.setNumJugadores(Integer.parseInt(num[1]));
			}
			else if (message.startsWith(ProtocoloComunicacion.GAME_REQUEST)){
				/*GAME*/
				s.play(j);
			}
			else if (message.startsWith(ProtocoloComunicacion.DIFICULTAD_MAQUINAS)) {
				s.dificultadMaquinas(message.split(" ")[1]);
			}
			else if (message.startsWith(ProtocoloComunicacion.LOAD)) {
				s.load(new File(message.split(" ")[1]));
			}
			else if(message.startsWith(ProtocoloComunicacion.GUARDAR_PARTIDA)) {
				s.save(message.split(" ")[1]);
			}
			else {
				Command c = CommandGenerator.parseCommand(message.split(" "));
				if (c!=null) {
					s.executeCommand(c);		
					System.out.println("OWOWOWOOW");
				}
			}
		
		}
		catch(Exception e) {
			j.getOutputStream().println(ProtocoloComunicacion.ERROR +" "+ e.getMessage());
		}
		
	}
	/**
	 * Asigna una lista a {@link #l}
	 * @param j lista de JugadorConectado
	 */
	public void setJugadores(List<JugadorConectado> j ) {
		this.l = j;
	}
	
	//Metodos de GameObserver
	@Override
	public void actualizaCasilla(Coordenadas coord, Casilla c) {
		for(JugadorConectado jugador : this.l) {
			jugador.getOutputStream().println(ProtocoloComunicacion.ACTUALIZAR_CASILLA + " " +
		coord.toString() + " " + c.to_string());
		}
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Override
	public void fichaRobada(Ficha f, Integrante j) {
		for(JugadorConectado jugador : this.l) {
			if (jugador.getNick().equals(j.getNick())){
				jugador.getOutputStream().println(ProtocoloComunicacion.ANIADIR_FICHA_MANO + " " + f.getId() + " " +f.toString());
			}
		}
	}

	@Override
	public void fichaUsada(Ficha f, Integrante j) {
		for(JugadorConectado jugador : this.l) {
			if (jugador.getNick().equals(j.getNick())){
				jugador.getOutputStream().println(ProtocoloComunicacion.BORRAR_FICHA_MANO + " " + f.getId()+ " "+ true);
			}
		}
	}

	@Override
	public void borrarFichaMano(Ficha f, Integrante j,boolean colocada) {
		
		for(JugadorConectado jugador : this.l) {
			if (jugador.getNick().equals(j.getNick())){
				jugador.getOutputStream().println(ProtocoloComunicacion.BORRAR_FICHA_MANO + " " + f.getId()+ " "+ colocada);
			}
		}
	}
	//Metodos TManagerObserver
	@Override
	public void mostrarTurnos(String act, String sig) {
		for(JugadorConectado jugador : this.l) {
			jugador.getOutputStream().println(ProtocoloComunicacion.MOSTRAR_TURNOS + " " + act + " " + sig);
		}
	}

	@Override
	public void mostrarPuntos(int puntos, String nick) {
		for(JugadorConectado jugador : this.l) {
			if(jugador.getNick().equals(nick)) {
				jugador.getOutputStream().println(ProtocoloComunicacion.MOSTRAR_PUNTOS+ " " + puntos + " " + nick);
			}
		}
	}

	@Override
	public void mostrarMonedas(int monedas, String nick) {
		for(JugadorConectado jugador : this.l) {
			if(jugador.getNick().equals(nick)) {
				jugador.getOutputStream().println(ProtocoloComunicacion.MOSTRAR_MONEDAS+ " " + monedas + " " + nick);
			}
		}
	}
	
	//Metodos LobbyObserver

	@Override
	public void loginCorrect(JugadorConectado j) {
		j.getOutputStream().println(ProtocoloComunicacion.LOGIN_CORRECTO +" "+ j.getNick());
	}

	@Override
	public void loginError(JugadorConectado j) {
		j.getOutputStream().println(ProtocoloComunicacion.LOGIN_ERROR);
		
	}

	@Override
	public void numJugadoresCorrect(JugadorConectado j) {
		j.getOutputStream().println(ProtocoloComunicacion.NUM_JUGADORES_ACCEPT);
	}

	@Override
	public void refresh(List<JugadorConectado> j, JugadorConectado creador) {
		this.l = j;
		String s = listaNombre(creador);
		
		for (JugadorConectado jugadores : this.l) {
			jugadores.getOutputStream().println(ProtocoloComunicacion.REFRESH_JUGADORES_SUCCESS+  " "+s);			
		}
		
	}
	/**
	 * M�todo privado que crea la lista de nombres para enviar
	 * a a hora de refrescar esta lista en la GUI del cliente
	 * @param creador
	 * @return
	 */
	private String listaNombre(JugadorConectado creador) {
		String str = "";
		String ocupacion ;
		for (JugadorConectado jugadores : this.l) {
			if (jugadores.equals(creador)) ocupacion = "creador";
			else ocupacion = "invitado";
			str += ocupacion + " " +jugadores.getNick()+ " ";
		}
		return str;
	}
	@Override
	public void start_game() {
		for(JugadorConectado j : this.l) {
			j.getOutputStream().println(ProtocoloComunicacion.GAME_START);
		}
	}
	
	@Override
	public void start_game_error(JugadorConectado j) {
		j.getOutputStream().println(ProtocoloComunicacion.GAME_START_ERROR);
	}

	@Override
	public void lobby_full(JugadorConectado j) {
		j.getOutputStream().println(ProtocoloComunicacion.LOBBY_FULL);
	}
	

	public void logoutCorrect(JugadorConectado j) {
		j.getOutputStream().println(ProtocoloComunicacion.LOGOUT_CORRECT);	
	}

	@Override
	public void GetDificultad(JugadorConectado j) {
		j.getOutputStream().println(ProtocoloComunicacion.DIFICULTAD_MAQUINAS_REQUEST);
	}
	/*******************************/
	@Override
	public void registerOn(Registrador c) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onError(String err, String nick) {
		for(JugadorConectado jugador : this.l) {
			if (nick.equals(jugador.getNick())){	
				jugador.getOutputStream().println(ProtocoloComunicacion.ERROR + err);
			}
			
		}
	}
	@Override
	public void nuevoTurno(Integrante i, String act, String sig) {

	}
	@Override
	public void onRegister(String nick, int puntos, int monedas, List<Ficha> mano) {
		for(JugadorConectado jugador : this.l) {
			if (nick.equals(jugador.getNick())){	
				String mensaje = ProtocoloComunicacion.ON_REGISTER + " " + jugador.getNick() + " " +puntos + " " +monedas;
				for (Ficha f : mano) mensaje += " " + f.toString() + " " + f.getId();
				jugador.getOutputStream().println(mensaje);
			
			}
			
		}
	}
	@Override
	public void onRegister(String act, String sig) {
		for(JugadorConectado jugador : this.l) {
			jugador.getOutputStream().println(ProtocoloComunicacion.MOSTRAR_TURNOS + " " + act + " " + sig);
		}
	}
	@Override
	public void turnoAcabado() {
		s.removeJugadorObserver(this);
		s.addJugadorObserver(this);
		
	}
	@Override
	public void onRegister(List<List<Casilla>> grid) {
		// TODO Auto-generated method stub		
	}
	

}
