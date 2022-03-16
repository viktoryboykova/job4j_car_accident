package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentMem;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccidentControl {
    private final AccidentMem accidentMem;

    public AccidentControl(AccidentMem accidents) {
        this.accidentMem = accidents;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", accidentMem.getAllTypes());
        return "accident/create";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidentMem.findAccidentById(id));
        model.addAttribute("types", accidentMem.getAllTypes());
        return "accident/edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        accidentMem.save(accident);
        return "redirect:/";
    }
}
