package utils;

import java.util.Comparator;

public class OrdenarCoordenadasInversa implements Comparator<Coordenadas>{

	@Override
	public int compare(Coordenadas o1, Coordenadas o2) {
		if (o1.getFila() == o2.getFila() && o1.getColumna() == o2.getColumna()) {
			return 0;
		}
		
		else if (o1.getFila() == o2.getFila()) {
			if (o1.getColumna() > o2.getColumna()) {
				return -1;
			}
			else {
				return +1;
			}
		}
		
		else if (o1.getColumna() == o2.getColumna()) {
			if (o1.getFila() > o2.getFila()) {
				return -1;
			}
			else {
				return +1;
			}
		}
		
		return 0;

	}
}
