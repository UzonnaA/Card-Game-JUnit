package org.example;


import java.io.PrintWriter;
import java.util.*;

public class Main {


    public static void main(String[] args) {
        System.out.println("COMP 4004 - Card Game");
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

//        @Override
//        public String toString() {
//            return name;
//        }
    }

    public class EventCard {
        private String type;
        private String name;

        public EventCard(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

//        @Override
//        public String toString() {
//            return name;
//        }
    }

    private List<AdventureCard> advDeck;
    private List<EventCard> eventDeck;
    private Map<String, List<AdventureCard>> players;
    private String currentPlayer;

    // Getter and Setter for the player whose turn it is
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String player) {
        this.currentPlayer = player;
    }

    public void InitializeDeck() {
        advDeck = init_Adv_Deck();
        eventDeck = init_Event_Deck();
    }

    // Overwrite cards in deck for testing purposes
    public void OverwriteDeckCard(int index, String type, String name, int value) {
        advDeck.set(index, new AdventureCard(type, name, value));
    }

    public void Introduction(PrintWriter output) {
        output.println("Welcome to the card game!");
    }

    public void StartGame() {
        players = distributeCards(advDeck, 4, 12);
        setCurrentPlayer("Player 1");
    }



    public void PromptPlayer(Scanner input, PrintWriter output, String playerName) {
        List<AdventureCard> playerHand = players.get(playerName);

        // Ensure the hand is sorted
        sortCards(playerHand);

        // Output whose turn it is
        output.println(playerName + "'s Turn:");

        // This is the format I'll be using: (1)F5, (2)F10, (3)Sword, (4)Horse
        for (int i = 0; i < playerHand.size(); i++) {
            output.print("(" + (i + 1) + ")" + playerHand.get(i));
            if (i < playerHand.size() - 1) {
                output.print(", ");
            }
        }
        output.println(); // Add newline after listing cards
    }

    // Need some quick helpers to add different cards to the deck
    private void addAdvCards(List<AdventureCard> deck, String type, String name, int value, int count) {
        for (int i = 0; i < count; i++) {
            deck.add(new AdventureCard(type, name, value));
        }
    }


    private void addEventCards(List<EventCard> deck, String type, String name, int count) {
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
        addEventCards(deck, "Quest", "Q2", 3);
        addEventCards(deck, "Quest", "Q3", 4);
        addEventCards(deck, "Quest", "Q4", 3);
        addEventCards(deck, "Quest", "Q5", 2);

        // Adding Event cards
        addEventCards(deck, "Event", "Plague", 1);
        addEventCards(deck, "Event", "Queen Favor", 2);
        addEventCards(deck, "Event", "Prosperity", 2);

        return deck;
    }


    public Map<String, List<AdventureCard>> distributeCards(List<AdventureCard> deck, int numPlayers, int cardsPerPlayer) {
        Map<String, List<AdventureCard>> players = new HashMap<>();
        Collections.shuffle(deck); // Here we shuffle to ensure random cards

        for (int i = 0; i < numPlayers; i++) {
            List<AdventureCard> playerHand = new ArrayList<>();
            for (int j = 0; j < cardsPerPlayer; j++) {
                if (!deck.isEmpty()) {
                    playerHand.add(deck.remove(0)); // Make sure we actually remove from deck when we add to hand
                }
            }
            players.put("Player" + (i + 1), playerHand); // Assign hand to player
        }

        return players;
    }

    public void sortCards(List<AdventureCard> cards) {
        // Simple sorting alg
        cards.sort((a, b) -> {
            if (a.getType().equals(b.getType())) {
                return Integer.compare(a.getValue(), b.getValue());
            }
            return a.getType().equals("Foe") ? -1 : 1; // This line ensures that Foes come before weapons
        });
    }

    public List<AdventureCard> getPlayerHand(String playerName) {
        return new ArrayList<>();
    }


}

