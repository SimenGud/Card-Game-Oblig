package no.ntnu.idatx2003.oblig4.cardgame.logic;

import no.ntnu.idatx2003.oblig4.cardgame.models.DeckOfCards;
import no.ntnu.idatx2003.oblig4.cardgame.models.HandOfCards;

public class GameController {
  private final DeckOfCards deck;
  private HandOfCards lastHand; // Store the last dealt hand

  public GameController() {
    this.deck = new DeckOfCards();
  }

  public HandOfCards dealHand() {
    deck.shuffleDeck();
    lastHand = deck.dealHand(5); // Save the last dealt hand
    return lastHand;
  }

  public HandOfCards getLastHand() {
    return lastHand;
  }
}
