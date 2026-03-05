package calculadora;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

    public class CalculadoraAdicaoTeste {

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
        public void testSomaDoisPositivos() {
            double resultado = calc.add(8, 5);
            Assert.assertThat(resultado, CoreMatchers.is(13.0));
        }

        @Test
        public void testSomaDoisNegativos() {
            double resultado = calc.add(-7, -3);
            Assert.assertThat(resultado, CoreMatchers.is(-10.0));
        }

        @Test
        public void testSomaPositivoComNegativo() {
            double resultado = calc.add(-4, 10);
            Assert.assertThat(resultado, CoreMatchers.is(6.0));
        }

        @Test
        public void testSomaComZeroNaoAlteraValor() {
            double resultado = calc.add(42, 0);
            Assert.assertThat(resultado, CoreMatchers.is(42.0));
        }

        @Test
        public void testSomaDecimais() {
            double resultado = calc.add(1.5, 2.5);
            Assert.assertThat(resultado, CoreMatchers.is(4.0));
        }

        @Test
        public void testSomaFluxoCompleto() {
            calc.inputDigit("8");
            calc.inputOperator("+");
            calc.inputDigit("2");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("10"));
        }

        @Test
        public void testSomaNegativoViaToggleSign() {
            calc.inputDigit("3");
            calc.toggleSign();
            calc.inputOperator("+");
            calc.inputDigit("1");
            calc.inputDigit("0");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("7"));
        }

        @Test
        public void testSomaResultadoEhNegativo() {
            calc.inputDigit("2");
            calc.inputOperator("+");
            calc.inputDigit("8");
            calc.toggleSign();
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("-6"));
        }

        @Test
        public void testSomaOperadorRegistrado() {
            calc.inputDigit("5");
            calc.inputOperator("+");
            Assert.assertThat(calc.getOperator(), CoreMatchers.is("+"));
        }

        @Test
        public void testSomaLimpaOperadorAposCalcular() {
            calc.inputDigit("4");
            calc.inputOperator("+");
            calc.inputDigit("6");
            calc.calculate();
            Assert.assertThat(calc.getOperator(), CoreMatchers.is(""));
        }
        @Test
        public void testMultiplasSomas() {
            calc.inputDigit("8");
            calc.inputOperator("+");
            calc.inputDigit("2");
            calc.inputOperator("+");
            calc.inputDigit("10");
            calc.inputOperator("+");
            calc.inputDigit("15");
            calc.inputOperator("+");
            calc.inputDigit("15");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("50"));
        }

        @Test
        public void testSomaComNumeroNegativo() {
            calc.inputDigit("5");
            calc.inputOperator("+");
            calc.inputDigit("-5");
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("-5"));
        }
    }

