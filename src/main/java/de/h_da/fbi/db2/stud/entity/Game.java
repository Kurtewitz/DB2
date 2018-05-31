/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.h_da.fbi.db2.stud.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
/**
 *
 * @author Michal
 */
public class Game {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int gameId;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "player")
    private Player gamePlayer;
    
    @Column(name = "game_start")
    private Instant gameStart;
    
    @Column(name = "game_finish")
    private Instant gameFinish;
    
    @Column(name = "correct_answers")
    private int correctAnswers;
    
    @ManyToMany
    private List<Category> gameCategories;
    
    @ManyToMany
    private List<Question> gameQuestions;
    
    @ManyToMany
    private List<Answer> gameAnswers;
    
    
    public Game() {
        gameCategories = new ArrayList<Category>();
        gameQuestions = new ArrayList<Question>();
        gameAnswers = new ArrayList<Answer>();
        correctAnswers = 0;
    }
    
    public Game(Player player, List<Category> categories, List<Question> questions, LocalDateTime date, Instant start) {
        this();
        gamePlayer = player;
        for(Category cat : categories) {
            gameCategories.add(cat);
        }
        for(Question quest : questions) {
            gameQuestions.add(quest);
        }
        gameStart = start;
    }
    
    public int getId() {
        return gameId;
    }
    
    public Player getPlayer() {
        return gamePlayer;
    }
    
    public void setPlayer(Player player) {
        this.gamePlayer = player;
    }

    public void setStart(Instant start) {
        this.gameStart = start;
    }
    
    public Instant getStart() {
        return gameStart;
    }
    
    public void setFinish(Instant finish) {
        this.gameFinish = finish;
    }
    
    public Instant getFinish() {
        return gameFinish;
    }
    
    public void increaseCorrect() {
        correctAnswers += 1;
    }
    
    public int getCorrectAnswers() {
        return correctAnswers;
    }
    
    public void addCategory(final Category category) {
        gameCategories.add(category);
    }
    
    public List<Category> getCategories() {
        return gameCategories;
    }
    
    public void addQuestion(final Question question) {
        gameQuestions.add(question);
    }
    
    public List<Question> getQuestions() {
        return gameQuestions;
    }
    
    public void addAnswer(final Answer answer) {
        gameAnswers.add(answer);
    }
    
    public List<Answer> getAnswers() {
        return gameAnswers;
    }
    
    
}
