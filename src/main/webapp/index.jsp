<%-- 
    Document   : index
    Created on : Nov 7, 2017, 2:04:58 AM
    Author     : user1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action='Home?action=CalculateAspectsHotel' method="get">
            
            <input type='submit' />
            
        </form>
        
       <form  method="post" action="Home?action=startSentiment">
      
      <input type="submit" value="startSentiment">     
      <input type="submit" value="geoloc"> 
      </form>
    </body>
</html>
