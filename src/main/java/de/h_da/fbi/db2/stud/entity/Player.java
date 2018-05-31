/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.h_da.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
/**
 *
 * @author Michal
 */
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int playerId;
    
    @Column(name = "name", unique = true)
    private String playerName;
    
    @OneToMany(mappedBy = "gamePlayer")
    private List<Game> playerGames;
    
    public Player() {
        playerGames = new ArrayList<Game>();
    }
    
    public Player(String name) {
        this();
        this.setName(name);
    }
    
    public int getId() {
        return playerId;
    }
    
    public void setName(String name) {
        this.playerName = name;
    }
    
    public String getName() {
        return playerName;
    }
    
    public List<Game> getGames() {
        return playerGames;
    }
    
    public void addGame(Game game) {
        playerGames.add(game);
    }
    
    
}
