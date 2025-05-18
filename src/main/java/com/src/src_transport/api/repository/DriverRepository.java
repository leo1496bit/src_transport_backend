package com.src.src_transport.api.repository;

import com.src.src_transport.api.model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface DriverRepository extends MongoRepository<Driver, String> {
}
