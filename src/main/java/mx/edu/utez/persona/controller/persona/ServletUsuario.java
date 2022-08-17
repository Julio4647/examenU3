package mx.edu.utez.persona.controller.persona;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.annotation.*;
import mx.edu.utez.persona.model.persona.BeanPersona;
import mx.edu.utez.persona.model.persona.DaoPersona;
import mx.edu.utez.persona.service.persona.ServicePersona;
import mx.edu.utez.persona.utils.ResultAction;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ServletUsuario",
        urlPatterns = {
                "/get-usuarios",
                "/get-user",
                "/create-user",
                "/add-user",
                "/save-user",
                "/delete-user"
        })


public class ServletUsuario extends HttpServlet {
    Logger logger = Logger.getLogger("ServletUsuario");
    String action;
    DaoPersona daoPersona = new DaoPersona();
    ServicePersona servicePersona = new ServicePersona();
    String urlRedirect = "/get-usuarios";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        action = request.getServletPath();
        logger.log(Level.INFO, "Path-> " + action);
        switch (action) {
            case "/get-usuarios":
                List<BeanPersona> personas = daoPersona.findAll();
                request.setAttribute("personas", personas);
                urlRedirect = "/views/persona/index.jsp";
                break;
            case "/create-user":
                urlRedirect = "/views/persona/create.jsp";
                break;
            case "/get-user":
                String id = request.getParameter("id");
                id = (id == null) ? "0" : id;
                try {
                    BeanPersona persona = daoPersona.findOne(Integer.parseInt(id));
                    request.setAttribute("persona", persona);
                    urlRedirect = "/views/persona/update.jsp";
                } catch (Exception e) {
                    urlRedirect = "/get-usuarios";
                }
                break;
            default:
                request.setAttribute("libros", daoPersona.findAll());
                urlRedirect = "/get-personas";
                break;
        }
        request.getRequestDispatcher(urlRedirect).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        action = request.getServletPath();
        switch (action) {
            case "/add-user":
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String curp = request.getParameter("curp");
                String birthday = request.getParameter("birthday");
                System.out.println(name);
                System.out.println(surname);
                System.out.println(curp);
                System.out.println(birthday);
                BeanPersona persona = new BeanPersona();
                persona.setName(name);
                persona.setSurname(surname);
                persona.setCurp(curp);
                persona.setBirthday(birthday);
                ResultAction result = servicePersona.save(persona);
                urlRedirect = "/get-usuarios?result=" +
                        result.isResult() + "&message=" + result.getMessage()
                        + "&status=" + result.getStatus();
                break;
            case "/save-user":
                String name2 = request.getParameter("name");
                String surname2 = request.getParameter("surname");
                String curp2 = request.getParameter("curp");
                String birthday2 = request.getParameter("birthday");
                String id = request.getParameter("id");
                System.out.println(name2);
                System.out.println(surname2);
                System.out.println(curp2);
                System.out.println(birthday2);
                BeanPersona persona2 = new BeanPersona();
                persona2.setName(name2);
                persona2.setSurname(surname2);
                persona2.setCurp(curp2);
                persona2.setBirthday(birthday2);
                persona2.setId(Integer.parseInt(id));
                ResultAction result2 = servicePersona.update(persona2);
                urlRedirect = "/get-usuarios?result=" +
                        result2.isResult() + "&message=" + result2.getMessage()
                        + "&status=" + result2.getStatus();
                break;
            case "/delete-user":
                String idUser = request.getParameter("id");
                ResultAction deleteResult = servicePersona.delete(idUser);
                urlRedirect = "/get-usuarios?result=" +
                        deleteResult.isResult() + "&message=" +
                        URLEncoder.encode(deleteResult.getMessage(), StandardCharsets.UTF_8.name())
                        + "&status=" + deleteResult.getStatus();
                break;
            default:
                urlRedirect = "/get-usuarios";
                break;
        }
        response.sendRedirect(request.getContextPath() + urlRedirect);
    }
}
