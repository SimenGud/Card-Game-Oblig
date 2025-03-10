package no.ntnu.idatx2003.oblig4.cardgame.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import no.ntnu.idatx2003.oblig4.cardgame.logic.*;
import no.ntnu.idatx2003.oblig4.cardgame.logic.CardImageMapper;
import no.ntnu.idatx2003.oblig4.cardgame.models.*;

import java.util.List;
import java.util.stream.Collectors;

public class CardGameGUI extends Application implements CardGameInterface {
  private HBox cardDisplay;
  private Label resultLabel;
  private GameController gameController;

  @Override
  public void start(Stage primaryStage) {
    gameController = new GameController(); // Create game controller

    cardDisplay = new HBox(10);
    cardDisplay.setAlignment(Pos.CENTER);

    resultLabel = new Label("Result: ");
    resultLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");

    Button dealButton = new Button("Deal Cards");
    dealButton.setOnAction(e -> dealCards());

    Button checkHandButton = new Button("Check Hand");
    checkHandButton.setOnAction(e -> checkHand());

    VBox layout = new VBox(20, cardDisplay, dealButton, checkHandButton, resultLabel);
    layout.setAlignment(Pos.CENTER);

    Image backgroundImage = new Image(getClass().getResource("/background/background.jpg").toExternalForm());
    layout.setBackground(new Background(new BackgroundImage(
        backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true)
    )));

    primaryStage.setScene(new Scene(layout, 800, 600));
    primaryStage.setTitle("Card Game");
    primaryStage.show();
  }

  @Override
  public void dealCards() {
    cardDisplay.getChildren().clear();
    HandOfCards hand = gameController.dealHand();

    for (PlayingCard card : hand.getCards()) {
      Image cardImage = new Image(getClass().getResource("/cards/" + CardImageMapper.getImageForCard(card)).toExternalForm());
      ImageView cardView = new ImageView(cardImage);
      cardView.setFitWidth(100);
      cardView.setPreserveRatio(true);
      cardDisplay.getChildren().add(cardView);
    }
  }

  @Override
  public void checkHand() {
    List<PlayingCard> hand = cardDisplay.getChildren().stream()
        .filter(node -> node instanceof ImageView)
        .map(node -> ((ImageView) node).getImage().getUrl())
        .map(url -> url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".png")))
        .map(CardParser::parseCard)
        .collect(Collectors.toList());

    int sum = hand.stream().mapToInt(PlayingCard::getFace).sum();
    boolean hasQueenOfSpades = hand.stream().anyMatch(card -> card.getSuit() == 'S' && card.getFace() == 12);

    resultLabel.setText("Sum: " + sum + " | Queen of Spades: " + hasQueenOfSpades);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
