/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author user1
 */
public class LemmaToken {
    private int SentenceId,id;
    private String After,Before,PosTag;
      public int getSid(){
    return id;
}
public void setSid(int id){
        this.id=id;
    }
    public int getSentenceid(){
    return SentenceId;
}
public void setSentenceid(int SentenceId){
        this.SentenceId=SentenceId;
    }
    public String getBefore(){
        return Before;
    }
    public void setBefore(String Before){
        this.Before=Before;
    }
    public String getAfter(){
        return After;
    }
    public void setAfter(String After){
        this.After=After;
    }
    public String getPosTag(){
        return PosTag;
    }
    public void setPosTag(String PosTag){
        this.PosTag=PosTag;
    }
}
