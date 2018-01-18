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
public class OpinionPairBean {
    String adjective,Noun;
    int idHotel,idComment ;
    int PolaritySentiment;
     public String getOpinionPairAdj(){
        return adjective;
    }
    public void setHotelId(String adjective){
        this.adjective=adjective;
    }
}
