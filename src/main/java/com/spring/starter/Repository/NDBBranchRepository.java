package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.NDBBranch;

public interface NDBBranchRepository extends JpaRepository<NDBBranch, Integer> {

}
