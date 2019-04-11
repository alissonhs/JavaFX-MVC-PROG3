package javafx.mvc;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.mvc.controller.LoginController;
import javafx.mvc.controller.PrincipalController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Alisson H. Silva
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PrincipalController.class.getResource("/javafx/mvc/view/Principal.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        PrincipalController c = loader.getController();
        c.setStagePrincipal(primaryStage);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sistema Cadastro");
        primaryStage.show();
        
        // Abre a tela de Login
        
        Stage login = new Stage();
        FXMLLoader loaderLogin = new FXMLLoader();
        loaderLogin.setLocation(LoginController.class.getResource("/javafx/mvc/view/login.fxml"));
        AnchorPane pageLogin = (AnchorPane) loaderLogin.load(); 
        login.setTitle("Login!");
        Scene sceneLogin = new Scene(pageLogin);
        sceneLogin.getStylesheets().add(this.getClass().getResource("Estilo1.css").toString());
        scene.getStylesheets().add(this.getClass().getResource("Estilo1.css").toString());

        
        
        login.setScene(sceneLogin);
        
        LoginController cLogin = loaderLogin.getController();
        cLogin.setDialogStage(login);
        
        login.initOwner(primaryStage);
        login.initModality(Modality.APPLICATION_MODAL);
        login.showAndWait();
        
        if (!cLogin.isIsAllowed()) {
            primaryStage.close();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
