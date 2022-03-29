package it.polimi.ingsw.triton.launcher.model;

public class CharacterCardFactory {

    public static CharacterCard getCarachterCard(int characterCardNumber,int cost,Bag bag) {
        switch (characterCardNumber) {
            case 1:
                return new CharacterCard01(characterCardNumber, cost, bag);
            case 2:
                return new CharacterCard02(characterCardNumber, cost);
            case 3:
                return new CharacterCard03(characterCardNumber, cost);
            default:
                throw new RuntimeException("This CharacterCard does not exist!");
        }
    }

    public static void main(String[] args) {
        CharacterCard characterCard03 = CharacterCardFactory.getCarachterCard(3,3,null);
    }
}
