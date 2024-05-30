package com.example.maoliang.Service;

import com.example.maoliang.Entity.Address;
import com.example.maoliang.Repository.DeliverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliverService {
    private  final Logger LOGGER = LoggerFactory.getLogger(DeliverService.class);
    @Autowired
    private DeliverRepository deliverRepository;
    public List<Address> addAddress(int userid, String address, String default_address){
        return DeliverRepository.add(userid,address,default_address);
    }


    public List<Address> deleteAddress(int userid, String address, String default_address) {
        return DeliverRepository.delete(userid,address,default_address);
    }

    public List<Address>modifyAddress(int userid,String newaddress) {
        return DeliverRepository.modify(userid,newaddress);
    }
}

