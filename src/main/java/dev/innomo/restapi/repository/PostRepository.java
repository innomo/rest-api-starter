package dev.innomo.restapi.repository;

import dev.innomo.restapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
