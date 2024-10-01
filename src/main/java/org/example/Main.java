package org.example;


import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        System.out.println("COMP 4004 - Card Game");
    }

    class AdventureCard {
        private String type;
        private String name;
        private int value;

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }

    class EventCard{
        private String name;
        private String type;

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }

    public List<EventCard> init_Event_Deck(){
        return new ArrayList<>();
    }

    public List<AdventureCard> init_Adv_Deck(){
        return new ArrayList<>();
    }


}

