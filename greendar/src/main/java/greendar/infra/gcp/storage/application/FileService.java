package greendar.infra.gcp.storage.application;

import greendar.infra.gcp.storage.domain.InputFile;
import greendar.infra.gcp.storage.dto.FileDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    List<InputFile> uploadFiles(MultipartFile[] files);
    FileDto uploadFile(MultipartFile file);
}
