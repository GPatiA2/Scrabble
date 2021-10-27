package Test.GeneradoresTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
	private InputStream in;
	
	/**
     * Antes de ejecutar cada prueba se genera un mazo a partir del fichero que contiene el mazo
     * @see src/ArchivoCarga/mazo.json
     * @see Modelo.GeneradorMazo 
     * @throws IOException si no se puede abrir el fichero o hay problemas con el JSONObject
     */
	@Before
	public void init() throws IOException {
				
		g = new GeneradorMazo();

	}
	
	/**
     * Comprueba que el mazo contiene fichas
	 * @throws IOException en caso de no poder leer el JSONObject del archivo de entrada
     * @see Modelo 
     */
	@Test
	public void testCarga() throws IOException {
		g.generate();
		assertTrue("El mazo no contiene fichas", g.getF().size()>0);
	}
	
	/**
     * Comprueba que el mazo generado se mezcla
     * @see Modelo 
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

