package mx.edu.utez.persona.controller.persona;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
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

@WebServlet(name = "ServletPersona",
        urlPatterns = {
                "/get-personas",
                "/add-persona",
                "/create-persona",
                "/save-persona",
                "/get-persona",
                "/delete-persona"
        })


public class ServletPersona extends HttpServlet {
    java.util.logging.Logger logger = Logger.getLogger("ServletPersona");
    String action;
    String urlRedirect = "/get-personas";

    DaoPersona daoPersona = new DaoPersona();
    ServicePersona servicePersona = new ServicePersona();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        action = request.getServletPath();
        logger.log(Level.INFO,"path-> " + action);
        switch (action){
            case "/get-personas":
                List<BeanPersona> personas = daoPersona.findAll();
                System.out.println(personas.size());
                request.setAttribute("personas", personas);
                urlRedirect = "/views/persona/index.jsp";
                break;
            case "/create-persona":
                urlRedirect = "/views/persona/create.jsp";
                break;
            case "/get-persona":
                String id = request.getParameter("id");
                id = (id == null) ? "0" : id;
                try {
                    BeanPersona persona = daoPersona.findOne(Integer.parseInt(id));
                    request.setAttribute("persona", persona);
                    urlRedirect = "/views/persona/update.jsp";
                }catch (Exception e){
                    urlRedirect = "/get-personas";
                }
                break;
            default:
                request.setAttribute("personas", daoPersona.findAll());
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
        switch (action){
            case "/add-persona":
                try{
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
                    urlRedirect = "/get-personas?result="+
                            result.isResult() + "&message="+
                            URLEncoder.encode(result.getMessage(), StandardCharsets.UTF_8.name())
                            + "&status=" + result.getStatus();
                }catch (Exception e){
                    Logger.getLogger(ServletPersona.class.getName()).log(Level.SEVERE,
                            "Error addPersona method" + e.getMessage());
                    urlRedirect = "/get-personas?result=false&message=" +
                            URLEncoder.encode("Error al registrar a la persona",
                                    StandardCharsets.UTF_8.name())
                            + "&status=400";
                }
                break;
            case "/save-persona":
                String name2 = request.getParameter("name");
                String surname2 = request.getParameter("surname");
                String curp2 = request.getParameter("curp");
                String birthday2 = request.getParameter("birthday");
                System.out.println(name2);
                System.out.println(surname2);
                System.out.println(curp2);
                System.out.println(birthday2);
                BeanPersona persona2 = new BeanPersona();
                persona2.setName(name2);
                persona2.setSurname(surname2);
                persona2.setCurp(curp2);
                persona2.setBirthday(birthday2);
                ResultAction result2 = servicePersona.save(persona2);
                urlRedirect = "/get-personas?result="+
                        result2.isResult() + "&message="+ result2.getMessage()
                        + "&status=" + result2.getStatus();
                break;
            case "/delete-persona":
                String idPersona = request.getParameter("id");
                ResultAction deleteResult = servicePersona.delete(idPersona);
                urlRedirect = "/get-personas?result=" +
                        deleteResult.isResult() + "&message" +
                        URLEncoder.encode(deleteResult.getMessage(), StandardCharsets.UTF_8.name())
                        + "&status=" + deleteResult.getStatus();
                break;
            default:
                urlRedirect = "/get-personas";
                break;
        }
        response.sendRedirect(request.getContextPath() + urlRedirect);
    }
}
