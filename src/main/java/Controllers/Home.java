/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Beans.EventBean;
import Beans.HotelBean;
import Beans.HotelLocBean;
import Beans.SentenceSentBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Beans.UserBean;
import DAO.DaoComments;
import DAO.DaoHotels;
import DAO.DaoSentence;
import DAO.DaoUser;
import DAO.DaoWords;
import GoogleApi.GeoLoc;
import GoogleApi.PlacesApi;
import Handlers.AspectCommentsHandler;
import Handlers.CommentsAllHandler;
import Handlers.CommentsByBothFiltersHandler;
import Handlers.CommentsByTypeOfUserHandler;
import Handlers.CommentsBycountryHandler;
import Handlers.CommentsHandler;
import Handlers.EventHandler;
import Handlers.FacebookSignup;
import Handlers.LoginHandler;
import Handlers.SignUpHandler;
import Handlers.SortOptionsHandler;
import Handlers.TopicsPerHotelHandler;
import java.util.List;
import java.io.BufferedReader;
import SentimentAylien.AspectSentimentAnalysis;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceType;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author user1
 */
public class Home extends HttpServlet {

    private final DaoUser dao;
    private final DaoHotels daohotel;
    private final DaoWords daoe;
    private final DaoComments daoee;
    private final DaoSentence daoSen;
    private static final long serialVersionUID = 1L;
    static Set<String> nounPhrases = new HashSet<>();
    static Map<String, String> Results = new LinkedHashMap<>();

    static Map<String, Double> NegativeReviews = new LinkedHashMap<>();
    static Map<String, Double> PositiveReviews = new LinkedHashMap<>();

    public Home() {
        daoSen = new DaoSentence();
        dao = new DaoUser();
        daohotel = new DaoHotels();
        daoe = new DaoWords();
        daoee = new DaoComments();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        //handle facebook events!!

        if (request.getParameter("action").equals("EventsFB")) {

            BufferedReader reader = request.getReader();
            StringBuilder builder = new StringBuilder();
            String aux = "";

            while ((aux = reader.readLine()) != null) {
                builder.append(aux);
            }

            String text = builder.toString().replaceAll("%(?![0-9a-fA-F]{2})", "%25").replaceAll("\\+", "%2B");
            // System.out.println(URLDecoder.decode(text, StandardCharsets.UTF_8.toString()) + "------ ");
            EventHandler EventHand = new EventHandler();
            List<EventBean> GetEventsHandler = EventHand.GetEventsHandler(text);
            request.setAttribute("EventsList", GetEventsHandler);

        }

        //Find Lat and Long of hotels
        if (request.getParameter("action").equalsIgnoreCase("geoloc")) {
            GeoLoc geo = new GeoLoc();
            List<HotelBean> allHotels = daohotel.getAllHotels();
            for (HotelBean hotel : allHotels) {
                geo.GeoLoc(hotel, daohotel);
            }
        }

        //Ayline Sentiment Aspect Based
        if (request.getParameter("action").equalsIgnoreCase("startSentiment")) {
            List<SentenceSentBean> allComments = daoSen.getAllSentences();
            AspectSentimentAnalysis analyze = new AspectSentimentAnalysis();
            analyze.Analyze(allComments, daoSen);
        }

        //SignUp Handler
        if (request.getParameter("action").equalsIgnoreCase("SignUp")) {

            RequestDispatcher rd = request.getRequestDispatcher("/newjsp.jsp");
            SignUpHandler SignUp = new SignUpHandler();

            SignUp.Signup(request, dao, session);
            rd.forward(request, response);
        }

        //Sign in Hanlder
        if (request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase("signin")) {
            try {

                RequestDispatcher rd = request.getRequestDispatcher("/newjsp.jsp");
                LoginHandler Login = new LoginHandler();
                Login.Login(request, dao, session);
                rd.forward(request, response);
            } catch (IOException | ServletException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Facebook Login Handler
        if (request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase("FbLogin")) {
            BufferedReader reader = request.getReader();
            StringBuilder builder = new StringBuilder();
            String aux = "";

            while ((aux = reader.readLine()) != null) {
                builder.append(aux);
            }

            String text = builder.toString().replaceAll("%(?![0-9a-fA-F]{2})", "%25").replaceAll("\\+", "%2B");
            FacebookSignup fb = new FacebookSignup();

            UserBean User = fb.GetEventsHandler(text);
            if (User != null) {
                List<UserBean> userByEmail = dao.getUserByEmailAndPassword(User.getEmail(),User.getPass());
                if (userByEmail.isEmpty()) {
                    session.setAttribute("user", User);
                    session.removeAttribute("ErrorValidation-Fb");

                }else{
                    session.setAttribute("ErrorValidation-Fb", "There is no account with that information try sign up first!");
                }
            }
        }

        //Facebook SignUp Handler
        if (request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase("FbSignUp")) {

            BufferedReader reader = request.getReader();
            StringBuilder builder = new StringBuilder();
            String aux = "";

            while ((aux = reader.readLine()) != null) {
                builder.append(aux);
            }

            String text = builder.toString().replaceAll("%(?![0-9a-fA-F]{2})", "%25").replaceAll("\\+", "%2B");
            FacebookSignup fb = new FacebookSignup();
            UserBean User = fb.GetEventsHandler(text);
            if (User != null) {
                List<UserBean> userByEmail = dao.getUserByEmail(User.getEmail());
                if (userByEmail.isEmpty()) {

                    dao.addUser(User);
                    session.setAttribute("user", User);
                    session.removeAttribute("ErrorValidation-Fb");
                } else {
                    session.setAttribute("ErrorValidation-Fb", "There is an existing account with that information try sign in !");
                }
            }
        }

        //Log Out Handler
        if (request.getParameter("action").equalsIgnoreCase("Logout")) {
            session.invalidate();
        }

        //Places Google api Handler
        if (request.getParameter("action").equalsIgnoreCase("places")) {
            PlacesApi api = new PlacesApi();
            List<HotelLocBean> allHotelLoc = daohotel.getAllHotelLoc();

            try {
                api.PlacesNaer(allHotelLoc.get(0), PlaceType.CAFE);
            } catch (ApiException | InterruptedException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Hotels MArkers on Map
        if (request.getParameter("action").equalsIgnoreCase("mapMarkers")) {
            JSONObject json = new JSONObject();
            List<HotelBean> allHotelsbyCity = daohotel.getAllHotelsbyArea(request.getParameter("city"));
            int count = 0;
            JSONArray ja = new JSONArray();
            for (HotelBean Hotel : allHotelsbyCity) {
                HotelLocBean allHotelLocById = daohotel.getAllHotelLocById(Hotel.getHotelId());
                ObjectMapper mapper = new ObjectMapper();
                String jsonInString = mapper.writeValueAsString(allHotelLocById);
                String jsonInStringg = mapper.writeValueAsString(Hotel);
                JSONObject jsonn = new JSONObject(jsonInString);
                JSONObject jsonnn = new JSONObject(jsonInStringg);
                JSONObject combined = new JSONObject();
                combined.put("Location", jsonInString);
                combined.put("Info", jsonInStringg);

                ja.put(combined);
            }
            json.put("Hotels", ja);
            response.setContentType("application/json");
            response.getWriter().write(json.toString());

        }
        if (request.getParameter("action").equalsIgnoreCase("BringComments")) {
            CommentsHandler Handler = new CommentsHandler();
            //System.out.println(request.getParameter("urlhotel"));
            JSONObject json = Handler.bringcomments(request, daohotel, daoSen);

            //System.out.println(json.toString());
            
            response.setContentType("application/json");
            response.getWriter().write(json.toString());
        }
        if(request.getParameter("action").equalsIgnoreCase("BringCommentsForCountry")){
            //SELECT * FROM dbtestaki.com where HotelId = 9998 and comreviewercountry = 'Grecce';
            CommentsBycountryHandler Hanlder =new CommentsBycountryHandler();
            JSONObject json = Hanlder.bringCommentsByCountry(request, daohotel, daoSen);
            System.out.println(json.toString());
            response.setContentType("application/json");
            response.getWriter().write(json.toString());
        }
        if(request.getParameter("action").equalsIgnoreCase("BringCommentsForTypeOfUser")){
            //SELECT * FROM dbtestaki.com where HotelId = 9998 and comreviewertype = 'Solo traveller';
            CommentsByTypeOfUserHandler Hanlder =new CommentsByTypeOfUserHandler();
            JSONObject json = Hanlder.bringCommentsByTypeOfUserHandler(request, daohotel, daoSen);
            System.out.println(json.toString());
            response.setContentType("application/json");
            response.getWriter().write(json.toString());
        }
        if(request.getParameter("action").equalsIgnoreCase("BringSortOptions")){
            SortOptionsHandler Handler =new SortOptionsHandler();
            JSONObject json =Handler.bringSortOptions(request, daohotel, daoSen);
            //System.out.println(json.toString());
            response.setContentType("application/json");
            response.getWriter().write(json.toString());
        }
        if(request.getParameter("action").equalsIgnoreCase("BringTopicsHotel")){
            TopicsPerHotelHandler Handler =new TopicsPerHotelHandler();
            JSONObject json = Handler.bringTopics(request, daohotel, daoSen);
            response.setContentType("application/json");
            if(json!=null){
            response.getWriter().write(json.toString());
            }
        }
        if(request.getParameter("action").equalsIgnoreCase("BringCommentsBothFilters")){
            CommentsByBothFiltersHandler Handler =new CommentsByBothFiltersHandler();
            JSONObject json = Handler.bringCommentsByBothFilters(request, daohotel, daoSen);
            response.setContentType("application/json");
            if(json!=null){
                System.out.println(json.toString());
            response.getWriter().write(json.toString());
            }
        }
        if(request.getParameter("action").equalsIgnoreCase("BringCommentsBothAll")){
            CommentsAllHandler Handler =new CommentsAllHandler();
            JSONObject json = Handler.bringCommentsAll(request, daohotel, daoSen);
            response.setContentType("application/json");
            if(json!=null){
            response.getWriter().write(json.toString());
            }
        }
        if(request.getParameter("action").equalsIgnoreCase("AspecFilter")){
            try {
                AspectCommentsHandler Handler =new AspectCommentsHandler();
                JSONObject json = Handler.bringCommentsByAspects(request, daohotel, daoSen);
                response.setContentType("application/json");
                if(json!=null){
                    response.getWriter().write(json.toString());
                }
            } catch (SQLException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
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
