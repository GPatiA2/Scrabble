package vista;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.SwingUtilities;

import Cliente.Cliente;
import Cliente.EstadoCliente;
import Servidor.Servidor;
import controlador.ControllerLocal;
import modelo.Integrante;
import modelo.Jugador;
import modelo.Maquina;
import modelo.Scrabble;



public class MenuPpal extends JFrame {
	
	public static final int ALTURAMENU= 600;
	public static final int ANCHOMENU = 400;
	private static final long serialVersionUID = 1L;
	
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
		this.setLocationRelativeTo(null);
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
					ControllerLocal c = new ControllerLocal (sc);
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
						new Cliente("localhost", 5000);
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
				String ip = JOptionPane.showInputDialog("Introduce la IP del servidor");
				new Thread() {
					public void run() {
						new Cliente(ip, 5000);
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
					JFileChooser fc = new JFileChooser(new File("Saves"));
					int sel = fc.showOpenDialog(mainPanel);
					if(sel == JFileChooser.APPROVE_OPTION) {
						File f = fc.getSelectedFile();
						List<Integrante> j = new ArrayList<Integrante>();
						j.add(new Jugador("OWO"));
						Scrabble sc;
						try {
							sc = new Scrabble(j);
							ControllerLocal ctrl = new ControllerLocal(sc);
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
		
		// Barras adcionales
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
					Info_juego info = new Info_juego ("");
			}
		}
		class comando implements ActionListener {
			public void actionPerformed(ActionEvent e) {
					Comandos comand = new Comandos ("");
			}
		}

		info.addActionListener(new instruccion());
		Command.addActionListener(new comando());
		
		this.setSize(ANCHOMENU, ALTURAMENU);
		this.setMinimumSize(new Dimension(ANCHOMENU, ALTURAMENU));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	List<Integrante> cargarJugadores (){
		Tabla t = new Tabla(this);
		t.setLocationRelativeTo(this);
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
			try {
				l.add(new Maquina(n, i));
			} catch (Exception e) {
				//Esta excepcion no deberia darse ahora que no se escribe por consola
				e.printStackTrace();
			}
		}
		
		return l;
	}
}
	
	

