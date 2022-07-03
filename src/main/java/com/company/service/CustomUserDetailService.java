package com.company.service;

import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService {

    @Autowired
    private ProfileRepository profileRepository;


}
