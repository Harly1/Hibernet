package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private final DBService dbService;

    public SignInServlet(DBService dbService) {

        this.dbService = dbService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");



        try {
            String loginFromBD = dbService.getUser(login,password).getLogin();
            String passwordFromBD = dbService.getUser(login,password).getPassword();
            if (login == loginFromBD || password == passwordFromBD){
                Authorized(request,response,login);
            } else {
                Unauthorized(request,response);
            }

        } catch (NullPointerException | DBException e) {
            Unauthorized(request,response);
            e.printStackTrace();
        }

    }

    public static void Unauthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(401);
        response.getWriter().println("Unauthorized");
    }
    public static void Authorized(HttpServletRequest request, HttpServletResponse response, String login) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(200);
        response.getWriter().println("Authorized: " + login);
    }
}
