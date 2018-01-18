package Search;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphFactory;
import edu.stanford.nlp.semgraph.semgrex.SemgrexMatcher;
import edu.stanford.nlp.semgraph.semgrex.SemgrexPattern;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class SimpleRunner {

    public static void main(String[] args) throws IOException, Exception {
        InitAnnotator InitAnnotator = new InitAnnotator();
        StanfordCoreNLP pipeline = InitAnnotator.InitAnnotator();
        File f = new File("C:\\Users\\user1\\Documents\\NetBeansProjects\\WebApplication1\\train\\aa.txt");

        BufferedReader b = new BufferedReader(new FileReader(f));

        String readLine = "";

        HashSet<String> hashMap;
        hashMap = new HashSet();
        HashSet<String> hashMap2 = new HashSet();
        List<String> Feautures = new ArrayList();
        List<String> Feautures2 = new ArrayList();
        while ((readLine = b.readLine()) != null) {
            //System.out.println(readLine);

            //content = removeStopWords(content);
            Annotation document = new Annotation(readLine.replaceAll("\\.", " . ").replaceAll("((?=\\.{2})|(?!^)\\G)\\.", " "));
            // run all Annotators on this text
            pipeline.annotate(document);
            // Iterate over all of the sentences found

            for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
                System.out.println(sentence.toString());
                Tree tree = sentence.get(TreeAnnotation.class);
// Get dependency tree
                TreebankLanguagePack tlp = new PennTreebankLanguagePack();
                GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
                GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
                Collection<TypedDependency> td = gs.typedDependenciesCollapsed();
                SemanticGraph graph = SemanticGraphFactory.generateUncollapsedDependencies(tree);

               //System.out.println(graph);

    if(!graph.isEmpty()){
        SemgrexPattern semgrex = SemgrexPattern.compile("{}=Head >>/compound|amod|nmod|nsubj|dobj|xcomp|advmod|neg|conj/=D {}=Dep  ");
               SemgrexMatcher matcher = semgrex.matcher(graph);
    while (matcher.find()) {
                    Set<String> nodeNames = matcher.getNodeNames();
                    
                    for(String node :nodeNames){
                      System.err.println(node+"-- "+matcher.getRelnString("D")+" "+matcher.getNode(node)); 
                    }
           
      
    }
    System.err.println(graph.toString());
    SemgrexPattern semgrex1 = SemgrexPattern.compile("{tag:/JJ.*|NN.*/}=A [?>>/neg/ {}=neg] >>/amod/ {tag:/NN.*|JJ.*/}=C [?>>/neg/ {}=D] ");
               SemgrexMatcher matcher1 = semgrex1.matcher(graph);
    while (matcher1.find()) {
                    Set<String> nodeNames = matcher1.getNodeNames();
                    Set<String> nodeNamess = matcher1.getRelationNames();
                    //System.out.println("amod");
                  
                    for(String node :nodeNames){
                        
                       // System.err.println(node+" "+matcher1.getNode(node)); 
                    }
           
      
    }
    SemgrexPattern semgrex2 = SemgrexPattern.compile("{tag:/JJ.*/} >>/dobj/ {} >>nsubj {tag:/NN.*/}=C [?>>/compound/ {tag:/NN.*/}=D] ");
               SemgrexMatcher matcher2 = semgrex2.matcher(graph);
    while (matcher2.find()) {
                    Set<String> nodeNames = matcher2.getNodeNames();
                   // System.out.println("dobj -> nsubj ");
                    for(String node :nodeNames){
                        if(matcher2.getNode(node).tag().contains("NN")){
                       // System.err.println("Aspect - "+matcher2.getNode(node));
                        }else{
                        //System.err.println("Opinion - "+matcher2.getNode(node));

                        }
                    }
           
      
    }
     SemgrexPattern semgrex3 = SemgrexPattern.compile("{tag:/NN.*/} >>/nsubj/ {} >>dobj {tag:/NN.*/}=C ");
               SemgrexMatcher matcher3 = semgrex3.matcher(graph);
    while (matcher3.find()) {
                    Set<String> nodeNames = matcher3.getNodeNames();
                    
                    for(String node :nodeNames){
                     // System.err.println("Aspect - "+matcher3.getNode(node)); 
                    }
           
      
    }
    SemgrexPattern semgrex4 = SemgrexPattern.compile("{tag:/JJ.*|NN.*/}=A >>/amod/ {tag:/NN.*|JJ.*|/}=C ");
               SemgrexMatcher matcher4 = semgrex4.matcher(graph);
    while (matcher4.find()) {
                    Set<String> nodeNames = matcher4.getNodeNames();
                    
                    for(String node :nodeNames){
                        if(matcher4.getNode(node).tag().contains("NN")){
                          // System.err.println("--Aspect - "+matcher4.getNode(node)); 
                        }else{
                          // System.err.println("--Opinion "+matcher4.getNode(node)); 
                        }
                         
                    }
           
      
    }
    SemgrexPattern semgrex5 = SemgrexPattern.compile(" {tag:/JJ.*|NN.*/}=A >>/nmod/ {tag:/NN.*|JJ.*|/}=C   ");
               SemgrexMatcher matcher5 = semgrex5.matcher(graph);
    while (matcher5.find()) {
        
                    Set<String> nodeNames = matcher5.getNodeNames();
                    
                    for(String node :nodeNames){
                        if(matcher5.getNode(node).tag().contains("NN")){
                           //System.err.println("---nmod-"+matcher5.getNode(node)); 
                        }else{
                           // System.err.println("---nmod-JJ"+matcher5.getNode(node)); 
                        }
                         
                    }
           
      
    }
    }
                Object[] list = td.toArray();
                TypedDependency typedDependency;
                /* int mainSentiment1 = 0;
                    int longest1 = 0;
                    Annotation annotation1 = pipeline.process(sentence.toString());
       for (CoreMap sen : annotation1.get(CoreAnnotations.SentencesAnnotation.class)) {
                        Tree treeSen = sen.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                        int sentiment = RNNCoreAnnotations.getPredictedClass(treeSen);
                        String partText = sentence.toString();
                        if (partText.length() > longest1) {
                            mainSentiment1 = sentiment;
                            longest1 = partText.length();

                        }

                    }
                 */
                List<TypedDependency> WordsList = new ArrayList<TypedDependency>();
               
                 for (Object object1 : list) {
                     typedDependency = (TypedDependency) object1;
                    
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("nsubj")) {
            // System.out.println("nsubj : " + typedDependency.dep().word() + " " + typedDependency.gov().word());
                        
                    }
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("dobj")) {
                       
                    }
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("amod")) {
                        if ((typedDependency.gov().tag().contains("NN") && typedDependency.dep().tag().contains("JJ"))) {
                            Feautures2.add(typedDependency.dep().lemma().toLowerCase());
                            Feautures.add(typedDependency.gov().lemma().toLowerCase());

                        }
                        if ((typedDependency.dep().tag().contains("NN") && typedDependency.gov().tag().contains("JJ"))) {
                            Feautures2.add(typedDependency.gov().lemma().toLowerCase());
                            Feautures.add(typedDependency.dep().lemma().toLowerCase());
                        }
                    }
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("xcomp")) {
                        
                    }
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("neg")) {
                        
                    }
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("nmod")) {
                        
                    }
                    /*
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("root")) {
                        System.out.println("nn : " + typedDependency.dep().word() + " " + typedDependency.gov().word());

                    }

                    if (typedDependency.reln().getShortName().equalsIgnoreCase("nsubj")) {
                        if (typedDependency.dep().tag().contains("NN") && typedDependency.gov().tag().contains("JJ")) {
                            //Feautures.add(removeStopWords(typedDependency.dep().lemma().toLowerCase()));
                            //Feautures2.add(removeStopWords((typedDependency.gov().lemma().toLowerCase()))+" "+removeStopWords((typedDependency.dep().lemma().toLowerCase())) );
                            //System.out.println("nsubj : " + removeStopWords(typedDependency.dep().word()) + " " + removeStopWords(typedDependency.gov().word()));
                            WordsList.add(typedDependency);
                        }

                    }
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("dobj")) {
                        WordsList.add(typedDependency);
                        System.out.println("dobj : " + removeStopWords(typedDependency.dep().word()) + " " + removeStopWords(typedDependency.gov().word()));

                    }
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("amod")) {
                        if ((typedDependency.gov().tag().contains("NN") && typedDependency.dep().tag().contains("JJ"))) {
                            Feautures2.add(removeStopWords(typedDependency.dep().lemma().toLowerCase()));
                            Feautures.add(removeStopWords(typedDependency.gov().lemma().toLowerCase()));

                        }
                        if ((typedDependency.dep().tag().contains("NN") && typedDependency.gov().tag().contains("JJ"))) {
                            Feautures2.add(removeStopWords(typedDependency.gov().lemma().toLowerCase()));
                            Feautures.add(removeStopWords(typedDependency.dep().lemma().toLowerCase()));
                        }
                        System.out.println("Amod : " + removeStopWords(typedDependency.dep().word()) + " " + removeStopWords(typedDependency.gov().word()));
                        WordsList.add(typedDependency);
                    }
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("compound")) {
                        System.out.println("Compound : " + removeStopWords(typedDependency.dep().lemma()) + " " + removeStopWords(typedDependency.gov().lemma()));
                        WordsList.add(typedDependency);
//Feautures.add(removeStopWords(typedDependency.dep().lemma())+ " " + removeStopWords(typedDependency.gov().lemma()));
                    }
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("nmod")) {

                        System.out.println("nmod : " + removeStopWords(typedDependency.dep().word()) + " " + removeStopWords(typedDependency.gov().word()));

                        WordsList.add(typedDependency);

                    }
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("neg")) {
                        if (typedDependency.gov().tag().contains("JJ")) {
                            System.out.println("neg : " + removeStopWords(typedDependency.dep().word()) + " " + removeStopWords(typedDependency.gov().word()));
                        }
                        WordsList.add(typedDependency);
                    }
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("xcomp")) {
                        System.out.println("xcomp : " + removeStopWords(typedDependency.dep().word()) + " " + removeStopWords(typedDependency.gov().word()));
                        WordsList.add(typedDependency);
                    }
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("acomp")) {
                        System.out.println("acomp : " + removeStopWords(typedDependency.dep().word()) + " " + removeStopWords(typedDependency.gov().word()));
                        WordsList.add(typedDependency);
                    }
                    if (typedDependency.reln().getShortName().equalsIgnoreCase("advmod")) {
                        System.out.println("advmod : " + removeStopWords(typedDependency.dep().word()) + " " + removeStopWords(typedDependency.gov().word()));
                        WordsList.add(typedDependency);
                    }
*/
                    //if((typedDependency.dep().tag().equalsIgnoreCase("NN") && typedDependency.gov().tag().equalsIgnoreCase("JJ")) || (typedDependency.dep().tag().equalsIgnoreCase("NN") && typedDependency.gov().tag().equalsIgnoreCase("NN"))||(typedDependency.dep().tag().equalsIgnoreCase("NN") && typedDependency.gov().tag().equalsIgnoreCase("NNs"))){
                    // if(typedDependency.dep().tag().equalsIgnoreCase("NN") || typedDependency.gov().tag().equalsIgnoreCase("NN")){
                    int mainSentiment = 0;
                    int longest = 0;
                    Annotation annotation = pipeline.process(typedDependency.dep().word() + " " + typedDependency.gov().word());
                    for (CoreMap sen : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                        Tree treeSen = sen.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                        int sentiment = RNNCoreAnnotations.getPredictedClass(treeSen);
                        String partText = sentence.toString();
                        if (partText.length() > longest) {
                            mainSentiment = sentiment;
                            longest = partText.length();

                        }

                        //System.out.println("Phrase: " + typedDependency.dep().word() + " " + typedDependency.gov().word() + " sentiment: " + (mainSentiment-mainSentiment1));
                        //System.out.println("Depdency Name::" + typedDependency.dep() + " :: " + typedDependency.gov() + " Node:" + typedDependency.reln());
                        // Feautures.add(typedDependency.dep().word().toLowerCase());
                        if (typedDependency.gov().tag() != null) {
                            if (typedDependency.gov().tag().equalsIgnoreCase("nn") || typedDependency.gov().tag().equalsIgnoreCase("nns")) {
                                if (typedDependency.gov().lemma() != null) {
                                    //Feautures.add(typedDependency.gov().word().toLowerCase());
                                }
                            }
                        }
                        if (typedDependency.dep().tag() != null) {
                            if (typedDependency.dep().tag().equalsIgnoreCase("nn") || typedDependency.dep().tag().equalsIgnoreCase("nns")) {
                                if (typedDependency.dep().lemma() != null) {
                                    // Feautures.add(typedDependency.dep().lemma().toLowerCase());
                                }

                            }
                        }
                        //}
                    }
                    // }
//if (typedDependency.reln().getShortName().equals("nsubj")) {
                    // }
                }

                for (TypedDependency word : WordsList) {
                    Set<String> Finalised = new HashSet<String>();
                    for (TypedDependency word1 : WordsList) {

                        if (word.gov().word().equalsIgnoreCase(word1.dep().word()) || word.dep().word().equalsIgnoreCase(word1.dep().word()) || word.gov().word().equalsIgnoreCase(word1.gov().word()) || word.dep().word().equalsIgnoreCase(word1.gov().word())) {
                            Finalised.add(word.gov().word());
                            Finalised.add(word.dep().word());
                            Finalised.add(word1.gov().word());
                            Finalised.add(word1.dep().word());
                        }
                    }
                    String str_1 = StringUtils.join(Finalised, " ");
                    for (String wordi : Finalised) {
                        //System.out.println("lexeis mazi: "+str_1);
                    }
                }

            }

        }

        for (String lemmat : Feautures) {
//System.out.println(lemmat+" - word");
            if (lemmat != null && Feautures.size() > 0) {
                int count = Collections.frequency(Feautures, lemmat);

                hashMap.add(lemmat.replaceAll("-", " ") + "-" + count);

            }
        }

        LinkedHashMap<String, Integer> endmap = new LinkedHashMap<String, Integer>();
        for (String fr : hashMap) {

            String[] split = fr.split("-");
            System.out.println(split[1] + " - word -" + split[0]);
            endmap.put(split[0], Integer.parseInt(split[1]));
        }
        LinkedHashMap<String, Integer> sortMap = sortMap(endmap);
        PrintWriter writer = new PrintWriter("C:\\Users\\user1\\Documents\\NetBeansProjects\\WebApplication1\\train\\candidatesopAmod.txt", "UTF-8");
        for (Map.Entry< String, Integer> entry : sortMap.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey()); // will call toString by default

            writer.println("Frequency :" + entry.getValue() + "  Candidate Opinions :" + entry.getKey());

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
        PrintWriter writer2 = new PrintWriter("C:\\Users\\user1\\Documents\\NetBeansProjects\\WebApplication1\\train\\candidatesAmodOp.txt", "UTF-8");
        for (Map.Entry< String, Integer> entry : sortMap2.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey()); // will call toString by default

            writer2.println("Frequency :" + entry.getValue() + "  Candidate Doubles :" + entry.getKey());

        }
        writer2.close();

    
    }

   

    public static LinkedHashMap<String, Integer> sortMap(LinkedHashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> capitalList = new LinkedList<>(map.entrySet());

        Collections.sort(capitalList, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        for (int i = capitalList.size() - 1; i >= 0; i--) {
            result.put(capitalList.get(i).getKey(), capitalList.get(i).getValue());
        }

        return result;
    }
}
