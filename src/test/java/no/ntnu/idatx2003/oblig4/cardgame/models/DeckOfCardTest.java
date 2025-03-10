package no.ntnu.idatx2003.oblig4.cardgame.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DeckOfCardsTest {

  private DeckOfCards deck;

  @BeforeEach
  void setUp() {
    deck = new DeckOfCards();
  }

  @AfterEach
  void tearDown() {
    deck = null;
  }

  @Test
  void constructor_ShouldCreateFullDeck() {
    assertEquals(52, deck.dealHand(52).getCards().size(), "A new deck should have 52 cards");
  }

  @Test
  void shuffleDeck_ShouldChangeCardOrder_Test() {
    List<PlayingCard> originalOrder = deck.dealHand(52).getCards();

    deck = new DeckOfCards();
    deck.shuffleDeck();
    List<PlayingCard> shuffledOrder = deck.dealHand(52).getCards();

    assertNotEquals(originalOrder, shuffledOrder, "Shuffling should change the order of cards");
  }

  @Test
  void dealHand_ValidNumberOfCards_ShouldReturnCorrectHandSize_Test() {
    HandOfCards hand = deck.dealHand(5);
    assertEquals(5, hand.getCards().size(), "Dealt hand should have 5 cards");
  }

  @Test
  void dealHand_NegativeOrZero_ShouldThrowException_Test() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> deck.dealHand(0));
    assertEquals("Invalid hand size: 0. Deck has: 52 cards left.", thrown.getMessage());

    thrown = assertThrows(IllegalArgumentException.class, () -> deck.dealHand(-3));
    assertEquals("Invalid hand size: -3. Deck has: 52 cards left.", thrown.getMessage());
  }

  @Test
  void dealHand_TooManyCards_ShouldThrowException_Test() {
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> deck.dealHand(53));
    assertEquals("Invalid hand size: 53. Deck has: 52 cards left.", thrown.getMessage());
  }

  @Test
  void dealHand_ShouldNotHaveDuplicateCards_Test() {
    HandOfCards hand = deck.dealHand(10);
    Set<String> uniqueCards = new HashSet<>();

    for (PlayingCard card : hand.getCards()) {
      assertTrue(uniqueCards.add(card.getAsString()), "Duplicate card found in hand");
    }
  }
}
