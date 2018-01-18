/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Beans.UserBean;
import Controllers.Home;
import DAO.DaoUser;
import Filters.PasswordEncytDecrypt;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user1
 */
public class SignUpHandler {

    public void Signup(HttpServletRequest request, DaoUser dao, HttpSession session) {
       Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            UserBean User = new UserBean();
            Filters.EmailValidation validate =  new Filters.EmailValidation();
            
            boolean validate1email = validate.validateemail((String) request.getParameter("EmailUser"));
            boolean validatepass = validate.validatePass(request.getParameter("PasswordUser").toString());

            if (validate1email && validatepass) {
                try {
                    PasswordEncytDecrypt Encryptor =new PasswordEncytDecrypt();
                    String input = (String) request.getParameter("PasswordUser");
                    System.out.println("Entered: " + input);
                    String encryptionBytes = Encryptor.encrypt(input);
                    String passw=encryptionBytes;
                    User.setEmail((String) request.getParameter("EmailUser"));
                    User.setPass(passw);
                    User.setFirstname((String) request.getParameter("FirstName"));
                    User.setlastname((String) request.getParameter("LastName"));
                    User.setcountry((String) request.getParameter("nationality"));
                    User.setGender((String) request.getParameter("Gender"));
                    User.setDate(sqlDate);
                    User.setGender((String) request.getParameter("Gender"));
                    request.setAttribute("bean", User);
                    List<UserBean> userByEmail = dao.getUserByEmail((String) request.getParameter("EmailUser"));
                    if(userByEmail.isEmpty()){
                        dao.addUser(User);
                        session.setAttribute("user", User);
                    }else{
                        request.setAttribute("ErrorValidation", "Enter a valid email and a password with 6-12 letters/digits !");
                    }
                    //System.out.println(StringUtils.getLevenshteinDistance("releva", "relevant", 2));
                  
                } catch (Exception ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                request.setAttribute("ErrorValidation", "Enter a valid email and a password with 6-12 letters/digits !");
              
            }
    }
    
}
