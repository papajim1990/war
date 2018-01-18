/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Beans.CommentBean;
import Beans.HotelBean;
import Beans.PagerCommentsRangeBean;
import Beans.SentenceSentBean;
import Beans.SortOptionCountryOfUserBean;
import Beans.SortOptionTypeOfUserBean;
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
public class SortOptionsHandler {

    public JSONObject bringSortOptions(HttpServletRequest request, DaoHotels daohotel, DaoSentence daoSen) {
        JSONObject json = new JSONObject();
        try {
            String url = URLDecoder.decode(request.getParameter("urlhotel"), "UTF-8");
            String endurl = url.split("\\?")[0];
            System.out.println(endurl);
            HotelBean Hotel = daohotel.getAllHotelsbyUrl(endurl.trim());

            if (Hotel.getHoteName() != null) {
                JSONArray j1 = new JSONArray();
                JSONArray j2 = new JSONArray();
                try {

                    ObjectMapper mapper = new ObjectMapper();

                    List<CommentBean> allComments = daohotel.getAllCommentsByHotelId(Hotel.getHotelId());
                    int numberofcomments = allComments.size();

                    
                    List<SortOptionCountryOfUserBean> allSortCountryOfUser = daohotel.getAllSortCountryOfUser(Hotel.getHotelId());
                    List<SortOptionTypeOfUserBean> allSortUserTypeOptions = daohotel.getAllSortUserTypeOptions(Hotel.getHotelId());
                    List<PagerCommentsRangeBean> rangeofCommentIds = daohotel.getRangeofCommentIds(Hotel.getHotelId());
                    for(SortOptionCountryOfUserBean sortcountry : allSortCountryOfUser){
                        
                        String JsonInfo = mapper.writeValueAsString(sortcountry);
                        JSONObject jsonnn = new JSONObject(JsonInfo);
                        j1.put(jsonnn);
                    }
                    for(SortOptionTypeOfUserBean sorttype:allSortUserTypeOptions){
                      
                        String JsonInfo = mapper.writeValueAsString(sorttype);
                        JSONObject jsonnn = new JSONObject(JsonInfo);
                        j2.put(jsonnn);
                    }
                    String JsonInfo = mapper.writeValueAsString(rangeofCommentIds.get(0));
                        JSONObject jsonnn = new JSONObject(JsonInfo);
                    json.put("CountrySort", j1);
                    json.put("typeSort", j2);
                    json.put("pager", jsonnn);
                } catch (JsonProcessingException ex) {
                    Logger.getLogger(CommentsHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CommentsHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }

}
