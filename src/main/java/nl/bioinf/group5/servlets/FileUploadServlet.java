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


@WebServlet(name = "FileUploadServlet", urlPatterns = {"/input", "/data_upload"})
public class FileUploadServlet extends HttpServlet {
    private String uploadDir;
    @Override
    public void init() throws ServletException {
        this.uploadDir = getInitParameter("upload_dir");
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
        File fileSaveDir = new File(this.uploadDir);
        String fileName;
        for (Part part : request.getParts()) {
            fileName = part.getSubmittedFileName();
            part.write(this.uploadDir + File.separator + fileName);
        }

        //This generates a file name something like this
        //molecule14260971264207930189.pdb
        File generatedFile = File.createTempFile("molecule", ".pdb");
        for (Part part : request.getParts()) {
            part.write(this.uploadDir + File.separator + generatedFile.getName());
        }

        //go back to the upload form
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
        WebConfig.createTemplateEngine(getServletContext()).
                process("input", ctx, response.getWriter());
    }

}