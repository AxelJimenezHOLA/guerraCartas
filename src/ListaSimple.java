import java.util.NoSuchElementException;

public class ListaSimple<T> {
    private Nodo<T> start;

    public ListaSimple() {
        this.start = null;
    }

    public void addFirst(T dato) {
        start = new Nodo<>(dato, start);
    }

    public T removeFirst() {
        T poppedData;
        if (start == null) {
            throw new RuntimeException("Empty list");
        } else {
            poppedData = start.getInfo();
            start = start.getSiguiente();
        }
        return poppedData;
    }

    public void addLast(T dato) {
        Nodo<T> n = new Nodo<>(dato, null);
        if (start == null) {
            n.setSiguiente(null);
            start = n;
        } else {
            Nodo<T> r = start;
            while (r.getSiguiente() != null) {
                r = r.getSiguiente();
            }
            r.setSiguiente(n);
            n.setSiguiente(null);
        }
    }

    public T removeLast() {
        T poppedData;
        if (start == null) {
            throw new RuntimeException("Empty list");
        } else if (start.getSiguiente() == null) {
            poppedData = start.getInfo();
            start = null;
        } else {
            Nodo<T> r = start;
            Nodo<T> a = r;

            while (r.getSiguiente() != null) {
                a = r;
                r = r.getSiguiente();
            }
            poppedData = r.getInfo();
            a.setSiguiente(null);
        }
        return poppedData;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if (start != null) {
            Nodo<T> aux = start;
            do {
                sb.append(aux);
                aux = aux.getSiguiente();
                if (aux != null) sb.append(", ");
            } while (aux != null);
        }
        sb.append("]");
        return sb.toString();
    }

    public int size() {
        int counter = 0;
        Nodo<T> aux = start;
        while (aux != null) {
            counter++;
            aux = aux.getSiguiente();
        }
        return counter;
    }

    /*
    metodos extra
    */

    public T popData(T data) { //Eliminar X
        T poppedData = null;
        Nodo<T> aux = start;
        Nodo<T> prev = null;

        if (start == null) {
            throw new RuntimeException("Empty list");
        } else if (start.getInfo().equals(data)) {
            poppedData = start.getInfo();
            start = start.getSiguiente();
        } else {
            while (aux != null) {
                if (aux.getInfo().equals(data)) {
                    if (prev != null) prev.setSiguiente(aux.getSiguiente());
                    poppedData = aux.getInfo();
                }
                prev = aux;
                aux = aux.getSiguiente();
            }
        }

        if (poppedData == null) throw new NoSuchElementException("Element not found");
        return poppedData;
    }


    public int getIndexOf(T data) { //Búsqueda lineal
        int index = 0;
        Nodo<T> n = start;
        while (n.getSiguiente() != null) {
            if (n.getInfo().equals(data)) return index;
            n = n.getSiguiente();
            index++;
        }
        return -1;
    }

    public T get(int selectedIndex) {
        Nodo<T> n = start;
        int currentIndex = 0;
        while (n != null) {
            if (currentIndex == selectedIndex) return n.getInfo();
            n = n.getSiguiente();
            currentIndex++;
        }
        return null;
    }

    public void addAt(T data, int index) { //Insertar en posición
        Nodo<T> n = new Nodo<>(data, null);
        int counter = 0;
        if (start == null) {
            start = n;
        } else if (index == 0) {
            addFirst(data);
        } else if (index < this.size() && index > 0) {
            Nodo<T> p = start;
            Nodo<T> r = start;

            while (p.getSiguiente() != null && counter != index - 1) {
                p = p.getSiguiente();
                counter++;
            }
            counter = 0;
            while (r.getSiguiente() != null && counter != index) {
                r = r.getSiguiente();
                counter++;
            }

            p.setSiguiente(n);
            n.setSiguiente(r);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public T removeAt(int index) {
        T poppedData;
        int counter = 0;
        if (start == null) {
            throw new RuntimeException("Empty list");
        }

        if (index == 0) {
            poppedData = start.getInfo();
            removeFirst();
            return poppedData;
        }

        if (index < this.size() && index > 0) {
            Nodo<T> aux = start;
            while (counter != index - 1) {
                aux = aux.getSiguiente();
                counter++;
            }
            Nodo<T> r = aux.getSiguiente();
            poppedData = r.getInfo();
            aux.setSiguiente(aux.getSiguiente().getSiguiente());
            r.setSiguiente(null);
            return poppedData;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void clear() {
        start = null;
    }

}