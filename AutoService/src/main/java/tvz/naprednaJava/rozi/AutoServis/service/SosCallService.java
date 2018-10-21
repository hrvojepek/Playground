package tvz.naprednaJava.rozi.AutoServis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tvz.naprednaJava.rozi.AutoServis.model.SosCall;
import tvz.naprednaJava.rozi.AutoServis.repository.SosCallRepository;

/**
 * Created by Hrvoje
 */

@Service
public class SosCallService {

    private final SosCallRepository sosCallRepository;

    @Autowired
    public SosCallService(SosCallRepository sosCallRepository) {
        this.sosCallRepository = sosCallRepository;
    }

    public void saveSosCall(SosCall sosCall){
        sosCallRepository.save(sosCall);
    }

}
