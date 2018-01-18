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
import Beans.AspectPerHotelBean;
import Beans.ChartSentenceAspectBean;
import Beans.LemmaToken;
import Beans.SentenceAspect;
import Beans.SentenceBean;
import Beans.SentenceSentBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectToDb;
import Search.AspectOpinionVerb;
import Search.Relation;
import java.util.Iterator;
import java.util.Map;

public class DaoSentence {

    private Connection conn;

    public DaoSentence() {
        conn = ConnectToDb.getConnection();
    }

    public void addSentence(SentenceSentBean Sentence) {
        try {
            String query = "insert into sentnecesentiment (IdHotel,idComment, Sentence,Polarity) values (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, Sentence.getHotelId());
            preparedStatement.setInt(2, Sentence.getCommentId());
            preparedStatement.setString(3, Sentence.getSentenceText());
            preparedStatement.setInt(4, Sentence.getSentiment());
          
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSentence(int sentenceid) {
        try {
            String query = "delete from sentence where idSentence=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, sentenceid);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSentence(SentenceBean Sentence) {
        try {
            String query = "update sentence set idSentence=?, idComment=?, Sentencetext=?,Polarity=?,Prob=? where idUsers=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, Sentence.getSentenceid());
            preparedStatement.setInt(2, Sentence.getCommentId());
            preparedStatement.setString(3, Sentence.getSentenceText());
            preparedStatement.setInt(4, Sentence.getPosOrNegSen());
            preparedStatement.setDouble(5, Sentence.getProbSen());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SentenceSentBean> getAllSentences() {
        List<SentenceSentBean> Sentences = new ArrayList<SentenceSentBean>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from sentnecesentiment order by idSentence");
            while (resultSet.next()) {
                SentenceSentBean Sentence = new SentenceSentBean();
                Sentence.setHotelId(resultSet.getInt("idHotel"));
                Sentence.setSentenceid(resultSet.getInt("idSentence"));
                Sentence.setCommentid(resultSet.getInt("idComment"));
                Sentence.setSentenceText(resultSet.getString("Sentence"));
                Sentence.setSentiment(resultSet.getInt("Polarity"));
                
                Sentences.add(Sentence);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
        }
        return Sentences;
    }

    
    public List<String> getDistinctSentenceAspect() {
        List<String> Aspects = new ArrayList<String>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select Distinct Aspect from aspectssentence");
            while (resultSet.next()) {
             
                String Aspect=resultSet.getString("Aspect");
     
                
                Aspects.add(Aspect);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
        }
        return Aspects;
    }

    public void addaspectssentence(SentenceAspect Sentence) {
        try {
            
                String query = "insert into aspectssentence (idSentence,Aspect,polarity,confidence) values (?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(2, Sentence.getAspect());
                preparedStatement.setInt(1, Sentence.getSentenceid());
                preparedStatement.setString(3, Sentence.getpolarity());
                preparedStatement.setDouble(4, Sentence.getconfidence());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addLemmaTokens(LemmaToken Lemma) {
        try {
            
                String query = "insert into lemmatokens (idSentence,BeforeLemma,AfterLemma,PosTag) values (?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
             
                preparedStatement.setString(4, Lemma.getPosTag());
                preparedStatement.setString(3, Lemma.getAfter());
                preparedStatement.setString(2, Lemma.getBefore());
                preparedStatement.setInt(1, Lemma.getSentenceid());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            
        } catch (SQLException e) {
            System.out.println(e);
            
            System.exit(1);
        }
    }
     public void addarelationspersentence(Relation relation) {
        try {
            
                String query = "insert into relations (IdSentence,Head,Relation,Dependend,PosHead,PosDep) values (?,?,?,?,?,?) ";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
           
                preparedStatement.setString(6, relation.getPosDep());
                preparedStatement.setString(5, relation.getPosHead());
                preparedStatement.setString(4, relation.getDep());
                preparedStatement.setString(3, relation.getRelation());
                preparedStatement.setString(2, relation.getHead());
                preparedStatement.setInt(1, relation.getidSentence());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     public void addarelationspersentenceout(Relation relation) {
        try {
            
                String query = "insert into relationspersentenceout (IdSentence,Head,Relation,Dependend,PosHead,PosDep,TargetLemma,TargetLemmaPos) values (?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(8, relation.getTargetLemmaPos());
                preparedStatement.setString(7, relation.getTargetLemma());
                preparedStatement.setString(6, relation.getPosDep());
                preparedStatement.setString(5, relation.getPosHead());
                preparedStatement.setString(4, relation.getDep());
                preparedStatement.setString(3, relation.getRelation());
                preparedStatement.setString(2, relation.getHead());
                preparedStatement.setInt(1, relation.getidSentence());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addTagsSentencePos(SentenceBean Sentence) {
        try {
Iterator it = Sentence.getSentenceTagsPos().entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
                    String query = "insert into tagpossen (Tag,IdSentence,idcomment,Pos) values (?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, pair.getKey().toString());
                preparedStatement.setInt(2, Sentence.getSentenceid());
                preparedStatement.setInt(3, Sentence.getCommentId());
                preparedStatement.setString(4, pair.getValue().toString());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                it.remove();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSentenceid(SentenceBean BeanSen) {
        int userid = 0;
        try {
            String selectSQL = "SELECT idSentence FROM sentence WHERE idComment = ? and SentenceText LIKE ? and PosOrNeg=? and Prob=?";
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setInt(1, BeanSen.getCommentId());
            preparedStatement.setString(2, BeanSen.getSentenceText());
            preparedStatement.setInt(3, BeanSen.getPosOrNegSen());
            preparedStatement.setDouble(4, BeanSen.getProbSen());

            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            while (rs.next()) {
                userid = rs.getInt("idSentence");
            }
        } catch (SQLException e) {
        }

        return userid;
    }
    /*
public List<String> getTagsSentence(int idSentence) {
        try {
           Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select Tag from sentencetags");
            while (resultSet.next()) {
                SentenceBean Sentence = new SentenceBean();
                Sentence.setSentenceid(resultSet.getInt("idSentence"));
                Sentence.setCommentid(resultSet.getInt("idComment"));
                Sentence.setSentenceText(resultSet.getString("SentenceText"));
                Sentence.setPosOrNeg(resultSet.getInt("PosOrNeg"));
                Sentence.setProbSen(resultSet.getDouble("Prob"));
                //Sentences.add(Sentence);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     */

    public List<SentenceSentBean> getAllSentenceAspectByCommentId(int commentId) {
List<SentenceSentBean> Sentences = new ArrayList<SentenceSentBean>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from sentnecesentiment where idComment = "+commentId);
            while (resultSet.next()) {
                SentenceSentBean Sentence = new SentenceSentBean();
                Sentence.setHotelId(resultSet.getInt("idHotel"));
                Sentence.setSentenceid(resultSet.getInt("idSentence"));
                Sentence.setCommentid(resultSet.getInt("idComment"));
                Sentence.setSentenceText(resultSet.getString("Sentence"));
                Sentence.setSentiment(resultSet.getInt("Polarity"));
                
                Sentences.add(Sentence);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
        }
        return Sentences;
    }
public List<SentenceAspect> getAllSentenceAspect() {
        List<SentenceAspect> Sentences = new ArrayList<SentenceAspect>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from aspectssentence");
            while (resultSet.next()) {
                SentenceAspect Sentence = new SentenceAspect();
                Sentence.setAspectid(resultSet.getInt("idaspectssentence"));
                Sentence.setSentenceid(resultSet.getInt("idSentence"));
                Sentence.setAspect(resultSet.getString("Aspect"));
                Sentence.setpolarity(resultSet.getString("polarity"));
                Sentence.setconfidence(resultSet.getDouble("confidence"));
                
                Sentences.add(Sentence);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
        }
        return Sentences;
    }
public List<AspectPerHotelBean> getAllAspectsForHotel(int idHotel) {
        List<AspectPerHotelBean> Sentences = new ArrayList<AspectPerHotelBean>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Aspect,dbtestaki.aspectssentence.polarity,confidence,count(*) as count FROM dbtestaki.aspectssentence inner join sentnecesentiment on sentnecesentiment.idSentence = dbtestaki.aspectssentence.idSentence where IdHotel = "+idHotel+" group by Aspect,dbtestaki.aspectssentence.polarity ;");
            while (resultSet.next()) {
                AspectPerHotelBean Sentence = new AspectPerHotelBean();
                
                Sentence.setAspect(resultSet.getString("Aspect"));
                Sentence.setpolarity(resultSet.getString("polarity"));
                Sentence.setconfidence(resultSet.getDouble("confidence"));
                Sentence.setCountAspect(resultSet.getInt("count"));
                
                Sentences.add(Sentence);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
        }
        return Sentences;
    }

public List<SentenceAspect> getAllSentenceAspectByidSentence(int idSentence) {
        List<SentenceAspect> Sentences = new ArrayList<SentenceAspect>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from aspectssentence where idSentence = "+idSentence);
            while (resultSet.next()) {
                SentenceAspect Sentence = new SentenceAspect();
                Sentence.setAspectid(resultSet.getInt("idaspectssentence"));
                Sentence.setSentenceid(resultSet.getInt("idSentence"));
                Sentence.setAspect(resultSet.getString("Aspect"));
                Sentence.setpolarity(resultSet.getString("polarity"));
                Sentence.setconfidence(resultSet.getDouble("confidence"));
                
                Sentences.add(Sentence);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
        }
        return Sentences;
    }
public List<ChartSentenceAspectBean> getAllSentenceAspectByHotelid(int hotelId) {
        List<ChartSentenceAspectBean> Sentences = new ArrayList<ChartSentenceAspectBean>();
        try {
            
            String query="Select dbtestaki.aspectssentence.idaspectssentence,reviewdate,dbtestaki.aspectssentence.Aspect,dbtestaki.aspectssentence.idSentence,dbtestaki.aspectssentence.polarity,dbtestaki.aspectssentence.confidence,comreviewertype,comreviewervactype,comreviewercountry FROM dbtestaki.aspectssentence inner join (Select idSentence,reviewdate,com.comreviewertype,com.comreviewercountry,com.comreviewervactype,sentnecesentiment.IdHotel from sentnecesentiment  join com on sentnecesentiment.idComment = com.idcom ) as sent on sent.idSentence = dbtestaki.aspectssentence.idSentence where IdHotel = ?  ;";
           PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
           
     
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ChartSentenceAspectBean Sentence = new ChartSentenceAspectBean();
                Sentence.setAspectid(resultSet.getInt("idaspectssentence"));
                Sentence.setSentenceid(resultSet.getInt("idSentence"));
                Sentence.setAspect(resultSet.getString("Aspect"));
                Sentence.setpolarity(resultSet.getString("polarity"));
                Sentence.setconfidence(resultSet.getDouble("confidence"));
                Sentence.setReviewerType(resultSet.getString("comreviewertype"));
                Sentence.setVacType(resultSet.getString("comreviewervactype"));
                Sentence.setCountry(resultSet.getString("comreviewercountry"));
                Sentence.setDateReview(resultSet.getString("reviewdate"));
                Sentences.add(Sentence);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
        }
        return Sentences;
    }
    public List<SentenceSentBean> getAllSentencesnot(int hotelId) {
        List<SentenceSentBean> Sentences = new ArrayList<SentenceSentBean>();
        try {
            String query="Select * from sentnecesentiment where IdHotel = ? and idSentence not in ( sELECT idSentence FROM dbtestaki.aspectssentence );";

                    PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SentenceSentBean Sentence = new SentenceSentBean();
                Sentence.setHotelId(resultSet.getInt("idHotel"));
                Sentence.setSentenceid(resultSet.getInt("idSentence"));
                Sentence.setCommentid(resultSet.getInt("idComment"));
                Sentence.setSentenceText(resultSet.getString("Sentence"));
                Sentence.setSentiment(resultSet.getInt("Polarity"));
                
                Sentences.add(Sentence);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
        }
        return Sentences;
    }
}
