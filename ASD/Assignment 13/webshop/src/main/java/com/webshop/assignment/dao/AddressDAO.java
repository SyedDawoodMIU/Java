package com.webshop.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webshop.assignment.shopping.Address;

public interface AddressDAO extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.user.id = ?1 AND a.post.id = ?2 AND a.comment.id = ?3")
    Address findByUserPostAndCommentId(Long userId, Long postId, Long commentId);
}
