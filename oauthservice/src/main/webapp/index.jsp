<html>
<head>
  <style type="text/css">
  .main {
    font-family: TimesNewRoman, Arial, Helvetica, serif;                                                                       
    font-style: normal;                                                                                                        
    font-size: 10pt;
  }  
  </style>
</head>
<body>
  <div class="main">
	  <h3>This is protected resource. Principal <%=request.getParameter("oauth_user_principal")%></h3>
  </div>
</body>
</html>
