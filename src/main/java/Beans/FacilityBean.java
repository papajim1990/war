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
public class FacilityBean {
    
    private String Header,facilityitem;
    private int idHotel;

    public int getFacilityBeanid(){
        return idHotel;
    }
    public void setFacilityBeanid(int idHotel){
        this.idHotel=idHotel;
    }
    public String getFacilityBeanHeader(){
        return Header;
    }
    public void setFacilityBeanHeader(String Header){
        this.Header=Header;
    }
        public String getFacilityBeanItem(){
        return facilityitem;
    }
    public void setFacilityBeanItem(String facilityitem){
        this.facilityitem=facilityitem;
    }
   

}
