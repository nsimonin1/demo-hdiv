package org.simon.pascal.controller;

import javax.validation.Valid;
import org.simon.pascal.bean.FormBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 *
 * @author simon.pascal.ngos
 */
@Controller
public class MainController {
    static final Logger LOG = LoggerFactory.getLogger(MainController.class);

	@RequestMapping("/")
	String home() {
		return "main";
	}

	@RequestMapping("/links")
	String links() {
		return "links";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	String form(Model model) {

		model.addAttribute("formBean", new FormBean());
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	String submit(@Valid @ModelAttribute FormBean bean, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return "form";
		}
		redirectAttributes.addFlashAttribute("resultFormBean", bean);
		LOG.info("Name: " + bean.getName() + " Type: " + bean.getType());
		return "redirect:/form";
	}
}
