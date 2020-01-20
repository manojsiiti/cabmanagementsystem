package librarysystem.controller;

import librarysystem.entities.Account;
import librarysystem.entities.Book;
import librarysystem.entities.BookStatus;
import librarysystem.entities.Library;
import librarysystem.entities.RequestBook;
import librarysystem.service.LibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class LibController {

    @Autowired
    private LibService service;

    public void setService(LibService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create-account")
    public Account createAccount(@RequestBody Account account){
        return service.createAccount(account);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create-book")
    public Book createBook(@RequestBody Book book){
        return service.createBook(book);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/request-book")
    public BookStatus requestBook(@RequestBody RequestBook requestBook){
        return service.changeBookStatus(requestBook);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/accounts")
    public Collection<Account> getAccounts() {
        return service.getAccounts();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/books")
    public List<Book> getBooks() {
        return service.getBooks();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/library")
    public Library viewLibrary() {
        return service.getLibrary();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/availability/{bookid}")
    public BookStatus availability(@PathVariable("bookid") String bookId) {
        return service.getLibrary().getBookStatusMap().get(bookId);
    }




}
