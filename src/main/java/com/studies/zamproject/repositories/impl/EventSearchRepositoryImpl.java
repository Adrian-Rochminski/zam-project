package com.studies.zamproject.repositories.impl;

import com.studies.zamproject.dtos.SearchCriteriaRequestDTO;
import com.studies.zamproject.entities.Event;
import com.studies.zamproject.repositories.EventSearchRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class EventSearchRepositoryImpl implements EventSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> findBySearchCriteria(SearchCriteriaRequestDTO criteria) {
        List<Event> events = getEventsInRadius(criteria);
        List<String> keywords = Arrays.stream(criteria.getSearchString().split("\\s+")).toList();
        events = events.stream()
            .filter(event ->
                (event.getFree() || event.getFree() == criteria.getIsFree()) &&
                    (event.getTags().stream().anyMatch(tag -> keywords.contains(StringUtils.stripAccents(tag.getName()))) ||
                        keywords.stream().anyMatch(keyword ->
                            StringUtils.stripAccents(event.getName()).contains(keyword) ||
                            StringUtils.stripAccents(event.getDescription()).contains(keyword))
                    )
            )
            .toList();
        return events;
    }

    private List<Event> getEventsInRadius(SearchCriteriaRequestDTO criteria) {
        String queryString = "SELECT * FROM event e WHERE ST_DWithin(" +
                "ST_MakePoint(e.longitude, e.latitude)\\:\\:geography, " +
                "ST_MakePoint(:longitude, :latitude)\\:\\:geography, " +
                ":radius * 1000) " +
                "AND e.start_time >= :startDate AND e.end_time <= :endDate ";
        Query query = entityManager.createNativeQuery(queryString, Event.class);

        query.setParameter("latitude", criteria.getLatitude());
        query.setParameter("longitude", criteria.getLongitude());
        query.setParameter("radius", criteria.getRadius());
        query.setParameter("startDate", criteria.getStartDate());
        query.setParameter("endDate", criteria.getEndDate().plusDays(1));

        return query.getResultList();
    }
}
