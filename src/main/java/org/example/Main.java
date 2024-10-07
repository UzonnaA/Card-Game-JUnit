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
            // Prompt the current player
            game.PromptPlayer(input, output, game.getCurrentPlayer().getName());

            // Draw and handle an event card (default event)
            game.DrawPlayEvents(input, output);

            input.nextLine();

            // Check for winners
            game.checkForWinners(input, output);
            if(game.finished){
                break;
            }

            game.checkAllOverload(input, output);


            // Wait for the player to press enter to switch to the next player
            game.handleNextPlayer(input, output, null, null);
            input.nextLine();


        }
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

        public Player(String name, int shields) {
            this.name = name;
            this.shields = shields;
            this.deck = new ArrayList<>();
            this.isWinner = false;
            this.isOverloaded = false;

            this.isSponsor = false;
            this.isAttacker = true;
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
        public void addToDeck(AdventureCard card) {
            deck.add(card);
            if (deck.size() > 12){
                isOverloaded = true;
            }
        }

        public void addToDeck(String type, String name, int value) {
            AdventureCard card = new AdventureCard(type, name, value);
            deck.add(card);

            if (deck.size() > 12){
                isOverloaded = true;
            }
        }

        public void removeCardByIndex(int index) {
            if (index >= 0 && index < deck.size()) {
                deck.remove(index);
                if (deck.size() <= 12) {
                    isOverloaded = false;
                }
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public List<AdventureCard> advDeck;
    public List<EventCard> eventDeck;
    public Map<String, Player> players;
    // Current player is whose turn it is
    public Player currentPlayer;

    // Active player is whoever is in the hotseat
    public Player activePlayer;

    public String lastEventCard;
    public boolean isQuest;

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
        players = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            Player player = new Player("Player " + i, 0);  // Create player with 0 shields initially
            players.put(player.getName(), player);
        }
        distributeCards();  // Distribute adventure cards to players' decks
        setCurrentPlayer("Player 1");
        setActivePlayer("Player 1");
        isQuest = false;
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




    public void PromptPlayer(Scanner input, PrintWriter output, String playerName) {
        Player player = players.get(playerName);  // Get the player object
        List<AdventureCard> playerHand = player.getDeck();  // Get player's deck

        // Ensure the hand is sorted
        sortCards(playerHand);

        // Output whose turn it is
        output.println(player.getName() + "'s Turn:");

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
                    player.addToDeck(advDeck.remove(0));  // Remove card from advDeck and add it to player's deck
                }
            }
        }
    }

    public void giveCards(Player p, int numCards) {
        Collections.shuffle(advDeck);  // Shuffle the adventure deck

        // Give the player the needed number of cards from the adv deck
        // Basically a copy of dist cards, but refactored for individual players
        for(int i = 0; i < numCards; i++){
            if (!advDeck.isEmpty()) {
                p.addToDeck(advDeck.remove(0));
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
            Random rand = new Random();
            int index = rand.nextInt(eventDeck.size());  // Randomly select a card from the event deck
            EventCard drawnCard = eventDeck.remove(index);  // Remove the card from the deck
            //System.out.println("Drew event card: " + drawnCard.getName());
            lastEventCard = drawnCard.getName();
        } else {
            //System.out.println("The event deck is empty!");
        }
    }

    public void DrawPlayEvents(Scanner input, PrintWriter output, String event) {
        // If the event is null, draw a card from the event deck (default behavior)
        String defaultAnswer = "NO";
        if (event == null) {
            drawEventCard();
        } else {
            lastEventCard = event;  // Use the custom event if provided

            if(lastEventCard.equals("Q2") || lastEventCard.equals("Q3") || lastEventCard.equals("Q4") || lastEventCard.equals("Q5")){
                isQuest = true;
            }

            if(lastEventCard.equals("Quest_Test")){
                defaultAnswer = "YES";
                lastEventCard = "Q2";
                isQuest = true;
            }
        }

        // Output the event card
        if (lastEventCard != null) {
            output.print("Drew event card: " + lastEventCard + "\n");
        }

        if(!isQuest){
            // Handle specific events
            if (lastEventCard.equals("Plague")) {
                currentPlayer.changeShields(-2);
                output.print(currentPlayer.getName() + " lost 2 shields!" + "\n");
            }

            // Handle specific events
            if (lastEventCard.equals("Queen's Favor")) {
                output.print(currentPlayer.getName() + " will draw 2 cards." + "\n");
                giveCards(currentPlayer, 2);

            }

            // Handle specific events
            if (lastEventCard.equals("Prosperity")) {
                output.print("All players will draw 2 cards." + "\n");
                for(Player p: players.values()){
                    giveCards(p, 2);
                }
            }
            output.println("Press enter to end your turn" + "\n");

        }else{
            AskForSponsor(input, output, defaultAnswer);
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
            output.print(currentAsk.getName() + ": Would you like to sponsor the quest? (Enter 0 for No, 1 for Yes): ");

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
            }

            // If the player says yes, we end the function
            if (choice == 1) {
                output.println(currentAsk.getName() + " has agreed to sponsor the quest!");
                AskForAttack(input, output, defaultAnswer);
                break;
            } else if (choice == 0) {
                // If they say no, move to the next player
                output.println(currentAsk.getName() + " has declined to sponsor the quest.");
                denied++;
                currentAsk = NextPlayer(currentAsk);
            }

            clearScreen(output);  // Clear the screen after each player's response
        }

        // If all players deny, handle that case
        if (denied == 4) {
            output.println("All players have declined to sponsor the quest.");
            isQuest = false;
        }
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
        Player currentAsk = players.get("Player 1");

        for(int i = 0; i < 4; i++) {
            if(!currentAsk.isSponsor){
                output.print(currentAsk.getName() + ": Would you like to attack the quest? (Enter 0 for No, 1 for Yes): ");

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
                    } else {
                        input.next();  // Clear invalid input
                        output.println("Invalid input. Using default answer.");
                    }
                } catch (NoSuchElementException | IllegalStateException e) {
                    output.println("Error with input. Using default answer.");
                }

                int stages = Integer.parseInt(lastEventCard.substring(1));

                if(choice == 1 && !canAttackQuest(currentAsk, stages)){
                    output.println(currentAsk.getName() + " cannot attack this quest.");
                    choice = 0;
                }

                // If the player says yes, we end the function
                if (choice == 1) {
                    output.println(currentAsk.getName() + " has agreed to attack the quest!");
                    currentAsk.isAttacker = true;
                } else if (choice == 0) {
                    // If they say no, move to the next player
                    output.println(currentAsk.getName() + " has declined to attack the quest.");
                    denied++;
                    currentAsk = NextPlayer(currentAsk);
                }
                clearScreen(output);
            }
             // Clear the screen after each player's response
        }

        // If all players deny, handle that case
        if (denied == 3) {
            output.println("All players have declined to attack the quest.");
            isQuest = false;
            // If we do nothing, it should send us all the way back to the function we call
        }else{
            // At least one person decided to attack
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

    public void doQuest(Scanner input, PrintWriter output, String defaultAnswer){
        // All this needs to do right now is print attacking players

        for(Player p: players.values()){
            if(p.isAttacker){
                output.println(p.getName() + " will be attacking the quest.");
            }
        }

        // I have responsibilities in an odd order
        // So let's assume that right here would be code
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


    public void clearScreen(PrintWriter output) {
        for (int i = 0; i < 100; i++) {
            output.println(); // Print 100 empty lines to simulate clearing the screen
        }
    }

    // Handle switching to the next player after an event
    public void handleNextPlayer(Scanner input, PrintWriter output, String playerName, String reason) {
        // If you don't specify a player, then I'll assume we switch turns as normal
        if(playerName == null){

            currentPlayer = players.get(NextPlayerString(currentPlayer.getName()));
            activePlayer = players.get(NextPlayerString(currentPlayer.getName()));
            clearScreen(output);
            output.println("Are you ready " + currentPlayer.getName() + "? Press enter to continue.");

        }else{
            // Otherwise, I'll assume that person will just be in the hotseast and not having a turn

            activePlayer = players.get(playerName);
            clearScreen(output);
            output.println("Even though it's still " + currentPlayer.getName() + "'s turn");
            output.println("Are you ready " + activePlayer.getName() + "? Press enter to continue.");

            if(reason.equals("delete")){
                // Actually I shouldn't prompt here
                // I'll fix it later
                PromptPlayer(input, output, playerName);

            }
        }

    }

    // I'm only keeping this for the 2 tests that use it
    // Don't call this again
    public void PromptNextPlayer(Scanner input, PrintWriter output, String playerName) {
        PromptPlayer(input, output, playerName);
    }

    public void checkForWinners(Scanner input, PrintWriter output){
        for(Player p: players.values()){
            if(p.getShields() >= 7){
                p.setWinner(true);
                output.print("\n" + p.getName() + " is a winner!");
                finished = true;
            }
        }
    }

    // We'll check all players and see if any of them are overloaded
    public void checkAllOverload(Scanner input, PrintWriter output){
        for(Player p: players.values()){
            if(p.isOverloaded){
                handlePlayerOverload(input, output, p);
            }
        }

    }

    public void handlePlayerOverload(Scanner input, PrintWriter output, Player player) {
        while (player.getCardCount() > 12) {
            int choice = 0;
            output.println(player.getName() + "'s hand has too many cards. Choose a card to delete by its number:");

            // Display the player's hand
            List<AdventureCard> playerHand = player.getDeck();
            for (int i = 0; i < playerHand.size(); i++) {
                output.println("(" + (i + 1) + ") " + playerHand.get(i).getName());
            }

            try {
                if (input.hasNextInt()) {
                    choice = input.nextInt() - 1;  // Read input and subtract 1 for 0-based indexing
                } else {
                    input.next();  // Clear invalid input
                    output.println("Invalid input. Defaulting to choice 1.");
                }
            } catch (NoSuchElementException | IllegalStateException e) {
                output.println("Error with input. Defaulting to choice 1.");
                // choice remains 0 (which corresponds to the first card)
            }

            // Remove the chosen card
            player.removeCardByIndex(choice);


            // Clear the screen after the player deletes a card
            clearScreen(output);

            // If the player is still overloaded, this loop continues
        }

        output.println(player.getName() + " no longer has too many cards.");
    }





}

