import javax.swing.*;

public class GUI extends JFrame {
    private JPanel panelPrincipal;
    private JButton avanzarTurnoButton;
    private JPanel panelCartasJ1;
    private JLabel labelGuerra;
    private JPanel panelAcciones;
    private JPanel panelInfo;
    private JPanel panelCartasJ2;
    private JLabel labelNombreJ1;
    private JLabel labelNombreJ2;
    private JLabel labelTurnos;

    private final ReglasGuerra reglasGuerra;

    public GUI() {
        // Inicializando algunos parámetros del JFrame
        setTitle("Guerra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        int numeroRondas = ingresarLimiteRondas();
        String nombreJ1 = ingresarNombre(1);
        String nombreJ2 = ingresarNombre(2);
        reglasGuerra = new ReglasGuerra(numeroRondas, nombreJ1, nombreJ2);
        reglasGuerra.repartirCartas();
        avanzarTurnoButton.addActionListener(e -> avanzarTurno());
        actualizarGUI();
    }

    private void avanzarTurno() {
        reglasGuerra.jugarTurno();
        actualizarGUI();

        if (reglasGuerra.isJuegoTerminado()) {
            mostrarMensajesJuegoTerminado();
            System.exit(0);
        }
    }

    public void mostrarMensajesJuegoTerminado() {
        Jugador ganador = reglasGuerra.getGanadorJuego();
        JOptionPane.showMessageDialog(
                null,
                "¡El juego se acabó!",
                "Juego terminado",
                JOptionPane.INFORMATION_MESSAGE
        );
        if (ganador != null) {
            String ganadorString = ganador.toString();
            JOptionPane.showMessageDialog(
                    null,
                    String.format("El ganador del juego es %s",ganadorString),
                    "Ganador del juego",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Hubo un empate, ¡nadie ha ganado!",
                    "Empate",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void actualizarGUI() {
        labelGuerra.setText(reglasGuerra.isGuerraActiva() ? "¡Guerra!" : "");
        labelTurnos.setText("Turno: %d de %d".formatted(reglasGuerra.getNumeroTurnoActual(),reglasGuerra.getLimiteTurnos()));
        labelNombreJ1.setText(reglasGuerra.getJugadorString(0, true));
        labelNombreJ2.setText(reglasGuerra.getJugadorString(1, true));
        agregarCartasPanel(panelCartasJ1, 0);
        agregarCartasPanel(panelCartasJ2, 1);
        pack();
        setLocationRelativeTo(null);
    }

    private void agregarCartasPanel(JPanel panel, int indiceJugador) {
        panel.removeAll();
        if (reglasGuerra.hayCartasEnMesa()) {
            for (String directorio : reglasGuerra.getDirectorios(indiceJugador)) {
                ImageIcon imagen = new ImageIcon(directorio);
                panel.add(new JLabel(imagen));
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    private int ingresarLimiteRondas() {
        int rondasElegidas = 0;
        boolean cantidadValida;
        boolean esUnNumero;
        do {
            try {
                rondasElegidas = Integer.parseInt(JOptionPane.showInputDialog(
                        null,
                        "Elige la cantidad de rondas",
                        "Límite de rondas",
                        JOptionPane.QUESTION_MESSAGE
                ));
                esUnNumero = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Por favor, ingrese un número.",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE
                );
                esUnNumero = false;
            }
            if (rondasElegidas < 10 && esUnNumero) {
                JOptionPane.showMessageDialog(
                        null,
                        "Por favor, ingrese un limite de rondas mayor o igual a 10.",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE
                );
                cantidadValida = false;
            } else {
                cantidadValida = true;
            }
        } while (!cantidadValida || !esUnNumero);
        return rondasElegidas;
    }

    private String ingresarNombre(int indiceJugador) {
        String nombreElegido;
        do {
            nombreElegido = JOptionPane.showInputDialog(
                    null,
                    "Jugador %d, ¿cuál es tu nombre?".formatted(indiceJugador),
                    "El nombre del jugador %d".formatted(indiceJugador),
                    JOptionPane.QUESTION_MESSAGE
            );

            if (nombreElegido == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Por favor, ingrese un nombre válido.",
                        "Nombre no válido.",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } while (nombreElegido == null);
        return nombreElegido;
    }
}