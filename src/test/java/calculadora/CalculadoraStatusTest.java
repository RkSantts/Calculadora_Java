package calculadora;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

    /**
     * Testes de estado e fluxo da calculadora:
     * digitação, encadeamento de operações, comportamento do display.
     */
    public class CalculadoraStatusTest {

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

        // ── Estado Inicial ─────────────────────────────────────────────────

        @Test
        public void testDisplayInicialEhZero() {
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("0"));
        }

        @Test
        public void testOperadorInicialEhVazio() {
            Assert.assertThat(calc.getOperator(), CoreMatchers.is(""));
        }

        @Test
        public void testNovaEntradaAtivaNoInicio() {
            Assert.assertThat(calc.isNewEntry(), CoreMatchers.is(true));
        }

        // ── Digitação ──────────────────────────────────────────────────────

        @Test
        public void testDigitarUmNumero() {
            calc.inputDigit("7");
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("7"));
        }

        @Test
        public void testDigitarVariosNumerosFormaString() {
            calc.inputDigit("1");
            calc.inputDigit("2");
            calc.inputDigit("3");
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("123"));
        }

        @Test
        public void testZeroNaoSeDuplica() {
            calc.inputDigit("0");
            calc.inputDigit("0");
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("0"));
        }

        @Test
        public void testNaoPermitePontoDuplicado() {
            calc.inputDigit("3");
            calc.inputDigit(".");
            calc.inputDigit(".");
            calc.inputDigit("5");
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("3.5"));
        }

        // ── Fluxo Encadeado ────────────────────────────────────────────────

        @Test
        public void testOperacaoEncadeada() {
            calc.inputDigit("5");
            calc.inputOperator("+");
            calc.inputDigit("3");
            calc.inputOperator("×");
            calc.inputDigit("2");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("16"));
        }

        @Test
        public void testCalculoSemOperadorNaoAlteraDisplay() {
            calc.inputDigit("5");
            calc.calculate();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("5"));
        }

        @Test
        public void testDigitarAposResultadoReiniciaEntrada() {
            calc.inputDigit("3");
            calc.inputOperator("+");
            calc.inputDigit("3");
            calc.calculate();
            calc.inputDigit("9");
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("9"));
        }

        @Test
        public void testOperadorAtualizaQuandoTrocadoAntesDeDigitar() {
            calc.inputDigit("5");
            calc.inputOperator("+");
            calc.inputOperator("-");
            Assert.assertThat(calc.getOperator(), CoreMatchers.is("-"));
        }
    }
