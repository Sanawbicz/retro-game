package com.github.retro_game.retro_game.controller;

import com.github.retro_game.retro_game.dto.BuildingKindDto;
import com.github.retro_game.retro_game.dto.TechnologyKindDto;
import com.github.retro_game.retro_game.dto.UnitKindDto;
import com.github.retro_game.retro_game.service.BodyService;
import com.github.retro_game.retro_game.service.DetailsService;
import com.github.retro_game.retro_game.service.TechnologyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DetailsController {
  private final BodyService bodyService;
  private final DetailsService detailsService;
  private final TechnologyService technologyService;

  public DetailsController(BodyService bodyService, DetailsService detailsService,
                           TechnologyService technologyService) {
    this.bodyService = bodyService;
    this.detailsService = detailsService;
    this.technologyService = technologyService;
  }

  @GetMapping("/details/building")
  @PreAuthorize("hasPermission(#bodyId, 'ACCESS')")
  public String buildingDetails(@RequestParam(name = "body") long bodyId, @RequestParam BuildingKindDto kind,
                                Model model) {
    model.addAttribute("bodyId", bodyId);
    model.addAttribute("kind", kind);
    model.addAttribute("details", detailsService.getBuildingDetails(bodyId, kind));
    model.addAttribute("temperature", bodyService.getTemperature(bodyId));
    model.addAttribute("energyTechnologyLevel", technologyService.getLevel(bodyId,
        TechnologyKindDto.ENERGY_TECHNOLOGY));
    return "details-building";
  }

  @GetMapping("/details/technology")
  @PreAuthorize("hasPermission(#bodyId, 'ACCESS')")
  public String technologyDetails(@RequestParam(name = "body") long bodyId, @RequestParam TechnologyKindDto kind,
                                  Model model) {
    model.addAttribute("bodyId", bodyId);
    model.addAttribute("kind", kind);
    model.addAttribute("details", detailsService.getTechnologyDetails(bodyId, kind));
    return "details-technology";
  }

  @GetMapping("/details/unit")
  @PreAuthorize("hasPermission(#bodyId, 'ACCESS')")
  public String unitDetails(@RequestParam(name = "body") long bodyId, @RequestParam UnitKindDto kind, Model model) {
    model.addAttribute("bodyId", bodyId);
    model.addAttribute("kind", kind);
    model.addAttribute("details", detailsService.getUnitDetails(bodyId, kind));
    return "details-unit";
  }
}
