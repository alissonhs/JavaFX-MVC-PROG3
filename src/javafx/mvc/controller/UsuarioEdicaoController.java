package javafx.mvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.mvc.model.Usuario;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alisson H. Silva
 */
public class UsuarioEdicaoController implements Initializable {

    @FXML
    private Button btFechar;

    @FXML
    private Button btNovo;

    @FXML
    private Button btSalvar;

    @FXML
    private ComboBox<String> cbStatus;

    @FXML
    private TextField edLogin;

    @FXML
    private TextField edNome;

    @FXML
    private PasswordField edSenha;
    
    
    private boolean OkClicked = false;

    @FXML
    void btFecharClick(ActionEvent event) {
    }

    @FXML
    void btNovoClick(ActionEvent event) {
    }

    @FXML
    void btSalvarClick(ActionEvent event) {
    }

   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }

    void setDialogStage(Stage dialogStage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setUsuario(Usuario usu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isOkClicked() {
        return OkClicked;
    }

    
    
}
