package org.examle.aston_practice.spellbook.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.service.CharacterService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling Character related HTTP requests.
 */
@WebServlet("/characters/*")
public class CharacterServlet extends HttpServlet {

    private final CharacterService characterService;
    private final ObjectMapper objectMapper;

    public CharacterServlet(CharacterService characterService) {
        this.characterService = characterService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<CharacterDTO> characters = characterService.findAll();
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(characters));
        } else {
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                CharacterDTO character = characterService.findById(id);
                if (character != null) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(objectMapper.writeValueAsString(character));
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CharacterDTO characterDTO = objectMapper.readValue(req.getReader(), CharacterDTO.class);
        characterService.save(characterDTO);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            Long id = Long.parseLong(pathInfo.substring(1));
            CharacterDTO characterDTO = objectMapper.readValue(req.getReader(), CharacterDTO.class);
            characterDTO.setId(id);
            characterService.update(characterDTO);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            Long id = Long.parseLong(pathInfo.substring(1));
            characterService.deleteById(id);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
