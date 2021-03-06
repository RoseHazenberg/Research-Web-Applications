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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@WebServlet(name = "HomeServlet", urlPatterns = "/home", loadOnStartup = 1)
public class HomeServlet extends HttpServlet {
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

        //Get the month name of the current date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
        String month = simpleDateFormat.format(new Date());
        //Set the month as variable to use in html
        ctx.setVariable("month", month);
        WebConfig.createTemplateEngine(getServletContext()).
                process("home", ctx, response.getWriter());

    }
}
