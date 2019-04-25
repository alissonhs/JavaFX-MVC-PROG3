/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.mvc.dao.DaoUsuario;
import javafx.mvc.model.Usuario;
import javafx.mvc.services.Conexao;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author thiag
 */
public class AlterarSenhaController implements Initializable {

    DaoUsuario du = new DaoUsuario(Conexao.getInstance().getConn());

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    private PasswordField txtNovaSenha;

    @FXML
    private PasswordField txtNovaSenhaConf;

    @FXML
    void btnSalvarClick(ActionEvent event) throws Exception {

        if (!txtNovaSenha.getText().equals(txtNovaSenhaConf.getText())) {
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setContentText("As senhas não são iguais!");
            Optional<ButtonType> opcao = erro.showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setContentText("Deseja realmente alterar a senha?");
        Optional<ButtonType> opcao = confirm.showAndWait();
        if (opcao.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {

            this.user.setSenha(txtNovaSenha.getText());

            this.isFinished = true;
            this.dialogStage.close();
        }

    }

    @FXML
    void btnCancelarClick(ActionEvent event) {
        this.dialogStage.close();
    }

    private Stage dialogStage;
    private boolean isFinished = false;
    private Usuario user;

    public boolean isIsFinished() {
        return isFinished;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Usuario getUser() {
        return user;
    }

}
