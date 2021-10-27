package Test.GeneradoresTests;

import static org.junit.Assert.*;



import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modelo.Ficha;
import modelo.GeneradorMazo;

/**
* Clase GeneradorMazoTest
*
* Contiene pruebas unitarias para la clase GeneradorMazo.
* Esta clase es la encargada de rellenar el mazo y mezclar las fichas.
*
* @author Grupo 5
*/

public class GeneradorMazoTest {

	//Atributos
	
	/**
	 *  Ruta de acceso al archivo donde se encuentra el mazo
	 */
	private GeneradorMazo g;
	
	/**
     * Antes de ejecutar cada prueba se genera un mazo a partir del fichero que contiene el mazo
     * @see src/ArchivoCarga/mazo.json
     * @see src/Modelo/GeneradorMazo.java 
     * @throws IOException si no se puede abrir el fichero o hay problemas con el JSONObject
     */
	@Before
	public void init() throws IOException {
				
		g = new GeneradorMazo();

	}
	
	/**
     * Comprueba que el mazo contiene fichas y que el numero de comodines en el mazo
     * es dos, conforme a las reglas del juego.
     * @see /Documentos/Reglas.docx
     * 
	 * @throws IOException en caso de no poder leer el JSONObject del archivo de entrada
     * @see src/Modelo/GeneradorMazo.java
     * @see src/ArchivoCarga/mazo.json archivo que contiene los distintos tipos de fichas del juego
     */
	@Test
	public void testCarga() throws IOException {
		g.generate();
		List<Ficha> mazo = g.getF();
		int comodines = 0;
		assertTrue("El mazo no contiene fichas", mazo.size()>0);
		for(int i = 0 ; i < mazo.size(); ++i) {
			if(mazo.get(i).igual_letra('*')) {
				comodines++;
			}
		}
		assertTrue(comodines == 2);
	}
	
	/**
     * Comprueba que el mazo generado se mezcla correctamente
     * 
     * @see src/Modelo/GeneradorMazo.java
     * @see src/ArchivoCarga/mazo.json archivo que contiene los distintos tipos de fichas del juego
     */
	@Test
	public void testMezcla() {
		g.generate();
		assertTrue("El mazo no contiene fichas", g.getF().size()>0);
		List<Ficha> expected = g.getF();
		g.mezclar();
		List<Ficha> actual = g.getF();
		assertNotSame(actual, expected);
	}

}

