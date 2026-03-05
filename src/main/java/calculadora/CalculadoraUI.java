package calculadora;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Interface gráfica da Calculadora usando Java Swing.
 */
public class CalculadoraUI extends JFrame {

    private final Calculadora calc = new Calculadora();
    private JLabel displayLabel;
    private JLabel historyLabel;

    // ─── Paleta de Cores ──────────────────────────────────────────────
    private static final Color BG          = new Color(18,  18,  18);
    private static final Color DISPLAY_BG  = new Color(28,  28,  28);
    private static final Color BTN_NUM     = new Color(48,  48,  48);
    private static final Color BTN_OP      = new Color(255, 149,  0);
    private static final Color BTN_FUNC    = new Color(68,  68,  68);
    private static final Color BTN_EQUALS  = new Color(255, 149,  0);
    private static final Color TEXT_MAIN   = Color.WHITE;
    private static final Color TEXT_FUNC   = new Color(210, 210, 210);
    private static final Color TEXT_HIST   = new Color(140, 140, 140);

    public CalculadoraUI() {
        super("Calculadora Java");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        buildUI();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ─── Construção da UI ─────────────────────────────────────────────

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(0, 0, 0, 0));

        root.add(buildDisplay(), BorderLayout.NORTH);
        root.add(buildButtons(), BorderLayout.CENTER);

        add(root);
        addKeyboardListener();
    }

    private JPanel buildDisplay() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(DISPLAY_BG);
        panel.setBorder(new EmptyBorder(20, 20, 12, 20));
        panel.setPreferredSize(new Dimension(320, 110));

        historyLabel = new JLabel(" ");
        historyLabel.setForeground(TEXT_HIST);
        historyLabel.setFont(new Font("SFMono-Regular", Font.PLAIN, 14));
        historyLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        displayLabel = new JLabel("0");
        displayLabel.setForeground(TEXT_MAIN);
        displayLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 48));
        displayLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(historyLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(displayLabel);

        return panel;
    }

    private JPanel buildButtons() {
        JPanel panel = new JPanel(new GridLayout(5, 4, 2, 2));
        panel.setBackground(new Color(8, 8, 8));
        panel.setBorder(new EmptyBorder(2, 2, 2, 2));

        // Linha 1 — funções
        addBtn(panel, "AC",  BTN_FUNC, TEXT_FUNC, e -> { calc.clear(); refresh(); });
        addBtn(panel, "+/-", BTN_FUNC, TEXT_FUNC, e -> { calc.toggleSign(); refresh(); });
        addBtn(panel, "%",   BTN_FUNC, TEXT_FUNC, e -> { calc.applyPercentage(); refresh(); });
        addBtn(panel, "÷",   BTN_OP,  Color.WHITE, e -> { calc.inputOperator("÷"); refresh(); });

        // Linha 2
        addBtn(panel, "7", BTN_NUM, TEXT_MAIN, e -> { calc.inputDigit("7"); refresh(); });
        addBtn(panel, "8", BTN_NUM, TEXT_MAIN, e -> { calc.inputDigit("8"); refresh(); });
        addBtn(panel, "9", BTN_NUM, TEXT_MAIN, e -> { calc.inputDigit("9"); refresh(); });
        addBtn(panel, "×", BTN_OP,  Color.WHITE, e -> { calc.inputOperator("×"); refresh(); });

        // Linha 3
        addBtn(panel, "4", BTN_NUM, TEXT_MAIN, e -> { calc.inputDigit("4"); refresh(); });
        addBtn(panel, "5", BTN_NUM, TEXT_MAIN, e -> { calc.inputDigit("5"); refresh(); });
        addBtn(panel, "6", BTN_NUM, TEXT_MAIN, e -> { calc.inputDigit("6"); refresh(); });
        addBtn(panel, "-", BTN_OP,  Color.WHITE, e -> { calc.inputOperator("-"); refresh(); });

        // Linha 4
        addBtn(panel, "1", BTN_NUM, TEXT_MAIN, e -> { calc.inputDigit("1"); refresh(); });
        addBtn(panel, "2", BTN_NUM, TEXT_MAIN, e -> { calc.inputDigit("2"); refresh(); });
        addBtn(panel, "3", BTN_NUM, TEXT_MAIN, e -> { calc.inputDigit("3"); refresh(); });
        addBtn(panel, "+", BTN_OP,  Color.WHITE, e -> { calc.inputOperator("+"); refresh(); });

        // Linha 5
        addWideBtn(panel, "0", BTN_NUM, TEXT_MAIN, e -> { calc.inputDigit("0"); refresh(); });
        addBtn(panel, ".",  BTN_NUM, TEXT_MAIN, e -> { calc.inputDigit("."); refresh(); });
        addBtn(panel, "=",  BTN_EQUALS, Color.WHITE, e -> { calc.calculate(); refresh(); });

        return panel;
    }

    // ─── Helpers de botões ────────────────────────────────────────────

    private void addBtn(JPanel p, String text, Color bg, Color fg, ActionListener al) {
        JButton btn = createButton(text, bg, fg);
        btn.addActionListener(al);
        p.add(btn);
    }

    /** Botão largo (ocupa 2 colunas via GridBagLayout trick usando um painel wrapper). */
    private void addWideBtn(JPanel p, String text, Color bg, Color fg, ActionListener al) {
        // GridLayout não suporta colspan diretamente; usamos um JPanel de 2 células
        JButton btn = createButton(text, bg, fg);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(0, 28, 0, 0));
        btn.addActionListener(al);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(8, 8, 8));
        wrapper.add(btn, BorderLayout.CENTER);

        // Adiciona duas colunas manualmente
        p.add(wrapper);
        p.add(new JLabel()); // placeholder invisível
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed()
                        ? bg.brighter()
                        : getModel().isRollover() ? bg.brighter().brighter() : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setPreferredSize(new Dimension(78, 78));
        btn.setFont(new Font("Helvetica Neue", Font.PLAIN, 22));
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setBorder(new EmptyBorder(0, 0, 0, 0));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ─── Atualização do display ────────────────────────────────────────

    private void refresh() {
        String display = calc.getCurrentDisplay();

        // Auto-reduz fonte se o número for grande
        int len = display.length();
        int fontSize = len > 12 ? 28 : len > 8 ? 36 : 48;
        displayLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, fontSize));
        displayLabel.setText(display);

        // Histórico
        String op = calc.getOperator();
        if (!op.isEmpty()) {
            historyLabel.setText(String.format("%s %s", formatDouble(calc.getFirstOperand()), op));
        } else {
            historyLabel.setText(" ");
        }

        // Muda botão AC <-> C
        // (simplificado: AC sempre limpa tudo nesta versão)
    }

    private String formatDouble(double v) {
        if (v == Math.floor(v) && Math.abs(v) < 1e15) return String.valueOf((long) v);
        return String.valueOf(v);
    }

    // ─── Teclado ─────────────────────────────────────────────────────

    private void addKeyboardListener() {
        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                char c = e.getKeyChar();

                if (c >= '0' && c <= '9') { calc.inputDigit(String.valueOf(c)); refresh(); }
                else if (c == '.') { calc.inputDigit("."); refresh(); }
                else if (c == '+') { calc.inputOperator("+"); refresh(); }
                else if (c == '-') { calc.inputOperator("-"); refresh(); }
                else if (c == '*') { calc.inputOperator("×"); refresh(); }
                else if (c == '/') { calc.inputOperator("÷"); refresh(); }
                else if (c == '%') { calc.applyPercentage(); refresh(); }
                else if (c == '=' || code == KeyEvent.VK_ENTER) { calc.calculate(); refresh(); }
                else if (code == KeyEvent.VK_BACK_SPACE) { calc.backspace(); refresh(); }
                else if (code == KeyEvent.VK_ESCAPE) { calc.clear(); refresh(); }
            }
        });
        setFocusable(true);
    }

    // ─── Main ─────────────────────────────────────────────────────────

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(CalculadoraUI::new);
    }
}