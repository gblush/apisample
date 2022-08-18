package dev.blush.apisample.rates;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InMemoryRateRepository extends CrudRepository<Rate, Long> {}
