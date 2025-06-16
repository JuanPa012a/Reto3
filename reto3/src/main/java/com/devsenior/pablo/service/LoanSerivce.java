package com.devsenior.pablo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.devsenior.pablo.exception.LoanNotFoundException;
import com.devsenior.pablo.model.Loan;
import com.devsenior.pablo.model.enums.LoanState;

public class LoanSerivce {
    private final BookService bookService;
    private final UserService userService;
    private final List<Loan> loans;

    public LoanSerivce(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
        this.loans = new ArrayList<>();
    }

    public void addLoan(String idUser, String idBook) {
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

}
