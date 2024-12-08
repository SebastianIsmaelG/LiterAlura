package com.sebastianismaelg.literalura.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastianismaelg.literalura.models.BookRecord;
import com.sebastianismaelg.literalura.services.ConvertData;
import com.sebastianismaelg.literalura.services.RequestAPI;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Scanner;

@Component
public class Main {
    //variables API

    private final String URL = "https://gutendex.com/books";
    Scanner write = new Scanner(System.in);
    RequestAPI requestAPI = new RequestAPI();
    ConvertData convertData = new ConvertData();
    public void menu(){
        //Interface Loop
        var response = true;
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
                    
                    
                    0- Salir
                    -----------------------------------------
                    """);
            option = write.nextLine();
            //write.nextLine();
            if (option.length() == 1 && Character.isDigit(option.charAt(0))){
                //numeroIngresado = Integer.parseInt(option);

                switch (option){
                    case "1":
                        try {
                            findByTitle();
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
                            ListByLanguage();
                        }catch (NullPointerException e){
                            break;
                        }
                        break;
                    case "0":
                        System.out.println("Saliendo de la aplicacion");
                        response = false;
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
            }else {
                System.out.println("Ingrese una opcion valida");

            }
        }

    }

    private void ListByLanguage() {
    }

    private void findAliveByYear() {
    }

    private void findRegiAuthors() {
    }

    private void findRegiBooks() {
    }

    private BookRecord BookData(){
        System.out.println("please enter the book title");
        String nameEnter = write.nextLine().toLowerCase(Locale.ROOT);
        var response = requestAPI.obtainData(URL+"?search="+nameEnter.replace("","+"));
        BookRecord data = convertData.obtainData(response, BookRecord.class);
        if (data.title()!= null){
            System.out.println("Data saved to : "+data.title());
        }else {
            System.out.println("The search result for "+nameEnter+" did not return any results, try again.");
        }
        return data;
    }
    private void  findByTitle() {
        BookRecord dataRecord = BookData();


    }
}
