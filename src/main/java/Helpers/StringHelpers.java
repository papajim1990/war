/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

/**
 *
 * @author user1
 */
public class StringHelpers {
    public String escapeMetaCharacters(String inputString){
    final String[] metaCharacters = {"\\","^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","\"","\'"};
    String outputString="";
    for (int i = 0 ; i < metaCharacters.length ; i++){
        if(inputString.contains(metaCharacters[i])){
            outputString = inputString.replaceAll(metaCharacters[i],"\\"+metaCharacters[i]);
            inputString = outputString;
        }
    }
    return outputString;
}
    
}
