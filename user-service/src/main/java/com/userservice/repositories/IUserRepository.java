package com.userservice.repositories;

import com.userservice.domains.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<Usuario, Long> {
}
