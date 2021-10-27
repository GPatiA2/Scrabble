package vista;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.List;


import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Cliente.EstadoCliente;
import Command.ComandoSalida;
import Excepciones.CommandExecuteException;

import controlador.Controller;
import controlador.ControllerLobby;
import modelo.Ficha;
import utils.Coordenadas;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static final int ALTURA= 850;
	public static final int ANCHO = 810;
	
	public static final float [] colorBordeTablero = Color.RGBtoHSB(29, 118, 27, null); 
	public static final float [] colorBorder = Color.RGBtoHSB(212, 212, 212, null); 
	
	private Controller c;
	
	private JPanel currentPanel;	
	
	private static GamePanel gm;
	private LoginPanel loginpanel;
	private LobbyPanel lp;
	
	
	public MainWindow(Controller c) {
		super("SCRUMBBLE");
			this.c = c;
			JMenuBar menuBarra = new JMenuBar();
			this.setJMenuBar(menuBarra);
			
			JMenu file = new JMenu ("Opciones"); // Opcion principal
			menuBarra.add(file);
												//Subopciones
			JMenuItem info = new JMenuItem ("Instrucciones");
			file.add(info);
			JMenuItem Command = new JMenuItem ("Comandos");
			file.add(Command);
			//
			
			class instruccion implements ActionListener {
				public void actionPerformed(ActionEvent e) {
						new Info_juego ("");
				}
			}
			
			class comando implements ActionListener {
				public void actionPerformed(ActionEvent e) {
						 new Comandos ("");
				}
			}
		
			info.addActionListener(new instruccion());
			Command.addActionListener(new comando());
			
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			this.setSize(ANCHO, ALTURA);
			this.setMinimumSize(new Dimension(ANCHO, ALTURA));
			this.setLocationRelativeTo(null);
			this.addWindowListener(new WindowAdapter() {
				  public void windowClosing(WindowEvent e) {
					int opcion = JOptionPane.showConfirmDialog(null, 
						"Seguro que quiere salir del juego?", "Salir",
						JOptionPane.YES_NO_OPTION);

					if (opcion == JOptionPane.YES_OPTION) {
						
						//Comunico que se desconecta
						try {
							c.runCommand(new ComandoSalida());
						} catch (FileNotFoundException | CommandExecuteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.exit(0);
						
					}
				}
			});
			this.currentPanel = new JPanel();	
	}
	
	
	public void setVista(EstadoCliente userProfile) {
		switch (userProfile) {
		case LOGIN:
			loginpanel = new LoginPanel((ControllerLobby<String>)this.c,this);
			cambiarDePanel(loginpanel);
			break;
		case LOBBY:
			ControllerLobby<String> c = (ControllerLobby<String>)this.c;
			c.removeLobbyObserver(loginpanel);
			this.lp = new LobbyPanel(c,this);
			cambiarDePanel(lp);
			break;
		case GAME:
			this.gm = new GamePanel(this.c);	
			cambiarDePanel(gm);
			break;
		default:
			break;
		}
		
	}
	
	private void cambiarDePanel(JPanel newPanel) {
		
		this.getContentPane().removeAll();
		this.getContentPane().add(newPanel);
		this.currentPanel = newPanel;
		validate();
		repaint();
	}	
		
	
	public void mostrar(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	public int PedirNumJugadores() {
		Object[] possibilities = {1,2,3,4,5,6,7,8};
		Integer n;
		do {
		 n = (Integer) JOptionPane.showInputDialog(
		 this,  "Elige un numero maximo de jugadores:", "Numero jugadores",
		 JOptionPane.INFORMATION_MESSAGE, 
		 null, 
		 possibilities,1);
		}while(n==null);
		
		return n;
	}
	
	public String PedirDificultadMaquinas() {
		Object[] niveles = {"EASY" , "MEDIUM" , "HARD"};
		String n;
		do {
			 n = (String) JOptionPane.showInputDialog(
			 this,  "Elige la dificultad: ", "Dificultad de las maquinas",
			 JOptionPane.INFORMATION_MESSAGE, 
			 null, 
			 niveles,1);
		}while(n==null);
		return n;
	}

	public static Coordenadas getCoordenadas(int x, int y) {
		return gm.getcoordenadas(x, y);
	}
	
	public static List<Ficha> getMano() {
		return gm.getMano();
	}
	
}
