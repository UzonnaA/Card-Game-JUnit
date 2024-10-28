package org.example;
import io.cucumber.java.en.*;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import java.util.Map;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;



public class GameSteps {
    Main game = new Main();
    private StringWriter output;
    private String input = "\n";

    @Given("a new game starts")
    public void a_new_game_starts() {
        game.InitializeDeck();
        game.StartGame();
        output = new StringWriter();
    }

    @And("the game is set to ATEST mode")
    public void the_game_is_set_to_ATEST_mode() {
        game.ATEST = true;
    }



    @And("Player 1 is ready")
    public void player_1_is_ready() {
        game.areYouReady(new Scanner(input), new PrintWriter(output), game.getCurrentPlayer());
    }

    @And("Player 1's hand is shown")
    public void player_1s_hand_is_shown() {
        game.ShowHand(new Scanner(input), new PrintWriter(output), game.getCurrentPlayer().getName(), true);
    }

    @And("Player 1 triggers a Q4 event")
    public void player_1_triggers_a_Q4_event() {
        game.DrawPlayEvents(new Scanner(input), new PrintWriter(output), "Q4");
    }

    @And("the game checks for winners")
    public void the_game_checks_for_winners() {
        game.checkForWinners(new Scanner(input), new PrintWriter(output));
    }

    @Then("Player 1 should have {int} shields")
    public void player_1_should_have_shields(int expectedShields) {
        assertTrue("Player 1 did not have the correct shields", game.getPlayerByName("Player 1").getShields() == expectedShields);
    }

    @And("Player 1 should have cards {string}")
    public void player_1_should_have_cards(String expectedCards) {
        game.ShowHand(new Scanner(input), new PrintWriter(output), "Player 1", false);
        assertTrue("Player 1 did not have the correct cards", output.toString().contains(expectedCards));
    }

    @Then("Player 3 should have {int} shields")
    public void player_3_should_have_shields(int expectedShields) {
        assertTrue( "Player 3 did not have the correct shields", game.getPlayerByName("Player 3").getShields() == expectedShields);
    }

    @And("Player 3 should have cards {string}")
    public void player_3_should_have_cards(String expectedCards) {
        game.ShowHand(new Scanner(input), new PrintWriter(output), "Player 3", false);
        assertTrue( "Player 3 did not have the correct cards", output.toString().contains(expectedCards));
    }

    @Then("Player 4 should have {int} shields")
    public void player_4_should_have_shields(int expectedShields) {
        assertTrue( "Player 4 did not have the correct shields", game.getPlayerByName("Player 4").getShields() == expectedShields);
    }

    @And("Player 4 should have cards {string}")
    public void player_4_should_have_cards(String expectedCards) {
        game.ShowHand(new Scanner(input), new PrintWriter(output), "Player 4", false);
        assertTrue( "Player 4 did not have the correct cards", output.toString().contains(expectedCards));
    }



}
