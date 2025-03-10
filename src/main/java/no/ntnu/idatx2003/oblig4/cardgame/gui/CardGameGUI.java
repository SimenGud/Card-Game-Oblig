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
import no.ntnu.idatx2003.oblig4.cardgame.HandOfCards;
import no.ntnu.idatx2003.oblig4.cardgame.PlayingCard;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CardGameGUI extends Application {
  private List<String> deck = new ArrayList<>();
  private HBox cardDisplay;
  private Label resultLabel;

  @Override
  public void start(Stage primaryStage) {
    cardDisplay = new HBox(10);
    cardDisplay.setAlignment(Pos.CENTER); // Ensure cards are centered

    resultLabel = new Label("Result: ");
    resultLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");

    Button dealButton = new Button("Deal Cards");
    dealButton.setOnAction(e -> dealCards());

    Button checkHandButton = new Button("Check Hand");
    checkHandButton.setOnAction(e -> checkHand());

    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER); // Ensure everything is centered
    layout.getChildren().addAll(cardDisplay, dealButton, checkHandButton, resultLabel);

    // Load background image
    Image backgroundImage = new Image(getClass().getResource("/background/background.jpg").toExternalForm());
    BackgroundImage bgImage = new BackgroundImage(
        backgroundImage,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER,
        new BackgroundSize(100, 100, true, true, true, true)
    );
    layout.setBackground(new Background(bgImage));

    Scene scene = new Scene(layout, 800, 600);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Card Game");
    primaryStage.show();
  }


  private void loadDeck() {
    File folder = new File("src/main/resources/cards");
    if (folder.exists() && folder.isDirectory()) {
      for (File file : folder.listFiles()) {
        if (file.getName().endsWith(".png")) {
          deck.add(file.getName());
        }
      }
    }
    Collections.shuffle(deck);
  }

  private void dealCards() {
    cardDisplay.getChildren().clear();
    loadDeck();
    for (int i = 0; i < 5; i++) {
      if (!deck.isEmpty()) {
        String cardName = deck.remove(0);
        Image cardImage = new Image(getClass().getResource("/cards/" + cardName).toExternalForm());
        ImageView cardView = new ImageView(cardImage);
        cardView.setFitWidth(100);
        cardView.setPreserveRatio(true);
        cardDisplay.getChildren().add(cardView);
      }
    }
    cardDisplay.setAlignment(Pos.CENTER); // Ensure the HBox is still centered
  }

  private void checkHand() {
    // Extract card values from images and process the hand
    List<PlayingCard> hand = cardDisplay.getChildren().stream()
        .filter(node -> node instanceof ImageView)
        .map(node -> ((ImageView) node).getImage().getUrl())
        .map(url -> url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".png")))
        .map(this::parseCard)
        .collect(Collectors.toList());

    // Calculate sum of values (Ace = 1)
    int sum = hand.stream().mapToInt(PlayingCard::getFace).sum();

    // Filter Hearts
    String hearts = hand.stream()
        .filter(card -> card.getSuit() == 'H')
        .map(PlayingCard::getAsString)
        .collect(Collectors.joining(" "));
    if (hearts.isEmpty()) hearts = "No Hearts";

    // Check if hand contains Queen of Spades
    boolean hasQueenOfSpades = hand.stream()
        .anyMatch(card -> card.getSuit() == 'S' && card.getFace() == 12);

    // Check for 5-flush
    HandOfCards handOfCards = new HandOfCards();
    hand.forEach(handOfCards::addCard);
    boolean hasFlush = handOfCards.hasFlush();

    resultLabel.setText("Sum: " + sum + " | Hearts: " + hearts +
        " | Has Queen of Spades: " + hasQueenOfSpades +
        " | 5-Flush: " + hasFlush);
    resultLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
  }

  private PlayingCard parseCard(String cardName) {
    // Remove file extension if present
    if (cardName.endsWith(".png")) {
      cardName = cardName.substring(0, cardName.length() - 4);
    }

    // Expected filename format: "card_suit_face", e.g., "card_clubs_09" or "card_hearts_Q"
    String[] parts = cardName.split("_");

    if (parts.length != 3) {
      throw new IllegalArgumentException("Invalid card filename: " + cardName);
    }

    char suit;
    switch (parts[1]) {
      case "hearts": suit = 'H'; break;
      case "diamonds": suit = 'D'; break;
      case "clubs": suit = 'C'; break;
      case "spades": suit = 'S'; break;
      default:
        throw new IllegalArgumentException("Invalid suit in filename: " + parts[1]);
    }

    int face;
    switch (parts[2]) {
      case "A": face = 1; break;
      case "J": face = 11; break;
      case "Q": face = 12; break;
      case "K": face = 13; break;
      default:
        try {
          face = Integer.parseInt(parts[2]); // Convert number cards normally
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid face value in filename: " + parts[2]);
        }
    }

    return new PlayingCard(suit, face);
  }



  public static void main(String[] args) {
    launch(args);
  }
}