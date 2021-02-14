/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Beans.HotelBeanWithDiv;
import Beans.OptionAndWeight;
import DAO.DaoComments;
import DAO.DaoHotels;
import DAO.DaoSentence;
import DAO.DaoUser;
import DAO.DaoWords;
import Handlers.PreparaeDocument;
import Handlers.SortCriteriaHandler;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author user1
 */
public class UrlContoller extends HttpServlet {

    private final DaoUser dao;
    private final DaoHotels daohotel;
    private final DaoWords daoe;
    private final DaoComments daoee;
    private final DaoSentence daoSen;

    public UrlContoller() {
        daoSen = new DaoSentence();
        dao = new DaoUser();
        daohotel = new DaoHotels();
        daoe = new DaoWords();
        daoee = new DaoComments();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session1 = request.getSession(true);
        if (request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase("SortOnReviews")) {

            Map<String, String[]> parameterMap = request.getParameterMap();
            OptionAndWeight option = new OptionAndWeight();
            HashMap<String, String> map = new HashMap();
            String[] value = parameterMap.get("sort_based_option");
            if (value != null && value.length > 0) {
                for (String val : value) {

                    map.put(val, parameterMap.get("range-" + val.replaceAll("[^A-Za-z0-9]", ""))[0]);
                }
                option.setOptions(map);

                if (value.length > 0) {
                    session1.setAttribute("sort_based_option", option);

                } else {
                    session1.removeAttribute("sort_based_option");
                }
            } else {
                session1.removeAttribute("sort_based_option");
            }
        }
        if (request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase("DeleteOptionsSort")) {
            session1.removeAttribute("sort_based_option");

        }
        if (request.getParameter("parameterUrl") != null) {
            URIBuilder b = null;
            try {
                // System.out.println(request.getParameter("parameterUrl"));
                RequestDispatcher rd = request.getRequestDispatcher("/newjsp1.jsp");
                URL domain = new URL("https://booking.com" + request.getParameter("q").replaceAll("[\\t\\n\\r\\s]+", "") + "&unchecked_filter=out_of_stock");
                //System.out.println(domain.toExternalForm().trim());
                Document doc = Jsoup.connect(domain.toString().trim()).get();
                if (!doc.getElementsByClass("hotel_name_link").isEmpty()) {

                    String url = domain.toString().trim() + "&offset=0";
                    if (session1.getAttribute("sort_based_option") != null) {
                        OptionAndWeight Filtetrs = (OptionAndWeight) session1.getAttribute("sort_based_option");
                        if (Filtetrs != null && Filtetrs.getOptions().size() > 0) {

                            SortCriteriaHandler Handler = new SortCriteriaHandler();
                            HashMap<HotelBeanWithDiv, List<Map.Entry<String, Double>>> AnalyzeHotelsCriteria = Handler.AnalyzeHotelsCriteria(doc, url, daohotel, daoSen, request);
                            System.out.println("bhke");
                            doc = Handler.returnOutpuSorted(AnalyzeHotelsCriteria, daoSen, doc, Filtetrs.getOptions());

                        }
                    }
                }
                if (doc != null) {
                    PreparaeDocument prepare = new PreparaeDocument();
                    doc = prepare.preparedoc(doc, daohotel, daoSen, request);

                    request.setAttribute("parameterUrl", doc);
                    rd.forward(request, response);
                }
            } catch (SocketTimeoutException | UnknownHostException e) {
                RequestDispatcher rd = request.getRequestDispatcher("/newjsp1.jsp");
                URL domain = new URL("https://www.booking.com/");

                Document doc = Jsoup.connect(domain.toString().trim()).get();

                if (doc != null) {
                    PreparaeDocument prepare = new PreparaeDocument();
                    doc = prepare.preparedoc(doc, daohotel, daoSen, request);

                    request.setAttribute("parameterUrl", doc);
                    rd.forward(request, response);
                }
            }

        } else if (request.getParameter("form-sarch") != null) {
            URIBuilder b = null;
            RequestDispatcher rd = request.getRequestDispatcher("/newjsp1.jsp");
            try {

                b = new URIBuilder("https://booking.com/searchresults.en-gb.html?id=test");
                Map<String, String[]> parameterNames = request.getParameterMap();
                for (Map.Entry<String, String[]> entry : parameterNames.entrySet()) {
                    if (entry.getKey().equalsIgnoreCase("action") || entry.getKey().equalsIgnoreCase("sort_based_option")) {

                    } else {
                        session1.setAttribute(entry.getKey(), entry.getValue()[0]);
                        b.addParameter(entry.getKey(), entry.getValue()[0]);
                    }

                }

                URL Url = b.build().toURL();
                // System.out.println(Url);
                Document doc = Jsoup.connect(Url.toString() + "&unchecked_filter=out_of_stock").get();
                if (!doc.getElementsByClass("hotel_name_link").isEmpty()) {

                    String url = Url.toString().trim() + "&offset=0";

                    if (session1.getAttribute("sort_based_option") != null) {

                        OptionAndWeight Filtetrs = (OptionAndWeight) session1.getAttribute("sort_based_option");

                        if (Filtetrs != null && Filtetrs.getOptions().size() > 0) {
                            doc.getElementsByClass("sr_item").remove();
                            SortCriteriaHandler Handler = new SortCriteriaHandler();
                            HashMap<HotelBeanWithDiv, List<Map.Entry<String, Double>>> AnalyzeHotelsCriteria = Handler.AnalyzeHotelsCriteria(doc, url, daohotel, daoSen, request);
                            System.out.println("bhke");
                            doc = Handler.returnOutpuSorted(AnalyzeHotelsCriteria, daoSen, doc, Filtetrs.getOptions());
                        }
                    }
                }
                PreparaeDocument prepare = new PreparaeDocument();
                doc = prepare.preparedoc(doc, daohotel, daoSen, request);

                request.setAttribute("parameterUrl", doc);
                rd.forward(request, response);
            } catch (URISyntaxException ex) {
                Logger.getLogger(UrlContoller.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (request.getParameter("form-nav-reviews") != null) {
            URIBuilder b = null;
            RequestDispatcher rd = request.getRequestDispatcher("/newjsp1.jsp");
            try {
                String tempurl = "https://booking.com/" + request.getParameter("hotelsurl").replace("hotel", "reviews") + "&unchecked_filter=out_of_stock";
                int lastIndexOf = tempurl.lastIndexOf("/");
                String endurl = tempurl.substring(0, lastIndexOf) + "/hotel/" + tempurl.substring(lastIndexOf, tempurl.length());
                b = new URIBuilder(endurl);
                Map<String, String[]> parameterNames = request.getParameterMap();

                for (Map.Entry<String, String[]> entry : parameterNames.entrySet()) {
                    if (entry.getKey().equalsIgnoreCase("action") || entry.getKey().equalsIgnoreCase("sort_based_option")) {

                    } else {
                        session1.setAttribute(entry.getKey(), entry.getValue()[0]);
                        b.addParameter(entry.getKey(), entry.getValue()[0]);
                    }

                }

                URL Url = b.build().toURL();
                System.out.println(Url);
                Document doc = Jsoup.connect(Url.toString()).get();
                PreparaeDocument prepare = new PreparaeDocument();
                if (session1.getAttribute("sort_based_option") != null) {
                    OptionAndWeight Filtetrs = (OptionAndWeight) session1.getAttribute("sort_based_option");
                    if (Filtetrs != null && Filtetrs.getOptions().size() > 0) {
                        doc.getElementsByClass("sr_item").remove();
                        SortCriteriaHandler Handler = new SortCriteriaHandler();
                        HashMap<HotelBeanWithDiv, List<Map.Entry<String, Double>>> AnalyzeHotelsCriteria = Handler.AnalyzeHotelsCriteria(doc, Url.toString(), daohotel, daoSen, request);
                        System.out.println("bhke");
                        doc = Handler.returnOutpuSorted(AnalyzeHotelsCriteria, daoSen, doc, Filtetrs.getOptions());
                    }
                }
                doc = prepare.preparedoc(doc, daohotel, daoSen, request);

                request.setAttribute("parameterUrl", doc);
                rd.forward(request, response);

            } catch (URISyntaxException ex) {
                Logger.getLogger(UrlContoller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
