package vista;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import Cliente.Cliente;
import Cliente.EstadoCliente;
import Cliente.TraductorCliente;
import Servidor.Servidor;
import controlador.Controller;
import controlador.Registrador;
import javafx.stage.FileChooser;
import modelo.Integrante;
import modelo.Jugador;
import modelo.Maquina;
import modelo.Scrabble;



public class MenuPpal extends JFrame {
	
	private Image fondo;
	
	public MenuPpal() { // Constructora
		super("Scrabble");
		fondo = new ImageIcon("Dibujos/fondo.jpg").getImage();
		initGUI();
		setVisible(true);
	}

	private JButton creaBoton(String nombre) {
		JButton button = new JButton(nombre);
		return button;
	}
	
	
	public void initGUI (){
		JButton localGame,netPlayHost,joinNetPlay,loadGame, salir;
		
		JPanel mainPanel = new JPanel() {
			@Override
			 protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(fondo,0,0, this);
			}
		}; // Creo mi panel principal
		mainPanel.setLayout(new BorderLayout());
		JPanel panelCentral = new JPanel();
		panelCentral.setOpaque(false);
		panelCentral.setLayout(new GridLayout(2,1));
		panelCentral.add(new JLabel(new ImageIcon("Dibujos/logo.png")));
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setOpaque(false);
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));
		//NEWGAME
		
		localGame = creaBoton ("PARTIDA LOCAL");
		localGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "ESTE BOTON JUGAR FUNCIONA");
				List<Integrante> lj = cargarJugadores();
				dispose();
				Scrabble sc = null;
				try {
					sc = new Scrabble(lj);
					Controller c = new Controller (sc);
					//Esto esta aqui de momento, si queremos jugar por consola tenemos que diseñar una vista
					//  que use los toString, no es dificil
					MainWindow mw = new MainWindow (c);
					mw.setVista(EstadoCliente.GAME);
					mw.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		localGame.setAlignmentX(CENTER_ALIGNMENT);
		buttonsPanel.add(localGame); // Lo añado a la ventana
		
		
		
		netPlayHost = new JButton("ALOJAR PARTIDA EN RED");
		netPlayHost.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				Servidor s = new Servidor();
				new Thread() {
					public void run() {
						Cliente.main(null);
					}
				}.start();
				dispose();
			}
			
		});
		netPlayHost.setAlignmentX(CENTER_ALIGNMENT);
		buttonsPanel.add(netPlayHost);
		
		joinNetPlay = creaBoton("UNIRSE A PARTIDA EN RED");
		joinNetPlay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Thread() {
					public void run() {
						Cliente.main(null);
					}
				}.start();
				dispose();
			}
			
		});
		joinNetPlay.setAlignmentX(CENTER_ALIGNMENT);
		buttonsPanel.add(joinNetPlay);
		
		
		//LOADGAME
		loadGame = creaBoton ("CARGAR PARTIDA");
		loadGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					JFileChooser fc = new JFileChooser();
					int sel = fc.showOpenDialog(mainPanel);
					if(sel == JFileChooser.APPROVE_OPTION) {
						File f = fc.getSelectedFile();
						List<Integrante> j = new ArrayList<Integrante>();
						j.add(new Jugador("OWO"));
						Scrabble sc;
						try {
							sc = new Scrabble(j);
							Controller ctrl = new Controller(sc);
							ctrl.load(f);
							SwingUtilities.invokeLater(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									MainWindow mw = new MainWindow(ctrl);
									mw.setVista(EstadoCliente.GAME);
									mw.setVisible(true);
								}
								
							});
							dispose();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			
		});
		loadGame.setAlignmentX(CENTER_ALIGNMENT);
		buttonsPanel.add(loadGame); // Lo añado a la ventana
		
		
		//SALIR
		salir = creaBoton ("SALIR");
		salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "GRACIAS POR JUGAR A SCRABBLE :D");
				System.exit(0);
		}
		});
		salir.setAlignmentX(CENTER_ALIGNMENT);
		buttonsPanel.add(salir); // Lo añado a la ventana
		
		panelCentral.add(buttonsPanel);
		buttonsPanel.setVisible(true);
		mainPanel.add(panelCentral, BorderLayout.CENTER);
		this.getContentPane().add(mainPanel);
		mainPanel.setVisible(true);
		//
		
		
		// Barras adcionales
		JMenuBar menuBarra = new JMenuBar();
		this.setJMenuBar(menuBarra);
		
		JMenu file = new JMenu ("Options"); // Opcion principal
		menuBarra.add(file);
											//Subopciones
		JMenuItem info = new JMenuItem ("Info_Juego");
		file.add(info);
		JMenuItem Leader = new JMenuItem ("Leaderboard");
		file.add(Leader);
		//
		
		class cargar implements ActionListener {
			public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "ESTE BOTON  DE CARGA FUNCIONA");
					mainPanel.setVisible(false);
				
			}
		}

		class instruccion implements ActionListener {
			public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "LE MOSTRAMOS LAS INSTRUCCIONES");
					Info_juego info = new Info_juego ("");
			}
		}

		this.pack();
//		this.setLocationRelativeTo(null);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	    ///////

	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPpal frame = new MenuPpal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	List<Integrante> cargarJugadores (){
		Tabla t = new Tabla(this);
		t.setVisible(true);
		List <Integrante> l = t.getList();
		String[] possibilities = {"EASY", "MEDIUM", "HARD"};
		
		String n = (String) JOptionPane.showInputDialog(
				 this, 
				 "Elige una dificultad para las maquinas:", 
				 "Dificultad", 
				 JOptionPane.INFORMATION_MESSAGE, 
				 null, 
				 possibilities, 
				 "Plane");
		boolean ok = false;
		int maquinas = 0;
		while(!ok) {
			String c = JOptionPane.showInputDialog("Introduce el numero de maquinas que juegan");
			try {
				maquinas = Integer.parseInt(c);				
				ok = true;
			}
			catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "Debes introducir un numero");
				ok = false;
			}
		}
		for(int i = 0; i < maquinas; i++) {
			l.add(new Maquina());
		}
		
		return l;
	}
}
	
	

