package it.polimi.ingsw.triton.launcher.controller;

/**
 * Server has a reference to Controller. This reference is passed to
 * ServeOneClient, so the latter can send new data to the Controller.
 *
  */

public class Controller {
    // example, just to test if the server works
    private String username;


    // this is called by ServeOneClient
    public void setUsername(String username) {
        this.username = username;
    }

    public void print() {
        System.out.println("bro:" + username);
    }

}
