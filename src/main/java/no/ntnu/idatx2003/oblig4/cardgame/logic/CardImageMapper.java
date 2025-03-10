package no.ntnu.idatx2003.oblig4.cardgame.logic;

import no.ntnu.idatx2003.oblig4.cardgame.models.PlayingCard;

public class CardImageMapper {
  public static String getImageForCard(PlayingCard card) {
    String suit = switch (card.getSuit()) {
      case 'H' -> "hearts";
      case 'D' -> "diamonds";
      case 'C' -> "clubs";
      case 'S' -> "spades";
      default -> throw new IllegalArgumentException("Unknown suit: " + card.getSuit());
    };

    String face = switch (card.getFace()) {
      case 1 -> "A";
      case 11 -> "J";
      case 12 -> "Q";
      case 13 -> "K";
      default -> String.format("%02d", card.getFace()); // Example: "09"
    };

    return "card_" + suit + "_" + face + ".png"; // Example: "card_hearts_09.png"
  }
}
