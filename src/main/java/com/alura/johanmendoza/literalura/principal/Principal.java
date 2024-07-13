package com.alura.johanmendoza.literalura.principal;

import com.alura.johanmendoza.literalura.model.*;
import com.alura.johanmendoza.literalura.services.ConsumoAPI;
import com.alura.johanmendoza.literalura.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {

    private Scanner leer = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private int opc = 0;

    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;

    public void MuestraMenu() {
        do {
            System.out.print("MENU PRINCIPAL\n" +
                    "1. Buscar por titulo del libro\n" +
                    "2. Ver todos los libros\n"+
                    "3. Ver todos los autores\n"+
                    "4. Mostrar todos los libros por idioma\n"+
                    "5. Salir\n");
            try {
                opc = leer.nextInt();
                leer.nextLine();  // Consumir el carácter de nueva línea pendiente
                switch (opc) {
                    case 1 -> buscaLibroPorTitulo();
                    case 2 -> mostrarTodosLosLibros();
                    case 3 -> mostrarTodosLosAutores();
                    case 4 -> {
                        System.out.println("Ingresa el código del idioma:");
                        String codIdioma = leer.nextLine();
                        mostrarLibrosPorIdioma(codIdioma); // Asegúrate de que este método esté definido
                    }
                    case 5 -> System.out.println("Gracias por usar la aplicacion!");
                    default -> System.out.println("Opción inválida. Por favor, intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida");
                leer.nextLine();  // Limpiar la entrada incorrecta
            }
        } while (opc != 3);
    }

    public void buscaLibroPorTitulo(){
        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        var titulo = leer.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + titulo.replace(" ", "+"));
        var datosBusqueda = convierteDatos.obtenerDatos(json, Datos.class);

        Optional<LibroDTO> libroBuscado = datosBusqueda.listaLibros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(titulo.toUpperCase()))
                .findFirst();

        if(libroBuscado.isPresent()){
            LibroDTO libroDTO = libroBuscado.get();
            Libros libro = convertirDTOaEntidad(libroDTO);
            libroService.guardarLibro(libro);
            System.out.println("Libro encontrado y guardado: " + datosBusqueda);
        } else {
            System.out.println("Libro no encontrado");
        }

        //Trabajando con estadísticas
        DoubleSummaryStatistics est = datosBusqueda.listaLibros().stream()
                .filter(d -> d.numeroDescargas() > 0)
                .collect(Collectors.summarizingDouble(LibroDTO::numeroDescargas));
        System.out.println("Cantidad Media de descargas: " + est.getAverage());
        System.out.println("Cantidad maxima de descargas: " + est.getMax());
        System.out.println("Cantidad minima de descargas: " + est.getMin());
    }

    private Libros convertirDTOaEntidad(LibroDTO libroDTO) {
        Libros libro = new Libros();
        libro.setTitulo(libroDTO.titulo());
        Autores autor = libroDTO.autores().isEmpty() ? null : obtenerOcrearAutor(libroDTO.autores().get(0));
        libro.setAutor(autor);
        libro.setIdiomas(libroDTO.idiomas());
        libro.setNumeroDescargas(libroDTO.numeroDescargas());
        return libro;
    }

    private Autores obtenerOcrearAutor(AutorDTO autorDTO) {
        return libroService.obtenerOcrearAutor(autorDTO);
    }

    @Transactional
    public void mostrarTodosLosLibros() {
        var todosLosLibros = libroService.obtenerTodosLosLibros();
        if (!todosLosLibros.isEmpty()) {
            todosLosLibros.forEach(libro -> System.out.println(libro.toString()));
        } else {
            System.out.println("No hay libros disponibles.");
        }
    }
    public void mostrarTodosLosAutores() {
        List<Autores> todosLosAutores = autorService.obtenerTodosLosAutores();

        if (!todosLosAutores.isEmpty()) {
            todosLosAutores.forEach(autor -> System.out.println(autor.toString()));
        } else {
            System.out.println("No hay autores disponibles.");
        }
    }
    public void mostrarLibrosPorIdioma(String nombreIdioma) {
        List<Libros> libros = libroService.obtenerLibrosPorIdioma(nombreIdioma);

        if (!libros.isEmpty()) {
            System.out.println("Libros en el idioma '" + nombreIdioma + "':");
            libros.forEach(libro -> System.out.println(libro.toString()));
        } else {
            System.out.println("No se encontraron libros en el idioma '" + nombreIdioma + "'.");
        }
    }
}
/*
    public void libosMasDescargados(){
         /* //Top 10 libros mas descargados
        var datos = convierteDatos.obtenerDatos(json, Datos.class);
        System.out.println(datos);


        System.out.println("Top 10 libros mas descargados");
        datos.listaLibros().stream()
                //Ordena los datos de mayor a menor
                .sorted(Comparator.comparing(DatosLibros::numeroDescargas).reversed())
                //Lo limita a top 10
                .limit(10)
                //Obtiene el titulo del libro con un mapeo
                .map(l -> l.titulo().toUpperCase())
                //Imprime el top 10
                .forEach(System.out::println);
    }
    }*/

