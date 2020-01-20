package librarysystem.service;

import librarysystem.entities.Account;
import librarysystem.entities.Book;
import librarysystem.entities.BookStatus;
import librarysystem.entities.Library;
import librarysystem.entities.RequestBook;
import librarysystem.entities.RequestType;
import librarysystem.entities.Status;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class LibService {

    Map<String , Account> accounts = new HashMap<>();
    List<Book> books = new ArrayList<>();
    Library library = new Library();

    public Account createAccount(Account account) {
        String id = UUID.randomUUID().toString();
        account.setId(id);
        accounts.put(id, account);
        return account;
    }

    public Book createBook(Book book) {
        book.setId(UUID.randomUUID().toString());
        books.add(book);
        addBookToLibrary(book);
        return book;
    }

    public BookStatus changeBookStatus(RequestBook requestBook) {
        String bookId = requestBook.getBookId();
        String accountId = requestBook.getAccountId();
        RequestType type = requestBook.getRequestType();
        BookStatus bookStatus = library.getBookStatusMap().get(bookId);
        Account account = getAccount(accountId);

        if(bookStatus ==null || account ==null) {
            throw new RuntimeException("Invalid account or book");
        }

        if(type == RequestType.RESERVE) { // to reserve book
            if(bookStatus.getStatus() == Status.AVAILABLE) {
                bookStatus.setStatus(Status.RESERVED);
                bookStatus.setAccount(account);
            } else {
                throw new RuntimeException("Invalid request for "+type.name());
            }

        } else if (type == RequestType.RETURN) { // To return or Un-reserve the book
            if( bookStatus.getStatus() == Status.ISSUED && accountId.equalsIgnoreCase(bookStatus.getAccount().getId())) {
                bookStatus.setStatus(Status.AVAILABLE);
                bookStatus.setAccount(null);
            } else {
                throw new RuntimeException("Invalid request for "+type.name());
            }
        } else if (type == RequestType.UN_RESERVE) { // To return or Un-reserve the book
            if( bookStatus.getStatus() == Status.RESERVED && accountId.equalsIgnoreCase(bookStatus.getAccount().getId())) {
                bookStatus.setStatus(Status.AVAILABLE);
                bookStatus.setAccount(null);
            } else {
                throw new RuntimeException("Invalid request for "+type.name());
            }
        }else if (type == RequestType.ISSUE) {
            if(bookStatus.getStatus() == Status.RESERVED && accountId.equalsIgnoreCase(bookStatus.getAccount().getId())) {
                bookStatus.setStatus(Status.ISSUED);// issue the book to same person as reserved
            } else if (bookStatus.getStatus() == Status.AVAILABLE) {
                bookStatus.setStatus(Status.ISSUED);// issue the book to same person
                bookStatus.setAccount(account);
            } else {
                throw new RuntimeException("Invalid request for "+type.name());
            }
        } else {
            throw new RuntimeException("Invalid request Type");
        }
        return bookStatus;
    }

    public Library getLibrary() {
        return library;
    }

    public Collection<Account> getAccounts() {
        return  accounts.values();
    }

    public List<Book> getBooks() {
        return books;
    }

    private void addBookToLibrary(Book book) {
        BookStatus bookStatus = new BookStatus(book, Status.AVAILABLE, null);
        library.getBookStatusMap().put(book.getId(), bookStatus);
    }

    private Account getAccount(String accountId) {
        return accounts.get(accountId);
    }


}
