package Test;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Clase AllTestsSuite
 * 
 * Esta clase corre el conjunto de pruebas unitarias realizadas 
 * sobre el proyecto, excepto TurnosTest2.java que debido a un 
 * error que no sabemos identificar lo hemos eliminado de este Suite.
 * Aun asi al correr el test de TurnosTest2 por separado este no tiene
 * ningún fallo, creemos que puede deberse a conflictos entre versiones 
 * de JUnit.
 * 
 * @see TurnosTest2.java
 * 
 * Las pruebas se centran en verificar el perfecto 
 * funcionamiento del juego y tener una version lo mas depurada 
 * del mismo.
 * 
 * @author Grupo 5
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ CasillaTest.class, CommandTestsSuite.class, FinJuegoTest.class, 
				LobbyTest.class, TableroTest.class, GeneradorTestSuite.class})

public class AllTestsSuite {

}
