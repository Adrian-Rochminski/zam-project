/* (C)2023 */
package com.studies.zamproject.repositories;

import com.studies.zamproject.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
