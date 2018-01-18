/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handlers;

import Beans.UserBean;
import DAO.DaoUser;
import Filters.PasswordEncytDecrypt;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user1
 */
public class LoginHandler {

    public void Login(HttpServletRequest request, DaoUser dao, HttpSession session) {
        try {
            UserBean User = new UserBean();
            PasswordEncytDecrypt Encryptor = new PasswordEncytDecrypt();
            String input = (String) request.getParameter("passwordLogin");
            User.setEmail((String) request.getParameter("emailLogin"));
            String encryptionBytes = Encryptor.encrypt(input);
            String passw = encryptionBytes;
            
            
            List<UserBean> userByEmail = dao.getUserByEmail(User.getEmail());
            if (!userByEmail.isEmpty()) {
                if (userByEmail.get(0).getPass().equals(passw)) {
                    
                    session.setAttribute("user", userByEmail.get(0));
                    session.removeAttribute("ErrorValidation-Fb");
                } else {
                    String b = User.getPass();
                    String end=Encryptor.decrypt(b);
                    System.out.println(end);
                    session.setAttribute("ErrorValidation-Fb", "Your typed a wrong password try again !");
                }
            } else {
                session.setAttribute("ErrorValidation-Fb", "There is no account with that information create an acount to continue !");
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
