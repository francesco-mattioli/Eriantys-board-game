package it.polimi.ingsw.triton.launcher.client.view;

import it.polimi.ingsw.triton.launcher.utils.View;

import java.util.ArrayList;

public interface ClientView extends View {
    // Ask methods
    public void askUsername();

    // Show methods
    public void showGenericMessage(String genericMessage);
    public void showLobbyMessage(ArrayList<String> onlineNicknames, int maxNumberPlayers );
}
