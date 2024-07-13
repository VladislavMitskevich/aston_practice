package org.examle.aston_practice.spellbook.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.service.SpellService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Servlet for handling spell-related requests
 */
@WebServlet("/spells/*")
public class SpellServlet extends HttpServlet {

    private final SpellService spellService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SpellServlet(SpellService spellService) {
        this.spellService = spellService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<SpellDTO> spells = spellService.findAll();
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(spells));
        } else {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 2) {
                Long id = Long.parseLong(pathParts[1]);
                Optional<SpellDTO> spellDTO = spellService.findById(id);
                if (spellDTO.isPresent()) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(objectMapper.writeValueAsString(spellDTO.get()));
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else if (pathParts.length == 3 && pathParts[1].contains("(") && pathParts[1].contains(")")) {
                String[] classAndCircle = pathParts[1].split("\\(");
                String spellClass = classAndCircle[0];
                String circle = classAndCircle[1].replace(")", "");
                List<SpellDTO> spells = spellService.findByClassAndCircle(spellClass, circle);
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(spells));
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SpellDTO spellDTO = objectMapper.readValue(req.getInputStream(), SpellDTO.class);
        spellService.save(spellDTO);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SpellDTO spellDTO = objectMapper.readValue(req.getInputStream(), SpellDTO.class);
        spellService.update(spellDTO);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.split("/").length == 2) {
            Long id = Long.parseLong(pathInfo.split("/")[1]);
            spellService.delete(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
