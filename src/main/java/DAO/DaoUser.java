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
import Beans.FollowBean;
import Beans.LikeOrDislikeBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Beans.UserBean;
import Beans.TagsUserBean;
import Connection.ConnectToDb;

public class DaoUser {

    private Connection conn;

    public DaoUser() {
        conn = ConnectToDb.getConnection();
    }

    public void addUser(UserBean User) {
        try {
            String query = "insert into users (UserName,FirstName,LastName,Email,Country,Password,Date,Gender) values (?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, User.getEmail());
            preparedStatement.setString(2, User.getFirstname());
            preparedStatement.setString(3, User.getlastname());
            preparedStatement.setString(4, User.getEmail());
            preparedStatement.setString(5, User.getcountry());

            preparedStatement.setString(6, User.getPass());
            preparedStatement.setDate(7, User.getDate());
             preparedStatement.setString(8, User.getGender());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<UserBean> getAllUsers() {
        List<UserBean> users = new ArrayList<UserBean>();
        try {

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Users");
            while (resultSet.next()) {
                UserBean user = new UserBean();
                user.setId(resultSet.getInt("idUsers"));
                
                user.setFirstname(resultSet.getString("FirstName"));
                user.setlastname(resultSet.getString("LastName"));
                user.setcountry(resultSet.getString("Country"));
                
                user.setEmail(resultSet.getString("UserEmail"));
                user.setPass(resultSet.getString("Password"));
                
                user.setDate(resultSet.getDate("Date"));
                user.setGender(resultSet.getString("Gender"));
                users.add(user);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public List<UserBean> getUserByEmail(String email) {
        List<UserBean> users = new ArrayList<>();
        try {
                        String selectSQL = "select * from Users where Email = ?";
PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
preparedStatement.setString(1, email);
ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                UserBean user = new UserBean();
                user.setId(resultSet.getInt("idUsers"));
                
                user.setFirstname(resultSet.getString("FirstName"));
                user.setlastname(resultSet.getString("LastName"));
                user.setcountry(resultSet.getString("Country"));
                
                user.setEmail(resultSet.getString("Email"));
                user.setPass(resultSet.getString("Password"));
                
                user.setDate(resultSet.getDate("Date"));
                user.setGender(resultSet.getString("Gender"));
                users.add(user);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public List<UserBean> getUserByEmailAndPassword(String email,String password){
                List<UserBean> users = new ArrayList<>();
        try {
                        String selectSQL = "select * from Users where Email = ? and Password = ?";
PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
preparedStatement.setString(1, email);
preparedStatement.setString(2, email);
ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                UserBean user = new UserBean();
                user.setId(resultSet.getInt("idUsers"));
                
                user.setFirstname(resultSet.getString("FirstName"));
                user.setlastname(resultSet.getString("LastName"));
                user.setcountry(resultSet.getString("Country"));
                
                user.setEmail(resultSet.getString("Email"));
                user.setPass(resultSet.getString("Password"));
                
                user.setDate(resultSet.getDate("Date"));
                user.setGender(resultSet.getString("Gender"));
                users.add(user);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void deleteUser(int userid) {
        try {
            String query = "delete from users where idusers=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userid);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(UserBean User) {
        try {
            String query = "update Users set UserName=?,FirstName=?,LastName=?,Email=?,Country=?,TypeUser=?,Follwoers=?, Password=?,DateTime=? where idusers=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
           
            preparedStatement.setString(2, User.getFirstname());
            preparedStatement.setString(3, User.getlastname());
            preparedStatement.setString(4, User.getEmail());
            preparedStatement.setString(5, User.getcountry());
           
            preparedStatement.setString(8, User.getPass());
            preparedStatement.setDate(9, User.getDate());
            preparedStatement.setInt(10, User.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

    public UserBean getStudentById(int userid) {
        UserBean user = new UserBean();
        try {
            String query = "select * from Users where idUsers=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
           
                user.setFirstname(resultSet.getString("FirstName"));
                user.setlastname(resultSet.getString("LastName"));
                user.setcountry(resultSet.getString("Country"));
         
                user.setEmail(resultSet.getString("Email"));
                user.setPass(resultSet.getString("Password"));
              
                user.setDate(resultSet.getDate("DateTime"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public UserBean getUsersId(String email) {
        UserBean user = new UserBean();
        try {
            String query = "select * from Users where Email=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("idUsers"));
        
                user.setFirstname(resultSet.getString("FirstName"));
                user.setlastname(resultSet.getString("LastName"));
                user.setcountry(resultSet.getString("Country"));
            
                user.setPass(resultSet.getString("Password"));
     
                user.setDate(resultSet.getDate("DateTime"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public void addTagUser(TagsUserBean tagBean){
        try {
        String query = "insert into tagsuser (idUser,Tag,importance) values (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
             preparedStatement.setInt(1, tagBean.getiduser());
            preparedStatement.setString(2, tagBean.gettag());
            preparedStatement.setDouble(3, tagBean.getimportance());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void DeleteTagUser(TagsUserBean tagBean){
        try {
        String query = "delete from tagsuser where id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
             preparedStatement.setInt(1, tagBean.getid());
             preparedStatement.executeUpdate();
            preparedStatement.close();
           
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public TagsUserBean gettagsuserId(int iduser,String tag) {
        TagsUserBean taguser = new TagsUserBean();
        try {
            String query = "select * from tagsuser where idUser=? and Tag=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, iduser);
            preparedStatement.setString(2, tag);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                taguser.setid(resultSet.getInt("id"));
                taguser.setiduser(iduser);
                taguser.settag(tag);
                taguser.setimportance(resultSet.getDouble("importance"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taguser;
    }
    public void UpdateTagUser(TagsUserBean tagBean){
        try {
            String query = "update tagsuser set iduser=? , Tag=?, importance=? where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, tagBean.getiduser());
            preparedStatement.setString(2, tagBean.gettag());
            preparedStatement.setDouble(3, tagBean.getimportance());
            preparedStatement.setInt(4, tagBean.getid());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        

    }
    public List<String> getallTagsByUser(UserBean User) throws SQLException{
        List<String> tag;
        tag = new ArrayList<String>();
        try {
        String query = "select Tag from tagsuser where idUser=?";
             PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, User.getId());
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               tag.add(resultSet.getString("Tag"));
            }
            resultSet.close();
            preparedStatement.close();
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;      
    }
    public void AddFollow(FollowBean FollowBean){
        try {
        String query = "insert into followusers (idUser1,idUser2) values (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
             preparedStatement.setInt(1, FollowBean.getiduser1());
             preparedStatement.setInt(1, FollowBean.getiduser2());
             preparedStatement.executeUpdate();
            preparedStatement.close();

           
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void DeleteFollow(FollowBean FollowBean){
        try {
        String query = "Delete  From followusers where idUser1=? and idUser2=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
             preparedStatement.setInt(1, FollowBean.getiduser1());
             preparedStatement.setInt(1, FollowBean.getiduser2());
             preparedStatement.executeUpdate();
            preparedStatement.close();

           
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void AddLikeOrDiss(LikeOrDislikeBean likeordiss){
        try {
        String query = "insert into likeordisslike (idUser,HotelName,LikeOrDiss,Comment) values (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
             preparedStatement.setInt(1, likeordiss.getidUser());
             preparedStatement.setString(2, likeordiss.gethotelname());
             preparedStatement.setInt(3, likeordiss.getlikeordiss());
             preparedStatement.setString(4, likeordiss.getcomment());
             preparedStatement.executeUpdate();
            preparedStatement.close();

           
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
     public void DeleteLikeOrDiss(LikeOrDislikeBean likeordiss){
        try {
        String query = "Delete from likeordisslike where idUser=? and HotelName=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
             preparedStatement.setInt(1, likeordiss.getidUser());
             preparedStatement.setString(2, likeordiss.gethotelname());
             preparedStatement.executeUpdate();
             preparedStatement.close();

           
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
     public List<LikeOrDislikeBean> getLikesDissByUser(int iduser) {
        List<LikeOrDislikeBean> likediss = new ArrayList<LikeOrDislikeBean>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from likeordisslike where idUser="+iduser+"");
            while (resultSet.next()) {
                LikeOrDislikeBean likeordiss = new LikeOrDislikeBean();
                likeordiss.setidUser(resultSet.getInt("idUser"));
                likeordiss.sethotelname(resultSet.getString("HotelName"));
                likeordiss.setlikeordiss(resultSet.getInt("LikeOrDiss"));
                likeordiss.setcomment(resultSet.getString("Comment"));
                
                likediss.add(likeordiss);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likediss;
    }
}
