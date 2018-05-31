/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.h_da.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
/**
 * A category containing questions
 * @author Michal
 */
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    int categoryId;
    
    @Column(name = "category", unique = true)
    /** the name of the category */
    private String categoryStr;
    
    @OneToMany(mappedBy = "category", targetEntity = Question.class)
    /** list of questions belonging to this category */
    private final List<Question> questions;
    
    /**
     * constructor.
     * @param category name of category as string
     */
    public Category(final String category) {
        questions = new ArrayList<Question>();
        this.categoryStr = category;
    }
    
    /** empty default constructor as is necessary with JPA / Oracle. */
    public Category() {
        questions = new ArrayList<Question>();
    }
    
    /** set method for the category string.
     * @param category name of this Category as String.
     */
    public void setCategory(final String category) {
        this.categoryStr = category;
    }
    
    /**
     * get method for the name of the category.
     * @return String representing the name of the category.
     */
    public String getCategory() {
        return categoryStr;
    }
    
    /**
     * add a question to the list of questions belonging to this category.
     * @param question Question to be added to this Category.
     */
    public void addQuestion(final Question question) {
        questions.add(question);
    }
    
    /**
     * get method for the list of questions belonging to this category.
     * @return ArrayList with all the Questions belonging to this Category.
     */
    public List<Question> getQuestions() {
        return questions;
    }
    
    /**
     * get method for the category id
     * @return the id of the category
     */
    public int getId() {
        return categoryId;
    }
    
    /**
     * Get a list containing <code>amount</code> unique questions from this category.
     * @param amount number of questions.
     * @return a list of unique questions from this category.
     * Returns at most questions.size() unique questions.
     */
    public List<Question> getNQuestions(int amount) {
        
        if(questions.size() < amount) return questions;
        
        Random r = new Random();
        ArrayList<Question> nQuestions = new ArrayList<Question>();
        while(nQuestions.size() < amount) {
            Question quest = questions.get(r.nextInt(questions.size()));
            if(!nQuestions.contains(quest)) nQuestions.add(quest);
        }
        return nQuestions;
    }
    
    @Override
    public boolean equals(final Object object) {
        return object instanceof Category && this.getCategory().equals( ( ( Category ) object ).getCategory() );
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.categoryId;
        hash = 67 * hash + Objects.hashCode(this.categoryStr);
        return hash;
    }

    
}
