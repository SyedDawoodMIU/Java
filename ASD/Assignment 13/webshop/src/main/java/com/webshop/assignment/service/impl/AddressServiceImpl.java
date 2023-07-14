package com.webshop.assignment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshop.assignment.dao.AddressDAO;
import com.webshop.assignment.service.AddressService;
import com.webshop.assignment.shopping.Address;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDAO addressDAO;

    @Override
    public List<Address> findAll() {
        return addressDAO.findAll();
    }

    @Override
    public Address getById(Long id) {

        return addressDAO.findById(id).orElse(null);
    }

    @Override
    public void delete(Address Address) {

        addressDAO.delete(Address);
    }

    @Override
    public void save(Address c) {

        addressDAO.save(c);
    }

    @Override
    public void update(Long id, Address c) {

        Address address = addressDAO.findById(id).orElse(null);
        if (address != null) {
            address.setCity(c.getCity());
            address.setZip(c.getZip());
            address.setStreet(c.getStreet());
            addressDAO.save(address);
        } else {
            throw new IllegalArgumentException("Address does not exist");
        }
    }

    @Override
    public void deleteByID(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteByID'");
    }

    @Override
    public Address findByUserPostAndCommentId(Long userId, Long postId, Long commentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUserPostAndCommentId'");
    }

}
