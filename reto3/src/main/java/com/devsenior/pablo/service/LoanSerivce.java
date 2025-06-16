package com.devsenior.pablo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.devsenior.pablo.exception.BookLentYetException;
import com.devsenior.pablo.exception.LoanNotFoundException;
import com.devsenior.pablo.exception.MaxLoansException;
import com.devsenior.pablo.model.Loan;
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
    public void setMaxLoans(Long maxLoans) {
        this.maxLoans = maxLoans;
    }


    
    public void addLoan(String idUser, String idBook) {
        
        var validateBook = validateLoanByBook(idBook);
        if(!validateBook) throw new BookLentYetException();
        var validateUser = validateLoanByUser(idUser);
        if(!validateUser) throw new MaxLoansException();
        
        var book = bookService.getBookById(idBook);
        var user = userService.getUserbyId(idUser);
        Loan loan = new Loan(book, user);
        loans.add(loan);
    }

    public void returnLoan(String idUser, String idBook) {
        var loan = loans.stream()
                .filter(l -> l.getBook().getId().equals(idBook)
                        && l.getUser().getId().equals(idUser)
                        && l.getState().equals(LoanState.STARTED))
                .findFirst()
                .orElseThrow(() -> new LoanNotFoundException(
                        "Loan not found whit User ID: " + idUser + " or Book ID: " + idBook));
        loan.setLoanFinishedDate(LocalDateTime.now());
        loan.setState(LoanState.FINISHED);
    }

    public List<Loan> getAllLoansFromUser(String idUser){
        return loans.stream()
        .filter(l -> l.getUser().getId().equals(idUser))
        .toList();
    }

    public List<Loan> getLoans() {
        return loans;
    }
    public Boolean validateLoanByUser(String idUser){
        var countLoans = loans.stream()
        .filter(l -> l.getUser().getId().equals(idUser)
                    && l.getState().STARTED == LoanState.STARTED
        )
        .count();
        return countLoans > maxLoans;
    }

    public Boolean validateLoanByBook(String idBook){
        var alreadyLent = loans.stream()
        .filter(l -> l.getBook().getId().equals(idBook))
        .findFirst().get();
        switch (alreadyLent.getState()) {
            case STARTED -> {
                return false;
            }
            case FINISHED -> {
                return true;
            }
            default -> throw new AssertionError();
        }
    }


    


}
