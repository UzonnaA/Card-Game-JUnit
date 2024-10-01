package org.example;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class MainTest {

    @Test
    @DisplayName("Check the adventure deck has 50 Foe and 50 Weapon cards")
    void RESP_01_test_01() {
        Main game = new Main();
        List<Main.AdventureCard> deck = game.init_Adv_Deck();

        // Check there are 50 Foe cards
        long foeCount = deck.stream().filter(card -> card.getType().equals("Foe")).count();
        assertEquals(50, foeCount, "There should be 50 Foe cards");

        // Check there are 50 Weapon cards
        long weaponCount = deck.stream().filter(card -> card.getType().equals("Weapon")).count();
        assertEquals(50, weaponCount, "There should be 50 Weapon cards");
    }

    @Test
    @DisplayName("Check the adventure deck has the correct amount of specific cards")
    void RESP_01_test_02() {
        Main game = new Main();
        List<Main.AdventureCard> deck = game.init_Adv_Deck();

        // Check there are 8 cards named "F5"
        long f5Count = deck.stream().filter(card -> card.getName().equals("F5")).count();
        assertEquals(8, f5Count, "Wrong number of F5 cards");

        // Check there are 7 cards named "F10"
        long f10Count = deck.stream().filter(card -> card.getName().equals("F10")).count();
        assertEquals(7, f10Count, "Wrong number of F10 cards");

        // Check there are 8 cards named "F15"
        long f15Count = deck.stream().filter(card -> card.getName().equals("F15")).count();
        assertEquals(8, f15Count, "Wrong number of F15 cards");

        // Check there are 7 cards named "F20"
        long f20Count = deck.stream().filter(card -> card.getName().equals("F20")).count();
        assertEquals(7, f20Count, "Wrong number of F20 cards");

        // Check there are 7 cards named "F25"
        long f25Count = deck.stream().filter(card -> card.getName().equals("F25")).count();
        assertEquals(7, f25Count, "Wrong number of F25 cards");

        // Check there are 4 cards named "F30"
        long f30Count = deck.stream().filter(card -> card.getName().equals("F30")).count();
        assertEquals(4, f30Count, "Wrong number of F30 cards");

        // Check there are 4 cards named "F35"
        long f35Count = deck.stream().filter(card -> card.getName().equals("F35")).count();
        assertEquals(4, f35Count, "Wrong number of F35 cards");

        // Check there are 2 cards named "F40"
        long f40Count = deck.stream().filter(card -> card.getName().equals("F40")).count();
        assertEquals(2, f40Count, "Wrong number of F40 cards");

        // Check there are 2 cards named "F50"
        long f50Count = deck.stream().filter(card -> card.getName().equals("F50")).count();
        assertEquals(2, f50Count, "Wrong number of F50 cards");

        // Check there is 1 card named "F70"
        long f70Count = deck.stream().filter(card -> card.getName().equals("F70")).count();
        assertEquals(1, f70Count, "Wrong number of F70 cards");

        // ~~~~~~~~~ Now we test for weapons ~~~~~~~~~~~~

        // Check there are 6 cards named "Dagger"
        long daggerCount = deck.stream().filter(card -> card.getName().equals("Dagger")).count();
        assertEquals(6, daggerCount, "Wrong number of Dagger cards");

        // Check there are 12 cards named "Horse"
        long horseCount = deck.stream().filter(card -> card.getName().equals("Horse")).count();
        assertEquals(12, horseCount, "Wrong number of Horse cards");

        // Check there are 16 cards named "Sword"
        long swordCount = deck.stream().filter(card -> card.getName().equals("Sword")).count();
        assertEquals(16, swordCount, "Wrong number of Sword cards");

        // Check there are 8 cards named "Battle-Axe"
        long battleAxeCount = deck.stream().filter(card -> card.getName().equals("Battle-Axe")).count();
        assertEquals(8, battleAxeCount, "Wrong number of Battle-Axe cards");

        // Check there are 6 cards named "Lance"
        long lanceCount = deck.stream().filter(card -> card.getName().equals("Lance")).count();
        assertEquals(6, lanceCount, "Wrong number of Lance cards");

        // Check there are 2 cards named "Excalibur"
        long excaliburCount = deck.stream().filter(card -> card.getName().equals("Excalibur")).count();
        assertEquals(2, excaliburCount, "Wrong number of Excalibur cards");
    }

    @Test
    @DisplayName("Check the event deck has 12 Quest and 5 Event cards")
    void RESP_01_test_03() {
        Main game = new Main();
        List<Main.EventCard> deck = game.init_Event_Deck();

        // Check there are 12 Quest cards
        long QCount = deck.stream().filter(card -> card.getType().equals("Quest")).count();
        assertEquals(12, QCount, "There should be 12 Quest cards");

        // Check there are 5 Event cards
        long ECount = deck.stream().filter(card -> card.getType().equals("Event")).count();
        assertEquals(5, ECount, "There should be 5 Event cards");
    }

    @Test
    @DisplayName("Check the event deck has the correct amount of specific cards")
    void RESP_01_test_04() {
        Main game = new Main();
        List<Main.EventCard> deck = game.init_Event_Deck();

        // Check there is 1 card named "Plague"
        long PlagueCount = deck.stream().filter(card -> card.getName().equals("Plague")).count();
        assertEquals(1, PlagueCount, "Wrong number of Plague cards");

        // Check there are 2 cards named "Queen Favor"
        long queenFavorCount = deck.stream().filter(card -> card.getName().equals("Queen Favor")).count();
        assertEquals(2, queenFavorCount, "Wrong number of Queen Favor cards");

        // Check there are 2 cards named "Prosperity"
        long prosperityCount = deck.stream().filter(card -> card.getName().equals("Prosperity")).count();
        assertEquals(2, prosperityCount, "Wrong number of Prosperity cards");

        // Check there are 3 cards named "Q2"
        long q2Count = deck.stream().filter(card -> card.getName().equals("Q2")).count();
        assertEquals(3, q2Count, "Wrong number of Q2 cards");

        // Check there are 4 cards named "Q3"
        long q3Count = deck.stream().filter(card -> card.getName().equals("Q3")).count();
        assertEquals(4, q3Count, "Wrong number of Q3 cards");

        // Check there are 3 cards named "Q4"
        long q4Count = deck.stream().filter(card -> card.getName().equals("Q4")).count();
        assertEquals(3, q4Count, "Wrong number of Q4 cards");

        // Check there are 2 cards named "Q5"
        long q5Count = deck.stream().filter(card -> card.getName().equals("Q5")).count();
        assertEquals(2, q5Count, "Wrong number of Q5 cards");


    }
}