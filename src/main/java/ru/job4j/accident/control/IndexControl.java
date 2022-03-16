package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentJdbcTemplate;

import java.util.Collection;

@Controller
public class IndexControl {
    private final AccidentJdbcTemplate accidentJdbcTemplate;

    public IndexControl(AccidentJdbcTemplate accidents) {
        this.accidentJdbcTemplate = accidents;
    }

    @GetMapping("/")
    public String index(Model model) {
        Collection<Accident> accidents = accidentJdbcTemplate.getAllAccidents();
        model.addAttribute("accidents", accidents);
        return "index";
    }
}
