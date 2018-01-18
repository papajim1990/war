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
public class CommentBean {
    private String CommentBodyPos,CommentReviwerType,CommentReviwerVacType,Country;
    private String CommentBodyNeg,ReviewTitle;
    private Double ReviewScore;
    private int Hotelid;
    private int idComment;
    private String Days;
    private String Date;
    public int getCommentId(){
        return idComment;
    }
    public void setCommentId(int idComment){
        this.idComment=idComment;
    }
    public int getHoteid(){
        return Hotelid;
    }
    public void setHotelHotelid(int Hotelid){
        this.Hotelid=Hotelid;
    }
    public String getReviewTitle(){
        return ReviewTitle;
    }
    public void setHotelReviewTitle(String ReviewTitle){
        this.ReviewTitle=ReviewTitle;
    }
    public Double getReviewScore(){
        return ReviewScore;
    }
    public void setHotelReviewScore(Double ReviewScore){
        this.ReviewScore=ReviewScore;
    }
        public String getCommentBodyPos(){
        return CommentBodyPos;
    }
    public void setCommentBodyPos(String CommentBodyPos){
        this.CommentBodyPos=CommentBodyPos;
    }
            public String getCommentBodyNeg(){
        return CommentBodyNeg;
    }
    public void setCommentBodyNeg(String CommentBodyNeg){
        this.CommentBodyNeg=CommentBodyNeg;
    }
                    public String getCommentReviwerType(){
        return CommentReviwerType;
    }
    public void setCommentReviwerType(String CommentReviwerType){
        this.CommentReviwerType=CommentReviwerType;
    }
                        public String getCommentReviwerVacType(){
        return CommentReviwerVacType;
    }
    public void setCommentReviwerVacType(String CommentReviwerVacType){
        this.CommentReviwerVacType=CommentReviwerVacType;
    }
                         public String getCommentCountry(){
        return Country;
    }
    public void setCommentCountry(String Country){
        this.Country=Country;
    }

    public void setDaysStayed(String string) {
        this.Days=string;
    }
        public String getDaysStayed(){
        return Days;
    }
           public void setDateReview(String string) {
        this.Date=string;
    }
        public String getDateReview(){
        return Date;
    }
}
