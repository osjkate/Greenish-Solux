package com.solux.greenish.Photo.Repository;

import com.solux.greenish.Photo.Domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}