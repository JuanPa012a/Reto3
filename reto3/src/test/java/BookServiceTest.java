import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsenior.pablo.exception.BookNotFoundException;
import com.devsenior.pablo.model.Book;
import com.devsenior.pablo.service.BookService;

public class BookServiceTest {
    private  BookService bookService;
    @BeforeEach
    void setUp(){
        bookService = new BookService();
    }

    @Test
    void testAddBook(){
        //GIVEN
        var id = "1";
        var isbn = "AB01";
        var titulo = "El principito";
        var autor = "Juan Pablo";
        var book = new Book(id, isbn, titulo, autor);
        
        //WHEN
        bookService.addBook(book);

        //THEN
        assertEquals(bookService.getBookByIsbn(isbn).getIsbn(), isbn);
    }

    @Test
    void testDeleteBook(){
        //GIVEN
        var id = "1";
        var isbn = "AB01";
        var titulo = "El principito";
        var autor = "Juan Pablo";
        var book = new Book(id, isbn, titulo, autor);
        

        //WHEN
        bookService.deleteBook(book);
        
        //THEN
        assertEquals(true, bookService.getAllBooks().isEmpty());
    }

    @Test
    void testGetUserById(){
        //GIVEN
        testAddBook();
        //WHEN - THEN
        var id = "AB02";
        try {
            var book = bookService.getBookById(id);  
                assertEquals(id, book.getId()); 

        } catch (BookNotFoundException e) {
        
            assertTrue(true);
        }
        
        
    }

    @Test
    void testGetUserByIsbn(){
        //GIVEN
        testAddBook();
        //WHEN - THEN
        var isbn = "2";
        try {
            var book = bookService.getBookByIsbn(isbn);  
                assertEquals(isbn, book.getIsbn()); 
                 
        } catch (BookNotFoundException e) {
        
            assertTrue(true);
        }
        
        
    }
        
}
