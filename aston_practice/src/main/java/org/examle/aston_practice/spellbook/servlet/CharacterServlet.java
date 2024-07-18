package org.examle.aston_practice.spellbook.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.examle.aston_practice.spellbook.dto.CharacterDTO;
import org.examle.aston_practice.spellbook.dto.SpellDTO;
import org.examle.aston_practice.spellbook.enums.CasterClass;
import org.examle.aston_practice.spellbook.enums.SpellCircle;
import org.examle.aston_practice.spellbook.exception.CharacterNotFoundException;
import org.examle.aston_practice.spellbook.exception.InvalidInputException;
import org.examle.aston_practice.spellbook.service.CharacterService;
import org.examle.aston_practice.spellbook.service.impl.CharacterServiceImpl;
import org.examle.aston_practice.spellbook.repository.impl.CharacterRepositoryImpl;
import org.examle.aston_practice.spellbook.repository.impl.SpellRepositoryImpl;
import org.examle.aston_practice.spellbook.mapper.CharacterMapper;
import org.examle.aston_practice.spellbook.mapper.SpellMapper;
import org.examle.aston_practice.spellbook.validator.CharacterValidator;
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
 * Servlet for handling character-related requests.
 */
@WebServlet("/characters")
public class CharacterServlet extends HttpServlet {

    private CharacterService characterService;
    private ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(CharacterServlet.class);

    @Override
    public void init() throws ServletException {
        SpellMapper spellMapper = new SpellMapper();
        CharacterMapper characterMapper = new CharacterMapper(spellMapper);
        CharacterValidator characterValidator = new CharacterValidator(new CharacterServiceImpl(new CharacterRepositoryImpl(), new SpellRepositoryImpl(), characterMapper, new CharacterValidator(null)));
        characterService = new CharacterServiceImpl(new CharacterRepositoryImpl(), new SpellRepositoryImpl(), characterMapper, characterValidator);
        objectMapper = new ObjectMapper();
        logger.info("CharacterServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doGet method called");
        try {
            String idParam = req.getParameter("id");
            String casterClassParam = req.getParameter("casterClass");
            String characterNameParam = req.getParameter("characterName");
            String spellNameParam = req.getParameter("spellName");
            String casterClassForSpellsParam = req.getParameter("casterClassForSpells");
            String spellCircleParam = req.getParameter("spellCircle");
            String includeSpellsParam = req.getParameter("includeSpells");

            if (idParam != null) {
                logger.info("Fetching character by ID: {}", idParam);
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
                logger.info("Fetching characters by caster class: {}", casterClassParam);
                CasterClass casterClass = CasterClass.valueOf(casterClassParam);
                List<CharacterDTO> characters = characterService.getCharactersByCasterClass(casterClass);
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(characters));
            } else if (characterNameParam != null) {
                if (Boolean.parseBoolean(includeSpellsParam)) {
                    logger.info("Fetching spells by character name: {}", characterNameParam);
                    List<SpellDTO> spells = characterService.getSpellsByCharacterName(characterNameParam);
                    resp.setContentType("application/json");
                    resp.getWriter().write(objectMapper.writeValueAsString(spells));
                } else {
                    logger.info("Fetching character by name: {}", characterNameParam);
                    Optional<CharacterDTO> character = characterService.getCharacterByName(characterNameParam);
                    if (character.isPresent()) {
                        resp.setContentType("application/json");
                        resp.getWriter().write(objectMapper.writeValueAsString(character.get()));
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        resp.getWriter().write("Character not found");
                    }
                }
            } else if (spellNameParam != null) {
                logger.info("Fetching characters by spell name: {}", spellNameParam);
                List<CharacterDTO> characters = characterService.getCharactersBySpellName(spellNameParam);
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(characters));
            } else if (casterClassForSpellsParam != null && spellCircleParam != null) {
                logger.info("Fetching spells by caster class: {} and spell circle: {}", casterClassForSpellsParam, spellCircleParam);
                CasterClass casterClass = CasterClass.valueOf(casterClassForSpellsParam);
                SpellCircle spellCircle = SpellCircle.valueOf(spellCircleParam);
                List<SpellDTO> spells = characterService.getSpellsByCasterClassAndSpellCircle(casterClass, spellCircle);
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(spells));
            } else {
                logger.info("Fetching all characters");
                List<CharacterDTO> characters = characterService.getAllCharacters();
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(characters));
            }
        } catch (CharacterNotFoundException e) {
            logger.error("Character not found", e);
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
            String characterNameParam = req.getParameter("characterName");
            String spellNameParam = req.getParameter("spellName");

            if (characterNameParam != null && spellNameParam != null) {
                logger.info("Adding spell with name: {} to character with name: {}", spellNameParam, characterNameParam);
                characterService.addSpellToCharacterByName(characterNameParam, spellNameParam);
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                logger.info("Creating new character");
                CharacterDTO characterDTO = objectMapper.readValue(req.getReader(), CharacterDTO.class);
                characterService.createCharacter(characterDTO);
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }
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
            String characterIdParam = req.getParameter("characterId");
            if (characterIdParam != null) {
                logger.info("Adding new spell to character with ID: {}", characterIdParam);
                Long characterId = Long.valueOf(characterIdParam);
                SpellDTO spellDTO = objectMapper.readValue(req.getReader(), SpellDTO.class);
                characterService.addNewSpellToCharacter(characterId, spellDTO);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                logger.info("Updating character");
                CharacterDTO characterDTO = objectMapper.readValue(req.getReader(), CharacterDTO.class);
                characterService.updateCharacter(characterDTO);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
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
                logger.info("Deleting character with ID: {}", idParam);
                Long id = Long.valueOf(idParam);
                characterService.deleteCharacter(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                logger.warn("Missing 'id' parameter");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Missing 'id' parameter");
            }
        } catch (Exception e) {
            logger.error("Internal server error", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Internal server error");
        }
    }
}
