package tech.mathieu.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = "/*")
public class FrontendFilter extends HttpFilter {

  private static final Pattern FILE_NAME_PATTERN = Pattern.compile(".*[.][a-zA-Z\\d]+");

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    chain.doFilter(request, response);

    if (response.getStatus() == 404) {
      String path =
          request.getRequestURI().substring(request.getContextPath().length()).replace("/", "");
      if (!FILE_NAME_PATTERN.matcher(path).matches()) {
        // We could not find the resource, i.e. it is not anything known to the server (i.e. it is
        // not a REST
        // endpoint or a servlet), and does not look like a file so try handling it in the front-end
        // routes
        // and reset the response status code to 200.
        try {
          response.setStatus(200);
          request.getRequestDispatcher("/").forward(request, response);
        } finally {
          response.getOutputStream().close();
        }
      }
    }
  }
}
