<html>
<head>
</head>

<body>

	<%@ include file="connectdb.jsp" %>
<%
		String photoID = request.getParameter("pid");
  		Statement stmt = null;
        ResultSet rset = null;
    	String sql = "select permitted, subject, place, description from images where photo_id = "+photoID+" ";
    	try{
        	stmt = conn.createStatement();
	        rset = stmt.executeQuery(sql);	
    	}
        catch(Exception ex){
	        out.println("<hr>" + ex.getMessage() + "<hr>");
    	}
%> 	
	<%  while(rset.next()){ %>
			<form method="post" action="UpdateImageInfo">
        		Permission: <input name="per" type="text" value=<%= rset.getString(1)%> >  (Enter a group_id, 1 is public, 2 is private. group_id can be found in "Groups") </br>  
        		Subject: <input name="sub" type="text" value=<%= rset.getString(2)%> > </br>
        		Place: <input name="pla" type="text" value=<%= rset.getString(3)%> > </br>
        		Description: <input name="des" type="text" value=<%= rset.getString(4)%> > </br>
                When: <input name="when" type="date" >  (Default will be sysdate!) </br>  

        		<input hidden name="pid" type="text" value= <%= photoID%> >	
        		<input type="submit" value="Update">
        	</form>
    <% } %>


	<%@ include file="closedb.jsp" %>


</body>	



