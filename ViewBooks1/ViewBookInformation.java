package com.example.system4;

public class ViewBookInformation {
    String bookId;
    String title;
    String author;
    int seriesNo;
    String genre;
    String shelf;
    int pubdate;
    String isbn;
    String status;
    double price;
    int quantity;

    public ViewBookInformation(String bookId, String title, String author, int seriesNo, String genre, String shelf, int pubdate, String isbn, String status, double price, int quantity) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.seriesNo = seriesNo;
        this.genre = genre;
        this.shelf = shelf;
        this.pubdate = pubdate;
        this.isbn = isbn;
        this.status = status;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getSeriesNo() {
        return seriesNo;
    }

    public void setSeriesNo(int seriesNo) {
        this.seriesNo = seriesNo;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public int getPubdate() {
        return pubdate;
    }

    public void setPubdate(int pubdate) {
        this.pubdate = pubdate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double Price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
