package org.project.urlshorteningservice.repository;

import org.project.urlshorteningservice.model.URL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface URLRepository extends JpaRepository<URL, Long> {
    Optional<URL> findURLByShortURL(String shortURL);
}
