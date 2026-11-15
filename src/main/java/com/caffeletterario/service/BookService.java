package com.caffeletterario.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import com.caffeletterario.model.Book;
import com.caffeletterario.repository.BookRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final RestClient restClient;

    @Value("${google.books.api.key}")
    private String googleApiKey;

    public BookService(BookRepository bookRepository, RestClient.Builder restClient) {
        this.bookRepository = bookRepository;
        this.restClient = restClient.build();
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByAuthors(List<String> authors) {
        return bookRepository.findByAuthors(authors);
    }

    public List<Book> getBooksByCategories(List<String> categories) {
        return bookRepository.findByCategories(categories);
    }

    // metodo per salvare il libro su db, per fini di test imposto i campi
    // totalCopies e availableCopies con un valore random da 1 a 3
    public Book saveBookFromAPIByIsbn(String isbn) {
        Random rand = new Random();
        Book book = findBookFromAPIByIsbn(isbn);
        // setting momentaneo per testing
        book.setTotalCopies(rand.nextInt(3) + 1);
        book.setAvailableCopies(book.getTotalCopies());
        return bookRepository.save(book);
    }

    // metodo per trovare un libro tramite isbn utilizzando le api di google libri
    public Book findBookFromAPIByIsbn(String isbn) {
        String openLibraryBook = restClient.get()
                .uri("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn + "&key=" + googleApiKey).retrieve()
                .body(String.class);
        JSONObject jsonObject = new JSONObject(openLibraryBook);

        return parseBookFromJson(jsonObject, isbn);
    }

    // metodo per parsare il json restituito dalle api di google libri e creare un
    // oggetto libro
    private Book parseBookFromJson(JSONObject jsonObject, String isbn) {
        if (jsonObject.optInt("totalItems") == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with ISBN: " + isbn);
        }
        try {
            // parsing e setting dell'oggetto libro
            JSONObject jsonGoogleAPIBook = jsonObject.getJSONArray("items").getJSONObject(0)
                    .getJSONObject("volumeInfo");
            Book book = new Book();
            book.setIsbn(isbn);
            book.setTitle(jsonGoogleAPIBook.optString("title", null));

            book.setAuthors(setAttributesListFromJson(jsonGoogleAPIBook, "authors"));
            book.setCategories(setAttributesListFromJson(jsonGoogleAPIBook, "categories"));

            JSONObject imageLinks = jsonGoogleAPIBook.optJSONObject("imageLinks");
            book.setCoverImageUrl(imageLinks != null ? imageLinks.optString("smallThumbnail", null) : null);

            return book;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error parsing book from Google Books API", e);
        }
    }

    // metodo per settare gli attributi autori e categorie che sono array di
    // stringhe
    private ArrayList<String> setAttributesListFromJson(JSONObject jsonGoogleAPIBook, String attributeName) {
        JSONArray jsonArray = jsonGoogleAPIBook.optJSONArray(attributeName);
        ArrayList<String> arrayList = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                arrayList.add(jsonArray.getString(i));
            }
        }
        return arrayList;
    }
}
