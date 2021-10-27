package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

import Cliente.TraductorCliente;
import Command.ComandoQuitarFicha;
import Command.Command;
import Excepciones.CommandExecuteException;
import controlador.Registrador;
import modelo.Casilla;
import modelo.Ficha;
import utils.Coordenadas;

public class CeldaTablero extends JButton {

	private boolean empty;
	private boolean disponible;
	private Coordenadas coord;
	private Registrador res;
	
	public CeldaTablero(Registrador res, Casilla c, Coordenadas coord) {
		super();
		this.res = res;
		empty = c.empty();
		disponible = c.esDisponible();
		this.coord = coord;
		String caption = "";
		float[] bC = Color.RGBtoHSB(52, 169, 63,null);
		
		if(coord.getColumna() == coord.getFila() && coord.getColumna() == 7) {
			bC = Color.RGBtoHSB(187, 36, 225, null);
		}
		else {			
			switch(c.getMultiplicador()) {
			case 1:
				if(c.empty())caption = "";
				bC = Color.RGBtoHSB(52, 169, 63, null);
				break;
			case 2:
				if(c.empty())caption = "x2L";
				bC = Color.RGBtoHSB(93, 181, 237, null);
				break;
			case 3:
				if(c.empty())caption = "x3L";
				bC = Color.RGBtoHSB(72, 116, 206, null);
				break;
			case 4:
				if(c.empty())caption = "x2P";
				bC = Color.RGBtoHSB(254, 255, 137, null);
				break;
			case 5:
				if(c.empty())caption = "x3P";
				bC = Color.RGBtoHSB(226, 45, 45, null);
				break;
			}
		}
		this.setText(caption);
		this.setBackground(Color.getHSBColor(bC[0], bC[1], bC[2]));
		Border b;
		if(disponible) {
			bC = Color.RGBtoHSB(22, 85, 28, null);
			b = BorderFactory.createLineBorder(Color.getHSBColor(MainWindow.colorBordeTablero[0], MainWindow.colorBordeTablero[1], MainWindow.colorBordeTablero[2]), 2);
		}
		else {
			bC = Color.RGBtoHSB(22, 85, 28, null);
			b = BorderFactory.createLineBorder(Color.getHSBColor(MainWindow.colorBordeTablero[0], MainWindow.colorBordeTablero[1], MainWindow.colorBordeTablero[2]), 2);
		}
		if (!c.empty()) {
			this.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					Command c = new ComandoQuitarFicha(coord.getFila()+1, coord.getColumna()+1);
					try {
						res.runCommand(c);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (CommandExecuteException e1) {
						e1.printStackTrace();
					}
					
				}
				
			});
			this.setIcon(new ImageIcon(this.generarImagen(c.getFicha().getLetra(),c.getFicha().getPuntos())));
			
		}
		this.setBorder(b);
		this.setVisible(true);
		//updateUI()
	}

	public Image generarImagen(char letra, int puntos){
		//Paso a un string la informacion
		String info = String.valueOf(letra);		
		info += " " + Integer.toString(puntos);
		//Leo el fondo y loy metiendo en un BufferedImage
		try {
			final BufferedImage image = ImageIO.read(new File("Dibujos/letra.png"));
			System.out.println(info);
			
			//'pinto' la imagen
			Graphics g = image.getGraphics();
			g.setFont(new Font("Arial",Font.BOLD,50));
			g.setColor(Color.black);
			g.drawString(info, 13, 75);
			g.dispose();
			
		
		    Image iScaled = image.getScaledInstance(40,40, Image.SCALE_SMOOTH);
		    
			return iScaled;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
}
