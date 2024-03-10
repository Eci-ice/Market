package com.example.maoliang.Service;

import com.example.maoliang.Entity.Usr;
import com.example.maoliang.Repository.UsrListRepository;
import com.example.maoliang.Repository.UsrRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsrService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsrService.class);


    @Autowired
    private UsrRepository usrRepository;

    @Autowired
    UsrListRepository goodListRepository;

    public Usr search(String username) throws SQLException {
        Usr usr=usrRepository.search(username);
//        System.out.println(usr.getUserid());
        return usr;
    }
    public boolean searchName(String username) throws SQLException {
        return usrRepository.searchName(username);
    }


    public List<Usr> loadBuyer() throws  SQLException {
        return usrRepository.loadBuyer();
    }

}
