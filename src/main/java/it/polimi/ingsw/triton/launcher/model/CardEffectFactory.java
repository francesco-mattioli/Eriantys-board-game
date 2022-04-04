package it.polimi.ingsw.triton.launcher.model;

public class CardEffectFactory {
        public static CardEffect getCardEffect(int characterCardId,Bag bag,Color student,Island island) {
            switch (characterCardId) {
                case 1:
                    return new CardEffect01(bag,student,island);
                default:
                    throw new RuntimeException("This CharacterCard does not exist!");
            }
        }
}
