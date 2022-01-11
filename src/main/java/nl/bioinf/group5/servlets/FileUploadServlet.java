package nl.bioinf.group5.servlets;

import nl.bioinf.group5.config.WebConfig;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/input", "/data_upload"})
public class FileUploadServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        final ServletContext servletContext = this.getServletContext();
        WebConfig.createTemplateEngine(servletContext);
    }
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext ctx = new WebContext(
                request,
                response,
                request.getServletContext(),
                request.getLocale());
        WebConfig.createTemplateEngine(getServletContext()).
                process("input", ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String outputFolder = getInitParameter("upload");
        Files.createDirectories(Paths.get(outputFolder));
        File fileSaveDir = new File(outputFolder);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
            throw new IllegalStateException("Upload dir does not exist: " + outputFolder);
        }

        HttpSession session = request.getSession(true);
        String sessionid = session.getId();

        //This generates a file name something like this
        //14260971264207930189.pdb
        for (Part part : request.getParts()) {
            part.write(outputFolder + sessionid + ".pdb");
        }

        WebContext ctx = new WebContext(
                request,
                response,
                request.getServletContext(),
                request.getLocale());


        Part filePart = request.getPart("pdb_data");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        //Defines the next page
        String nextPage;

        System.out.println("fileName = " + fileName);
        if (fileName.isEmpty()) {
            ctx.setVariable("message", "There is no file provided; please try again");
            ctx.setVariable("message_type", "error");
            nextPage = "input";
        } else {
            nextPage = "atom";
        }

        WebConfig.createTemplateEngine(getServletContext()).
                process(nextPage, ctx, response.getWriter());
    }
}