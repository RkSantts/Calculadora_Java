package calculadora;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

    /**
     * Testes para as operações utilitárias:
     * porcentagem, inversão de sinal, backspace e clear.
     */
    public class CalculadoraUtilidadesTest {

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

        // ── Porcentagem ────────────────────────────────────────────────────

        @Test
        public void testPorcentagem200Resulta2() {
            double resultado = calc.percentage(200);
            Assert.assertThat(resultado, CoreMatchers.is(2.0));
        }

        @Test
        public void testPorcentagem100Resulta1() {
            double resultado = calc.percentage(100);
            Assert.assertThat(resultado, CoreMatchers.is(1.0));
        }

        @Test
        public void testPorcentagemDeZeroEhZero() {
            double resultado = calc.percentage(0);
            Assert.assertThat(resultado, CoreMatchers.is(0.0));
        }

        @Test
        public void testPorcentagemNoDisplay50Vira0virgula5() {
            calc.inputDigit("5");
            calc.inputDigit("0");
            calc.applyPercentage();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("0,5"));
        }

        @Test
        public void testPorcentagemNoDisplay100Vira1() {
            calc.inputDigit("1");
            calc.inputDigit("0");
            calc.inputDigit("0");
            calc.applyPercentage();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("1"));
        }

        // ── Inversão de Sinal ──────────────────────────────────────────────

        @Test
        public void testNegacaoPositivoViraNegativo() {
            double resultado = calc.negate(9);
            Assert.assertThat(resultado, CoreMatchers.is(-9.0));
        }

        @Test
        public void testNegacaoNegativoViraPositivo() {
            double resultado = calc.negate(-15);
            Assert.assertThat(resultado, CoreMatchers.is(15.0));
        }

        @Test
        public void testNegacaoDeZeroEhZero() {
            double resultado = calc.negate(0);
            Assert.assertThat(resultado, CoreMatchers.is(-0.0));
        }

        @Test
        public void testToggleSignNoDisplayPositivoViraNegativo() {
            calc.inputDigit("7");
            calc.toggleSign();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("-7"));
        }

        @Test
        public void testToggleSignDuasVezesRetornaOriginal() {
            calc.inputDigit("7");
            calc.toggleSign();
            calc.toggleSign();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("7"));
        }

        // ── Backspace ──────────────────────────────────────────────────────

        @Test
        public void testBackspaceRemoveUltimoDigito() {
            calc.inputDigit("1");
            calc.inputDigit("2");
            calc.inputDigit("3");
            calc.backspace();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("12"));
        }

        @Test
        public void testBackspaceEmDigitoUnicoRetornaZero() {
            calc.inputDigit("5");
            calc.backspace();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("0"));
        }

        @Test
        public void testBackspaceEmZeroPermaneceZero() {
            calc.backspace();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("0"));
        }

        @Test
        public void testBackspaceRemovePontoDecimal() {
            calc.inputDigit("3");
            calc.inputDigit(".");
            calc.backspace();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("3"));
        }

        // ── Clear ──────────────────────────────────────────────────────────

        @Test
        public void testClearResetaDisplayParaZero() {
            calc.inputDigit("9");
            calc.inputDigit("9");
            calc.clear();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("0"));
        }

        @Test
        public void testClearResetaOperador() {
            calc.inputDigit("5");
            calc.inputOperator("+");
            calc.clear();
            Assert.assertThat(calc.getOperator(), CoreMatchers.is(""));
        }

        @Test
        public void testClearAposResultadoResetaTudo() {
            calc.inputDigit("4");
            calc.inputOperator("+");
            calc.inputDigit("4");
            calc.calculate();
            calc.clear();
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("0"));
            Assert.assertThat(calc.getOperator(), CoreMatchers.is(""));
        }
    }
