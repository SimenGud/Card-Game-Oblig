package no.ntnu.idatx2003.oblig4.cardgame.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a hand of cards dealt to a player.
 *
 * @author Simen Gudbrandsen
 * @version 0.1
 * @since 10.03.2025
 */
public class HandOfCards {
  private final List<PlayingCard> hand;

  /**
   * Creates an empty hand of cards.
   */
  public HandOfCards() {
    this.hand = new ArrayList<>();
  }

  /**
   * Adds a card to the hand.
   *
   * @param card the playing card to add
   */
  public void addCard(PlayingCard card) {
    hand.add(card);
  }

  /**
   * Returns the list of cards in the hand.
   *
   * @return the list of playing cards
   */
  public List<PlayingCard> getCards() {
    return hand;
  }

  /**
   * Checks if the hand contains a flush (five cards of the same suit).
   *
   * @return true if the hand contains a flush, false otherwise
   */
  public boolean hasFlush() {
    if (hand.size() < 5) {
      return false;
    }

    Map<Character, Long> suitCounts = hand.stream()
        .collect(Collectors.groupingBy(PlayingCard::getSuit, Collectors.counting()));

    return suitCounts.values().stream().anyMatch(count -> count >= 5);
  }
}
