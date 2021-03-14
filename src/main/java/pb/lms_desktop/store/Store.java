package pb.lms_desktop.store;

import pb.lms_desktop.store.modules.Author;
import pb.lms_desktop.store.modules.Book;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Book> books;
    private List<Author> authors;

    public Store() {
        this.books = new ArrayList<>();
        this.authors = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
