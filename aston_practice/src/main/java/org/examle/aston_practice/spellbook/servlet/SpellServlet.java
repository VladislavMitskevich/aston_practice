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

/**
 * Servlet for handling HTTP requests for spells.
 */
@WebServlet("/spells")
public class SpellServlet extends HttpServlet {

    private SpellService spellService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        spellService = (SpellService) getServletContext().getAttribute("spellService");
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String classParam = req.getParameter("class");
        String circleParam = req.getParameter("circle");
        String schoolParam = req.getParameter("school");

        List<SpellDTO> spells;

        if (classParam != null) {
            spells = spellService.getSpellsByClass(classParam);
        } else if (circleParam != null) {
            spells = spellService.getSpellsByCircle(circleParam);
        } else if (schoolParam != null) {
            spells = spellService.getSpellsBySchool(schoolParam);
        } else {
            spells = spellService.getAllSpells();
        }

        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getOutputStream(), spells);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SpellDTO spellDTO = objectMapper.readValue(req.getInputStream(), SpellDTO.class);
        spellService.createSpell(spellDTO);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SpellDTO spellDTO = objectMapper.readValue(req.getInputStream(), SpellDTO.class);
        spellService.updateSpell(spellDTO);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        spellService.deleteSpell(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
