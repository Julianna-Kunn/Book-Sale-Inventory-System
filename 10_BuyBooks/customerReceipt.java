import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class customerReceipt {


    @FXML
    private Label userIdLabel;


    public void setUserData(String idstr) {
        userIdLabel.setText(idstr);
    }

   private String bookId;
   private String title;
   private int quantity;
   private Double totalPrice;

   public void receiptPurchase(String BookId, String title, int quantity, Double totalPrice){
       this.bookId = BookId;
       this.title = title;
       this.quantity = quantity;
       this.totalPrice = totalPrice;
   }

   public String getBookId(){
       return bookId;
    }

    public String getTitle(){
       return title;
    }

    public int getQuantity(){
       return quantity;
    }

    public Double getTotalPrice(){
       return totalPrice;
    }



}
