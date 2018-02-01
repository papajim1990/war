/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Beans.ChartSentenceAspectBean;
import Beans.HotelBean;
import DAO.DaoHotels;
import DAO.DaoSentence;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author user1
 */
public class BringAspectsByHotelHandler {

    Document bringAspectsForEachHotel(DaoHotels daohotel, DaoSentence daoSen, Document elementsByClass, HttpServletRequest request) {
        try {
            elementsByClass.getElementsByTag("head").get(0).append("<script src=\"Scripts/jquery-3.2.1.min.js\" type=\"text/javascript\"></script><script src=\"Scripts/canvasjs.min.js\" type=\"text/javascript\"></script>");
            //////Hotels From UI
            for (Element HotelL : elementsByClass.getElementsByClass("sr_item")) {
                String url = URLDecoder.decode(HotelL.select("a.hotel_name_link").attr("href").replaceAll("UrlContoller\\?q=", ""), "UTF-8");
                String endurl = url.split("\\?")[0];
                //System.out.println(HotelL.select("a.hotel_name_link").attr("href").replaceAll("UrlContoller\\?q=", ""));
                HotelBean Hotel = daohotel.getAllHotelsbyUrl(endurl.trim());
                List<ChartSentenceAspectBean> allSentenceAspectByHotelid = daoSen.getAllSentenceAspectByHotelid(Hotel.getHotelId());
                if (allSentenceAspectByHotelid.size() > 0) {

                }
                //System.out.println(Hotel.getHotelId());

                Set<String> SetAspects = new HashSet<String>();

                for (ChartSentenceAspectBean Aspect : allSentenceAspectByHotelid) {

                    SetAspects.add(Aspect.getAspect());

                }
                //////
                /// check if hotel is in database and remove banner
                /////
                if (Hotel.getHotelId() != 0 && SetAspects.size() > 0) {
                    if (!HotelL.getElementsByClass("js_icon_over_photo").isEmpty()) {
                        HotelL.getElementsByClass("js_icon_over_photo").remove();
                    }
                    //////////
                    //////////
                    /////Modal for stats ////////////////////////////////////
                    HotelL.append("<div class='stats-container" + Hotel.getHotelId() + "'></div>" + "<button class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\".bd-example-modal-lghotel" + Hotel.getHotelId() + "\">See detailed stats</button>\n");
                           HotelL.parent().append("\n"
                            + "<div class=\"modal fade bd-example-modal-lghotel" + Hotel.getHotelId() + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myLargeModalLabel\" aria-hidden=\"true\">\n"
                            + "  <div class=\"modal-dialog modal-lg\">\n"
                            + "    <div class=\"modal-content\">\n"
                            + "<div class=\"modal-header\">\n"
                            + "        <h5 style='width:100%' class=\"modal-title text-center\">" + Hotel.getHoteName() + " Stats</h5>\n"
                            + "        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\n"
                            + "          <span aria-hidden=\"true\">&times;</span>\n"
                            + "        </button>\n"
                            
                            + "      </div>\n"
                            + "      <div id='hotel" + Hotel.getHotelId() + "' class=\"modal-body\">\n"
                            + "<div class='search-input-modal'><input id='search-" + Hotel.getHotelId() + "' type='text' class='seac-bar-modal' placeholder='Find Aspect...' ></div>"
                            + "      </div>\n"
                            + "      <div class=\"modal-footer\">\n"
                            + "        <button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Close</button>\n"
                            + "      </div>"
                            + "    </div>\n"
                            + "  </div>\n"
                            + "</div>");

                }
                //////////////////////////////////////////////
                Elements Selected = HotelL.parent().select("#hotel" + Hotel.getHotelId());
                //////Check if there are any hotels with stats//////////////////
                if (Selected.size() > 0) {
                    Selected.get(0).append("<div id=\"accordionHotelStats" + Hotel.getHotelId() + "\" role=\"tablist\" aria-multiselectable=\"true\">\n"
                            + "</div>");
                    HashMap<String, Double> PositivepermonthForAll = new HashMap();
                    HashMap<String, Double> NegativepermonthForAll = new HashMap();
                    HashMap<String, Double> PositiveperReviewerTypeForAll = new HashMap();
                    HashMap<String, Double> NegativeperReviewerTypeForAll = new HashMap();
                    for (int j = 0; j < 12; j++) {
                        PositivepermonthForAll.put(getMonthForInt(j), 0.0);
                        NegativepermonthForAll.put(getMonthForInt(j), 0.0);
                    }
                    ///////Foreach Aspects////////////////////
                    for (String asp : SetAspects) {
                        int countPos = 0;
                        int countNeg = 0;
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
                            DateFormat format = new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH);
                            // System.out.println(daterev);
                            Date date = format.parse(daterev);
                            // System.out.println(date.getMonth());

                            if (Aspect.getAspect().equalsIgnoreCase(asp)) {
                                if (Aspect.getpolarity().equalsIgnoreCase("positive")) {
                                    countPos++;
                                    Positivepermonth.put(getMonthForInt(date.getMonth()), Positivepermonth.get(getMonthForInt(date.getMonth())) + 1);
                                    PositiveperCountry.put(Aspect.getCountry(), PositiveperCountry.get(Aspect.getCountry()) + 1);
                                    PositiveperReviewerType.put(Aspect.getReviewerType(), PositiveperReviewerType.get(Aspect.getReviewerType()) + 1);
                                    PositivepermonthForAll.put(getMonthForInt(date.getMonth()), PositivepermonthForAll.get(getMonthForInt(date.getMonth())) + (1 * Aspect.getconfidence()));
                                    PositiveperReviewerTypeForAll.put(Aspect.getReviewerType(), PositiveperReviewerTypeForAll.get(Aspect.getReviewerType()) + (1 * Aspect.getconfidence()));
                                } else if (Aspect.getpolarity().equalsIgnoreCase("negative")) {
                                    countNeg++;
                                    Negativepermonth.put(getMonthForInt(date.getMonth()), Negativepermonth.get(getMonthForInt(date.getMonth())) + 1);
                                    NegativeperCountry.put(Aspect.getCountry(), NegativeperCountry.get(Aspect.getCountry()) + 1);
                                    NegativeperReviewerType.put(Aspect.getReviewerType(), NegativeperReviewerType.get(Aspect.getReviewerType()) + 1);
                                    NegativepermonthForAll.put(getMonthForInt(date.getMonth()), NegativepermonthForAll.get(getMonthForInt(date.getMonth())) + (1 * Aspect.getconfidence()));
                                    NegativeperReviewerTypeForAll.put(Aspect.getReviewerType(), NegativeperReviewerTypeForAll.get(Aspect.getReviewerType()) + (1 * Aspect.getconfidence()));
                                }
                            }
                        }

                        String append = "";
                        String append2 = "";
                        String append3 = "";
                        if (countPos + countNeg > 0) {
                            append = append + "<div class='full-lenght' id=\"chartContainer" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\" style=\"height: 370px; width: 100%;\"><img src='Css/graph-survey-platform-design-ux-interface-ramotion.gif' class='loading-gif'/></div>\n"
                                    + "  <button class=\"btn invisible\" id=\"backButton" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\">< Back</button>";
                            append = append + "<script>\n"
                                    + "$(document).ready( function () {\n "
                                    + "$('#accordionHotelStats" + Hotel.getHotelId() + "').on('show.bs.collapse', function (e) {" + "$(\".full-lenght\").html(\"<img src='Css/graph-survey-platform-design-ux-interface-ramotion.gif' class='loading-gif'/>\");" + "});"
                                    + "$('#accordionHotelStats" + Hotel.getHotelId() + "').on('shown.bs.collapse', function (e) {"
                                    + "$(\".loading-gif\").hide();"
                                    + "\n"
                                    + "var totalVisitors" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + " = " + (countPos + countNeg) + ";\n"
                                    + "var visitorsData" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + " = {\n"
                                    + "	\"New vs Returning Visitors\": [{\n"
                                    + "		click: visitorsChartDrilldownHandler" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ",\n"
                                    + "		cursor: \"pointer\",\n"
                                    + "		explodeOnClick: false,\n"
                                    + "		innerRadius: \"75%\",\n"
                                    + "		legendMarkerType: \"square\",\n"
                                    + "		name: \"Positive and Negative for " + asp + "\",\n"
                                    + "		radius: \"100%\",\n"
                                    + "		showInLegend: true,\n"
                                    + "		startAngle: 90,\n"
                                    + "		type: \"doughnut\",\n"
                                    + "		dataPoints: [\n"
                                    + "			{ y: " + countPos + ", name: \"Positive\", color: \"#9ae055\" },\n"
                                    + "			{ y: " + countNeg + ", name: \"Negative\", color: \"#ed5c5c\" }\n"
                                    + "		]\n"
                                    + "	}],\n" + "	\"Positive\": [{\n"
                                    + "		color: \"#9ae055\",\n"
                                    + "		name: \"Positive\",\n"
                                    + "		type: \"column\",\n"
                                    + "		dataPoints: [\n";

                            for (Map.Entry<String, Integer> entry : Positivepermonth.entrySet()) {
                                String key = entry.getKey();
                                Object value = entry.getValue();
                                append = append + "			{ x: new Date(\"1 " + key + " 2017 \"), y: " + value + " },\n";

                            }

                            append = append
                                    + "		]\n"
                                    + "	}],\n";
                            append = append
                                    + "	\"Negative\": [{\n"
                                    + "		color: \"#ed5c5c\",\n"
                                    + "		name: \"Negative\",\n"
                                    + "		type: \"column\",\n"
                                    + "		dataPoints: [\n";
                            for (Map.Entry<String, Integer> entry : Negativepermonth.entrySet()) {
                                String key = entry.getKey();
                                Object value = entry.getValue();
                                append = append
                                        + "			{ x: new Date(\"1 " + key + " 2017 \"), y: " + value + " },\n";

                            }
                            append = append
                                    + "		]\n"
                                    + "	}]\n"
                                    + "};\n"
                                    + "\n";
                            append = append
                                    + "var newVSReturningVisitorsOptions" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + " = {\n"
                                    + "	animationEnabled: true,\n"
                                    + "	theme: \"light2\",\n"
                                    + "	title: {\n"
                                    + "		text: \"Positive and Negative for " + asp + "\"\n"
                                    + "	},\n"
                                    + "	subtitles: [{\n"
                                    + "		text: \"Click on Any Segment to Drilldown\",\n"
                                    + "		backgroundColor: \"#2eacd1\",\n"
                                    + "		fontSize: 16,\n"
                                    + "		fontColor: \"white\",\n"
                                    + "		padding: 5\n"
                                    + "	}],\n"
                                    + "	legend: {\n"
                                    + "		fontFamily: \"calibri\",\n"
                                    + "		fontSize: 14,\n"
                                    + "		itemTextFormatter: function (e) {\n"
                                    + "			return e.dataPoint.name + \": \" + Math.round(e.dataPoint.y / totalVisitors" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + " * 100) + \"%\";  \n"
                                    + "		}\n"
                                    + "	},\n"
                                    + "	data: []\n"
                                    + "};\n"
                                    + "\n"
                                    + "var visitorsDrilldownedChartOptions" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + " = {\n"
                                    + "	animationEnabled: true,\n"
                                    + "	theme: \"light2\",\n"
                                    + "	axisX: {\n"
                                    + "		labelFontColor: \"#717171\",\n"
                                    + "		lineColor: \"#a2a2a2\",\n"
                                    + "		tickColor: \"#a2a2a2\"\n"
                                    + "	},\n"
                                    + "	axisY: {\n"
                                    + "		gridThickness: 0,\n"
                                    + "		includeZero: false,\n"
                                    + "		labelFontColor: \"#717171\",\n"
                                    + "		lineColor: \"#a2a2a2\",\n"
                                    + "		tickColor: \"#a2a2a2\",\n"
                                    + "		lineThickness: 1\n"
                                    + "	},\n"
                                    + "	data: []\n"
                                    + "};\n"
                                    + "\n"
                                    + "var chart" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + " = new CanvasJS.Chart(\"chartContainer" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\", newVSReturningVisitorsOptions" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ");\n"
                                    + "chart" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ".options.data = visitorsData" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "[\"New vs Returning Visitors\"];\n"
                                    + "chart" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ".render();\n"
                                    + "\n"
                                    + "function visitorsChartDrilldownHandler" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "(e) {\n"
                                    + "	chart" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + " = new CanvasJS.Chart(\"chartContainer" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\", visitorsDrilldownedChartOptions" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ");\n"
                                    + "	chart" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ".options.data = visitorsData" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "[e.dataPoint.name];\n"
                                    + "	chart" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ".options.title = { text: e.dataPoint.name }\n"
                                    + "	chart" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ".render();\n"
                                    + "	$(\"#backButton" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\").toggleClass(\"invisible\");\n"
                                    + "}\n"
                                    + "\n"
                                    + "$(\"#backButton" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\").click(function() { \n"
                                    + "	$(this).toggleClass(\"invisible\");\n"
                                    + "	chart" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + " = new CanvasJS.Chart(\"chartContainer" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\", newVSReturningVisitorsOptions" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ");\n"
                                    + "	chart" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ".options.data = visitorsData" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "[\"New vs Returning Visitors\"];\n"
                                    + "	chart" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ".render();\n"
                                    + "});\n"
                                    + "\n"
                                    + "});});\n"
                                    + "</script>";

                            append2 = append2 + "<div class='full-lenght' id=\"chartContainercountry" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\" style=\"height: 370px; width: 100%;\"><img src='Css/graph-survey-platform-design-ux-interface-ramotion.gif' class='loading-gif'/></div>";
                            append2 = append2 + "<script>\n"
                                    + "$(document).ready( function() {\n"
                                    + "$('#accordionHotelStats" + Hotel.getHotelId() + "').on('show.bs.collapse', function (e) {" + "$(\".full-lenght\").html(\"<img src='Css/graph-survey-platform-design-ux-interface-ramotion.gif' class='loading-gif'/>\");" + "});"
                                    + "$('#accordionHotelStats" + Hotel.getHotelId() + "').on('shown.bs.collapse', function (e) {"
                                    + "$(\".loading-gif\").hide();"
                                    + "var chartcountry" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + " = new CanvasJS.Chart(\"chartContainercountry" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\", {\n"
                                    + "	exportEnabled: true,\n"
                                    + "	animationEnabled: true,\n"
                                    + "	title:{\n"
                                    + "		text: \"Positive and Negative per reviewer Country\"\n"
                                    + "	},\n"
                                    + "	subtitles: [{\n"
                                    + "		text: \"Click Legend to Hide or Unhide Data Series\"\n"
                                    + "	}], \n"
                                    + "	axisX: {\n"
                                    + "		title: \"Country\"\n"
                                    + "	},\n"
                                    + "	axisY: {\n"
                                    + "		title: \"Positive - Count\",\n"
                                    + "		titleFontColor: \"#9ae055\",\n"
                                    + "		lineColor: \"#9ae055\",\n"
                                    + "		labelFontColor: \"#9ae055\",\n"
                                    + "		tickColor: \"#9ae055\"\n"
                                    + "	},\n"
                                    + "	axisY2: {\n"
                                    + "		title: \"Negative - Count\",\n"
                                    + "		titleFontColor: \"#ed5c5c\",\n"
                                    + "		lineColor: \"#ed5c5c\","
                                    + "		labelFontColor: \"#ed5c5c\",\n"
                                    + "		tickColor: \"#ed5c5c\"\n"
                                    + "	},\n"
                                    + "	toolTip: {\n"
                                    + "		shared: true\n"
                                    + "	},\n"
                                    + "	legend: {\n"
                                    + "		cursor: \"pointer\",\n"
                                    + "		itemclick: toggleDataSeriescountry" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\n"
                                    + "	},\n"
                                    + "	data: [{\n"
                                    + "		type: \"column\",\n"
                                    + "		name: \"Positive\",\n"
                                    + "		showInLegend: true,      \n"
                                    + "              color : \"#9ae055\",       "
                                    + "		yValueFormatString: \"#,##0.# \",\n"
                                    + "		dataPoints: [\n";
                            for (Map.Entry<String, Integer> entry : PositiveperCountry.entrySet()) {
                                String key = entry.getKey();
                                Object value = entry.getValue();

                                append2 = append2 + "			{ label: \"" + key + "\", y: " + value + ",color: \"#9ae055\" },\n";

                            }
                            append2 = append2
                                    + "		]\n"
                                    + "	},\n"
                                    + "	{\n"
                                    + "		type: \"column\",\n"
                                    + "		name: \"Negative\",\n"
                                    + "		axisYType: \"secondary\",\n"
                                    + "		showInLegend: true,\n"
                                    + "              color : \"#ed5c5c\",       "
                                    + "		yValueFormatString: \"#,##0.# \",\n"
                                    + "		dataPoints: [\n";
                            for (Map.Entry<String, Integer> entry : NegativeperCountry.entrySet()) {
                                String key = entry.getKey();
                                Object value = entry.getValue();

                                append2 = append2
                                        + "			{ label: \"" + key + "\", y: " + value + ",color: \"#ed5c5c\" },\n";

                            }
                            append2 = append2
                                    + "		]\n"
                                    + "	}]\n"
                                    + "});\n"
                                    + "chartcountry" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ".render();\n"
                                    + "\n"
                                    + "function toggleDataSeriescountry" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "(e) {\n"
                                    + "	if (typeof (e.dataSeries.visible) === \"undefined\" || e.dataSeries.visible) {\n"
                                    + "		e.dataSeries.visible = false;\n"
                                    + "	} else {\n"
                                    + "		e.dataSeries.visible = true;\n"
                                    + "	}\n"
                                    + "	e.chartcountry" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ".render();\n"
                                    + "}"
                                    + "});});\n"
                                    + "</script>";

                            /////
                            append3 = append3 + "<div class='full-lenght' id=\"chartContainertype" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\" style=\"height: 370px; width: 100%;\"><img src='Css/graph-survey-platform-design-ux-interface-ramotion.gif' class='loading-gif'/></div>";
                            append3 = append3 + "<script>\n"
                                    + "$(document).ready( function() {\n"
                                    + "$('#accordionHotelStats" + Hotel.getHotelId() + "').on('show.bs.collapse', function (e) {" + "$(\".full-lenght\").html(\"<img src='Css/graph-survey-platform-design-ux-interface-ramotion.gif' class='loading-gif'/>\");" + "});"
                                    + "$('#accordionHotelStats" + Hotel.getHotelId() + "').on('shown.bs.collapse', function (e) {"
                                    + "$(\".loading-gif\").hide();"
                                    + "var charttype" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + " = new CanvasJS.Chart(\"chartContainertype" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\", {\n"
                                    + "	exportEnabled: true,\n"
                                    + "	animationEnabled: true,\n"
                                    + "	title:{\n"
                                    + "		text: \"Positive and Negative per reviewer Reviewer Type\"\n"
                                    + "	},\n"
                                    + "	subtitles: [{\n"
                                    + "		text: \"Click Legend to Hide or Unhide Data Series\"\n"
                                    + "	}], \n"
                                    + "	axisX: {\n"
                                    + "		title: \"Reviewer Type\"\n"
                                    + "	},\n"
                                    + "	axisY: {\n"
                                    + "		title: \"Positive - Count\",\n"
                                    + "		titleFontColor: \"#9ae055\",\n"
                                    + "		lineColor: \"#9ae055\",\n"
                                    + "		labelFontColor: \"#9ae055\",\n"
                                    + "		tickColor: \"#9ae055\"\n"
                                    + "	},\n"
                                    + "	axisY2: {\n"
                                    + "		title: \"Negative - Count\",\n"
                                    + "		titleFontColor: \"#ed5c5c\",\n"
                                    + "		lineColor: \"#ed5c5c\",\n"
                                    + "		labelFontColor: \"#ed5c5c\",\n"
                                    + "		tickColor: \"#ed5c5c\"\n"
                                    + "	},\n"
                                    + "	toolTip: {\n"
                                    + "		shared: true\n"
                                    + "	},\n"
                                    + "	legend: {\n"
                                    + "		cursor: \"pointer\",\n"
                                    + "		itemclick: toggleDataSeriestype" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\n"
                                    + "	},\n"
                                    + "	data: [{\n"
                                    + "		type: \"column\",\n"
                                    + "		name: \"Positive\",\n"
                                    + "		showInLegend: true,      \n"
                                    + "              color : \"#9ae055\",       "
                                    + "		yValueFormatString: \"#,##0.# \",\n"
                                    + "		dataPoints: [\n";
                            for (Map.Entry<String, Integer> entry : PositiveperReviewerType.entrySet()) {
                                String key = entry.getKey();
                                Object value = entry.getValue();

                                append3 = append3 + "			{ label: \"" + key + "\", y: " + value + ",color: \"#9ae055\" },\n";

                            }
                            append3 = append3
                                    + "		]\n"
                                    + "	},\n"
                                    + "	{\n"
                                    + "		type: \"column\",\n"
                                    + "		name: \"Negative\",\n"
                                    + "		axisYType: \"secondary\",\n"
                                    + "		showInLegend: true,\n"
                                    + "              color : \"#ed5c5c\",       "
                                    + "		yValueFormatString: \"#,##0.# \",\n"
                                    + "		dataPoints: [\n";
                            for (Map.Entry<String, Integer> entry : NegativeperReviewerType.entrySet()) {
                                String key = entry.getKey();
                                Object value = entry.getValue();

                                append3 = append3
                                        + "			{ label: \"" + key + "\", y: " + value + ",color: \"#ed5c5c\" },\n";

                            }
                            append3 = append3
                                    + "		]\n"
                                    + "	}]\n"
                                    + "});\n"
                                    + "charttype" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ".render();\n"
                                    + "\n"
                                    + "function toggleDataSeriestype" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "(e) {\n"
                                    + "	if (typeof (e.dataSeries.visible) === \"undefined\" || e.dataSeries.visible) {\n"
                                    + "		e.dataSeries.visible = false;\n"
                                    + "	} else {\n"
                                    + "		e.dataSeries.visible = true;\n"
                                    + "	}\n"
                                    + "	e.charttype" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + ".render();\n"
                                    + "}"
                                    + "});});\n"
                                    + "</script>";

                            Selected.get(0).select("#accordionHotelStats" + Hotel.getHotelId()).get(0).append("<div style='height:inherit' class=\"card card-trans card-hotel-"+ Hotel.getHotelId()+"\">\n"
                                    + "    <div class=\"card-header\"  role=\"tab\" id=\"headingOne" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\">\n"
                                    + "      <h5 class=\"mb-0 text-center\">\n"
                                    + "        <a class='tab-link-noncss' data-toggle=\"collapse\" data-parent=\"#accordionHotelStats" + Hotel.getHotelId() + "\" href=\"#collapseOne" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\">\n"
                                    + asp
                                    + "        </a>\n"
                                    + "<br><i class=\"fas fa-chevron-down\"></i>\n"
                                    + "\n"
                                    + ""
                                    + "      </h5>\n"
                                    + "    </div>\n"
                                    + "\n"
                                    + "    <div id=\"collapseOne" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"headingOne" + asp.replaceAll("[^A-Za-z0-9]", "") + Hotel.getHotelId() + "\">\n"
                                    + "      <div class=\"card-block\" style='width:100%'>\n"
                                    + append + "<hr>" + append2 + "<hr>" + append3 + "<hr>"
                                    + "      </div>\n"
                                    + "    </div>\n"
                                    + "  </div>");

                        }
                    }
                    ////////////////////////////////////////////////////////////////////////
                    //   The session personal search info      //////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////////
                    HttpSession session = request.getSession(true);
                    String month = (String) session.getAttribute("checkin_month");
                    String purpose = (String) session.getAttribute("sb_travel_purpose");
                    String adults = (String) session.getAttribute("group_adults");
                    String kids = (String) session.getAttribute("group_children");
                    String Type = null;
                    ///////////////////////////////////////////////////////////////////////
                    ///////////////////////////////////////////////////////////////////////
                    //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! /n month" + month + " purpose" + purpose + " adults=" + adults + " kids = " + kids);
                    if (month == null && purpose == null && adults == null && kids == null) {

                        Elements select = HotelL.select(".stats-container" + Hotel.getHotelId());
                        if (select.size() > 0) {
                            PositivepermonthForAll = addValues(PositivepermonthForAll, NegativepermonthForAll);
                            PositiveperReviewerTypeForAll = addValues(PositiveperReviewerTypeForAll, NegativeperReviewerTypeForAll);

                            List<Entry<String, Double>> PositiveAllMonth = getOrderedMaps(PositivepermonthForAll);

                            List<Entry<String, Double>> PositiveAllTypeRev = getOrderedMaps(PositiveperReviewerTypeForAll);
                            for (Entry<String, Double> entry : PositivepermonthForAll.entrySet()) {
                                // System.out.println(entry.getKey()+"_"+entry.getValue());
                            }
                            for (Entry<String, Double> entry : PositiveAllMonth) {
                                System.out.println(entry.getKey() + "/" + entry.getValue());
                            }
                            //HttpSession session = request.getSession(true);
                            // System.out.println(session.getAttribute("sb_travel_purpose") + "-----------------");
                            if (PositiveAllMonth.size() > 0 && PositiveAllMonth.get(0).getValue() > 0.0) {

                                select.get(0).append("<div class=\"alert alert-success alert-dismissable fade in show d-block\">\n"
                                        + "    <a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>\n"
                                        + "    Best Month to visit is <strong> " + PositiveAllMonth.get(0).getKey() + "</strong>"
                                        + "  </div>");
                            }
                            if (PositiveAllTypeRev.size() > 0 && PositiveAllTypeRev.get(0).getValue() > 0) {
                                select.get(0).append("<div class=\"alert alert-success alert-dismissable fade in show d-block\">\n"
                                        + "    <a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>\n"
                                        + "    Best suited for <strong>" + PositiveAllTypeRev.get(0).getKey() + "</strong>"
                                        + "  </div>");

                            }
                            if (PositiveAllTypeRev.size() > 0 && PositiveAllTypeRev.get(PositiveAllTypeRev.size() - 1).getValue() < 0.0) {
                                select.get(0).append("<div class=\"alert alert-danger alert-dismissable fade in show d-block\">\n"
                                        + "    <a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>\n"
                                        + "    Not the best choise for <strong> " + PositiveAllTypeRev.get(PositiveAllTypeRev.size() - 1).getKey() + "</strong>"
                                        + "  </div>");

                            }
                            if (PositiveAllMonth.size() > 0 && PositiveAllMonth.get(PositiveAllMonth.size() - 1).getValue() < 0.0) {

                                select.get(0).append("<div class=\"alert alert-danger alert-dismissable fade in show d-block\">\n"
                                        + "    <a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>\n"
                                        + "    Worst Month to visit is <strong> " + PositiveAllMonth.get(PositiveAllMonth.size() - 1).getKey() + "</strong>"
                                        + "  </div>");

                            }

                        }
                    } else {
                        if (Integer.parseInt(adults) < 2) {
                            Type = "Solo traveller";
                        } else if (adults != null && Integer.parseInt(adults) == 2 && Integer.parseInt(kids) == 0) {
                            Type = "Couple";
                        } else if (adults != null && Integer.parseInt(adults) == 2 && Integer.parseInt(kids) != 0 && Integer.parseInt(kids) > 1) {
                            Type = "Family with children";
                        } else if (adults != null && Integer.parseInt(adults) > 2 && Integer.parseInt(kids) == 0) {
                            Type = "Group";
                        } else {
                            Type = null;
                        }
                        System.out.println("Type of traveller = " + Type);
                        Elements select = HotelL.select(".stats-container" + Hotel.getHotelId());
                        if (select.size() > 0) {
                            PositivepermonthForAll = addValues(PositivepermonthForAll, NegativepermonthForAll);
                            PositiveperReviewerTypeForAll = addValues(PositiveperReviewerTypeForAll, NegativeperReviewerTypeForAll);

                            List<Entry<String, Double>> PositiveAllMonth = getOrderedMaps(PositivepermonthForAll);

                            List<Entry<String, Double>> PositiveAllTypeRev = getOrderedMaps(PositiveperReviewerTypeForAll);
                            for (Entry<String, Double> entry : PositivepermonthForAll.entrySet()) {
                                // System.out.println(entry.getKey()+"_"+entry.getValue());
                            }
                            for (Entry<String, Double> entry : PositiveAllMonth) {
                                System.out.println(entry.getKey() + "/" + entry.getValue());
                            }
                            //HttpSession session = request.getSession(true);
                            // System.out.println(session.getAttribute("sb_travel_purpose") + "-----------------");
                            if (PositiveAllMonth.size() > 0 && PositiveAllMonth.get(0).getValue() > 0.0) {
                                System.out.println("--------" + month + "'''''''''");
                                if (month != null && !"".equals(month)) {
                                    if ((getMonthForInt(Integer.parseInt(month) - 1).equalsIgnoreCase(PositiveAllMonth.get(0).getKey()))) {
                                        String addBanner = "<div style='    background-color: #077812;    color: #fff!important;' class=\"ribbon--outer ribbon__extra js_icon_over_photo\"><div style='left: 10;'class=\"ribbon js-fly-content-tooltip\" data-content-tooltip=\"<span class='ribbon--tooltip'>There are many positive reviews by visitors for the dates you picked ! </span>\">\n"
                                                + "Great score for " + getMonthForInt(Integer.parseInt(month) - 1)
                                                + "                 </div></div>";
                                        HotelL.prepend(addBanner);
                                    }
                                }
                                select.get(0).append("<div class=\"alert alert-success alert-dismissable fade in show d-block\">\n"
                                        + "    <a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>\n"
                                        + "    Best Month to visit is <strong> " + PositiveAllMonth.get(0).getKey() + "</strong>"
                                        + "  </div>");
                            }
                            if (PositiveAllTypeRev.size() > 0 && PositiveAllTypeRev.get(0).getValue() > 0) {
                                select.get(0).append("<div class=\"alert alert-success alert-dismissable fade in show d-block\">\n"
                                        + "    <a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>\n"
                                        + "    Best suited for <strong>" + PositiveAllTypeRev.get(0).getKey() + "</strong>"
                                        + "  </div>");

                            }
                            if (PositiveAllTypeRev.size() > 0 && PositiveAllTypeRev.get(PositiveAllTypeRev.size() - 1).getValue() < 0.0) {
                                select.get(0).append("<div class=\"alert alert-danger alert-dismissable fade in show d-block\">\n"
                                        + "    <a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>\n"
                                        + "    Not the best choise for <strong> " + PositiveAllTypeRev.get(PositiveAllTypeRev.size() - 1).getKey() + "</strong>"
                                        + "  </div>");

                            }
                            if (PositiveAllMonth.size() > 0 && PositiveAllMonth.get(PositiveAllMonth.size() - 1).getValue() < 0.0) {
                                if (month != null && !"".equals(month)) {
                                    System.err.println(PositiveAllMonth.get(PositiveAllMonth.size() - 1).getKey() + "----" + getMonthForInt(Integer.parseInt(month) - 1));
                                    if ((getMonthForInt(Integer.parseInt(month) - 1).equalsIgnoreCase(PositiveAllMonth.get(PositiveAllMonth.size() - 1).getKey()))) {
                                        String addBanner = "<div style='' class=\"ribbon--outer ribbon__extra js_icon_over_photo\"><div style='"
                                                + "    left: 10;background-color:ghostwhite;color: rgb(255, 100, 2)' class=\"ribbon js-fly-content-tooltip\" data-content-tooltip=\"<span  class='ribbon--tooltip'>There are many bad reviews by visitors for the dates you picked ! </span>\">\n"
                                                + "Bad score for " + getMonthForInt(Integer.parseInt(month) - 1)
                                                + "                 </div></div>";
                                        HotelL.prepend(addBanner);

                                    }
                                }
                                select.get(0).append("<div class=\"alert alert-danger alert-dismissable fade in show d-block\">\n"
                                        + "    <a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>\n"
                                        + "    Worst Month to visit is <strong> " + PositiveAllMonth.get(PositiveAllMonth.size() - 1).getKey() + "</strong>"
                                        + "  </div>");

                            }

                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException | ParseException ex) {
            Logger.getLogger(BringAspectsByHotelHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return elementsByClass;
    }

    public static String getMonthForInt(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    public static List<Entry<String, Double>> getOrderedMaps(HashMap map) {
        Set<Entry<String, Double>> set = map.entrySet();
        List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(set);
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
