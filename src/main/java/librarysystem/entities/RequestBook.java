package librarysystem.entities;

public class RequestBook {
    private String bookId;
    private String accountId;
    private RequestType requestType;

    public RequestBook() {
    }

    public RequestBook(String bookId, String accountId, RequestType requestType) {
        super();
        this.bookId = bookId;
        this.accountId = accountId;
        this.requestType = requestType;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}
