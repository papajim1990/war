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
public class AspectPerHotelBean {
    private String Aspect;
        int count;
         private String polarity;
         private double confidence;
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
    public int getCountAspect(){
        return count;
    }
    public void setCountAspect(int count){
        this.count=count;
    }
}
