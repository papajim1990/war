/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.List;

/**
 *
 * @author user1
 */
public class CommentStatsBean {

    private List<String> TagsComm;
    private int idComment;
    private int PosOrNeg;
    private Double Prob;

    public int getCommentid() {
        return idComment;
    }

    public void setCommentid(int idcomment) {
        this.idComment = idcomment;
    }

    public int getPosOrNeg() {
        return PosOrNeg;
    }

    public void setPosOrNeg(int PosOrNeg) {
        this.PosOrNeg = PosOrNeg;
    }

    public Double getProbComment() {
        return Prob;
    }

    public void setProbComment(Double Prob) {
        this.Prob = Prob;
    }

    public List<String> getTagsComment() {
        return TagsComm;
    }

    public void setTagsComment(List<String> TagsComm) {
        this.TagsComm = TagsComm;
    }
}
