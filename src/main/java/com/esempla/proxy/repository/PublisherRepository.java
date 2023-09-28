package com.esempla.proxy.repository;


import com.esempla.proxy.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher,Integer> {
//    @Query(value = "select name from publisher where name =:nume", nativeQuery = true)
//    String findByName(@Param("nume") String nume);
}
