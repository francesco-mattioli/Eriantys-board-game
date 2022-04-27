package it.polimi.ingsw.triton.launcher.utils.message;

public enum MessageType {
    LOGIN_REQUEST,
    LOGIN_REPLY,
    PLAYERSNUMBER_REQUEST,
    PLAYERSNUMBER_REPLY,
    TOWER_COLOR_REQUEST,
    TOWER_COLOR_REPLY,
    WIZARD_REQUEST,
    WIZARD_REPLY,
    LOBBY,
    COLORS_REQUEST, //What color?
    COLORS_REPLY,   //What color?
    ASSISTANT_CARD_REQUEST,
    ASSISTANT_CARD_REPLY,
    CHARACTER_CARD_REQUEST,   //a player wants to use a character card
    CHARACTER_CARD_REPLY,     //server returns the available character cards
    CHARACTER_CARD_CHOICE,    //the card selected by the user
    MOVE, // to understand ??
    CLOUD_TILE_REQUEST,
    CLOUD_TILE_REPLY,
    MOTHER_NATURE_POSITION,
    WIN,
    GAME_INFO,
    GENERIC,
    DISCONNECTION,
    ERROR,
    FULL_LOBBY,
    EMPTY_BAG,
    FILLED_CLOUD_TILES,
    YOUR_TURN,
    INFO_ASSISTANT_CARD_PLAYED,
    INFO_ACTION_PHASE,
    MOVE_STUDENT_FROM_ENTRANCE,
    NUMBER_STEPS_MOTHER_NATURE,
    CHANGE_INFLUENCE_ISLAND,
    TOWER_ON_ISLAND,
    TOWER_ON_SCHOOLBOARD,
    MERGE_ISLANDS,
    TIE,
}
