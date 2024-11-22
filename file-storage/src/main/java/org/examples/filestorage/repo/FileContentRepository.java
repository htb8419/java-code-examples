package org.examples.filestorage.repo;

import org.examples.filestorage.model.FileContent;
import org.examples.filestorage.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileContentRepository extends JpaRepository<FileContent, Long> {

    Optional<FileContent> findByUid(UUID uid);
}
