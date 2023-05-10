package com.law.Service;

import com.law.Model.LawFirm;
import com.law.Repository.LawFirmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LawFirmService {


    @Autowired
    LawFirmRepository lawFirmRepository;

    public void save(LawFirm booking) {
        lawFirmRepository.save(booking);
    }

    public List<LawFirm> listAll() {
        return lawFirmRepository.findAll();
    }

    public Optional<LawFirm> findClientById(Long id) {
        return lawFirmRepository.findById(id);
    }


}
