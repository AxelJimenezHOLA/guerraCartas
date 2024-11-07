public class Mano extends MontonCartas {
    public Mano() {
        super();
    }

    @Override
    public Carta retirarCarta() {
        return cartas.removeFirst();
    }
}