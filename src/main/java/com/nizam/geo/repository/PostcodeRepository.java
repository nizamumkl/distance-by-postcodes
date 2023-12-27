package com.nizam.geo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nizam.geo.entity.PostcodeEntity;

public interface PostcodeRepository extends JpaRepository<PostcodeEntity, Long> {
    PostcodeEntity findByPostcode(String postCode);
}
