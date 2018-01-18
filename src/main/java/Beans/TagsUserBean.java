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
public class TagsUserBean {
    private String tag;
    private int id,iduser;
    private Double importance;
    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }
      public int getiduser() {
        return iduser;
    }

    public void setiduser(int iduser) {
        this.iduser = iduser;
    }
    public String gettag() {
        return tag;
    }

    public void settag(String tag) {
        this.tag = tag;
    }
    public Double getimportance() {
        return importance;
    }

    public void setimportance(Double importance) {
        this.importance = importance;
    }
}
