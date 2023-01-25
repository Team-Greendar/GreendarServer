package greendar.infra.gcp.storage.dao;

import greendar.infra.gcp.storage.domain.InputFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<InputFile, Long> {
}
