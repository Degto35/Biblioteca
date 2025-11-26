public class Pila {
    public NodoP inicio;
    private int tamano;

    public Pila(){
        this.inicio = null;
        this.tamano = 0;
    }
    public boolean PilaVacia(){
        return inicio == null;
    }
    public int getTamano(){
        return tamano;
    }
    public void Apilar(Accion tipo){
        NodoP nuevo = new NodoP();
        nuevo.setDato(tipo);

        if (PilaVacia()){
            inicio = nuevo;
        }else {
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
        }
        tamano++;
    }
    public Accion Cima(){
        if (!PilaVacia()){
            return inicio.getDato();
        }else {
            return null;
        }
    }
    public void Mostrar(){
        NodoP auxiliar = inicio;
        while (auxiliar != null){
            System.out.println(auxiliar.getDato());
            auxiliar = auxiliar.getSiguiente();
        }
    }
    public Accion Buscar(Accion elemento_buscar){
        NodoP auxiliar = inicio;
        boolean existe = false;
        while (existe != true && auxiliar != null){
            if (elemento_buscar == auxiliar.getDato()){
                existe = true;
                return  auxiliar.getDato();
            }else {
                auxiliar = auxiliar.getSiguiente();
            }
        }
        return null;
    }
    public NodoP getInicio() {
        return inicio;
    }

}
