/* (C)2023 */
package com.studies.zamproject.repositories;

import com.studies.zamproject.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
