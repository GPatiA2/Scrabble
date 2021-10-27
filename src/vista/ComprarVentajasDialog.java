package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import Cliente.TraductorCliente;
import Command.ComandoComprarComodin;
import Command.ComandoInvertirSentido;
import Command.ComandoSaltarJugador;
import Excepciones.CommandExecuteException;
import controlador.Controller;
import controlador.Registrador;
import modelo.Casilla;
import modelo.Ficha;
import modelo.Integrante;
import modelo.JugadorObserver;
import modelo.ModelObserver;
import modelo.Tablero;
import modelo.TableroObserver;
import utils.Coordenadas;

public class ComprarVentajasDialog extends JDialog implements ActionListener, TableroObserver, JugadorObserver {

	private JComboBox<Ficha> fichas;
	private DefaultComboBoxModel<Ficha> modeloFichas;
	private JButton invertir;
	private JButton jump;
	private JButton comodin;
	private boolean opcion;
	private Registrador res;
	
	public ComprarVentajasDialog(Registrador res) {
		initGUI();
		this.res = res;
		registerOn(res);
		this.setSize(new Dimension(800, 250));
	}
	
	private void initGUI() {
		
		this.setTitle("Comprar ventajas");
		
		//Combo box que se usa en el dialogo aceptar comodin
		this.modeloFichas = new DefaultComboBoxModel<>();
		this.fichas = new JComboBox<>(this.modeloFichas);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.getHSBColor(170, 30, 59));
		this.setContentPane(mainPanel);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
		mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.getHSBColor(170, 30, 59));
		mainPanel.add(buttonsPanel, BorderLayout.CENTER);
			
		//Hay tres paneles. Cada panel contiene un boton, su texto de descripcion y el precio
		
		JPanel panel_invertir = new JPanel();
		panel_invertir.setLayout(new FlowLayout());
		panel_invertir.setBackground(Color.getHSBColor(145, 81, 83));
		
		JPanel panel_jump = new JPanel();
		panel_jump.setLayout(new FlowLayout());
		panel_jump.setBackground(Color.getHSBColor(145, 81, 83));
		
		JPanel panel_comodin = new JPanel();
		panel_comodin.setLayout(new FlowLayout());
		panel_comodin.setBackground(Color.getHSBColor(145, 81, 83));
		
		buttonsPanel.add(panel_invertir);
		buttonsPanel.add(panel_jump);
		buttonsPanel.add(panel_comodin);
		
			//Definicion de los componentes de cada panel
		
			//Invertir sentido
		
			this.invertir = new JButton();
			invertir.setIcon(new ImageIcon("Dibujos/cesta.jpg"));
			invertir.addActionListener(this);
			invertir.setPreferredSize(new Dimension(50, 50));
			panel_invertir.add(this.invertir);
			panel_invertir.setAlignmentX(CENTER_ALIGNMENT);
			panel_invertir.setAlignmentY(CENTER_ALIGNMENT);
			panel_invertir.setBorder(new LineBorder(Color.black));
			
			JTextArea text_invertir = new JTextArea("Invertir sentido: Esta ventaja te permite invertir el orden en el que juegan los jugadores "
					+ "de manera que si eres el jugador 1 y antes el orden era 1 -> 2 -> 3 -> 4 ahora sera 1 -> 4 -> 3 -> 2");
			text_invertir.setLineWrap(true);
			text_invertir.setPreferredSize(new Dimension(600, 50));
			text_invertir.setWrapStyleWord(true);
			text_invertir.setAlignmentY(CENTER_ALIGNMENT);
			text_invertir.setAlignmentX(CENTER_ALIGNMENT);
			text_invertir.setEditable(false);
			text_invertir.setMargin(new Insets(10,10,10,10));
			panel_invertir.add(text_invertir);
			
			JTextArea precio_invertir = new JTextArea("Precio: 5 monedas");
			precio_invertir.setLineWrap(true);
			precio_invertir.setPreferredSize(new Dimension(80, 50));
			precio_invertir.setWrapStyleWord(true);
			precio_invertir.setAlignmentY(CENTER_ALIGNMENT);
			precio_invertir.setAlignmentX(CENTER_ALIGNMENT);
			precio_invertir.setEditable(false);
			precio_invertir.setMargin(new Insets(10,10,10,10));
			panel_invertir.add(precio_invertir);
			
			//Saltar jugador
			this.jump = new JButton();
			jump.setIcon(new ImageIcon("Dibujos/cesta.jpg"));
			jump.addActionListener(this);
			jump.setPreferredSize(new Dimension(50, 50));
			panel_jump.add(this.jump);
			panel_jump.setAlignmentX(CENTER_ALIGNMENT);
			panel_jump.setAlignmentY(CENTER_ALIGNMENT);
			panel_jump.setBorder(new LineBorder(Color.black));
			
			JTextArea text_jump = new JTextArea("Saltar jugador: Esta ventaja te permite saltar el truno del jugador siguiente en el orden de turnos.");
			text_jump.setLineWrap(true);
			text_jump.setPreferredSize(new Dimension(600, 50));
			text_jump.setWrapStyleWord(true);
			text_jump.setAlignmentY(CENTER_ALIGNMENT);
			text_jump.setAlignmentX(CENTER_ALIGNMENT);
			text_jump.setEditable(false);
			text_jump.setMargin(new Insets(10,10,10,10));
			panel_jump.add(text_jump);
			
			JTextArea precio_jump = new JTextArea("Precio: 3 monedas");
			precio_jump.setLineWrap(true);
			precio_jump.setPreferredSize(new Dimension(80, 50));
			precio_jump.setWrapStyleWord(true);
			precio_jump.setAlignmentY(CENTER_ALIGNMENT);
			precio_jump.setAlignmentX(CENTER_ALIGNMENT);
			precio_jump.setEditable(false);
			precio_jump.setMargin(new Insets(10,10,10,10));
			panel_jump.add(precio_jump);
			
			//Comprar comodín
			this.comodin = new JButton();
			comodin.setIcon(new ImageIcon("Dibujos/cesta.jpg"));
			comodin.addActionListener(this);
			comodin.setPreferredSize(new Dimension(50, 50));
			panel_comodin.add(this.comodin);
			panel_comodin.setAlignmentX(CENTER_ALIGNMENT);
			panel_comodin.setAlignmentY(CENTER_ALIGNMENT);
			panel_comodin.setBorder(new LineBorder(Color.black));
			
			JTextArea text_comodin = new JTextArea("Comprar comdin: Esta ventaja te permite cambiar una de tus fichas por un comodin.");
			text_comodin.setLineWrap(true);
			text_comodin.setPreferredSize(new Dimension(600, 50));
			text_comodin.setWrapStyleWord(true);
			text_comodin.setAlignmentY(CENTER_ALIGNMENT);
			text_comodin.setAlignmentX(CENTER_ALIGNMENT);
			text_comodin.setEditable(false);
			text_comodin.setMargin(new Insets(10,10,10,10));
			panel_comodin.add(text_comodin);
			
			JTextArea precio_comodin = new JTextArea("Precio: 5 monedas");
			precio_comodin.setLineWrap(true);
			precio_comodin.setPreferredSize(new Dimension(80, 50));
			precio_comodin.setWrapStyleWord(true);
			precio_comodin.setAlignmentY(CENTER_ALIGNMENT);
			precio_comodin.setAlignmentX(CENTER_ALIGNMENT);
			precio_comodin.setEditable(false);
			precio_comodin.setMargin(new Insets(10,10,10,10));
			panel_comodin.add(precio_comodin);
			
		//Descripcion + precio para cada botón
		
		this.setPreferredSize(new Dimension(500, 200));
		
	}

	private void dialogoAceptarJump() {
		
		JDialog confirmacion = new JDialog();
		confirmacion.setSize(new Dimension(350, 100));
		confirmacion.setTitle("Confirmacion");
		confirmacion.setLocationRelativeTo((MainWindow) SwingUtilities.getWindowAncestor(this.getParent()));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		confirmacion.setContentPane(mainPanel);
		
		JTextArea texto = new JTextArea("¿Estas seguro de que quieres comprar esta ventaja?");
		mainPanel.add(texto);
		texto.setBackground(Color.getHSBColor(145, 81, 83));
		texto.setAlignmentX(CENTER_ALIGNMENT);
		texto.setAlignmentY(CENTER_ALIGNMENT);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.setBackground(Color.getHSBColor(145, 81, 83));
		mainPanel.add(buttonsPanel);
		
		JButton cancelar = new JButton("Cancel");
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				opcion = true;
				confirmacion.setVisible(false);
				ComprarVentajasDialog.this.setVisible(true);
			}
		});
		buttonsPanel.add(cancelar);
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					res.runCommand(new ComandoSaltarJugador());
				} catch (FileNotFoundException e1) {
					confirmacion.setVisible(false);
					JOptionPane.showMessageDialog(null,e1.getMessage());
				} catch (CommandExecuteException e1) {
					confirmacion.setVisible(false);
					JOptionPane.showMessageDialog(null,e1.getMessage());	
				}
				confirmacion.setVisible(false);
			}
		});
		buttonsPanel.add(aceptar);
		
		ComprarVentajasDialog.this.setVisible(false);
		confirmacion.setVisible(true);
	}
	
	private void dialogoAceptarInvertir() {
		
		
		JDialog confirmacion = new JDialog();
		confirmacion.setSize(new Dimension(350, 100));
		confirmacion.setTitle("Confirmacion");
		confirmacion.setLocationRelativeTo((MainWindow) SwingUtilities.getWindowAncestor(this.getParent()));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		confirmacion.setContentPane(mainPanel);
		
		JTextArea texto = new JTextArea("¿Estas seguro de que quieres comprar esta ventaja?");
		mainPanel.add(texto);
		texto.setBackground(Color.getHSBColor(145, 81, 83));
		texto.setAlignmentX(CENTER_ALIGNMENT);
		texto.setAlignmentY(CENTER_ALIGNMENT);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.setBackground(Color.getHSBColor(145, 81, 83));
		mainPanel.add(buttonsPanel);
		
		JButton cancelar = new JButton("Cancel");
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				opcion = true;
				
				confirmacion.setVisible(false);
				ComprarVentajasDialog.this.setVisible(true);
			}
		});
		buttonsPanel.add(cancelar);
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					res.runCommand(new ComandoInvertirSentido());
				} catch (FileNotFoundException e1) {
					confirmacion.setVisible(false);
					JOptionPane.showMessageDialog(null,e1.getMessage());
				} catch (CommandExecuteException e1) {
					confirmacion.setVisible(false);
					JOptionPane.showMessageDialog(null,e1.getMessage());	
				}
				confirmacion.setVisible(false);
			}
		});
		buttonsPanel.add(aceptar);
		
		ComprarVentajasDialog.this.setVisible(false);
		confirmacion.show();
	}


	private void dialogoAceptarComodin() {
		
		JDialog confirmacion = new JDialog();
		confirmacion.setSize(new Dimension(500, 120));
		confirmacion.setTitle("Confirmacion");
		confirmacion.setLocationRelativeTo((MainWindow) SwingUtilities.getWindowAncestor(this.getParent()));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		confirmacion.setContentPane(mainPanel);
		
		JTextArea texto = new JTextArea("Si quieres comprar esta ventaja selecciona la ficha a descartar y pulsa el boton aceptar");
		mainPanel.add(texto);
		texto.setAlignmentX(CENTER_ALIGNMENT);
		texto.setAlignmentY(CENTER_ALIGNMENT);
		texto.setBackground(Color.getHSBColor(145, 81, 83));
		
		//Anado la caja combo fichas
		this.fichas.setPreferredSize(new Dimension(100,25));
		mainPanel.add(this.fichas);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.setBackground(Color.getHSBColor(145, 81, 83));
		mainPanel.add(buttonsPanel);
		
		JButton cancelar = new JButton("Cancel");
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				opcion = true;
				confirmacion.setVisible(false);
				ComprarVentajasDialog.this.setVisible(true);
			}
		});
		buttonsPanel.add(cancelar);
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ficha f = (Ficha) fichas.getSelectedItem();
				try {
					res.runCommand(new ComandoComprarComodin(f.getLetra()));
				} catch (FileNotFoundException e1) {
					confirmacion.setVisible(false);
					JOptionPane.showMessageDialog(null,e1.getMessage());
				} catch (CommandExecuteException e1) {
					confirmacion.setVisible(false);
					JOptionPane.showMessageDialog(null,e1.getMessage());	
				}
				confirmacion.setVisible(false);
			}
		});
		buttonsPanel.add(aceptar);
		
		ComprarVentajasDialog.this.setVisible(false);
		confirmacion.show();
	}

	private void comprarComodin() {
		System.out.println("comodin");
		this.dialogoAceptarComodin();
	}

	private void saltarJugador() {
		this.dialogoAceptarJump();
	}

	private void invertirSentido() {
		this.dialogoAceptarInvertir();
	}
	
	public void open(List<Ficha> lista) {
		for (Ficha f : lista) {
			this.fichas.addItem(f);
		}
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.invertir) {
			this.invertirSentido();
		}
		else if (e.getSource() == this.jump) {
			this.saltarJugador();
		}
		else if (e.getSource() == this.comodin) {
			this.comprarComodin();
		}
	}

	@Override
	public void registerOn(Registrador c) {
		c.addGameObserver(this);
	}

	@Override
	public void actualizaCasilla(Coordenadas coord, Casilla c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fichaRobada(Ficha f, Integrante j) {
		this.fichas.addItem(f);
	}

	@Override
	public void fichaUsada(Ficha f, Integrante player) {
		this.fichas.removeItem(f);
	}

	@Override
	public void borrarFichaMano(Ficha f, Integrante j,boolean bienColocada) {
		this.fichas.removeItem(f);
	}

	@Override
	public void mostrarPuntos(int puntos, String nick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarMonedas(int monedas, String nick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(String nick, int puntos, int monedas, List<Ficha> mano) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(List<List<Casilla>> grid) {
		// TODO Auto-generated method stub
		
	}

}
