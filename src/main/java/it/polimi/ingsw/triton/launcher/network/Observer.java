package it.polimi.ingsw.triton.launcher.network;

import it.polimi.ingsw.triton.launcher.network.message.Message;

public interface Observer<T> {
    public void update(T message);
}
