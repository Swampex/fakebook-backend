package ru.fakebook.pet.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.server.ResponseStatusException;
import ru.fakebook.pet.security.captcha.GoogleResponse;
import ru.fakebook.pet.security.captcha.InvalidReCaptchaException;
import ru.fakebook.pet.security.captcha.ReCaptchaInvalidException;
import ru.fakebook.pet.security.config.CaptchaSettings;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class CaptchaFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private CaptchaSettings captchaSettings;

    @Autowired
    private RestTemplate restTemplate;
    private static final String POST = "POST";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        if(restTemplate==null) {
            ServletContext servletContext = req.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            restTemplate = webApplicationContext.getBean(RestTemplate.class);
        }

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        if(request.getRequestURI().indexOf("login") != -1 && request.getMethod().equals(POST)){
            try {
                processResponse(request.getParameter("captchaValue"));
            } catch (ReCaptchaInvalidException | InvalidReCaptchaException e) {
//                this.unsuccessfulAuthentication(request, response, e);
                response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
                return;
            }
            chain.doFilter(request, response);
        } else {
            //No captcha validation, so keep going through next filter in chain
            chain.doFilter(request, response);
        }
    }

    private Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

    private void processResponse(String response) {
        if(!responseSanityCheck(response)) {
            throw new InvalidReCaptchaException("Response contains invalid characters");
        }

        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                "6LcwtugUAAAAADdSCZTTQ79Sjc2kGQtr14Wt3BbC", response, "192.168.1.70"));

        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);

        if(!googleResponse.isSuccess()) {
            throw new ReCaptchaInvalidException("reCaptcha was not successfully validated");
        }
    }

    private boolean responseSanityCheck(String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }

}
