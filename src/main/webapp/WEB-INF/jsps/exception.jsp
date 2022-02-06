<%--
  Created by IntelliJ IDEA.
  User: Rose Hazenberg & Akastia Christo
  Date: 7-1-2022
  Time: 15:19
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
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
