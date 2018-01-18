/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author user1
 */
public class LikeOrDislikeBean {
    private String hotelname,comment;
    private int idUser;
    private int likeordiss;

    public int getidUser() {
        return idUser;
    }

    public void setidUser(int idUser) {
        this.idUser = idUser;
    }
    public String gethotelname() {
        return hotelname;
    }

    public void sethotelname(String hotelname) {
        this.hotelname = hotelname;
    }
    public String getcomment() {
        return comment;
    }

    public void setcomment(String comment) {
        this.comment = comment;
    }
    public int getlikeordiss() {
        return likeordiss;
    }

    public void setlikeordiss(int likeordiss) {
        this.likeordiss = likeordiss;
    }

    
}
