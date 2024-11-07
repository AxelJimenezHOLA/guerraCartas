import java.util.Random;

public class Baraja extends MontonCartas {

    public Baraja() {
        super();
        crearCartas();
    }

    @Override
    public Carta retirarCarta() {
        return cartas.removeAt(new Random().nextInt(cartas.size()));
    }

    private void crearCartas() {
        for (Figura figura : Figura.values()) {
            for (int i = 2; i <= 14; i++) {
                agregarCarta(new Carta(i, figura));
            }
        }
    }
}