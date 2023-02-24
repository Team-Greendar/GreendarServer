package greendar.infra.gcp.storage.api;

import greendar.global.common.ApiResponse;
import greendar.infra.gcp.storage.application.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class StorageApi {
    private final FileService fileService;

    @PostMapping(value = "/file",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse addFile(@RequestParam("file") MultipartFile[] file){
        if(file.length > 1) return ApiResponse.fail("add multiple files in file");
        return ApiResponse.success(fileService.uploadFiles(file).get(0).getFileName());
    }
    @PostMapping(value = "/files",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse addFiles(@RequestParam("files") MultipartFile[] files){
        return ApiResponse.success(fileService.uploadFiles(files));
    }
}
