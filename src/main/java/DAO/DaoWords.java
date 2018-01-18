/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Beans.CommentStatsBean;
import Beans.UserBean;
import Connection.ConnectToDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user1
 */
public class DaoWords {
    private Connection conn;
     public DaoWords() {
          conn = ConnectToDb.getConnection();
    }
     public void addsimwords(String Word ,Map<String,Double> words) {
        try {
            if(Word!=null && words!=null){
                        String query = "insert into similaritywords (Word,SimWord,Meter) values (?,?,?)";

       for (String iter : words.keySet()) {
                    String key = iter;
                    Double value = words.get(iter);{
    PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1,Word);
            preparedStatement.setString(2, key);
            preparedStatement.setDouble(3, value);

            preparedStatement.executeUpdate();
        
            preparedStatement.close();
}
            }
       
        }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
     public List<String> getWords() {
        List<String> words = new ArrayList<String>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  distinct Tag as Tag FROM sentencetags");
            while (resultSet.next()) {
                words.add(resultSet.getString("Tag"));
                
                            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }
     public HashMap<String,String> getWordspos() {
        HashMap<String,String> words = new HashMap<String,String>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT   Tag,Pos  FROM tagpossen");
            while (resultSet.next()) {
                words.put(resultSet.getString("Tag").toString(), resultSet.getString("Pos").toString());
                
                            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }
     public LinkedHashMap<String,Integer> getBuss() {
        LinkedHashMap<String,Integer> WordCount = new LinkedHashMap<String,Integer>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  Tag,count(Tag)  FROM dbtestaki.sentencetags join comments on idComments = idcomment where ReviwerVacType LIKE \"Bussiness trip\" group by Tag ORDER BY count(Tag) DESC");
            while (resultSet.next()) {
                WordCount.put(resultSet.getString("Tag"),resultSet.getInt("count(Tag)"));
                
                            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return WordCount;
    }
     public LinkedHashMap<String,Integer> getLeissure() {
        LinkedHashMap<String,Integer> WordCount = new LinkedHashMap<String,Integer>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  Tag,count(Tag)  FROM dbtestaki.sentencetags join comments on idComments = idcomment where ReviwerVacType LIKE \"Leisure trip\" group by Tag ORDER BY count(Tag) DESC;");
            while (resultSet.next()) {
                WordCount.put(resultSet.getString("Tag"),resultSet.getInt("count(Tag)"));
                
                            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return WordCount;
    }
      public LinkedHashMap<String,Integer> getSolo() {
        LinkedHashMap<String,Integer> WordCount = new LinkedHashMap<String,Integer>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  Tag,count(Tag)  FROM dbtestaki.sentencetags join comments on idComments = idcomment where ReviwerType LIKE \"Solo traveller\" group by Tag ORDER BY count(Tag) DESC;");
            while (resultSet.next()) {
                WordCount.put(resultSet.getString("Tag"),resultSet.getInt("count(Tag)"));
                
                            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return WordCount;
    }
      public LinkedHashMap<String,Integer> getCouple() {
        LinkedHashMap<String,Integer> WordCount = new LinkedHashMap<String,Integer>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  Tag,count(Tag)  FROM dbtestaki.sentencetags join comments on idComments = idcomment where ReviwerType LIKE \"Couple\" group by Tag ORDER BY count(Tag) DESC;");
            while (resultSet.next()) {
                WordCount.put(resultSet.getString("Tag"),resultSet.getInt("count(Tag)"));
                
                            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return WordCount;
    }
      public LinkedHashMap<String,Integer> getGroup() {
        LinkedHashMap<String,Integer> WordCount = new LinkedHashMap<String,Integer>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  Tag,count(Tag)  FROM dbtestaki.sentencetags join comments on idComments = idcomment where ReviwerType LIKE \"Group\" group by Tag ORDER BY count(Tag) DESC;");
            while (resultSet.next()) {
                WordCount.put(resultSet.getString("Tag"),resultSet.getInt("count(Tag)"));
                
                            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return WordCount;
    }
      public LinkedHashMap<String,Integer> getFamYoung() {
        LinkedHashMap<String,Integer> WordCount = new LinkedHashMap<String,Integer>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  Tag,count(Tag)  FROM dbtestaki.sentencetags join comments on idComments = idcomment where ReviwerType LIKE \"Family with young children\" group by Tag ORDER BY count(Tag) DESC;");
            while (resultSet.next()) {
                WordCount.put(resultSet.getString("Tag"),resultSet.getInt("count(Tag)"));
                
                            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return WordCount;
    }
            public LinkedHashMap<String,Integer> getFam() {
        LinkedHashMap<String,Integer> WordCount = new LinkedHashMap<String,Integer>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  Tag,count(Tag)  FROM dbtestaki.sentencetags join comments on idComments = idcomment where ReviwerType LIKE \"%Family%\" group by Tag ORDER BY count(Tag) DESC;");
            while (resultSet.next()) {
                WordCount.put(resultSet.getString("Tag"),resultSet.getInt("count(Tag)"));
                
                            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return WordCount;
    }
            public List<String> getWordi() {
        List<String> WordCount = new ArrayList<String>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  Tag,Pos,count(Tag) FROM dbtestaki.tagpossen where Pos like \"%NN%\" group by Tag;");
            while (resultSet.next()) {
                WordCount.add(resultSet.getString("Tag"));
                
                            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return WordCount;
    }
            
}
