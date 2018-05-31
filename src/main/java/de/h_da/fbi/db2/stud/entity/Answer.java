/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.h_da.fbi.db2.stud.entity;

import de.h_da.fbi.db2.persistence.AnswerPK;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(AnswerPK.class)
/**
 * Class representing an answer.
 * @author Michal
 */
public class Answer {
    
    @Id
    @Column(name = "answer")
    /** the actual answer as string */
    private String answerStr;
    
    @Column(name = "nr")
    /** the nr of the answer (valid values 0...3) */
    private int nr;
    
    @Column(name = "is_correct")
    /** is this answer correct */
    private boolean correct;
    
    @ManyToOne
    @Id
    @JoinColumn(referencedColumnName = "id")
    /** the question this answer belongs to */
    private Question question;
    
    /**
     * constructor. By Default an answer is INcorrect.
     * Set one of them to correct while creating the question.
     * @param number the nr of the answer (0..3)
     * @param answer the answer as string
     * @param question the question this answer belongs to
     */
    public Answer(final int number, final String answer, final Question question) {
        correct = false;
        this.nr = nr;
        this.answerStr = answer;
        this.question = question;
    }
    
    /** empty default constructor as is necessary with JPA / Oracle. */
    public Answer() {
        correct = false;
    }
    
    /** set method for the answer number (0-3).
     * @param newNumber Nr of this Answer (0-3).
     */
    public void setNr(final int newNumber) {
        this.nr = newNumber;
    }
    
    /** set method for the answer string.
     * @param answer Answer as String.
     */
    public void setAnswer(final String answer) {
        this.answerStr = answer;
    }
    
    /**
     * set this answer as being correct or not.
     * Since all Answers are wrong by default, use this method to set
     * one of them as correct.
     * @param correct Boolean, is this the correct answer?
     */
    public void setCorrect(final boolean correct) {
        this.correct = correct;
    }
    
    /** set the Question this answer belongs to.
     * @param question Question this answer belongs to.
     */
    public void setQuestion(final Question question) {
        this.question = question;
    }
    
    /**
     * get method for the question nr.
     * @return Nr of Answer (0-3).
     */
    public int getNr() {
        return nr;
    }
    
    /**
     * get method for the answer string.
     * @return Answer as String.
     */
    public String getAnswer() {
        return answerStr;
    }
    
    /**
     * get method for the isCorrect boolean.
     * @return <code>true</code> if this answer is correct,
     * <code>false</code> if this answer is incorrect.
     */
    public boolean isCorrect() {
        return correct;
    }
    
    /** get method for the question this answer belongs to.
     * @return Question this Answer belongs to.
     */
    public Question getQuestion() {
        return question;
    }

}