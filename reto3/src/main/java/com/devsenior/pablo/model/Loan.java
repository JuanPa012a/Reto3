package com.devsenior.pablo.model;

import java.time.LocalDateTime;

import com.devsenior.pablo.model.enums.LoanState;

public class Loan {
    private Book book;
    private User user;
    private LocalDateTime loanDate;
    private LocalDateTime loanFinishedDate;
    private LoanState state;

    public Loan(Book book, User user, LocalDateTime loanDate) {
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.state = LoanState.STARTED;

    }

    public Loan(Book book, User user) {
        this.book = book;
        this.user = user;
        this.loanDate = LocalDateTime.now();
        this.state = LoanState.STARTED;

    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDateTime getLoanFinishedDate() {
        return loanFinishedDate;
    }

    public void setLoanFinishedDate(LocalDateTime loanFinishedDate) {
        this.loanFinishedDate = loanFinishedDate;
    }

    public LoanState getState() {
        return state;
    }

    public void setState(LoanState state) {
        this.state = state;
    }

}
