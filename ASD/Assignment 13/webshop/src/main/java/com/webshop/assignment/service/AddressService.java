package com.webshop.assignment.service;

import java.util.List;

import com.webshop.assignment.shopping.Address;

public interface AddressService {
    List<Address> findAll();
    Address getById(Long id);
    void delete(Address Address);
    void save(Address c);
    void update(Long id, Address c);
    void deleteByID(Long id);
    Address findByUserPostAndCommentId(Long userId, Long postId, Long commentId);

}
