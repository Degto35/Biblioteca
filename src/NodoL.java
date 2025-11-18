public class NodoL {
    public Usuario usuario;
    public NodoL siguiente;

    public NodoL(Usuario usuario, NodoL inicio){
        this.usuario = usuario;
        this.siguiente = inicio;
    }
    public NodoL(Usuario usuario){
        this.usuario = usuario;
        this.siguiente = null;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public NodoL getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoL siguiente) {
        this.siguiente = siguiente;
    }
}
