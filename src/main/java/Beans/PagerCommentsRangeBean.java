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
public class PagerCommentsRangeBean {
    private int start,end;
    public int getHstart(){
        return start;
    }
    public void setstart(int start){
        this.start=start;
    }
     public int getend(){
        return end;
    }
    public void setend(int end){
        this.end=end;
    }
}
