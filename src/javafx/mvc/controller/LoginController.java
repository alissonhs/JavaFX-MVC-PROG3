/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alisson H. Silva
 */
public class LoginController implements Initializable {
    
    
    @FXML
    private Button btnEntrar;

    @FXML
    private Button btnSair;

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtSenha;


    @FXML
    void btnEntrarClick(ActionEvent event) {
        System.out.println("Passsei AQUI!");
        //Código para Validar o Lojin
        isAllowed = true;
        System.out.println("Entrei!");
        dialogStage.close();
 
    }

    @FXML
    void btnSairClick(ActionEvent event) {
        dialogStage.close();
    }
    
    private Stage dialogStage;
    private boolean isAllowed = false;
    
    /**
     * Initializes the controller class.
     */
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }    

    public boolean isIsAllowed() {
        return isAllowed;
        // TODO
    }    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
