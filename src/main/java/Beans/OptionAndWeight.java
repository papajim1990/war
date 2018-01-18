/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.HashMap;

/**
 *
 * @author user1
 */
public class OptionAndWeight {
    private HashMap<String,String> options;

     public HashMap getOptions(){
        return options;
        }
    public void setOptions(HashMap options){
        this.options=options;
    }
}
