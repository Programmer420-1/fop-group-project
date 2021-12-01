/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fop.foptproject.controller;

import com.fop.foptproject.App;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SceneController {
    private Scene scene;
    private Stage stage;
    private final double WIDTH;
    private final double HEIGHT;
    
    public SceneController(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        this.WIDTH = screenBounds.getWidth();
        this.HEIGHT = screenBounds.getHeight();
    }
    
    private void switchScene(ActionEvent event, String fxmlFile) throws IOException{
        if(fxmlFile.contains("App.fxml")){
            ScrollPane root = FXMLLoader.load(App.class.getResource(fxmlFile));
            root.hvalueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    root.setHvalue(50);
            }});
            stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
            scene = new Scene(root,WIDTH,HEIGHT);
        }
        else{
            Parent root = FXMLLoader.load(App.class.getResource(fxmlFile));
            stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
            scene = new Scene(root,WIDTH,HEIGHT);
        }
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    
    public void switchToOTPScene(ActionEvent event) throws IOException{
        switchScene(event,"OTP.fxml");
    }
    
    public void switchToRegisterAndLogin(ActionEvent event) throws IOException{
        switchScene(event,"LoginRegister.fxml");
    }
 
    public void switchToSeats(ActionEvent event) throws IOException{
        switchScene(event,"Seats.fxml");
    }
    
    public void switchToLandingPage(ActionEvent event) throws IOException{
        switchScene(event,"App.fxml");
    }
}