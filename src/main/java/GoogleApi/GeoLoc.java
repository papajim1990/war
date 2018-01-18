/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GoogleApi;

import Beans.HotelBean;
import Beans.HotelLocBean;
import DAO.DaoHotels;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user1
 */
public class GeoLoc {
    public void GeoLoc(HotelBean hotel,DaoHotels dao){
           try {
               HotelLocBean Loc =new HotelLocBean();
    GeoApiContext context = new GeoApiContext.Builder()
    .apiKey("AIzaSyD_UUguNqW2DkyHAXxZjykXO2k6OZ6ajhE")
    .build();
GeocodingResult[] results;
Gson gson = new GsonBuilder().setPrettyPrinting().create();


     
            results = GeocodingApi.geocode(context,
                    hotel.getArea()).await();
            if(results.length>0){
              
               double lat = results[0].geometry.location.lat;
               double lng = results[0].geometry.location.lng;
               Loc.setHotelId(hotel.getHotelId());
               Loc.setHoteLat(lat);
               Loc.setHoteLong(lng);
               dao.addHotelLoc(Loc);
               
            }
            
        } catch (ApiException ex) {
            Logger.getLogger(GeoLoc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GeoLoc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeoLoc.class.getName()).log(Level.SEVERE, null, ex);
        }
    
}
}
