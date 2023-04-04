package com.storemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.storemanagement.model.AdminUserEntity;

public interface AdminUserRepository extends JpaRepository<AdminUserEntity, Long> {

	@Query(value = "select a from AdminUserEntity a where a.userName=:userName")
	Optional<AdminUserEntity> findUserByUserName(@Param("userName") String userName);
}
