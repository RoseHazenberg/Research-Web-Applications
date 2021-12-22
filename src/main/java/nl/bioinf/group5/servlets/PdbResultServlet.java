package nl.bioinf.group5.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(urlPatterns = "/output")
public class PdbResultServlet extends HttpServlet {
    private final int ARBITRARY_SIZE = 1048;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        String sessionid = session.getId();

        String outputFolder = getServletContext().getInitParameter("upload");
        String outFile = outputFolder + sessionid + ".pdb";

        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=" + outFile);


        /* Read in the file and write to the OutputStream of the response */
        try (InputStream in = new FileInputStream(outFile);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[ARBITRARY_SIZE];

            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
