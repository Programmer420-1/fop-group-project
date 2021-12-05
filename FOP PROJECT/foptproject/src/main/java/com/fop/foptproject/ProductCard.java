/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fop.foptproject;

import com.fop.foptproject.controller.FoodnBeverageController;
import com.fop.sqlUtil.sqlConnect;
import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
/**
 *
 * @author WeiXin
 */
public class ProductCard{
    private HashMap<String,Object> productDetails = new HashMap<>();
    private HBox productCard;
    private Label quantity;
    private static HashMap<String,Integer> tempStorage = new HashMap<>();
    private static double totalAmount = 0;
    
    public ProductCard(String productID,String imgPath,double imgW,double imgH,double scale,double price,String productName,String productDesc){
        productDetails.put("productID",productID);
        productDetails.put("imgPath",imgPath);
        productDetails.put("imgW",imgW*scale);
        productDetails.put("imgH",imgH*scale);
        productDetails.put("price",price);
        productDetails.put("productName",productName);
        productDetails.put("productDesc",productDesc);
               
        this.productCard = makeCard(productID,price);
        HBox.setMargin(this.productCard,new Insets(0,45,0,0));   
    }
        
    public HashMap<String,Object> getProductDetails(){
        return productDetails;
    }
    
    public Object getValue(String key){
        return this.productDetails.get(key);
    }
    
    public HBox getCard(){
        
        return this.productCard;
    }
        
    private HBox makeCard(String productId, double price){
        HBox card = new HBox();
        
        VBox img = productImage(productId,price);
        VBox productDetails = makeProductDescription(productId);
        StackPane buttons = makeButtons(productId);
        
        card.getChildren().addAll(img,productDetails,buttons);
        return card;
    }
    
    private Image getImage(){
        String path = getClass().getResource((String)getValue("imgPath")).toString();
            Image img = new Image(path,(double)getValue("imgW"),(double)getValue("imgH"),false,false);
        return img;
    }
    
    private StackPane makeButtons(String productId){
        // components
        this.quantity = new Label();
        quantity.setText(retrievePurchaseDetail(productId));
        quantity.setId(productId+"_quantity");
        quantity.setPrefSize(20,20);
        quantity.setAlignment(Pos.CENTER);
        
        
        Button addition = new Button();
        addition.setId(productId+"_addition");
        addition.setText("+");
        addition.setOnAction(e->{
            quantity.setText(Integer.toString(Integer.parseInt(quantity.getText())+1));
            addPurchase(productId,Integer.parseInt(quantity.getText()));
            totalAmount += Double.parseDouble(new sqlConnect().queryProductInfo(productId,"price"));
        });
        addition.setPrefSize(33,33);
        addition.setStyle("-fx-background-color:#FFEE00;-fx-background-radius:0 8 8 0;-fx-background-insets:0;");
          
        
        Button decrement = new Button();
        decrement.setId(productId+"_decrement");
        decrement.setText("-");
        decrement.setOnAction(e->{
            if(Integer.parseInt(quantity.getText())>0){
                quantity.setText(Integer.toString(Integer.parseInt(quantity.getText())-1));
                addPurchase(productId,Integer.parseInt(quantity.getText()));
                totalAmount -= Double.parseDouble(new sqlConnect().queryProductInfo(productId,"price"));
            }
        });
        decrement.setPrefSize(33,33);
        decrement.setStyle("-fx-background-color:#434343;-fx-background-radius:8 0 0 8;-fx-text-fill:#FFFFFF;-fx-background-insets:0;");
        
        // packaging
        HBox buttons = new HBox();
        buttons.getChildren().addAll(decrement,quantity,addition);
        HBox.setMargin(decrement,new Insets(0,12,0,0));
        HBox.setMargin(quantity,new Insets(0,12,0,0));
        buttons.setMaxSize(116, 90);
        buttons.setAlignment(Pos.CENTER);       
        StackPane buttonBox = new StackPane();
        buttonBox.getChildren().add(buttons);
        buttonBox.setMaxWidth(126.4);
        buttonBox.setAlignment(buttons,Pos.BOTTOM_CENTER);
        
        return buttonBox;
    }
    
    private VBox makeProductDescription(String productId){
        
        Label productName = new Label();
        Label productDesc = new Label();
        VBox labelContainer = new VBox();
        
        productName.setId(productId+"_name");
        productDesc.setId(productId+"_desc");
        productName.setStyle("-fx-text-fill:#FFFFFF;-fx-font-size:18px");
        productDesc.setStyle("-fx-text-fill:#FFFFFF;-fx-font-size:18px");
        productName.setText((String)this.productDetails.get("productName"));
        productDesc.setText((String)this.productDetails.get("productDesc"));
        
        labelContainer.setPrefWidth(350);
        labelContainer.setStyle("-fx-padding:10 0 0 20");
        labelContainer.getChildren().addAll(productName,productDesc);
        

        return labelContainer;
    }
    
    private VBox productImage(String productId, double price){
        Image productImg = getImage();
        ImageView img = new ImageView(productImg);
        img.setId(productId+"_img");
        img.setFitWidth((double)this.productDetails.get("imgW"));
        img.setFitHeight((double)this.productDetails.get("imgH"));
        img.setPreserveRatio(true);
        img.setSmooth(true);
        
        
        Label priceTag = new Label();
        priceTag.setText(String.format("RM%.2f",price));
        priceTag.setStyle("-fx-padding:10px 0 0 18px;-fx-font-size:18px;-fx-alignment:center;-fx-text-fill:#FFFFFF");
        priceTag.setId(productId+"_priceTag");
        
        
        VBox container = new VBox();
        container.setStyle("-fx-padding:0 0 0 0");
        container.getChildren().addAll(img,priceTag);
        
        return container;
    }
    
    public void mergeHashMap(){
        System.out.println("under development");
    }
    
    public void addPurchase(String key, int value){
        if(value == 0){
            tempStorage.remove(key);
        }
        else if(tempStorage.containsKey(key)){
            tempStorage.replace(key, value);
        }
        else if(!(tempStorage.containsKey(key))){
            tempStorage.put(key,value);                 
        }
        else{
            return;
        }
    }
    
    public String retrievePurchaseDetail(String key){
        if(tempStorage.containsKey(key)){
            return Integer.toString(tempStorage.get(key));
        }
        else{
            return "0";
        }
    }
    
    public static HashMap<String,Integer> retrieveAllPurchaseDetail(){
        return tempStorage;
    }
    

    
}
