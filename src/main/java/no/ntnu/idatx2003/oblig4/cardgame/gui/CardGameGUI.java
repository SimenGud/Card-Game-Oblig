package no.ntnu.idatx2003.oblig4.cardgame.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardGameGUI extends Application {
  private List<String> deck = new ArrayList<>();
  private HBox cardDisplay;

  @Override
  public void start(Stage primaryStage) {
    loadDeck();
    cardDisplay = new HBox(10);

    Button dealButton = new Button("Deal Card");
    dealButton.setOnAction(e -> dealCard());

    VBox layout = new VBox(20, cardDisplay, dealButton);
    layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

    Scene scene = new Scene(layout, 600, 400);
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

  private void dealCard() {
    if (!deck.isEmpty()) {
      String cardName = deck.remove(0);
      Image cardImage = new Image(getClass().getResource("/cards/" + cardName).toExternalForm());
      ImageView cardView = new ImageView(cardImage);
      cardView.setFitWidth(100);
      cardView.setPreserveRatio(true);
      cardDisplay.getChildren().add(cardView);
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
