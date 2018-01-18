/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Beans.CommentBean;
import Beans.HotelBean;
import DAO.DaoHotels;
import DAO.DaoSentence;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author user1
 */
public class PreparaeDocument {

    public Document preparedoc(Document doc, DaoHotels daohotel, DaoSentence daoSen, HttpServletRequest request) {
        
        if (!doc.getElementsByClass("review_list_nav_wrapper").isEmpty()) {
            Elements formCommentsPage = doc.getElementsByClass("review_list_nav_wrapper").select("form");
            Element form = formCommentsPage.first();
            String urlaki = doc.getElementsByClass("standalone_reviews_hotel_button").select("a").attr("href").split("\\?")[0];
            form.attr("action", "UrlContoller");
            form.append("<input type='hidden' name='form-nav-reviews' value='ok'></input>");
            form.append("<input type='hidden' name='hotelsurl' value='" + urlaki + "'></input>");
        }
        //reviews page
        if (doc.getElementById("b2reviews_hotelPage") != null) {
            String LinkHotel = doc.getElementsByClass("standalone_reviews_hotel_button").first().select("a").attr("href");
            // System.out.println(":::::::::"+LinkHotel.split("\\?")[0]);
            HotelBean allHotelsbyUrl = daohotel.getAllHotelsbyUrl(LinkHotel.split("\\?")[0].replaceAll("https://booking,com/", ""));
            if (allHotelsbyUrl!=null) {
                //System.out.println("Hotel Url Found:::::::::"+allHotelsbyUrl.getHotelUrl());
            }
        }
        
        for (Element el : doc.select("[data-url]")) {
            try {
                if (!el.attr("data-url").contains("https://booking.com")) {
                    el.attr("data-url", el.attr("data-url").replaceAll("https://booking.com", ""));
                }
                String url = URLEncoder.encode(el.attr("data-url"), "UTF-8");
                el.attr("data-url", "UrlContoller?q=" + url + "&parameterUrl=link"+"&unchecked_filter=out_of_stock");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(PreparaeDocument.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Element el : doc.select("li[data-url]")) {
            try {
                if (el.attr("data-url").contains("https://booking.com")) {
                    el.attr("data-url", el.attr("data-url").replaceAll("https://booking.com", ""));
                }
                String url = URLEncoder.encode(el.attr("data-url"), "UTF-8");
                el.attr("data-url", "UrlContoller?q=" + (el.attr("data-url") + "&parameterUrl=link"+"&unchecked_filter=out_of_stock"));;
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(PreparaeDocument.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        for (Element el : doc.select("[data-sr-url]")) {
            try {
                if (el.attr("data-sr-url").contains("https://booking.com")) {
                    el.attr("data-sr-url", el.attr("data-sr-url").replaceAll("https://booking.com", "")+"&unchecked_filter=out_of_stock");
                }
                String url = URLEncoder.encode(el.attr("data-sr-url"), "UTF-8");
                el.attr("data-sr-url", "UrlContoller?q=" + url + "&parameterUrl=link"+"&unchecked_filter=out_of_stock");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(PreparaeDocument.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Element link : doc.select("[src]")) {

            if (!link.attr("src").contains("https")) {
                link.attr("src", "https://booking.com" + link.attr("src"));
            }
        }
        for (Element link : doc.select("a[href]")) {
            if (link.attr("href").contains("https://booking.com")) {
                link.attr("href", link.attr("href").replaceAll("https://booking.com", "")+"&unchecked_filter=out_of_stock");
                //System.out.println(link.attr("href"));
            }
            if (!(link.attr("href").startsWith("#") || link.attr("href").startsWith("javascript"))) {
                try {
                    String url = URLEncoder.encode(link.attr("href"), "UTF-8");
                    link.attr("href", "UrlContoller?q=" + url + "&parameterUrl=link"+"&unchecked_filter=out_of_stock");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(PreparaeDocument.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        for (Element link : doc.select("[data-link]")) {
            try {
                if (link.attr("data-link").contains("https://booking.com")) {
                    link.attr("data-link", link.attr("data-link").replaceAll("https://booking.com", "")+"&unchecked_filter=out_of_stock");
                }
                String url = URLEncoder.encode(link.attr("data-link"), "UTF-8");
                link.attr("data-link", "UrlContoller?q=" + url + "&parameterUrl=link"+"&unchecked_filter=out_of_stock");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(PreparaeDocument.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (doc.getElementById("searchresultsTmpl")!=null) {
            
            List<String> allAspects = daoSen.getDistinctSentenceAspect();
            doc.getElementById("sort_by").append("<ul class='sort_option_list_down '><li><button id='opensortoptionbutton' class='sort_option_avoid '><span>Personalize your search</span><i style='margin-left:4px;' id='rotation-bouli' class=\"fas fa-cogs\"></i>\n" +"</button></li></ul>");
            doc.getElementById("sort_by").append("<div style='display:none' id='sort-option-div'><div class='row'><div class='col-sm-12'><h4 class='text-center'>Select the criteria to sort your results .</h4><p class='text-center'>Based on users reviews .</p></div></div></div>");
            doc.getElementById("sort-option-div").append("<div class='row'><div class=\" col-md-12\"><select id='select-sort-option-container' multiple class='selectpicker form-control form-control-lg '></select></div></div>");
            for (String Aspect : allAspects) {
                doc.getElementById("select-sort-option-container").append("<option value='" + Aspect + "'>" + Aspect + "</option>");

            }
            doc.getElementById("hotellist_inner").prepend("<div class='button-events'><button id='events-neraby'>See Events in Area</button></div><div id='Fb-events-container'></div>");
        }

        if (!doc.getElementsByTag("iframe").isEmpty()) {
            for (Element iframe : doc.getElementsByTag("iframe")) {
                iframe.attr("src", "https://booking.com" + iframe.attr("src")+"&unchecked_filter=out_of_stock");
            }
        }
        if(!doc.getElementsByClass("review_list_block one_col").isEmpty()){
            if (request.getQueryString()!= null){
                try {
                    Element document=doc.getElementsByClass("review_list_block one_col").get(0);
                    String url = URLDecoder.decode(request.getRequestURI(),"UTF-8");
                    String endurl=url.split("\\?")[0];
                    System.out.println(url);
                    HotelBean Hotel=daohotel.getAllHotelsbyUrl(endurl);
                    if(Hotel!=null){
                        List<CommentBean> allComments = daohotel.getAllCommentsByHotelId(Hotel.getHotelId());
                        for(CommentBean comment:allComments){
                          document.append(comment.getCommentBodyNeg());
                        }
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(PreparaeDocument.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (!doc.getElementsByClass("sr_item").isEmpty()) {
            if (!doc.getElementsByClass("sr_item").select("a.hotel_name_link").isEmpty()) {

                BringAspectsByHotelHandler Handler = new BringAspectsByHotelHandler();
                doc=Handler.bringAspectsForEachHotel(daohotel, daoSen, doc,request);

            }
        }
      if(doc.getElementById("b2hotelPage")!=null){
          
              HotelPageInfoFromReviewsHandler Handler =new HotelPageInfoFromReviewsHandler();
              doc=Handler.bringHotelAspectsStats(daohotel, daoSen, doc,request);
          
      }
    return doc;   
    }

}
