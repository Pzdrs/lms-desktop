package pb.lms_desktop.store;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONObject;
import pb.lms_desktop.API;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.store.modules.Author;
import pb.lms_desktop.store.modules.Book;
import pb.lms_desktop.store.modules.History;
import pb.lms_desktop.store.modules.User;

import java.io.IOException;

public class Store {
    private Main main;
    private User user;
    private ObservableList<Book> books;
    private ObservableList<Author> authors;
    private ObservableList<User> users;
    private ObservableList<History> history;

    public Store(Main main) {
        this.main = main;
        this.books = FXCollections.observableArrayList();
        this.authors = FXCollections.observableArrayList();
        this.users = FXCollections.observableArrayList();
        this.history = FXCollections.observableArrayList();
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

    public ObservableList<History> getHistory() {
        return history;
    }

    public ObservableList<User> getUsers() {
        return users;
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
                            Utils.getAuthorById(book.getString("author")),
                            Utils.toDate(book.getString("createdAt"))
                    ));
                });
            } catch (IOException e) {
                System.out.println("Couldn't load the books");
            }
        }
    }

    public void reLoadBook() {
        books.clear();
        loadBooks();
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

    public void loadHistory() {
        loadUsers();
        loadBooks();
        if (this.history.size() == 0) {
            try {
                JSONArray history = new JSONObject(API.asText(Main.getApi().get("/history").getEntity().getContent())).getJSONArray("history");
                history.forEach(json -> {
                    JSONObject singleHistory = new JSONObject(json.toString());
                    this.history.add(new History(
                            singleHistory.getString("_id"),
                            singleHistory.getBoolean("returned"),
                            Utils.getBookById(singleHistory.getString("book")),
                            Utils.getUserById(singleHistory.getString("user")),
                            Utils.toDate(singleHistory.getJSONObject("date").get("from")),
                            Utils.toDate(singleHistory.getJSONObject("date").get("to"))
                    ));
                });
            } catch (IOException e) {
                System.out.println("Couldn't load the history");
            }
        }
    }

    public void loadUsers() {
        if (this.users.size() == 0) {
            try {
                JSONArray users = new JSONObject(API.asText(Main.getApi().get("/users").getEntity().getContent())).getJSONArray("users");
                users.forEach(json -> {
                    JSONObject user = new JSONObject(json.toString());
                    this.users.add(new User(
                            null,
                            user.getString("_id"),
                            user.getString("username"),
                            user.getString("email"),
                            user.getString("firstName"),
                            user.getString("lastName"),
                            user.getString("password"),
                            user.getBoolean("isAdmin"),
                            Utils.toDate(user.getString("registeredAt"))
                    ));
                });
            } catch (IOException e) {
                System.out.println("Couldn't load the users");
            }
        }
    }

    public void reLoadUsers() {
        users.clear();
        loadUsers();
    }
}
