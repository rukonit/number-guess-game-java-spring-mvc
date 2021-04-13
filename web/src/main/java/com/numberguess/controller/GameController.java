package com.numberguess.controller;

import com.numberguess.service.GameService;
import com.numberguess.until.AttributeNames;
import com.numberguess.until.GameMappings;
import com.numberguess.until.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class GameController {

    // == fields ==
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // == request methods ==
    @GetMapping(GameMappings.PlAY)
    public String play(Model model){
        model.addAttribute(AttributeNames.MAIN_MESSAGE, gameService.getMainMessage());
        model.addAttribute(AttributeNames.RESULT_MESSAGE, gameService.getResultMessage());
        log.info("model = {}", model);

        if(gameService.isGameOver()){
            return ViewNames.GAME_OVER;
        }

        return ViewNames.PLAY;
    }

    @PostMapping(GameMappings.PlAY)
    public String processMessage(@RequestParam int guess){
        log.info("guess  = {}", guess);
        gameService.checkGuess(guess);
        return GameMappings.REDIRECT_PLAY;
    }

    @GetMapping(GameMappings.RESTART_GAME)
    public String restart(){
        gameService.reset();
        return GameMappings.REDIRECT_PLAY;
    }

    @ModelAttribute("bn")
    public String langBn(Model model){
       return "?lang=bn";
    }
    @ModelAttribute("en")
    public String LangEn(Model model){
        return "?lang=en";
    }
}
