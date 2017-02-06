package edu.insightr.spellmonger;

import edu.insightr.spellmonger.game.net.GameClient;
import edu.insightr.spellmonger.game.net.GameServer;
import edu.insightr.spellmonger.game.packets.Packet00Login;
import edu.insightr.spellmonger.model.MultiPlayer;
import edu.insightr.spellmonger.model.cards.Card;
import edu.insightr.spellmonger.model.cards.Creature;
import edu.insightr.spellmonger.model.Player;
import edu.insightr.spellmonger.model.MyModel;
import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.util.Duration;

//import static edu.insightr.spellmonger.model.SpellmongerApp.app;

import static edu.insightr.spellmonger.MenuController.app;
import static edu.insightr.spellmonger.model.SpellmongerApp.setIgMsg;


public class Controller extends Application {

    //ALL GAME IS PLAYED ON SERVER ONLY
    //PLAYERS SEND THE ACTION THEY WANNA DO TO THE SERVER (socket 1331)
    //YOU CAN CREATE OR JOIN SERVER

    @Override
    public void start(final Stage primaryStage) {

        try {
            final URL url = getClass().getResource("/Board.fxml");
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            final AnchorPane root = fxmlLoader.load();
            final Scene scene = new Scene(root, 1050, 650);

            Image cursorImage = new Image("img/cursor-basic.png");
            scene.setCursor(new ImageCursor(cursorImage));

            scene.getStylesheets().add(getClass().getResource("/design").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException ex) {
            System.err.println("Loading error: " + ex);
        }
        primaryStage.setTitle("cards Game");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    @FXML
    private Rectangle player1Box, player2Box;

    @FXML
    private Label namePlayer1, namePlayer2;

    @FXML
    private Button draw1Button, draw2Button;

    @FXML
    private Label hpPlayer1, hpPlayer2, manaPlayer1, manaPlayer2;

    @FXML
    private ScrollPane hand1, hand2;

    @FXML
    private HBox handP1, handP2;

    @FXML
    private GridPane handG1, handG2;

    @FXML
    private Label gameMsg, currHP, currAttack;

    @FXML
    private ScrollPane board1, board2;

    @FXML
    private HBox boardP1, boardP2;

    //@FXML
    //private ProgressBar TimeToPlay;

    @FXML
    private GridPane boardG1, boardG2;

    @FXML
    private ImageView currentCard, discard1, discard2;

    private MyModel Model = new MyModel();

    public static void main(String[] args) {
        launch(args);
    }

    private void makeItFade(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(5000), node);
        ft.setFromValue(1.0);
        ft.setToValue(0.5);
        ft.setRate(5);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }

    private void turnEnded() { // actually just checks dead crea then call real end of turn method (go rename)

        //System.out.println("turnEnded entered - checking deaths");
        FadeTransition ft = new FadeTransition();
        ArrayList<Creature> temp = Model.checkdeadcrea();

        //System.out.println("last death not empty : size = "+app.getLastDeadCrea().size());
        if (temp.isEmpty()) {
            turnEnded2();
        } else {

            //System.out.println("Anim should start");

            for (Creature crea : temp) {
                ft = new FadeTransition(Duration.millis(3000), crea.getPic()); //3k
                ft.setFromValue(1.0);
                ft.setToValue(0.5);
                ft.setRate(3); //3
                ft.setCycleCount(1); //1
                ft.play();
                //ft.setOnFinished(e -> turnEnded2());
            }
            ft.setOnFinished(e -> turnEnded2());
                /*ft.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent){
                        turnEnded2();
                    }
                });*/
        }
    }

    private void animCardPlayed(Node node) {
        double initY = node.getLayoutY();

        FadeTransition ft = new FadeTransition(Duration.millis(3000), node);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setRate(3);
        ft.setCycleCount(1);

        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(2000), node);
        translateTransition.setFromY(initY);
        translateTransition.setToY(initY - 50);
        translateTransition.setCycleCount(1);

        RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(2000), node);
        rotateTransition.setByAngle(360f);
        rotateTransition.setCycleCount(1);

        ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(1000), node);
        scaleTransition.setToX(1f);
        scaleTransition.setToY(0.3f);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                ft,
                translateTransition,
                rotateTransition,
                scaleTransition
        );
        parallelTransition.setCycleCount(1);
        parallelTransition.play();

        parallelTransition.onFinishedProperty().set(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                refreshHand(Model.getCurrent()); // getOppo
            }
        });

    }

    private void makeItNormal(Node node) {
        // should stop fading player box
    }

    public void initialize() {
        hand2.setDisable(true);
        hand1.setDisable(true);
        refreshHand(Model.getPlayer(1));
        refreshHand(Model.getPlayer(2));
        refreshPlayerInfo(Model.getCurrent(), Model.getOpponent());
        displayInitialPlayers();

        Packet00Login loginPacket = new Packet00Login(app.getPlayer1().getName());
        if (app.getSocketServer() != null) {
            try {
                app.getSocketServer().addConnection((MultiPlayer) app.getPlayer1(), loginPacket);
            } catch (Exception e) {
                //System.out.println("error :"+e.getStackTrace());
                e.getStackTrace();
            }
        }
        loginPacket.writeData(app.getSocketClient());
    }

    void addCursorEffect(Node node) {
        Image imageCursorExit = new Image("img/cursor-basic.png");
        Image imageCursorEnter = new Image("img/cursor-hover.png");

        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                (node.getScene()).setCursor(new ImageCursor(imageCursorEnter));
            }
        });

        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                (node.getScene()).setCursor(new ImageCursor(imageCursorExit));
            }
        });
    }

    private void refreshBoard(List<Creature> creaOnBoard) {
        displayBoard(creaOnBoard);
    }

    @FXML
    private void draw(ActionEvent event) {
        resfreshIGMsg();
        if (event.getSource() == draw1Button) {
            Model.DrawPlayer(1);
            hand2.setDisable(true);
            hand1.setDisable(false);
        } else if (event.getSource() == draw2Button) {
            Model.DrawPlayer(2);
            hand1.setDisable(true);
            hand2.setDisable(false);
        }

        refreshPlayerInfo(Model.getCurrent(), Model.getOpponent());
        if (Model.GetSizeHand(1) < 5) {
            Model.DrawCard(Model.getCurrent(), Model.getOpponent());
            draw1Button.setDisable(true);
            draw2Button.setDisable(true);
            refreshPlayerInfo(Model.getCurrent(), Model.getOpponent());
            refreshHand(Model.getCurrent());
            resfreshIGMsg();
        } else {
            setIgMsg("You have already 5 cards! Play before you draw");
            refreshPlayerInfo(Model.getCurrent(), Model.getOpponent());
            refreshHand(Model.getCurrent());
            resfreshIGMsg();
        }
    }

    @FXML
    void playNoCard() {
        refreshHand(Model.getCurrent());
        refreshHand(Model.getOpponent());
        Model.CardPlay();
        //refreshBoard(Model.CheckCreaBoard());
        refreshDiscard();
        turnEnded();
    }

    private void turnEnded2() {

        //System.out.println("Turn ends... (turnEnded2 starts)");
        refreshBoard(Model.CheckCreaBoard());
        Model.TurnEndModel();
        if (Model.getCurrent().equals(Model.getPlayer(1))) {
            player2Box.setFill(Color.rgb(173, 237, 125));
            player1Box.setFill(Color.rgb(255, 240, 175));
        } else {
            player1Box.setFill(Color.rgb(173, 237, 125));
            player2Box.setFill(Color.rgb(255, 240, 175));
        }
        if (Model.getCurrent().equals(Model.getPlayer(1))) {
            makeItFade(player1Box);
            makeItNormal(player2Box);
            draw1Button.setDisable(false);
            draw2Button.setDisable(true);
            setIgMsg(Model.MsgIg() + "\nEnd of turn " + Model.getOpponent().getName());
        } else if (Model.getCurrent().equals(Model.getPlayer(2))) {
            makeItFade(player2Box);
            makeItNormal(player1Box);
            draw1Button.setDisable(true);
            draw2Button.setDisable(false);
            setIgMsg(Model.MsgIg() + "\nEnd of turn " + Model.getOpponent().getName());
        }
        Model.setMsg(Model.MsgIg() + "\n" + Model.getCurrent().getName() + " to draw");
        if (Model.getCurrent().equals(Model.getPlayer(1))) {
            hand2.setDisable(true);
            hand1.setDisable(false);
        } else {
            hand1.setDisable(true);
            hand2.setDisable(false);
        }
        refreshPlayerInfo(Model.getCurrent(), Model.getOpponent());
        resfreshIGMsg();
        if (Model.CheckWinner()) {
            //myController.loadScreen(Main.Score_ID, Main.Score_FILE);
            //myController.setScreen(Main.Score_ID);
            //MenuController ctrll = new MenuController();
            //ctrll.viewScore();
            System.exit(0);
        }
    }

    private void refreshDiscard() {
        if (Model.getCurrent().getDiscard().size() > 0) {
            Card lastDiscard = (Card) Model.getCurrent().getDiscard().get(Model.getCurrent().getDiscard().size() - 1);
            Image image = lastDiscard.getImg();
            ImageView pic = new ImageView();
            if (Model.getCurrent().equals(Model.getPlayer(1))) {
                discard1.setImage(image);
                pic = discard1;
            } else {
                discard2.setImage(image);
                pic = discard2;
            }
            pic.hoverProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    refreshCurrCard(image);
                    refreshCurrStats(lastDiscard);
                }
            });
            addCursorEffect(pic);
        }
        if (Model.getOpponent().getDiscard().size() > 0) {
            Card lastDiscard = (Card) Model.getOpponent().getDiscard().get(Model.getOpponent().getDiscard().size() - 1);
            Image image = lastDiscard.getImg();
            ImageView pic = new ImageView();
            if (Model.getOpponent().equals(Model.getPlayer(1))) {
                discard1.setImage(image);
                pic = discard1;
            } else {
                discard2.setImage(image);
                pic = discard2;
            }
            pic.hoverProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    refreshCurrCard(image);
                    refreshCurrStats(lastDiscard);
                }
            });
            addCursorEffect(pic);
        }
    }

    private void goPlayCard(Card card, Player currentPlayer, Player opponent, Node cardToMove) {

        //System.out.println("current player : " + Model.getCurrent().getName());

        if (Model.GoPlayCardModel(card, currentPlayer, opponent, cardToMove)) {
            animCardPlayed(cardToMove);
            Model.setMsg("");
            //effect if crea dies
            //refreshBoard(app.getAllCreaOnBoard()); // test - remettre (go voir si peut etre injecter dans turnEnded
            removecardhand(card, currentPlayer);
            //refreshHand(currentPlayer); // now in anim
            Creature.displayGroupOfCrea(Model.CheckCreaBoard());//affichage crea du board raffraichis
            refreshBoard(Model.CheckCreaBoard());//refresh main + crea
            //refreshHand(currentPlayer); //doesnt allow animation
            refreshPlayerInfo(Model.getCurrent(), Model.getOpponent());
            /*  if (currentPlayer.getEnergy() == 0) { // à décommenter si on veut faire passer le tour automatiquement lorsque le joueur n'a plus d'energie.
                   app.cardPlayed();
                   turnEnded();
               }*/
        } else {
            resfreshIGMsg();
        }
        refreshDiscard();
    }

    private void removecardhand(Card card, Player currentPlayer) {
        Model.RemoveHandCard(card, currentPlayer);
    }


    @FXML
    private void refreshCurrCard(Image img) {
        currentCard.setImage(img);
    }

    private void resfreshIGMsg() {
        gameMsg.setText(Model.MsgIg());
    }

    void resfreshIGMsgintoapp() {
        gameMsg.setText(Model.MsgIg());
    }

    private void refreshCurrStats(Card card) {
        if (card instanceof Creature) {
            int hp = ((Creature) card).getHp();
            int attack = ((Creature) card).getAttack();
            if (hp > 0) {
                currHP.setText("HP : " + hp);
                currAttack.setText("Atk : " + attack);
            } else {
                currHP.setText("");
                currAttack.setText("DEAD");
            }
        } else {
            currHP.setText("");
            currAttack.setText("");
        }
    }

    private void refreshHand(Player currPlayer) {
        int j = 0;
        Image img;
        ImageView pic;

        if (currPlayer.equals(Model.getPlayer(1))) {

            hand1.setContent(null);
            handP1 = new HBox();

            for (int i = 0; i < currPlayer.getHand().size(); i++) {

                img = currPlayer.getHand().get(i).getImg();
                pic = new ImageView(img);
                final Image img0 = img;
                final ImageView movingPic = pic;

                int index = i;

                pic.hoverProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        refreshCurrCard(img0);
                        if (index < currPlayer.getHand().size()) {
                            refreshCurrStats(currPlayer.getHand().get(index));
                        }
                    }
                });

                addCursorEffect(pic);

                pic.setOnMouseClicked(e ->
                        goPlayCard(Model.getCurrent().getHand().get(index), Model.getCurrent(), Model.getOpponent(), movingPic)
                );

                hand1.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                hand1.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

                handG1.setAlignment(Pos.CENTER);
                handG1.setPadding(new Insets(5, 5, 5, 5));
                handG1.setHgap(20);
                handP1.setPadding(new Insets(14, 2, 2, 14));
                handP1.setSpacing(20);

                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setFillWidth(true);
                columnConstraints.setHgrow(Priority.ALWAYS);
                handG1.getColumnConstraints().add(columnConstraints);

                pic.setFitWidth(110);
                pic.setFitHeight(130);
                pic.setImage(img);

                handG1.add(pic, j, 0);
                handP1.getChildren().add(pic);

                GridPane.setMargin(pic, new Insets(2, 2, 2, 2));
                hand1.setContent(handP1);
            }

        } else {

            hand2.setContent(null);
            handP2 = new HBox();

            for (int i = 0; i < currPlayer.getHand().size(); i++) {

                img = currPlayer.getHand().get(i).getImg();
                pic = new ImageView(img);
                final ImageView movingPic = pic;
                final Image img0 = img;

                int index = i;

                pic.hoverProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        refreshCurrCard(img0);
                        if (index < currPlayer.getHand().size()) {
                            refreshCurrStats(currPlayer.getHand().get(index));
                        }
                    }
                });

                addCursorEffect(pic);

                pic.setOnMouseClicked(e ->
                        goPlayCard(Model.getCurrent().getHand().get(index), Model.getCurrent(), Model.getOpponent(), movingPic)
                );

                hand2.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                hand2.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

                handG2.setAlignment(Pos.CENTER);
                handG2.setPadding(new Insets(5, 5, 5, 5));
                handG2.setHgap(20);
                handP2.setPadding(new Insets(14, 2, 2, 14));
                handP2.setSpacing(20);

                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setFillWidth(true);
                columnConstraints.setHgrow(Priority.ALWAYS);
                handG2.getColumnConstraints().add(columnConstraints);

                pic.setFitWidth(110);
                pic.setFitHeight(130);
                pic.setImage(img);

                handG2.add(pic, j, 0);
                handP2.getChildren().add(pic);

                GridPane.setMargin(pic, new Insets(2, 2, 2, 2));
                hand2.setContent(handP2);

            }

        }
    }

    private void refreshPlayerInfo(Player currPlayer, Player oppo) {
        if (currPlayer == Model.getPlayer(1)) {
            hpPlayer1.setText("Hp : " + Integer.toString(currPlayer.getHp()));
            manaPlayer1.setText("Energy : " + Integer.toString(currPlayer.getEnergy()));
            hpPlayer2.setText("Hp : " + Integer.toString(oppo.getHp()));
            manaPlayer2.setText("Energy : " + Integer.toString(oppo.getEnergy()));
        } else {
            hpPlayer2.setText("Hp : " + Integer.toString(currPlayer.getHp()));
            manaPlayer2.setText("Energy : " + Integer.toString(currPlayer.getEnergy()));
            hpPlayer1.setText("Hp : " + Integer.toString(oppo.getHp()));
            manaPlayer1.setText("Energy : " + Integer.toString(oppo.getEnergy()));
        }
    }

    private void displayBoard(List<Creature> creaOnBoard) {

        board1.setContent(null);
        board2.setContent(null);
        boardP1 = new HBox();
        boardP2 = new HBox();

        int imageCol = 0;
        Image img;
        ImageView pic;

        for (int i = 0; i < creaOnBoard.size(); i++) {

            img = creaOnBoard.get(i).getImg();
            pic = new ImageView(img);

            if (Model.getCurrent().equals(Model.getPlayer(1))) {
                if (Model.getCurrent().getName().equals(creaOnBoard.get(i).getOwner())) {
                    displayB1(pic, img, imageCol, creaOnBoard.get(i));
                } else {
                    displayB2(pic, img, imageCol, creaOnBoard.get(i));
                }
            } else if (Model.getCurrent().equals(Model.getPlayer(2))) {
                if (Model.getCurrent().getName().equals(creaOnBoard.get(i).getOwner())) {
                    displayB2(pic, img, imageCol, creaOnBoard.get(i));
                } else {
                    displayB1(pic, img, imageCol, creaOnBoard.get(i));
                }
            }
        }
    }

    private void displayB1(ImageView pic, Image img, int j, Card card) {

        board1.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        board1.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        boardG1.setAlignment(Pos.CENTER);
        boardG1.setPadding(new Insets(5, 5, 5, 5));
        boardG1.setHgap(20);
        boardP1.setPadding(new Insets(14, 2, 2, 14));
        boardP1.setSpacing(20);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setFillWidth(true);
        columnConstraints.setHgrow(Priority.ALWAYS);
        boardG1.getColumnConstraints().add(columnConstraints);

        pic.setFitWidth(110);
        pic.setFitHeight(130);
        pic.setImage(img);

        pic.hoverProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                refreshCurrCard(img);
                refreshCurrStats(card);
            }
        });

        //pic.setId(Integer.toString(card.getIdCode()));

        ((Creature) card).setPic(pic);

        addCursorEffect(pic);

        boardG1.add(pic, j, 0);
        boardP1.getChildren().add(pic);

        GridPane.setMargin(pic, new Insets(2, 2, 2, 2));
        board1.setContent(boardP1);
    }

    private void displayB2(ImageView pic, Image img, int j, Card card) {

        board2.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        board2.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        boardG2.setAlignment(Pos.CENTER);
        boardG2.setPadding(new Insets(5, 5, 5, 5));
        boardG2.setHgap(20);
        boardP2.setPadding(new Insets(14, 2, 2, 14));
        boardP2.setSpacing(20);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setFillWidth(true);
        columnConstraints.setHgrow(Priority.ALWAYS);
        boardG2.getColumnConstraints().add(columnConstraints);

        pic.setFitWidth(110);
        pic.setFitHeight(130);
        pic.setImage(img);

        pic.hoverProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                refreshCurrCard(img);
                refreshCurrStats(card);
            }
        });

        ((Creature) card).setPic(pic);

        addCursorEffect(pic);

        boardG2.add(pic, j, 0);
        boardP2.getChildren().add(pic);

        GridPane.setMargin(pic, new Insets(2, 2, 2, 2));
        board2.setContent(boardP2);
    }

    private void displayInitialPlayers() {
        namePlayer1.setText(Model.getPlayer(1).getName());
        namePlayer2.setText(Model.getPlayer(2).getName());
    }

}
