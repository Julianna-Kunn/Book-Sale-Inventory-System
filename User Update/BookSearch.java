package com.example.system4;

public class BookSearch {
    String BookId;
    String Title;
    String Author;
    int Series;
    String Genre;
    String Shelf;
    String Status;
    Double Price;

    public BookSearch(String bookId, String title, String author, int series, String genre, String shelf, String status, Double price) {
        BookId = bookId;
        Title = title;
        Author = author;
        Series = series;
        Genre = genre;
        Shelf = shelf;
        Status = status;
        Price = price;
    }

    public String getBookId() {
        return BookId;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public int getSeries() {
        return Series;
    }

    public String getGenre() {
        return Genre;
    }

    public String getShelf() {
        return Shelf;
    }

    public String getStatus() {
        return Status;
    }

    public Double getPrice() {
        return Price;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setSeries(int series) {
        Series = series;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public void setShelf(String shelf) {
        Shelf = shelf;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setPrice(Double price) {
        Price = price;
    }
}
