package pb.lms_desktop.store.modules;

import java.util.Date;

public class History {
    private String id;
    private boolean returned;
    private Book book;
    private User user;
    private Date from, to;

    public History(String id, boolean returned, Book book, User user, Date from, Date to) {
        this.id = id;
        this.returned = returned;
        this.book = book;
        this.user = user;
        this.from = from;
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public boolean isReturned() {
        return returned;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "History{" +
                "id='" + id + '\'' +
                ", returned=" + returned +
                ", book=" + book +
                ", user=" + user +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
