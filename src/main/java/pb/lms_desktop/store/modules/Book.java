package pb.lms_desktop.store.modules;

import java.util.Date;

public class Book {
    private String id, title, isbn, writtenIn, fullTitle;
    private int pageCount;
    private Author author;
    private Date createdAt;

    public Book(String id, String title, String isbn, String writtenIn, int pageCount, Author author, Date createdAt) {
        this.id = id;
        this.title = title;
        this.fullTitle = new StringBuilder(title)
                .append(" (")
                .append(writtenIn)
                .append(")")
                .toString();
        this.isbn = isbn;
        this.writtenIn = writtenIn;
        this.pageCount = pageCount;
        this.author = author;
        this.createdAt = createdAt;
    }

    public String getFullTitle() {
        return fullTitle;
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

    public String getWrittenIn() {
        return writtenIn;
    }

    public int getPageCount() {
        return pageCount;
    }

    public Author getAuthor() {
        return author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", pageCount=" + pageCount +
                ", author=" + author.toString() +
                ", writtenIn=" + writtenIn +
                ", createdAt=" + createdAt +
                '}';
    }
}
