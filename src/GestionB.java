import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestionB {
    private Arbolb arbol_de_libros;
    private Lista fila_de_usuarios;
    private Pila acciones_de_usuares;
    private List<Prestamo> prestamosActivos = new ArrayList<>();

    public void Agregar_libro(String Titulo, String Autor, String Categoria, LocalDate Fecha){
        int codigo = Libro.Ultimo_codigo+1;
        Libro.Ultimo_codigo = codigo;
        Libro libro = new Libro(Titulo, Autor, Categoria, Fecha, codigo);
        this.arbol_de_libros.AgregarNodo(libro);
    }

}
