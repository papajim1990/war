/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GoogleApi;

import Beans.HotelLocBean;
import Beans.PlaceNearBean;
import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.RankBy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user1
 */
//AIzaSyBiqqXvRCz2TYwNLf5Sb9nIvLlPQDE8Ww8


public class PlacesApi {
    public List<PlaceNearBean> PlacesNaer(HotelLocBean Hotel,PlaceType type) throws ApiException, InterruptedException, IOException {
  GeoApiContext context = new GeoApiContext.Builder()
    .apiKey("AIzaSyD_UUguNqW2DkyHAXxZjykXO2k6OZ6ajhE")
    .build();
  PlaceType[] values = PlaceType.values();
  
  LatLng location = new LatLng (Hotel.getHoteLat(),Hotel.getHoteLong());
PlacesApi places =new PlacesApi();
NearbySearchRequest pl =new NearbySearchRequest (context);
 List<PlaceNearBean> Places = new ArrayList<>();
   pl.location(location);
   pl.type(type);
   pl.rankby(RankBy.DISTANCE);
        PlacesSearchResult[] results = pl.await().results;
        for(PlacesSearchResult re :results){
            PlaceNearBean Place= new PlaceNearBean();
            System.out.println(re.name +"-"+re.rating+"-"+re.vicinity);
            Place.setNamePlace(re.name);
            Place.setRating(re.rating);
            Place.setRAddress(re.vicinity);
            Places.add(Place);
        }
        return Places;
        
    }
}
