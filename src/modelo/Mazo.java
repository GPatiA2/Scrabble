package modelo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class Mazo implements Originator 
{
	 private List<Ficha> mazo;
	 
	    //Constructor
	    protected Mazo(){
	        //Crear el mazo
	    	mazo = new LinkedList<Ficha>();   
	    }
	 
	    public Mazo(Collection<Ficha> randomStack) {
	    	//Crea una pila vacia
	    	mazo = new LinkedList<Ficha>();
	    	//Inserta en la pila todo el array mezclado que se pasa por parametro
	    	mazo.addAll(randomStack);
	    }

		public Ficha robar() {
			if(!mazo.isEmpty()) {				
				Random r = new Random();
				return mazo.remove(r.nextInt(mazo.size()));
			}
			else {
				return null;
			}
	    }
	    public void anniadir(Ficha f) {
	    	mazo.add(f);
	    }
	    
	    public void clear() {
	    	mazo.clear();
	    }
	    
	    public int getNumFichas() {
	    	return mazo.size();
	    }

		
		public boolean contains(char c) {
			for (Ficha f : mazo) {
				if (f.getLetra() == c) {
					return true;
				}
			}
			return false;
		}


		public Ficha robarFicha(char c) {
			for (Ficha f : this.mazo) {
				if (f.getLetra() == c) {
					return f;
				}
			}
			return null;
		}
	    
		
		public List<Ficha> getLista() {
			return this.mazo;
		}
		
		@Override
		public void setMemento(Memento m) {
			mazo.clear(); //Limpiar mazo por si hubiera fichas 
			JSONArray jsonArrayFichas = m.getState().getJSONArray("listaFichas");
			
			for(int i = 0; i < jsonArrayFichas.length(); i++) { //Rellenar mazo
				Memento memento = new Memento();
				memento.setState(jsonArrayFichas.getJSONObject(i));
				Ficha f = new Ficha();
				f.setMemento(memento); //Cargar estado de la ficha
				mazo.add(i, f);
			}		
		}
		
		@Override
		public Memento createMemento() {
			Memento memento = new Memento();
			JSONArray jsonArrayFichas = new JSONArray();
			JSONObject jsonMazo = new JSONObject();
			
			for(Ficha f: this.mazo) {
				JSONObject jsonFicha = new JSONObject();
				jsonFicha = f.createMemento().getState();
				jsonArrayFichas.put(jsonFicha);
			}
			
			jsonMazo.put("listaFichas", jsonArrayFichas);
			
			memento.setState(jsonMazo);
			
			return memento;
		}
}