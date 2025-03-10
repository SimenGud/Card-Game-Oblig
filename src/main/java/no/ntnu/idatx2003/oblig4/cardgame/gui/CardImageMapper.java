package no.ntnu.idatx2003.oblig4.cardgame.gui;

import no.ntnu.idatx2003.oblig4.cardgame.models.PlayingCard;

/**
 * A utility class for mapping a PlayingCard to an image file name.
 *
 * Each card in the deck has a corresponding image file in the resources folder. This class
 * provides a method for mapping a PlayingCard to the corresponding image file name.
 *
 * @author Simen
 * @version 0.2
 * @since 10.03.2025
 */

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
      default -> String.format("%02d", card.getFace());
    };

    return "card_" + suit + "_" + face + ".png";
  }
}
