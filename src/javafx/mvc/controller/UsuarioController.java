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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.mvc.services.HashSHA2;
import javafx.scene.Scene;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Marlon
 */
public class UsuarioController implements Initializable {

    private Stage stagePrincipal;

    public Stage getStagePrincipal() {
        return stagePrincipal;
    }

    public void setStagePrincipal(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }

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
    private Button btnAlterarSenha;

    @FXML
    void btnAlterarSenhaClick(ActionEvent event) throws IOException, Exception {
        //Abrir tela de alterar senha
        //Stage primary = (Stage) this.btCancelar.getParent().getScene().getRoot().get
        Stage alterarSenha = new Stage();
        FXMLLoader loaderSenha = new FXMLLoader();
        loaderSenha.setLocation(AlterarSenhaController.class.getResource("/javafx/mvc/view/AlterarSenha.fxml"));

        AnchorPane pageSenha = (AnchorPane) loaderSenha.load();

        alterarSenha.setTitle("Alterar senha!");
        Scene sceneSenha = new Scene(pageSenha);
        // sceneSenha.getStylesheets().add(this.getClass().getResource("Estilo1.css").toString());

        alterarSenha.setScene(sceneSenha);

        AlterarSenhaController cSenha = loaderSenha.getController();
        cSenha.setDialogStage(alterarSenha);

        Criterios c = new Criterios(" where idUsuario=" + this.txtId.getText());
        List<Usuario> users = (List<Usuario>) du.getByCriterios(c);
        cSenha.setUser(users.get(0));
        //alterarSenha.initOwner(this);
        alterarSenha.initModality(Modality.APPLICATION_MODAL);
        alterarSenha.showAndWait();

        txtSenha.setText(cSenha.getUser().getSenha());

    }

    @FXML
    void btCancelarClick(ActionEvent event) throws Exception {
        camposEnabled(true);
        btEnabled(1);
        limparCampos();
        tabSelected(0);
        listarUsuarios();
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
        listarUsuarios();
        tabSelected(0);
    }

    @FXML
    void btInserirClick(ActionEvent event) throws Exception {
        camposEnabled(false);
        btEnabled(2);
        limparCampos();

        txtId.setText("0");

        cbStatus.getSelectionModel().selectFirst();

    }

    @FXML
    void btPesquisarClick(ActionEvent event) throws Exception {
        listarUsuarios();
    }

    @FXML
    void btSalvarClick(ActionEvent event) throws Exception {

        Usuario usuario = new Usuario();
        usuario.setId(Integer.parseInt(txtId.getText()));
        usuario.setNome(txtNome.getText().trim());
        usuario.setLogin(txtLogin.getText().toLowerCase());
        usuario.setSenha(txtSenha.getText());
        usuario.setStatus(cbStatus.getValue());

        ArrayList<String> listaDeErros = new ArrayList<>();
        if (usuario.getNome().isEmpty()) {
            listaDeErros.add("Campo nome não pode ser vazio!");
        }
        if (usuario.getNome().length() > 150) {
            listaDeErros.add("Campo nome não pode ter mais que 150 caracteres!");
        }
        if (usuario.getLogin().isEmpty()) {
            listaDeErros.add("Campo usuario/login não pode ser vazio!");
        }
        if (usuario.getLogin().length() > 45) {
            listaDeErros.add("Campo usuario/login não pode ter mais que 45 caracteres!");
        }
        if (usuario.getId() == 0) {
            if (usuario.getSenha().isEmpty()) {
                listaDeErros.add("Campo senha não pode ser vazio!");
            }
        }
        if (usuario.getStatus().length() < 2) {
            listaDeErros.add("Campo status inválido!");
        }

        String filtro;
        if (usuario.getId() == 0) {
            filtro = " where SHA2(loginUsuario,'256') = '" + HashSHA2.hashSHA2(usuario.getLogin()) + "' limit 1;";
        } else {
            filtro = " where SHA2(loginUsuario,'256') = '" + HashSHA2.hashSHA2(usuario.getLogin()) + "' and idUsuario != " + usuario.getId() + " limit 1;";
        }
        Criterios c = new Criterios(filtro);
        List<Usuario> listaUsuarioDuplicado = (List<Usuario>) this.du.getByCriterios(c);
        if (!listaUsuarioDuplicado.isEmpty()) {
            listaDeErros.add("Login já registrado!");
        }

        if (!listaDeErros.isEmpty()) {

            Alert confirm = new Alert(Alert.AlertType.ERROR);

            confirm.setHeaderText("Os seguintes problemas foram encontrados:");

            for (String str : listaDeErros) {
                confirm.setContentText(confirm.getContentText() + "\r\n• " + str);
            }

            Optional<ButtonType> opcao = confirm.showAndWait();

            if (opcao.get()
                    .getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {

            }
        } else {
            this.du.salvar(usuario);
            camposEnabled(true);
            btEnabled(1);
            limparCampos();
            listarUsuarios();
            tabSelected(0);
        }
    }

    @FXML
    void tableViewUsuarioCliked(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btEnabled(1);
        camposEnabled(true);
        limparCampos();
        tabSelected(0);

        du = new DaoUsuario(Conexao.getInstance().getConn());
        try {
            listarUsuarios();

        } catch (Exception ex) {
            Logger.getLogger(UsuarioController.class
                    .getName()).log(Level.SEVERE, null, ex);
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
            txtSenha.setText("");
            cbStatus.setValue(u.getStatus());

            SingleSelectionModel<Tab> tabModel = tabUsuario.getSelectionModel();
            tabModel.select(1);
            camposEnabled(false);
            txtSenha.setDisable(true);
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
                btnAlterarSenha.setDisable(true);
                break;
            case 2:
                btInserir.setDisable(true);
                btSalvar.setDisable(false);
                btExcluir.setDisable(true);
                btCancelar.setDisable(false);
                btnAlterarSenha.setDisable(true);
                break;
            case 3:
                btInserir.setDisable(true);
                btSalvar.setDisable(false);
                btExcluir.setDisable(false);
                btCancelar.setDisable(false);
                btnAlterarSenha.setDisable(false);
                break;
            default:
                btInserir.setDisable(false);
                btSalvar.setDisable(true);
                btExcluir.setDisable(true);
                btCancelar.setDisable(true);
                btnAlterarSenha.setDisable(true);
        }
    }

    private void camposEnabled(boolean b) {
        txtId.setDisable(true);
        txtNome.setDisable(b);
        txtLogin.setDisable(b);
        txtSenha.setDisable(b);
        cbStatus.setDisable(b);
        txtSenha.setDisable(b);
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

    }

    private void tabSelected(int id) {
        SingleSelectionModel<Tab> tabModel = tabUsuario.getSelectionModel();
        tabModel.select(id);
    }
}
