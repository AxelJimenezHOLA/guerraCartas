public class Jugador {
    private final String nombre;
    private final Mano mano;

    public Jugador(String nombre) {
        this.nombre = nombre;
        mano = new Mano();
    }

    public void agregarCarta(Carta carta) {
        mano.agregarCarta(carta);
    }

    public Carta retirarCarta() {
        return mano.retirarCarta();
    }

    public int contarCartas() {
        return mano.size();
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String regresarNombreYCantidadDeCartas() {
        return "%s: %d".formatted(nombre, mano.size());
    }

    public MontonCartas getMano() {
        return mano;
    }
}