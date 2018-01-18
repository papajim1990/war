/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.Comparator;

/**
 *
 * @author user1
 */
public  class HotelRankBean implements Comparable<HotelRankBean> {
    private String  Aspect;
    private HotelBeanWithDiv Hotel;
    private double Score;
    public HotelRankBean() {
    
    }

    public HotelBeanWithDiv getHote(){
        return Hotel;
    }
    public void setHotel(HotelBeanWithDiv Hotel){
        this.Hotel=Hotel;
    }
    public String getHotelAspect(){
        return Aspect;
    }
    public void setHotelAspect(String Aspect){
        this.Aspect=Aspect;
    }
    public double getHoteAspectScore(){
        return Score;
    }
    public void setHotelAspectScore(double Score){
        this.Score=Score;
    }

@Override
    public int compareTo(HotelRankBean o) {
        return this.Score > o.getHoteAspectScore() ? 1 : (this.Score < o.getHoteAspectScore() ? -1 : 0);
    }

}
