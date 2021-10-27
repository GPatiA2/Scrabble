package vista;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Cliente.EstadoCliente;
import Cliente.TraductorCliente;
import Command.ComandoSalida;
import Excepciones.CommandExecuteException;
import Servidor.ProtocoloComunicacion;
import controlador.Controller;
import controlador.Registrador;
import modelo.Ficha;
import utils.Coordenadas;

public class MainWindow extends JFrame {
	public static final int ALTURA= 850;
	public static final int ANCHO = 810;
	
	public static final float [] colorBordeTablero = Color.RGBtoHSB(29, 118, 27, null); 
	public static final float [] colorBorder = Color.RGBtoHSB(212, 212, 212, null); 
	
	private Registrador c;
	
	private JPanel currentPanel;	
	
	private static GamePanel gm;
	private LoginPanel loginpanel;
	private StartPanel startPanel;
	private LobbyPanel lp;
	
	
	public MainWindow(Registrador c) {
		super("SCRUMBBLE");
			this.c = c;
		
			// JMenuBar
						JMenuBar menuBarra = new JMenuBar();
						this.setJMenuBar(menuBarra);
						
						JMenu file = new JMenu ("Options"); // Opcion principal
						menuBarra.add(file);
															//Subopciones
						JMenuItem info = new JMenuItem ("Info_Juego");
						file.add(info);
						JMenuItem Command = new JMenuItem ("Comandos");
						file.add(Command);
						//
						
					
					
						class instruccion implements ActionListener {
							public void actionPerformed(ActionEvent e) {
									JOptionPane.showMessageDialog(null, "LE MOSTRAMOS LAS INSTRUCCIONES");
									Info_juego info = new Info_juego ("");
							}
						}
						
						class comando implements ActionListener {
							public void actionPerformed(ActionEvent e) {
									JOptionPane.showMessageDialog(null, "LE MOSTRAMOS LOS COMANDOS");
									Comandos comand = new Comandos ("");
							}
						}
					
						info.addActionListener(new instruccion());
						Command.addActionListener(new comando());
			
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			this.startPanel = new StartPanel(this);
			this.currentPanel = startPanel;
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
			
		
			
			this.cambiarDePanel(startPanel);

		
	}
	
	
	public void setVista(EstadoCliente userProfile) {
		switch (userProfile) {
		case START:
			startPanel = new StartPanel(this);
			cambiarDePanel(startPanel);
			break;
			
		case LOGIN:
			loginpanel = new LoginPanel(this);
			cambiarDePanel(loginpanel);
			break;
		case LOBBY:
			this.lp = new LobbyPanel(this);
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
		
	public void mostrarLista(String jugadoreslista) {
		this.lp.mostrarLista(jugadoreslista);
	}
	
	public void mostrar(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	public void PedirNumJugadores() {
		Object[] possibilities = {1,2,3,4,5,6,7,8};
		Integer n;
		do {
		 n = (Integer) JOptionPane.showInputDialog(
		 this,  "Elige un numero maximo de jugadores:", "Numero jugadores",
		 JOptionPane.INFORMATION_MESSAGE, 
		 null, 
		 possibilities,1);
		}while(n==null);
		
		TraductorCliente.getTraductor().NumJugadores(n);
	}
	
	public void PedirDificultadMaquinas() {
		Object[] niveles = {"EASY" , "MEDIUM" , "HARD"};
		String n;
		do {
			 n = (String) JOptionPane.showInputDialog(
			 this,  "Elige la dificultad: ", "Dificultad de las maquinas",
			 JOptionPane.INFORMATION_MESSAGE, 
			 null, 
			 niveles,1);
		}while(n==null);
		TraductorCliente.getTraductor().dificultadMaquinas(n);
	}

	public static Coordenadas getCoordenadas(int x, int y) {
		return gm.getcoordenadas(x, y);
	}
	
	public static List<Ficha> getMano() {
		return gm.getMano();
	}
	
		
}
