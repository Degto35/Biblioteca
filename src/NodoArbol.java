public class NodoArbol {
    private Libro dato;
    private NodoArbol hijoizquiero;
    private NodoArbol hijoderecho;

    public NodoArbol(Libro dato){
        this.dato = dato;
        this.hijoizquiero = null;
        this.hijoderecho= null;
    }

    public Libro getDato() {
        return dato;
    }

    public void setDato(Libro dato) {
        this.dato = dato;
    }

    public NodoArbol getHijoizquiero() {
        return hijoizquiero;
    }

    public void setHijoizquiero(NodoArbol hijoizquiero) {
        this.hijoizquiero = hijoizquiero;
    }

    public NodoArbol getHijoderecho() {
        return hijoderecho;
    }

    public void setHijoderecho(NodoArbol hijoderecho) {
        this.hijoderecho = hijoderecho;
    }
}
