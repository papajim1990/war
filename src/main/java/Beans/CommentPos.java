/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.InputStream;


/**
 *
 * @author user1
 */
public class CommentPos {
    private String CommentBodyPos;

    public CommentPos(InputStream modelIn) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public String getCommentBodyPos(){
        return CommentBodyPos;
    }
    public void setCommentBodyPos(String CommentBodyPos){
        this.CommentBodyPos=CommentBodyPos;
    }
 
}
