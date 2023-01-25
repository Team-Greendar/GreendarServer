package greendar.infra.gcp.storage.api;

import greendar.infra.gcp.storage.application.FileService;
import greendar.infra.gcp.storage.domain.InputFile;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor

public class StorageApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageApi.class);

    private final FileService fileService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<InputFile> addFile(@RequestParam("files") MultipartFile[] files){
        LOGGER.debug("Call addFile API");
        return fileService.uploadFiles(files);
    }
}
