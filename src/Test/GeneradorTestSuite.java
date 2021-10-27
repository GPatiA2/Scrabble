package Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Test.GeneradoresTests.GeneradorDiccionarioTest;
import Test.GeneradoresTests.GeneradorMazoTest;

@RunWith(Suite.class)

@SuiteClasses({GeneradorMazoTest.class, GeneradorDiccionarioTest.class})

public class GeneradorTestSuite {

}
