/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

import edu.stanford.nlp.ling.IndexedWord;
import java.util.Comparator;


public class Relation {
    private String Head,Dep;
    private String Relation,PosHead,PosDep,TargetLemmaPos;
private int idSentence;
    private String TargetLemma;
     public int getidSentence(){
        return idSentence;
    }
    public void setidSentence(int idSentence){
        this.idSentence=idSentence;
    }
     public String getHead(){
        return Head;
    }
    public void setHead(String Head){
        this.Head=Head;
    }
    public String getDep(){
        return Dep;
    }
    public void setDep(String Dep){
        this.Dep=Dep;
    }
    public String getRelation(){
        return Relation;
    }
    public void setRelation(String Relation){
        this.Relation=Relation;
    }
    public String getPosHead(){
        return PosHead;
    }
    public void setPosHead(String PosHead){
        this.PosHead=PosHead;
    }
    public String getPosDep(){
        return PosDep;
    }
    public void setPosDep(String PosDep){
        this.PosDep=PosDep;
    }
    public String getTargetLemma(){
        return TargetLemma;
    }
    public void setTargetLemma(String TargetLemma){
        this.TargetLemma=TargetLemma;
    }
     public String getTargetLemmaPos(){
        return TargetLemmaPos;
    }
    public void setTargetLemmaPos(String TargetLemmaPos){
        this.TargetLemmaPos=TargetLemmaPos;
    }
   
public boolean compareTo(Relation o){
if(this.idSentence==o.idSentence && this.Head.equalsIgnoreCase(o.Head) && this.Dep.equalsIgnoreCase(o.Dep) && this.Relation.equalsIgnoreCase(o.Relation)){
    return true;
}else{
        return false;
}
}
}
