package com.Project.SpringBootDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.SpringBootDemo.entity.userEntity;

public interface UserRepository extends JpaRepository<userEntity,Long>{

}
