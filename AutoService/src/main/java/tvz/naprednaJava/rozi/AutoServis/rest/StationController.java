package tvz.naprednaJava.rozi.AutoServis.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tvz.naprednaJava.rozi.AutoServis.model.Station;
import tvz.naprednaJava.rozi.AutoServis.service.StationService;

/**
 * Created by Hrvoje
 */

@RestController
@RequestMapping("/api/stations")
public class StationController {

    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    } 

    @GetMapping
    public Iterable<Station> getAll(){
        return stationService.getAll();
    }

}
