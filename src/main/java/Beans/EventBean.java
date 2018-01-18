/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author user1
 */
public class EventBean {
    private String EventName,EventEndTime,EventStartTime,EventLatitude,EventLongitude;
    private Date EndTime,StartTime;
 public String getEventName(){
    return EventName;
 }   
 public void setEventName(String EventName){
     this.EventName=EventName;
 }
  public String getEventEndTime(){
    return EventEndTime;
 }   
 public void setEventEndTime(String EventEndTime){
     this.EventEndTime=EventEndTime;
 }
   public String getEventStartTime(){
    return EventStartTime;
 }   
 public void setEventStartTime(String EventStartTime){
     this.EventStartTime=EventStartTime;
 }
    public String getEventLatitude(){
    return EventLatitude;
 }   
 public void setEventLatitude(String EventLatitude){
     this.EventLatitude=EventLatitude;
 }
   public String getEventLongitude(){
    return EventLongitude;
 }   
 public void setEventLongitude(String EventLongitude){
     this.EventLongitude=EventLongitude;
 }
}
