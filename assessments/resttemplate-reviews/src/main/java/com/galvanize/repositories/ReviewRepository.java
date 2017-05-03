package com.galvanize.repositories;

import com.galvanize.models.Review;
import org.springframework.data.repository.CrudRepository;


public interface ReviewRepository extends CrudRepository<Review, Long>{
}
