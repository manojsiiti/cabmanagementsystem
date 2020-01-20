package librarysystem.entities;

public class Book {
    private String id;
    private String bookDetail;

    public Book() {

    }

    public Book(String bookDetail) {
        super();
        this.bookDetail = bookDetail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(String bookDetail) {
        this.bookDetail = bookDetail;
    }

    @Override
    public String toString(){
        return "Book with id="+this.id;
    }
}
