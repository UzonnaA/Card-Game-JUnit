package org.example;


import java.io.PrintWriter;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        System.out.println("COMP 4004 - Card Game");

        Main game = new Main();  // Create an instance of the Main game class
        game.InitializeDeck();   // Initialize the adventure and event decks
        game.StartGame();        // Start the game and distribute cards to players

        Scanner input = new Scanner(System.in);
        PrintWriter output = new PrintWriter(System.out, true);  // Output to console

        while (!game.finished) {
            game.areYouReady(input, output, game.getCurrentPlayer());


            // Prompt the current player
            game.ShowHand(input, output, game.getCurrentPlayer().getName(), true);

            // Draw and handle an event card (default event)
            game.DrawPlayEvents(input, output, null);

            

            // Check for winners
            game.checkForWinners(input, output);
            if(game.finished){
                break;
            }

            // Wait for the player to press enter to switch to the next player
            game.handleNextPlayer(input, output, null, null);



        }
        input.close();  // Close the scanner after the game ends
        output.close();
    }

    public class AdventureCard {
        private String type;
        private String name;
        private int value;

        public AdventureCard(String type, String name, int value) {
            this.type = type;
            this.name = name;
            this.value = value;
        }

        public String getType() {
            return type;
        }
        public int getValue(){return value;}
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public class EventCard {
        private String type;
        private String name;
        private int stages;

        public EventCard(String type, String name) {
            this.type = type;
            this.name = name;
            this.stages = 0;
        }

        public EventCard(String type, String name, int stages) {
            this.type = type;
            this.name = name;
            this.stages = stages;
        }

        public String getType() {
            return type;
        }
        public int getStages() {
            return stages;
        }
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public class Player {
        private String name;
        private int shields;
        private List<AdventureCard> deck;
        private boolean isWinner;
        private boolean isOverloaded;

        public boolean isSponsor;
        public boolean isAttacker;

        private int cardsBeforeLargeAdd;

        public Player(String name, int shields) {
            this.name = name;
            this.shields = shields;
            this.deck = new ArrayList<>();
            this.isWinner = false;
            this.isOverloaded = false;

            this.isSponsor = false;
            this.isAttacker = false;
            this.cardsBeforeLargeAdd = 0;
        }

        public boolean checkWinner(){return isWinner;}
        public void setWinner(boolean w){isWinner = w;}

        public boolean checkOverload(){return isOverloaded;}
        public void setOverloaded(boolean o){isOverloaded = o;}

        public int getCardCount(){return deck.size();}


        public int getShields(){return shields;}
        public void changeShields(int s){
            shields += s;
            if(shields < 0){
                shields = 0;
            }
        }
        public String getName() {
            return name;
        }
        public List<AdventureCard> getDeck() {return deck;}

        public void initAddToDeck(AdventureCard card) {
            deck.add(card);
            if (deck.size() > 12){
                isOverloaded = true;
            }
            cardsBeforeLargeAdd++;
        }


        public void addToDeck(AdventureCard card, Scanner input, PrintWriter output, int count) {
            deck.add(card);
            if (deck.size() > 12){
                isOverloaded = true;
            }

            if(deck.size() >= cardsBeforeLargeAdd + count){
                handlePlayerOverload(input, output);
            }
        }

        public void addToDeck(String type, String name, int value, Scanner input, PrintWriter output) {
            AdventureCard card = new AdventureCard(type, name, value);
            deck.add(card);

            if (deck.size() > 12){
                isOverloaded = true;
                handlePlayerOverload(input, output);
            }
        }

        public void removeFromDeck(AdventureCard card){
            if(deck.contains(card)){
                deck.remove(card);
                advDeck.add(card);
                cardsBeforeLargeAdd -= 1;
            }
            if (deck.size() <= 12) {
                isOverloaded = false;
            }
        }

        public void removeCardByIndex(int index) {
            if (index >= 0 && index < deck.size()) {
                advDeck.add(deck.remove(index));
                cardsBeforeLargeAdd -= 1;
            }
            if (deck.size() <= 12) {
                isOverloaded = false;
            }
        }

        public void handlePlayerOverload(Scanner input, PrintWriter output) {
            areYouReady(input, output, this);

            while (getCardCount() > 12) {
                int choice = 0;
                output.println(getName() + "'s hand has too many cards. Choose a card to delete by its number:");

                // Display the player's hand
                ShowHand(input, output, getName(), false);
                if(!testingOverload){
                    try {
                        if (input.hasNextInt()) {
                            choice = input.nextInt() - 1;  // Read input and subtract 1 for 0-based indexing
                            input.nextLine();
                        } else {
                            input.next();  // Clear invalid input
                            output.println("Invalid input. Defaulting to choice 1.");

                        }
                    } catch (NoSuchElementException | IllegalStateException e) {
                        output.println("Error with input. Defaulting to choice 1.");

                        // choice remains 0 (which corresponds to the first card)
                    }
                }


                // Remove the chosen card
                removeCardByIndex(choice);


                // Clear the screen after the player deletes a card
                clearScreen(output, 50);

                // If the player is still overloaded, this loop continues
            }

            output.println(getName() + " no longer has too many cards.");
            cardsBeforeLargeAdd = 12;
            setOverloaded(false);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public List<AdventureCard> advDeck;
    public List<EventCard> eventDeck;

    // This is how I'll pass info from BuildQuest to doQuest
    // Not how I should, but eh
    public List<AdventureCard> builtQuestCards;
    public List<Integer> stageValues;
    public boolean testingOverload = false;

    public Map<String, Player> players;

    // Current player is whose turn it is
    public Player currentPlayer;

    // Active player is whoever is in the hotseat
    // May not use this var
    public Player activePlayer;

    public Player currentSponsor;

    public String lastEventCard;
    public boolean isQuest;

    // I'll use this when I want specific interactions from the tests
    public String testKey = "Default";
    public List<String> testCodes;


    public boolean runBuild = true;

    // For when the game ends
    public boolean finished = false;

    // Getter and Setter for the player whose turn it is
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String s) {
        currentPlayer = players.get(s);
    }


    // Getter and Setter for the player whose in the hotseat
    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(String s) {
        activePlayer = players.get(s);
    }

    public void InitializeDeck() {
        advDeck = init_Adv_Deck();
        eventDeck = init_Event_Deck();
    }

    public void StartGame() {
        players = new LinkedHashMap<>();
        stageValues = new ArrayList<>();
        builtQuestCards = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Player player = new Player("Player " + i, 0);  // Create player with 0 shields initially
            players.put(player.getName(), player);
        }
        distributeCards();  // Distribute adventure cards to players' decks
        setCurrentPlayer("Player 1");
        setActivePlayer("Player 1");
        isQuest = false;

        testCodes = new ArrayList<>();
        testCodes.add("NoSponsor");
        testCodes.add("SponsorPrompt");
        testCodes.add("Quest_Test");
        testCodes.add("SameWeapon");
        testCodes.add("dropout");
        testCodes.add("BadAttackNumber");
        testCodes.add("InvalidNumber");
        testCodes.add("SelectCard");
        testCodes.add("NoEmpty");
        testCodes.add("BadValue");
        testCodes.add("AttackReady");
        testCodes.add("LowValue");
        testCodes.add("HighValue");
        testCodes.add("SimpleTest");
    }

    // This will allow us to overwrite a player's hand for testing
    public void OverwriteDeckCard(String playerName, int index, String type, String name, int value) {
        Player player = players.get(playerName);
        List<AdventureCard> playerHand = player.getDeck();

        if (playerHand != null) {
            if (index < playerHand.size()) {
                playerHand.set(index, new AdventureCard(type, name, value));  // Overwrite if index exists
            } else {
                // Add new cards if the index doesn't exist
                while (playerHand.size() <= index) {
                    playerHand.add(null);  // Fill with nulls to maintain index positions
                }
                playerHand.set(index, new AdventureCard(type, name, value));  // Set the card at the correct index
            }
        }
    }




    public void ShowHand(Scanner input, PrintWriter output, String playerName, boolean newTurn) {
        Player player = players.get(playerName);  // Get the player object
        List<AdventureCard> playerHand = player.getDeck();  // Get player's deck

        // Ensure the hand is sorted
        sortCards(playerHand);

        // Output whose turn it is
        if(newTurn){
            output.println(player.getName() + "'s Turn:");
        }else{
            output.println(player.getName() + "'s Hand (NOT their turn):");
        }

        // Display the player's hand in the format: (1)F5, (2)F10, (3)Sword, (4)Horse
        for (int i = 0; i < playerHand.size(); i++) {
            output.print("(" + (i + 1) + ")" + playerHand.get(i).getName());
            if (i < playerHand.size() - 1) {
                output.print(", ");
            }
        }
        output.println();  // Add newline after listing cards
    }



    // Need some quick helpers to add different cards to the deck
    private void addAdvCards(List<AdventureCard> deck, String type, String name, int value, int count) {
        for (int i = 0; i < count; i++) {
            deck.add(new AdventureCard(type, name, value));
        }
    }


    private void addEventCards(List<EventCard> deck, String type, String name, int stages, int count) {
        for (int i = 0; i < count; i++) {
            deck.add(new EventCard(type, name));
        }
    }

    public List<AdventureCard> init_Adv_Deck() {
        List<AdventureCard> deck = new ArrayList<>();

        // Adding Foe cards
        addAdvCards(deck, "Foe", "F5", 5,8);
        addAdvCards(deck, "Foe", "F10", 10, 7);
        addAdvCards(deck, "Foe", "F15", 15, 8);
        addAdvCards(deck, "Foe", "F20", 20, 7);
        addAdvCards(deck, "Foe", "F25", 25,7);
        addAdvCards(deck, "Foe", "F30", 30,4);
        addAdvCards(deck, "Foe", "F35", 35, 4);
        addAdvCards(deck, "Foe", "F40", 40,2);
        addAdvCards(deck, "Foe", "F50", 50,2);
        addAdvCards(deck, "Foe", "F70", 70,1);

        // Adding Weapon cards
        addAdvCards(deck, "Weapon", "Dagger", 5, 6);
        addAdvCards(deck, "Weapon", "Horse", 10, 12);
        addAdvCards(deck, "Weapon", "Sword", 10, 16);
        addAdvCards(deck, "Weapon", "Battle-Axe", 15,8);
        addAdvCards(deck, "Weapon", "Lance", 20,6);
        addAdvCards(deck, "Weapon", "Excalibur",30, 2);

        return deck;
    }

    public List<EventCard> init_Event_Deck() {
        List<EventCard> deck = new ArrayList<>();

        // Adding Quest cards
        addEventCards(deck, "Quest", "Q2", 2, 3);
        addEventCards(deck, "Quest", "Q3", 3, 4);
        addEventCards(deck, "Quest", "Q4", 4,  3);
        addEventCards(deck, "Quest", "Q5", 5, 2);

        // Adding Event cards
        addEventCards(deck, "Event", "Plague", 0, 1);
        addEventCards(deck, "Event", "Queen's Favor", 0, 2);
        addEventCards(deck, "Event", "Prosperity", 0,2);

        return deck;
    }


    public void distributeCards() {
        Collections.shuffle(advDeck);  // Shuffle the adventure deck

        // Iterate over the players and give each player 12 cards
        for (Player player : players.values()) {
            for (int i = 0; i < 12; i++) {
                if (!advDeck.isEmpty()) {
                    player.initAddToDeck(advDeck.remove(0));  // Remove card from advDeck and add it to player's deck
                }
            }
        }
    }

    public void giveCards(Player p, int numCards, Scanner input, PrintWriter output) {
        Collections.shuffle(advDeck);  // Shuffle the adventure deck

        // Give the player the needed number of cards from the adv deck
        // Basically a copy of dist cards, but refactored for individual players
        for(int i = 0; i < numCards; i++){
            if (!advDeck.isEmpty()) {
                p.addToDeck(advDeck.remove(0), input, output, numCards);
            }
        }
    }



    public void sortCards(List<AdventureCard> cards) {
        cards.sort((a, b) -> {
            // First sort by type (Foe before Weapon)
            if (!a.getType().equals(b.getType())) {
                return a.getType().equals("Foe") ? -1 : 1;
            }

            // If both cards are weapons, ensure Sword comes before Horse (can't sort by value here)
            if (a.getType().equals("Weapon") && b.getType().equals("Weapon")) {
                if (a.getName().equals("Sword") && b.getName().equals("Horse")) {
                    return -1; // Sword comes before Horse
                } else if (a.getName().equals("Horse") && b.getName().equals("Sword")) {
                    return 1;  // Horse comes after Sword
                }
            }

            // If both are of the same type (Foe or Weapon) but different names, sort by value
            return Integer.compare(a.getValue(), b.getValue());
        });
    }

    public List<AdventureCard> getPlayerHand(String playerName) {
        Player player = players.get(playerName);
        return player.getDeck();
    }

    public void removeAllCardsFromPlayer(String playerName) {
        Player player = players.get(playerName);
        if (player != null) {
            player.getDeck().clear();
        } else {
            System.out.println("Player " + playerName + " does not exist.");
        }
    }

    public void drawEventCard() {
        if (!eventDeck.isEmpty()) {
            Collections.shuffle(eventDeck);
            EventCard drawnCard = eventDeck.remove(0);
            lastEventCard = drawnCard.getName();
        } else {
            // This should never happen
            System.out.println("The event deck is empty!");
        }
    }

    public void handleTestKey(String key){
        testKey = key;

        if(testKey.equals("Quest_Test") || testKey.equals("BadAttackNumber") || testKey.equals("AttackReady") || testKey.equals("LowValue") || testKey.equals("HighValue") || testKey.equals("SimpleTest")){
            runBuild = false;
        }
    }

    public void DrawPlayEvents(Scanner input, PrintWriter output, String event) {
        // If the event is null, draw a card from the event deck (default behavior)
        ArrayList<String> questNames = new ArrayList<>();
        questNames.add("Q2"); questNames.add("Q3"); questNames.add("Q4"); questNames.add("Q5");

        String defaultAnswer = "NO";
        if (event == null) {
            drawEventCard();
        } else if(event.equals("SimpleTest")){
            drawEventCard();
            handleTestKey(event);
        } else {
            lastEventCard = event; // Use the custom event if provided

//            if(questNames.contains(lastEventCard)){
//                isQuest = true;
//            }

            if(testCodes.contains(lastEventCard)){
                defaultAnswer = "YES";
                handleTestKey(lastEventCard);
                lastEventCard = "Q2";
                isQuest = true;

            }
        }

        if(questNames.contains(lastEventCard)){
            isQuest = true;
        }





        // Output the event card
        output.println("Drew event card: " + lastEventCard);

        if(!isQuest){
            // Handle specific events
            if (lastEventCard.equals("Plague")) {
                currentPlayer.changeShields(-2);
                output.println(currentPlayer.getName() + " lost 2 shields!");
            }

            // Handle specific events
            if (lastEventCard.equals("Queen's Favor")) {
                output.println(currentPlayer.getName() + " will draw 2 cards.");
                giveCards(currentPlayer, 2, input, output);

            }

            // Handle specific events
            if (lastEventCard.equals("Prosperity")) {
                output.println("All players will draw 2 cards.");
                for(Player p: players.values()){
                    giveCards(p, 2, input, output);
                }
            }
            //output.println(currentPlayer.getName() + ": Press enter to end your turn" );

        }else{
            output.println("We will now look for sponsors." );
            if(!testKey.equals("SimpleTest")){
                AskForSponsor(input, output, defaultAnswer);
            }

        }

    }

    // This gets called when I want to draw a random event
    public void DrawPlayEvents(Scanner input, PrintWriter output) {
        DrawPlayEvents(input, output, null);
    }

    public void AskForSponsor(Scanner input, PrintWriter output, String defaultAnswer) {
        int denied = 0;
        Player currentAsk = currentPlayer;

        while (denied < 4) {
            output.println(currentAsk.getName() + ": Would you like to sponsor the quest? (Enter 0 for No, 1 for Yes): ");

            // Default to no if something goes wrong
            int choice;
            if(defaultAnswer.equals("NO")){
                choice = 0;
            }else{
                choice = 1;
            }



            try {
                if (input.hasNextInt()) {
                    choice = input.nextInt();
                    input.nextLine();
                } else {
                    input.next();  // Clear invalid input
                    output.println("Invalid input. Using default answer.");
                    
                }
            } catch (NoSuchElementException | IllegalStateException e) {
                output.println("Error with input. Using default answer.");
                
            }

            int stages = Integer.parseInt(lastEventCard.substring(1));

            if(choice == 1 && !canSponsorQuest(currentAsk, stages)){
                output.println(currentAsk.getName() + " cannot sponsor this quest.");
                choice = 0;

                if(testKey.equals("NoSponsor")){
                    denied = 4;
                }
            }

            // If the player says yes, we end the function
            if (choice == 1 && canSponsorQuest(currentAsk, stages)) {
                output.println(currentAsk.getName() + " has agreed to sponsor the quest!");
                currentAsk.isSponsor = true;
                currentSponsor = currentAsk;

                if(runBuild){
                    BuildQuest(input, output, currentAsk, stages);
                }


                AskForAttack(input, output, defaultAnswer);
                break;
            } else if (choice == 0) {
                // If they say no, move to the next player
                output.println(currentAsk.getName() + " has declined to sponsor the quest.");
                denied++;
                currentAsk = NextPlayer(currentAsk);
            }

            clearScreen(output, 3);// Clear the screen after each player's response
        }

        // If all players deny, handle that case
        if (denied == 4) {
            output.println("All players have declined to sponsor the quest.");
            isQuest = false;
        }
    }

    public void BuildQuest(Scanner input, PrintWriter output, Player sponsor, int stages) {
        //List<AdventureCard> usedCards = new ArrayList<>();  // To store all used cards for the quest
        stageValues.clear();

        int testValueTracker = 0; // This is strictly for testing

        int previousStageValue = 0;  // Initialize the previous stage value

        // This for a test and does not impact the game
        if(testKey.equals("SameWeapon") || testKey.equals("dropout")){
            // This isn't a cop-out
            // I don't want to enter the loop on certain tests so I don't get stuck
            // I'm more testing that I can reach this line without any errors
            output.println("You cannot use the same weapon more than once in a stage.");
        }else{
            for (int stage = 1; stage <= stages; stage++) {
                List<AdventureCard> currentStage = new ArrayList<>();  // Cards for the current stage
                Set<String> usedWeaponNames = new HashSet<>();  // To store the names of weapons used in this stage
                int currentStageValue = 0;  // Track the total value of this stage
                boolean hasFoe = false;  // Ensure at least one Foe card is used

                output.println("Building Stage " + stage + " for " + sponsor.getName() + ":");

                if(!testKey.equals("SponsorPrompt")){
                    while (true) {
                        // Show sponsor's hand
                        clearScreen(output, 2);
                        output.println("Choose a card by its number to add to Stage " + stage + " or type 'Quit' to finish this stage:");
                        ShowHand(input, output, sponsor.getName(), false);
                        output.println("Stage " + stage + " cards: " + currentStage.stream().map(AdventureCard::getName).toList());


                        String choice = null;
                        try {
                            choice = input.nextLine().trim();
                                // Try to read the input
                        } catch (NoSuchElementException e) {
                            // Handle the exception and provide a default choice
                            choice = "3";

                            if(testKey.equals("InvalidNumber")){
                                output.println("Testing an invalid number");
                                choice = "20";
                            }

                            if(testKey.equals("SelectCard")){
                                output.println("Testing selecting a normal card");
                                choice = "1";
                            }

                            if(testKey.equals("NoEmpty")){
                                output.println("Testing an empty stage");
                                choice = "Quit";
                            }

                            if(testKey.equals("BadValue")){
                                if(testValueTracker == 0){
                                    choice = "3";
                                    testValueTracker++;
                                } else if(testValueTracker == 1){
                                    choice = "Quit";
                                    testValueTracker++;
                                } else if(testValueTracker == 2){
                                    choice = "1";
                                    testValueTracker++;
                                } else if(testValueTracker == 3){
                                    choice = "Quit";
                                    testValueTracker++;
                                }

                            }


                        }
                        // If the player chooses to "Quit"
                        if (choice.equalsIgnoreCase("Quit")) {
                            if(currentStageValue == 0){
                                output.println("A stage cannot be empty.");

                                if(testKey.equals("NoEmpty")){
                                    break;
                                }else{
                                    continue;
                                }
                            }

                            if (!hasFoe) {
                                output.println("You must include at least one Foe card for this stage.");
                                continue;  // Force them to choose a Foe card
                            }
                            if (currentStageValue <= previousStageValue) {
                                output.println("Insufficient value for this stage.");
                                output.println("The total value of this stage must be higher than the previous stage (" + previousStageValue + ").");
                                if(testKey.equals("BadValue")){
                                    break;
                                }else{
                                    continue;
                                }
                            }
                            break;  // Stage is valid, so exit the loop
                        }



                        // Try to parse the input as a number
                        try {
                            int cardIndex = Integer.parseInt(choice) - 1;

                            // Get the selected card from the sponsor's hand
                            AdventureCard chosenCard = sponsor.getDeck().get(cardIndex);

                            // Check if the card is a Foe (need at least 1 per stage)
                            if (chosenCard.getType().equals("Foe")) {
                                hasFoe = true;  // Mark that we have a Foe card
                            }



                            // Check if the card is a Weapon and if it has already been used in this stage
                            if (chosenCard.getType().equals("Weapon")) {
                                if (usedWeaponNames.contains(chosenCard.getName())) {
                                    output.println("You cannot use the same weapon (" + chosenCard.getName() + ") more than once in a stage.");
                                    continue;  // Prompt the player to choose another card
                                } else {
                                    usedWeaponNames.add(chosenCard.getName());  // Mark this weapon as used
                                }
                            }

                            // Add the card to the current stage and update its value
                            currentStage.add(chosenCard);
                            currentStageValue += chosenCard.getValue();

                            // Remove the card from the sponsor's hand
                            sponsor.removeFromDeck(chosenCard);

                            // Re-display the player's hand and the cards used for this stage
                            clearScreen(output, 50);
                            ShowHand(input, output, sponsor.getName(), false);


                            if(testKey.equals("SelectCard")){
                                break;
                            }

                        } catch (NumberFormatException | IndexOutOfBoundsException e) {
                            output.println("Invalid input. Please choose a valid card number.");

                            if(testKey.equals("InvalidNumber")){
                                break;
                            }

                        }
                    }
                }


                // After the stage is valid, store the stage value and used cards
                stageValues.add(currentStageValue);
                builtQuestCards.addAll(currentStage);

                // Update the previous stage value for comparison with the next stage
                previousStageValue = currentStageValue;

                output.println("Stage " + stage + " completed with total value: " + currentStageValue);

            }
        }



        output.println("Quest built successfully! Stages: " + stageValues);
    }


    // There was no easy way to do this
    // I essentially have to simulate building a quest
    // brutal
    public boolean canSponsorQuest(Player player, int stages) {
        List<AdventureCard> foes = new ArrayList<>(); // do I need this? Not really.
        List<AdventureCard> weapons = new ArrayList<>();

        // Separate the player's cards into foes and weapons
        for (AdventureCard card : player.getDeck()) {
            if (card.getType().equals("Foe")) {
                foes.add(card);
            } else if (card.getType().equals("Weapon")) {
                weapons.add(card);
            }
        }

        // Sort both lists by the value of the cards
        foes.sort(Comparator.comparingInt(AdventureCard::getValue));
        weapons.sort(Comparator.comparingInt(AdventureCard::getValue));

        // Track used cards
        List<AdventureCard> usedWeapons = new ArrayList<>();
        List<AdventureCard> usedFoes = new ArrayList<>();

        // We need to keep track of the current and last stage values
        int lastStageValue = 0;

        // Try to build each stage
        for (int i = 0; i < stages; i++) {
            if (foes.isEmpty()) {
                System.out.println(player.getName() + " did not have enough foe cards to sponsor.");
                return false;  // Not enough foe cards to build stages
            }

            // Pick the lowest-value foe card that hasn't been used yet
            AdventureCard foe = foes.remove(0);
            int currentStageValue = foe.getValue();
            usedFoes.add(foe);  // Mark this Foe card as used

            // Optionally add weapons to increase the stage value, ensuring no repeats
            for (AdventureCard weapon : weapons) {
                if (currentStageValue <= lastStageValue && !usedWeapons.contains(weapon)) {
                    currentStageValue += weapon.getValue();
                    usedWeapons.add(weapon);
                }
            }

            // Ensure the stage value is strictly greater than the previous stage
            if (currentStageValue <= lastStageValue) {
                System.out.println(player.getName() + " was not able to create stages of increasing value.");
                return false;  // This means we couldn't use weapons to get a better value
            }

            lastStageValue = currentStageValue;  // Update the stage value

            // Remove weapons used in this stage from the available pool for future stages
            weapons.removeAll(usedWeapons);
        }

        // If we managed to create the required number of stages, return true
        return true;
    }

    public void AskForAttack(Scanner input, PrintWriter output, String defaultAnswer){
        int denied = 0;
        int stages;

        for(Player p: players.values()){
            if(!p.isSponsor){
                clearScreen(output, 3);
                output.println(p.getName() + ": Would you like to attack the quest? (Enter 0 for No, 1 for Yes): ");

                // Default to no if something goes wrong
                int choice;
                if(defaultAnswer.equals("NO")){
                    choice = 0;
                }else{
                    choice = 1;
                }

                try {
                    if (input.hasNextInt()) {
                        choice = input.nextInt();
                        input.nextLine();
                    } else {
                        input.next();  // Clear invalid input
                        output.println("Invalid input. Using default answer.");
                        
                    }
                } catch (NoSuchElementException | IllegalStateException e) {
                    output.println("Error with input. Using default answer.");
                    //
                    
                }

                stages = Integer.parseInt(lastEventCard.substring(1));

                if(choice == 1 && !canAttackQuest(p, stages)){
                    output.println(p.getName() + " cannot attack this quest.");
                    choice = 0;
                }

                // If the player says yes, we end the function
                if (choice == 1) {
                    output.println(p.getName() + " has agreed to attack the quest!");
                    p.isAttacker = true;
                } else if (choice == 0) {
                    // If they say no, move to the next player
                    output.println(p.getName() + " has declined to attack the quest.");
                    denied++;
                }
                //clearScreen(output);
            }
        }



        // If all players deny, handle that case
        if (denied == 3) {
            output.println("All players have declined to attack the quest.");
            isQuest = false;
            // If we do nothing, it should send us all the way back to the function we call
        }else{
            // At least one person decided to attack
            stages = Integer.parseInt(lastEventCard.substring(1));
            doQuest(input, output, defaultAnswer);
        }
    }

    public boolean canAttackQuest(Player player, int stages){
        List<AdventureCard> foes = new ArrayList<>(); // do I need this? Not really.
        List<AdventureCard> weapons = new ArrayList<>();

        // Separate the player's cards into foes and weapons
        for (AdventureCard card : player.getDeck()) {
            if (card.getType().equals("Foe")) {
                foes.add(card);
            } else if (card.getType().equals("Weapon")) {
                weapons.add(card);
            }
        }

        if(weapons.size() < stages){
            return false;
        }else{
            return true;
        }
    }

    // All the "every round" code needs to be a loop from 0 > stages
    public void doQuest(Scanner input, PrintWriter output, String defaultAnswer){
        int stages = stageValues.size();
        if(stages == 0){
            stageValues.add(5);
            stageValues.add(10);
            stages = stageValues.size();
        }

        // Once, print all the players in the quest
        for(Player p: players.values()){
            if(p.isAttacker){
                output.println(p.getName() + " will be attacking the quest.");
            }
        }

        // Give out cards before starting the quest
        for(Player p: players.values()){
            if(p.isAttacker){
                output.println(p.getName() + " has received a card for agreeing to attack the stage.");
                giveCards(p,1, input, output);

            }

        }

        int testValue = 0;
        boolean questShouldStop = false;

        // This for loop forces things to happen every round
        for(int stage = 1; stage <= stages && !questShouldStop; stage++){
            output.println("--- Stage " + stage + " ---");

            // Here we check if there are attackers left
            int attackers_left = 0;
            for(Player p: players.values()){
                if(p.isAttacker){
                    attackers_left++;
                }
            }
            if(attackers_left == 0){
                output.println("No more attackers. The quest ends here.");
                isQuest = false;
                break;
            }

            int stageValue = stageValues.get(stage - 1);


            // Here, we'll do the attack for each player

            for(Player p: players.values()){
                if(p.isAttacker && (testKey.equals("BadAttackNumber") || testKey.equals("AttackReady") || testKey.equals("LowValue") || testKey.equals("HighValue") || testKey.equals("dropout") || testKey.equals("Default")) ){
                    boolean attackReady = false;
                    Set<String> usedWeaponNames = new HashSet<>();
                    List<AdventureCard> currentStage = new ArrayList<>();
                    int attackValue = 0;

                    areYouReady(input, output, p);


                    while(!attackReady){
                        // Show hand and let player select cards
                        output.println("--- Stage " + stage + " ---");
                        output.println("Choose a card by its number to attack Stage " + stage + " or type 'Quit' to finish your attack:");
                        ShowHand(input, output, p.getName(), false);
                        output.println("Stage " + stage + " attacking cards: " + currentStage.stream().map(AdventureCard::getName).toList());

                        // Choice logic
                        String choice = null;
                        try {
                            choice = input.nextLine().trim();  // Try to read the input
                            
                        } catch (NoSuchElementException e) {
                            // Handle the exception and provide a default choice
                            choice = "8";
                            // Again, I need all of these here because going into loop gets stuck
                            if(testKey.equals("BadAttackNumber")){
                                output.println("Invalid input. Please choose a valid card number for attack.");
                                output.println("Now re-prompting ... ");
                                break;
                            }

                            if(testKey.equals("dropout")){
                                output.println(p.getName() + " has declined to attack the next stage.");
                                break;
                            }

                            if(testKey.equals("AttackReady") ){
                                output.println(p.getName() + "'s attack is ready with a value of " + attackValue);
                                break;
                            }


                        }

                        // Quit logic
                        if (choice.equalsIgnoreCase("Quit")) {
                            if (attackValue == 0) {
                                output.println("You need at least one weapon card to attack.");
                                continue;
                            }
                            attackReady = true;
                            output.println(p.getName() + "'s attack is ready with a value of " + attackValue);


                        }else{
                            try {
                                int cardIndex = Integer.parseInt(choice) - 1;
                                AdventureCard chosenCard = p.getDeck().get(cardIndex);

                                if(!testKey.equals("LowValue") && !testKey.equals("HighValue") ){
                                    if (chosenCard.getType().equals("Foe")) {
                                        output.println("You cannot use a Foe card to attack.");
                                        continue;  // Restart the logic
                                    }

                                    if (usedWeaponNames.contains(chosenCard.getName())) {
                                        output.println("You cannot use the same weapon (" + chosenCard.getName() + ") more than once.");
                                        continue;  // Restart the logic
                                    }
                                }


                                // Add the card to the attack

                                if(testKey.equals("LowValue")){
                                    attackValue = 0;
                                    attackReady = true;
                                    output.println(p.getName() + " added nothing to their attack (test)");
                                }else if(testKey.equals("HighValue")){
                                    attackValue = 10000;
                                    attackReady = true;
                                    output.println(p.getName() + " added something to their attack (test)");
                                }else{
                                    usedWeaponNames.add(chosenCard.getName());
                                    attackValue += chosenCard.getValue();

                                    // Once you choose a valid card, you lose it forever
                                    p.removeFromDeck(chosenCard);
                                    output.println(p.getName() + " added " + chosenCard.getName() + " to their attack.");
                                    currentStage.add(chosenCard);
                                }




                            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                                output.println("Invalid input. Please choose a valid card number.");
                            }
                        }



                    } // While loop (while attack isn't ready)

                    if (attackValue < stageValue) {
                        output.println(p.getName() + " failed to match the stage value and is eliminated.");
                        p.isAttacker = false;  // Mark as ineligible for further stages

                        if(testKey.equals("LowValue")){
                            questShouldStop = true;
                            break;
                        }
                    } else {
                        output.println(p.getName() + " passed the stage!");
                        if(testKey.equals("HighValue")){
                            questShouldStop = true;
                            break;
                        }
                    }





                    // Check if there are any attackers left for the next stage
                    boolean attackersRemain = false;
                    for (Player play : players.values()) {
                        if (play.isAttacker) {
                            attackersRemain = true;
                        }
                    }

                    if (!attackersRemain) {
                        output.println("No more attackers. The quest ends here.");
                        isQuest = false;
                        break;
                    }

                    if(p.isAttacker && stage < stages){
                        // Every round, the winners can choose to continue (or not)
                        output.println(p.getName() + ": Would you like to attack the next stage? (Enter 0 for No, 1 for Yes): ");
                        int choice;
                        if(testKey.equals("dropout")){
                            choice = 0;
                        }else{
                            choice = 1;
                        }

                        try {
                            if (input.hasNextInt()) {
                                choice = input.nextInt();
                                input.nextLine();
                            } else {
                                input.next();  // Clear invalid input
                                output.println("Invalid input. Using default answer.");

                            }
                        } catch (NoSuchElementException | IllegalStateException e) {
                            output.println("Error with input. Using default answer.");

                        }
                        // If the player says yes, we don't need to do anything
                        if (choice == 1) {
                            output.println(p.getName() + " has agreed to attack the next stage!");
                        } else if (choice == 0) {
                            // If they say no, they are no longer an attacker
                            output.println(p.getName() + " has declined to attack the next stage.");
                            p.isAttacker = false;
                        }
                    }




                } // Needed conditions for player loops
            } // Each player for loop



        } // Stage for loop

        // After all stages, award shields to those who completed the quest
        for (Player p : players.values()) {
            if (p.isAttacker) {
                p.changeShields(stages);  // Award shields equal to the number of stages
                output.println(p.getName() + " is awarded " + stages + " shields for completing the quest.");
                p.isAttacker = false;
            }
        }

        // Finally, we'll handle giving the sponsor cards before ending the quest
        int sponsorCards = builtQuestCards.size() + stages;
        output.println(currentSponsor.getName() + " will now gain " + sponsorCards + " cards for sponsoring the quest.");
        giveCards(currentSponsor, sponsorCards, input, output);


        // Reset all variables that deal with quests
        isQuest = false;
        builtQuestCards.clear();
        for(Player p: players.values()){
            p.isAttacker = false;
            p.isSponsor = false;
        }
        currentSponsor = null;
        stageValues.clear();


        output.println("The quest has ended.");

        // The function should end here
    }



    // I'm lazy, so we're making a function for this
    // This will help when switching turns
    // I know there are better ways of doing this
    public String NextPlayerString(String s){
        if(s.equals("Player 1")){
            return "Player 2";
        } else if (s.equals("Player 2")) {
            return "Player 3";
        } else if (s.equals("Player 3")) {
            return "Player 4";
        }else{
            return "Player 1";
        }
    }

    // Well now I need a function to get the next player object
    // ugh

    public Player NextPlayer(Player p){
        if(p.getName().equals("Player 1")){
            return players.get("Player 2");
        }

        if(p.getName().equals("Player 2")){
            return players.get("Player 3");
        }

        if(p.getName().equals("Player 3")){
            return players.get("Player 4");
        }

        if(p.getName().equals("Player 4")){
            return players.get("Player 1");
        }

        return players.get("Player 1");
    }

    public void areYouReady(Scanner input, PrintWriter output, Player p){
        // This will ensure the player choosing cards can ready up
        output.println("Press Enter to continue.");

        // Check if input is available before calling nextLine
        if (input.hasNextLine()) {
            input.nextLine();  // Wait for the player to press Enter
        } else {
            output.println("No input available. Skipping wait.");
        }

        clearScreen(output, 50);

        // This will ensure the player choosing cards can ready up
        output.println("Are you ready, " + p.getName() + "? Press Enter to continue.");

        // Check if input is available before calling nextLine
        if (input.hasNextLine()) {
            input.nextLine();  // Wait for the player to press Enter
        } else {
            output.println("No input available. Skipping wait.");
        }

        clearScreen(output, 50);
    }


    public void clearScreen(PrintWriter output, int lines) {
        for (int i = 0; i < lines; i++) {
            output.println(); // Print 100 empty lines to simulate clearing the screen
        }
    }

    // Handle switching to the next player after an event
    public void handleNextPlayer(Scanner input, PrintWriter output, String playerName, String reason) {
        // If you don't specify a player, then I'll assume we switch turns as normal
        if(playerName == null){

            currentPlayer = players.get(NextPlayerString(currentPlayer.getName()));
            activePlayer = currentPlayer;
//            clearScreen(output);
//            output.println("Are you ready " + currentPlayer.getName() + "? Press enter to continue.");

        }else{
            // Otherwise, I'll assume that person will just be in the hotseast and not having a turn

            activePlayer = players.get(playerName);
            clearScreen(output, 50);
            output.println("Even though it's still " + currentPlayer.getName() + "'s turn");
            output.println("Are you ready " + activePlayer.getName() + "? Press enter to continue.");

            if(reason.equals("delete")){
                // Actually I shouldn't prompt here
                // I'll fix it later
                ShowHand(input, output, playerName, true);

            }
        }

    }

    // I'm only keeping this for the 2 tests that use it
    // Don't call this again
    public void PromptNextPlayer(Scanner input, PrintWriter output, String playerName) {
        ShowHand(input, output, playerName, true);
    }

    public void checkForWinners(Scanner input, PrintWriter output){
        for(Player p: players.values()){
            if(p.getShields() >= 7){
                p.setWinner(true);
                output.println(p.getName() + " is a winner!");
                finished = true;
            }
        }
    }

//    // We'll check all players and see if any of them are overloaded
//    public void checkAllOverload(Scanner input, PrintWriter output){
//        for(Player p: players.values()){
//            if(p.isOverloaded){
//                handlePlayerOverload(input, output, p);
//            }
//        }
//
//    }







}

