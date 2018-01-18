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
public class SentenceBean {

    private String SentenceText;
    private Set<String> TagsSen;
    private int idSentence, idComment;
    private int PosOrNeg;
    private Double Prob;
    private LinkedHashMap mapi;
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
    public int getPosOrNegSen() {
        return PosOrNeg;
    }

    public void setPosOrNeg(int PosOrNeg) {
        this.PosOrNeg = PosOrNeg;
    }

    public Double getProbSen() {
        return Prob;
    }

    public void setProbSen(Double Prob) {
        this.Prob = Prob;
    }

    public String getSentenceText() {
        return SentenceText;
    }

    public void setSentenceText(String SentenceText) {
        this.SentenceText = SentenceText;
    }

    public Set<String> getSentenceTags() {
        return TagsSen;
    }

    public void setSentenceTags(Set<String> TagsSen) {
        this.TagsSen = TagsSen;
    }
    public LinkedHashMap<String,String> getSentenceTagsPos() {
        return mapi;
    }

    public void setSentenceTagsPos(LinkedHashMap<String,String> mapi) {
        this.mapi = mapi;
    }
}
