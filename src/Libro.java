import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Libro {
    private String Titulo;
    private String Autor;
    private String Categoria;
    private LocalDate Fecha;
    private int Codigo;
    private boolean Disponibilidad;
    public static int Ultimo_codigo = 0;

    public Libro(String Titulo, String Autor, String Categoria, LocalDate Fecha, int Codigo){
        this.Titulo = Titulo;
        this.Autor = Autor;
        this.Categoria = Categoria;
        this.Fecha = Fecha;
        this.Codigo = Codigo;
        this.Disponibilidad = true;
    }
    public Libro(String Titulo, String Autor, String Categoria, LocalDate Fecha, int Codigo, boolean disponibilidad){
        this.Titulo = Titulo;
        this.Autor = Autor;
        this.Categoria = Categoria;
        this.Fecha = Fecha;
        this.Codigo = Codigo;
        this.Disponibilidad = disponibilidad;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        Autor = autor;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public LocalDate getFecha() {
        return Fecha;
    }

    public void setFecha(LocalDate fecha) {
        Fecha = fecha;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public boolean getDisponibilidad() {
        return Disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        Disponibilidad = disponibilidad;
    }
}
