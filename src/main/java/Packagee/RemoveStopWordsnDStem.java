/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packagee;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import org.tartarus.snowball.ext.EnglishStemmer;

/**
 *
 * @author user1
 */
public class RemoveStopWordsnDStem {
    public List<String> RemoveStop(List<String> words){
        List<String> newWords =new ArrayList<String>();
        String[] Stop = {""," ","   ","a", "an","anyone","anything","any","are", "and", "are","able", "as","al","ala","all", "at", "be", "but", "by","for", "if", "in", "into", "is", "it","no", "not", "of", "on", "or", "such","that", "the", "their", "then", "there", "these","they", "this", "to", "was", "will", "with"};
        for(String word :words){
            for(String wordi :Stop){
                
            
            if(word.equalsIgnoreCase(wordi)){
                System.out.println("StopWord:"+word);
            }else{
                newWords.add(word);
            }
            
            }
        }
    return newWords;
    }
    public String Stemmword(String word){
EnglishStemmer stemer =new EnglishStemmer();
stemer.setCurrent("Stemmed:"+word);
stemer.stem();
    
System.out.println(stemer.getCurrent());
    return stemer.getCurrent();


    }
}
