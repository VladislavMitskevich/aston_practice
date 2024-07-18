package org.examle.aston_practice.spellbook.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.exception.InvalidInputException;
import org.examle.aston_practice.spellbook.exception.SpellNotFoundException;
import org.examle.aston_practice.spellbook.service.SpellService;
import org.examle.aston_practice.spellbook.service.impl.SpellServiceImpl;
import org.examle.aston_practice.spellbook.repository.impl.SpellRepositoryImpl;
import org.examle.aston_practice.spellbook.mapper.SpellMapper;
import org.examle.aston_practice.spellbook.validator.SpellValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(SpellServlet.class);

    @Override
    public void init() throws ServletException {
        SpellMapper spellMapper = new SpellMapper();
        SpellValidator spellValidator = new SpellValidator(new SpellServiceImpl(new SpellRepositoryImpl(), spellMapper, new SpellValidator(null)));
        spellService = new SpellServiceImpl(new SpellRepositoryImpl(), spellMapper, spellValidator);
        objectMapper = new ObjectMapper();
        logger.info("SpellServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doGet method called");
        try {
            String idParam = req.getParameter("id");
            String nameParam = req.getParameter("name");
            String casterClassParam = req.getParameter("casterClass");
            String circleParam = req.getParameter("circle");
            String includeCharactersParam = req.getParameter("includeCharacters");

            if (idParam != null) {
                logger.info("Fetching spell by ID: {}", idParam);
                Long id = Long.valueOf(idParam);
                Optional<SpellDTO> spell = spellService.getSpellById(id);
                if (spell.isPresent()) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(objectMapper.writeValueAsString(spell.get()));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Spell not found");
                }
            } else if (nameParam != null) {
                logger.info("Fetching spell by name: {}", nameParam);
                Optional<SpellDTO> spell = spellService.getSpellByName(nameParam);
                if (spell.isPresent()) {
                    if (Boolean.parseBoolean(includeCharactersParam)) {
                        logger.info("Fetching characters by spell name: {}", nameParam);
                        List<CharacterDTO> characters = spellService.getCharactersBySpellName(nameParam);
                        spell.get().setCharacters(characters);
                    }
                    resp.setContentType("application/json");
                    resp.getWriter().write(objectMapper.writeValueAsString(spell.get()));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Spell not found");
                }
            } else if (casterClassParam != null && circleParam != null) {
                logger.info("Fetching spells by caster class: {} and circle: {}", casterClassParam, circleParam);
                CasterClass casterClass = CasterClass.valueOf(casterClassParam);
                SpellCircle circle = SpellCircle.valueOf(circleParam);
                List<SpellDTO> spells = spellService.getSpellsByCasterClassAndCircle(casterClass, circle);
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(spells));
            } else {
                logger.info("Fetching all spells");
                List<SpellDTO> spells = spellService.getAllSpells();
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(spells));
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid 'id' parameter", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid 'id' parameter");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid 'casterClass' or 'circle' parameter", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid 'casterClass' or 'circle' parameter");
        } catch (SpellNotFoundException e) {
            logger.error("Spell not found", e);
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(e.getMessage());
        } catch (InvalidInputException e) {
            logger.error("Invalid input", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        } catch (Exception e) {
            logger.error("Internal server error", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doPost method called");
        try {
            SpellDTO spellDTO = objectMapper.readValue(req.getReader(), SpellDTO.class);
            logger.info("Creating spell: {}", spellDTO);
            spellService.createSpell(spellDTO);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (InvalidInputException e) {
            logger.error("Invalid input", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        } catch (Exception e) {
            logger.error("Internal server error", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doPut method called");
        try {
            SpellDTO spellDTO = objectMapper.readValue(req.getReader(), SpellDTO.class);
            logger.info("Updating spell: {}", spellDTO);
            spellService.updateSpell(spellDTO);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (InvalidInputException e) {
            logger.error("Invalid input", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        } catch (Exception e) {
            logger.error("Internal server error", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doDelete method called");
        try {
            String idParam = req.getParameter("id");
            if (idParam != null) {
                logger.info("Deleting spell by ID: {}", idParam);
                Long id = Long.valueOf(idParam);
                spellService.deleteSpell(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                logger.warn("Missing 'id' parameter");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Missing 'id' parameter");
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid 'id' parameter", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid 'id' parameter");
        } catch (Exception e) {
            logger.error("Internal server error", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        }
    }
}
