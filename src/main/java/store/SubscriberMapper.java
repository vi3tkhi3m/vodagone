package store;

import domain.Subscriber;
import exceptions.DataMapperException;

import java.util.List;
import java.util.Optional;

public interface SubscriberMapper {

    Optional<Subscriber> find(int subscriber_id);

    void insert(Subscriber subscriber_id) throws DataMapperException;

    void update(Subscriber subscriber_id) throws DataMapperException;

    void delete(Subscriber subscriber_id) throws DataMapperException;

    List<Subscriber> getSubscribers();
}
