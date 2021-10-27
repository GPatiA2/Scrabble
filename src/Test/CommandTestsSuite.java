package Test;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Test.CommandTests.ComandoCambiarFichaTest;
import Test.CommandTests.ComandoColocarFichaTest;
import Test.CommandTests.ComandoComprarComodinTest;
import Test.CommandTests.ComandoInvertirSentidoTest;
import Test.CommandTests.ComandoPasarTurnoTest;
import Test.CommandTests.ComandoQuitarFichaTest;
import Test.CommandTests.ComandoSaltarJugadorTest;

/**
 * Clase CommandTestsSuite
 * 
 * Esta clase corre el conjunto de pruebas unitarias sobre los comandos.
 * 
 * Las pruebas de cada comando se centran en verificar el perfecto 
 * funcionamiento del mismo, asi como que cumplen con las reglas y
 * responden de manera controlada a excepciones.
 * 
 * @author Grupo 5
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ ComandoColocarFichaTest.class, ComandoQuitarFichaTest.class, ComandoPasarTurnoTest.class,
				ComandoCambiarFichaTest.class, ComandoSaltarJugadorTest.class, ComandoComprarComodinTest.class,
				ComandoInvertirSentidoTest.class})

public class CommandTestsSuite {

}
