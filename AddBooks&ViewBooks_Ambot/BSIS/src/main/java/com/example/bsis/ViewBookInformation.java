package com.example.bsis;

public class ViewBookInformation {

    String bookId;
    String title;
    String author;
    String genre;
    int seriesNo;
    String status;
    String isbn;
    int pubdate;
    double sellingPrice;
    String shelf;
    int quantity;
    double supplierPrice;
    String invoiceid;
    String timestamp;


    public double getSupplierPrice() {
        return supplierPrice;
    }

    public void setSupplierPrice(double supplierPrice) {
        this.supplierPrice = supplierPrice;
    }

    public String getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(String invoiceid) {
        this.invoiceid = invoiceid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ViewBookInformation (String bookId, String title, String author, String genre, String status, String isbn,
                                int pubdate, double sellingPrice, int seriesNo, String shelf, int quantity, double supplierPrice, String invoiceid, String timestamp) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.seriesNo = seriesNo;
        this.status = status;
        this.isbn = isbn;
        this.pubdate = pubdate;
        this.sellingPrice = sellingPrice;
        this.shelf = shelf;
        this.quantity = quantity;
        this.supplierPrice = supplierPrice;
        this.invoiceid = invoiceid;
        this.timestamp = timestamp;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setSeriesNo(int seriesNo) {
        this.seriesNo = seriesNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPubdate(int pubdate) {
        this.pubdate = pubdate;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookId() {
        return bookId;
    }

    public String getGenre() {
        return genre;
    }

    public int getPubdate() {
        return pubdate;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getSeriesNo() {
        return seriesNo;
    }

    public String getShelf() {
        return shelf;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



}