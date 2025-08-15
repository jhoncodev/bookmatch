package com.jhoncodev.bookmatch.main;

import com.jhoncodev.bookmatch.model.Author;
import com.jhoncodev.bookmatch.model.Book;
import com.jhoncodev.bookmatch.model.DataBook;
import com.jhoncodev.bookmatch.model.DataResult;
import com.jhoncodev.bookmatch.repository.IBookRepository;
import com.jhoncodev.bookmatch.service.ConvertData;
import com.jhoncodev.bookmatch.service.UseAPI;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private UseAPI useAPI = new UseAPI();
    private ConvertData convertData = new ConvertData();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private IBookRepository repository;
    public Main(IBookRepository repository) {
        this.repository = repository;
    }

    public void showMenu(){
        int option = 1;
        while (option != 0) {
            System.out.println("""
                    1. Search book by title
                    2. List registered books
                    3. List registered authors
                    4. List living authors in a given year
                    5. List books by language
                    6. Books statistics
                    0. Exit
                    
                    Enter an option:
                    """);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 0:System.out.println("Bye!");break;
                case 1:
                    searchBookTitle();
                    break;
                case 2:
                    listAllBooks();
                    break;
                case 3:
                    listAllAuthors();
                    break;
                case 4:
                    listAuthorsByYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 6:
                    booksStadistics();
                    break;
                default:System.out.println("Enter a correct option!");break;
            }
        }
    }

    private DataBook getDataBook() {
        System.out.println("Please, enter the name of the book you wish to search: ");
        String nameBook = scanner.nextLine();

        var json = useAPI.getData(URL_BASE+ URLEncoder.encode(nameBook, StandardCharsets.UTF_8));

        var result =  convertData.getData(json, DataResult.class);

        if (result.results() == null || result.results().isEmpty()) {
            return null;
        }

        return result.results().getFirst();
    }

    private void searchBookTitle() {
        DataBook dataBook = getDataBook();
        if (dataBook != null) {
            String title = dataBook.title();
            if (repository.findByTitle(title).isPresent()) {
                System.out.println("Already registered book");
                return;
            }

            Book bookNew = new Book(dataBook);
            List<Author> authors = dataBook.authors().stream()
                    .map(Author::new)
                    .collect(Collectors.toList());
            bookNew.setAuthors(authors);
            repository.save(bookNew);

            System.out.println("Book saved correctly");
            showInfoBook(bookNew);
        } else {
            System.out.println("Book not found");
        }
    }

    private void listAllBooks() {
        repository.findAll().forEach(this::showInfoBook);
    }

    private void listAllAuthors() {
        repository.listAuthors().forEach(this::showInfoAuthor);
    }

    private void listAuthorsByYear() {
        System.out.println("Enter the year: ");
        int yearDeath = scanner.nextInt();

        List<Author> authors = repository.listAuthorsByYear(yearDeath);

        if (!authors.isEmpty()) {
            authors.forEach(this::showInfoAuthor);
            return;
        }

        System.out.println("Authors not found!");
    }

    private void listBooksByLanguage() {
        System.out.println("""
                es: Spanish
                en: English
                fr: French
                pt: Portuguese
                
                Enter the abbreviation of language:
                """);

        String language = scanner.nextLine();

        List<Book> books = repository.findByLanguage(language);
        if (!books.isEmpty()) {
            books.forEach(this::showInfoBook);
            return;
        }
        System.out.println("Books not found!");
    }

    private void booksStadistics() {
        DoubleSummaryStatistics sta = repository.findAll().stream()
                .collect(Collectors.summarizingDouble(Book::getDownloadCount));

        System.out.println("\n--Statistics--"+
                "\nAverage downloads: "+sta.getAverage()+
                "\nLargest number of downloads: "+sta.getMax()+
                "\nLowest number of downloads: "+sta.getMin()+
                "\nNumber of registers: "+sta.getCount()+"\n");
    }

    private void showInfoBook(Book book) {
        System.out.println("---------- Book ----------");
        System.out.println("Title: "+book.getTitle());

        System.out.println("Language: "+book.getLanguage());

        String authorsName = book.getAuthors().stream()
                        .map(Author::getName)
                        .collect(Collectors.joining("| "));
        System.out.println("Authors: "+authorsName);

        System.out.println("Downloads count: "+book.getDownloadCount());
        System.out.println("--------------------------\n");
    }

    private void showInfoAuthor(Author author) {
        System.out.println("---------- Author ----------");
        System.out.println("Author: "+author.getName());
        System.out.println("Birthday: "+author.getBirthdate());
        System.out.println("Date of death: "+author.getDeathdate());
        Book book = author.getBook();
        System.out.println("Book: "+book.getTitle());
        System.out.println("--------------------------\n");
    }
}
