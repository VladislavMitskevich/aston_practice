package org.examle.aston_practice.spellbook.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.service.SpellService;
import org.examle.aston_practice.spellbook.service.impl.SpellServiceImpl;
import org.examle.aston_practice.spellbook.repository.impl.SpellRepositoryImpl;
import org.examle.aston_practice.spellbook.mapper.SpellMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Servlet for handling spell-related requests.
 */
@WebServlet("/spells")
public class SpellServlet extends HttpServlet {

    private SpellService spellService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        spellService = new SpellServiceImpl(new SpellRepositoryImpl(), new SpellMapper());
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String casterClassParam = req.getParameter("casterClass");
        String circleParam = req.getParameter("circle");

        if (idParam != null) {
            try {
                Long id = Long.valueOf(idParam);
                Optional<SpellDTO> spell = spellService.getSpellById(id);
                if (spell.isPresent()) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(objectMapper.writeValueAsString(spell.get()));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (casterClassParam != null && circleParam != null) {
            try {
                CasterClass casterClass = CasterClass.valueOf(casterClassParam);
                SpellCircle circle = SpellCircle.valueOf(circleParam);
                List<SpellDTO> spells = spellService.getSpellsByCasterClassAndCircle(casterClass, circle);
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(spells));
            } catch (IllegalArgumentException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            List<SpellDTO> spells = spellService.getAllSpells();
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(spells));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SpellDTO spellDTO = objectMapper.readValue(req.getReader(), SpellDTO.class);
        spellService.createSpell(spellDTO);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SpellDTO spellDTO = objectMapper.readValue(req.getReader(), SpellDTO.class);
        spellService.updateSpell(spellDTO);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                Long id = Long.valueOf(idParam);
                spellService.deleteSpell(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}