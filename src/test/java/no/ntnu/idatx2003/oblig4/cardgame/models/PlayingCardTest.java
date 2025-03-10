package no.ntnu.idatx2003.oblig4.cardgame.models;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatx2003.oblig4.cardgame.models.PlayingCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayingCardTest {

  private PlayingCard card1;
  private PlayingCard card2;
  private PlayingCard card3;

  @BeforeEach
  public void setUp() {
    card1 = new PlayingCard('H', 1);
    card2 = new PlayingCard('S', 13);
    card3 = new PlayingCard('D', 7);
  }

  @AfterEach
  public void tearDown() {
    card1 = null;
    card2 = null;
    card3 = null;
  }

  @Test
  void constructor_ValidInput_PlayingCard_Test() {
    assertEquals('H', card1.getSuit());
    assertEquals(1, card1.getFace());
  }

  @Test
  void negative_constructor_ValidInput_PlayingCard_Test() {
    assertNotEquals('S', card1.getSuit());
    assertNotEquals(13, card1.getFace());
  }

  @Test
  void equals_DifferentCard_ShouldReturnFalse() {
    PlayingCard anotherCard = new PlayingCard('C', 10);
    assertNotEquals(card1, anotherCard);
  }

  @Test
  void equals_NullObject_ShouldReturnFalse() {
    PlayingCard card = new PlayingCard('H', 5);
    assertFalse(card.equals(null), "A PlayingCard should not be equal to null");
  }

  @Test
  void equals_DifferentClass_ShouldReturnFalse() {
    PlayingCard card = new PlayingCard('D', 10);
    String differentObject = "Not a PlayingCard";
    assertFalse(card.equals(differentObject), "A PlayingCard should not be equal to an object of a different class");
  }


  @Test
  void exception_Face_PlayingCard_Test1() {
    assertThrows(IllegalArgumentException.class, () -> new PlayingCard('H', 14));
  }

  @Test
  void exception_Suit_PlayingCard_Test2() {
    assertThrows(IllegalArgumentException.class, () -> new PlayingCard('A', 1));
  }

  @Test
  void exception_Suit_PlayingCard_Test3() {
    assertThrows(IllegalArgumentException.class, () -> new PlayingCard('A', 12));
  }

  @Test
  void getAsString_PlayingCard_Test() {
    assertEquals("H1", card1.getAsString());
    assertEquals("S13", card2.getAsString());
    assertEquals("D7", card3.getAsString());
  }

  @Test
  void hashCode_SameCard_ShouldReturnSameHash() {
    PlayingCard duplicateCard = new PlayingCard('S', 13);
    assertEquals(card2.hashCode(), duplicateCard.hashCode());
  }


}
