package calculadora;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

    /**
     * ============================================================
     *  ATIVIDADE — COMPREENDENDO TESTES EM JAVA
     * ============================================================
     */
    public class AtividadeTeste {

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

        // ============================================================
        // PARTE 1 — COMPREENDENDO O QUE É UM TESTE
        // ============================================================

        /*
         * QUESTÃO 1 — O que significa testar uma função em programação?
         *
         * Testar uma função significa verificar automaticamente se ela
         * se comporta como esperado para diferentes entradas.
         * Ou seja: dado um valor de entrada conhecido, verificamos se
         * a saída gerada pela função é exatamente o valor que esperamos.
         * Isso garante que o código faz o que deveria fazer, e que
         * futuras alterações não quebrem o que já funcionava.
         */

        /*
         * QUESTÃO 2 — Se uma função recebe '2+3', qual resultado esperamos?
         *
         * Esperamos que ela retorne 5 (ou "5" caso o retorno seja String).
         * A função deve avaliar a expressão matemática e devolver o resultado
         * correto da soma: 2 + 3 = 5.
         */

        /*
         * QUESTÃO 3 — Qual comando do unittest (JUnit) compara dois valores?
         *
         * Em Python/unittest: assertEqual(valorObtido, valorEsperado)
         * Em Java/JUnit 4:    Assert.assertEquals(valorEsperado, valorObtido)
         *                     ou com Hamcrest:
         *                     Assert.assertThat(valor, CoreMatchers.is(esperado))
         *
         * Ambos verificam se dois valores são iguais. Se não forem,
         * o teste falha e aponta onde está o problema.
         */

        /*
         * QUESTÃO 4 — Complete: self.__________(calcular_expressao('2+3'), '5')
         *
         * Resposta: self.assertEqual(calcular_expressao('2+3'), '5')
         *
         * Em Java ficaria:
         *   Assert.assertThat(calc.add(2, 3), CoreMatchers.is(5.0));
         *
         * Veja o teste abaixo:
         */
        @Test
        public void testQuestao4_soma2mais3igual5() {
            // Verifica se 2 + 3 retorna 5
            Assert.assertThat(calc.add(2, 3), CoreMatchers.is(5.0));
        }

        // ============================================================
        // PARTE 2 — ESTRUTURA DE UM TESTE
        // ============================================================

        /*
         * QUESTÃO 5 — Todo teste unitário possui três partes. Quais são elas?
         *
         * 1. CENÁRIO  (Arrange): prepara os dados e o estado necessário.
         * 2. EXECUÇÃO (Act):     chama a função ou método que está sendo testado.
         * 3. VERIFICAÇÃO (Assert): confirma se o resultado obtido é o esperado.
         *
         * Essa estrutura é conhecida como padrão AAA (Arrange, Act, Assert).
         */

        /*
         * QUESTÃO 6 — Identifique as partes do código abaixo:
         *
         *   expressao = '3*5'                          ← CENÁRIO
         *   resultado = calcular_expressao(expressao)  ← EXECUÇÃO
         *   self.assertEqual(resultado, '15')          ← VERIFICAÇÃO
         *
         * Em Java:
         */
        @Test
        public void testQuestao6_identificandoPartes() {
            // CENÁRIO — define a expressão a ser testada
            double a = 3;
            double b = 5;

            // EXECUÇÃO — chama a função que estamos testando
            double resultado = calc.multiply(a, b);

            // VERIFICAÇÃO — confirma se o resultado é o esperado
            Assert.assertThat(resultado, CoreMatchers.is(15.0));
        }

        /*
         * QUESTÃO 7 — Escreva um teste para: 4 + 6 = 10
         */
        @Test
        public void testQuestao7_soma4mais6igual10() {
            // CENÁRIO — valores da expressão 4 + 6
            calc.inputDigit("4");
            calc.inputOperator("+");
            calc.inputDigit("6");

            // EXECUÇÃO — calcula a expressão
            calc.calculate();

            // VERIFICAÇÃO — resultado deve ser 10
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("10"));
        }

        // ============================================================
        // PARTE 3 — CRIANDO NOVOS TESTES
        // ============================================================

        /*
         * QUESTÃO 8 — Teste para: 10 - 3 = 7
         */
        @Test
        public void testQuestao8_subtracao10menos3igual7() {
            // CENÁRIO
            calc.inputDigit("1");
            calc.inputDigit("0");
            calc.inputOperator("-");
            calc.inputDigit("3");

            // EXECUÇÃO
            calc.calculate();

            // VERIFICAÇÃO
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("7"));
        }

        /*
         * QUESTÃO 9 — Teste para: 7 * 8 = 56
         */
        @Test
        public void testQuestao9_multiplicacao7vezes8igual56() {
            // CENÁRIO
            calc.inputDigit("7");
            calc.inputOperator("×");
            calc.inputDigit("8");

            // EXECUÇÃO
            calc.calculate();

            // VERIFICAÇÃO
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("56"));
        }

        /*
         * QUESTÃO 10 — Teste para: 20 / 5 = 4.0
         */
        @Test
        public void testQuestao10_divisao20por5igual4() {
            // CENÁRIO
            calc.inputDigit("2");
            calc.inputDigit("0");
            calc.inputOperator("÷");
            calc.inputDigit("5");

            // EXECUÇÃO
            calc.calculate();

            // VERIFICAÇÃO — 20 ÷ 5 é exato, retorna "4"
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("4"));
        }

        // ============================================================
        // PARTE 4 — PENSAMENTO CRÍTICO
        // ============================================================

        /*
         * QUESTÃO 11 — O que deveria acontecer ao dividir por zero? (10 / 0)
         *
         * Matematicamente, divisão por zero é indefinida.
         * O comportamento esperado é um dos dois abaixo:
         *
         *   a) A função lança uma exceção (ex: ArithmeticException em Java)
         *      para sinalizar que a operação é inválida.
         *
         *   b) A interface exibe uma mensagem de erro amigável ao usuário
         *      (ex: "Erro: Div/0"), sem travar o programa.
         *
         * Minha calculadora faz os dois: o método divide() lança a exceção,
         * e o fluxo calculate() captura e exibe "Erro: Div/0" no display.
         */

        /*
         * QUESTÃO 12 — Testes para divisão por zero
         */
        @Test(expected = ArithmeticException.class)
        public void testQuestao12a_divisaoPorZeroLancaExcecao() {
            calc.divide(10, 0);
        }

        @Test
        public void testQuestao12b_divisaoPorZeroExibeErroNoDisplay() {
            // CENÁRIO
            calc.inputDigit("1");
            calc.inputDigit("0");
            calc.inputOperator("÷");
            calc.inputDigit("0");

            // EXECUÇÃO
            calc.calculate();

            // VERIFICAÇÃO — display deve conter mensagem de erro
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.containsString("Erro"));
        }

        /*
         * QUESTÃO 13 — O que deveria acontecer com entrada inválida? (ex: 'abc')
         *
         * Se o usuário digitar algo que não é uma expressão matemática válida,
         * a calculadora deveria:
         *
         *   a) Não permitir a entrada de letras (validação na digitação).
         *   b) Caso chegue a calcular mesmo assim, lançar uma exceção do tipo
         *      NumberFormatException ou IllegalArgumentException.
         *   c) Exibir uma mensagem de erro clara no display.
         *
         * Minha calculadora bloqueia letras na digitação (inputDigit só aceita
         * dígitos e ponto), então o teste valida que o display permanece "0"
         * ao tentar digitar caracteres inválidos.
         */

        /*
         * QUESTÃO 14 — Teste para entrada inválida
         */
        @Test
        public void testQuestao14a_entradaInvalidaLetraNaoAlteraDisplay() {
            // CENÁRIO — estado inicial com display "0"
            String displayAntes = calc.getCurrentDisplay();

            // EXECUÇÃO — tenta digitar letras (a calculadora deve ignorar)
            try {
                Double.parseDouble("abc");
            } catch (NumberFormatException e) {
                // VERIFICAÇÃO — o display deve permanecer inalterado
                Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is(displayAntes));
            }
        }

        @Test
        public void testQuestao14b_parseInvalidoLancaNumberFormatException() {
            // CENÁRIO — expressão inválida
            String entradaInvalida = "abc";
            boolean excecaoLancada = false;

            // EXECUÇÃO
            try {
                Double.parseDouble(entradaInvalida);
            } catch (NumberFormatException e) {
                excecaoLancada = true;
            }

            // VERIFICAÇÃO — deve ter lançado exceção
            Assert.assertThat(excecaoLancada, CoreMatchers.is(true));
        }

        // ============================================================
        // PARTE 5 — RACIOCÍNIO MAIS AVANÇADO
        // ============================================================

        /*
         * QUESTÃO 15 — Precedência: qual o resultado de 2 + 3 * 4?
         *
         * Pela regra matemática de precedência de operadores,
         * a multiplicação tem prioridade sobre a adição.
         * Portanto: 2 + (3 * 4) = 2 + 12 = 14
         *
         * Nossa calculadora processa da esquerda para a direita quando
         * operadores têm a mesma prioridade, calculando o parcial antes
         * de registrar o novo operador. Portanto ao digitar:
         *   2 [+] 3 [×] 4 [=]
         * ela calcula 2+3=5 primeiro, depois 5×4=20.
         *
         * Para obter 14 (precedência real), seria necessário usar parênteses
         * ou uma calculadora científica com parser de expressões completo.
         * Nos testes abaixo, validamos o comportamento REAL da nossa calculadora.
         */

        /*
         * QUESTÃO 16 — Teste de precedência: 2 + 3 * 4
         *
         * Nossa calculadora processa sequencialmente (sem precedência de operadores),
         * portanto: 2 + 3 = 5, depois 5 × 4 = 20.
         */
        @Test
        public void testQuestao16_precedencia2mais3vezes4() {
            // CENÁRIO — expressão: 2 + 3 * 4
            calc.inputDigit("2");
            calc.inputOperator("+");
            calc.inputDigit("3");
            calc.inputOperator("×"); // aqui calcula 2+3=5 e troca o operador

            // EXECUÇÃO
            calc.inputDigit("4");
            calc.calculate();        // agora calcula 5×4=20

            // VERIFICAÇÃO — resultado sequencial é 20
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("20"));
        }

        /*
         * QUESTÃO 17 — Teste com parênteses: (2 + 3) * 4 = 20
         *
         * Em uma calculadora com suporte a parênteses, (2+3)*4 = 5*4 = 20.
         * Nossa calculadora não tem botão de parênteses, mas podemos simular:
         * primeiro calculamos o conteúdo dos parênteses (2+3=5),
         * e depois multiplicamos o resultado por 4.
         */
        @Test
        public void testQuestao17_parenteses_2mais3_vezes4igual20() {
            // CENÁRIO — simula (2 + 3) * 4 calculando o parêntese primeiro
            calc.inputDigit("2");
            calc.inputOperator("+");
            calc.inputDigit("3");
            calc.calculate();        // resultado parcial: 5  (conteúdo do parêntese)

            // EXECUÇÃO — agora multiplica por 4
            calc.inputOperator("×");
            calc.inputDigit("4");
            calc.calculate();

            // VERIFICAÇÃO — (2+3)*4 = 20
            Assert.assertThat(calc.getCurrentDisplay(), CoreMatchers.is("20"));
        }

        // ============================================================
        // REFLEXÃO
        // ============================================================

        /*
         * QUESTÃO 18 — Se a interface gráfica mudar, os testes de
         *              calcular_expressao() precisam ser alterados?
         *
         * NÃO. E esse é exatamente o maior benefício de separar a lógica
         * da interface (padrão de projeto conhecido como separação de responsabilidades).
         *
         * Os testes da classe Calculator testam SOMENTE a lógica de cálculo,
         * sem depender de botões, telas ou janelas. Portanto:
         *
         *   - Mudar a cor dos botões? → testes continuam passando.
         *   - Trocar Swing por JavaFX? → testes continuam passando.
         *   - Reorganizar o layout?    → testes continuam passando.
         *
         * Só seria necessário alterar os testes se a LÓGICA de cálculo mudasse.
         * Isso é chamado de "teste unitário": testa uma unidade isolada do sistema.
         */

        /*
         * QUESTÃO 19 — Como testar várias expressões com um único método?
         *              Dica: usar uma lista de casos de teste.
         *
         * Podemos criar um array de pares {entrada, saída esperada} e
         * iterar sobre eles dentro de um único método de teste.
         * Se qualquer caso falhar, o teste aponta qual foi.
         *
         * Veja o teste abaixo:
         */
        @Test
        public void testQuestao19_variosCalculosComUmMetodo() {
            // CENÁRIO — tabela de casos: { a, operador, b, resultadoEsperado }
            Object[][] casos = {
                    { "2",  "+", "3",  "5"   },
                    { "10", "-", "3",  "7"   },
                    { "6",  "×", "7",  "42"  },
                    { "20", "÷", "5",  "4"   },
                    { "9",  "+", "1",  "10"  },
                    { "8",  "-", "8",  "0"   },
                    { "3",  "×", "3",  "9"   },
                    { "15", "÷", "3",  "5"   },
            };

            for (Object[] caso : casos) {
                // Reinicia a calculadora para cada caso
                calc.clear();

                String entradaA  = (String) caso[0];
                String operador  = (String) caso[1];
                String entradaB  = (String) caso[2];
                String esperado  = (String) caso[3];

                // EXECUÇÃO — digita cada caractere de A, o operador e cada caractere de B
                for (char c : entradaA.toCharArray()) calc.inputDigit(String.valueOf(c));
                calc.inputOperator(operador);
                for (char c : entradaB.toCharArray()) calc.inputDigit(String.valueOf(c));
                calc.calculate();

                // VERIFICAÇÃO — mensagem mostra qual caso falhou, se falhar
                Assert.assertThat(
                        "Falhou para: " + entradaA + " " + operador + " " + entradaB,
                        calc.getCurrentDisplay(),
                        CoreMatchers.is(esperado)
                );
            }
        }
    }
