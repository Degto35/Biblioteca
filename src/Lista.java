public class Lista {
    protected NodoL inicio;
    protected NodoL fin;

    public Lista(){
        inicio = null;
        fin = null;
    }
    public boolean ListaVacia(){
        if(inicio == null){
            return true;
        }else {
         return false;
        }
    }
    public void AgregarElementoInicio(Usuario usuario){
        inicio = new NodoL(usuario, inicio);
        if(fin == null){
            fin = inicio;
        }
    }
    public void AgregarElementoFinal(Usuario usuario){
        if (!ListaVacia()){
            fin.siguiente = new NodoL(usuario);
            fin = fin.siguiente;
        }else {
            inicio = fin= new NodoL(usuario);
        }
    }
    public Usuario EliminarFinalLista(){
        Usuario usuario = fin.usuario;
        if (inicio == fin){
            inicio = fin = null;
        }else {
            NodoL auxiliar = inicio;
            while (auxiliar.siguiente != fin){
                auxiliar = auxiliar.siguiente;
            }
            fin = auxiliar;
            fin.siguiente = null;
        }
        return usuario;
    }

    public void Mostrarlista() {
        NodoL mostrar = inicio;
        while (mostrar != null) {
            System.out.println("[" + mostrar.usuario.getNombre()+"--> "+"Identificacion: "+mostrar.usuario.getIdentidad()+"]");
            mostrar = mostrar.siguiente;
        }
    }
    public Usuario Eliminar_inicio_Lista(){
        Usuario usuario = inicio.getUsuario();
        if (inicio == fin){
            inicio = fin = null;
        }else {
            inicio = inicio.siguiente;
        }
        return usuario;
    }
    public NodoL getInicio() {
        return inicio;
    }

    public NodoL getFin() {
        return fin;
    }
}
