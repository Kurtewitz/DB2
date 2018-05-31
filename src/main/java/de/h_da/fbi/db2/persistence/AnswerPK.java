/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.h_da.fbi.db2.persistence;

import java.io.Serializable;

/**
 * class for handling composite primary key on table Answer.
 * 
 * @author Michal
 */
public class AnswerPK implements Serializable {
    private int question;
    private String answerStr;
    
    public AnswerPK() {}
    
    public AnswerPK(final int question, final String answer) {
        this.question = question;
        this.answerStr = answer;
    }
    
    public void setQuestion(final int question) {
        this.question = question;
    }
    
    public int getQuestion() {
        return question;
    }
    
    public void setAnswerStr(final String answerStr) {
        this.answerStr = answerStr;
    }
    
    public String getAnswerStr() {
        return answerStr;
    }
    
    public boolean equals(final Object object) {
        if (object instanceof AnswerPK) {
            AnswerPK pk = (AnswerPK)object;
            return answerStr.equals(pk.answerStr) && question == pk.question;
        } else {
            return false;
        }
    }
    
    public int hashCode() {
        return (int)(answerStr.hashCode() + question);
    }

}
