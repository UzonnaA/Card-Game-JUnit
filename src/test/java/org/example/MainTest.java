package org.example;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
        long queenFavorCount = deck.stream().filter(card -> card.getName().equals("Queen's Favor")).count();
        assertEquals(2, queenFavorCount, "Wrong number of Queen's Favor cards");

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

    @Test
    @DisplayName("Distribute 12 adventure cards to all players > check deck size")
    void RESP_02_test_01() {
        Main game = new Main();
        game.InitializeDeck(); // Initialize the adventure deck with cards

        // Distribute cards to players
        game.StartGame();

        // Retrieve the adventure deck after distribution
        List<Main.AdventureCard> deck = game.advDeck;

        // Retrieve the player hands
        Map<String, Main.Player> players = game.players;

        // Check each player has exactly 12 cards
        for (Map.Entry<String, Main.Player> entry : players.entrySet()) {
            Main.Player player = entry.getValue();
            assertEquals(12, player.getDeck().size(), "Each player should have 12 cards");
        }

        // Check the deck has been updated correctly
        int expectedRemainingCards = 52; // 100 initial cards - 48 distributed cards = 52 remaining cards
        assertEquals(expectedRemainingCards, deck.size(), "The deck should have 52 cards left after distribution.");
    }


    @Test
    @DisplayName("Test that Player's card hand is displayed in correct order after sorting.")
    void RESP_03_test_01() {
        Main game = new Main();
        game.InitializeDeck();  // Initialize the deck
        game.StartGame();       // Start the game and distribute cards

        game.removeAllCardsFromPlayer("Player 1");

        // Overwrite Player 1's hand with a specific set of cards (in a mixed-up order)
        game.OverwriteDeckCard("Player 1", 0, "Weapon", "Sword", 10); // Weapon card with value 10
        game.OverwriteDeckCard("Player 1", 1, "Foe", "F10", 10);      // Foe card with value 10
        game.OverwriteDeckCard("Player 1", 2, "Weapon", "Horse", 5);  // Weapon card with value 5
        game.OverwriteDeckCard("Player 1", 3, "Foe", "F5", 5);        // Foe card with value 5

        // Simulate player's interaction
        String input = "\n";
        StringWriter output = new StringWriter();

        // Call sortCards to ensure the cards are in the correct order
        //game.sortCards(game.getPlayerHand("Player 1"));

        // Now display the player's hand
        game.PromptPlayer(new Scanner(input), new PrintWriter(output), "Player 1");

        // Check if the hand is displayed correctly: foes first, then weapons
        boolean assertion = false;
        if (output.toString().contains("(1)F5, (2)F10, (3)Sword, (4)Horse")) {
            assertion = true;
        }

        assertTrue(assertion, "The cards are not in the right order");
    }


    @Test
    @DisplayName("Test drawing a card from the event deck reduces deck size by 1")
    void RESP_04_test_01() {
        Main game = new Main();
        game.InitializeDeck();  // Initialize both the adventure and event decks
        game.StartGame();       // Start the game and distribute cards

        // Get the initial size of the event deck
        int initialEventDeckSize = game.eventDeck.size();

        // Simulate Player 1's turn
        StringWriter output = new StringWriter();
        game.PromptPlayer(new Scanner(System.in), new PrintWriter(output), "Player 1");
        game.DrawPlayEvents(new Scanner(System.in), new PrintWriter(output));

        // We call drawEventCard in promptPlayer, this line is not needed

        // Check if the event deck size has been reduced by 1
        int finalEventDeckSize = game.eventDeck.size();
        assertEquals(initialEventDeckSize - 1, finalEventDeckSize, "The event deck size should be reduced by 1 after drawing a card.");
    }

    @Test
    @DisplayName("Test that the drawn event card is displayed correctly.")
    void RESP_04_test_02() {
        Main game = new Main();
        game.InitializeDeck();  // Initialize both the adventure and event decks
        game.StartGame();       // Start the game and distribute cards



        // Simulate Player 1's turn
        StringWriter output = new StringWriter();
        game.PromptPlayer(new Scanner(System.in), new PrintWriter(output), "Player 1");
        game.DrawPlayEvents(new Scanner(System.in), new PrintWriter(output));



        // Check if the name of the drawn card is displayed correctly
        // "The drawn event card's name should be displayed as 'Plague'."
        assertTrue(output.toString().contains("Drew event card: " + game.lastEventCard), "What I see: " + output.toString());
    }


    @Test
    @DisplayName("Test that the Plague Event works correctly")
    void RESP_05_test_01() {
        Main game = new Main();
        game.InitializeDeck();  // Initialize both the adventure and event decks
        game.StartGame();       // Start the game and distribute cards



        // Simulate Player 1's turn
        StringWriter output = new StringWriter();
        game.PromptPlayer(new Scanner(System.in), new PrintWriter(output), "Player 1");

        // Give the player shields then take them away
        game.players.get("Player 1").changeShields(5);
        // Force a plague event
        game.DrawPlayEvents(new Scanner(System.in), new PrintWriter(output), "Plague");

        int actualShields = game.players.get("Player 1").getShields();
        assertEquals(3, actualShields, "Player 1 should have 3 shields after the Plague event.");



        // Check if the name of the drawn card is displayed correctly
        // "The drawn event card's name should be displayed as 'Plague'."
        assertTrue(output.toString().contains("Drew event card: " + game.lastEventCard), "What I see: " + output.toString());
    }

    @Test
    @DisplayName("Test that the display is cleared after event plays out (non-quest event)")
    void RESP_06_test_01() {
        Main game = new Main();
        game.InitializeDeck();  // Initialize both the adventure and event decks
        game.StartGame();       // Start the game and distribute cards

        // Simulate Player 1's turn
        StringWriter output = new StringWriter();
        game.PromptPlayer(new Scanner(System.in), new PrintWriter(output), game.currentPlayer.getName());

        // Simulate playing a non-quest event
        game.DrawPlayEvents(new Scanner(System.in), new PrintWriter(output), "Plague");

        // Check for the transition message after event is processed
        assertTrue(output.toString().contains("Press enter to switch to the next player"), "I see: " + output.toString());

    }

    @Test
    @DisplayName("Test that switching to the next player works correctly")
    void RESP_06_test_02() {
        Main game = new Main();
        game.InitializeDeck();  // Initialize both the adventure and event decks
        game.StartGame();       // Start the game and distribute cards

        // Simulate Player 1's turn
        StringWriter output = new StringWriter();
        game.PromptPlayer(new Scanner(System.in), new PrintWriter(output), game.currentPlayer.getName());

        // Simulate playing a non-quest event

        game.DrawPlayEvents(new Scanner(System.in), new PrintWriter(output), "Plague");
        // Simulate pressing enter to switch to the next player
        String input = "\n";
        game.handleNextPlayer(new Scanner(input), new PrintWriter(output));

        // Check for the "Are you ready Player 2" message
        assertTrue(output.toString().contains("Are you ready"), "I see: " + output.toString());
    }

    @Test
    @DisplayName("Test that Player 2 is prompted to start after pressing enter")
    void RESP_06_test_03() {
        Main game = new Main();
        game.InitializeDeck();  // Initialize both the adventure and event decks
        game.StartGame();       // Start the game and distribute cards

        // Simulate Player 1's turn and event processing
        StringWriter output = new StringWriter();
        game.PromptPlayer(new Scanner(System.in), new PrintWriter(output), game.currentPlayer.getName());

        game.DrawPlayEvents(new Scanner(System.in), new PrintWriter(output), "Plague");

        // Simulate pressing enter to switch to the next player
        String input = "\n";
        game.handleNextPlayer(new Scanner(input), new PrintWriter(output));

        // Simulate Player 2 pressing enter to start their turn
        game.PromptNextPlayer(new Scanner(input), new PrintWriter(output), game.NextPlayerString(game.currentPlayer.getName()));

        // Check if Player 2 is prompted and shown their hand
        assertTrue(output.toString().contains("Turn:"), "Player 2 should be prompted after switching.");
    }


    @Test
    @DisplayName("Test that a player with 7 shields is set to winner")
    void RESP_07_test_01() {
        // Code to run the game as normal
        Main game = new Main();
        game.InitializeDeck();
        game.StartGame();
        StringWriter output = new StringWriter();
        game.PromptPlayer(new Scanner(System.in), new PrintWriter(output), game.currentPlayer.getName());
        game.DrawPlayEvents(new Scanner(System.in), new PrintWriter(output), "Plague");

        // Simulate pressing enter to switch to the next player
        String input = "\n";

        // Before we let the next player, we'll check if there are winners
        // For the test, we'll grant player 1 some shields
        game.currentPlayer.changeShields(7);
        game.checkForWinners(new Scanner(System.in), new PrintWriter(output));



        // Check if Player 1 was declared as a winner
        assertTrue(game.currentPlayer.checkWinner(), "Player 1 should be a winner.");
    }


    @Test
    @DisplayName("Test that winners are displayed")
    void RESP_08_test_01() {
        // Code to run the game as normal
        Main game = new Main();
        game.InitializeDeck();
        game.StartGame();
        StringWriter output = new StringWriter();
        game.PromptPlayer(new Scanner(System.in), new PrintWriter(output), game.currentPlayer.getName());
        game.DrawPlayEvents(new Scanner(System.in), new PrintWriter(output), "Plague");

        // Simulate pressing enter to switch to the next player
        String input = "\n";

        // Before we let the next player, we'll check if there are winners
        // For the test, we'll grant player 1 some shields
        game.currentPlayer.changeShields(7);
        game.checkForWinners(new Scanner(System.in), new PrintWriter(output));



        // Check if Player 1 was declared as a winner
        assertTrue(output.toString().contains("Player 1 is a winner"), "Player 1 should be a winner.");
    }




}