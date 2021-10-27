package vista;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controlador.Controller;
import modelo.Ficha;
import modelo.Integrante;
import modelo.JugadorObserver;


public class PanelMano extends JPanel implements JugadorObserver {

	/*ATRIBUTOS */
	
	private static final long serialVersionUID = 1L;
	
	private Controller c;
	private HashMap<Ficha,FichaView> mapaFichas;
	private String jugador;
	
	/*CONSTRUCTO*/
	
	public PanelMano(Controller c, String j) {
		this.c = c;
		mapaFichas = new HashMap<Ficha,FichaView>();
		jugador = j;
		registerOn(c);
		initGUI();
	}
	
	private void initGUI() {
		
		this.setLayout(new FlowLayout());
		this.setBackground(Color.getHSBColor(MainWindow.colorBordeTablero[0], MainWindow.colorBordeTablero[1], MainWindow.colorBordeTablero[2]));
	
	
		Border b = BorderFactory.createLineBorder(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]), 2);
		this.setBorder(BorderFactory.createTitledBorder(b,"Mano"));
		((javax.swing.border.TitledBorder)this.getBorder()).setTitleColor(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));
		
	
	}
	
	/*METODOS ModelObserver*/

	@Override
	public void fichaRobada(Ficha f, Integrante j) {
		
		FichaView ficha = new FichaView(c,f);
		this.add(ficha);
		this.mapaFichas.put(f, ficha);
		updateUI();
	}

	
	@Override
	public void fichaUsada(Ficha f, Integrante j) {
		
		//Elimino la FichaView de la vista
		this.remove(this.mapaFichas.get(f));
		//Elimino el par FichaView-Ficha del mapa
		mapaFichas.remove(f);
		
	}
	
	

	@Override
	public void borrarFichaMano(Ficha f, Integrante j,boolean bienColocada) {
		FichaView v= this.mapaFichas.get(f);
		if(v!=null && bienColocada) {
			this.remove(v);
			this.mapaFichas.remove(f);
		}
		else if (v!=null && !bienColocada) {
			v.setImagenLetra();
		}
		
		updateUI();
	}

	@Override
	public void registerOn(Controller c) {
		c.addJugadorObserver(this, jugador);
	}


	public List<Ficha> getMano() {
		List<Ficha> l  = new ArrayList<>();
		l.addAll(this.mapaFichas.keySet());
		return l;
	}

	@Override
	public void mostrarPuntos(int puntos, String nick) { }

	@Override
	public void mostrarMonedas(int monedas, String nick) {	}

	@Override
	public void onRegister(String nick, int puntos, int monedas,List<Ficha> mano) {
		this.clear();
		for(Ficha f: mano) {
			this.fichaRobada(f,null);
		}
	}

	public void clear() {
		// TODO Auto-generated method stub
		for(Entry<Ficha, FichaView> p: mapaFichas.entrySet()) {
			this.remove(p.getValue());
			updateUI();
		}
		mapaFichas.clear();
		updateUI();
	}
}
