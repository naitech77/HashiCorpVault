package com.naitech.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naitech.model.Card;

@Repository
public interface CardDao extends JpaRepository<Card, Integer>{

}
