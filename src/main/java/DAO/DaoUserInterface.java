/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Beans.CommentBean;
import Beans.HotelBean;
/**
 *
 * @author user1
 */
import java.util.List;

import Beans.UserBean;

public interface DaoUserInterface {

    public void addUser(UserBean user);

    public void deleteUser(int studentId);

    public void updateUser(UserBean user);

    public List<UserBean> getAllUsers();

    public UserBean getUseryId(int usersid);

    public UserBean getUsersId(String email);

    public void addHotel(HotelBean hotel);

    public void deleteHotel(int hotelid);

    public void addComment(CommentBean Comment);

    public void getAllComments();

    public void getAllhotels();

    public HotelBean getHotelbyId(int id);
}
