/**
 * 
 */
package session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayServlet
 * @author wang8
 */
public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			sqlDisplay = "SELECT photo FROM images WHERE owner_name = " + username + "OR" + "permitted = " + username + '"';
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(sqlDisplay)
			while(rs.next()) {
				response.setContentType("image/jpg");
				response.setHeader("Content-Disposition","attachment;filename=" + String.valueOf(rs.getInt(1)) + "_" + 
						String.valueOf(rs.getInt(2))+ "_" + String.valueOf(rs.getDate(3)) + ".jpg" ); //save in jpg
				if (full.equals("yes")){
					bytes = rs.getBytes(6);
				}
				else {
					bytes = rs.getBytes(5);
				}
				
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
				OutputStream out = response.getOutputStream();
				ImageIO.write(bufferedImage, "jpg", out);
				out.close();
			}
        	}
 
		}
		catch(Exception ex) {
			out.println("Error photos not found");
		}

        if (valid == false) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            out.println("<font color=red>There has been a mistake in your credential submission.  Please try again.</font>");
            rd.include(request, response);
        }
 
    }
 
}
	
	/*
	 * Check username/password do not match an existing account.
	 */
	protected boolean isValidRegistration(String user, String pass) {
		boolean isValid = false;
		
		try {
			isValid = CredentialHandler.getInstance().isValidLogin(user, pass);
		}
		catch(Exception ex) {
			System.out.println("<hr>" + ex.getMessage() + "<hr>");
		}
		
		return isValid;
	}


}
