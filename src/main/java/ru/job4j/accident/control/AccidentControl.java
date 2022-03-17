package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentHibernate;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccidentControl {
    private final AccidentHibernate accidentHibernate;

    public AccidentControl(AccidentHibernate accidents) {
        this.accidentHibernate = accidents;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", accidentHibernate.getAllTypes());
        model.addAttribute("rules", accidentHibernate.getAllRules());
        return "accident/create";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidentHibernate.findAccidentById(id));
        model.addAttribute("types", accidentHibernate.getAllTypes());
        model.addAttribute("rules", accidentHibernate.getAllRules());
        return "accident/edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] rIds = req.getParameterValues("rIds");
        accident.setType(accidentHibernate.findAccidentTypeById(accident.getType().getId()));
        for (Rule rule : accidentHibernate.createRulesForCurrentAccident(rIds)) {
            accident.getRules().add(rule);
        }
        accidentHibernate.saveAccident(accident);
        return "redirect:/";
    }
}
