package edu.insightr.spellmonger;


import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.stage.Stage;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.event.EventHandler;
import javafx.scene.Scene;
//import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
//import javafx.scene.control.PasswordField;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;





public class login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

            primaryStage.setTitle("JavaFX Welcome");

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));

            Scene scene = new Scene(grid, 400, 400);



            Text scenetitle = new Text("Welcome");
            scenetitle.setFont(Font.font("", FontWeight.NORMAL, 20));
            grid.add(scenetitle, 0, 0, 2, 1);

            Label userName = new Label("User Name:");
            grid.add(userName, 0, 1);

            TextField userTextField = new TextField();
            grid.add(userTextField, 1, 1);



            Button button1 = new Button("Play");


            Button button2= new Button("View score");
            grid.add(button1, 1, 2, 1, 2);
            grid.add(button2, 2, 2, 1, 2);




        primaryStage.setScene(scene);

            primaryStage.show();


    }


}
