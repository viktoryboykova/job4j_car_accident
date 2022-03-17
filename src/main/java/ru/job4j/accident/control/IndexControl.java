package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.repository.AccidentHibernate;

@Controller
public class IndexControl {
    private final AccidentHibernate accidentHibernate;

    public IndexControl(AccidentHibernate accidents) {
        this.accidentHibernate = accidents;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("accidents", accidentHibernate.getAllAccidents());
        return "index";
    }
}
