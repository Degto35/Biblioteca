import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestionB {
    Scanner sc = new Scanner(System.in);
    private Arbolb arbol_de_libros;
    private Lista fila_de_usuarios;
    private Pila acciones_de_usuares;
    private List<Prestamo> prestamosActivos = new ArrayList<>();
    public GestionB() {
        this.arbol_de_libros = new Arbolb();
        this.fila_de_usuarios = new Lista();
        this.acciones_de_usuares = new Pila();
        this.prestamosActivos = new ArrayList<>();
    }
    public Arbolb getArbol_de_libros(){
        return arbol_de_libros;
    }
    public Lista getFila_de_usuarios(){
        return fila_de_usuarios;
    }

    public Pila getAcciones_de_usuares() {
        return acciones_de_usuares;
    }

    public List<Prestamo> getPrestamosActivos() {
        return prestamosActivos;
    }

    public void Agregar_libro(String Titulo, String Autor, String Categoria, LocalDate Fecha){
            int codigo = Libro.Ultimo_codigo+1;
            Libro.Ultimo_codigo = codigo;
            Libro libro = new Libro(Titulo, Autor, Categoria, Fecha, codigo);
            this.arbol_de_libros.AgregarNodo(libro);
            Accion Agregar_libro = new Accion("Agregar libro",String.format("Se guargo el libro %s Autor %s categoria %s Codigo %s", Titulo, Autor, Categoria, codigo));
            this.acciones_de_usuares.Apilar(Agregar_libro);
        }

    public void Solicitar_Prestamo(String Nombre, float Identificacion){
        Usuario usuario = new Usuario(Nombre, Identificacion);
        this.fila_de_usuarios.AgregarElementoFinal(usuario);
        Accion Agregar_Usuario = new Accion("Agregar usuario",String.format("Se registra el usuario %s Identifocacion %s",Nombre, Identificacion));
        this.acciones_de_usuares.Apilar(Agregar_Usuario);
    }
    public String generarUUID(){
        boolean unica = true;
        String id;
        do{
            id = UUID.randomUUID().toString();
            unica = true;
            for (Prestamo prestamo : this.prestamosActivos){
                if (id.equals(prestamo.getIDprestamo())){
                    unica = false;
                }
            }
        }while (!unica);
        return  id;
    }
    // Método principal para atender usuario
    public void Atender_Usuario() {
        if (!this.fila_de_usuarios.ListaVacia()) {
            Usuario usuario = this.fila_de_usuarios.Eliminar_inicio_Lista();
            Accion atenderUsuario = new Accion("Atender Usuario",
                    String.format("Se atiende al usuario %s", usuario.getNombre()));
            this.acciones_de_usuares.Apilar(atenderUsuario);

            System.out.println("¿Qué operación desea realizar?");
            System.out.println("1 - Realizar un préstamo");
            System.out.println("2 - Realizar una devolución");
            int opcion = sc.nextInt();

            if (opcion == 1) {
                Realizar_Prestamo(usuario);
            } else if (opcion == 2) {
                Registros_Devolucion(usuario);
            } else {
                System.out.println("Opción no válida");
            }
        } else {
            System.out.println("No hay usuarios en la fila de espera");
        }
    }

    // Reemplazar el método Realizar_Prestamo en GestionB.java

    public void Realizar_Prestamo(Usuario usuario) {
        System.out.println("El usuario atendido es: "+usuario.getNombre()+" "+"y su identificacion es: "+usuario.getIdentidad());
        boolean cancelarPrestamo = false;

        while (!cancelarPrestamo) {
            this.arbol_de_libros.InOrden(this.arbol_de_libros.raiz);
            System.out.println("\nIngrese el código del libro que quiere prestar:");
            int codigo = sc.nextInt();
            sc.nextLine(); // IMPORTANTE: Limpiar buffer

            Libro libro = this.arbol_de_libros.buscarLibroPorCodigo(this.arbol_de_libros.raiz, codigo);

            // Validar que el libro existe
            if (libro == null) {
                System.out.println("⚠ No se encontró ningún libro con ese código");
                System.out.println("¿Desea intentar con otro código?");
                System.out.println("1 - Sí, intentar de nuevo");
                System.out.println("2 - No, cancelar");
                int opcion = sc.nextInt();
                sc.nextLine();

                if (opcion != 1) {
                    cancelarPrestamo = true;
                }
                continue; // Volver al inicio del while
            }

            Accion buscarLibro = new Accion("Buscar Libro",
                    String.format("El usuario %s solicitó el libro de código %s y nombre %s",
                            usuario.getNombre(), codigo, libro.getTitulo()));
            this.acciones_de_usuares.Apilar(buscarLibro);

            System.out.println("El libro que buscas es: " + libro.getTitulo() + " - " +
                    (libro.getDisponibilidad() ? "Disponible" : "No disponible"));

            if (libro.getDisponibilidad()) {
                System.out.println("\nIngrese el número de días que desea prestarlo: ");
                int dias = sc.nextInt();
                sc.nextLine(); // IMPORTANTE: Limpiar buffer

                LocalDate fechaActual = LocalDate.now();
                String id = generarUUID();
                Prestamo p = new Prestamo(fechaActual, libro, usuario, id);
                p.setfechaDevolucion(dias);

                this.prestamosActivos.add(p);
                this.arbol_de_libros.actualizarDispoibilidad(this.arbol_de_libros.raiz, codigo, false);

                System.out.println(String.format("\n✓ El libro fue prestado correctamente"));
                System.out.println(String.format("  Código de préstamo: %s", p.getIDprestamo()));
                System.out.println(String.format("  Fecha de devolución: %s", p.getFechaDevolucion()));

                Accion prestamo = new Accion("Préstamo de libro",
                        String.format("Se realiza préstamo del libro %s al usuario %s bajo el ID de préstamo %s para devolución el %s",
                                libro.getTitulo(), usuario.getNombre(), p.getIDprestamo(), p.getFechaDevolucion().toString()));
                this.acciones_de_usuares.Apilar(prestamo);

                cancelarPrestamo = true;
            } else {
                System.out.println("\nLo sentimos, el libro que estás solicitando NO se encuentra disponible");
                System.out.println("Marca la opción que deseas hacer:");
                System.out.println("1 - Prestar otro libro");
                System.out.println("2 - No prestar ningún libro");

                int opcion = sc.nextInt();
                sc.nextLine(); // IMPORTANTE: Limpiar buffer
                cancelarPrestamo = (opcion != 1);

                if (cancelarPrestamo) {
                    Accion cancelarPrestamoAccion = new Accion("Cancelar préstamo de libro",
                            String.format("Por motivos de disponibilidad el libro %s no se pudo prestar y el usuario %s decidió no prestar ningún libro",
                                    libro.getTitulo(), usuario.getNombre()));
                    this.acciones_de_usuares.Apilar(cancelarPrestamoAccion);
                }
            }
        }
    }
    // Método para eliminar un libro del sistema
    public void Eliminar_Libro() {
        System.out.println("\n=== ELIMINAR LIBRO ===");

        // Mostrar todos los libros disponibles
        System.out.println("\nLibros en el sistema:");
        if (this.arbol_de_libros.ArbolVacio()) {
            System.out.println("No hay libros en el sistema.");
            return;
        }

        this.arbol_de_libros.InOrden(this.arbol_de_libros.raiz);

        System.out.println("\nIngrese el código del libro que desea eliminar:");
        int codigo = sc.nextInt();

        // Buscar el libro
        Libro libro = this.arbol_de_libros.buscarLibroPorCodigo(this.arbol_de_libros.raiz, codigo);

        // Validar que el libro existe
        if (libro == null) {
            System.out.println("Error: No se encontró un libro con el código " + codigo);
            Accion buscarLibroFallido = new Accion("Buscar Libro para Eliminar",
                    String.format("Se intentó buscar el libro con código %d para eliminarlo, pero no existe", codigo));
            this.acciones_de_usuares.Apilar(buscarLibroFallido);
            return;
        }

        // Verificar si el libro está en préstamo
        if (verificarLibroEnPrestamo(codigo)) {
            System.out.println("Error: No se puede eliminar el libro '" + libro.getTitulo() +
                    "' porque actualmente está en préstamo.");
            System.out.println("Debe esperar a que sea devuelto para poder eliminarlo.");

            Accion eliminarLibroFallido = new Accion("Eliminar Libro - Fallido",
                    String.format("No se pudo eliminar el libro '%s' (código %d) porque está en préstamo activo",
                            libro.getTitulo(), codigo));
            this.acciones_de_usuares.Apilar(eliminarLibroFallido);
            return;
        }

        // Confirmar eliminación
        System.out.println("\n¿Está seguro que desea eliminar el siguiente libro?");
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Autor: " + libro.getAutor());
        System.out.println("Categoría: " + libro.getCategoria());
        System.out.println("Código: " + libro.getCodigo());
        System.out.println("\n1 - Sí, eliminar");
        System.out.println("2 - No, cancelar");

        int confirmacion = sc.nextInt();

        if (confirmacion == 1) {
            // Intentar eliminar el libro del árbol
            boolean eliminado = this.arbol_de_libros.Eliminar(libro);

            if (eliminado) {
                System.out.println("El libro '" + libro.getTitulo() + "' ha sido eliminado exitosamente.");

                Accion eliminarLibro = new Accion("Eliminar Libro",
                        String.format("Se eliminó el libro '%s' - Autor: %s - Código: %d del sistema",
                                libro.getTitulo(), libro.getAutor(), codigo));
                this.acciones_de_usuares.Apilar(eliminarLibro);
            } else {
                System.out.println("Error: No se pudo eliminar el libro del sistema.");

                Accion eliminarLibroError = new Accion("Eliminar Libro - Error",
                        String.format("Error al intentar eliminar el libro con código %d", codigo));
                this.acciones_de_usuares.Apilar(eliminarLibroError);
            }
        } else {
            System.out.println("Eliminación cancelada.");

            Accion cancelarEliminacion = new Accion("Cancelar Eliminación",
                    String.format("Se canceló la eliminación del libro '%s' (código %d)",
                            libro.getTitulo(), codigo));
            this.acciones_de_usuares.Apilar(cancelarEliminacion);
        }
    }

    // Método auxiliar para verificar si un libro está en préstamo
    private boolean verificarLibroEnPrestamo(int codigoLibro) {
        for (Prestamo prestamo : this.prestamosActivos) {
            if (prestamo.getLibro().getCodigo() == codigoLibro) {
                return true;
            }
        }
        return false;
    }

    // Método para registrar devolución
    public void Registros_Devolucion(Usuario usuario) {
        System.out.println("El usuario atendido es: "+usuario.getNombre()+" "+"y su identificacion es: "+usuario.getIdentidad());
        System.out.println("¿Desea hacer la devolución por código del libro (marque 1) o por código del préstamo (marque 2)?");
        boolean esPorCodigoPrestamo = sc.nextInt() != 1;

        if (esPorCodigoPrestamo) {
            Devolver_Por_Codigo_Prestamo(usuario);
        } else {
            Devolver_Por_Codigo_Libro(usuario);
        }
    }

    // Método auxiliar para devolución por código de préstamo
    private void Devolver_Por_Codigo_Prestamo(Usuario usuario) {
        int intentar = 1;

        while (intentar == 1) {
            System.out.println("Ingrese el código del préstamo sin comas ni espacios:");
            String codigo = sc.next();
            int ind = -1;

            for (Prestamo p : this.prestamosActivos) {
                if (p.getIDprestamo().equals(codigo)) {
                    ind = this.prestamosActivos.indexOf(p);
                    Accion buscarPrestamo = new Accion("Buscar Préstamo",
                            String.format("Se realiza búsqueda de un préstamo activo con el código de préstamo %s", codigo));
                    this.acciones_de_usuares.Apilar(buscarPrestamo);
                    break;
                }
            }

            if (ind != -1) {
                Procesar_Devolucion(ind, usuario);
                break;
            } else {
                System.out.println("No se encontró el préstamo con ese código ingresado");
                System.out.println("Marque con el número de la opción:");
                System.out.println("1 - Volver a intentar");
                System.out.println("2 - Regresar al menú anterior");
                intentar = sc.nextInt();
            }
        }
    }

    // Método auxiliar para devolución por código de libro
    private void Devolver_Por_Codigo_Libro(Usuario usuario) {
        int intentar = 1;

        while (intentar == 1) {
            System.out.println("Ingrese el código del libro sin comas ni espacios:");
            int codigo = sc.nextInt();
            int ind = -1;

            for (Prestamo p : this.prestamosActivos) {
                if (p.getLibro().getCodigo() == codigo) {
                    ind = this.prestamosActivos.indexOf(p);
                    Accion buscarLibro = new Accion("Buscar libro",
                            String.format("Se realiza búsqueda de un libro en préstamo activo con el código de libro %s", codigo));
                    this.acciones_de_usuares.Apilar(buscarLibro);
                    break;
                }
            }

            if (ind != -1) {
                Procesar_Devolucion(ind, usuario);
                break;
            } else {
                System.out.println("No se encontró el préstamo con ese código de libro ingresado");
                System.out.println("Marque con el número de la opción:");
                System.out.println("1 - Volver a intentar");
                System.out.println("2 - Regresar al menú anterior");
                intentar = sc.nextInt();
            }
        }
    }

    // Método auxiliar para procesar la devolución
    private void Procesar_Devolucion(int indice, Usuario usuario) {
        Prestamo p = this.prestamosActivos.get(indice);
        this.arbol_de_libros.actualizarDispoibilidad(this.arbol_de_libros.raiz, p.getLibro().getCodigo(), true);
        this.prestamosActivos.remove(indice);

        Accion actualizarDisponibilidad = new Accion("Actualizar disponibilidad",
                String.format("Se buscó el libro con código %s con el fin de actualizar la disponibilidad",
                        p.getLibro().getCodigo()));
        this.acciones_de_usuares.Apilar(actualizarDisponibilidad);

        System.out.println(String.format("Se realizó la devolución del libro %s con código %s correctamente",
                p.getLibro().getTitulo(), p.getLibro().getCodigo()));

        Accion devolucionLibro = new Accion("Devolución de libro",
                String.format("Se realizó la devolución del libro %s - código %s, por parte del usuario %s",
                        p.getLibro().getTitulo(), p.getLibro().getCodigo(), usuario.getNombre()));
        this.acciones_de_usuares.Apilar(devolucionLibro);
    }
    public void Guardar_Datos() {
        // Guardar libros en el árbol
        this.arbol_de_libros.guardarEnArchivo("libros.txt");

        // Guardar préstamos activos
        guardarPrestamosActivos("prestamos.txt");

        // Guardar fila de usuarios en espera
        guardarFilaUsuarios("usuarios_fila.txt");

        // Guardar acciones (historial)
        guardarAcciones("acciones.txt");

        // Guardar el último código usado
        guardarUltimoCodigo("config.txt");

        System.out.println("Todos los datos han sido guardados exitosamente.");
    }

    // Método para cargar todos los datos del sistema
    public void Cargar_Datos() {
        // Cargar libros y actualizar el último código
        this.arbol_de_libros.cargarDesdeArchivo("libros.txt");

        // Cargar configuración (último código)
        cargarUltimoCodigo("config.txt");

        // Cargar préstamos activos
        cargarPrestamosActivos("prestamos.txt");

        // Cargar fila de usuarios
        cargarFilaUsuarios("usuarios_fila.txt");

        // Cargar acciones (historial)
        cargarAcciones("acciones.txt");

        System.out.println("Todos los datos han sido cargados exitosamente.");
    }

    private void guardarPrestamosActivos(String nombreArchivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

            for (Prestamo prestamo : this.prestamosActivos) {
                String linea = String.format("%s,%d,%s,%.0f,%s,%s",
                        prestamo.getIDprestamo(),
                        prestamo.getLibro().getCodigo(),
                        prestamo.getUsuario().getNombre(),
                        prestamo.getUsuario().getIdentidad(),
                        prestamo.getFechaPrestamo().format(formatter),
                        prestamo.getFechaDevolucion().format(formatter)
                );
                pw.println(linea);
            }
            System.out.println("Préstamos guardados exitosamente en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar préstamos: " + e.getMessage());
        }
    }

    private void guardarFilaUsuarios(String nombreArchivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            NodoL actual = this.fila_de_usuarios.inicio;

            while (actual != null) {
                String linea = String.format("%s,%.0f",
                        actual.getUsuario().getNombre(),
                        actual.getUsuario().getIdentidad()
                );
                pw.println(linea);
                actual = actual.getSiguiente();
            }
            System.out.println("Fila de usuarios guardada exitosamente en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar fila de usuarios: " + e.getMessage());
        }
    }

    private void guardarAcciones(String nombreArchivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            Pila pilaTemp = new Pila();
            NodoP actual = this.acciones_de_usuares.inicio;

            while (actual != null) {
                Accion accion = actual.getDato();
                String linea = String.format("%s|%s|%s",
                        accion.getTipo(),
                        accion.getDetalle(),
                        accion.getFecha().format(formatter)
                );
                pw.println(linea);
                actual = actual.getSiguiente();
            }
            System.out.println("Acciones guardadas exitosamente en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar acciones: " + e.getMessage());
        }
    }

    private void guardarUltimoCodigo(String nombreArchivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            pw.println(Libro.Ultimo_codigo);
            System.out.println("Configuración guardada exitosamente en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar configuración: " + e.getMessage());
        }
    }

    private void cargarPrestamosActivos(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("No existe archivo de préstamos previos.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length == 6) {
                    String idPrestamo = datos[0];
                    int codigoLibro = Integer.parseInt(datos[1]);
                    String nombreUsuario = datos[2];
                    float identificacionUsuario = Float.parseFloat(datos[3]);
                    LocalDate fechaPrestamo = LocalDate.parse(datos[4], formatter);
                    LocalDate fechaDevolucion = LocalDate.parse(datos[5], formatter);

                    Libro libro = this.arbol_de_libros.buscarLibroPorCodigo(
                            this.arbol_de_libros.raiz, codigoLibro);

                    if (libro != null) {

                        Usuario usuario = new Usuario(nombreUsuario, identificacionUsuario);

                        Prestamo prestamo = new Prestamo(fechaPrestamo, libro, usuario, idPrestamo);
                        prestamo.setFechaDevolucion(fechaDevolucion);

                        this.prestamosActivos.add(prestamo);
                    }
                }
            }
            System.out.println("Préstamos cargados exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al cargar préstamos: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error de formato en archivo de préstamos: " + e.getMessage());
        }
    }

    private void cargarFilaUsuarios(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("No existe archivo de usuarios en fila.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length == 2) {
                    String nombre = datos[0];
                    float identificacion = Float.parseFloat(datos[1]);

                    Usuario usuario = new Usuario(nombre, identificacion);
                    this.fila_de_usuarios.AgregarElementoFinal(usuario);
                }
            }
            System.out.println("Fila de usuarios cargada exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al cargar fila de usuarios: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error de formato en archivo de usuarios: " + e.getMessage());
        }
    }

    private void cargarAcciones(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("No existe archivo de acciones previas.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            List<String> lineas = new ArrayList<>();
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }

            for (int i = lineas.size() - 1; i >= 0; i--) {
                String[] datos = lineas.get(i).split("\\|");

                if (datos.length == 3) {
                    String tipo = datos[0];
                    String detalle = datos[1];
                    LocalDateTime fecha = LocalDateTime.parse(datos[2], formatter) ;

                    Accion accion = new Accion(tipo, detalle, fecha);
                    this.acciones_de_usuares.Apilar(accion);
                }
            }
            System.out.println("Acciones cargadas exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al cargar acciones: " + e.getMessage());
        }
    }

    private void cargarUltimoCodigo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("No existe archivo de configuración. Se usará código inicial 0.");
            // Actualizar desde los libros cargados
            actualizarUltimoCodigoDesdeArbol();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea = br.readLine();
            if (linea != null && !linea.trim().isEmpty()) {
                Libro.Ultimo_codigo = Integer.parseInt(linea.trim());
            }
            System.out.println("Configuración cargada. Último código: " + Libro.Ultimo_codigo);

            actualizarUltimoCodigoDesdeArbol();
        } catch (IOException e) {
            System.err.println("Error al cargar configuración: " + e.getMessage());
            actualizarUltimoCodigoDesdeArbol();
        } catch (NumberFormatException e) {
            System.err.println("Error de formato en archivo de configuración: " + e.getMessage());
            actualizarUltimoCodigoDesdeArbol();
        }
    }

    private void actualizarUltimoCodigoDesdeArbol() {
        int maxCodigo = encontrarMaximoCodigo(this.arbol_de_libros.raiz);
        if (maxCodigo > Libro.Ultimo_codigo) {
            Libro.Ultimo_codigo = maxCodigo;
            System.out.println("Último código actualizado desde el árbol: " + Libro.Ultimo_codigo);
        }
    }

    private int encontrarMaximoCodigo(NodoArbol nodo) {
        if (nodo == null) {
            return 0;
        }

        int codigoActual = nodo.getDato().getCodigo();
        int maxIzquierdo = encontrarMaximoCodigo(nodo.getHijoizquiero());
        int maxDerecho = encontrarMaximoCodigo(nodo.getHijoderecho());

        return Math.max(codigoActual, Math.max(maxIzquierdo, maxDerecho));
    }

}
