public class NodoP {
    private Accion dato;
    private NodoP siguiente;

    public NodoP(){
        this.dato = null;
        this.siguiente = null;
    }

    public Accion getDato() {
        return dato;
    }

    public void setDato(Accion dato) {
        this.dato = dato;
    }

    public NodoP getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoP siguiente) {
        this.siguiente = siguiente;
    }
}
