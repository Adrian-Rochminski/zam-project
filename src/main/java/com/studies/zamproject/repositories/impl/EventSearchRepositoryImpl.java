package com.studies.zamproject.repositories.impl;

import com.studies.zamproject.dtos.SearchCriteriaRequestDTO;
import com.studies.zamproject.entities.Event;
import com.studies.zamproject.repositories.EventSearchRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class EventSearchRepositoryImpl implements EventSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> findBySearchCriteria(SearchCriteriaRequestDTO criteria) {
        List<Event> events = getEventsInRadius(criteria);
        return events;
    }

    private List<Event> getEventsInRadius(SearchCriteriaRequestDTO criteria) {
        String queryString = "SELECT * FROM event e WHERE ST_DWithin(" +
                "ST_MakePoint(e.longitude, e.latitude)\\:\\:geography, " +
                "ST_MakePoint(:longitude, :latitude)\\:\\:geography, " +
                ":radius * 1000)";
        Query query = entityManager.createNativeQuery(queryString, Event.class);

        query.setParameter("latitude", criteria.getLatitude());
        query.setParameter("longitude", criteria.getLongitude());
        query.setParameter("radius", criteria.getRadius());

        return query.getResultList();
    }
}