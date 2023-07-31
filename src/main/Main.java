package main;

import Database.DBConnection;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application{


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 600, 800));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        DBConnection.makeConnection();
        launch(args);
        DBConnection.closeConnection();
    }

}
