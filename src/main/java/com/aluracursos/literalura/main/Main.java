package com.aluracursos.literalura.main;

import com.aluracursos.literalura.models.Author;
import com.aluracursos.literalura.models.BooksData;
import com.aluracursos.literalura.models.Book;
import com.aluracursos.literalura.repository.AuthorRepository;
import com.aluracursos.literalura.repository.BooksRepository;
import com.aluracursos.literalura.services.ConvertData;
import com.aluracursos.literalura.services.RequestAPI;

import java.util.List;
import java.util.Scanner;

public class Main {
    private final RequestAPI requestAPI = new RequestAPI();
    private final Scanner scanner = new Scanner(System.in);
    private final String urlBase ="https://gutendex.com/books/";
    private final ConvertData convertData = new ConvertData();
    private final BooksRepository booksRepository;
    private final AuthorRepository authorRepository;
    private List<Book> books;
    private List<Author> authors;
    private String nameEnter;

    public Main(BooksRepository booksRepository, AuthorRepository authorRepository) {
        this.booksRepository = booksRepository;
        this.authorRepository = authorRepository;
    }

    public void mainMenu(){

        boolean response = true;
        String option;

        System.out.println("Welcome to LiterAlura,select an option to continue:");

        while (response){
            System.out.println("""
                    -----------------------------------------
                    1 - Find books by title
                    2 - List of registered books
                    3 - List of registered authors
                    4 - List of authors alive by year
                    5 - List book by language
                    
                    
                    0- Exit
                    -----------------------------------------
                    """);

            option = scanner.nextLine();

            if (option.length() == 1 && Character.isDigit(option.charAt(0))){


                switch (option){
                    case "1":
                        try {
                            findBook();
                        }catch (NullPointerException e){
                            break;
                        }
                        break;
                    case "2":
                        try {
                            findRegiBooks();
                        }catch (NullPointerException e){
                            break;
                        }
                        break;
                    case "3":
                        try {
                            findRegiAuthors();
                        }catch (NullPointerException e){
                            break;
                        }
                        break;
                    case "4":
                        try {
                            findAliveByYear();
                        }catch (NullPointerException e){
                            break;
                        }
                        break;
                    case "5":
                        try {
                            findByLanguage();
                        }catch (NullPointerException e){
                            break;
                        }
                        break;
                    case "0":
                        System.out.println("Exit");
                        response = false;
                        break;
                    default:
                        System.out.println("invalid choice, try again");
                        break;
                }
            }else {
                System.out.println("invalid choice, try again");

            }
        }


    }


    private BooksData getBooksData() {
        System.out.println("please enter the book title");
        nameEnter = scanner.nextLine().toLowerCase().replace(" ","%20");
        var json = requestAPI.getData(urlBase + "?search=" + nameEnter);

        BooksData booksData = convertData.obtainData(json, BooksData.class);
        return booksData;

    }

    private void findBook() {
        BooksData booksData = getBooksData();

        if (booksData != null && booksData.results() != null && !booksData.results().isEmpty()) {
            System.out.println("Book found");

            // Crear el Book a partir de los datos obtenidos
            Book book = new Book(booksData.results().get(0));

            // Crear el Author a partir de los datos obtenidos
            Author author = new Author(booksData.results().get(0).autorList().get(0));

            // Verificar si el autor ya existe
            Author existingAuthor = authorRepository.findByName(author.getName());
            if (existingAuthor != null) {
                // Si el autor ya existe, asociarlo con el libro
                book.setAuthor(existingAuthor);
                System.out.println("Autor existe");
            } else {
                // Si el autor no existe, guardarlo
                authorRepository.save(author);
                book.setAuthor(author); // Asignar el nuevo autor al libro
            }

            // Verificar si el libro ya existe
            boolean exists = booksRepository.existsByTitulo(book.getTitle());
            if (!exists) {
                // Guardar el libro en la base de datos
                booksRepository.save(book);
                System.out.println("Data Saved to : " + book.getTitle() + ", in DataBase");
            } else {
                System.out.println("Book's data already exists, check option 2");
            }

        } else {
            System.out.println("No books found with " + nameEnter + ", try again.");
        }
    }



    private void findRegiBooks() {
        books = booksRepository.findAll();
        books.stream().forEach(l -> {
            System.out.println("""    
                        Titulo: %s
                        Author: %s
                        Lenguaje: %s
                        Descargas: %s
                    """.formatted(l.getTitle(),
                    l.getAutor(),
                    l.getLanguage(),
                    l.getDownloads().toString()));
        });
    }

    // Trae todos los autores de los libros consultados en la BD
    private void findRegiAuthors() {
        authors = authorRepository.findAll();
        authors.stream().forEach(a -> {
            System.out.println("""
                        Autor: %s
                        Año de nacimiento: %s
                        Año de defuncion: %s
                    """.formatted(a.getName(),
                    a.getBirth_date().toString(),
                    a.getDeath_date().toString()));
        });
    }

    // Trae a los autores apartir de cierto año
    public void findAliveByYear()
    {
        System.out.println("Ingresa el año a partir del cual buscar:");
        var anoBusqueda = scanner.nextInt();
        scanner.nextLine();

        List<Author> authors = authorRepository.authorByDate(anoBusqueda);
        authors.forEach( a -> {
            System.out.println("""
                    Nombre: %s
                    Fecha de nacimiento: %s
                    Fecha de defuncion: %s
                    """.formatted(a.getName(),a.getBirth_date().toString(),a.getDeath_date().toString()));
        });
    }


    private void findByLanguage()
    {
        System.out.println("""
                ****************************************************************    
                    Selcciona el lenguaje de los libros que deseas consultar
                ****************************************************************
                1 - En (Ingles)
                2 - Es (Español)
                """);

        try {

            var opcion2 = scanner.nextInt();
            scanner.nextLine();

            switch (opcion2) {
                case 1:
                    books = booksRepository.findByLanguage("en");
                    break;
                case 2:
                    books = booksRepository.findByLanguage("es");
                    break;

                default:
                    System.out.println("Ingresa una opcion valida");
            }

            books.stream().forEach(l -> {
                System.out.println("""    
                        Titulo: %s
                        Author: %s
                        Lenguaje: %s
                        Descargas: %s
                    """.formatted(l.getTitle(),
                        l.getAutor(),
                        l.getLanguage(),
                        l.getDownloads().toString()));
            });

        } catch (Exception e){
            System.out.println("Ingresa un valor valido");
        }
    }
}
