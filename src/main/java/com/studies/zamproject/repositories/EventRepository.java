/* (C)2023 */
package com.studies.zamproject.repositories;

import com.studies.zamproject.entities.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>, EventSearchRepository {
    List<Event> findByOwnerEmail(String email);
}
