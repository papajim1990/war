/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.Search.Search;

import Beans.SentenceSentBean;
import DAO.DaoSentence;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author user1
 */
public class StanfordTripletsAndAspects {

    public void StanfordTripletsAndAspects(List<SentenceSentBean> Sente, DaoSentence dao) throws IOException, Exception {
        InitAnnotator InitAnnotator = new InitAnnotator();
        StanfordCoreNLP pipeline = InitAnnotator.InitAnnotator();


        HashSet<String> hashMap;
        hashMap = new HashSet();
        HashSet<String> hashMap2 = new HashSet();
        List<String> Feautures = new ArrayList();
        List<String> Feautures2 = new ArrayList();
        for (SentenceSentBean readLine : Sente) {
            System.out.println(readLine.getSentenceid());


            //content = removeStopWords(content);


            Annotation document = new Annotation(readLine.getSentenceText().replaceAll("[^a-zA-Z0-9 .,'\"]", "-").replaceAll("\\.+", " . "));
            // run all Annotators on this text
            pipeline.annotate(document);
            // Iterate over all of the sentences found
            for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
                System.out.println(sentence.toString());

                Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
                if (!sentence.toString().replaceAll("[^a-zA-Z0-9]", "").isEmpty()) {
                    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
                    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
                    GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
                    Collection<TypedDependency> td = gs.typedDependenciesEnhancedPlusPlus();


                    // Annotation tokenAnnotation = new Annotation(word);
                    //  pipeline.annotate(tokenAnnotation);  // necessary for the LemmaAnnotation to be set.
                    // List<CoreMap> lista = tokenAnnotation.get(SentencesAnnotation.class);
                    //String tokenLemma = lista
                    //.get(0).get(TokensAnnotation.class)
                    // .get(0).get(LemmaAnnotation.class);


                }
                
           
                       
                        /*
                     for(TypedDependency type:td){
                                 Relation Rel = new Relation();
                        Rel.setTargetLemma(null);
                        Rel.setidSentence(readLine.getSentenceid());
                        if(type.reln().getSpecific()!=null){
                        Rel.setRelation(type.reln().getShortName()+":"+type.reln().getSpecific());
                        }else{
                            Rel.setRelation(type.reln().getShortName());
                        }
                        Rel.setDep(type.dep().lemma());
                        Rel.setHead(type.gov().lemma());
                        Rel.setPosDep(type.dep().tag());
                        Rel.setPosHead(type.gov().tag());
                        System.out.println(type.gov().lemma()+" "+type.reln().getShortName()+":"+type.reln().getSpecific()+" "+type.dep().lemma());
                         dao.addarelationspersentence(Rel);
                
                        
                     
                        /*
                        SemgrexPattern semgrexout = SemgrexPattern.compile("{}=Head >>=D {word:/" + word + "/}=Dep  ");
                        SemgrexMatcher matcherout = semgrexout.matcher(graph);
                        //System.err.println("Outcoming Edges");
                        while (matcherout.find()) {
                        Set<String> nodeNames = matcherout.getNodeNames();
                        String relnString = matcherout.getRelnString("D");
                        //System.err.println(matcherout.getNode("Head")+" "+matcherout.getRelnString("D")+" "+ matcherout.getNode("Dep"));
                        Relation Rel = new Relation();
                        Rel.setTargetLemma(tokenLemma);
                        Rel.setidSentence(readLine.getSentenceid());
                        Rel.setRelation(relnString);
                        Rel.setDep(matcherout.getNode("Dep").word());
                        Rel.setPosDep(matcherout.getNode("Dep").tag());
                        Rel.setPosHead(matcherout.getNode("Head").tag());
                        Rel.setHead(matcherout.getNode("Head").word());
                        Rel.setTargetLemmaPos(matcherout.getNode("Dep").tag());
                        
                        //dao.addarelationspersentenceout(Rel);
                        
                        }
                    }
                      
                        
                    
                    
                        TregexPattern pattern = TregexPattern.compile("__");
                        TregexMatcher matcher = pattern.matcher(tree);
                        Set<String> Aspects = new HashSet<String>();
                        List<AspectOpinionVerb> ListaAVO = new ArrayList<>();
                        AspectOpinionVerb bean = new AspectOpinionVerb();
                        while (matcher.find()) {
                        AspectOpinionVerb beante = new AspectOpinionVerb();
                        Tree match = matcher.getMatch();
                        List<Tree> leaves1 = match.getChildrenAsList();
                        StringBuilder stringbuilder = new StringBuilder();
                        StringBuilder stringbuilder2 = new StringBuilder();
                        StringBuilder stringbuilder3 = new StringBuilder();
                        for (Tree tree1 : leaves1) {
                        String val = tree1.label().value();
                        if (val.equals("DT")) {
                        Tree nn[] = tree1.children();
                        ArrayList<Label> ss = nn[0].yield();
                        for (Label lab : ss) {
                        Annotation tokenAnnotation = new Annotation(lab.value());
                        pipeline.annotate(tokenAnnotation);  // necessary for the LemmaAnnotation to be set.
                        List<CoreMap> lista = tokenAnnotation.get(SentencesAnnotation.class);
                        String tokenLemma = lista
                        .get(0).get(TokensAnnotation.class)
                        .get(0).get(LemmaAnnotation.class);
                        if (tokenLemma.equalsIgnoreCase("no")) {
                        stringbuilder.append(tokenLemma).append(" ");
                        }
                        }
                        }
                        if(val.equals("CC")){
                        stringbuilder.delete(0, stringbuilder.capacity());
                        }
                        if (val.equals("NN") || val.equals("NNS")
                        || val.equals("NNP") || val.equals("NNPS")) {
                        Tree nn[] = tree1.children();
                        ArrayList<Label> ss = nn[0].yield();
                        for (Label lab : ss) {
                        Annotation tokenAnnotation = new Annotation(lab.value());
                        pipeline.annotate(tokenAnnotation);  // necessary for the LemmaAnnotation to be set.
                        List<CoreMap> lista = tokenAnnotation.get(SentencesAnnotation.class);
                        String tokenLemma = lista
                        .get(0).get(TokensAnnotation.class)
                        .get(0).get(LemmaAnnotation.class);
                        stringbuilder.append(tokenLemma).append(" ");
                        }
                        } else if (val.contains("ADJP")) {
                        Tree nn[] = tree1.children();
                        for (Tree lab : nn) {
                        if (lab.value().equalsIgnoreCase("RB") || lab.value().contains("JJ")) {
                        stringbuilder2.append(lab.yieldWords().toString().replaceAll("\\p{P}", "")).append(" ");
                        }
                        if (lab.value().contains("JJ")) {
                        Annotation tokenAnnotation = new Annotation(lab.yieldWords().toString().replaceAll("\\p{P}", ""));
                        pipeline.annotate(tokenAnnotation);  // necessary for the LemmaAnnotation to be set.
                        List<CoreMap> lista = tokenAnnotation.get(SentencesAnnotation.class);
                        String tokenLemma = lista
                        .get(0).get(TokensAnnotation.class)
                        .get(0).get(LemmaAnnotation.class);
                        Feautures2.add(tokenLemma);
                        }
                        }
                        } else if (val.contains("JJ")) {
                        Tree nn[] = tree1.children();
                        String ss = nn[0].yield().toString();
                        ArrayList<Label> sss = nn[0].yield();
                        for (Label lab : sss) {
                        Annotation tokenAnnotation = new Annotation(lab.value());
                        pipeline.annotate(tokenAnnotation);  // necessary for the LemmaAnnotation to be set.
                        List<CoreMap> lista = tokenAnnotation.get(SentencesAnnotation.class);
                        String tokenLemma = lista
                        .get(0).get(TokensAnnotation.class)
                        .get(0).get(LemmaAnnotation.class);
                        stringbuilder2.append(tokenLemma + " ");
                        Feautures2.add(tokenLemma);
                        }
                        } else if (val.contains("VB")) {
                        Tree nn[] = tree1.children();
                        String ss = nn[0].yield().toString();
                        ArrayList<Label> sss = nn[0].yield();
                        for (Label lab : sss) {
                        Annotation tokenAnnotation = new Annotation(lab.value());
                        pipeline.annotate(tokenAnnotation);  // necessary for the LemmaAnnotation to be set.
                        List<CoreMap> lista = tokenAnnotation.get(SentencesAnnotation.class);
                        String tokenLemma = lista
                        .get(0).get(TokensAnnotation.class)
                        .get(0).get(LemmaAnnotation.class);
                            try {
                                stringbuilder3.append(removeStopWords(tokenLemma)).append(" ");
                            } catch (Exception ex) {
                                Logger.getLogger(StanfordTripletsAndAspects.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        }
                        }
                        if (stringbuilder.length() > 0) {
                        Feautures.add(stringbuilder.toString().trim());
                        //System.out.println("Aspect: "+ stringbuilder.toString().trim());
                        bean.setAspect(stringbuilder.toString().trim());
                        }
                        if (stringbuilder3.length() > 1) {
                        // System.out.println("Verb: "+ stringbuilder3.toString().trim());
                        bean.setVerb(stringbuilder3.toString().trim());
                        } else {
                        bean.setVerb(null);
                        }
                        if (stringbuilder2.length() > 0) {
                        Feautures2.add(stringbuilder2.toString().trim());
                        // System.out.println("Opinion - " + stringbuilder2.toString().trim());
                        bean.setOpinion(stringbuilder2.toString().trim());
                        } else {
                        bean.setOpinion(null);
                        }
                        if (bean.getAspect() != null) {
                        Aspects.add(bean.getAspect());
                        }
                        beante.setAspect(bean.getAspect());
                        beante.setVerb(bean.getVerb());
                        beante.setOpinion(bean.getOpinion());
                        beante.setSentenceid(readLine.getSentenceid());
                        ListaAVO.add(beante);
                        }
                        for (AspectOpinionVerb beani : ListaAVO) {
                        if (beani.getAspect() != null && ((beani.getVerb() != null && !"".equals(beani.getVerb())) || beani.getOpinion() != null)) {
                        System.out.println("*********Insertion**********");
                        System.out.println(beani.getAspect() + "----" + beani.getVerb() + "------" + beani.getOpinion());
                        // dao.addaspectopinion(beani);
                        }
                        }
                        for(String AspectTerm:Aspects){
                        System.out.println("Aspect: "+AspectTerm);
                        SentenceAspect aspect =new SentenceAspect();
                        aspect.setSentenceid(readLine.getSentenceid());
                        aspect.setAspect(AspectTerm);
                        //dao.addaspectssentence(aspect);
                        }
                        
                        
                        //  }
                    });
                //});
                        */
                /*for (String lemmat : Feautures) {
                //System.out.println(lemmat+" - word");
                if (lemmat != null && Feautures.size() > 0) {
                int count = Collections.frequency(Feautures, lemmat);

                hashMap.add(lemmat.replaceAll("-", " ") + "-" + count);
                
                }
                }
                */
                /*
                LinkedHashMap<String, Integer> endmap = new LinkedHashMap<String, Integer>();
                for (String fr : hashMap) {
                
                String[] split = fr.split("-");
                System.out.println(split[1] + " - word -" + split[0]);
                endmap.put(split[0], Integer.parseInt(split[1]));
                }
                LinkedHashMap<String, Integer> sortMap = sortMap(endmap);
                PrintWriter writer = new PrintWriter("C:\\Users\\user1\\Documents\\NetBeansProjects\\WebApplication1\\train\\Aspects.txt", "UTF-8");
                for (Map.Entry< String, Integer> entry : sortMap.entrySet()) {
                System.out.println(entry.getValue() + " " + entry.getKey()); // will call toString by default
                
                writer.println(entry.getValue() + " - " + entry.getKey());
                
                }
                writer.close();
                
                for (String lemmat : Feautures2) {
                System.out.println(lemmat + " - word");
                if (lemmat != null && Feautures2.size() > 0) {
                int count = Collections.frequency(Feautures2, lemmat);

                hashMap2.add(lemmat.replaceAll("-", " ") + "-" + count);

                }
                }
                LinkedHashMap<String, Integer> endmap2 = new LinkedHashMap<String, Integer>();
                for (String fr : hashMap2) {
                
                String[] split = fr.split("-");
                System.out.println(split[1] + " - word -" + split[0]);
                endmap2.put(split[0], Integer.parseInt(split[1]));
                }
                LinkedHashMap<String, Integer> sortMap2 = sortMap(endmap2);
                PrintWriter writer2 = new PrintWriter("C:\\Users\\user1\\Documents\\NetBeansProjects\\WebApplication1\\train\\candidateOp.txt", "UTF-8");
                for (Map.Entry< String, Integer> entry : sortMap2.entrySet()) {
                System.out.println(entry.getValue() + " " + entry.getKey()); // will call toString by default
                
                writer2.println(entry.getKey() + " " + entry.getValue());
                */


            }

        }


    }

}


//}
