/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Beans.ChartSentenceAspectBean;
import Beans.HotelBean;
import Beans.HotelBeanWithDiv;
import Beans.HotelRankBean;
import DAO.DaoHotels;
import DAO.DaoSentence;
import SentimentAylien.AspectSentimentAnalysis;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author user1
 */
public class SortCriteriaHandler {

    public HashMap<HotelBeanWithDiv, List<Map.Entry<String, Double>>> AnalyzeHotelsCriteria(Document doc, String url, DaoHotels daohotel, DaoSentence daoSen, HttpServletRequest request) throws FileNotFoundException, UnsupportedEncodingException {
        HashMap<HotelBeanWithDiv, List<Map.Entry<String, Double>>> map = new HashMap<HotelBeanWithDiv, List<Map.Entry<String, Double>>>();
        if (doc.getElementsByClass("results-meta").size() > 0) {
            doc.getElementsByClass("sr_item").remove();
            List<HotelBeanWithDiv> HotelsList = new ArrayList<>();
            Elements el = doc.getElementsByClass("results-meta").select("span").remove();
            String Total = el.html().replaceAll("[^0-9]", "");
            // System.out.println(Total);
            // int total = Integer.parseInt(Total);

            boolean flag = true;

            while (flag) {
                try {
                    
                    Document doci = Jsoup.connect(url).get();
                    Elements Sr_Item = doci.getElementsByClass("sr_item");
                    if (Sr_Item.size() > 0) {

                        for (Element eli : Sr_Item) {
                            if (eli.select(".hotel_name_link").size() > 0) {
                                Element hotel = eli.select(".hotel_name_link").get(0);
                                HotelBeanWithDiv hoteldiv = new HotelBeanWithDiv();
                                // System.out.println("Hotel Url : " + hotel.attr("href").split("\\?")[0].trim());
                                if (daohotel.getAllHotelsbyUrl(hotel.attr("href").split("\\?")[0].trim()).getHoteName() != null) {

                                    hoteldiv.setHotel(daohotel.getAllHotelsbyUrl(hotel.attr("href").split("\\?")[0].trim()));
                                    hoteldiv.setDiv(eli.toString());
                                    if (containsName(HotelsList, hoteldiv.getHotel().getHotelUrl())) {

                                    } else {
                                        if (hoteldiv.getHotel().getHotelId() == 9495) {
                                            // AspectSentimentAnalysis senti= new AspectSentimentAnalysis();
                                            //senti.Analyze(daoSen.getAllSentencesnot(hoteldiv.getHotel().getHotelId()), daoSen);
                                        }
                                        System.out.println("Hotel Url : " + hotel.attr("href").split("\\?")[0].trim());

                                        HotelsList.add(hoteldiv);
                                    }

                                } else {
                                    // System.out.println("null");
                                }
                            }

                        }
                        if (doci.getElementsByClass("paging-next").size() > 0) {
                            url = "https://booking.com" + doci.getElementsByClass("paging-next").get(0).attr("href");
                        } else {
                            flag = false;
                        }
                    }

                } catch (IOException ex) {
                    Logger.getLogger(SortCriteriaHandler.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            for (HotelBeanWithDiv hotel : HotelsList) {

                // System.out.print(hotel.getHotel().getHotelAdress());
                List<ChartSentenceAspectBean> allSentenceAspectByHotelid = daoSen.getAllSentenceAspectByHotelid(hotel.getHotel().getHotelId());
                Set<String> SetAspects = new HashSet<String>();
                HashMap<String, Double> VacTypeCount = daohotel.bringVacTypeAndCount(hotel.getHotel().getHotelId());
                for (ChartSentenceAspectBean Aspect : allSentenceAspectByHotelid) {
//System.out.print(Aspect.getAspect());
                    SetAspects.add(Aspect.getAspect());

                }
                if (SetAspects.size() > 0) {
                    try {

                        ///////Foreach Aspects////////////////////
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
                        map.put(hotel, PositivePerHotelAspect);
                    } catch (ParseException ex) {
                        Logger.getLogger(SortCriteriaHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
        return map;
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

    public boolean containsName(final List<HotelBeanWithDiv> list, final String name) {
        return list.stream().filter(o -> o.getHotel().getHotelUrl().equals(name)).findFirst().isPresent();
    }

    public boolean containsNameRank(final List<HotelRankBean> list, final String name) {
        return list.stream().filter(o -> o.getHote().getHotel().getHotelUrl().equals(name)).findFirst().isPresent();
    }

    public List<HotelRankBean> containsNameRank(final List<HotelRankBean> list, final String name, double score, String aspect, final List<HotelRankBean> after, HotelBeanWithDiv Hotel) {
        HotelRankBean n = new HotelRankBean();
        n.setHotelAspectScore(score);
        n.setHotelAspect(aspect);
        n.setHotel(Hotel);
        list.stream().filter(o -> o.getHote().getHotel().getHotelUrl().equals(name) && !(o.getHotelAspect().equalsIgnoreCase(aspect))).forEach((item)
                -> {
            n.setHotel(item.getHote());
            n.setHotelAspectScore(n.getHoteAspectScore() + item.getHoteAspectScore());
            n.setHotelAspect(n.getHotelAspect() + "," + item.getHotelAspect());

        });

        if (!containsNameRank(after, n.getHote().getHotel().getHotelUrl())) {
            System.out.println("After " + n.getHote().getHotel().getHoteName() + " --- " + n.getHotelAspect() + "-----  " + n.getHoteAspectScore());
            after.add(n);
        }

        return after;
    }

    public Document returnOutpuSorted(HashMap<HotelBeanWithDiv, List<Map.Entry<String, Double>>> AnalyzeHotelsCriteria, DaoSentence daoSen, Document doc, HashMap<String, String> Filtetrs) {
        List<HotelRankBean> Arr = new ArrayList<HotelRankBean>();
        int i = 0;
        if (AnalyzeHotelsCriteria.size() > 0) {
            for (Map.Entry<HotelBeanWithDiv, List<Map.Entry<String, Double>>> entry : AnalyzeHotelsCriteria.entrySet()) {

                HotelBean key = entry.getKey().getHotel();

                //System.out.println("The hotel is ::::: "+key.getHoteName());
                //System.out.println("The div is ::::: "+entry.getKey().getDiv());
                List<Map.Entry<String, Double>> value = entry.getValue();
                for (Map.Entry<String, Double> entryy : value) {
                    if (Arrays.asList(Filtetrs).contains(entryy.getKey())) {
                        HotelRankBean rankbean = new HotelRankBean();
                        rankbean.setHotel(entry.getKey());
                        rankbean.setHotelAspect(entryy.getKey());
                        rankbean.setHotelAspectScore(entryy.getValue());
                        //System.out.println("Aspect :"+entryy.getKey()+" Value = "+entryy.getValue());
                        Arr.add(rankbean);
                    } else {
                        HotelRankBean rankbean = new HotelRankBean();
                        rankbean.setHotel(entry.getKey());
                        rankbean.setHotelAspect(entryy.getKey());
                        rankbean.setHotelAspectScore(entryy.getValue());
                        //System.out.println("Aspect :"+entryy.getKey()+" Value = "+entryy.getValue());
                        Arr.add(rankbean);
                    }
                }
                i++;
            }

            Collections.sort(Arr, Collections.reverseOrder());
            int j = 0;
            List<String> distinctSentenceAspect = daoSen.getDistinctSentenceAspect();
            HashMap<String, LinkedList<HotelRankBean>> AspectMAps = new HashMap<String, LinkedList<HotelRankBean>>();
            for (String aspect : distinctSentenceAspect) {

                LinkedList<HotelRankBean> lista = new LinkedList<>();
                for (HotelRankBean temp : Arr) {

                    if (temp.getHotelAspect().equalsIgnoreCase(aspect)) {

                        //doc.getElementById("hotellist_inner").append(temp.getHote().getDiv());
                        // System.out.println("Hotels " + ++j + " : " + temp.getHote().getHotel().getHoteName()+
                        //", Aspect : " + temp.getHotelAspect() +" Score :"+temp.getHoteAspectScore());
                        lista.add(temp);
                    }

                }

                AspectMAps.put(aspect, lista);
            }
            LinkedList<HotelRankBean> Combined = new LinkedList<>();
            List<HotelRankBean> end = new ArrayList<>();
            String[] picks = new String[Filtetrs.size()];
            if (Filtetrs.size() > 1) {
doc.getElementById("hotellist_inner").append("<div class='block-header-criteria text-center'><div class='row'><div class='col-sm-12'><div class='choises-panel'><h3 > The best choises sorted based on your criteria :</h3><ul class='list-inline'></ul></div></div></div><div class='row'><div class='col-sm-12'><button id=\"opensortoptionbutton\" class=\"sort_option_avoid \"><span>Change your criteria</span></button></div></div><div class='row'><div class='col-sm-12'><button id=\"opensortoptionbutton_delete\" class=\"sort_option_avoid_delete \"><span><i class=\"far fa-trash-alt\"></i>\n" +
"Delete your options</span></button></div></div></div>");
                int o = 0;

                for (String aspe : Filtetrs.keySet()) {
                    LinkedList<HotelRankBean> get = AspectMAps.get(aspe);
                    doc.getElementById("searchboxInc").append("<div class='box-top-5 searchboxInc-" + aspe.replaceAll("[^A-Za-z0-9]", "") + "'><h3>Top 5 for " + aspe + "</h></div>");
                    int k = 0;
                    for (HotelRankBean sorted : get) {
                        System.out.println("Polaplasiasths - prin :::: " + sorted.getHoteAspectScore());
                        sorted.setHotelAspectScore(sorted.getHoteAspectScore() * Double.parseDouble(Filtetrs.get(aspe) + ".0") / (100 * Filtetrs.size()));
                        System.out.println("Polaplasiasths :::: " + sorted.getHoteAspectScore());
                        Combined.add(sorted);
                        System.out.println("Before " + sorted.getHote().getHotel().getHoteName() + " --- " + sorted.getHotelAspect() + "-----  " + sorted.getHoteAspectScore());
                        // doc.getElementById("hotellist_inner").append(sorted.getHote().getDiv());
                        if (sorted.getHotelAspect().equalsIgnoreCase(aspe) && k < 5) {
                            doc.getElementsByClass("searchboxInc-" + sorted.getHotelAspect().replaceAll("[^A-Za-z0-9]", "")).append("<div><a href='"+sorted.getHote().getHotel().getHotelUrl().replaceAll("http://www.booking.com/", "")+"'> "+ sorted.getHote().getHotel().getHoteName() + "</a></div>");
                            k++;
                        }
                    }
                    picks[o] = aspe + " - " + Filtetrs.get(aspe);
                    doc.select(".choises-panel").select("ul").append("<li class='list-inline-item'><span class='aspect-li'>" + aspe + "</span><span class='range-li'>  " + Filtetrs.get(aspe) + "%</span></li>");
                    o++;
                    
                }
                doc.select(".choises-panel").append("<script>$(\".choises-panel li\").click(function(){\n" +
                        "$('html, body').animate({\n" +
"        scrollTop: $('.searchboxInc-'+$(this).find(\".aspect-li\").html().replace(/[^a-zA-Z0-9]+/g, \"\")).offset().top\n" +
"    }, 2000);"+
"        $('.box-top-5').removeClass('box-top-5-hi'); \n $('.searchboxInc-'+$(this).find(\".aspect-li\").html().replace(/[^a-zA-Z0-9]+/g, \"\")).addClass('box-top-5-hi');" +
"    });</script>");
                for (HotelRankBean ending : Combined) {

                    containsNameRank(Combined, ending.getHote().getHotel().getHotelUrl(), ending.getHoteAspectScore(), ending.getHotelAspect(), end, ending.getHote());
                }
                Collections.sort(end, Collections.reverseOrder());
                for (HotelRankBean beani : end) {
                    doc.getElementById("hotellist_inner").append(beani.getHote().getDiv());
                }
                doc.append("<form id='DeleteOptions' action='UrlContoller?action=DeleteOptionsSort' method='post'></form>");
            } else {
                doc.getElementById("hotellist_inner").append("<div class='block-header-criteria text-center'><div class='row'><div class='col-sm-12'><div class='choises-panel'><h3 > The best choises sorted based on your criteria :</h3><ul></ul></div></div></div><div class='row'><div class='col-sm-12'><button id=\"opensortoptionbutton\" class=\"sort_option_avoid \"><span>Change your criteria</span></button></div></div><div class='row'><div class='col-sm-12'><button id=\"opensortoptionbutton_delete\" class=\"sort_option_avoid_delete \"><span><i class=\"far fa-trash-alt\"></i>\n" +
"Delete your options</span></button></div></div></div>");

                for (String aspe : Filtetrs.keySet()) {
                    LinkedList<HotelRankBean> get = AspectMAps.get(aspe);
                    for (HotelRankBean sorted : get) {
                        sorted.setHotelAspectScore(sorted.getHoteAspectScore() * Integer.parseInt(Filtetrs.get(aspe)) / (100 * Filtetrs.size()));
                        Combined.add(sorted);
                        System.out.println("Before " + sorted.getHote().getHotel().getHoteName() + " --- " + sorted.getHotelAspect() + "-----" + sorted.getHoteAspectScore());
                        // doc.getElementById("hotellist_inner").append(sorted.getHote().getDiv());
                    }
                   doc.select(".choises-panel").select("ul").append("<li class='list-inline-item'><span class='aspect-li'>" + aspe + "</span><span class='range-li'> - " + Filtetrs.get(aspe) + "%</span></li>");

                }
                doc.select(".choises-panel").append("<script>$(\".choises-panel li\").click(function(){\n" +
"       \n" +
"    });</script>");
                Collections.sort(Combined, Collections.reverseOrder());

                for (HotelRankBean beani : Combined) {
                    doc.getElementById("hotellist_inner").append(beani.getHote().getDiv());
                }
                doc.append("<form id='DeleteOptions' action='UrlContoller?action=DeleteOptionsSort' method='post'></form>");
            }
        } else {
            doc.getElementById("hotellist_inner").append("<div><h3>No results</h3></div>");
        }
        return doc;

    }

}
