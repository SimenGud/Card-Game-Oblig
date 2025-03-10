package no.ntnu.idatx2003.oblig4.cardgame.logic;

import no.ntnu.idatx2003.oblig4.cardgame.models.DeckOfCards;
import no.ntnu.idatx2003.oblig4.cardgame.models.HandOfCards;

public class CardGameController {
  private final DeckOfCards deck;
  private HandOfCards lastHand; // Store the last dealt hand

  public CardGameController() {
    this.deck = new DeckOfCards();
  }

  /**
   * Deals a hand of 5 cards from the deck.
   *
   * @return the hand of cards
   */

  public HandOfCards dealHand() {
    deck.shuffleDeck();
    lastHand = deck.dealHand(5); // Save the last dealt hand
    return lastHand;
  }

  /*

  //Example method for automatically dealing a flush.
  public HandOfCards dealHand() {
    deck.shuffleDeck();
    HandOfCards hand = new HandOfCards();

    for (int i = 0; i < 5; i++) {
      int face = (i % 13) + 1; // Ensures values between 1 (Ace) to 13 (King)
      hand.addCard(new PlayingCard('H', face));
    }

    lastHand = hand; // Save the last hand
    return hand;
  }

   */

  /**
   * Returns the last hand dealt.
   *
   * @return the last hand dealt
   */

  public HandOfCards getLastHand() {
    return lastHand;
  }
}
