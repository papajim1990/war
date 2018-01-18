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
public class PlaceNearBean {
    private String NamePlace,Address;
    private float Rating;
    public String getNamePlace(){
        return NamePlace;
    }
    public void setNamePlace(String NamePlace){
        this.NamePlace=NamePlace;
    }
    public float getRating(){
        return Rating;
    }
    public void setRating(float Rating){
        this.Rating=Rating;
    }
       public String getAddress(){
        return Address;
    }
    public void setRAddress(String Address){
        this.Address=Address;
    }
}
