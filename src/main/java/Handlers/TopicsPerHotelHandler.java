/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Beans.AspectPerHotelBean;
import Beans.CommentBean;
import Beans.HotelBean;
import DAO.DaoHotels;
import DAO.DaoSentence;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author user1
 */
public class TopicsPerHotelHandler {

    public JSONObject bringTopics(HttpServletRequest request, DaoHotels daohotel, DaoSentence daoSen) {
        JSONObject json = new JSONObject();
        try {
            String url;

            url = URLDecoder.decode(request.getParameter("urlhotel"), "UTF-8");

            String endurl = url.split("\\?")[0];
            System.out.println(endurl);
            HotelBean Hotel = daohotel.getAllHotelsbyUrl(endurl.trim());

            if (Hotel.getHoteName() != null) {
                ObjectMapper mapper = new ObjectMapper();
                JSONArray j1 = new JSONArray();
                List<AspectPerHotelBean> allAspects = daoSen.getAllAspectsForHotel(Hotel.getHotelId());
                for (AspectPerHotelBean aspect : allAspects) {
                    String JsonInfo = mapper.writeValueAsString(aspect);
                    JSONObject jsonnn = new JSONObject(JsonInfo);
                    j1.put(jsonnn);
                }
                json.put("AspectsHotel", j1);
            }

        } catch (UnsupportedEncodingException | JsonProcessingException ex) {
            Logger.getLogger(TopicsPerHotelHandler.class.getName()).log(Level.SEVERE, null, ex);

        }
        return json;
    }
}
