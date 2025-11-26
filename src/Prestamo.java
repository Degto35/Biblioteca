import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Prestamo {
    private LocalDate fechaPrestamo;
    private String IDprestamo;
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaDevolucion;

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getIDprestamo() {
        return IDprestamo;
    }

    public void setIDprestamo(String IDprestamo) {
        this.IDprestamo = IDprestamo;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Prestamo(LocalDate fechaPrestamo, Libro libro, Usuario usuario, String idprestamo){
        this.fechaPrestamo = fechaPrestamo;
        this.IDprestamo = idprestamo;
        this.libro = libro;
        this.usuario = usuario;
        // NO inicialices fechaDevolucion aquí
    }

    public void setfechaDevolucion(int Dias){
        this.fechaDevolucion = this.fechaPrestamo.plusDays(Dias);
    }
}
