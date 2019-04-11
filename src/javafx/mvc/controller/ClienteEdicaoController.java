package javafx.mvc.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.mvc.model.Cliente;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClienteEdicaoController implements Initializable {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    private ComboBox<String> cbSituacao;

    @FXML
    private TextField txtCnpj;

    @FXML
    private TextField txtNome;

    @FXML
    void btnCancelarClick(ActionEvent event) {
        this.dialogStage.close();
    }

    @FXML
    void btnSalvarClick(ActionEvent event) {

        this.cliente.setNome(txtNome.getText());
        this.cliente.setCnpj(txtCnpj.getText());
        this.cliente.setSituacao(cbSituacao.getValue());
        this.okClicked = true;
        this.dialogStage.close();

    }

    private Stage dialogStage;
    private boolean okClicked = false;
    private Cliente cliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ArrayList<String> opcoes = new ArrayList<String>();
        opcoes.add("Ativo");
        opcoes.add("Inativo");
        cbSituacao.setItems(FXCollections.observableArrayList(opcoes));

        txtCnpj.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                String value = txtCnpj.getText();
                if (value != null) {
                    value = value.replaceAll("[^0-9]", "");
                }

                if (!newValue) {

                    if (txtCnpj.getText().length() == 11) {

                        value = value.replaceFirst("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})$", "$1.$2.$3-$4");
                    }
                    if (txtCnpj.getText().length() == 14) {
                        value = value.replaceFirst("([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})", "$1.$2.$3/$4-$5");
                    }
                }
                txtCnpj.setText(value);
                if (txtCnpj.getText() != value) {
                    txtCnpj.setText("");
                    txtCnpj.insertText(0, value);
                }
            }
        });

    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Cliente getCliente() {

        return cliente;
    }

    public void setCliente(Cliente cliente) {
        txtNome.setText(cliente.getNome());
        txtCnpj.setText(cliente.getNome());
        cbSituacao.setValue(cliente.getSituacao());

        this.cliente = cliente;
    }

}
