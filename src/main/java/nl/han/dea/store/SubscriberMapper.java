package nl.han.dea.store;

import nl.han.dea.domain.Subscriber;
import nl.han.dea.exceptions.DataMapperException;

import java.util.List;
import java.util.Optional;

public interface SubscriberMapper {

    Optional<Subscriber> find(int subscriber_id);

    void insert(Subscriber subscriber_id) throws DataMapperException;

    void update(Subscriber subscriber_id) throws DataMapperException;

    void delete(Subscriber subscriber_id) throws DataMapperException;

    List<Subscriber> getSubscribers();
}
