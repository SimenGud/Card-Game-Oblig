package no.ntnu.idatx2003.oblig4.cardgame.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class HandOfCardsTest {

  private HandOfCards hand;

  @BeforeEach
  void setUp() {
    hand = new HandOfCards();
  }

  @AfterEach
  void tearDown() {
    hand = null;
  }

  @Test
  void constructor_ShouldCreateEmptyHand_Test() {
    assertTrue(hand.getCards().isEmpty(), "New hand should be empty");
  }

  @Test
  void addCard_ShouldIncreaseHandSize_Test() {
    PlayingCard card = new PlayingCard('H', 7);
    hand.addCard(card);

    assertEquals(1, hand.getCards().size());
    assertTrue(hand.getCards().contains(card));
  }

  @Test
  void getCards_ShouldReturnCorrectList_Test() {
    PlayingCard card1 = new PlayingCard('D', 2);
    PlayingCard card2 = new PlayingCard('S', 5);
    hand.addCard(card1);
    hand.addCard(card2);

    List<PlayingCard> cards = hand.getCards();
    assertEquals(2, cards.size());
    assertTrue(cards.contains(card1));
    assertTrue(cards.contains(card2));
  }

  @Test
  void hasFlush_LessThanFiveCards_ShouldReturnFalse_Test() {
    hand.addCard(new PlayingCard('S', 2));
    hand.addCard(new PlayingCard('S', 5));
    hand.addCard(new PlayingCard('S', 9));
    hand.addCard(new PlayingCard('S', 12));

    assertFalse(hand.hasFlush());
  }

  @Test
  void hasFlush_FiveSameSuitCards_ShouldReturnTrue_Test() {
    hand.addCard(new PlayingCard('H', 2));
    hand.addCard(new PlayingCard('H', 5));
    hand.addCard(new PlayingCard('H', 7));
    hand.addCard(new PlayingCard('H', 10));
    hand.addCard(new PlayingCard('H', 12));

    assertTrue(hand.hasFlush());
  }

  @Test
  void hasFlush_MixedSuits_ShouldReturnFalse_Test() {
    hand.addCard(new PlayingCard('D', 3));
    hand.addCard(new PlayingCard('S', 7));
    hand.addCard(new PlayingCard('H', 9));
    hand.addCard(new PlayingCard('C', 11));
    hand.addCard(new PlayingCard('D', 13));

    assertFalse(hand.hasFlush());
  }

  @Test
  void hasFlush_MoreThanFiveCardsOneSuit_ShouldReturnTrue_Test() {
    hand.addCard(new PlayingCard('C', 2));
    hand.addCard(new PlayingCard('C', 4));
    hand.addCard(new PlayingCard('C', 6));
    hand.addCard(new PlayingCard('C', 8));
    hand.addCard(new PlayingCard('C', 10));
    hand.addCard(new PlayingCard('D', 12)); // Extra card of a different suit

    assertTrue(hand.hasFlush());
  }
}
