/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.mvc.dao.Criterios;
import javafx.mvc.dao.DaoUsuario;
import javafx.mvc.model.Usuario;
import javafx.mvc.services.Conexao;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Marlon
 */
public class UsuarioController implements Initializable {

    @FXML
    private Button btCancelar;

    @FXML
    private Button btExcluir;

    @FXML
    private Button btInserir;

    @FXML
    private Button btPesquisar;

    @FXML
    private Button btSalvar;

    @FXML
    private ComboBox<String> cbStatus;

    @FXML
    private TabPane tabUsuario;

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
    private PasswordField txtSenha;

    @FXML
    private TableView<Usuario> tableViewUsuario;

    @FXML
    void btCancelarClick(ActionEvent event) {
        camposEnabled(true);
        btEnabled(1);
        limparCampos();
    }

    @FXML
    void btExcluirClick(ActionEvent event) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(Integer.parseInt(txtId.getText()));

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setContentText("Deseja realmente excluir o Item?");
        Optional<ButtonType> opcao = confirm.showAndWait();
        if (opcao.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
            this.du.remover(usuario);
            listarUsuarios();
        }

        camposEnabled(true);
        btEnabled(1);
        limparCampos();
    }

    @FXML
    void btInserirClick(ActionEvent event) throws Exception {
        camposEnabled(false);
        btEnabled(2);
        limparCampos();

        txtId.setText("0");
    }

    @FXML
    void btPesquisarClick(ActionEvent event) throws Exception {
        listarUsuarios();
    }

    @FXML
    void btSalvarClick(ActionEvent event) throws Exception {

        Usuario usuario = new Usuario();

        usuario.setId(Integer.parseInt(txtId.getText()));
        usuario.setNome(txtNome.getText());
        usuario.setLogin(txtLogin.getText());
        usuario.setSenha(txtSenha.getText());
        usuario.setStatus(cbStatus.getValue());

        this.du.salvar(usuario);

        camposEnabled(true);
        btEnabled(1);

        limparCampos();

        listarUsuarios();

    }

    @FXML
    void tableViewUsuarioCliked(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btEnabled(1);
        camposEnabled(true);
        limparCampos();

        du = new DaoUsuario(Conexao.getInstance().getConn());
        try {
            listarUsuarios();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableViewUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> selecionaUsuario(newvalue));

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
            if (!par.matches("[A-Za-z0-9]+")) {
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

    private void selecionaUsuario(Usuario u) {
        if (u != null) {
            txtId.setText(String.valueOf(u.getId()));
            txtNome.setText(u.getNome());
            txtLogin.setText(u.getLogin());
            txtSenha.setText(u.getSenha());
            cbStatus.setValue(u.getStatus());

            SingleSelectionModel<Tab> tabModel = tabUsuario.getSelectionModel();
            tabModel.select(1);
            camposEnabled(false);
            btEnabled(3);
        }
    }

    private void btEnabled(int id) {
        switch (id) {
            case 1:
                btInserir.setDisable(false);
                btSalvar.setDisable(true);
                btExcluir.setDisable(true);
                btCancelar.setDisable(true);
                break;
            case 2:
                btInserir.setDisable(true);
                btSalvar.setDisable(false);
                btExcluir.setDisable(true);
                btCancelar.setDisable(false);
                break;
            case 3:
                btInserir.setDisable(true);
                btSalvar.setDisable(false);
                btExcluir.setDisable(false);
                btCancelar.setDisable(false);
                break;
            default:
                btInserir.setDisable(false);
                btSalvar.setDisable(true);
                btExcluir.setDisable(true);
                btCancelar.setDisable(true);
        }
    }

    private void camposEnabled(boolean b) {
        txtId.setDisable(true);
        txtNome.setDisable(b);
        txtLogin.setDisable(b);
        txtSenha.setDisable(b);
        cbStatus.setDisable(b);
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtLogin.setText("");
        txtSenha.setText("");

        
        ArrayList<String> opcoes = new ArrayList<String>();
        opcoes.add("Ativo");
        opcoes.add("Inativo");
        cbStatus.setItems(FXCollections.observableArrayList(opcoes));

        SingleSelectionModel<Tab> tabModel = tabUsuario.getSelectionModel();
        tabModel.select(0);
    }
}
