/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Beans.ChartSentenceAspectBean;
import Beans.CommentBean;
import Beans.HotelBean;
import DAO.DaoHotels;
import DAO.DaoSentence;
import static Handlers.BringAspectsByHotelHandler.getMonthForInt;
import static Handlers.BringAspectsByHotelHandler.getOrderedMaps;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.jsoup.nodes.Document;

/**
 *
 * @author user1
 */
public class HotelPageInfoFromReviewsHandler {

    Document bringHotelAspectsStats(DaoHotels daohotel, DaoSentence daoSen, Document doc, HttpServletRequest request) {
        //if (!doc.getElementsByClass("hotel_description_wrapper").isEmpty()) {
        try {

            String url = URLDecoder.decode(request.getParameter("q"), "UTF-8");
            String endurl = url.split("\\?")[0];
            System.out.println("Nato      " + endurl + "----");

            HotelBean Hotel = daohotel.getAllHotelsbyUrl(endurl.trim());

            HashMap<String, Double> VacTypeCount = daohotel.bringVacTypeAndCount(Hotel.getHotelId());
            List<ChartSentenceAspectBean> allSentenceAspectByHotelid = daoSen.getAllSentenceAspectByHotelid(Hotel.getHotelId());
            String countryLocals = endurl.trim().split("/")[2];
            String CountryNAme = daohotel.bringCountryName(countryLocals);

            List<CommentBean> allCommentsByHotelIdAndCountryLimit3 = daohotel.getAllCommentsByHotelIdAndCountryLimit3(Hotel.getHotelId(), CountryNAme);
            System.out.println("Hotel number locals=" + allCommentsByHotelIdAndCountryLimit3.size());
            if (allCommentsByHotelIdAndCountryLimit3.size() > 0) {
                doc.getElementById("hp_availability_style_changes").prepend("<hr><div id='locals-commnets-container' class=\"hp-social_proof hp-social_proof__horizontal\" data-et-view=\"\n"
                        + "TAeKPDSSELIdLcGKBMRVSFKCTPROTHT:1\n"
                        + "TAeKPWbOTBMRVFWUDIYScXQOVWe:1\"> \n"
                        + "           <h3 class=\"poi-list-header\" data-et-view=\"eDdQAOTBMRVFWJGTcO:1 eDdQAOTBMRVFWJGTcO:2 \"> What Locals say about this property </h3> \n"
                        + "</div><hr>");
                for (CommentBean Beanni : allCommentsByHotelIdAndCountryLimit3) {
                    if (!(Beanni.getCommentBodyPos().trim().equalsIgnoreCase("") || Beanni.getCommentBodyPos().trim().isEmpty()) && !(Beanni.getCommentBodyNeg().trim().equalsIgnoreCase("") || Beanni.getCommentBodyNeg().trim().isEmpty())) {
                        doc.getElementById("locals-commnets-container").append(
                                "           <div class=\"hp-social_proof-item\"> \n"
                                + "            <div class=\"hp-social_proof-quote_bubble\"> \n"
                                + "             <div class=\"quote_bubble__content\"> \n" + "<i class=\"fas fa-plus\"></i>\n"
                                + ""
                                + Beanni.getCommentBodyPos()
                                + "<br>" + "<i class=\"fas fa-minus\"></i>\n" + Beanni.getCommentBodyNeg()
                                + "             </div> \n"
                                + "            </div> \n"
                                + "            <div class=\"hp-social_proof-quote_author clearfix\"> \n"
                                + "             <div class=\"hp-social_proof-quote_author-details\">\n" + Beanni.getCommentReviwerType()
                                + "              <br>\n"
                                + Beanni.getCommentCountry()
                                + "             </div> \n"
                                + "            </div> \n"
                                + "           </div> \n");
                    } else if ((Beanni.getCommentBodyPos().trim().equalsIgnoreCase("") || Beanni.getCommentBodyPos().trim().isEmpty()) && !(Beanni.getCommentBodyNeg().trim().equalsIgnoreCase("") || Beanni.getCommentBodyNeg().trim().isEmpty())) {
                        doc.getElementById("locals-commnets-container").append(
                                "           <div class=\"hp-social_proof-item\"> \n"
                                + "            <div class=\"hp-social_proof-quote_bubble\"> \n"
                                + "             <div class=\"quote_bubble__content\"> \n"
                                + "<br>" + "<i class=\"fas fa-minus\"></i>\n" + Beanni.getCommentBodyNeg()
                                + "             </div> \n"
                                + "            </div> \n"
                                + "            <div class=\"hp-social_proof-quote_author clearfix\"> \n"
                                + "             <div class=\"hp-social_proof-quote_author-details\">\n" + Beanni.getCommentReviwerType()
                                + "              <br>\n"
                                + Beanni.getCommentCountry()
                                + "             </div> \n"
                                + "            </div> \n"
                                + "           </div> \n");
                    } else if (!(Beanni.getCommentBodyPos().trim().equalsIgnoreCase("") || Beanni.getCommentBodyPos().trim().isEmpty()) && (Beanni.getCommentBodyNeg().trim().equalsIgnoreCase("") || Beanni.getCommentBodyNeg().trim().isEmpty())) {
                        doc.getElementById("locals-commnets-container").append(
                                "           <div class=\"hp-social_proof-item\"> \n"
                                + "            <div class=\"hp-social_proof-quote_bubble\"> \n"
                                + "             <div class=\"quote_bubble__content\"> \n"
                                + "<br>" + "<i class=\"fas fa-plus\"></i>\n" + Beanni.getCommentBodyPos()
                                + "             </div> \n"
                                + "            </div> \n"
                                + "            <div class=\"hp-social_proof-quote_author clearfix\"> \n"
                                + "             <div class=\"hp-social_proof-quote_author-details\">\n" + Beanni.getCommentReviwerType()
                                + "              <br>\n"
                                + Beanni.getCommentCountry()
                                + "             </div> \n"
                                + "            </div> \n"
                                + "           </div> \n");
                    }
                }
            }
            if (allSentenceAspectByHotelid.size() > 0) {
                Set<String> SetAspects = new HashSet<String>();

                for (ChartSentenceAspectBean Aspect : allSentenceAspectByHotelid) {

                    SetAspects.add(Aspect.getAspect());

                }

                doc.getElementById("hp_availability_style_changes").prepend("<hr> <h3 class='hp-description-sub-header'>Ranked From most Positive to Most Negative </h3>   <div class=\"row\">\n"
                        + "        <div class=\"col-md-4\">\n"
                        + "<h3 class='hp-description-sub-header'>Per Topic</h3>"
                        + "            <ul id=\"tree1\">\n"
                        + "                <li><a href=\"#\">Positive</a>\n"
                        + "\n"
                        + "                    <ul id='wrapper-for-stats-positive-1'>\n"
                        + "                    </ul>\n"
                        + "                </li>\n"+
                        "                <li><a href=\"#\">Neutral</a>\n"
                        + "\n"
                        + "                    <ul id='wrapper-for-stats-neutrall-1'>\n"
                        + "                    </ul>\n"
                        + "                </li>\n"
                        + "                <li><a href=\"#\">Negative</a>\n"
                        + "                    <ul id='wrapper-for-stats-negative-1'>\n"
                        + "                    </ul>\n"
                        + "                </li>\n"
                        + "            </ul>\n"
                        + "        </div>"
                        + "        <div class=\"col-md-4\">\n"
                        + "<h3 class='hp-description-sub-header'>Per Reviewer Type</h3>"
                        + "            <ul id=\"tree2\">\n"
                        + "                <li><a href=\"#\">Positive</a>\n"
                        + "\n"
                        + "                    <ul id='wrapper-for-stats-positive-2'>\n"
                        + "                    </ul>\n"
                        + "                </li>\n"+
                        "                <li><a href=\"#\">Neutral</a>\n"
                        + "\n"
                        + "                    <ul id='wrapper-for-stats-neutrall-2'>\n"
                        + "                    </ul>\n"
                        + "                </li>\n"
                        + "                <li><a href=\"#\">Negative</a>\n"
                        + "                    <ul id='wrapper-for-stats-negative-2'>\n"
                        + "                    </ul>\n"
                        + "                </li>\n"
                        + "            </ul>\n"
                        + "        </div>"
                        + "        <div class=\"col-md-4\">\n"
                        + "<h3 class='hp-description-sub-header'>Per Month</h3>"
                        + "            <ul id=\"tree3\">\n"
                        + "                <li><a href=\"#\">Positive</a>\n"
                        + "\n"
                        + "                    <ul id='wrapper-for-stats-positive-3'>\n"
                        + "                    </ul>\n"
                        + "                </li>\n"
                        +
                        "                <li><a href=\"#\">Neutral</a>\n"
                        + "\n"
                        + "                    <ul id='wrapper-for-stats-neutrall-3'>\n"
                        + "                    </ul>\n"
                        + "                </li>\n"
                        + "                <li><a href=\"#\">Negative</a>\n"
                        + "                    <ul id='wrapper-for-stats-negative-3'>\n"
                        + "                    </ul>\n"
                        + "                </li>\n"
                        + "            </ul>\n"
                        + "        </div><div id='wrapper-for-stats' class='col-sm-12'></div><div style='margin-top:10px' class='col-sm-12'><a href=\"#blockdisplay4\" class=\" btn btn-primary hp_nav_reviews_link toggle_review track_review_link_zh\" id=\"show_reviews_tab\" data-tab-link=\"\" rel=\"reviews\" data-component=\"core/sliding-panel-trigger\" data-target=\"hp-reviews-sliding\" data-active-class=\"selected\" data-google-track=\"Click/Action: hotel/review_link_inline_v1\" data-auto-open=\"from_sr_review\" aria-haspopup=\"true\" aria-role=\"button\" aria-controls=\"hp-reviews-sliding\"> <span> <strong>Guest reviews</strong></span> </a></div></div><hr>");

                HashMap<String, Double> PositivepermonthForAll = new HashMap();
                HashMap<String, Double> NegativepermonthForAll = new HashMap();
                HashMap<String, Double> PositiveperReviewerTypeForAll = new HashMap();
                HashMap<String, Double> NegativeperReviewerTypeForAll = new HashMap();
                HashMap<String, Double> PositivePerAspect = new HashMap<>();
                HashMap<String, Double> NegativePerAspect = new HashMap<>();
                HashMap<String, Double> VacationType = new HashMap();
                for (int j = 0; j < 12; j++) {
                    PositivepermonthForAll.put(getMonthForInt(j), 0.0);
                    NegativepermonthForAll.put(getMonthForInt(j), 0.0);
                }
                for (String asp : SetAspects) {
                    int countPos = 0;
                    int countNeg = 0;
                    Double ProbcountPos = 0.0;
                    Double ProbcountNeg = 0.0;

                    HashMap<String, Integer> Positivepermonth = new HashMap();
                    HashMap<String, Integer> Negativepermonth = new HashMap();
                    HashMap<String, Integer> PositiveperCountry = new HashMap();
                    HashMap<String, Integer> NegativeperCountry = new HashMap();
                    HashMap<String, Integer> PositiveperReviewerType = new HashMap();
                    HashMap<String, Integer> NegativeperReviewerType = new HashMap();

                    for (int i = 0; i < 12; i++) {
                        Positivepermonth.put(getMonthForInt(i), 0);
                        Negativepermonth.put(getMonthForInt(i), 0);

                    }

                    for (ChartSentenceAspectBean Aspect : allSentenceAspectByHotelid) {

                        String daterev = Aspect.getDateReview();
                        if (PositiveperCountry.get(Aspect.getCountry()) == null) {
                            PositiveperCountry.put(Aspect.getCountry(), 0);
                        }
                        if (NegativeperCountry.get(Aspect.getCountry()) == null) {
                            NegativeperCountry.put(Aspect.getCountry(), 0);
                        }
                        if (PositiveperReviewerType.get(Aspect.getReviewerType()) == null) {
                            PositiveperReviewerType.put(Aspect.getReviewerType(), 0);
                        }
                        if (NegativeperReviewerType.get(Aspect.getReviewerType()) == null) {
                            NegativeperReviewerType.put(Aspect.getReviewerType(), 0);
                        }
                        if (PositiveperReviewerTypeForAll.get(Aspect.getReviewerType()) == null) {
                            PositiveperReviewerTypeForAll.put(Aspect.getReviewerType(), 0.0);
                        }
                        if (NegativeperReviewerTypeForAll.get(Aspect.getReviewerType()) == null) {
                            NegativeperReviewerTypeForAll.put(Aspect.getReviewerType(), 0.0);
                        }
                        if (VacationType.get(Aspect.getVacType()) == null && Aspect.getVacType() != null) {
                            VacationType.put(Aspect.getVacType(), 0.0);
                        } else if (VacationType.get(Aspect.getVacType()) != null && Aspect.getVacType() != null) {
                            VacationType.put(Aspect.getVacType(), (VacationType.get(Aspect.getVacType()) + 1.0));
                        }
                        DateFormat format = new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH);
                        // System.out.println(daterev);
                        Date date = format.parse(daterev);
                        // System.out.println(date.getMonth());

                        if (Aspect.getAspect().equalsIgnoreCase(asp)) {
                            if (Aspect.getpolarity().equalsIgnoreCase("positive")) {
                                countPos++;
                                ProbcountPos = ProbcountPos + (1 * Aspect.getconfidence());
                                Positivepermonth.put(getMonthForInt(date.getMonth()), Positivepermonth.get(getMonthForInt(date.getMonth())) + 1);
                                PositiveperCountry.put(Aspect.getCountry(), PositiveperCountry.get(Aspect.getCountry()) + 1);
                                PositiveperReviewerType.put(Aspect.getReviewerType(), PositiveperReviewerType.get(Aspect.getReviewerType()) + 1);
                                PositivepermonthForAll.put(getMonthForInt(date.getMonth()), PositivepermonthForAll.get(getMonthForInt(date.getMonth())) + (1 * Aspect.getconfidence()));
                                PositiveperReviewerTypeForAll.put(Aspect.getReviewerType(), PositiveperReviewerTypeForAll.get(Aspect.getReviewerType()) + (1 * Aspect.getconfidence()));
                            } else if (Aspect.getpolarity().equalsIgnoreCase("negative")) {
                                countNeg++;
                                ProbcountNeg = ProbcountNeg + (1 * Aspect.getconfidence());
                                Negativepermonth.put(getMonthForInt(date.getMonth()), Negativepermonth.get(getMonthForInt(date.getMonth())) + 1);
                                NegativeperCountry.put(Aspect.getCountry(), NegativeperCountry.get(Aspect.getCountry()) + 1);
                                NegativeperReviewerType.put(Aspect.getReviewerType(), NegativeperReviewerType.get(Aspect.getReviewerType()) + 1);
                                NegativepermonthForAll.put(getMonthForInt(date.getMonth()), NegativepermonthForAll.get(getMonthForInt(date.getMonth())) + (1 * Aspect.getconfidence()));
                                NegativeperReviewerTypeForAll.put(Aspect.getReviewerType(), NegativeperReviewerTypeForAll.get(Aspect.getReviewerType()) + (1 * Aspect.getconfidence()));
                            }
                        }

                    }
                    PositivePerAspect.put(asp, ProbcountPos);
                    NegativePerAspect.put(asp, ProbcountNeg);

                }

                PositivepermonthForAll = addValues(PositivepermonthForAll, NegativepermonthForAll);
                PositiveperReviewerTypeForAll = addValues(PositiveperReviewerTypeForAll, NegativeperReviewerTypeForAll);
                PositivePerAspect = addValues(PositivePerAspect, NegativePerAspect);
                List<Map.Entry<String, Double>> PositiveAllMonth = getOrderedMaps(PositivepermonthForAll);
                List<Map.Entry<String, Double>> VacationTypes = getOrderedMaps(VacTypeCount);
                List<Map.Entry<String, Double>> PositiveAllTypeRev = getOrderedMaps(PositiveperReviewerTypeForAll);
                List<Map.Entry<String, Double>> PositivePerHotelAspect = getOrderedMaps(PositivePerAspect);

                for (Map.Entry<String, Double> entry : PositivepermonthForAll.entrySet()) {
                    // // System.out.println(entry.getKey()+"_"+entry.getValue());
                }
                for (Map.Entry<String, Double> entry : PositivePerHotelAspect) {
                    System.out.println(entry.getKey() + "_" + entry.getValue());
                    if (entry.getValue() > 0.0) {
                        doc.getElementById("wrapper-for-stats-positive-1").append("<li class='positive-value'>" + entry.getKey() + "</li>");
                    } else if(entry.getValue() == 0.0) {
                        doc.getElementById("wrapper-for-stats-neutrall-1").append("<li class='neutrall-value'>" + entry.getKey() + "</li>");
                    }else{
                        doc.getElementById("wrapper-for-stats-negative-1").append("<li class='negative-value'>" + entry.getKey() + "</li>");
                    }
                }
                for (Map.Entry<String, Double> entry : PositiveAllTypeRev) {
                    System.out.println(entry.getKey() + "_" + entry.getValue());
                    if (entry.getValue() > 0.0) {
                        doc.getElementById("wrapper-for-stats-positive-2").append("<li class='positive-value'>" + entry.getKey() + "</li>");
                    }else if(entry.getValue() == 0.0) {
                        doc.getElementById("wrapper-for-stats-neutrall-2").append("<li class='neutrall-value'>" + entry.getKey() + "</li>");
                    } else {
                        doc.getElementById("wrapper-for-stats-negative-2").append("<li class='negative-value'>" + entry.getKey() + "</li>");
                    }
                }
                for (Map.Entry<String, Double> entry : PositiveAllMonth) {
                    System.out.println(entry.getKey() + "_" + entry.getValue());
                    if (entry.getValue() > 0.0) {
                        doc.getElementById("wrapper-for-stats-positive-3").append("<li class='positive-value'>" + entry.getKey() + "</li>");
                    }else if(entry.getValue() == 0.0) {
                        doc.getElementById("wrapper-for-stats-neutrall-3").append("<li class='neutrall-value'>" + entry.getKey() + "</li>");
                    } else {
                        doc.getElementById("wrapper-for-stats-negative-3").append("<li class='negative-value'>" + entry.getKey() + "</li>");
                    }
                }

                for (Map.Entry<String, Double> entry : VacationTypes) {
                    if (entry.getValue() > 0.0 && entry.getKey() != null) {
                        doc.getElementById("wrapper-for-stats").append("<h3 style='margin:10px 0px 10px 0px;'>" + entry.getValue().intValue() + " travellers visit this property for " + entry.getKey() + "!</h3></div>");
                    }
                }

            }

        } catch (ParseException | UnsupportedEncodingException ex) {
            Logger.getLogger(HotelPageInfoFromReviewsHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        doc.append("<script src=\"Scripts/FacebookMAp.js\" type=\"text/javascript\"></script>");
        return doc;
    }

    public static String getMonthForInt(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    public static List<Map.Entry<String, Double>> getOrderedMaps(HashMap map) {
        Set<Map.Entry<String, Double>> set = map.entrySet();
        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        return list;
    }

    public HashMap<String, Double> addValues(HashMap<String, Double> a, HashMap<String, Double> b) {
        HashMap<String, Double> ret = new HashMap<String, Double>(a);
        for (String s : b.keySet()) {
            if (ret.containsKey(s)) {
                ret.put(s, -b.get(s) + ret.get(s));
            } else {
                ret.put(s, -b.get(s));
            }
        }
        return ret;
    }
}
