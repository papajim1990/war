/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

import Beans.CommentBean;
import Beans.SentenceSentBean;
import DAO.DaoSentence;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author user1
 */
public class Sentence {

    public  void Sentence(List<CommentBean> Path,DaoSentence daoSen) throws IOException, Exception {
        InitAnnotator InitAnnotator = new InitAnnotator();
        StanfordCoreNLP pipeline = InitAnnotator.InitAnnotator();
        for(CommentBean Bean:Path){

      
            //System.out.println(readLine);

            //content = removeStopWords(content);
            Annotation document = new Annotation(Bean.getCommentBodyPos());
            // run all Annotators on this text
            pipeline.annotate(document);
            // Iterate over all of the sentences found
            SentenceSentBean Sente =new SentenceSentBean();
            for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
                if(sentence.toString().trim()!=null &&sentence.toString().trim()!=""){
                Sente.setHotelId(Bean.getHoteid());
                Sente.setCommentid(Bean.getCommentId());
                Sente.setSentenceText(sentence.toString());
                Sente.setSentiment(1);
                daoSen.addSentence(Sente);
                System.out.println("..");
                }
            }
       }
        }
            

  
}
