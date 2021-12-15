package nl.bioinf.group5.servlets;

import nl.bioinf.group5.config.WebConfig;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
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
        String outputFolder = getServletContext().getInitParameter("output");
        Files.createDirectories(Paths.get(outputFolder));

        String fileName;
        for (Part part : request.getParts()) {
            fileName = part.getSubmittedFileName();
            part.write(outputFolder + File.separator + fileName);
        }

        //This generates a file name something like this
        //molecule14260971264207930189.pdb
        File generatedFile = File.createTempFile("molecule", ".pdb");
        for (Part part : request.getParts()) {
            part.write(outputFolder + File.separator + generatedFile.getName());
        }

        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
        WebConfig.createTemplateEngine(getServletContext()).
                process("atom", ctx, response.getWriter());
    }

}