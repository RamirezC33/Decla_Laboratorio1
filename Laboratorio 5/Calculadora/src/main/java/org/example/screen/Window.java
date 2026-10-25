package org.example.screen;

import org.example.Connection;
import org.example.screen.components.Buttons;
import org.example.screen.components.PanelResult;
import org.example.screen.components.TextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

/** Ventana principal: arma los componentes y conecta la interfaz con el motor Prolog. */
public class Window extends JFrame {

    // Entero o decimal, con signo negativo opcional: 5, -3, 2.5
    private static final String NUMERO = "-?\\d+(\\.\\d+)?";

    private final TextField txtA = new TextField("Ej: 8");
    private final TextField txtB = new TextField("Ej: 2");
    private final PanelResult panelResultado = new PanelResult();
    private final JLabel lblEstado = new JLabel(" ");

    public Window(Connection motor) {
        super("Calculadora básica en Prolog");

        // Enter en cualquiera de los campos = sumar
        txtA.addActionListener(e -> ejecutar(motor, "suma", "Suma"));
        txtB.addActionListener(e -> ejecutar(motor, "suma", "Suma"));

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        agregar(contenido, etiqueta("Número A"), 6);
        agregar(contenido, txtA, 12);
        agregar(contenido, etiqueta("Número B"), 6);
        agregar(contenido, txtB, 18);
        agregar(contenido, crearBotones(motor), 22);
        agregar(contenido, panelResultado, 10);
        agregar(contenido, crearPie(), 0);

        JPanel raiz = new JPanel(new BorderLayout(0, 20));
        raiz.setBackground(Theme.BG);
        raiz.setBorder(new EmptyBorder(24, 28, 24, 28));
        raiz.add(crearEncabezado(), BorderLayout.NORTH);
        raiz.add(contenido, BorderLayout.CENTER);

        setContentPane(raiz);
        setMinimumSize(new Dimension(660, 560));
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // ------------------------------------------------------------------
    //  Construcción de secciones
    // ------------------------------------------------------------------

    private JPanel crearEncabezado() {
        JLabel titulo = new JLabel("Calculadora básica");
        titulo.setFont(Theme.sans(Font.BOLD, 24));
        titulo.setForeground(Theme.TEXT);

        JLabel subtitulo = new JLabel("Las 4 operaciones resueltas con reglas en Prolog");
        subtitulo.setFont(Theme.sans(Font.PLAIN, 13));
        subtitulo.setForeground(Theme.MUTED);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.add(titulo);
        p.add(Box.createVerticalStrut(4));
        p.add(subtitulo);
        return p;
    }

    private JPanel crearBotones(Connection motor) {
        Buttons btnSumar = new Buttons("+", Theme.BLUE, Theme.BG, 26, 11, 16);
        Buttons btnRestar = new Buttons("−", Theme.GREEN, Theme.BG, 26, 11, 16);
        Buttons btnMultiplicar = new Buttons("×", Theme.AMBER, Theme.BG, 26, 11, 16);
        Buttons btnDividir = new Buttons("÷", Theme.RED, Theme.BG, 26, 11, 16);
        btnSumar.addActionListener(e -> ejecutar(motor, "suma", "Suma"));
        btnRestar.addActionListener(e -> ejecutar(motor, "resta", "Resta"));
        btnMultiplicar.addActionListener(e -> ejecutar(motor, "multiplicacion", "Multiplicación"));
        btnDividir.addActionListener(e -> ejecutar(motor, "division", "División"));

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        p.setOpaque(false);
        p.add(btnSumar);
        p.add(btnRestar);
        p.add(btnMultiplicar);
        p.add(btnDividir);
        return p;
    }

    private JPanel crearPie() {
        lblEstado.setFont(Theme.sans(Font.PLAIN, 12));
        lblEstado.setForeground(Theme.MUTED);

        Buttons btnCopiar = new Buttons("Copiar", Theme.CARD, Theme.TEXT, 16, 7, 12);
        btnCopiar.addActionListener(e -> copiarResultado());

        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(lblEstado, BorderLayout.WEST);
        p.add(btnCopiar, BorderLayout.EAST);
        return p;
    }

    // ------------------------------------------------------------------
    //  Lógica
    // ------------------------------------------------------------------

    private void ejecutar(Connection motor, String pred, String nombre) {
        String a = txtA.getText().trim();
        String b = txtB.getText().trim();
        panelResultado.setTitulo(nombre + " de A y B");

        if (a.isEmpty() || b.isEmpty()) {
            mostrar("", "Escribí los dos números.", Theme.AMBER);
            return;
        }
        // Solo se mandan números a Prolog, nunca texto libre
        if (!a.matches(NUMERO) || !b.matches(NUMERO)) {
            mostrar("", "Solo se aceptan números .", Theme.RED);
            return;
        }
        try {
            String resultado = motor.consultar(pred, a, b);
            if (resultado.startsWith("Sin solución")) {
                String motivo = pred.equals("division") ? "No se puede dividir entre cero." : resultado;
                mostrar("", motivo, Theme.AMBER);
            } else {
                mostrar(resultado, "Listo", Theme.GREEN);
            }
        } catch (Exception ex) {
            mostrar("", "Operación inválida: " + ex.getMessage(), Theme.RED);
        }
    }

    private void mostrar(String resultado, String estado, Color colorEstado) {
        panelResultado.setTexto(resultado);
        lblEstado.setText(estado);
        lblEstado.setForeground(colorEstado);
    }

    private void copiarResultado() {
        String t = panelResultado.getTexto();
        if (t.isEmpty()) {
            return;
        }
        Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new StringSelection(t), null);
        lblEstado.setText("Copiado al portapapeles");
        lblEstado.setForeground(Theme.BLUE);
    }

    // ------------------------------------------------------------------
    //  Utilidades
    // ------------------------------------------------------------------

    private static void agregar(JPanel panel, JComponent c, int espacioDespues) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(c);
        if (espacioDespues > 0) {
            panel.add(Box.createVerticalStrut(espacioDespues));
        }
    }

    private static JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(Theme.sans(Font.BOLD, 12));
        l.setForeground(Theme.MUTED);
        return l;
    }
}
