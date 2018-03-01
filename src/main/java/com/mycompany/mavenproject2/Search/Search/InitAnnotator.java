/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.Search.Search;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.util.Properties;

/**
 *
 * @author user1
 */
public class InitAnnotator {
public StanfordCoreNLP InitAnnotator() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit , pos, parse, lemma,ner,depparse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        return pipeline;
}
}
