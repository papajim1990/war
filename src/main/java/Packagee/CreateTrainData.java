/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packagee;

import Beans.CommentBean;
import DAO.DaoHotels;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author user1
 */
public class CreateTrainData {
    DaoHotels dao =new DaoHotels();
    public void TrainData() throws IOException{
        List<CommentBean> allComments = dao.getAllComments();
        FileWriter fileWriter =
                    new FileWriter("C:\\Users\\user1\\Documents\\NetBeansProjects\\WebApplication1\\train\\TrainData.txt");
            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);
        for(CommentBean Bean : allComments){
            String commentBodyPos = Bean.getCommentBodyPos();
            String commentBodyNeg = Bean.getCommentBodyNeg();

            // Assume default encoding.
            
            // Note that write() does not automatically
            // append a newline character.
            if(commentBodyPos!=null && !commentBodyPos.trim().isEmpty()){
            bufferedWriter.write("1 "+commentBodyPos);
            
            bufferedWriter.newLine();
            }
                        if(commentBodyNeg!=null && !commentBodyNeg.trim().isEmpty()){

            bufferedWriter.write("0 "+commentBodyNeg);
            
                        bufferedWriter.newLine();
                        }
            
            // Always close files.
           
            
        } 
        bufferedWriter.close();
    }
}
