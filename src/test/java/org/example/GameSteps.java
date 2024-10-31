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


    @Given("the game is set to ATEST mode")
    public void the_game_is_set_to_ATEST_mode() {
        game.ATEST = true;
    }

    @Given("the game is set to ATEST2 mode")
    public void the_game_is_set_to_ATEST2_mode() {
        game.ATEST2 = true;
    }

    @Given("the game is set to ATEST3 mode")
    public void the_game_is_set_to_ATEST3_mode() {
        game.ATEST3 = true;
    }

    @Given("the game is set to ATEST4 mode")
    public void the_game_is_set_to_ATEST4_mode() {
        game.ATEST4 = true;
    }




    @When("a new game starts")
    public void a_new_game_starts() {
        game.InitializeDeck();
        game.StartGame();
        output = new StringWriter();
    }





    @And("The next player is ready")
    public void player_1_is_ready() {
        game.areYouReady(new Scanner(input), new PrintWriter(output), game.getCurrentPlayer());
    }

    @And("The player's hand is shown")
    public void player_1s_hand_is_shown() {
        game.ShowHand(new Scanner(input), new PrintWriter(output), game.getCurrentPlayer().getName(), true);
    }

    @And("The player triggers a Q4 event")
    public void player_1_triggers_a_Q4_event() {
        game.DrawPlayEvents(new Scanner(input), new PrintWriter(output), "Q4");
    }

    @And("The player triggers a Q3 event")
    public void player_1_triggers_a_Q3_event() {
        game.DrawPlayEvents(new Scanner(input), new PrintWriter(output), "Q3");
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

    @Then("Player 3 should be eliminated for failing a stage")
    public void player_3_should_be_eliminated() {
        assertTrue("Player 3 did not fail any stages", output.toString().contains("Player 3 failed to match the stage value and is eliminated"));
    }

    @Then("Player 2 should be awarded {int} shields for completing the quest")
    public void player_2_should_be_awarded_shields(int shields) {
        assertTrue("Player 2 did not win a quest. Output: " + output.toString(), output.toString().contains("Player 2 is awarded " + shields + " shields for completing the quest"));
    }

    @Then("Player 4 should be awarded {int} shields for completing the quest")
    public void player_4_should_be_awarded_shields(int shields) {
        assertTrue("Player 4 did not win a quest. Output: " + output.toString(), output.toString().contains("Player 4 is awarded " + shields + " shields for completing the quest"));
    }

    @Then("Player 2 should decline to sponsor the quest")
    public void player_2_declines_to_sponsor() {
        assertTrue("Player 2 sponsored the quest?", output.toString().contains("Player 2 has declined to sponsor the quest"));
    }

    @Then("Player 3 should agree to sponsor the quest")
    public void player_3_agrees_to_sponsor() {
        assertTrue("Player 3 did not sponsor", output.toString().contains("Player 3 has agreed to sponsor the quest"));
    }

    @Then("Player 1 should decline to attack the quest")
    public void player_1_declines_to_attack() {
        assertTrue("Player 1 did not decline the quest", output.toString().contains("Player 1 has declined to attack the quest"));
    }

    @Then("Player {int} should be declared a winner")
    public void player_is_declared_winner(int playerNumber) {
        assertTrue("Player " + playerNumber + " did not win the game", output.toString().contains("Player " + playerNumber + " is a winner"));
    }



}
