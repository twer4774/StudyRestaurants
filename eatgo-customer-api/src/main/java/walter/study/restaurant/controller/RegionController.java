package walter.study.restaurant.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import walter.study.restaurant.application.RegionService;
import walter.study.restaurant.domain.Region;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RegionController {


    private final RegionService regionService;

    @GetMapping("/regions")
    public List<Region> list(){


        List<Region> regions = regionService.getRegions();

        return regions;
    }

}
