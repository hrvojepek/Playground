package tvz.naprednaJava.rozi.AutoServis.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tvz.naprednaJava.rozi.AutoServis.model.SosCall;
import tvz.naprednaJava.rozi.AutoServis.service.SosCallService;

/**
 * Created by Hrvoje
 */

@RestController
@RequestMapping("/api/sos")
public class SOSController {

    private final SosCallService sosCallService;

    @Autowired
    public SOSController(SosCallService sosCallService) {
        this.sosCallService = sosCallService;
    } 


    public void sosCall(@RequestBody SosCall sosCall){
        sosCallService.saveSosCall(sosCall);
    }

}
