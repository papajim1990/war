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
import Beans.CommentStatsBean;
import Beans.SentenceSentBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Connection.ConnectToDb;

public class DaoComments {

    private Connection conn;

    public DaoComments() {
        conn = ConnectToDb.getConnection();
    }

    public void addcommentsstats(CommentStatsBean Commentstats) {
        try {
            String query = "insert into commentsstats (idCommentsStats,Prob, PosOrNeg) values (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, Commentstats.getCommentid());
            preparedStatement.setDouble(2, Commentstats.getProbComment());
            preparedStatement.setInt(3, Commentstats.getPosOrNeg());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addtagscomments(CommentStatsBean Commentstats) {
        try {
            for(String Tag:Commentstats.getTagsComment()){
            String query = "insert into tagscomments (idComments,Tag) values (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, Commentstats.getCommentid());
            preparedStatement.setString(2, Tag);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addtSentenceSentiment(SentenceSentBean SentSent) {
        try {
            
            String query = "insert into SentneceSentiment (IdHotel,idComment,Sentence,Sentiment) values (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, SentSent.getHotelId());
            preparedStatement.setInt(2, SentSent.getCommentId());
            preparedStatement.setString(3, SentSent.getSentenceText());
            preparedStatement.setInt(4, SentSent.getSentiment());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
