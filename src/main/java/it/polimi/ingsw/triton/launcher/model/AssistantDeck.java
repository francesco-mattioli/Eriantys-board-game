package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.model.enums.Wizard;

import java.util.ArrayList;

public class AssistantDeck {
    private Wizard wizard;
    private ArrayList<AssistantCard> assistantDeck = new ArrayList<>();
    private final int CARDS_NUMBER = 10;

    public AssistantDeck(Wizard wizard){
        this.wizard=wizard;
        for(int i = 0; i < CARDS_NUMBER; i++){
            assistantDeck.add(new AssistantCard(AssistantCardType.values()[i]));
        }
    }

}
