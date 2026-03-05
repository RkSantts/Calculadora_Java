package calculadora;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

    public class CalculadoraSubtracaoTest {

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
        public void testSubtracaoSimples() {
            double resultado = calc.subtract(15, 6);
            Assert.assertThat(resultado, CoreMatchers.is(9.0));
        }

        @Test
        public void testSubtracaoResultadoNegativo() {
            double resultado = calc.subtract(3, 10);
            Assert.assertThat(resultado, CoreMatchers.is(-7.0));
        }

        @Test
        public void testSubtracaoDeSimesmoEhZero() {
            double resultado = calc.subtract(7, 7);
            Assert.assertThat(resultado, CoreMatchers.is(0.0));
        }

        @Test
        public void testSubtracaoComZeroNaoAlteraValor() {
            double resultado = calc.subtract(99, 0);
            Assert.assertThat(resultado, CoreMatchers.is(99.0));
        }

        @Test
        public void testSubtracaoDoisNegativos() {
            double resultado = calc.subtract(-5, -3);
            Assert.assertThat(resultado, CoreMatchers.is(-2.0));
        }

        @Test
        public void testSubtracaoDecimais() {
            double resultado = calc.subtract(5.5, 2.5);
            Assert.assertThat(resultado, CoreMatchers.is(3.0));
        }

        @Test
        public void testSubtracaoFluxoCompleto() {
            calc.inputDigit("9");
            calc.inputOperator("-");
            calc.inputDigit("4");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("5"));
        }

        @Test
        public void testSubtracaoResultadoZeroNoDisplay() {
            calc.inputDigit("7");
            calc.inputOperator("-");
            calc.inputDigit("7");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("0"));
        }

        @Test
        public void testSubtracaoOperadorRegistrado() {
            calc.inputDigit("5");
            calc.inputOperator("-");
            Assert.assertThat(calc.getOperator(), CoreMatchers.is("-"));
        }

        @Test
        public void testSubtracaoLimpaOperadorAposCalcular() {
            calc.inputDigit("9");
            calc.inputOperator("-");
            calc.inputDigit("3");
            calc.calculate();
            Assert.assertThat(calc.getOperator(), CoreMatchers.is(""));
        }

        @Test
        public void testSubtracaoComNumeroNegativoRetornaPositivo() {
            calc.inputDigit("9");
            calc.inputOperator("-");
            calc.inputDigit("-4");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("13"));
        }
    }

