package it.polimi.ingsw.triton.launcher.utils.obs;

public interface Observer<T> {
    public void update(T message);
}
