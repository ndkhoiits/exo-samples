<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ page import="org.jcrportlet.portlet.*" %>
<%@ page import="java.util.*" %>



<portlet:defineObjects/>

<h1>hahahahah</h1>
<form enctype="multipart/form-data" action="<%= renderResponse.createActionURL() %>" method="POST">
   <div>
      <input type="file" name="fileupload" value="File">
      <input type="submit" value="Upload">
   </div>
</form>
<br/>
<table border="1">
   <thead style="background: aqua;">
      <tr>
         <td>Name</td>
         <td>Download</td>
      </tr>
   </thead>

<%
   Map<String, String> links = JCRPortlet.getDownloadLinks();
   Set<String> listKey = links.keySet();
   for (String key : listKey)
   {
      String downloadLink = links.get(key);
      %>
      <tr>
         <td><%=key %></td>
         <td><a href=<%=downloadLink %>>Download</a></td>
      </tr>
      <%
   }
%>

</table>