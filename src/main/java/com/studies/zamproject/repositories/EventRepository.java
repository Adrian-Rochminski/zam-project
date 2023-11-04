/* (C)2023 */
package com.studies.zamproject.repositories;

import com.studies.zamproject.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {}
