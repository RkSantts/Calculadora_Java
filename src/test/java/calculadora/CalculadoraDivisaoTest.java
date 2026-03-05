package calculadora;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

    public class CalculadoraDivisaoTest {

        private Calculadora calc;

        @Before
        public void setUp() {
            calc = new Calculadora();
        }

        @After
        public void tearDown() {
            calc.clear();
            calc = null;
        }

        @Test
        public void testDivisaoExata() {
            double resultado = calc.divide(20, 4);
            Assert.assertThat(resultado, CoreMatchers.is(5.0));
        }

        @Test
        public void testDivisaoResultDecimal() {
            double resultado = calc.divide(10, 4);
            Assert.assertThat(resultado, CoreMatchers.is(2.5));
        }

        @Test
        public void testDivisaoPorUmRetornaOMesmoValor() {
            double resultado = calc.divide(8, 1);
            Assert.assertThat(resultado, CoreMatchers.is(8.0));
        }

        @Test
        public void testDivisaoDeZeroResultaZero() {
            double resultado = calc.divide(0, 5);
            Assert.assertThat(resultado, CoreMatchers.is(0.0));
        }

        @Test
        public void testDivisaoNegativoPorPositivo() {
            double resultado = calc.divide(-10, 2);
            Assert.assertThat(resultado, CoreMatchers.is(-5.0));
        }

        @Test
        public void testDivisaoDoisNegativos() {
            double resultado = calc.divide(-12, -4);
            Assert.assertThat(resultado, CoreMatchers.is(3.0));
        }

        @Test(expected = ArithmeticException.class)
        public void testDivisaoPorZeroLancaExcecao() {
            calc.divide(7, 0);
        }

        @Test
        public void testDivisaoPorZeroExibeErroNoDisplay() {
            calc.inputDigit("5");
            calc.inputOperator("÷");
            calc.inputDigit("0");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.containsString("Erro"));
        }

        @Test
        public void testDivisaoFluxoCompleto() {
            calc.inputDigit("10");
            calc.inputOperator("÷");
            calc.inputDigit("4");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("2,5"));
        }

        @Test
        public void testDivisaoResultadoContemVirgulaDecimal() {
            calc.inputDigit("1");
            calc.inputOperator("÷");
            calc.inputDigit("3");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.containsString(","));
        }

        @Test
        public void testDivisaoOperadorRegistrado() {
            calc.inputDigit("8");
            calc.inputOperator("÷");
            Assert.assertThat(calc.getOperator(), CoreMatchers.is("÷"));
        }

        @Test
        public void testDivisaoLimpaOperadorAposCalcular() {
            calc.inputDigit("9");
            calc.inputOperator("÷");
            calc.inputDigit("3");
            calc.calculate();
            Assert.assertThat(calc.getOperator(), CoreMatchers.is(""));
        }

        @Test
        public void testDivisaoComDecimal() {
            double resultado = calc.divide(10, 2.5);
            Assert.assertThat(resultado, CoreMatchers.is(4.0));
        }
    }
