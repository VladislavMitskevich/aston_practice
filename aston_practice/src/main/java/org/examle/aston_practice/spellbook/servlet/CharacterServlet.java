package org.examle.aston_practice.spellbook.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.service.CharacterService;
import org.examle.aston_practice.spellbook.service.impl.CharacterServiceImpl;
import org.examle.aston_practice.spellbook.repository.impl.CharacterRepositoryImpl;
import org.examle.aston_practice.spellbook.mapper.CharacterMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Servlet for handling character-related requests.
 */
@WebServlet("/characters")
public class CharacterServlet extends HttpServlet {

    private CharacterService characterService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        characterService = new CharacterServiceImpl(new CharacterRepositoryImpl(), new CharacterMapper());
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                Long id = Long.valueOf(idParam);
                Optional<CharacterDTO> character = characterService.getCharacterById(id);
                if (character.isPresent()) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(objectMapper.writeValueAsString(character.get()));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            List<CharacterDTO> characters = characterService.getAllCharacters();
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(characters));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CharacterDTO characterDTO = objectMapper.readValue(req.getReader(), CharacterDTO.class);
        characterService.createCharacter(characterDTO);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CharacterDTO characterDTO = objectMapper.readValue(req.getReader(), CharacterDTO.class);
        characterService.updateCharacter(characterDTO);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                Long id = Long.valueOf(idParam);
                characterService.deleteCharacter(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}