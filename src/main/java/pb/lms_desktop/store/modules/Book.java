package pb.lms_desktop.store.modules;

import java.util.Date;

public class Book {
    private String id, title, isbn;
    private int pageCount;
    private Author author;
    private Date writtenIn, createdAt;

    public Book(String id, String title, String isbn, int pageCount, Author author, Date writtenIn, Date createdAt) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.author = author;
        this.writtenIn = writtenIn;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPageCount() {
        return pageCount;
    }

    public Author getAuthor() {
        return author;
    }

    public Date getWrittenIn() {
        return writtenIn;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
