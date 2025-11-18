public class Usuario {
    private String Nombre;
    private float Identidad;

    public Usuario(String Nombre, float Identidad){
        this.Nombre = Nombre;
        this.Identidad = Identidad;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public float getIdentidad() {
        return Identidad;
    }

    public void setIdentidad(float identidad) {
        Identidad = identidad;
    }
}
