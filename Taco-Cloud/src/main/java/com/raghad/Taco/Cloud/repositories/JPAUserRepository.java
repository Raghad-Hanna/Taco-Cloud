package com.raghad.Taco.Cloud.repositories;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import com.raghad.Taco.Cloud.models.User;

public interface JPAUserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
