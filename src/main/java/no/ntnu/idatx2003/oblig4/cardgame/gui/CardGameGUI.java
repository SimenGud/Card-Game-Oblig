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
import no.ntnu.idatx2003.oblig4.cardgame.logic.CardGameInterface;
import no.ntnu.idatx2003.oblig4.cardgame.logic.CardImageMapper;
import no.ntnu.idatx2003.oblig4.cardgame.models.DeckOfCards;
import no.ntnu.idatx2003.oblig4.cardgame.models.HandOfCards;
import no.ntnu.idatx2003.oblig4.cardgame.models.PlayingCard;

import java.util.List;
import java.util.stream.Collectors;

public class CardGameGUI extends Application implements CardGameInterface {
  private HBox cardDisplay;
  private Label resultLabel;
  private DeckOfCards deck;

  @Override
  public void start(Stage primaryStage) {
    deck = new DeckOfCards(); // Create a deck instance

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
    HandOfCards hand = deck.dealHand(5);

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
        .map(this::parseCard)
        .collect(Collectors.toList());

    int sum = hand.stream().mapToInt(PlayingCard::getFace).sum();

    String hearts = hand.stream()
        .filter(card -> card.getSuit() == 'H')
        .map(PlayingCard::getAsString)
        .collect(Collectors.joining(" "));
    if (hearts.isEmpty()) hearts = "No Hearts";

    boolean hasQueenOfSpades = hand.stream().anyMatch(card -> card.getSuit() == 'S' && card.getFace() == 12);

    HandOfCards handOfCards = new HandOfCards();
    hand.forEach(handOfCards::addCard);
    boolean hasFlush = handOfCards.hasFlush();

    resultLabel.setText(String.format("Sum: %d | Hearts: %s | Queen of Spades: %b | 5-Flush: %b",
        sum, hearts, hasQueenOfSpades, hasFlush));
  }

  private PlayingCard parseCard(String cardName) {
    String[] parts = cardName.replace(".png", "").split("_");
    if (parts.length != 3) throw new IllegalArgumentException("Invalid card filename: " + cardName);

    char suit = switch (parts[1]) {
      case "hearts" -> 'H';
      case "diamonds" -> 'D';
      case "clubs" -> 'C';
      case "spades" -> 'S';
      default -> throw new IllegalArgumentException("Invalid suit: " + parts[1]);
    };

    int face = switch (parts[2]) {
      case "A" -> 1;
      case "J" -> 11;
      case "Q" -> 12;
      case "K" -> 13;
      default -> {
        try { yield Integer.parseInt(parts[2]); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Invalid face value: " + parts[2]); }
      }
    };

    return new PlayingCard(suit, face);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
