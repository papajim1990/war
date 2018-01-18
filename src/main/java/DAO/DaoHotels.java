/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author user1
 */
import Beans.CommentBean;
import Beans.FacilityBean;
import Beans.HighlightsHotel;
import Beans.HoteOverallScoreFeautures;
import Beans.HotelBean;
import Beans.HotelLocBean;
import Beans.PagerCommentsRangeBean;
import Beans.SortOptionCountryOfUserBean;
import Beans.SortOptionTypeOfUserBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectToDb;
import static java.lang.System.exit;
import java.util.HashMap;

public class DaoHotels {

    private Connection conn;

    public DaoHotels() {
        conn = ConnectToDb.getConnection();
    }

    public void addHotel(HotelBean hotel) {
        try {
            String query = "insert into Hotels (HotelName,HotelAdress, HotelCountry,HotelCity,HotelTk,HotelScore,HotelScoreString,DateCreated,UrlHotel,Area)  values (?,?,?,?,?,?,?,?,?,?) ";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, hotel.getHoteName());
            preparedStatement.setString(2, hotel.getHotelAdress());
            preparedStatement.setString(3, hotel.getHotelCountry());
            preparedStatement.setString(4, hotel.getHotelCity());
            preparedStatement.setString(5, hotel.getHotelTk());
            preparedStatement.setString(6, hotel.getHotelScore());
            preparedStatement.setString(7, hotel.getHotelScoreString());
            preparedStatement.setDate(8, hotel.getDate());
            preparedStatement.setString(9, hotel.getHotelUrl());
            preparedStatement.setString(10, hotel.getArea());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addHotelLoc(HotelLocBean hotel) {
        try {
            String query = "insert into hotellocation ( idHotel , lat , Longi )  values (?,?,?) ";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, hotel.getHotelId());
                preparedStatement.setString(2, String.valueOf(hotel.getHoteLat()));
                preparedStatement.setString(3, String.valueOf(hotel.getHoteLong()));

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    public List<HotelLocBean> getAllHotelLoc() {
        List<HotelLocBean> Hotels = new ArrayList<HotelLocBean>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dbtestaki.hotellocation;");
            while (resultSet.next()) {
                HotelLocBean Hotel = new HotelLocBean();
                Hotel.setHotelId(resultSet.getInt("idHotel"));
                Hotel.setHoteLat(resultSet.getDouble("lat"));
                Hotel.setHoteLong(resultSet.getDouble("Longi"));
                Hotels.add(Hotel);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Hotels;
    }

    public HotelLocBean getAllHotelLocById(int idHotel) {
        List<HotelLocBean> Hotels = new ArrayList<HotelLocBean>();
        HotelLocBean Hotel = new HotelLocBean();
        try {
            String query = "SELECT * FROM dbtestaki.hotellocation where idHotel = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, idHotel);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Hotel.setHotelId(resultSet.getInt("idHotel"));
                Hotel.setHoteLat(resultSet.getDouble("lat"));
                Hotel.setHoteLong(resultSet.getDouble("Longi"));

            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Hotel;
    }

    public void updateHotel(HotelBean hotel) {
        try {
            String query = "update Hotels set HotelAdress=?,HotelCountry=?,HotelCity=?,HotelTk=?,HotelScore=?,HotelScoreString=?, DateCreated=?,UrlHotel=?,Area=? where HotelName=?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, hotel.getHotelAdress());
            preparedStatement.setString(2, hotel.getHotelCountry());
            preparedStatement.setString(3, hotel.getHotelCity());
            preparedStatement.setString(4, hotel.getHotelTk());
            preparedStatement.setString(5, hotel.getHotelScore());
            preparedStatement.setString(6, hotel.getHotelScoreString());

            preparedStatement.setDate(7, hotel.getDate());
            preparedStatement.setString(8, hotel.getHotelUrl());
            preparedStatement.setString(9, hotel.getArea());

            preparedStatement.setString(10, hotel.getHoteName());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<HotelBean> getAllHotels() {
        List<HotelBean> Hotels = new ArrayList<HotelBean>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dbtestaki.hotels;");
            while (resultSet.next()) {
                HotelBean Hotel = new HotelBean();
                Hotel.setHotelId(resultSet.getInt("idHotels"));
                Hotel.setHotelName(resultSet.getString("HotelName"));
                Hotel.setHotelAdress(resultSet.getString("HotelAdress"));
                Hotel.setHotelCountry(resultSet.getString("HotelCountry"));
                Hotel.setHotelCity(resultSet.getString("HotelCity"));
                Hotel.setHotelAdress(resultSet.getString("HotelAdress"));
                Hotel.setHotelCountry(resultSet.getString("HotelCountry"));
                Hotel.setHotelTk(resultSet.getString("HotelTk"));
                Hotel.setHotelScoreString(resultSet.getString("HotelScore"));
                Hotel.setHotelScoreString(resultSet.getString("HotelScoreString"));
                Hotel.setHotelScoreString(resultSet.getString("DateCreated"));
                Hotel.setHotelUrl(resultSet.getString("UrlHotel"));
                Hotel.setArea(resultSet.getString("Area"));
                Hotels.add(Hotel);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Hotels;
    }

    public List<HotelBean> getAllHotelsbyCity(String HotelLocation) {
        List<HotelBean> Hotels = new ArrayList<HotelBean>();
        try {
            String query = "SELECT * FROM dbtestaki.hotels where HotelCountry like ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, HotelLocation);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                HotelBean Hotel = new HotelBean();
                Hotel.setHotelId(resultSet.getInt("idHotels"));
                Hotel.setHotelName(resultSet.getString("HotelName"));
                Hotel.setHotelAdress(resultSet.getString("HotelAdress"));
                Hotel.setHotelCountry(resultSet.getString("HotelCountry"));
                Hotel.setHotelCity(resultSet.getString("HotelCity"));
                Hotel.setHotelAdress(resultSet.getString("HotelAdress"));
                Hotel.setHotelCountry(resultSet.getString("HotelCountry"));
                Hotel.setHotelTk(resultSet.getString("HotelTk"));
                Hotel.setHotelScoreString(resultSet.getString("HotelScore"));
                Hotel.setHotelScoreString(resultSet.getString("HotelScoreString"));
                Hotel.setHotelScoreString(resultSet.getString("DateCreated"));
                Hotel.setHotelUrl(resultSet.getString("UrlHotel"));
                Hotel.setArea(resultSet.getString("Area"));
                Hotels.add(Hotel);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Hotels;
    }
    public List<HotelBean> getAllHotelsbyArea(String HotelLocation) {
        List<HotelBean> Hotels = new ArrayList<HotelBean>();
        try {
            String query = "SELECT * FROM dbtestaki.hotels where Area like ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "%"+HotelLocation+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                HotelBean Hotel = new HotelBean();
                Hotel.setHotelId(resultSet.getInt("idHotels"));
                Hotel.setHotelName(resultSet.getString("HotelName"));
                Hotel.setHotelAdress(resultSet.getString("HotelAdress"));
                Hotel.setHotelCountry(resultSet.getString("HotelCountry"));
                Hotel.setHotelCity(resultSet.getString("HotelCity"));
                Hotel.setHotelAdress(resultSet.getString("HotelAdress"));
                Hotel.setHotelCountry(resultSet.getString("HotelCountry"));
                Hotel.setHotelTk(resultSet.getString("HotelTk"));
                Hotel.setHotelScoreString(resultSet.getString("HotelScore"));
                Hotel.setHotelScoreString(resultSet.getString("HotelScoreString"));
                Hotel.setHotelScoreString(resultSet.getString("DateCreated"));
                Hotel.setHotelUrl(resultSet.getString("UrlHotel"));
                Hotel.setArea(resultSet.getString("Area"));
                Hotels.add(Hotel);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Hotels;
    }

    public HotelBean getAllHotelsbyUrl(String Url) {
        HotelBean Hotel = new HotelBean();
        try {
            String query = "SELECT * FROM dbtestaki.hotels where UrlHotel like ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "%" + Url + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Hotel.setHotelId(resultSet.getInt("idHotels"));
                Hotel.setHotelName(resultSet.getString("HotelName"));
                Hotel.setHotelAdress(resultSet.getString("HotelAdress"));
                Hotel.setHotelCountry(resultSet.getString("HotelCountry"));
                Hotel.setHotelCity(resultSet.getString("HotelCity"));
                Hotel.setHotelAdress(resultSet.getString("HotelAdress"));
                Hotel.setHotelCountry(resultSet.getString("HotelCountry"));
                Hotel.setHotelTk(resultSet.getString("HotelTk"));
                Hotel.setHotelScoreString(resultSet.getString("HotelScore"));
                Hotel.setHotelScoreString(resultSet.getString("HotelScoreString"));
                Hotel.setHotelScoreString(resultSet.getString("DateCreated"));
                Hotel.setHotelUrl(resultSet.getString("UrlHotel"));
                Hotel.setArea(resultSet.getString("Area"));

            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Hotel;
    }

    public List<CommentBean> getCommentsbyHotelName(String HotelName) {
        List<CommentBean> Comments = new ArrayList<CommentBean>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from com where Hotel = '" + HotelName + "'");
            while (resultSet.next()) {

                CommentBean Comment = new CommentBean();
                Comment.setCommentId(resultSet.getInt("idcom"));
                Comment.setCommentBodyPos(resultSet.getString("compositive"));
                Comment.setCommentBodyNeg(resultSet.getString("comnegative"));
                Comment.setCommentReviwerType(resultSet.getString("comreviewertype"));
                Comment.setCommentReviwerVacType(resultSet.getString("comreviewervactype"));
                Comment.setDaysStayed(resultSet.getString("daysstayed"));
                Comment.setCommentCountry(resultSet.getString("comreviewercountry"));
                Comment.setHotelHotelid(resultSet.getInt("HotelId"));
                Comment.setDateReview(resultSet.getString("reviewdate"));

                Comment.setHotelHotelid(resultSet.getInt("HotelId"));

                Comments.add(Comment);

                Comments.add(Comment);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }

    public List<CommentBean> getAllComments() {
        List<CommentBean> Comments = new ArrayList<CommentBean>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from com ");
            while (resultSet.next()) {
                CommentBean Comment = new CommentBean();
                Comment.setCommentId(resultSet.getInt("idcom"));
                Comment.setCommentBodyPos(resultSet.getString("compositive"));
                Comment.setCommentBodyNeg(resultSet.getString("comnegative"));
                Comment.setCommentReviwerType(resultSet.getString("comreviewertype"));
                Comment.setCommentReviwerVacType(resultSet.getString("comreviewervactype"));
                Comment.setDaysStayed(resultSet.getString("daysstayed"));
                Comment.setCommentCountry(resultSet.getString("comreviewercountry"));
                Comment.setHotelHotelid(resultSet.getInt("HotelId"));
                Comment.setDateReview(resultSet.getString("reviewdate"));

                Comment.setHotelHotelid(resultSet.getInt("HotelId"));

                Comments.add(Comment);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }

    public List<CommentBean> getAllCommentsByHotelId(int idhotel) {
        List<CommentBean> Comments = new ArrayList<CommentBean>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from com where HotelId = " + idhotel);
            while (resultSet.next()) {
                CommentBean Comment = new CommentBean();
                Comment.setCommentId(resultSet.getInt("idcom"));
                Comment.setCommentBodyPos(resultSet.getString("compositive"));
                Comment.setCommentBodyNeg(resultSet.getString("comnegative"));
                Comment.setCommentReviwerType(resultSet.getString("comreviewertype"));
                Comment.setCommentReviwerVacType(resultSet.getString("comreviewervactype"));
                Comment.setDaysStayed(resultSet.getString("daysstayed"));
                Comment.setCommentCountry(resultSet.getString("comreviewercountry"));
                Comment.setHotelHotelid(resultSet.getInt("HotelId"));
                Comment.setDateReview(resultSet.getString("reviewdate"));

                Comment.setHotelHotelid(resultSet.getInt("HotelId"));

                Comments.add(Comment);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }

    public void deleteHotel(int Hotelid) {
        try {
            String query = "delete from Hotels where idHotels=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, Hotelid);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addComment(CommentBean Comment) {
        try {
            String query = "insert into com (HotelId,compositive,comnegative,comreviewertype,comreviewervactype,daysstayed,roomtype,comreviewercountry,reviewdate)  values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, Comment.getHoteid());
            preparedStatement.setString(2, Comment.getCommentBodyPos());
            preparedStatement.setString(3, Comment.getCommentBodyNeg());
            preparedStatement.setString(4, Comment.getCommentReviwerType());
            preparedStatement.setString(5, Comment.getCommentReviwerVacType());
            preparedStatement.setString(6, Comment.getDaysStayed());
            preparedStatement.setString(7, "");
            preparedStatement.setString(8, Comment.getCommentCountry());
            preparedStatement.setString(9, Comment.getDateReview());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFacility(FacilityBean Comment) {
        try {
            String query = "insert into facilitieschecklist (Header,facilityitem,idHotel)  values (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(3, Comment.getFacilityBeanid());
            preparedStatement.setString(1, Comment.getFacilityBeanHeader());
            preparedStatement.setString(2, Comment.getFacilityBeanItem());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatecomment(CommentBean Comment) {
        try {
            String query = "update com set ReviewScore=? , ReviewTitle=? where Hotel=? and BodyCommentPos=? and BodyCommentNeg=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDouble(1, Comment.getReviewScore());
            preparedStatement.setString(2, Comment.getReviewTitle());
            preparedStatement.setInt(3, Comment.getHoteid());
            preparedStatement.setString(4, Comment.getCommentBodyPos());
            preparedStatement.setString(5, Comment.getCommentBodyNeg());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getHotelId(HotelBean Hotel) {
        int id = 0;

        try {
            String query = "select idHotels from Hotels where HotelName=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, Hotel.getHoteName());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("idhotels");
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;

    }

    public HotelBean getHotelbyId(int id) {
        HotelBean Hotel = new HotelBean();
        try {

            String query = "select * from Hotels where idHotels=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Hotel.setHotelId(resultSet.getInt("idHotels"));
                Hotel.setHotelName(resultSet.getString("HotelName"));
                Hotel.setHotelAdress(resultSet.getString("HotelAdress"));
                Hotel.setHotelCountry(resultSet.getString("HotelCountry"));
                Hotel.setHotelCity(resultSet.getString("HotelCity"));
                Hotel.setHotelAdress(resultSet.getString("HotelAdress"));
                Hotel.setHotelCountry(resultSet.getString("HotelCountry"));
                Hotel.setHotelTk(resultSet.getString("HotelTk"));
                Hotel.setHotelScoreString(resultSet.getString("HotelScore"));
                Hotel.setHotelScoreString(resultSet.getString("HotelScoreString"));
                Hotel.setHotelScoreString(resultSet.getString("DateCreated"));
                Hotel.setArea(resultSet.getString("Area"));

            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Hotel;

    }

    public void deleteCommentFromid(int idcomment) {
        try {
            String query = "delete  from comments where idComments=? ";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, idcomment);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteduplicateComment() {
        try {
            String query = "delete* from comments where BodyComment  ";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addHigh(HighlightsHotel high) {
        try {
            String query = "insert into higlights (HotelName,title)  values (?,?) ";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, high.getHoteName());
            preparedStatement.setString(2, high.gettitle());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFeat(HoteOverallScoreFeautures feat) {
        try {
            String query = "insert into FeauteuresScore (HotelName,title, score)  values (?,?,?) ";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, feat.getHoteName());
            preparedStatement.setString(2, feat.gettitle());
            preparedStatement.setDouble(3, feat.getscore());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CommentBean> getAllCommentsTest() {
        List<CommentBean> Comments = new ArrayList<CommentBean>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from comments where ReviewTitle IS NULL");
            while (resultSet.next()) {
                CommentBean Comment = new CommentBean();
                Comment.setCommentId(resultSet.getInt("idComments"));
                Comment.setCommentBodyPos(resultSet.getString("BodyCommentPos"));
                Comment.setCommentBodyNeg(resultSet.getString("BodyCommentNeg"));
                Comment.setCommentCountry(resultSet.getString("ReviwerCountry"));
                Comment.setCommentReviwerType(resultSet.getString("ReviwerType"));
                Comment.setCommentReviwerVacType(resultSet.getString("ReviwerVacType"));
                Comment.setHotelReviewTitle(resultSet.getString("ReviewTitle"));
                Comment.setHotelReviewScore(resultSet.getDouble("ReviewScore"));

                Comment.setHotelHotelid(resultSet.getInt("Hotel"));

                Comments.add(Comment);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }

    public List<SortOptionTypeOfUserBean> getAllSortUserTypeOptions(int idhotel) {
        List<SortOptionTypeOfUserBean> Comments = new ArrayList<SortOptionTypeOfUserBean>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT comreviewertype ,count(*) as count FROM dbtestaki.com where Hotelid = " + idhotel + " group by HotelId,comreviewertype ;");
            while (resultSet.next()) {
                SortOptionTypeOfUserBean Comment = new SortOptionTypeOfUserBean();
                Comment.setCommentCountReviwerType(resultSet.getInt("count"));
                Comment.setCommentReviwerType(resultSet.getString("comreviewertype"));

                Comments.add(Comment);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }

    public List<SortOptionCountryOfUserBean> getAllSortCountryOfUser(int idhotel) {
        List<SortOptionCountryOfUserBean> Comments = new ArrayList<SortOptionCountryOfUserBean>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT comreviewercountry ,count(*) as count FROM dbtestaki.com where Hotelid = " + idhotel + " group by HotelId,comreviewercountry ;");
            while (resultSet.next()) {
                SortOptionCountryOfUserBean Comment = new SortOptionCountryOfUserBean();
                Comment.setCountCommentCountry(resultSet.getInt("count"));
                Comment.setCommentCountry(resultSet.getString("comreviewercountry"));

                Comments.add(Comment);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }

    public List<PagerCommentsRangeBean> getRangeofCommentIds(int idhotel) {
        List<PagerCommentsRangeBean> Comments = new ArrayList<PagerCommentsRangeBean>();
        try {
            Statement statement = conn.createStatement();
            ;
            ResultSet resultSet = statement.executeQuery("SELECT min(idcom) as start,max(idcom) as end FROM com where HotelId = " + idhotel + " group by HotelId ;");
            while (resultSet.next()) {
                PagerCommentsRangeBean Comment = new PagerCommentsRangeBean();
                Comment.setstart(resultSet.getInt("start"));
                Comment.setend(resultSet.getInt("end"));

                Comments.add(Comment);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }

    public List<CommentBean> getAllCommentsByHotelIdAndLimit(int hotelId, String parameter, String parameter0) {
        List<CommentBean> Comments = new ArrayList<CommentBean>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select *  from com  where HotelId = " + hotelId + " and (idcom BETWEEN " + parameter + " AND " + parameter0 + " )");
            while (resultSet.next()) {
                CommentBean Comment = new CommentBean();
                Comment.setCommentId(resultSet.getInt("idcom"));
                Comment.setCommentBodyPos(resultSet.getString("compositive"));
                Comment.setCommentBodyNeg(resultSet.getString("comnegative"));
                Comment.setCommentReviwerType(resultSet.getString("comreviewertype"));
                Comment.setCommentReviwerVacType(resultSet.getString("comreviewervactype"));
                Comment.setDaysStayed(resultSet.getString("daysstayed"));
                Comment.setCommentCountry(resultSet.getString("comreviewercountry"));
                Comment.setHotelHotelid(resultSet.getInt("HotelId"));
                Comment.setDateReview(resultSet.getString("reviewdate"));

                Comment.setHotelHotelid(resultSet.getInt("HotelId"));

                Comments.add(Comment);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }

    public List<CommentBean> getAllCommentsByHotelIdAndCountryLimit(int hotelId, String Country, int parameter, int parameter0) {
        List<CommentBean> Comments = new ArrayList<CommentBean>();
        try {

            String query = "select *  from com  where HotelId = ? and comreviewercountry like ? LIMIT ? OFFSET ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setString(2, Country);
            preparedStatement.setInt(3, parameter);
            preparedStatement.setInt(4, parameter0);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CommentBean Comment = new CommentBean();
                Comment.setCommentId(resultSet.getInt("idcom"));
                Comment.setCommentBodyPos(resultSet.getString("compositive"));
                Comment.setCommentBodyNeg(resultSet.getString("comnegative"));
                Comment.setCommentReviwerType(resultSet.getString("comreviewertype"));
                Comment.setCommentReviwerVacType(resultSet.getString("comreviewervactype"));
                Comment.setDaysStayed(resultSet.getString("daysstayed"));
                Comment.setCommentCountry(resultSet.getString("comreviewercountry"));
                Comment.setHotelHotelid(resultSet.getInt("HotelId"));
                Comment.setDateReview(resultSet.getString("reviewdate"));

                Comment.setHotelHotelid(resultSet.getInt("HotelId"));

                Comments.add(Comment);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }

    public List<CommentBean> getAllCommentsByHotelIdAndTypeOfUserLimit(int hotelId, String Country, int parameter, int parameter0) {
        List<CommentBean> Comments = new ArrayList<CommentBean>();
        try {

            String query = "select *  from com  where HotelId = ? and comreviewertype like ? LIMIT ? OFFSET ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setString(2, Country);
            preparedStatement.setInt(3, parameter);
            preparedStatement.setInt(4, parameter0);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CommentBean Comment = new CommentBean();
                Comment.setCommentId(resultSet.getInt("idcom"));
                Comment.setCommentBodyPos(resultSet.getString("compositive"));
                Comment.setCommentBodyNeg(resultSet.getString("comnegative"));
                Comment.setCommentReviwerType(resultSet.getString("comreviewertype"));
                Comment.setCommentReviwerVacType(resultSet.getString("comreviewervactype"));
                Comment.setDaysStayed(resultSet.getString("daysstayed"));
                Comment.setCommentCountry(resultSet.getString("comreviewercountry"));
                Comment.setHotelHotelid(resultSet.getInt("HotelId"));
                Comment.setDateReview(resultSet.getString("reviewdate"));

                Comment.setHotelHotelid(resultSet.getInt("HotelId"));

                Comments.add(Comment);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }

    public List<CommentBean> getAllCommentsByHotelIdAndBothFilters(int hotelId, String Country, String TypeOfUser, int parameter, int parameter0) {
        List<CommentBean> Comments = new ArrayList<CommentBean>();
        try {

            String query = "select *  from com  where HotelId = ? and comreviewertype like ? and comreviewercountry like ? LIMIT ? OFFSET ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setString(2, TypeOfUser);
            preparedStatement.setString(3, Country);
            preparedStatement.setInt(4, parameter);
            preparedStatement.setInt(5, parameter0);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CommentBean Comment = new CommentBean();
                Comment.setCommentId(resultSet.getInt("idcom"));
                Comment.setCommentBodyPos(resultSet.getString("compositive"));
                Comment.setCommentBodyNeg(resultSet.getString("comnegative"));
                Comment.setCommentReviwerType(resultSet.getString("comreviewertype"));
                Comment.setCommentReviwerVacType(resultSet.getString("comreviewervactype"));
                Comment.setDaysStayed(resultSet.getString("daysstayed"));
                Comment.setCommentCountry(resultSet.getString("comreviewercountry"));
                Comment.setHotelHotelid(resultSet.getInt("HotelId"));
                Comment.setDateReview(resultSet.getString("reviewdate"));

                Comment.setHotelHotelid(resultSet.getInt("HotelId"));

                Comments.add(Comment);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }
    public int getCountByHotelIdAndBothFilters(int hotelId, String Country, String TypeOfUser) {
        int Total=0;
        try {

            String query = "select count(*) as count  from com  where HotelId = ? and comreviewertype like ? and comreviewercountry like ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setString(2, Country);
            preparedStatement.setString(3, TypeOfUser);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Total =resultSet.getInt("count");
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Total;
    }
    public List<CommentBean> getCommentsAllByHotelId(int hotelId, int parameter, int parameter0) {
        List<CommentBean> Comments = new ArrayList<CommentBean>();
        try {

            String query = "select *  from com  where HotelId = ?  LIMIT ? OFFSET ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
           
            preparedStatement.setInt(2, parameter);
            preparedStatement.setInt(3, parameter0);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CommentBean Comment = new CommentBean();
                Comment.setCommentId(resultSet.getInt("idcom"));
                Comment.setCommentBodyPos(resultSet.getString("compositive"));
                Comment.setCommentBodyNeg(resultSet.getString("comnegative"));
                Comment.setCommentReviwerType(resultSet.getString("comreviewertype"));
                Comment.setCommentReviwerVacType(resultSet.getString("comreviewervactype"));
                Comment.setDaysStayed(resultSet.getString("daysstayed"));
                Comment.setCommentCountry(resultSet.getString("comreviewercountry"));
                Comment.setHotelHotelid(resultSet.getInt("HotelId"));
                Comment.setDateReview(resultSet.getString("reviewdate"));

                Comment.setHotelHotelid(resultSet.getInt("HotelId"));

                Comments.add(Comment);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }
    public int getCountByHotelIdAndAll(int hotelId) {
        int Total=0;
        try {

            String query = "select count(*) as count  from com where HotelId = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
            
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Total =resultSet.getInt("count");
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Total;
    }
    public List<CommentBean> getAlcommentsByAspectOptions(int hotelId,String[] options, int parameter, int parameter0) throws SQLException {
        List<CommentBean> Comments = new ArrayList<CommentBean>();
        try {
            String query="";
            
            
        if(options.length>1){
            String start  ="Select * from com where idcom in(Select distinct idComment FROM dbtestaki.aspectssentence inner join sentnecesentiment on sentnecesentiment.idSentence = dbtestaki.aspectssentence.idSentence where IdHotel =? and ( ";
            String mid="";
            int count=1;
            for(String option: options){ 
                
                if(count<options.length){
                mid=mid+" aspectssentence.Aspect like ? || ";
                }else {
                    mid=mid+" aspectssentence.Aspect like ? )) LIMIT ? OFFSET ? ;";
                }
                count++;
            }
                query=start+mid;
                System.out.println(query);
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        int counter=1;
        preparedStatement.setInt(1, hotelId);
        for(String option: options){
            counter++;
            
            preparedStatement.setString(counter, option);
            
            
        }
        preparedStatement.setInt(counter+1, parameter);
            preparedStatement.setInt(counter+2, parameter0);
        ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CommentBean Comment = new CommentBean();
                Comment.setCommentId(resultSet.getInt("idcom"));
                Comment.setCommentBodyPos(resultSet.getString("compositive"));
                Comment.setCommentBodyNeg(resultSet.getString("comnegative"));
                Comment.setCommentReviwerType(resultSet.getString("comreviewertype"));
                Comment.setCommentReviwerVacType(resultSet.getString("comreviewervactype"));
                Comment.setDaysStayed(resultSet.getString("daysstayed"));
                Comment.setCommentCountry(resultSet.getString("comreviewercountry"));
                Comment.setHotelHotelid(resultSet.getInt("HotelId"));
                Comment.setDateReview(resultSet.getString("reviewdate"));

                Comment.setHotelHotelid(resultSet.getInt("HotelId"));

                Comments.add(Comment);
            }
            resultSet.close();
            preparedStatement.close();
        }
        else{
            query ="Select * from com where idcom in(Select distinct idComment FROM dbtestaki.aspectssentence inner join sentnecesentiment on sentnecesentiment.idSentence = dbtestaki.aspectssentence.idSentence where IdHotel =? and aspectssentence.Aspect like ? ) LIMIT ? OFFSET ?;";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setString(2, options[0]);
            preparedStatement.setInt(3, parameter);
            preparedStatement.setInt(4, parameter0);
        ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CommentBean Comment = new CommentBean();
                Comment.setCommentId(resultSet.getInt("idcom"));
                Comment.setCommentBodyPos(resultSet.getString("compositive"));
                Comment.setCommentBodyNeg(resultSet.getString("comnegative"));
                Comment.setCommentReviwerType(resultSet.getString("comreviewertype"));
                Comment.setCommentReviwerVacType(resultSet.getString("comreviewervactype"));
                Comment.setDaysStayed(resultSet.getString("daysstayed"));
                Comment.setCommentCountry(resultSet.getString("comreviewercountry"));
                Comment.setHotelHotelid(resultSet.getInt("HotelId"));
                Comment.setDateReview(resultSet.getString("reviewdate"));

                Comment.setHotelHotelid(resultSet.getInt("HotelId"));

                Comments.add(Comment);
            }
            resultSet.close();
            preparedStatement.close();
        }
            
            
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }
    public int getCountcommentsByAspectOptions(int hotelId,String[] options) throws SQLException {
        int Total =0;
        try {
            String query="";
            
            
        if(options.length>1){
            String start  ="Select count(*) as count from com where idcom in(Select distinct idComment FROM dbtestaki.aspectssentence inner join sentnecesentiment on sentnecesentiment.idSentence = dbtestaki.aspectssentence.idSentence where IdHotel =? and ( ";
            String mid="";
            int count=1;
            for(String option: options){ 
                
                if(count<options.length){
                mid=mid+" aspectssentence.Aspect like ? || ";
                }else {
                    mid=mid+" aspectssentence.Aspect like ? ));";
                }
                count++;
            }
                query=start+mid;
                System.out.println(query);
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        int counter=1;
        preparedStatement.setInt(1, hotelId);
        for(String option: options){
            counter++;
            
            preparedStatement.setString(counter, option);
            
            
        }
       
        ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Total=resultSet.getInt("count");
            }
            resultSet.close();
            preparedStatement.close();
        }
        else{
            query ="Select count(*) as count from com where idcom in(Select distinct idComment FROM dbtestaki.aspectssentence inner join sentnecesentiment on sentnecesentiment.idSentence = dbtestaki.aspectssentence.idSentence where IdHotel =? and aspectssentence.Aspect like ? );";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setString(2, options[0]);
           
        ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Total=resultSet.getInt("count");
            }
            resultSet.close();
            preparedStatement.close();
        }
            
            
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Total;
    }
    public HashMap<String,Double> bringVacTypeAndCount( int HotelId){
        HashMap<String,Double> map =new HashMap<String,Double>();
        try {

            String query = "SELECT count(*) as count,comreviewervactype FROM dbtestaki.com where HotelId = ? group by comreviewervactype ;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, HotelId);
           
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                double count=resultSet.getInt("count");
                String VacType= resultSet.getString("comreviewervactype");
                map.put(VacType, count);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
    public String bringCountryName(String code){
        String Country=null;
        try {

            String query = "SELECT NameCountry FROM dbtestaki.nationalitycode where code like ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, code);
           
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                
                 Country= resultSet.getString("NameCountry");
                
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Country;
    }

    public List<CommentBean> getAllCommentsByHotelIdAndCountryLimit3(int hotelId, String Country) {
        List<CommentBean> Comments = new ArrayList<CommentBean>();
        try {

            String query = "select *  from com  where HotelId = ? and comreviewercountry like ? LIMIT 3 ";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setString(2, Country);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CommentBean Comment = new CommentBean();
                Comment.setCommentId(resultSet.getInt("idcom"));
                Comment.setCommentBodyPos(resultSet.getString("compositive"));
                Comment.setCommentBodyNeg(resultSet.getString("comnegative"));
                Comment.setCommentReviwerType(resultSet.getString("comreviewertype"));
                Comment.setCommentReviwerVacType(resultSet.getString("comreviewervactype"));
                Comment.setDaysStayed(resultSet.getString("daysstayed"));
                Comment.setCommentCountry(resultSet.getString("comreviewercountry"));
                Comment.setHotelHotelid(resultSet.getInt("HotelId"));
                Comment.setDateReview(resultSet.getString("reviewdate"));

                Comment.setHotelHotelid(resultSet.getInt("HotelId"));

                Comments.add(Comment);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Comments;
    }
//SELECT count(*) as count FROM dbtestaki.com where  comreviewercountry like "Greece" and comreviewertype like "Couple"  ;
    public void closeconnectio() {
        if (conn != null) {
            ConnectToDb.closeConnection(conn);
        }
    }

}
