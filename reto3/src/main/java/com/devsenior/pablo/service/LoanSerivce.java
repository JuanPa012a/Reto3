package com.devsenior.pablo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.devsenior.pablo.exception.BookLentYetException;
import com.devsenior.pablo.exception.LoanNotFoundException;
import com.devsenior.pablo.exception.MaxLoansException;
import com.devsenior.pablo.model.Book;
import com.devsenior.pablo.model.Loan;
import com.devsenior.pablo.model.User;
import com.devsenior.pablo.model.enums.LoanState;

public class LoanSerivce {
    private final BookService bookService;
    private final UserService userService;
    private final List<Loan> loans;
    private  Long maxLoans;
    

    public LoanSerivce(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
        this.maxLoans = 3L;
        this.loans = new ArrayList<>();
    }

    public BookService getBookService() {
        return bookService;
    }

    public UserService getUserService() {
        return userService;
    }
    
    public void setMaxLoans(Long maxLoans) {
        this.maxLoans = maxLoans;
    }

    
    public void addLoan(User user, Book book) {
        
        var validateUser = validateLoanByUser(user.getId());
        if(!validateUser) throw new MaxLoansException();

        var validateBook = validateLoanByBook(book.getId());
        if(!validateBook) throw new BookLentYetException();
        
        Loan loan = new Loan(book, user);
        loans.add(loan);
    }

    public void returnLoan(String idUser, String idBook) {
        if(loans.isEmpty()){
            throw new LoanNotFoundException("No hay prestamos para devolver en el momento");
        }
        var loan = loans.stream()
                .filter(l -> l.getBook().getId().equals(idBook)
                        && l.getUser().getId().equals(idUser)
                        && l.getState().equals(LoanState.STARTED))
                .findFirst()
                .orElseThrow(() -> new LoanNotFoundException(
                        "Prestamo no encontrado con el ID del usuario: " + idUser + 
                        " O el ID del libro: " + idBook));
        loan.setLoanFinishedDate(LocalDateTime.now());
        loan.setState(LoanState.FINISHED);
    }

    public List<Loan> getAllLoansFromUser(String idUser){
        var lista = loans.stream()
        .filter(l -> l.getUser().getId().equals(idUser)).findFirst();
        if(!lista.isPresent()){
            throw new LoanNotFoundException("No hay prestamos registrados con este usuario");
        }
        return loans.stream()
        .filter(l -> l.getUser().getId().equals(idUser))
        .toList();
    }
    public List<Loan> getAllLoansFromBook(String idBook){
        var lista = loans.stream()
        .filter(l -> l.getBook().getId().equals(idBook)).findFirst();
        if(!lista.isPresent()){
            throw new LoanNotFoundException("No hay prestamos registrados con este libro");
        }
        return loans.stream()
        .filter(l -> l.getBook().getId().equals(idBook))
        .toList();
    }


    public List<Loan> getLoans() {
        if(loans.isEmpty()){
            throw new LoanNotFoundException("No hay prestamos registrados");
        }
        return loans;
    }
    public Boolean validateLoanByUser(String idUser){
        if(loans.isEmpty()){
            return true;
        }
        var countLoans = loans.stream()
        .filter(l -> l.getUser().getId().equals(idUser)
                    && l.getState().STARTED == LoanState.STARTED
        )
        .count();
        return countLoans <= maxLoans;
    }

    public Boolean validateLoanByBook(String idBook){
        if(loans.isEmpty()){
            return true;
        }
        var alreadyLent = loans.stream()
        .filter(l -> l.getBook().getId().equals(idBook))
        .reduce((first, second) -> second);
        if(!alreadyLent.isPresent()){
            return true;
        }
        switch (alreadyLent.get().getState()) {
            case STARTED -> {
                return false;
            }
            case FINISHED -> {
                return true;
            }
            default -> throw new AssertionError();
        }
    }

    

    public Long getMaxLoans() {
        return maxLoans;
    }


    


}
