import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.devsenior.pablo.exception.BookLentYetException;
import com.devsenior.pablo.exception.MaxLoansException;
import com.devsenior.pablo.model.Book;
import com.devsenior.pablo.model.User;
import com.devsenior.pablo.model.enums.LoanState;
import com.devsenior.pablo.service.BookService;
import com.devsenior.pablo.service.LoanSerivce;
import com.devsenior.pablo.service.UserService;

public class LoanServiceTest {
    private BookService bookService;
    private UserService userService;
    private LoanSerivce service;


    @BeforeEach
    void setUp() {
        bookService = Mockito.mock(BookService.class);
        userService = Mockito.mock(UserService.class);

      service = new LoanSerivce(bookService, userService);
    }

    @DisplayName("Agregar un prestamo con un usuario y libro existente")
    @Test
    void testAddLoanWhitExistingUserAndExistingBook()  {
        //Given
        var id = "123";
        var idBook = "1";
        var isbn = "1234";
        var mockUser = new User(id, "Pablo");
        var mockBook = new Book(idBook,isbn, "El principito", "Antoine de Saint-Exupéry");
        
        Mockito.when(userService.getUserbyId(id)).thenReturn(mockUser);
        Mockito.when(bookService.getBookById(idBook)).thenReturn(mockBook);


        //When
        service.addLoan(userService.getUserbyId(id), bookService.getBookById(idBook));

        //Then

        var loans = service.getLoans();
        assertEquals(1, loans.size());


        var loan = loans.get(0);
        assertNotNull(loan.getUser());
        assertNotNull(loan.getBook());
        
        assertEquals(LoanState.STARTED, loan.getState());
                

    }

    @Test
    void testReturnBookWhitExistingUserAndExistingBook(){
        //Given
        var id = "123";
        var isbn = "1234";
        var idBook = "1";

        var userMock = new User(id, "pablo"); 
        var mockBook = new Book(idBook, isbn, "El principito", "Antoine de Saint-Exupéry");
      
        Mockito.when(userService.getUserbyId(id)).thenReturn(userMock);
        Mockito.when(bookService.getBookById(idBook)).thenReturn(mockBook);
        service.addLoan(userService.getUserbyId(id), bookService.getBookById(idBook));

        //When
        service.returnLoan(id, idBook);

        //Then
        var loans = service.getLoans();
        
        assertEquals(1, loans.size());
        var loan = loans.getFirst();


        assertEquals(id, loan.getUser().getId());
        assertEquals(isbn, loan.getBook().getIsbn());
        assertEquals(LoanState.FINISHED, loan.getState());

    }

    @Test
    void testAddLoanWhitTheSameBook(){
        //GIVEN
        var id = "123";
        var isbn = "1234";
        var idBook = "1";

        var userMock = new User(id, "pablo"); 
        var mockBook = new Book(idBook, isbn, "El principito", "Antoine de Saint-Exupéry");
      
        Mockito.when(userService.getUserbyId(id)).thenReturn(userMock);
        Mockito.when(bookService.getBookById(idBook)).thenReturn(mockBook);
        service.addLoan(userService.getUserbyId(id), bookService.getBookById(idBook));
        //WHEN - THEN
        assertThrows(BookLentYetException.class, ()-> service.addLoan(userService.getUserbyId(id), bookService.getBookById(idBook)));
    }

    @Test
    void testAddLoanWhitTheSameUserOften(){
        //GIVEN
        var id = "123";
        var isbn = "1234";
        var idBook = "1";

        var userMock = new User(id, "pablo"); 
        var mockBook = new Book(idBook, isbn, "El principito", "Antoine de Saint-Exupéry");
        
        var isbn2 = "4321";
        var idBook2 = "3";
        var mockBook2 = new Book(idBook2, isbn2, "Luna de pluton", "Dross");
      

        Mockito.when(userService.getUserbyId(id)).thenReturn(userMock);
        Mockito.when(bookService.getBookById(idBook)).thenReturn(mockBook);
        Mockito.when(bookService.getBookById(idBook2)).thenReturn(mockBook2);
        service.addLoan(userService.getUserbyId(id), bookService.getBookById(idBook));
        service.setMaxLoans(0L);
        //WHEN - THEN
         assertThrows(MaxLoansException.class, ()-> 
        service.addLoan(userService.getUserbyId(id), bookService.getBookById(idBook2)));
        
    }

    @Test
    void addLoandWhitABookWhenItWasAlreadyLent(){
        //GIVEN
        /* GIVEN USER N°1 */
        var idUser = "12";
        var name = "Juan";
        var user = new User(idUser, name);

        /* GIVEN USER N°2 */ 
        var idUser2 = "12";
        var name2 = "Juan";
        var user2 = new User(idUser2, name2); 

        /* GIVEN BOOK*/

        var idBook = "1";
        var isbn = "1234";
        var title = "El fin del mundo";
        var autor = "Anonimo";
        var book = new Book(idBook, isbn, title, autor);
        /* GIVEN OTHER BOOK */
        var idBook2 = "2";
        var isbn2 = "3212";
        var title2 = "El inicio del mundo";
        var autor2 = "Anonimo";
        var book2 = new Book(idBook2, isbn2, title2, autor2);

        /*ACTIVE MOCKITO */
        //MOCKITO USERS
        Mockito.when(userService.getUserbyId(idUser)).thenReturn(user);
        Mockito.when(userService.getUserbyId(idUser2)).thenReturn(user2);
        //MOCKITO BOOKS
        Mockito.when(bookService.getBookById(idBook)).thenReturn(book);
        Mockito.when(bookService.getBookById(idBook2)).thenReturn(book2);
        
        //WHEN
        service.addLoan(userService.getUserbyId(idUser), bookService.getBookById(idBook));
        //THEN
        assertDoesNotThrow(() -> service.addLoan(userService.getUserbyId(idUser2), bookService.getBookById(idBook2)));
        assertThrows(BookLentYetException.class, ()-> service.addLoan(userService.getUserbyId(idUser2), bookService.getBookById(idBook)));

    }

}
