/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;


import Beans.UserBean;
import Filters.PasswordEncytDecrypt;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author user1
 */
public class FacebookSignup {
    public UserBean GetEventsHandler(String text) throws UnsupportedEncodingException {
        UserBean User =new UserBean();
        JSONObject jsonObject;
           System.out.println(text);
            jsonObject = new JSONObject(URLDecoder.decode(text, "UTF-8"));
            if(jsonObject.get("email")!=null){
        String email = jsonObject.get("email").toString();
        User.setEmail(email);
            }
            if(jsonObject.get("name")!=null){
        String name = jsonObject.get("name").toString();
        User.setFirstname(name.split(" ")[0]);
        User.setlastname(name.split(" ")[1]);
            }
            if(jsonObject.get("gender")!=null){
        String gender = jsonObject.get("gender").toString();
         User.setGender(gender);
            }
            if(jsonObject.get("id")!=null){
            try {
                PasswordEncytDecrypt Encryptor =new PasswordEncytDecrypt();
                String input = (String)jsonObject.get("id");
                //System.out.println("Entered: " + input);
                String encryptionBytes = Encryptor.encrypt(input);
               
                String id = (String)jsonObject.get("id");
                User.setPass(encryptionBytes);
            } catch (Exception ex) {
                Logger.getLogger(FacebookSignup.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        return User;
            
            }
     
            
            }
    

