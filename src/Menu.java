import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static GestionB sistema;
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        sistema = new GestionB();

        // Cargar datos previos al iniciar
        System.out.println("=== SISTEMA DE GESTI0N DE BIBLIOTECA ===\n");
        System.out.println("Cargando datos del sistema...");
        sistema.Cargar_Datos();

        // Menú principal
        boolean continuar = true;
        while (continuar) {
            continuar = mostrarMenuPrincipal();
        }

        // Guardar datos antes de salir
        System.out.println("\nGuardando datos del sistema...");
        sistema.Guardar_Datos();

        System.out.println("\n¡Gracias por usar el Sistema de Biblioteca!");
        System.out.println("Sistema cerrado exitosamente.");

        sc.close();
    }
//Muestra mi menu principal y despliega opciones.
    private static boolean mostrarMenuPrincipal() {
        try {
            System.out.println("BIBLIOTECA S.A.S\n");
            System.out.println("1)--> Gestion de Libros");
            System.out.println("2)--> Gestion de Usuarios");
            System.out.println("3)--> Gestion de Prestamos y Devoluciones");
            System.out.println("4)--> Reportes y Consultas");
            System.out.println("5)--> Guardar Datos");
            System.out.println("0)--> Sair del Sistema");
            System.out.print("Seleccione una opcion valida entre el 0 y el 5 \n");

            int opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    menuGestionLibros();
                    break;
                case 2:
                    menuGestionUsuarios();
                    break;
                case 3:
                    menuPrestamosYDevoluciones();
                    break;
                case 4:
                    menuReportesYConsultas();
                    break;
                case 5:
                    sistema.Guardar_Datos();
                    System.out.println("Datos guardados exitosamente");
                    break;
                case 0:
                    System.out.println("\nCerrando el sistema...");
                    return false;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número válido entre el 0 y el 5");
            sc.nextLine(); // Limpiar buffer
        }

        return true;
    }
    /*Me controla todo el acceso de libros en el inventario*/
    private static void menuGestionLibros() {
        boolean volver = false;

        while (!volver) {
            try {
                System.out.println("1. Agregar nuevo libro");
                System.out.println("2. Eliminar libro");
                System.out.println("3. Buscar libro por código");
                System.out.println("4. Listar todos los libros");
                System.out.println("0. Volver al menú principal");
                System.out.print("Seleccione una opción: ");

                int opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        agregarLibro();
                        break;
                    case 2:
                        sistema.Eliminar_Libro();
                        break;
                    case 3:
                        buscarLibro();
                        break;
                    case 4:
                        listarLibros();
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido");
                sc.nextLine();
            }
        }
    }

    private static void agregarLibro() {
        try {
            System.out.println("\n--- AGREGAR NUEVO LIBRO ---");

            System.out.print("Título del libro: ");
            String titulo = sc.nextLine();

            System.out.print("Autor del libro: ");
            String autor = sc.nextLine();

            System.out.print("Categoría del libro: ");
            String categoria = sc.nextLine();

            System.out.print("Fecha de publicación (formato: dd/MM/yyyy): ");
            String fechaStr = sc.nextLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(fechaStr, formatter);

            sistema.Agregar_libro(titulo, autor, categoria, fecha);
            System.out.println("Libro agregado exitosamente");

        } catch (DateTimeParseException e) {
            System.out.println(" Error: Formato de fecha inválido. Use dd/MM/yyyy");
        } catch (Exception e) {
            System.out.println(" Error al agregar el libro: " + e.getMessage());
        }
    }

    private static void buscarLibro() {
        System.out.println("\n--- BUSCAR LIBRO ---");
        System.out.print("Ingrese el código del libro: ");

        try {
            int codigo = sc.nextInt();
            sc.nextLine();

            Libro libro = sistema.getArbol_de_libros().buscarLibroPorCodigo(
                    sistema.getArbol_de_libros().raiz, codigo);

            if (libro != null) {
                System.out.println("Título:       " + libro.getTitulo());
                System.out.println("Autor:        " + libro.getAutor());
                System.out.println("Categoría:    " + libro.getCategoria());
                System.out.println("Fecha:        " + libro.getFecha());
                System.out.println("Código:       " + libro.getCodigo());
                System.out.println("Disponible:   " + (libro.getDisponibilidad() ? "SI" : "NO"));

            } else {
                System.out.println(" No se encontró ningún libro con ese código");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un código válido");
            sc.nextLine();
        }
    }

    private static void listarLibros() {
        if (sistema.getArbol_de_libros().ArbolVacio()) {
            System.out.println("No hay libros registrados en el sistema");
        } else {
            sistema.getArbol_de_libros().InOrden(sistema.getArbol_de_libros().raiz);
        }
    }

    private static void menuGestionUsuarios() {
        boolean volver = false;

        while (!volver) {
            try {
                System.out.println("1. Registrar usuario en fila de espera");
                System.out.println("2. Ver fila de usuarios en espera");
                System.out.println("0. Volver al menú principal");
                System.out.print("Seleccione una opción: ");

                int opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        registrarUsuario();
                        break;
                    case 2:
                        verFilaUsuarios();
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido");
                sc.nextLine();
            }
        }
    }

    private static void registrarUsuario() {
        try {
            System.out.println("\n--- REGISTRAR USUARIO EN FILA ---");

            System.out.print("Nombre del usuario: ");
            String nombre = sc.nextLine();

            System.out.print("Número de identificación: ");
            float identificacion = sc.nextFloat();
            sc.nextLine();

            sistema.Solicitar_Prestamo(nombre, identificacion);
            System.out.println("Usuario agregado a la fila de espera exitosamente");

        } catch (InputMismatchException e) {
            System.out.println("Error: Identificación debe ser un número válido");
            sc.nextLine();
        }
    }

    private static void verFilaUsuarios() {

        if (sistema.getFila_de_usuarios().ListaVacia()) {
            System.out.println("No hay usuarios en la fila de espera");
        } else {
            sistema.getFila_de_usuarios().Mostrarlista();
        }
    }

    private static void menuPrestamosYDevoluciones() {
        boolean volver = false;

        while (!volver) {
            try {
                System.out.println("1. Atender usuario (prestamo o devolución)");
                System.out.println("2. Ver prestamos activos");
                System.out.println("0. Volver al menú principal");
                System.out.print("Seleccione una opción: ");

                int opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        sistema.Atender_Usuario();
                        break;
                    case 2:
                        verPrestamosActivos();
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido");
                sc.nextLine();
            }
        }
    }

    private static void verPrestamosActivos() {
        if (sistema.getPrestamosActivos().isEmpty()) {
            System.out.println("No hay préstamos activos en este momento");
        } else {
            int contador = 1;
            for (Prestamo p : sistema.getPrestamosActivos()) {
                System.out.println("\n--- Préstamo #" + contador + " ---");
                System.out.println("ID Préstamo:      " + p.getIDprestamo());
                System.out.println("Usuario:          " + p.getUsuario().getNombre());
                System.out.println("Libro:            " + p.getLibro().getTitulo());
                System.out.println("Código Libro:     " + p.getLibro().getCodigo());
                System.out.println("Fecha Préstamo:   " + p.getFechaPrestamo());
                System.out.println("Fecha Devolución: " + p.getFechaDevolucion());
                contador++;

                LocalDate hoy = LocalDate.now();
                LocalDate fechaDevolucion = p.getFechaDevolucion();

                // Comparar fechas
                if (fechaDevolucion.isBefore(hoy)) {
                    // Vencido - calcular días de retraso
                    int diasRetraso = calcularDiasEntre(fechaDevolucion, hoy);
                    System.out.println("Estado: VENCIDO (" + diasRetraso + " días de retraso)");
                } else if (fechaDevolucion.isEqual(hoy)) {
                    System.out.println("Estado: Vence HOY");
                } else {
                    // Vigente - calcular días restantes
                    int diasRestantes = calcularDiasEntre(hoy, fechaDevolucion);
                    System.out.println("Estado: Vigente (" + diasRestantes + " días restantes)");
                }

                contador++;

            }
        }
    }
    // Método auxiliar para calcular días entre dos fechas
    private static int calcularDiasEntre(LocalDate fechaInicio, LocalDate fechaFin) {
        int dias = 0;
        LocalDate fecha = fechaInicio;

        while (fecha.isBefore(fechaFin)) {
            dias++;
            fecha = fecha.plusDays(1);
        }

        return dias;
    }

    // ==================== MENÚ REPORTES Y CONSULTAS ====================
    private static void menuReportesYConsultas() {
        boolean volver = false;

        while (!volver) {
            try {
                System.out.println("1. Ver historial de acciones");
                System.out.println("2. Estadísticas del sistema");
                System.out.println("3. Libros disponibles");
                System.out.println("4. Libros prestados");
                System.out.println("0. Volver al menú principal");
                System.out.print("Seleccione una opción: ");

                int opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        verHistorialAcciones();
                        break;
                    case 2:
                        mostrarEstadisticas();
                        break;
                    case 3:
                        listarLibrosDisponibles();
                        break;
                    case 4:
                        listarLibrosPrestados();
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido");
                sc.nextLine();
            }
        }
    }

    private static void verHistorialAcciones() {
        if (sistema.getAcciones_de_usuares().PilaVacia()) {
            System.out.println("No hay acciones registradas");
        } else {
            System.out.println("Total de acciones: " + sistema.getAcciones_de_usuares().getTamano());
            System.out.println("\nÚltimas acciones (más reciente primero):\n");

            NodoP actual = sistema.getAcciones_de_usuares().inicio;
            int contador = 1;
            int maxMostrar = 20; // Limitar a últimas 20 acciones

            while (actual != null && contador <= maxMostrar) {
                Accion a = actual.getDato();
                System.out.println(contador + ". [" + a.getTipo() + "]");
                System.out.println("   " + a.getDetalle());
                System.out.println("   Fecha: " + a.getFecha());
                System.out.println();
                actual = actual.getSiguiente();
                contador++;
            }

            if (sistema.getAcciones_de_usuares().getTamano() > maxMostrar) {
                System.out.println("... y " + (sistema.getAcciones_de_usuares().getTamano() - maxMostrar) + " acciones más");
            }
        }
    }

    private static void mostrarEstadisticas() {
        // Contar libros totales
        int totalLibros = contarLibros(sistema.getArbol_de_libros().raiz);
        int librosDisponibles = contarLibrosDisponibles(sistema.getArbol_de_libros().raiz);
        int librosPrestados = totalLibros - librosDisponibles;

        System.out.println("Libros Totales:       " + totalLibros);
        System.out.println("Libros Disponibles:   " + librosDisponibles);
        System.out.println("Libros Prestados:     " + librosPrestados);
        System.out.println("Préstamos Activos:    " + sistema.getPrestamosActivos().size());
        System.out.println("Usuarios en Espera:   " + contarUsuariosEnFila());
        System.out.println("Acciones Registradas: " + sistema.getAcciones_de_usuares().getTamano());
        System.out.println("Último Código Libro:  " + Libro.Ultimo_codigo);

    }

    private static void listarLibrosDisponibles() {
        listarLibrosPorDisponibilidad(sistema.getArbol_de_libros().raiz, true);
    }

    private static void listarLibrosPrestados() {
        listarLibrosPorDisponibilidad(sistema.getArbol_de_libros().raiz, false);
    }


    private static int contarLibros(NodoArbol nodo) {
        if (nodo == null) return 0;
        return 1 + contarLibros(nodo.getHijoizquiero()) + contarLibros(nodo.getHijoderecho());
    }

    private static int contarLibrosDisponibles(NodoArbol nodo) {
        if (nodo == null) return 0;

        int count = nodo.getDato().getDisponibilidad() ? 1 : 0;
        return count + contarLibrosDisponibles(nodo.getHijoizquiero()) +
                contarLibrosDisponibles(nodo.getHijoderecho());
    }

    private static int contarUsuariosEnFila() {
        int count = 0;
        NodoL actual = sistema.getFila_de_usuarios().inicio;
        while (actual != null) {
            count++;
            actual = actual.getSiguiente();
        }
        return count;
    }

    private static void listarLibrosPorDisponibilidad(NodoArbol nodo, boolean disponible) {
        if (nodo != null) {
            listarLibrosPorDisponibilidad(nodo.getHijoizquiero(), disponible);

            if (nodo.getDato().getDisponibilidad() == disponible) {
                Libro l = nodo.getDato();
                System.out.println("• " + l.getTitulo() + " - " + l.getAutor() +
                        " (Código: " + l.getCodigo() + ")");
            }

            listarLibrosPorDisponibilidad(nodo.getHijoderecho(), disponible);
        }
    }
}
