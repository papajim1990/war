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
public class HotelLocBean {
    private int HotelId;
    private double lati,longi;
     public int getHotelId(){
        return HotelId;
    }
    public void setHotelId(int HotelId){
        this.HotelId=HotelId;
    }
    public double getHoteLat(){
        return lati;
    }
    public void setHoteLat(double lati){
        this.lati=lati;
    }
    public double getHoteLong(){
        return longi;
    }
    public void setHoteLong(double longi){
        this.longi=longi;
    }
}
