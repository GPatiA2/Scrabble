package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import modelo.Ficha;

public class PalabraPosible {

	private String palabra;
	private int puntos;
	private Coordenadas coor_inicial;
	private Coordenadas coor_final;
	private List<Ficha> fichas;
	
	public PalabraPosible() {
		palabra = "";
		puntos = 0;
		coor_inicial = new Coordenadas();
		coor_final = new Coordenadas();
		fichas = new ArrayList<Ficha>();
	}
	
	public PalabraPosible(String p, int punt, Coordenadas ini, Coordenadas fin) {
		palabra = p;
		puntos = punt;
		coor_inicial = ini;
		coor_final = fin;
	}
	
	public void addFicha(Ficha f) {
		fichas.add(f);
	}
	
	public void removeFicha(Ficha f) {
		fichas.remove(f);
	}
	
	//--------------------METODOS AUXILIARES, GETTERS, SETTERS...--------------------

	public int getPuntos() {
		return puntos;
	}
	
	public void setPuntos(int p) {
		this.puntos = p;
	}
	
	public Coordenadas getCoorIni() {
		return this.coor_inicial;
	}
	
	public Coordenadas getCoorFin() {
		return this.coor_final;
	}
	
	public void setCoorFin(Coordenadas c) {
		this.coor_final = c;
	}
	
	public String getPalabra() {
		return palabra;
	}
	
	public void setPalabra(String p) {
		this.palabra = p;
	}
	
	public List<Ficha> getFichas(){
		return Collections.unmodifiableList(fichas);
	}
	
	public Ficha getPrimera() {
		Ficha f = fichas.get(0);
		fichas.remove(0);
		return f;
	}
}
