import java.time.LocalDateTime;
import java.util.Date;

public class Accion {
    private String tipo;
    private  String detalle;
    private LocalDateTime fecha;

    public Accion (String tipo, String detalle){
        this.tipo = tipo;
        this.detalle = detalle;
        this.fecha = LocalDateTime.now();
    }
    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
