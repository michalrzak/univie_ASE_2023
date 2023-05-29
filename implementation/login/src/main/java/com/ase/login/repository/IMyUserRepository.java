package com.ase.login.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMyUserRepository extends JpaRepository<MyUserSavable, String> {

    Optional<MyUserSavable> findMyUserSavableByEmail(String email);
}