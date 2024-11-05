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



    @Given("the game is set to {string} mode")
    public void set_test_mode(String test) {
        if(test.equals("ATEST")){
            game.ATEST = true;
        }

        if(test.equals("ATEST2")){
            game.ATEST2 = true;
        }

        if(test.equals("ATEST3")){
            game.ATEST3 = true;
        }

        if(test.equals("ATEST4")){
            game.ATEST4 = true;
        }

    }




    @When("a new game starts")
    public void a_new_game_starts() {
        game.InitializeDeck();
        game.StartGame();
        output = new StringWriter();
    }

    @And("The next player is ready")
    public void player_is_ready() {
        game.areYouReady(new Scanner(input), new PrintWriter(output), game.getCurrentPlayer());
    }

    @And("The player's hand is shown")
    public void player_hand_is_shown() {
        game.ShowHand(new Scanner(input), new PrintWriter(output), game.getCurrentPlayer().getName(), true);
    }


    @And("The player triggers a {string} event")
    public void player_triggers_event(String event) {
        game.DrawPlayEvents(new Scanner(input), new PrintWriter(output), event);
    }


    @And("the game checks for winners")
    public void the_game_checks_for_winners() {
        game.checkForWinners(new Scanner(input), new PrintWriter(output));
    }

    @Then("Player {int} should have {int} shields")
    public void player_should_have_shields(int playerNum, int expectedShields) {
        assertTrue("Player " + playerNum +  " did not have the correct shields", game.getPlayerByName("Player " + playerNum).getShields() == expectedShields);
    }

    @And("Player {int} should have cards {string}")
    public void player_should_have_cards(int playerNum, String expectedCards) {
        game.ShowHand(new Scanner(input), new PrintWriter(output), "Player " + playerNum, false);
        assertTrue( "Player " + playerNum + " did not have the correct cards", output.toString().contains(expectedCards));
    }


    @Then("Player {int} should be eliminated for failing a stage")
    public void player_should_be_eliminated(int playerNumber) {
        assertTrue("Player " + playerNumber + " did not fail any stages", output.toString().contains("Player " + playerNumber + " failed to match the stage value and is eliminated"));
    }

    @Then("Player {int} should have lost shields from plague")
    public void player_should_lose_shields(int playerNumber) {
        assertTrue("Player " + playerNumber + " did not lose any shields" + output.toString() , output.toString().contains("Player " + playerNumber + " lost 2 shields!"));
    }

    @Then("Player {int} should have gained cards from the Queen")
    public void player_get_from_Queen(int playerNumber) {
        assertTrue("Player " + playerNumber + " did gain any cards from Queen's Favor" + output.toString() , output.toString().contains("Player " + playerNumber + " will draw 2 cards."));
    }

    @And("Prosperity must have happened")
    public void prosperity_must_have_happened() {
        game.ShowHand(new Scanner(input), new PrintWriter(output), "Player 4", false);
        assertTrue( "Prosperity did not occur", output.toString().contains("All players will draw 2 cards."));
    }


    @Then("Player {int} should be awarded {int} shields for completing the quest")
    public void player_should_be_awarded_shields(int playerNum, int shields) {
        assertTrue("Player " + playerNum + " did not win a quest. Output: " + output.toString(), output.toString().contains("Player " + playerNum + " is awarded " + shields + " shields for completing the quest"));
    }



    @Then("Player {int} should decline to sponsor the quest")
    public void player_declines_to_sponsor(int playerNum) {
        assertTrue("Player " + playerNum + " sponsored the quest?", output.toString().contains("Player " + playerNum + " has declined to sponsor the quest"));
    }

    @Then("Player {int} should agree to sponsor the quest")
    public void player_agrees_to_sponsor(int playerNum) {
        assertTrue("Player " + playerNum + " did not sponsor", output.toString().contains("Player " + playerNum + " has agreed to sponsor the quest"));
    }

    @Then("Player {int} should decline to attack the quest")
    public void player_declines_to_attack(int playerNum) {
        assertTrue("Player " + playerNum + " did not decline the quest", output.toString().contains("Player " + playerNum + " has declined to attack the quest"));
    }

    @Then("Player {int} should be declared a winner")
    public void player_is_declared_winner(int playerNumber) {
        assertTrue("Player " + playerNumber + " did not win the game", output.toString().contains("Player " + playerNumber + " is a winner"));
    }

    @Then("Player {int} should gain {int} cards for sponsoring")
    public void sponsor_gets_cards(int playerNumber, int cards) {
        assertTrue("Player " + playerNumber + " did not get cards for sponsoring", output.toString().contains("Player " + playerNumber + " will now gain " + cards + " cards for sponsoring the quest."));
    }

    @Then("Player {int} should not have too many cards")
    public void player_trims_hand(int playerNumber) {
        assertTrue("Player " + playerNumber + " did not trim their hand", output.toString().contains("Player " + playerNumber + " no longer has too many cards."));
    }

    @Then("Player {int} should have sponsored a {string} quest")
    public void player_did_sponsor(int playerNumber, String quest) {
        assertTrue("Player " + playerNumber + " did not sponsor the quest", output.toString().contains("Player " + playerNumber + " has agreed to sponsor the quest!"));
        assertTrue("Player " + playerNumber + " did not sponsor the CORRECT quest", output.toString().contains("Drew event card: " + quest));
    }


    @Then("Player {int} should have exactly {int} shields")
    public void player_has_shields(int playerNumber, int shields) {
        assertTrue("Player " + playerNumber + " did not have the needed shields", game.getPlayerByName("Player " + playerNumber).getShields() == shields);

    }




}
