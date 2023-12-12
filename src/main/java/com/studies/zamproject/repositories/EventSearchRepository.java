/* (C)2023 */
package com.studies.zamproject.repositories;

import com.studies.zamproject.dtos.SearchCriteriaRequestDTO;
import com.studies.zamproject.entities.Event;
import java.util.List;

public interface EventSearchRepository {
    List<Event> findBySearchCriteria(SearchCriteriaRequestDTO criteria, Long userId);
}
