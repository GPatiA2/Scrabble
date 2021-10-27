package utils;
import org.json.JSONObject;

import modelo.IMemento;
import modelo.Memento;
import modelo.Originator;

public class ScoredWord implements Originator {
	private String palabra;
	private int puntos;
	private Coordenadas coor_inicial;
	private Coordenadas coor_final;
	
	public ScoredWord() {
		palabra = "";
		puntos = 0;
		coor_inicial = new Coordenadas();
		coor_final = new Coordenadas();
	}
	
	public ScoredWord(String p, int punt, Coordenadas ini, Coordenadas fin) {
		palabra = p;
		puntos = punt;
		coor_inicial = ini;
		coor_final = fin;
	}
	
	public int getPuntos() {
		return puntos;
	}
	
	public Coordenadas getCoorIni() {
		return this.coor_inicial;
	}
	
	public Coordenadas getCoorFin() {
		return this.coor_final;
	}
	
	public String getPalabra() {
		return palabra;
	}
	public boolean thisContenidaEnOther(ScoredWord s) {
		if(coor_inicial.mayorOIgual(s.coor_inicial)&& coor_final.menorOIgual(s.coor_final)){
			return true;
		}
		else {
			return false;
		}
	}
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		
		ScoredWord o1 = (ScoredWord) o;
		if (palabra == o1.palabra && puntos == o1.puntos &&
				coor_inicial == o1.coor_final && coor_final == o1.coor_final) {
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public void setMemento(Memento m) {
		palabra = m.getState().getString("palabra");
		puntos = m.getState().getInt("puntos");
		
		Memento mementoCoordIni = new Memento();
		mementoCoordIni.setState(m.getState().getJSONObject("coord inicial"));
		coor_inicial.setMemento(mementoCoordIni);
		
		Memento mementoCoordFinal = new Memento();
		mementoCoordFinal.setState(m.getState().getJSONObject("coord final"));
		coor_final.setMemento(mementoCoordIni);
		
	}

	@Override
	public Memento createMemento() {
		Memento memento = new Memento();
		JSONObject jsonScoredWord = new JSONObject();
		
		jsonScoredWord.put("palabra", this.palabra);
		jsonScoredWord.put("puntos", this.puntos);
		jsonScoredWord.put("coord inicial", this.coor_inicial.createMemento().getState());
		jsonScoredWord.put("coord final", this.coor_final.createMemento().getState());
		
		memento.setState(jsonScoredWord);
		
		return memento;
	}
}
