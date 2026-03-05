package calculadora;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CalculadoraAdicaoTeste.class, CalculadoraDivisaoTest.class, CalculadoraMultiplicacaoTest.class, CalculadoraSubtracaoTest.class, CalculadoraStatusTest.class, CalculadoraUtilidadesTest.class})
public class TodosOsTest {

}
