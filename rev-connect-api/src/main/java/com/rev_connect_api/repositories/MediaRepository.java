package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface MediaRepository extends JpaRepository<Media, BigInteger> {
}
