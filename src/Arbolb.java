import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Date;

public class Arbolb {
    NodoArbol raiz;

    public Arbolb(){
        this.raiz = null;
    }
    public boolean ArbolVacio(){
        return raiz == null;
    }
    public void AgregarNodo(Libro dato){
        NodoArbol nuevo = new NodoArbol(dato);

        if (raiz == null){
            raiz = nuevo;
        }else {
            NodoArbol auxiliar = raiz;
            NodoArbol padre;

            while (true){
                padre = auxiliar;

                if (dato.getCodigo() < auxiliar.getDato().getCodigo()){
                    auxiliar = auxiliar.getHijoizquiero();

                    if (auxiliar == null){
                        padre.setHijoizquiero(nuevo);
                        return;
                    }
                }else {
                    auxiliar = auxiliar.getHijoderecho();

                    if (auxiliar == null){
                        padre.setHijoderecho(nuevo);
                        return;
                    }
                }
            }
        }
    }
    public void InOrden(NodoArbol raiz){
        if (raiz != null){
            InOrden(raiz.getHijoizquiero());
            System.out.println(" "+raiz.getDato().getTitulo()+" - "+raiz.getDato().getAutor()+" - "+raiz.getDato().getCategoria()+" - "+raiz.getDato().getFecha()+" -"+(raiz.getDato().getDisponibilidad()?"Disponible": "No Disponible")+" - "+raiz.getDato().getCodigo());
            InOrden(raiz.getHijoderecho());
        }
    }
    private void guardarPreorden(NodoArbol raiz, PrintWriter pw){
        if (raiz != null){
            Libro libro = raiz.getDato();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

            String linea = String.format("%d,%s,%s,%s,%s,%b",
                    libro.getCodigo(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getFecha().format(formatter),
                    libro.getCategoria(),
                    libro.getDisponibilidad());
            pw.println(linea);
            guardarPreorden(raiz.getHijoizquiero(), pw);
            guardarPreorden(raiz.getHijoderecho(), pw);
        }
    }
    public Libro buscarLibroPorCodigo(NodoArbol raiz, int codigo) {
        return buscarRecursivo(raiz, codigo);
    }

    private Libro buscarRecursivo(NodoArbol nodo, int codigo) {
        if (nodo == null) {
            return null;
        }

        int codigoNodo = nodo.getDato().getCodigo();
        if (codigo == codigoNodo) {
            return nodo.getDato();
        } else if (codigo < codigoNodo) {
            return buscarRecursivo(nodo.getHijoizquiero(), codigo);
        } else {
            return buscarRecursivo(nodo.getHijoderecho(), codigo);
        }
    }
    public boolean actualizarDispoibilidad(NodoArbol raiz, int codigo, boolean disponibilidad) {
        return ActualizarRecursivo_disponibilidad(raiz, codigo, disponibilidad);
    }
    private boolean ActualizarRecursivo_disponibilidad(NodoArbol nodo, int codigo, boolean disponibilidad) {
        if (nodo == null) {
            return false;
        }
        int codigoNodo = nodo.getDato().getCodigo();
        if (codigo == codigoNodo) {
            nodo.getDato().setDisponibilidad(disponibilidad);
            return true;
        } else if (codigo < codigoNodo) {
            return ActualizarRecursivo_disponibilidad(nodo.getHijoizquiero(), codigo, disponibilidad);
        } else {
            return ActualizarRecursivo_disponibilidad(nodo.getHijoderecho(), codigo, disponibilidad);
        }
    }
    public  void guardarEnArchivo(String nombreArchivo){
        try (PrintWriter fw = new PrintWriter(new FileWriter(nombreArchivo))){
            guardarPreorden(raiz, fw);
            System.out.println("Libros guardados exitosamente en: " + nombreArchivo);
        }catch (IOException e) {
            System.err.println(" Error al guardar los datos: " + e.getMessage());
        }
    }

    public void cargarDesdeArchivo(String nombreArchivo) {
        // 1. Reinicializa la raíz (si es necesario) y el contador
        this.raiz = null;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                // 2. Analizar la línea leída
                String[] datos = linea.split(",");

                if (datos.length == 6) {
                    // 3. Crear el objeto Libro
                    int codigo = Integer.parseInt(datos[0]);
                    String titulo = datos[1];
                    String autor = datos[2];
                    LocalDate fecha = LocalDate.parse(datos[3], formatter);
                    String categoria = datos[4];
                    boolean disponibilidad = Boolean.parseBoolean(datos[5]);

                    Libro nuevoLibro = new Libro(titulo, autor, categoria, fecha, codigo, disponibilidad);

                    // 4. Insertar en el árbol
                    AgregarNodo(nuevoLibro);
                }
            }
            System.out.println("Libros cargados y árbol reconstruido exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error de formato en el archivo (Año): " + e.getMessage());
        }
    }
    public NodoArbol ObtenerNodoReemplazar(NodoArbol nodoReemp) {

        NodoArbol reemplazoPadre = nodoReemp;

        NodoArbol reemplazo = nodoReemp.getHijoderecho();

        NodoArbol auxiliar = nodoReemp.getHijoderecho();

        while (auxiliar != null) {
            reemplazoPadre = reemplazo;
            reemplazo = auxiliar;
            auxiliar = auxiliar.getHijoizquiero(); // Ir a la izquierda
        }

        if (reemplazo != nodoReemp.getHijoderecho()) {

            reemplazoPadre.setHijoizquiero(reemplazo.getHijoderecho());

            reemplazo.setHijoderecho(nodoReemp.getHijoderecho());
        }

        System.out.println("El nodo reemplazante es: " + reemplazo);
        return reemplazo;
    }
    public boolean Eliminar(Libro dato) {
        NodoArbol auxiliar = raiz;
        NodoArbol padre = raiz;
        boolean esHijoIzquierdo = true;

        // 1. BUSCAR EL NODO A ELIMINAR (Y SU PADRE)
        while (auxiliar != null) {
            // ERROR CORREGIDO: El bucle debe ser mientras auxiliar no sea null.

            if (dato.getCodigo() == auxiliar.getDato().getCodigo()) {
                break; // Libro encontrado
            }

            padre = auxiliar;

            // Asumiendo que getCodigo() retorna un int o permite la comparación directa
            if (dato.getCodigo() < auxiliar.getDato().getCodigo()) {
                esHijoIzquierdo = true;
                auxiliar = auxiliar.getHijoizquiero();
            } else {
                esHijoIzquierdo = false;
                auxiliar = auxiliar.getHijoderecho();
            }
        }


        if (auxiliar == null) {
            return false; // El libro no existe en el árbol
        }


        if (auxiliar.getHijoizquiero() == null && auxiliar.getHijoderecho() == null) {
            if (auxiliar == raiz) {
                raiz = null; // Árbol vacío
            } else if (esHijoIzquierdo) {
                padre.setHijoizquiero(null); // Eliminar del padre
            } else {
                padre.setHijoderecho(null); // Eliminar del padre
            }
        }

        else if (auxiliar.getHijoderecho() == null) {
            if (auxiliar == raiz) {
                raiz = auxiliar.getHijoizquiero();
            } else if (esHijoIzquierdo) {
                padre.setHijoizquiero(auxiliar.getHijoizquiero());
            } else {
                padre.setHijoderecho(auxiliar.getHijoizquiero());
            }
        } else if (auxiliar.getHijoizquiero() == null) {
            if (auxiliar == raiz) {
                raiz = auxiliar.getHijoderecho();
            } else if (esHijoIzquierdo) {
                padre.setHijoizquiero(auxiliar.getHijoderecho());
            } else {
                padre.setHijoderecho(auxiliar.getHijoderecho()); // CORREGIDO: Debería ser getHijoderecho()
            }
        }

        else {
            NodoArbol reemplazo = ObtenerNodoReemplazar(auxiliar);

            if (auxiliar == raiz) {
                raiz = reemplazo;
            } else if (esHijoIzquierdo) {
                padre.setHijoizquiero(reemplazo);
            } else {
                padre.setHijoderecho(reemplazo);
            }

            reemplazo.setHijoizquiero(auxiliar.getHijoizquiero());
        }

        return true; // Eliminación exitosa
    }

}
