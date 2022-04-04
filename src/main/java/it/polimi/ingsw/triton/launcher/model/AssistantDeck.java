package it.polimi.ingsw.triton.launcher.model;

import java.util.ArrayList;

public class AssistantDeck {
    private Wizard wizard;
    private ArrayList<AssistantCard> assistantDeck = new ArrayList<>();

    public AssistantDeck(Wizard wizard){
        this.wizard=wizard;
        for(int i = 0; i < 10; i++){
            assistantDeck.add(new AssistantCard(AssistantCardType.values()[i]));
        }
    }

}
