package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.service.AccidentService;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AccidentControl {
    private final AccidentService service;

    public AccidentControl(AccidentService service) {
        this.service = service;
    }

    @GetMapping(path = "/create")
    public String create(Model model) {
        Collection<AccidentType> types = service.getAllTypes();
        model.addAttribute("types", types);
        Collection<Rule> rules = service.getAllRules();
        model.addAttribute("rules", rules);
        return "accident/create";
    }

    @GetMapping(path = "/edit")
    public String edit(Model model, @RequestParam(name = "id") Integer id) {
        Collection<AccidentType> types = service.getAllTypes();
        model.addAttribute("types", types);
        model.addAttribute("a", service.get(id));
        Collection<Rule> rules = service.getAllRules();
        model.addAttribute("rules", rules);
        return "accident/edit";
    }

    @PostMapping(path = "/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        service.save(accident, req);
        return "redirect:/";
    }
}