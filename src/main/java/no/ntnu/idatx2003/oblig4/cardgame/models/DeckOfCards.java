package no.ntnu.idatx2003.oblig4.cardgame.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck of cards. A deck of cards contains 52 cards, one of each combination of suit
 * and face value.
 */

public class DeckOfCards {

  private final char[] suits = { 'S', 'H', 'D', 'C' };
  private final List<PlayingCard> deck;

  /**
   * Creates a new deck of cards, shuffled.
   */

  public DeckOfCards() {
    deck = new ArrayList<>();
    for (char suit : suits) {
      for (int face = 1; face <= 13; face++) {
        deck.add(new PlayingCard(suit, face));
      }
    }
    shuffleDeck();
  }

  /**
   * Shuffles the deck of cards.
   */

  public void shuffleDeck() {
    Collections.shuffle(deck);
  }

  /**
   * Deals a hand of n cards from the deck. If n is less than 1 or greater than the number of cards
   * in the deck, an IllegalArgumentException is thrown.
   *
   * @param n the number of cards to deal
   * @return a list of n cards
   * @thrown IllegalArgumentException if n is less than 1 or greater than the number of cards in the
   */

  public HandOfCards dealHand(int n) {
    if (n < 1 || n > deck.size()) {
      throw new IllegalArgumentException("Invalid hand size: " + n + ". Deck has: " + deck.size() + " cards left.");
    }
    HandOfCards hand = new HandOfCards();
    for (int i = 0; i < n; i++) {
      hand.addCard(deck.get(i));
    }
    return hand;
  }

}
