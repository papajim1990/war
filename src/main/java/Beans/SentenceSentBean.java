/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author user1
 */
public class SentenceSentBean {

    private String Sentence;
    private int idSentence, idComment,idHotel;
    private int Sentiment;
    
    public int getSentenceid() {
        return idSentence;
    }

    public void setSentenceid(int idSentence) {
        this.idSentence = idSentence;
    }

    public void setCommentid(int idComment) {
        this.idComment = idComment;
    }
  public int getCommentId() {
        return idComment;
    }
    public int getHotelId() {
        return idHotel;
    }

    public void setHotelId(int idHotel) {
        this.idHotel = idHotel;
    }

    public int getSentiment() {
        return Sentiment;
    }

    public void setSentiment(int Sentiment) {
        this.Sentiment = Sentiment;
    }

    public String getSentenceText() {
        return Sentence;
    }

    public void setSentenceText(String Sentence) {
        this.Sentence = Sentence;
    }


}
