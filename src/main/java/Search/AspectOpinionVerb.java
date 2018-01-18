/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

/**
 *
 * @author user1
 */
public class AspectOpinionVerb {
     private String Aspect,Opinion,Verb;
     private int Sentenceid;
public int getSentenceid(){
        return Sentenceid;
    }
    public void setSentenceid(int Sentenceid){
        this.Sentenceid=Sentenceid;
    }
    public String getAspect(){
        return Aspect;
    }
    public void setAspect(String Aspect){
        this.Aspect=Aspect;
    }
    public String getOpinion(){
        return Opinion;
    }
    public void setOpinion(String Opinion){
        this.Opinion=Opinion;
    }
    public String getVerb(){
        return Verb;
    }
    public void setVerb(String Verb){
        this.Verb=Verb;
    }
}
