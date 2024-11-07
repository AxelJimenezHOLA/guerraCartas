public class Carta implements Comparable<Carta> {
    private final int valor;
    private final Figura figura;
    private boolean visible;

    public Carta(int valor, Figura figura) {
        this.valor = valor;
        this.figura = figura;
        visible = true;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getDirectorio() {
        return visible ?
                "src/imagenes/%d_%s.png".formatted(valor, figura.toString()) :
                "src/imagenes/REVERSO.png";
    }

    @Override
    public String toString() {
        return switch (valor) {
            case 11 -> "J de %s".formatted(figura);
            case 12 -> "Q de %s".formatted(figura);
            case 13 -> "K de %s".formatted(figura);
            case 14 -> "A de %s".formatted(figura);
            default -> "%d de %s".formatted(valor, figura);
        };
    }

    @Override
    public int compareTo(Carta otraCarta) {
        return Integer.compare(this.valor, otraCarta.valor);
    }
}