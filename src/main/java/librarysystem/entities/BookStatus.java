package librarysystem.entities;

public class BookStatus {
    private Book book;
    private Status status;
    private Account account;

    public BookStatus(Book book, Status bookStatus, Account account) {
        this.book = book;
        this.status = bookStatus;
        this.account = account;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString(){
        return book.toString() + " | "+ status.name()+" | "+((account != null)?account.toString():"");
    }
}
