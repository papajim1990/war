package com.mycompany.mavenproject2.Search.Search;

import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.List;

public class SpellChecker {
    private static JLanguageTool langTool = new JLanguageTool(new BritishEnglish());

    public void correctSpellCheck(String sentence){
        // comment in to use statistical ngram data:
        //langTool.activateLanguageModelRules(new File("/data/google-ngram-data"));
        List<RuleMatch> matches = null;
        try {
            matches = langTool.check(sentence);

        for (RuleMatch match : matches) {

            for (String rep:match.getSuggestedReplacements()) {
                sentence = sentence.replace(sentence.substring(match.getFromPos(), match.getToPos()), rep);
                System.out.println(sentence);
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
