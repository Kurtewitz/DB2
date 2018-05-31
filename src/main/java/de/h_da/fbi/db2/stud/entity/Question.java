/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.h_da.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
/**
 * Class representing a Question, belonging to a Category and having 4 Answers
 * @author Michal
 */
public class Question {
    
    @Id
    @Column(name = "id")
    /** the id of the question*/
    private int questionId;
    
    @Column(name = "question")
    /** the actual question as String*/
    private String questionStr;
    
    @OneToMany(mappedBy = "question", targetEntity = Answer.class)
    /** an array holding the 4 answers*/
    private final List<Answer> answers;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    /** the category this question belongs to */
    private Category category;
    
    /** empty default constructor as is necessary with JPA / Oracle. */
    public Question() {
        answers = new ArrayList();
    }
    
    /**
     * Constructor.
     * @param questionId the id of the Question.
     * @param question the Question string.
     * @param category the Category this Question belongs to.
     */
    public Question(final int questionId, final String question, final Category category) {
        this.questionId = questionId;
        this.questionStr = question;
        this.category = category;
        answers = new ArrayList<Answer>();
    }
    
    /** set method for the id.
     * @param newId Question ID.
     */
    public void setId(final int newId) {
        this.questionId = newId;
    }
    
    /** set method for the question string.
     * @param question Question as String.
     */
    public void setQuestion(final String question) {
        this.questionStr = question;
    }
    
    /** set method for the Category this Question belongs to.
     * @param category set the Category this Question belongs to.
     */
    public void setCategory(final Category category) {
        this.category = category;
    }
    
    /** get method for the Category this Question belongs to.
     * @return Category this Question belongs to.
     */
    public Category getCategory() {
        return category;
    }
    
    /**
     * add a new Answer to the array of answers.
     * @param answer Answer to be added to this Question.
     */
    public void addAnswer(final Answer answer) {
        answers.add(answer);
    }
    
    /**
     * get method for the question ID.
     * @return Question ID Integer.
     */
    public int getId() {
        return questionId;
    }
    
    /**
     * get method for the question string.
     * @return Question String.
     */
    public String getQuestion() {
        return questionStr;
    }
    
    /**
     * get method for the array of answers.
     * @return ArrayList holding the Answers to this Question.
     */
    public List<Answer> getAnswers() {
        return answers;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (object instanceof Question) {
            return this.getId() == ((Question) object).getId();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.questionId;
        hash = 59 * hash + Objects.hashCode(this.questionStr);
        return hash;
    }

}
