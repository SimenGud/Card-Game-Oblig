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
import no.ntnu.idatx2003.oblig4.cardgame.models.*;

import java.util.stream.Collectors;

/**
 * A simple GUI for a card game.
 *
 * The GUI displays a hand of cards and provides buttons for
 * dealing a new hand and checking the hand.
 *
 * @author Simen Gudbrandsen
 * @version 0.1
 * @since 10.03.2025
 */

public class CardGameGUI extends Application implements CardGameInterface {
  private HBox cardDisplay;
  private Label resultLabel;
  private CardGameController cardGameController;

  @Override
  public void start(Stage primaryStage) {
    cardGameController = new CardGameController(); // Create game controller

    cardDisplay = new HBox(10);
    cardDisplay.setAlignment(Pos.CENTER);

    resultLabel = new Label("Click 'Deal Cards' to start");
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
    HandOfCards hand = cardGameController.dealHand();

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
    HandOfCards hand = cardGameController.getLastHand();
    if (hand == null) {
      resultLabel.setText("No hand dealt yet!");
      return;
    }

    // Calculate sum of all cards on hand
    int sum = hand.getCards().stream().mapToInt(PlayingCard::getFace).sum();

    // Gives all hearts on hand
    String hearts = hand.getCards().stream()
        .filter(card -> card.getSuit() == 'H')
        .map(PlayingCard::getAsString)
        .collect(Collectors.joining(" "));
    if (hearts.isEmpty()) hearts = "No Hearts";

    // Check if hand contains any queen of spades
    boolean hasQueenOfSpades = hand.getCards().stream()
        .anyMatch(card -> card.getSuit() == 'S' && card.getFace() == 12);

    // Check for flush
    boolean hasFlush = hand.hasFlush();

    resultLabel.setText(String.format("Sum: %d | Hearts: %s | Queen of Spades: %b | 5-Flush: %b",
        sum, hearts, hasQueenOfSpades, hasFlush));
  }


  public static void main(String[] args) {
    launch(args);
  }
}
