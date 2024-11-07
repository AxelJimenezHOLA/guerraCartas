public class ReglasGuerra {
    private final Jugador[] jugadores;
    private Jugador ganadorRonda;
    private Jugador ganadorJuego;

    private final Baraja barajaJuego;
    private final MontonCartas[] mesaJuego;

    private int numeroTurnoActual;
    private final int limiteTurnos;

    private boolean guerraActiva;
    private boolean juegoTerminado;
    private boolean agregoCartasGuerra;

    public ReglasGuerra(int limiteTurnos, String nombreJ1, String nombreJ2) {
        jugadores = new Jugador[]{new Jugador(nombreJ1), new Jugador(nombreJ2)};
        barajaJuego = new Baraja();
        mesaJuego = new MontonCartas[]{new MontonCartas(), new MontonCartas()};
        this.limiteTurnos = limiteTurnos;
        numeroTurnoActual = 0;
        guerraActiva = false;
        juegoTerminado = false;
        agregoCartasGuerra = false;
    }

    public void repartirCartas() {
        for (int i = 0; i < 52; i++) {
            jugadores[i % 2].agregarCarta(barajaJuego.retirarCarta());
        }
    }

    public void jugarTurno() {
        if (!alcanzoNumeroTurnos()) {
            finalizarJuego();
            return;
        }

        if (guerraActiva && !agregoCartasGuerra) {
            System.out.println("ANTES DE COLOCAR CARTAS (Guerra)");
            System.out.println(this);
            agregarCartasGuerra();
            System.out.println("DESPUES DE COLOCAR CARTAS (Guerra)");
            System.out.println(this);
            agregoCartasGuerra = true;
            return;
        }

        if (guerraActiva) {
            System.out.println("DESPUES DE COMPARAR Y DAR CARTAS (Guerra)");
            compararCartasGuerra();
            if (!guerraActiva) {
                darCartasAlGanador();
            }
            numeroTurnoActual++;
            System.out.println(this);
            return;
        }

        if (!hayCartasEnMesa()) {
            System.out.println("ANTES DE COLOCAR CARTAS");
            System.out.println(this);
            agregarCartasNormal();
            System.out.println("DESPUES DE COLOCAR CARTAS");
            System.out.println(this);
            return;
        }



        System.out.println("DESPUES DE COMPARAR Y DAR CARTAS");
        compararCartasNormal();
        if (!guerraActiva) darCartasAlGanador();
        numeroTurnoActual++;
        System.out.println(this);
    }

    private boolean alcanzoNumeroTurnos() {
        return numeroTurnoActual < limiteTurnos;
    }

    private void agregarCartasNormal() {
        jugarCartaIndividual(jugadores[0], mesaJuego[0], true);
        jugarCartaIndividual(jugadores[1], mesaJuego[1], true);
    }

    private void jugarCartaIndividual(Jugador jugador, MontonCartas camino, boolean cartaVisible) {
        if (jugador.contarCartas() > 0) {
            Carta cartaJugada = jugador.retirarCarta();
            cartaJugada.setVisible(cartaVisible);
            camino.agregarCarta(cartaJugada);
        } else {
            finalizarJuego();
        }
    }

    private void compararCartasNormal() {
        Carta cartaJ1 = mesaJuego[0].retirarCarta();
        Carta cartaJ2 = mesaJuego[1].retirarCarta();

        mesaJuego[0].agregarCarta(cartaJ1);
        mesaJuego[1].agregarCarta(cartaJ2);

        int comparacion = cartaJ1.compareTo(cartaJ2);
        if (comparacion != 0) {
            ganadorRonda = (comparacion > 0) ? jugadores[0] : jugadores[1];
        } else {
            guerraActiva = true;
        }
    }

    private void darCartasAlGanador() {
        while (mesaJuego[0].size() > 0) {
            Carta cartaGanada = mesaJuego[0].retirarCarta();
            ganadorRonda.agregarCarta(cartaGanada);
        }

        while (mesaJuego[1].size() > 0) {
            Carta cartaGanada = mesaJuego[1].retirarCarta();
            ganadorRonda.agregarCarta(cartaGanada);
        }
    }

    private void agregarCartasGuerra() {
        if (!suficientesCartasParaGuerra()) {
            finalizarJuego();
            return;
        }

        for (int j = 0; j < jugadores.length; j++) {
            Jugador jugador = jugadores[j];
            for (int i = 0; i < 3; i++) {
                jugarCartaIndividual(jugador, mesaJuego[j], false);
            }
            jugarCartaIndividual(jugador, mesaJuego[j], true);
        }
    }

    private void compararCartasGuerra() {
        Carta cartaJ1 = mesaJuego[0].retirarCarta();
        Carta cartaJ2 = mesaJuego[1].retirarCarta();

        mesaJuego[0].agregarCarta(cartaJ1);
        mesaJuego[1].agregarCarta(cartaJ2);

        int comparacion = cartaJ1.compareTo(cartaJ2);
        if (comparacion != 0) {
            ganadorRonda = (comparacion > 0) ? jugadores[0] : jugadores[1];
            guerraActiva = false;
        }
        agregoCartasGuerra = false;
    }

    private boolean suficientesCartasParaGuerra() {
        return jugadores[0].contarCartas() >= 4 && jugadores[1].contarCartas() >= 4;
    }

    private void finalizarJuego() {
        if (jugadores[0].contarCartas() > jugadores[1].contarCartas()) {
            ganadorJuego = jugadores[0];
        } else if (jugadores[0].contarCartas() == jugadores[1].contarCartas()) {
            ganadorJuego = null;
        } else {
            ganadorJuego = jugadores[1];
        }
        juegoTerminado = true;
    }

    // Getters
    public Jugador getGanadorJuego() {
        return ganadorJuego;
    }

    public boolean isGuerraActiva() {
        return guerraActiva;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public boolean hayCartasEnMesa() {
        for (MontonCartas montonCartas : mesaJuego) {
            if (montonCartas.size() > 0) return true;
        }
        return false;
    }

    public String[] getDirectorios(int indiceJugador) {
        return mesaJuego[indiceJugador].getDirectoriosCartas();
    }

    public int getLimiteTurnos() {
        return limiteTurnos;
    }

    public int getNumeroTurnoActual() {
        return numeroTurnoActual;
    }

    public String getJugadorString(int indiceJugador, boolean agregarPuntaje) {
        return agregarPuntaje ?
                jugadores[indiceJugador].regresarNombreYCantidadDeCartas() :
                jugadores[indiceJugador].toString();
    }

    //toString tramposo jejeje
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("J1:\n");
        sb.append("%s\n".formatted(jugadores[0].getMano().toString()));

        sb.append("J2:\n");
        sb.append("%s\n".formatted(jugadores[1].getMano().toString()));

        sb.append("MESA:\n");
        for (MontonCartas montonCartas : mesaJuego) {
            sb.append("%s%n".formatted(montonCartas.toString()));
        }

        sb.append("Último ganador ronda: %s%n".formatted(ganadorRonda));
        return sb.toString();
    }
}