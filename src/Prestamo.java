import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Prestamo {
    private Date fechaPrestamo;
    private String IDprestamo;
    private Libro libro;
    private Usuario usuario;
    private Date fechaDevolucion;

    public Prestamo(Date fechaPrestamo, String IDprestamo, Libro libro, Usuario usuario){
        this.fechaPrestamo = fechaPrestamo;
        this.IDprestamo = IDprestamo;
        this.libro = libro;
        this.usuario = usuario;

    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
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

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setfechaDevolucion(int Dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.fechaPrestamo);
        calendar.add(Calendar.DAY_OF_MONTH, Dias);
        this.fechaDevolucion = calendar.getTime();
    }
}
