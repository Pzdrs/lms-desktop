package pb.lms_desktop.store;

import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONObject;
import pb.lms_desktop.API;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.store.modules.Author;
import pb.lms_desktop.store.modules.Book;
import pb.lms_desktop.store.modules.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class Store {
    private Main main;
    private User user;
    private ObservableList<Book> books;
    private ObservableList<Author> authors;

    public Store(Main main) {
        this.main = main;
        this.books = FXCollections.observableArrayList();
        this.authors = FXCollections.observableArrayList();

        /*books.addAll(new Book("5ff34284af13df0bc05de559", "Harry Potter and the Philosopher's Stone", "0-7475-3269-9", "1998", 309,
                        new Author("5ff33134af13df0bc05de553", "Joanne", "Rowling", new Date(), new Date(), new Date()), new Date()),
                new Book("5ff34284af13df0bc05de559", "Harry Potter and the Philosopher's Stone", "0-7475-3269-9", "1998", 309,
                        new Author("5ff33134af13df0bc05de553", "Joanne", "Rowling", new Date(), new Date(), new Date()), new Date()),
                new Book("5ff34284af13df0bc05de559", "Harry Potter and the Philosopher's Stone", "0-7475-3269-9", "1998", 309,
                        new Author("5ff33134af13df0bc05de553", "Joanne", "Rowling", new Date(), new Date(), new Date()), new Date()));*/
    }

    public Main getMain() {
        return main;
    }

    public ObservableList<Book> getBooks() {
        return books;
    }

    public ObservableList<Author> getAuthors() {
        return authors;
    }

    public void setUser(User user) {
        this.user = user;

        Main.getController().navbarController.loggedInStatus.setText(user == null ? "Not logged in" : "Logged in as ");
        Main.getController().navbarController.username.setText(user == null ? "" : user.getUsername());
        Main.getApi().setHeaders(new BasicHeader(HttpHeaders.AUTHORIZATION, user == null ? null : user.getAccessToken()));
    }

    public User getUser() {
        return user;
    }

    public void loadBooks() {
        loadAuthors();
        if (this.books.size() == 0) {
            try {
                JSONArray books = new JSONObject(API.asText(Main.getApi().get("/books").getEntity().getContent())).getJSONArray("books");
                books.forEach(json -> {
                    JSONObject book = new JSONObject(json.toString());
                    this.books.add(new Book(book.getString("_id"),
                            book.getString("title"),
                            book.getString("isbn"),
                            book.getString("writtenIn"),
                            book.getInt("pageCount"),
                            getAuthorById(book.getString("author")),
                            Utils.toDate(book.getString("createdAt"))
                    ));
                });
            } catch (IOException e) {
                System.out.println("Couldn't load the books");
            }
        }
    }

    public void loadAuthors() {
        if (this.authors.size() == 0) {
            try {
                JSONArray authors = new JSONObject(API.asText(Main.getApi().get("/authors").getEntity().getContent())).getJSONArray("authors");
                authors.forEach(json -> {
                    JSONObject author = new JSONObject(json.toString());
                    this.authors.add(new Author(author.getString("_id"),
                            author.getString("firstName"),
                            author.getString("lastName"),
                            Utils.toDate(author.getString("born")),
                            Utils.toDate(author.get("died")),
                            Utils.toDate(author.getString("createdAt"))
                    ));
                });
            } catch (IOException e) {
                System.out.println("Couldn't load the authors");
            }
        }
    }

    public Author getAuthorById(String id) {
        for (Author author : this.authors) {
            if (author.getId().equals(id)) return author;
        }
        return null;
    }
}
