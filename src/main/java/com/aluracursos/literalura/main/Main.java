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
                    5 - List books by language
                    6 - List Top 10 most downloaded books
                    
                    
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
                    case "6":
                        try {
                            findTop10();
                        }catch (NullPointerException e){
                            break;
                        }
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

    private void findTop10() {
        try {
            List<Book> topBooks = booksRepository.findTop10ByOrderByDownloadsDesc();
            if (!topBooks.isEmpty()){
                topBooks.forEach(b -> {
                    System.out.println("""
                            Title: %s
                            Author: %s
                            Language: %s
                            Downloads: %d
                        """.formatted(
                            b.getTitle(), b.getAutor(), b.getLanguage(), b.getDownloads()
                    ));
                });
            }else {
                System.out.println("No books found, try option 1 first");
            }
        } catch (Exception e) {
            System.out.println("ErrorTopfind10");
            throw new RuntimeException(e);
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


            Book book = new Book(booksData.results().get(0));
            Author author = new Author(booksData.results().get(0).autorList().get(0));

            //Books has a foreing key of authors id so author must be unique
            Author existingAuthor = authorRepository.findByName(author.getName());
            if (existingAuthor != null) {
                book.setAuthor(existingAuthor);
            } else {
                authorRepository.save(author);
                book.setAuthor(author);
            }

            boolean exists = booksRepository.existsByTitulo(book.getTitle());
            if (!exists) {
                booksRepository.save(book);
                System.out.println("Data Saved to : " + book.getTitle() + ", in DataBase");
            } else {
                System.out.println("Book's data already exists, check option 2");
            }

        } else {
            System.out.println("No books found with " + nameEnter.replace("%20"," ") + ", try again.");
        }
    }



    private void findRegiBooks() {
        try {
            books = booksRepository.findAll();
            if (!books.isEmpty()){
                System.out.println(books.size()+" Results find : ");
                books.stream().forEach(b -> {
                    System.out.println("""    
                        Book Title: %s
                        Author: %s
                        Language: %s
                        Total Downloads: %s
                    """.formatted(b.getTitle(), b.getAutor(), b.getLanguage(), b.getDownloads().toString()));
                });
            }else {
                System.out.println("No books find, try option 1 first");
            }
        } catch (Exception e) {
            System.out.println("Error in findRegiBooks");
            throw new RuntimeException(e);
        }

    }

    private void findRegiAuthors() {
        try {
            authors = authorRepository.findAll();
            if (!authors.isEmpty()){
                authors.stream().forEach(a -> {
                    System.out.println("""
                        Author: %s
                        Birth Date: %s
                        Death Date: %s
                    """.formatted(a.getName(), a.getBirth_date().toString(), a.getDeath_date().toString()));
                });
            }else {
                System.out.println("No authors find,try option 1 first");
            }
        } catch (Exception e) {
            System.out.println("Error in fiRegiAuthors");
            throw new RuntimeException(e);
        }


    }

    public void findAliveByYear() {
       try {
           System.out.println("From what year you want to search :");
           var inputYear = scanner.nextInt();
           scanner.nextLine();

           List<Author> authors = authorRepository.authorByDate(inputYear);
           if (!authors.isEmpty()){
               System.out.println(authors.size()+" Results find : ");
               authors.forEach( a -> {
                   System.out.println("""
                    Name Author: %s
                    Birth Year: %s
                    Death Year: %s
                    """.formatted(a.getName(),a.getBirth_date().toString(),a.getDeath_date().toString()));
               });
           }else {
               System.out.println("No authors find, try another date");
           }

       } catch (Exception e) {
           System.out.println("Error findAlivebyYear");
           throw new RuntimeException(e);
       }
    }


    private void findByLanguage() {
        System.out.println("""
                Which Language do you want to search?
                1 - En (Ingles)
                2 - Es (Español)
                """);

        try {
            var optionLanguage = scanner.nextLine();
            //aa
            if (optionLanguage.length() == 1 && Character.isDigit(optionLanguage.charAt(0))){

                switch (optionLanguage) {
                    case "1":
                        try {
                            books=booksRepository.findByLanguage("en");
                            if (books.isEmpty()){
                                System.out.println("No books found, try option 1 first");
                                break;
                            }else {
                                books.stream().forEach(b -> {
                                        System.out.println("""    
                                            Title: %s
                                            Author: %s
                                            Language: %s
                                            Downloads: %s
                                        """.formatted(b.getTitle(), b.getAutor(),b.getLanguage(), b.getDownloads().toString()));
                                });
                            }
                            break;
                        } catch (Exception e) {
                            throw new RuntimeException(e);

                        }
                    case "2":
                        try {
                            books=booksRepository.findByLanguage("es");
                            if (books.isEmpty()){
                                System.out.println("No books found, try option 1 first");
                                break;
                            }else {
                                books.stream().forEach(b -> {
                                    System.out.println("""    
                                            Title: %s
                                            Author: %s
                                            Language: %s
                                            Downloads: %s
                                        """.formatted(b.getTitle(), b.getAutor(),b.getLanguage(), b.getDownloads().toString()));
                                });
                            }
                            break;
                        } catch (Exception e) {
                            throw new RuntimeException(e);

                        }
                    default:
                        System.out.println("invalid choice, try again");
                }
            }else {
                System.out.println("invalid choice, try again");

            }



        } catch (Exception e){
            System.out.println("Error findByLanguage");
        }
    }
}
