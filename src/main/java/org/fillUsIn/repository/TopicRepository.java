package org.fillUsIn.repository;

import org.fillUsIn.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, String> { }