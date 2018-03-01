/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SentimentAylien;

import Beans.SentenceAspect;
import Beans.SentenceSentBean;
import Controllers.Home;
import DAO.DaoSentence;
import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.parameters.AspectBasedSentimentParams;
import com.aylien.textapi.responses.Aspect;
import com.aylien.textapi.responses.AspectSentence;
import com.aylien.textapi.responses.AspectsSentiment;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user1
 */
public class AspectSentimentAnalysis {
    public void Analyze(List<SentenceSentBean> allComments,DaoSentence daoSen){
        
            //List<SentenceAspect> allAspects = daoSen.getAllSentenceAspect();
            //SentenceAndSentiment sente =new SentenceAndSentiment();
            //StanfordTripletsAndAspects sente =new  StanfordTripletsAndAspects();
            // try {
            //   sente.StanfordTripletsAndAspects(allComments,daoSen);
            // } catch (Exception ex) {
            //     Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            // }
            try {

                int count = 1;

                for (SentenceSentBean sent : allComments) {

                   
System.out.println(sent.getSentenceid());
                        String api = "b91eb90e";//b746857d a17edd28 c7f05131
                        String secret = "b67670a3c6bea9bf28747bdf1c659e02";//e3716f3d5910320815318cbd7e27d744 639fbcfd3f8689ccdf1781ad1dae854a 639fbcfd3f8689ccdf1781ad1dae854a
                        if (count % 2 == 0) {

                            api = "c7f05131";
                            secret = "639fbcfd3f8689ccdf1781ad1dae854a";
                        } else {
                            api = "501d6e49";
                            secret = "c8aa4d44571809321597c266e2166049";
                        }
                        count++;
                        System.out.println(count + " " + sent.getSentenceid() + " " + sent.getSentenceText());
                        SentenceAspect aspecti = new SentenceAspect();
                        TextAPIClient client = new TextAPIClient(api, secret);

                        AspectBasedSentimentParams.Builder builder = AspectBasedSentimentParams.newBuilder();
                        builder.setDomain(AspectBasedSentimentParams.StandardDomain.HOTELS);
                        builder.setText(sent.getSentenceText());
                        try {
                            AspectsSentiment aspectsSentiment = client.aspectBasedSentiment(builder.build());

                            for (Aspect aspect : aspectsSentiment.getAspects()) {

                                String name1 = aspect.getName();
                                String polarity = aspect.getPolarity();
                                double polarityConfidence = aspect.getPolarityConfidence();
                                aspecti.setAspect(name1);
                                aspecti.setSentenceid(sent.getSentenceid());
                                aspecti.setpolarity(polarity);
                                aspecti.setconfidence(polarityConfidence);
                                System.out.println(sent.getSentenceid() + " " + name1 + " " + polarity);
                                daoSen.addaspectssentence(aspecti);
                            }
                            for (AspectSentence sentence : aspectsSentiment.getSentences()) {
                                sentence.getText();
                                sentence.getPolarity();
                            }
                        } catch (com.aylien.textapi.TextAPIException e) {

                            Thread.sleep(60 * 1000);
                            System.err.println(e);
                        }

                    }

                

            } catch (Exception ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
