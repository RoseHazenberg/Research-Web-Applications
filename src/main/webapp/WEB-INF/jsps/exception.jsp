<%--
  Created by IntelliJ IDEA.
  User: Rose Hazenberg
  Date: 7-1-2022
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html SYSTEM "http://www.thymeleaf.org/dtd/xhtml1-strict-thymeleaf-4.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>Error Page</title>
</head>
<body>
<h3>Error on server side</h3>
<b>
  <%= request.getAttribute("javax.servlet.error.exception") %>
</b>
</body>
</html>
