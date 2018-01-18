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
public class ChartSentenceAspectBean {
     private String Aspect, ReviewerType,VacType,DateReview;
         private int Sentenceid,Aspectid;
         private String polarity;
         private double confidence;
    private String Country;
         
         public int getAspectid(){
    return Aspectid;
}
public void setAspectid(int Aspectid){
        this.Aspectid=Aspectid;
    }
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
    public String getpolarity(){
        return polarity;
    }
    public void setpolarity(String polarity){
        this.polarity=polarity;
    }
    public double getconfidence(){
        return confidence;
    }
    public void setconfidence(double confidence){
        this.confidence=confidence;
    }
    public String getCountry(){
        return Country;
    }
    public void setCountry(String Country){
        this.Country=Country;
    }
    public String getReviewerType(){
         
        return ReviewerType;
    }
    public void setReviewerType(String ReviewerType){
        this.ReviewerType=ReviewerType;
    }
    public String getVacType(){
         
        return VacType;
    }
    public void setVacType(String VacType){
        this.VacType=VacType;
    }
 
    public String getDateReview(){
         
        return DateReview;
    }
    public void setDateReview(String DateReview){
        this.DateReview=DateReview;
    }
}
