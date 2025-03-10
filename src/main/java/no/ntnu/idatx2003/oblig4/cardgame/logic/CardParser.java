package no.ntnu.idatx2003.oblig4.cardgame.logic;

import no.ntnu.idatx2003.oblig4.cardgame.models.PlayingCard;

public class CardParser {
  public static PlayingCard parseCard(String cardName) {
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
}
