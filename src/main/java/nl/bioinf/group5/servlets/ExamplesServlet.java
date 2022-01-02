package nl.bioinf.group5.servlets;
import nl.bioinf.group5.config.WebConfig;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ExamplesServlet", urlPatterns = {"/examples"}, loadOnStartup = 1)
public class ExamplesServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("Initializing Thymeleaf template engine");
        final ServletContext servletContext = this.getServletContext();
        WebConfig.createTemplateEngine(servletContext);
    }
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        process(request, response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        process(request, response);
    }
    public void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //this step is optional; standard settings also suffice
        WebConfig.configureResponse(response);
        WebContext ctx = new WebContext(
                request,
                response,
                request.getServletContext(),
                request.getLocale());

        String molecule = request.getParameter("molecule");

        if (molecule.startsWith("1igt")) {
            WebConfig.createTemplateEngine(getServletContext()).
                    process("1igt", ctx, response.getWriter());
        } else if (molecule.startsWith("1svc")) {
            WebConfig.createTemplateEngine(getServletContext()).
                    process("1svc", ctx, response.getWriter());
        } else if (molecule.startsWith("2uv8a")) {
            WebConfig.createTemplateEngine(getServletContext()).
                    process("2uv8a", ctx, response.getWriter());
        } else if (molecule.startsWith("7kj2")) {
            WebConfig.createTemplateEngine(getServletContext()).
                    process("7kj2", ctx, response.getWriter());
        } else if (molecule.startsWith("7kx4")) {
            WebConfig.createTemplateEngine(getServletContext()).
                    process("7kx4", ctx, response.getWriter());
        }
    }
}
