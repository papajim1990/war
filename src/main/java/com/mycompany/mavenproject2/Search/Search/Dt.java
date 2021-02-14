/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.Search.Search;

import Beans.CommentBean;
import Beans.FacilityBean;
import Beans.HighlightsHotel;
import Beans.HoteOverallScoreFeautures;
import Beans.HotelBean;
import Beans.UserBean;
import DAO.DaoHotels;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author user1
 */
public class Dt {

    /**
     *
     */
    public List GetData() throws ParseException {
        DaoHotels dao = new DaoHotels();
        List<HotelBean> Hotels = new ArrayList<HotelBean>();
        List<CommentBean> Comments = new ArrayList<CommentBean>();
        List<HighlightsHotel> high = new ArrayList<HighlightsHotel>();
        List<HoteOverallScoreFeautures> feaut = new ArrayList<HoteOverallScoreFeautures>();
        List Result = new ArrayList();
        List Result1 = new ArrayList();
        try {
            java.util.Date date = new java.util.Date();
            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(date);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsed = format.parse(currentTime);
            java.sql.Date sql = new java.sql.Date(parsed.getTime());
            Document hotelListDoc;
            int offset = 0, rows = 15, countHotels = 1;
            String country = "Athens";
            String url = "https://www.booking.com/searchresults.en-gb.html?aid=304142&label=gen173nr-1FCAEoggJCAlhYSDNiBW5vcmVmaFyIAQGYAS64AQrIAQzYAQHoAQH4AQuoAgM&sid=bacdc68c9c2acd4f8273dc41658fd8c3&city=-814876&class_interval=1&dtdisc=0&group_adults=2&group_children=0&inac=0&index_postcard=0&label_click=undef&no_rooms=1&postcard=0&room1=A%2CA&sb_price_type=total&src=searchresults&src_elem=sb&ss=Athens&ss_all=0&ssb=empty&sshis=0&ssne=Athens&ssne_untouched=Athens&rows=0&offset=0";
            hotelListDoc = Jsoup.connect(url).timeout(300000).get();

            String totalStr = hotelListDoc.getElementsByClass("sr_header").select("h1").text().trim().replaceAll("[^\\d.]", "").replaceAll("[^A-Za-z0-9]", "");
            int totalHotels = Integer.parseInt(totalStr);
            System.out.println(totalHotels);
            while (countHotels < totalHotels) {
                Elements flightElms;
                flightElms = hotelListDoc.getElementsByClass("sr_item");

                Document hotelDoc;
                for (Element elm : flightElms) {
                    HotelBean hotel = new HotelBean();

                    String hotelUrl = elm.getElementsByClass("hotel_name_link").attr("href");
                    hotelDoc = Jsoup.connect("http://www.booking.com/" + hotelUrl).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").timeout(300000).get();
                    //System.out.println("total="+countHotels);

                    countHotels++;
                    String Hotelname = null;
                    String Adress = null;
                    String City = null;
                    String Sccore = null;
                    String score1 = null;
                    String Tk = null;
                    String[] adrr = null;
                    String UrlHotel = null;

                    HoteOverallScoreFeautures featur = new HoteOverallScoreFeautures();
                    Hotelname = hotelDoc.getElementById("hp_hotel_name").text();
                    hotel.setHotelName(Hotelname);
                    featur.setHotelName(Hotelname);
                    Elements elementsByClass = hotelDoc.select("div#review_list_score_container").first().getElementsByClass("review_score_name");
                    Elements elementsByClass1 = hotelDoc.select("div#review_list_score_container").first().getElementsByClass("review_score_value");
                    int counteri = 0;
                    for (Element elementio : elementsByClass) {
                        featur.setHotelName(Hotelname);
                        featur.setHoteltitle(elementio.text());
                        featur.setscore(Double.parseDouble(elementsByClass1.get(counteri).text()));

                        System.out.println(elementio.text() + "///////////" + Double.parseDouble(elementsByClass1.get(counteri).text()));
                        feaut.add(featur);
                        dao.addFeat(featur);
                        counteri++;
                    }
                    Document hotelDocer = Jsoup.connect("http://www.booking.com/" + hotelUrl + "&#tab-reviews").userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").timeout(300000).get();
                    HighlightsHotel highlight = new HighlightsHotel();
                    hotelDocer.getElementsByClass("important_facility").select("i").remove();
                    Elements elementsByClass4 = hotelDocer.getElementsByClass("important_facility");
                    for (Element el : elementsByClass4) {
                        highlight.setHotelName(Hotelname);
                        highlight.setHoteltitle(el.text());
                        high.add(highlight);
                        dao.addHigh(highlight);
                    }
                    //System.out.println(Hotelname);
                    String fulladr = hotelDoc.getElementsByClass("hp_address_subtitle").text();
                    System.out.println("Full adress:@@@" + fulladr);
                    if (fulladr != null) {
                        adrr = fulladr.split(",");
                    }
                    if (adrr != null) {
                        Adress = adrr[0];
                        City = adrr[adrr.length - 1];
                        if (adrr.length - 2 > 0) {
                            Tk = adrr[adrr.length - 2];
                        }
                        country = country;
                    }

                    hotel.setHotelAdress(Adress);
                    hotel.setHotelCity(City);
                    hotel.setHotelCountry(country);
                    hotel.setHotelTk(Tk);
                    hotel.setArea(fulladr);
                    Element score = hotelDoc.getElementById("js--hp-gallery-scorecard");
                    if (score != null) {
                        // //System.out.println("Textual Score:" + score.getElementsByClass("js--hp-scorecard-scoreword").text());
                        // //System.out.println("Arithmetic Score:" + score.getElementsByClass("rating").text());                
                        // //System.out.println("\n");         
                        Sccore = score.getElementsByClass("js--hp-scorecard-scoreword").text();
                        score1 = score.getElementsByClass("rating").text();
                    }

                    hotel.setHotelHotelScore(Sccore);
                    hotel.setHotelScoreString(score1);
                    hotel.setDate(sql);
                    hotel.setHotelUrl("http://www.booking.com/" + hotelUrl);
                    dao.updateHotel(hotel);

                    Hotels.add(hotel);

                    DaoHotels Dao = new DaoHotels();
                    int hotelid = Dao.getHotelId(hotel);
                    Document HotelsComments = Jsoup.connect("http://www.booking.com/" + hotelDoc.getElementsByClass("show_all_reviews_btn").attr("href")).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").timeout(300000).get();
                    Elements reviews = HotelsComments.getElementsByClass("review_item");
                    // //System.out.println("Comments");
                    //DataMining dtt = new DataMining();
                    for (Element r : reviews) {
                        CommentBean Comment = new CommentBean();
                        System.out.println("-----" + r.getElementsByClass("review_item_header_score_container").get(0).text() + "---" + r.getElementsByClass("review_item_header_content_container").get(0).select("span").first().text());
                        r.getElementsByClass("review_item_review_content").select("i.review_item_icon").remove();
                        //Comment.setHotelHotelid(Hotelname);
                        Comment.setCommentBodyPos(r.getElementsByClass("review_pos").text());
                        Comment.setCommentBodyNeg(r.getElementsByClass("review_neg").text());
                        Comment.setHotelReviewTitle(r.getElementsByClass("review_item_header_content_container").get(0).select("span").first().text());
                        Comment.setHotelReviewScore(Double.parseDouble(r.getElementsByClass("review_item_header_score_container").get(0).text()));

                        //System.out.println("Reviewer Country: " + r.getElementsByClass("reviewer_country").text() );
                        Comment.setCommentCountry(r.getElementsByClass("reviewer_country").text());
                        r.getElementsByClass("review_info_tag").select("span").remove();
                        Elements tags = r.getElementsByClass("review_info_tag");
                        for (Element k : tags) {
                            if (k.text().trim().equalsIgnoreCase("Bussiness trip") || k.text().trim().equalsIgnoreCase("Leisure trip")) {
                                // //System.out.println("Reviewer Type of vacations:"+ k.text().trim());
                                Comment.setCommentReviwerVacType((k.text().trim()));
                            }
                            if (k.text().trim().equalsIgnoreCase("family") || k.text().trim().equalsIgnoreCase("Group") || k.text().trim().equalsIgnoreCase("Solo traveller") || k.text().trim().equalsIgnoreCase("Couple")) {
                                Comment.setCommentReviwerType((k.text().trim()));
                                // //System.out.println("Reviewer type:"+ k.text().trim());
                            }
                        }
                        // DataMining tool = new DataMining();
                        boolean flag = false;
                        for (CommentBean test : Comments) {
                            //  if (test.getCommentBodyPos().equals(Comment.getCommentBodyPos()) && test.getHoteid().equals(hotel.getHoteName()) && test.getCommentBodyNeg().equals(Comment.getCommentBodyNeg())) {
                            flag = true;
                            System.out.println(test.getCommentBodyPos() + "-----" + hotel.getHoteName());
                            //  }
                        }
                        System.out.println(flag);
                        if (flag == false) {
                            if (Comment.getReviewScore() != null && Comment.getReviewTitle() != null) {
                                dao.updatecomment(Comment);
                            }
                            Comments.add(Comment);
                        }

                    }
                    Element link = HotelsComments.select("a#review_next_page_link").first();
                    while (link != null) {

                        HotelsComments = Jsoup.connect("http://www.booking.com/" + link.attr("href")).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").timeout(300000).get();
                        link = HotelsComments.select("a#review_next_page_link").first();
                        reviews = HotelsComments.getElementsByClass("review_item");
                        // //System.out.println("Comments");
                        for (Element r : reviews) {
                            CommentBean Comment = new CommentBean();
                            System.out.println("-----" + r.getElementsByClass("review_item_header_score_container").get(0).text() + "---" + r.getElementsByClass("review_item_header_content_container").get(0).select("span").first().text());
                            r.getElementsByClass("review_item_review_content").select("i.review_item_icon").remove();
                            // Comment.setHotelHotelid(Hotelname);
                            Comment.setCommentBodyPos(r.getElementsByClass("review_pos").text());
                            Comment.setCommentBodyNeg(r.getElementsByClass("review_neg").text());
                            Comment.setHotelReviewTitle(r.getElementsByClass("review_item_header_content_container").get(0).select("span").first().text());
                            Comment.setHotelReviewScore(Double.parseDouble(r.getElementsByClass("review_item_header_score_container").get(0).text()));
                            //System.out.println("Reviewer Country: " + r.getElementsByClass("reviewer_country").text() );

                            Comment.setCommentCountry(r.getElementsByClass("reviewer_country").text());
                            r.getElementsByClass("review_info_tag").select("span").remove();
                            Elements tags = r.getElementsByClass("review_info_tag");
                            for (Element k : tags) {
                                if (k.text().trim().equalsIgnoreCase("Bussiness trip") || k.text().trim().equalsIgnoreCase("Leisure trip")) {
                                    ////System.out.println("Reviewer Type of vacations:"+ k.text().trim());
                                    Comment.setCommentReviwerVacType((k.text().trim()));
                                }
                                if (k.text().trim().toLowerCase().contains("family") || k.text().trim().equalsIgnoreCase("Group") || k.text().trim().equalsIgnoreCase("Solo traveller") || k.text().trim().equalsIgnoreCase("Couple")) {
                                    Comment.setCommentReviwerType((k.text().trim()));
                                    ////System.out.println("Reviewer type:"+ k.text().trim());
                                }
                            }
                            // DataMining tool = new DataMining();
                            boolean flag = false;
                            for (CommentBean test : Comments) {
                                //  if (test.getCommentBodyPos().equals(Comment.getCommentBodyPos()) && test.getHoteid().equals(hotel.getHoteName()) && test.getCommentBodyNeg().equals(Comment.getCommentBodyNeg())) {
                                flag = true;
                                System.out.println(test.getCommentBodyPos() + "-----" + hotel.getHoteName());
                                //  }
                            }
                            System.out.println(flag);
                            if (flag == false) {
                                if (Comment.getReviewScore() != null && Comment.getReviewTitle() != null) {

                                    dao.updatecomment(Comment);
                                }
                                Comments.add(Comment);
                            }
                        }
                    }

                }

                url = url.replace("offset=" + offset, "offset=" + (offset + rows));
                offset += rows;
                hotelListDoc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").timeout(300000).get();
                //System.out.println("offset="+offset); 

            }

        } catch (IOException ex) {
            Logger.getLogger(Dt.class.getName()).log(Level.SEVERE, null, ex);
        }
        Result.add(Hotels);
        Result.add(Comments);
        return Result;

    }

    public List GetComments(HotelBean url, DaoHotels Dao) throws IOException {

        HotelBean Hotel = url;
        List<CommentBean> Comments = new ArrayList<CommentBean>();

        String hotelUrl = Hotel.getHotelUrl();
        Document hotelDoc = Jsoup.connect(hotelUrl).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").timeout(300000).get();
        Document HotelsComments = Jsoup.connect("https://www.booking.com" + hotelDoc.getElementsByClass("show_all_reviews_btn").attr("href")).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").timeout(300000).get();
        System.out.println(hotelUrl + hotelDoc.getElementsByClass("show_all_reviews_btn").attr("href"));

        Elements reviews = HotelsComments.getElementsByClass("review_item");
        for (Element r : reviews) {
            CommentBean Comment = new CommentBean();
            r.getElementsByClass("review_item_review_content").select("i.review_item_icon").remove();
            Comment.setHotelHotelid(Hotel.getHotelId());
            Comment.setCommentBodyPos(r.getElementsByClass("review_pos").text());
            Comment.setCommentBodyNeg(r.getElementsByClass("review_neg").text());
            System.out.println("Reviewer Country: " + r.select("span[itemprop=\"nationality\"]").text());
            Comment.setCommentCountry(r.select("span[itemprop=\"nationality\"]").text().trim());
            r.getElementsByClass("review_info_tag").select("span").remove();
            Elements tags = r.getElementsByClass("review_info_tag");
            for (Element k : tags) {
                if (k.text().trim().equalsIgnoreCase("Bussiness trip") || k.text().trim().equalsIgnoreCase("Leisure trip")) {
                    System.out.println("Reviewer Type of vacations:" + k.text().trim());
                    Comment.setCommentReviwerVacType((k.text().trim()));
                }
                if (k.text().trim().toLowerCase().contains("family") || k.text().trim().equalsIgnoreCase("Group") || k.text().trim().equalsIgnoreCase("Solo traveller") || k.text().trim().equalsIgnoreCase("Couple")) {
                    Comment.setCommentReviwerType((k.text().trim()));
                    System.out.println("Reviewer type:" + k.text().trim());
                }
                if (k.text().trim().contains("Stayed")) {
                    Comment.setDaysStayed((k.text().trim()));
                    System.out.println("Reviewer type:" + k.text().trim());
                }
            }
            System.out.println("Date:" + r.getElementsByClass("review_item_date").text());
            Comment.setDateReview(r.getElementsByClass("review_item_date").text());

            boolean flag = false;
            for (CommentBean test : Comments) {
                if (test.getCommentBodyPos().equals(Comment.getCommentBodyPos()) && test.getHoteid() == Hotel.getHotelId() && test.getCommentBodyNeg().equals(Comment.getCommentBodyNeg())) {
                    flag = true;
                    System.out.println(test.getCommentBodyPos() + "-----" + Hotel.getHoteName());
                }
            }
            System.out.println(flag);
            if (flag == false) {

                Comments.add(Comment);
            }

        }
        Element link = HotelsComments.select("a#review_next_page_link").first();
        while (link != null) {

            HotelsComments = Jsoup.connect("http://www.booking.com/" + link.attr("href")).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").timeout(300000).get();
            link = HotelsComments.select("a#review_next_page_link").first();
            reviews = HotelsComments.getElementsByClass("review_item");
            System.out.println("Comments");
            for (Element r : reviews) {
                CommentBean Comment = new CommentBean();
                r.getElementsByClass("review_item_review_content").select("i.review_item_icon").remove();
                Comment.setHotelHotelid(Hotel.getHotelId());
                Comment.setCommentBodyPos(r.getElementsByClass("review_pos").text());
                Comment.setCommentBodyNeg(r.getElementsByClass("review_neg").text());
                System.out.println("Reviewer Country: " + r.select("span[itemprop=\"nationality\"]").text());
                Comment.setCommentCountry(r.select("span[itemprop=\"nationality\"]").text().trim());
                r.getElementsByClass("review_info_tag").select("span").remove();
                Elements tags = r.getElementsByClass("review_info_tag");
                Comment.setDateReview(r.getElementsByClass("review_item_date").text());

                for (Element k : tags) {
                    if (k.text().trim().equalsIgnoreCase("Bussiness trip") || k.text().trim().equalsIgnoreCase("Leisure trip")) {
                        System.out.println("Reviewer Type of vacations:" + k.text().trim());
                        Comment.setCommentReviwerVacType((k.text().trim()));
                    }
                    if (k.text().trim().toLowerCase().contains("family") || k.text().trim().equalsIgnoreCase("Group") || k.text().trim().equalsIgnoreCase("Solo traveller") || k.text().trim().equalsIgnoreCase("Couple")) {
                        Comment.setCommentReviwerType((k.text().trim()));
                        System.out.println("Reviewer type:" + k.text().trim());
                    }
                    if (k.text().trim().contains("Stayed")) {
                        Comment.setDaysStayed((k.text().trim()));
                        System.out.println("Reviewer type:" + k.text().trim());
                    }
                }
                boolean flag = false;
                for (CommentBean test : Comments) {
                    if (test.getCommentBodyPos().equals(Comment.getCommentBodyPos()) && test.getHoteid() == Hotel.getHotelId() && test.getCommentBodyNeg().equals(Comment.getCommentBodyNeg())) {
                        flag = true;
                        System.out.println(test.getCommentBodyPos() + "-----" + Hotel.getHoteName());
                    }
                }
                System.out.println(flag);
                if (flag == false) {

                    Comments.add(Comment);
                }

            }
        }

        return Comments;

    }

    public List GetfacilitiesChecklistSection(HotelBean url, DaoHotels Dao) throws IOException {
        HotelBean Hotel = url;
        List<FacilityBean> Facilities = new ArrayList<FacilityBean>();

        String hotelUrl = Hotel.getHotelUrl();
        Document hotelDoc = Jsoup.connect(hotelUrl).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").timeout(300000).get();
        FacilityBean bean = new FacilityBean();
        Elements elementsByClass = hotelDoc.getElementsByClass("facilitiesChecklistSection");
        for (Element e : elementsByClass) {
            e.getElementsByClass("facilityGroupIcon").remove();

            System.out.println("-----" + e.select("h5").text() + "----" + "/n");
            String Header = e.select("h5").text();
            if (Header != null) {
                bean.setFacilityBeanHeader(Header);
            }
            Elements lis = e.select("ul > li");
            List<String> ListItem = new ArrayList<String>();
            for (Element li : lis) {
                ListItem.add(li.text());
                System.out.println("List item:" + li.text());
            }
            String listString = "";

            for (String s : ListItem) {
                listString += s + "\t";
            }
            if (listString != null) {
                bean.setFacilityBeanItem(listString);
            }
            bean.setFacilityBeanid(url.getHotelId());
            Dao.addFacility(bean);
            Facilities.add(bean);
        }

        return Facilities;

    }
}
