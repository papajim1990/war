/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Beans.EventBean;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author user1
 */
public class EventHandler {

    public List<EventBean> GetEventsHandler(String text) {
        List<EventBean> BeansList =new ArrayList<>();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(URLDecoder.decode(text, "UTF-8"));
            
            JSONArray pair = jsonObject.getJSONArray("data");
            for (int i = 0; i < pair.length(); i++) {
                EventBean bean = new EventBean();
                JSONObject jsonObject1 = pair.getJSONObject(i);
                for (Object key : jsonObject1.keySet()) {
                    if (key.toString().equalsIgnoreCase("name")) {
                        String nameEvent = (String) jsonObject1.get(key.toString());
                        bean.setEventName(nameEvent);
                    }
                    if (key.toString().equalsIgnoreCase("start_time")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
                        LocalDateTime dateTime = LocalDateTime.parse((String) jsonObject1.get(key.toString()), formatter);
                        bean.setEventStartTime(dateTime.getYear() + "-" + dateTime.getMonthValue()+ "-" + dateTime.getDayOfMonth());
                        //System.out.println(key + "=" + dateTime.getYear() + "-" + dateTime.getMonthValue() + "-" + dateTime.getDayOfMonth());
                    }
                    if (key.toString().equalsIgnoreCase("end_time")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
                        LocalDateTime dateTime = LocalDateTime.parse((String) jsonObject1.get(key.toString()), formatter);
                        bean.setEventEndTime(dateTime.getYear() + "-" + dateTime.getMonthValue() + "-" + dateTime.getDayOfMonth());
                       // System.out.println(key + "=" + dateTime.getYear() + "-" + dateTime.getMonthValue() + "-" + dateTime.getDayOfMonth());
                    }
                    if (key.toString().equalsIgnoreCase("place")) {
                        JSONObject get = jsonObject1.getJSONObject("place");
                        for (Object key1 : get.keySet()) {
                            if (key1.toString().equalsIgnoreCase("location")) {
                                JSONObject jsonObject2 = get.getJSONObject("location");
                                for (Object key3 : jsonObject2.keySet()) {
                                    if (key3.toString().equalsIgnoreCase("latitude")) {
                                        bean.setEventLatitude(jsonObject2.get("latitude").toString());
                                        bean.setEventLongitude(jsonObject2.get("longitude").toString());
                                       // System.out.println("lati:" + jsonObject2.get("latitude") + "  longitude:" + jsonObject2.get("longitude"));
                                    }

                                }
                            }
                        }
                    }

                }
                BeansList.add(bean);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EventHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return BeansList;
    }
}
