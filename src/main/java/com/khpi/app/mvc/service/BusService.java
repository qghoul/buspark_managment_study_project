package com.khpi.app.mvc.service;

import com.khpi.app.mvc.models.Bus;
import com.khpi.app.mvc.models.BusType;
import com.khpi.app.mvc.models.UnavailableBusDetails;
import com.khpi.app.mvc.repositories.BusRepository;
import com.khpi.app.mvc.repositories.UnavailableBusDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private UnavailableBusDetailsRepository unavailableBusDetailsRepository;

    @Transactional
    public Bus saveWithAutoTypeIdentity(Bus bus){
        if (bus.getCapacity() < 30) {
            bus.setBusType(BusType.Minibus);
        } else if (bus.getCapacity() <= 60) {
            bus.setBusType(BusType.MidSized);
        } else {
            bus.setBusType(BusType.FullSized);
        }
        return busRepository.save(bus);
    }

    @Transactional
    public void deleteBus(Bus bus){
        busRepository.delete(bus);
    }

    @Transactional
    public void deleteUnavailableBusDetails(UnavailableBusDetails unavailableBusDetails){
        unavailableBusDetailsRepository.delete(unavailableBusDetails);
    }
}
