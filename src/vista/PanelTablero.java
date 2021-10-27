package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controlador.Controller;
import controlador.Registrador;
import modelo.Casilla;
import modelo.Ficha;
import modelo.Integrante;
import modelo.Jugador;
import modelo.ModelObserver;
import modelo.Tablero;
import modelo.TableroObserver;
import utils.Coordenadas;

public class PanelTablero extends JPanel implements TableroObserver {
	
	private Registrador ctrl;
	private HashMap<Coordenadas,JPanel> tab;
	private HashMap<JPanel,Coordenadas> tab2;
	private static final int nFilas = 15;
	private static final int nCols = 15;
	
	public PanelTablero(Registrador c) {
		ctrl = c;
		tab = new HashMap<Coordenadas,JPanel>();
		tab2 = new  HashMap<JPanel,Coordenadas>();
		initGui();
		registerOn(c);
	}
	
	private void initGui() {
		this.setPreferredSize(new Dimension(600,600));
		this.setBackground(Color.getHSBColor(MainWindow.colorBordeTablero[0], MainWindow.colorBordeTablero[1], MainWindow.colorBordeTablero[2]));
		setLayout(new GridLayout(nFilas, nCols));
		for(int i = 0; i < nFilas; i++) {
			for(int j = 0; j < nCols; j++) {
				Coordenadas coord = new Coordenadas(i,j);
				JPanel cell = new JPanel();
				Border b = BorderFactory.createLineBorder(Color.LIGHT_GRAY , 1);
				cell.setBorder(b);
				cell.setLayout(new BorderLayout());
				//Diagonales
				if (i == j || j == nCols - i - 1) {
					//Esquinas Triple Palabra
					if (i == 0 || i == 14) {
						System.out.println("Casilla Roja");
						cell.add(new CeldaTablero(ctrl, new Casilla(Casilla.CASILLA_TRIPLE_PALABRA), coord), BorderLayout.CENTER);
					}
					//Casilla Triple Letra
					else if ( i == 5 || i == 9) {
						System.out.println("Casilla Naranja");
						cell.add(new CeldaTablero(ctrl,  new Casilla(Casilla.CASILLA_TRIPLE_LETRA), coord), BorderLayout.CENTER);
					}
					//Casilla Doble Letra
					else if (i == 6 || i == 8) {
						System.out.println("Casilla Azul");
						cell.add(new CeldaTablero(ctrl, new Casilla(Casilla.CASILLA_DOBLE_LETRA),coord), BorderLayout.CENTER);
					}
					//Si no es la casilla central Doble Palabra
					else if (i != 7) {
						System.out.println("Casilla Morada");
						cell.add(new CeldaTablero(ctrl, new Casilla(Casilla.CASILLA_DOBLE_PALABRA), coord), BorderLayout.CENTER);
					}
					//Casilla central. La ponemos como normal
					else {
						System.out.println("Casilla Normal");
						cell.add(new CeldaTablero(ctrl,  new Casilla(Casilla.CASILLA_NORMAL), coord), BorderLayout.CENTER);
					}
				}
				
				//Triple Palabra restantes
				else if ((i == 7 && j == 0) || (i == 7 && j == 14) || (i == 0 && j == 7) || (i == 14 && j == 7)) {
					cell.add(new CeldaTablero(ctrl,  new Casilla(Casilla.CASILLA_TRIPLE_LETRA), coord), BorderLayout.CENTER);
				}
				
				//Doble Palabra completados en las diagonales
				
				//Triple Letra restantes
				else if ((i == 1 && j == 5) || (i == 5 && j == 1) || (i == 1 && j == 9) || (i == 9 && j == 1) || 
						(i == 5 && j == 13) || (i == 13 && j == 5) || (i == 9 && j == 13) || (i == 13 && j == 9)) {
					cell.add(new CeldaTablero(ctrl,  new Casilla(Casilla.CASILLA_TRIPLE_LETRA), coord), BorderLayout.CENTER);
				}
				
				//Doble Letra restantes y casillas normales
				else {
					
					//Laterales
					if ((i == 0 && ((j == 3)|| j == 11)) || (j == 0 && ((i == 3)|| i == 11)) ||
						(i == 14 && ((j == 3)|| j == 11)) || (j == 14 && ((i == 3)|| i == 11))	) {
						cell.add(new CeldaTablero(ctrl,  new Casilla(Casilla.CASILLA_DOBLE_LETRA), coord), BorderLayout.CENTER);
					}
					
					//Fila y columna central
					else if ((i == 7 && ((j == 3)||(j == 11))) || (j == 7 && ((i == 3)||(i == 11)))) {
						cell.add(new CeldaTablero(ctrl, new Casilla(Casilla.CASILLA_DOBLE_LETRA), coord), BorderLayout.CENTER);
					}
					
					//Resto de casillas Doble Letra
					else if (((i == 6)||(i == 8)) && ((j == 2)||(j == 6)||(j == 8)||(j == 12)) ||
							((j == 6)||(j == 8)) && ((i == 2)||(i == 6)||(i == 8)||(i == 12))) {
						cell.add(new CeldaTablero(ctrl,  new Casilla(Casilla.CASILLA_DOBLE_LETRA), coord), BorderLayout.CENTER);
					}
					
					//Casillas normales
					else {
						cell.add(new CeldaTablero(ctrl,  new Casilla(Casilla.CASILLA_NORMAL), coord), BorderLayout.CENTER);
					}
				}
				
				this.add(cell);
				tab.put(new Coordenadas(i,j), cell);
				tab2.put(cell, new Coordenadas(i,j));
			}
		}
	}
	public Coordenadas Localizacion(Point c) {
		
		Component[] list = this.getComponents();
		boolean miComponente;
		for(Component compo :list) {
			miComponente = compo.getLocationOnScreen().getX()<c.x && compo.getLocationOnScreen().getX()+40 > c.x;
			miComponente = miComponente && compo.getLocationOnScreen().getY()<c.y && compo.getLocationOnScreen().getY()+40 > c.y;
			if(miComponente) return this.tab2.get(compo);
			
		}
		return new Coordenadas(-3,-3);		
	}
	@Override
	public void actualizaCasilla(Coordenadas coord, Casilla c) {
		
		JPanel cell = this.tab.get(coord);
		
		cell.removeAll();
		cell.setLayout(new BorderLayout());
		
		cell.add(new CeldaTablero(ctrl, c, coord), BorderLayout.CENTER);
		updateUI();
		
		cell.repaint();
	}
	
	@Override
	public void registerOn(Registrador c) {
		c.addGameObserver(this);
	}

	@Override
	public void onRegister(List<List<Casilla>> grid) {
		if(!grid.isEmpty()) { //esto solo se da si hemos cargado una partida anterior
			for(int i = 0; i < nFilas; i++) {
				for(int j = 0; j < nCols; j++) {
					if(grid.get(i).get(j).getFicha() != null) {
						Ficha ficha = grid.get(i).get(j).getFicha();
						int puntos = grid.get(i).get(j).getFicha().getPuntos();
						this.actualizaCasilla(new Coordenadas(i,j), new Casilla(ficha,puntos));							
					}
				}
			}			
		}
	}

}
