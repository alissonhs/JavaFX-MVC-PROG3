/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.mvc.dao.Criterios;
import javafx.mvc.dao.DaoUsuario;
import javafx.mvc.model.Usuario;
import javafx.mvc.services.Conexao;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Marlon
 */
public class UsuarioListaController implements Initializable {

    @FXML
    private Button btAlterar;

    @FXML
    private Button btExcluir;

    @FXML
    private Button btFechar;

    @FXML
    private Button btInserir;

    @FXML
    private Button btPesquisar;

    @FXML
    private Button btSalvar;

    @FXML
    private ComboBox<?> cbStatus;

    @FXML
    private TableColumn<?, ?> tableViewId;

    @FXML
    private TableColumn<?, ?> tableViewLogin;

    @FXML
    private TableColumn<?, ?> tableViewNome;

    @FXML
    private TableColumn<?, ?> tableViewStatus;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPesquisarNome;

    @FXML
    private TextField txtSenha;

    @FXML
    private TableView<Usuario> tableViewUsuario;

    @FXML
    void btAlterarClick(ActionEvent event) {
    }

    @FXML
    void btExcluirClick(ActionEvent event) {
    }

    @FXML
    void btInserirClick(ActionEvent event) throws Exception {

        Usuario usu = new Usuario();
        boolean okClicked = showDialog(usu);
        if (okClicked) {
            this.du.salvar(usu);
            listarUsuarios();
        }

    }

    @FXML
    void btPesquisarClick(ActionEvent event) throws Exception {

        listarUsuarios();

    }

    @FXML
    void btSalvarClick(ActionEvent event) {
    }

    @FXML
    void txtId(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        du = new DaoUsuario(Conexao.getInstance().getConn());
    }

    private DaoUsuario du;
    private List<Usuario> lista;
    private ObservableList<Usuario> listaObserver;

    private void listarUsuarios() throws Exception {

        tableViewId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableViewNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableViewLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        tableViewStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        Criterios c = new Criterios("");
        if (!txtNome.getText().trim().isEmpty()) {
            String par = txtNome.getText();
            if (!par.matches("[A-Za-z0-9]")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Caracteres Inválidos");
                alert.show();
                return;
            }
            c.setCriterio(" where nomeUsuario like '%" + par + "%'");
        }
        
        lista = (List<Usuario>) du.getByCriterios(c);
        listaObserver = FXCollections.observableArrayList(lista);
        
        tableViewUsuario.setItems(listaObserver);

    }

    private boolean showDialog(Usuario usu) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UsuarioListaController.class.getResource("/javafx/mvc/view/UsuarioEdicao.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Usuário.");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        UsuarioEdicaoController u = loader.getController();
        u.setDialogStage(dialogStage);
        u.setUsuario(usu);

        //Aqui ele mostra a TELA '-' //
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.showAndWait();

        return u.isOkClicked();

    }

}
