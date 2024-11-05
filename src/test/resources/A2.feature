Feature: Assignment 2

  Scenario: A1_Scenario
    Given the game is set to "ATEST" mode
    When a new game starts
    And The next player is ready
    And The player's hand is shown
    And The player triggers a "Q4" event
    And the game checks for winners
    Then Player 1 should have 0 shields
    And Player 1 should have cards "(1)F5, (2)F10, (3)F15, (4)F15, (5)F30, (6)Horse, (7)Battle-Axe, (8)Battle-Axe, (9)Lance"
    And Player 3 should have 0 shields
    And Player 3 should have cards "(1)F5, (2)F5, (3)F15, (4)F30, (5)Sword"
    And Player 4 should have 4 shields
    And Player 4 should have cards "(1)F15, (2)F15, (3)F40, (4)Lance"

  Scenario: 2winner_game_2winner_quest
    Given the game is set to "ATEST2" mode
    When a new game starts
    And The next player is ready
    And The player's hand is shown
    And The player triggers a "Q4" event
    And the game checks for winners
    Then Player 1 should have sponsored a "Q4" quest
    And Player 3 should be eliminated for failing a stage
    And Player 2 should be awarded 4 shields for completing the quest
    And Player 4 should be awarded 4 shields for completing the quest
#    Do a second loop for the second quest
    And The next player is ready
    And The player's hand is shown
    And The player triggers a "Q3" event
    And the game checks for winners
    Then Player 2 should decline to sponsor the quest
    And Player 3 should have sponsored a "Q3" quest
    And Player 1 should decline to attack the quest
    And Player 2 should have exactly 7 shields
    And Player 4 should have exactly 7 shields
    And Player 2 should be declared a winner
    And Player 4 should be declared a winner

  Scenario: 1winner_game_with_events
    Given the game is set to "ATEST3" mode
    When a new game starts
    And The next player is ready
    And The player's hand is shown
    And The player triggers a "Q4" event
    And the game checks for winners
    Then Player 1 should have sponsored a "Q4" quest
    And Player 3 should be awarded 4 shields for completing the quest
    And Player 2 should be awarded 4 shields for completing the quest
    And Player 4 should be awarded 4 shields for completing the quest
#    Loop for plague event
    And The next player is ready
    And The player's hand is shown
    And The player triggers a "Plague" event
    And the game checks for winners
    And Player 2 should have lost shields from plague
#    Loop for prosperity event
    And The next player is ready
    And The player's hand is shown
    And The player triggers a "Prosperity" event
    And the game checks for winners
    And Prosperity must have happened
#    Loop for queen's favor event
    And The next player is ready
    And The player's hand is shown
    And The player triggers a "Queen's Favor" event
    And the game checks for winners
    And Player 4 should have gained cards from the Queen
#    loop for final quest
    And The next player is ready
    And The player's hand is shown
    And The player triggers a "Q3" event
    And the game checks for winners
    Then Player 1 should have sponsored a "Q3" quest
    And Player 4 should be eliminated for failing a stage
    And Player 2 should have exactly 5 shields
    And Player 3 should have exactly 7 shields
    And Player 4 should have exactly 4 shields
    And Player 3 should be declared a winner

  Scenario: 0_winner_quest
    Given the game is set to "ATEST4" mode
    When a new game starts
    And The next player is ready
    And The player's hand is shown
    And The player triggers a "Q2" event
    And the game checks for winners
    Then Player 1 should have sponsored a "Q2" quest
    And Player 2 should be eliminated for failing a stage
    And Player 3 should be eliminated for failing a stage
    And Player 4 should be eliminated for failing a stage
    And Player 1 should gain 4 cards for sponsoring
    And Player 1 should not have too many cards
    And Player 1 should have exactly 0 shields
    And Player 2 should have exactly 0 shields
    And Player 3 should have exactly 0 shields
    And Player 4 should have exactly 0 shields


