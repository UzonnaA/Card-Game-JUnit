Feature: Assignment 2

  Scenario: A1_Scenario
    Given a new game starts
    When the game is set to ATEST mode
    And Player 1 is ready
    And Player 1's hand is shown
    And Player 1 triggers a Q4 event
    And the game checks for winners
    Then Player 1 should have 0 shields
    And Player 1 should have cards "(1)F5, (2)F10, (3)F15, (4)F15, (5)F30, (6)Horse, (7)Battle-Axe, (8)Battle-Axe, (9)Lance"
    And Player 3 should have 0 shields
    And Player 3 should have cards "(1)F5, (2)F5, (3)F15, (4)F30, (5)Sword"
    And Player 4 should have 4 shields
    And Player 4 should have cards "(1)F15, (2)F15, (3)F40, (4)Lance"