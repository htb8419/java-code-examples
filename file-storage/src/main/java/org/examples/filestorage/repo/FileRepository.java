package org.examples.filestorage.repo;

import org.examples.filestorage.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FileRepository extends JpaRepository<FileInfo, Integer> {

    Optional<FileInfo> findByUid(UUID uid);
}