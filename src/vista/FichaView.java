package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


import Command.ComandoColocarFicha;
import Command.Command;
import Excepciones.CommandExecuteException;
import controlador.Controller;
import modelo.Ficha;
import utils.Coordenadas;

public class FichaView extends JLabel implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	
	private Image img;
	private Ficha ficha;
	private Controller c;

	public FichaView(Controller c,Ficha f) {
		/*Imagen de fondo*/
		this.setMinimumSize(new Dimension(50,50));
		this.setPreferredSize(new Dimension(50,50));
		this.setMaximumSize(new Dimension(50,50));
		
		ficha = f;
		try {
			img = this.generarImagen(f.getLetra(), f.getPuntos());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setIcon(new ImageIcon(img));
		addMouseListener(this); 
		this.c = c;
	}
	
	public Image generarImagen(char letra, int puntos) throws IOException{
		//Paso a un string la informacion
		String info = String.valueOf(letra);		
		info += " " + Integer.toString(puntos);
		//Leo el fondo y loy metiendo en un BufferedImage
		final BufferedImage image = ImageIO.read(new File("Dibujos/letra.png"));
		System.out.println(info);
		//'pinto' la imagen
		Graphics g = image.getGraphics();
		g.setFont(new Font("Arial",Font.BOLD,50));
		g.setColor(Color.black);
		g.drawString(info, 13, 75);
		g.dispose();
		
	    Image iScaled = image;
	    
		return iScaled.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	}	
	
	public char getLetraPulsada() {
		return ficha.getLetra();
	}
	public void setImagenLetra() {
		this.setIcon(new ImageIcon(this.img));
		updateUI();
	}
	/*METODOS MOUSELISTENER*/
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mousePressed(MouseEvent e) {
		/*Cuando presiono cambio la imagen
		 * del cursor a la imagen actual */
		if(GamePanel.getEnable()) {
			Cursor cur = Toolkit.getDefaultToolkit().createCustomCursor(this.img, new Point(0,0),"");
		    
			setCursor(cur);
			this.setIcon(new ImageIcon());
		}
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(GamePanel.getEnable()) {
			setCursor(Cursor.getDefaultCursor());
			
			
	
			Command command;
			String comodin = "0";
			String id = ficha.getId();
			if(ficha.esComodin()) {
				do{
					comodin = JOptionPane.showInputDialog("Que letra desea colocar?");
					
				}while(comodin.isEmpty()||comodin.length()>1|| comodin.equals(" ")|| isNumeric(comodin));
				
				//Pido la letra mientras que sea la entrada vacia, un espacio, varias letras
				//o sea un numero
				
				comodin = comodin.toUpperCase().trim();
				id = "*";
				
			}
			Coordenadas c= MainWindow.getCoordenadas(e.getXOnScreen(),e.getYOnScreen());
			
			if (Coordenadas.checkCommand(c.getFila()+1, c.getColumna()+1)) {
				command = new ComandoColocarFicha(id,c.getFila()+1,c.getColumna()+1,comodin.charAt(0));
				try {
					this.c.runCommand(command);
				} catch (FileNotFoundException | CommandExecuteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
			else this.setImagenLetra();
		}
	}
	public static boolean isNumeric(String str)	{
		
		//Metodo que devuelve si un string es un numero
		  return str.matches("-?\\d+(\\.\\d+)?");  
		}
}
