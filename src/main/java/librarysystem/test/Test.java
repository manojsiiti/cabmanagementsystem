package librarysystem.test;


import librarysystem.controller.LibController;
import librarysystem.entities.Account;
import librarysystem.entities.Book;
import librarysystem.entities.BookStatus;
import librarysystem.entities.RequestBook;
import librarysystem.entities.RequestType;
import librarysystem.entities.Status;
import librarysystem.service.LibService;
import org.springframework.util.Assert;

public class Test {

    public static void main(String s[]) {
        Test test =  new Test();
        test.happyTest();
    }
    public void happyTest() {
        LibController controller = new LibController();
        LibService service = new LibService();
        controller.setService(service);
        Book book = new Book("MathBook");
        Book bookRes =  controller.createBook(book);

        System.out.println(bookRes.toString());

        Account account = new Account("Manoj Saini");
        Account accountRes = controller.createAccount(account);

        System.out.println(accountRes.toString());

        RequestBook requestBook = new RequestBook(bookRes.getId(), accountRes.getId(), RequestType.RESERVE);

        controller.requestBook(requestBook);
        BookStatus bookStatus = controller.availability(bookRes.getId());
        Assert.isTrue(bookStatus.getStatus()== Status.RESERVED);
        System.out.println("Successfully Reserved the book");

        //Try to Reserve again
        try {
            controller.requestBook(requestBook);

        } catch (RuntimeException ex) {
            System.out.println("Book can not be reserved again");
            Assert.isTrue(ex.getMessage().equalsIgnoreCase("Invalid request for RESERVE"));
        }

        requestBook.setRequestType(RequestType.ISSUE);
        controller.requestBook(requestBook);
        bookStatus = controller.availability(bookRes.getId());
        Assert.isTrue(bookStatus.getStatus()== Status.ISSUED);

        System.out.println("Successfully issued the book");


        requestBook.setRequestType(RequestType.RETURN);
        controller.requestBook(requestBook);
        bookStatus = controller.availability(bookRes.getId());
        Assert.isTrue(bookStatus.getStatus()== Status.AVAILABLE);
        System.out.println("Successfully returned the book");

    }
}
