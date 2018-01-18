package Search;

import Beans.CommentBean;
import Beans.LemmaToken;
import Beans.SentenceSentBean;
import DAO.DaoSentence;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreNLPProtos.CorefChain;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.spelling.SpellingCheckRule;

public class SentenceAndSentiment {

public void SentenceAndSentiment(List<SentenceSentBean> Sente, DaoSentence dao) throws IOException, Exception {
        InitAnnotator InitAnnotator = new InitAnnotator();
        
        StanfordCoreNLP pipeline = InitAnnotator.InitAnnotator();
                               JLanguageTool langTool = new JLanguageTool(new BritishEnglish());
for (Rule rule : langTool.getAllRules()) {
  if (!rule.isDictionaryBasedSpellingRule()) {
    langTool.disableRule(rule.getId());
  }

  if (rule.getId().equals("ENGLISH_SPELLER_RULE")) {
      if (rule instanceof SpellingCheckRule) {
            SpellingCheckRule srule=(SpellingCheckRule) rule;
            String [] words={"wifi","WIFI", "ac","a/c"};
            List<String> tokens=new ArrayList<String>();
            for (String word:words) {
                tokens.add(word);
            }
            srule.addIgnoreTokens(tokens);
        }
  
}
}

        

            

HashSet<String> hashMap;
        hashMap = new HashSet();
        HashSet<String> hashMap2 = new HashSet();
         List<String> Feautures = new ArrayList();
         List<String> Feautures2 = new ArrayList();
            for(SentenceSentBean readLine:Sente){
            System.out.println(readLine.getSentenceid());
                //System.out.println(readLine);
            if(readLine.getSentenceid()>189473){

        //content = removeStopWords(content);
       

        Annotation document = new Annotation(readLine.getSentenceText().replaceAll("[^a-zA-Z0-9 .,'\"]", "-").replaceAll("\\.+", " . "));
        pipeline.annotate(document);
            List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        // run all Annotators on this text
        
            // Iterate over all of the sentences found
            for(CoreMap sentence:sentences){
            
                // traversing the words in the current sentence
                // a CoreLabel is a CoreMap with additional token-specific methods
                for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                    try {
                         LemmaToken lemmatok =new LemmaToken();
                        // this is the text of the token
                        String word = token.get(TextAnnotation.class);
                        if(!word.replaceAll("[^a-zA-Z]", "").isEmpty()){
                        // this is the POS tag of the token
                        String pos = token.get(PartOfSpeechAnnotation.class);
                        if(pos.contains("NN")|| pos.contains("VB")||pos.contains("JJ")||pos.contains("RB")){
                        String lemma =token.get(LemmaAnnotation.class);
                        List<RuleMatch> matches;
                        matches = langTool.check(lemma);
                        for (RuleMatch match : matches) {
                            if(!match.getSuggestedReplacements().isEmpty()){
                            lemma=match.getSuggestedReplacements().get(0);}
                        }
                        // this is the NER label of the token
                        String ne = token.get(NamedEntityTagAnnotation.class);
                        lemmatok.setAfter(lemma);
                        lemmatok.setBefore(word);
                        lemmatok.setPosTag(pos);
                        lemmatok.setSentenceid(readLine.getSentenceid());
                       // System.out.println("word: "+word +" "+ "lemma: "+ lemma+" pos: "+pos);
                        
                            dao.addLemmaTokens(lemmatok);
                        }
                    }
                    } catch (IOException ex) {
                        Logger.getLogger(SentenceAndSentiment.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                }     
            }
}
    }
}
}

    
