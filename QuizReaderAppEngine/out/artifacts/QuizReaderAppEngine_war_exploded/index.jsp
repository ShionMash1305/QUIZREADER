<%@ page import="com.developer.shion.Home" %><%--
  Created by IntelliJ IDEA.
  User: shion
  Date: 2019/03/31
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.developer.shion.Home" %>
<html>
  <head>
    <link href='//fonts.googleapis.com/css?family=Marmelad' rel='stylesheet' type='text/css'>
    <title>Hello App Engine Standard Java 8</title>
  </head>
  <body>
  <h1>Hello App Engine -- Java 8!</h1>
  <form method="POST" enctype="multipart/form-data" action="./upload">
    <input type="file" name="file" multiple/><br />
    <input type="submit" value="アップロード" />
  </form>

  <p>This is <%= Home.getInfo() %>.</p>
  <table>
    <tr>
      <td colspan="2" style="font-weight:bold;">Available Servlets:</td>
    </tr>
    <tr>
      <td><a href='/hello'>Hello App Engine</a></td>
    </tr>
  </table>
  </body>
</html>
