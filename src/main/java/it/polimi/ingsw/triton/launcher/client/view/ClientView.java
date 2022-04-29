package it.polimi.ingsw.triton.launcher.client.view;

import it.polimi.ingsw.triton.launcher.client.model.ClientModel;
import it.polimi.ingsw.triton.launcher.utils.obs.Observer;
import it.polimi.ingsw.triton.launcher.utils.View;

import java.util.ArrayList;

public interface ClientView extends View, Observer<Object> {
    public void showGenericMessage(String genericMessage);
    public ClientModel getClientModel();
    public void askUsername();
    public void askTowerColor(boolean[] availableTowerColors);
    public void showLobbyMessage(ArrayList<String> onlineNicknames, int maxNumberPlayers );
}
