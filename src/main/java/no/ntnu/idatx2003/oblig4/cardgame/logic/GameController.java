package no.ntnu.idatx2003.oblig4.cardgame.logic;

import no.ntnu.idatx2003.oblig4.cardgame.models.DeckOfCards;
import no.ntnu.idatx2003.oblig4.cardgame.models.HandOfCards;
import no.ntnu.idatx2003.oblig4.cardgame.models.PlayingCard;

public class GameController {
  private final DeckOfCards deck;

  public GameController() {
    this.deck = new DeckOfCards();
  }

  public HandOfCards dealHand() {
    return deck.dealHand(5); // Always deal 5 cards
  }

  public void resetDeck() {
    deck.shuffleDeck(); // Shuffle after every hand
  }
}
