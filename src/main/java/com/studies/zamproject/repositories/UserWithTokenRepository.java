/* (C)2023 */
package com.studies.zamproject.repositories;

import com.studies.zamproject.entities.UserWithToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWithTokenRepository extends JpaRepository<UserWithToken, Long> {
    Optional<UserWithToken> findByToken(String token);
}
