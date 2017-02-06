package edu.insightr.spellmonger;

import edu.insightr.spellmonger.game.net.GameClient;
import edu.insightr.spellmonger.game.net.GameServer;
import edu.insightr.spellmonger.model.SpellmongerApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import static javafx.application.Application.launch;

public class MenuController implements Initializable, ControlledScreen {

    public static SpellmongerApp app = new SpellmongerApp();

    ScreenController myController;

    @FXML
    public TextField Login;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setScreenParent(ScreenController screenParent) {
        myController = screenParent;
    }

    public void playMulti() {
        myController.addData("NamePlayer", Login.getText());

        //if there is a server already, join it
        //if not, create it

        System.out.println("Run server ? y / n");
        Scanner in = new Scanner(System.in);
        String answer = in.next();
        if (answer.equals("y")) {
            app.setSocketServer(new GameServer(app));
            app.getSocketServer().start();
            System.out.println("You started the server : your game is joinable");
        } else {
            System.out.println("You did not start the server");
        }

        String ipadddd = "127.0.0.1";
        app.setSocketClient(new GameClient(app, ipadddd)); // was "localhost" then "192.168.1.79"
        app.getSocketClient().start();

        launchGame();
    }

    public void playSolo() {
        myController.addData("NamePlayer", Login.getText());

        app.setSocketClient(new GameClient(app, "localhost"));

        launchGame();
    }

    public void playiA()
    {
        myController.addData("NamePlayer", Login.getText());
        launchGameIa();
    }

    public void viewScore() {
        myController.loadScreen(Main.Score_ID, Main.Score_FILE);
        myController.setScreen(Main.Score_ID);
    }

    public void launchGame() {
        app.initPlayer(Login.getText(), "Opponent");
        app.setCurrentPlayer(app.getPlayer1());
        app.setOpponent(app.getPlayer2());
        app.drawFirstTwoCards();
        Stage pstage = new Stage();
        Controller ctrl = new Controller();
        ctrl.start(pstage);
    }

    public void launchGameIa() {
        app.initPlayer(Login.getText(), "IA");
        app.setCurrentPlayer(app.getPlayer1());
        app.setOpponent(app.getPlayer2());
        app.drawFirstTwoCards();
        Stage pstage = new Stage();
        ControllerIA ctrl = new ControllerIA();
        ctrl.start(pstage);
    }

}
