package com.example.demo.repository;

import com.example.demo.entity.URLLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLLinkRepository extends JpaRepository<URLLink, Long> {
    Optional<URLLink> findURLLinkByShortenedLink(String shortenedLink);
}
