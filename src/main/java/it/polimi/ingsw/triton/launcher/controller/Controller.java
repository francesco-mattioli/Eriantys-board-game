package it.polimi.ingsw.triton.launcher.controller;

import it.polimi.ingsw.triton.launcher.model.Game;

/**
 * Server has a reference to Controller. This reference is passed to
 * ServeOneClient, so the latter can send new data to the Controller.
 *
  */

public class Controller {
    /*private Game game;


    public void addPlayer(String username){
        if(game.isUsernameChosen(username)){
            //return an error message
        }
        else
            game.addPlayer(username);
    }
     */


    // ---------------------------------------------------------
    // TO DELETE: some examples to check that the server works
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
