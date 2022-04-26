package it.polimi.ingsw.triton.launcher.view;

import it.polimi.ingsw.triton.launcher.network.Observable;
import it.polimi.ingsw.triton.launcher.network.Observer;
import it.polimi.ingsw.triton.launcher.network.message.Message;

public interface ClientView extends View, Observer<Object> {
}
