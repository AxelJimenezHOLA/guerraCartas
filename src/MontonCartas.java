public class MontonCartas {
    protected final ListaSimple<Carta> cartas;

    public MontonCartas() {
        cartas = new ListaSimple<>();
    }

    public void agregarCarta(Carta carta) {
        cartas.addLast(carta);
    }

    public Carta retirarCarta() {
        return cartas.removeLast();
    }

    public int size() {
        return cartas.size();
    }

    public String[] getDirectoriosCartas() {
        String[] directorios = new String[cartas.size()];
        for (int i = 0; i < cartas.size(); i++) {
            directorios[i] = cartas.get(i).getDirectorio();
        }
        return directorios;
    }

    @Override
    public String toString() {
        return cartas.toString();
    }
}