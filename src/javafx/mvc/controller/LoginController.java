/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.controller;

import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.mvc.dao.Criterios;
import javafx.mvc.dao.DaoUsuario;
import javafx.mvc.model.Usuario;
import javafx.mvc.services.Conexao;
import javafx.mvc.services.HashSHA2;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label lbErro;

    @FXML
    void btnEntrarClick(ActionEvent event) throws Exception {
        //Código para Validar o Login

        if (isValidLogin()) {
            isAllowed = true;
            dialogStage.close();
        } else {

            Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            Date now = new Date();
            String dataFinal = formatter.format(now);

            this.lbErro.setText(dataFinal + " > Usuário e/ou senha inválidos!");
        }

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

    private Boolean isValidLogin() throws Exception {
        Boolean isValid = false;

        Usuario digitedUser = new Usuario();
        digitedUser.setLogin(txtLogin.getText());
        digitedUser.setSenha(txtSenha.getText());

        DaoUsuario daoUsuario = new DaoUsuario(Conexao.getInstance().getConn());

        Criterios c = new Criterios(" WHERE SHA2(nomeUsuario,'256')='" + HashSHA2.hashSHA2(digitedUser.getLogin()) + "' AND senhaUsuario='" + HashSHA2.hashSHA2(digitedUser.getSenha()) + "' and statusUsuario='Ativo' limit 1;");

        List<Usuario> resultadosDao = (List<Usuario>) daoUsuario.getByCriterios(c);

        System.out.println("minha senha: " + digitedUser.getSenha());
        if (!resultadosDao.isEmpty()) {
            isValid = true;
        }

        return isValid;
    }

}
