/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.sql.Date;

/**
 *
 * @author user1
 */
public class HotelBean {
    private String HotelName,HotelAdress,HotelCountry,HotelCity,HotelTk,HotelScore,HotelScoreString,HotelUrl,Area;
    private Date date;
    private int HotelId;
    public int getHotelId(){
        return HotelId;
    }
    public void setHotelId(int HotelId){
        this.HotelId=HotelId;
    }
    public String getHoteName(){
        return HotelName;
    }
    public void setHotelName(String HotelName){
        this.HotelName=HotelName;
    }
    public String getArea(){
        return Area;
    }
    public void setArea(String Area){
        this.Area=Area;
    }
        public String getHotelAdress(){
        return HotelAdress;
    }
    public void setHotelAdress(String HotelAdress){
        this.HotelAdress=HotelAdress;
    }
        public String getHotelCountry(){
        return HotelCountry;
    }
           public void setHotelUrl(String HotelUrl){
        this.HotelUrl=HotelUrl;
    }
        public String getHotelUrl(){
        return HotelUrl;
    }
    public void setHotelCountry(String HotelCountry){
        this.HotelCountry=HotelCountry;
    }
            public String getHotelCity(){
        return HotelCity;
    }
    public void setHotelCity(String HotelCity){
        this.HotelCity=HotelCity;
    }        public String getHotelTk(){
        return HotelTk;
    }
    public void setHotelTk(String HotelTk){
        this.HotelTk=HotelTk;
    }        public String getHotelScore(){
        return HotelScore;
    }
    public void setHotelHotelScore(String HotelScore){
        this.HotelScore=HotelScore;
    }     
      public String getHotelHotelScore()
      {
        return HotelScore;
    }  
    public String getHotelScoreString(){
        return HotelScoreString;
    }
    public void setHotelScoreString(String HotelScoreString){
        this.HotelScoreString=HotelScoreString;
    }
    public Date getDate(){
        return date;
    }public void setDate(Date date){
        this.date=date;
    }
}
