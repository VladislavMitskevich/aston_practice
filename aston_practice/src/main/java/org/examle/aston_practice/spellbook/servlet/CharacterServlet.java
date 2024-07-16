package org.examle.aston_practice.spellbook.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.exception.CharacterNotFoundException;
import org.examle.aston_practice.spellbook.exception.InvalidInputException;
import org.examle.aston_practice.spellbook.service.CharacterService;
import org.examle.aston_practice.spellbook.service.impl.CharacterServiceImpl;
import org.examle.aston_practice.spellbook.repository.impl.CharacterRepositoryImpl;
import org.examle.aston_practice.spellbook.mapper.CharacterMapper;
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
 * Servlet for handling character-related requests.
 */
@WebServlet("/characters")
public class CharacterServlet extends HttpServlet {

    private CharacterService characterService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        SpellMapper spellMapper = new SpellMapper();
        CharacterMapper characterMapper = new CharacterMapper(spellMapper);
        characterService = new CharacterServiceImpl(new CharacterRepositoryImpl(), characterMapper);
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");
            String casterClassParam = req.getParameter("casterClass");
            String nameParam = req.getParameter("name");
            String spellNameParam = req.getParameter("spellName");

            if (idParam != null) {
                Long id = Long.valueOf(idParam);
                Optional<CharacterDTO> character = characterService.getCharacterById(id);
                if (character.isPresent()) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(objectMapper.writeValueAsString(character.get()));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Character not found");
                }
            } else if (casterClassParam != null) {
                CasterClass casterClass = CasterClass.valueOf(casterClassParam);
                List<CharacterDTO> characters = characterService.getCharactersByCasterClass(casterClass);
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(characters));
            } else if (nameParam != null) {
                Optional<CharacterDTO> character = characterService.getCharacterSpellsByName(nameParam);
                if (character.isPresent()) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(objectMapper.writeValueAsString(character.get()));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Character not found");
                }
            } else if (spellNameParam != null) {
                List<CharacterDTO> characters = characterService.getCharactersBySpellName(spellNameParam);
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(characters));
            } else {
                List<CharacterDTO> characters = characterService.getAllCharacters();
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(characters));
            }
        } catch (CharacterNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(e.getMessage());
        } catch (InvalidInputException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CharacterDTO characterDTO = objectMapper.readValue(req.getReader(), CharacterDTO.class);
            characterService.createCharacter(characterDTO);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (InvalidInputException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String characterIdParam = req.getParameter("characterId");
            String spellIdParam = req.getParameter("spellId");

            if (characterIdParam != null && spellIdParam != null) {
                Long characterId = Long.valueOf(characterIdParam);
                Long spellId = Long.valueOf(spellIdParam);
                characterService.addSpellToCharacter(characterId, spellId);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                CharacterDTO characterDTO = objectMapper.readValue(req.getReader(), CharacterDTO.class);
                characterService.updateCharacter(characterDTO);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (InvalidInputException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");
            if (idParam != null) {
                Long id = Long.valueOf(idParam);
                characterService.deleteCharacter(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Missing 'id' parameter");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        }
    }
}