package org.example.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.servlets.domain.User;
import org.example.servlets.services.UserStorageService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CRUDServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();
    private final static String GET = "GET";
    private final static String POST = "POST";
    private final static String DELETE = "DELETE";

    private final AtomicInteger atomicInteger = new AtomicInteger();

    private UserStorageService userStorageService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", this.userStorageService.getUsers());
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = new User(UUID.randomUUID(),
                req.getParameter("name"),
                req.getParameter("surname"),
                Integer.parseInt(req.getParameter("age")),
                req.getParameter("email"));
        this.userStorageService.addUser(user);
        logger.info(String.format("User %s was added!", user));
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        UUID userId = UUID.fromString(req.getParameter("id"));
        this.userStorageService.deleteUser(userId);
        logger.info(String.format("User by userId %s was deleted!", userId));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = new User(UUID.fromString(req.getParameter("id")),
                req.getParameter("name"),
                req.getParameter("surname"),
                Integer.parseInt(req.getParameter("age")),
                req.getParameter("email"));
        this.userStorageService.updateUser(user);
        logger.info(String.format("User %s was updated!", user));
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = new Cookie("numberOfJspViews", String.valueOf(atomicInteger.incrementAndGet()));
        resp.addCookie(cookie);

        String stringId = req.getParameter("id");
        String method = req.getMethod();
        String type = req.getParameter("type");

        if (method.equalsIgnoreCase(GET)) {
            doGet(req, resp);
        } else if (method.equalsIgnoreCase(POST) && stringId.isEmpty()) {
            doPost(req, resp);
        } else if (method.equalsIgnoreCase(POST) && !stringId.isEmpty() && type == null) {
            doPut(req, resp);
        } else if (method.equalsIgnoreCase(POST) && DELETE.equalsIgnoreCase(type)) {
            doDelete(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.userStorageService = new UserStorageService();
    }
}
