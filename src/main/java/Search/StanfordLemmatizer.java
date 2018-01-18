/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.util.LinkedList;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author user1
 */



public class StanfordLemmatizer {

protected StanfordCoreNLP pipeline;

public StanfordLemmatizer() {
    // Create StanfordCoreNLP object properties, with POS tagging
    // (required for lemmatization), and lemmatization
  
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit , pos, parse, lemma, sentiment,depparse,natlog,openie");
        this.pipeline=  new StanfordCoreNLP(props);
}

public List<String> lemmatize(String documentText)
{
    List<String> lemmas = new LinkedList<String>();
    // Create an empty Annotation just with the given textd
    Annotation document = new Annotation(documentText);
    // run all Annotators on this text
    this.pipeline.annotate(document);
    // Iterate over all of the sentences found
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
     for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
      // Get the OpenIE triples for the sentence
      Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
      // Print the triples
      for (RelationTriple triple : triples) {
        System.out.println(triple.confidence + "\t" +
            triple.subjectLemmaGloss()+"-- "+ triple.relationLemmaGloss() + "--\t" +
            triple.objectLemmaGloss());
      }
    }
    for(CoreMap sentence: sentences) {
        		int mainSentiment = 0;
			int longest = 0;

        // Iterate over all tokens in a sentence
        for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
            // Retrieve and add the lemma for each word into the
            // list of lemmas
            lemmas.add(token.get(LemmaAnnotation.class));

        }
                   /* Tree tree = sentence
						.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
				int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
				String partText = sentence.toString();
				if (partText.length() > longest) {
					mainSentiment = sentiment;
					longest = partText.length();*/
				
    }
    return lemmas;
}


static String readFile(String path, Charset encoding) 
  throws IOException 
{
  byte[] encoded = Files.readAllBytes(Paths.get(path));
  return new String(encoded, encoding);
}

public static void main(String[] args) throws IOException {

    System.out.println("Starting Stanford Lemmatizer");
    String content = readFile("C:\\Users\\user1\\Documents\\NetBeansProjects\\WebApplication1\\train\\aa.txt", Charset.defaultCharset());

   String text = "How could you be seeing into my eyes like open doors? \n"+
            "You led me down into my core where I've became so numb \n"+
            "Without a soul my spirit's sleeping somewhere cold \n"+
            "Until you find it there and led it back home \n"+
            "You woke me up inside \n"+
            "Called my name and saved me from the dark \n"+
            "You have bidden my blood and it ran \n"+
            "Before I would become undone \n"+
            "You saved me from the nothing I've almost become \n"+
            "You were bringing me to life \n"+
            "Now that I knew what I'm without \n"+
            "You can've just left me \n"+
            "You breathed into me and made me real \n"+
            "Frozen inside without your touch \n"+
            "Without your love, darling \n"+
            "Only you are the life among the dead \n"+
            "I've been living a lie, there's nothing inside \n"+
            "You were bringing me to life.";

    StanfordLemmatizer slem = new StanfordLemmatizer();
    String[] Stop = {""," ","   ","a", "an","anyone","anything","any","are", "and", "are","able", "as","al","ala","all", "at", "be", "but", "by","for", "if", "in", "into", "is", "it","no", "not", "of", "on", "or", "such","that", "the", "their", "then", "there", "these","they", "this", "to", "was", "will", "with"};
    List<String> lemmatize = slem.lemmatize(content);

    
    System.out.println(lemmatize);
	for(String lemmat:lemmatize){
            if (lemmat != null && lemmatize.size() > 0) {
    int count = Collections.frequency(lemmatize, lemmat);
    System.out.println("count:"+count + " lemma: "+lemmat);
}
        }	
	}

}

