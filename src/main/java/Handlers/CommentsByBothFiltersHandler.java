/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Beans.CommentBean;
import Beans.HotelBean;
import Beans.SentenceAspect;
import Beans.SentenceSentBean;
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
public class CommentsByBothFiltersHandler {

    public JSONObject bringCommentsByBothFilters(HttpServletRequest request, DaoHotels daohotel, DaoSentence daoSen) {
JSONObject json = new JSONObject();
        try {
            

            String url = URLDecoder.decode(request.getParameter("urlhotel"), "UTF-8");
            String endurl = url.split("\\?")[0];
            System.out.println(endurl);
            HotelBean Hotel = daohotel.getAllHotelsbyUrl(endurl.trim());

            if (Hotel.getHoteName() != null) {

                JSONArray jaa = new JSONArray();

                ObjectMapper mapper = new ObjectMapper();

                System.out.println(Hotel.getHotelId() + "-" + Hotel.getHoteName());
                List<CommentBean> allComments = daohotel.getAllCommentsByHotelIdAndBothFilters(Hotel.getHotelId(),request.getParameter("Country"),request.getParameter("TypeOfUser"), Integer.parseInt(request.getParameter("start")), Integer.parseInt(request.getParameter("end")));
                String HotelJsonInfo = mapper.writeValueAsString(Hotel);
                int total= daohotel.getCountByHotelIdAndBothFilters(Hotel.getHotelId(),request.getParameter("Country"),request.getParameter("TypeOfUser"));
                JSONObject jsonn = new JSONObject(HotelJsonInfo);
                
                for (CommentBean comment : allComments) {
                    
                    JSONObject jsonin = new JSONObject();
                    JSONArray jaaa = new JSONArray();
                    String CommentsHotelInfo = mapper.writeValueAsString(comment);
                    JSONObject jsonnn = new JSONObject(CommentsHotelInfo);
                    System.out.println(comment.getCommentBodyPos());
                    
                    List<SentenceSentBean> allSentenceAspectByCommentId = daoSen.getAllSentenceAspectByCommentId(comment.getCommentId());
                    for (SentenceSentBean sentence : allSentenceAspectByCommentId) {
                        JSONArray jaaAspect = new JSONArray();
                        JSONObject jsoninSen = new JSONObject();
                        String CommentsSentenceInfo = mapper.writeValueAsString(sentence);
                        JSONObject jso = new JSONObject(CommentsSentenceInfo);
                        
                        List<SentenceAspect> allSentenceAspectByidSentence = daoSen.getAllSentenceAspectByidSentence(sentence.getSentenceid());
                        //System.out.println(sentence.getSentenceText());
                        for(SentenceAspect aspect:allSentenceAspectByidSentence){
                            String AspectSentenceInfo = mapper.writeValueAsString(aspect);
                            JSONObject jsoAspect = new JSONObject(AspectSentenceInfo);
                            jaaAspect.put(jsoAspect);
                            
                        }
                        jsoninSen.put("sentenceobject", jso);
                        jsoninSen.put("Aspects", jaaAspect);
                        jaaa.put(jsoninSen);
                    }
                    
                    jsonin.put("com", jsonnn);
                    jsonin.put("sentence", jaaa);
                    
                    jaa.put(jsonin);
                    
                    
                }
                
                json.put("Hotel", jsonn);
                json.put("Comments", jaa);
                json.put("total", total);
                
            }
            
           
        } catch (UnsupportedEncodingException | JsonProcessingException ex) {
            Logger.getLogger(CommentsBycountryHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
         return json;    }
    
}
