package it.polimi.ingsw.triton.launcher.utils.message;

public enum ErrorTypeID {
    USERNAME_ALREADY_CHOSEN("This username is already used!"),
    WRONG_PLAYERS_NUMBER("The number of players must be 2 or 3!"),
    FORBIDDEN_USERNAME("Username is not correct!"),
    FULL_LOBBY("Lobby is full!"),
    WRONG_USERNAME("This username doesn't exists!"),
    WRONG_COLOR("This color cannot be chosen!"),
    WRONG_WIZARD("This wizard cannot be chosen!"),
    ILLEGAL_MOVE("This move cannot be done"),
    ASSISTANTCARD_ALREADY_CHOSEN("This assistant card is already used!"),
    TOO_MANY_MOTHERNATURE_STEPS("The number of steps exceeds the maximum possible!"),
    NULL_COLOR("Color cannot be null!"),
    EMPTY_BAG("The bag is empty; a student cannot be drawn!"),
    EMPTY_GENERAL_COIN_SUPPLY("The general coin supply is empty! You won't receive the coin!"),
    CLOUD_TILE_ALREADY_CHOSEN("The selected cloud tile is already selected!"),
    NOT_ENOUGH_COINS("You don't have enough coins to buy this character card!"),
    CHARACTER_CARD_NOT_AVAILABLE("The character card selected is not available!"),
    NO_STUDENT_WITH_COLOR_ENTRANCE("There aren't students with that color in your entrance!"),
    NO_ISLAND_WITH_THAT_ID("There aren't existing islands with that id!");

    private final String description;

    ErrorTypeID(final String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
