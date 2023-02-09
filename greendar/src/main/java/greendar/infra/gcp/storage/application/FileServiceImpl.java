package greendar.infra.gcp.storage.application;

import greendar.infra.gcp.storage.dao.FileRepository;
import greendar.infra.gcp.storage.domain.InputFile;
import greendar.infra.gcp.storage.dto.FileDto;
import greendar.infra.gcp.storage.exception.BadRequestException;
import greendar.infra.gcp.storage.exception.GCPFileUploadException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
    private final FileRepository fileRepository;
    private final DataBucketUtil dataBucketUtil;

    public FileDto uploadFile(MultipartFile file){
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null){
            throw new BadRequestException("Original file name is null");
        }
        try {
            return dataBucketUtil.uploadFile(file, originalFileName,"test");
        } catch (Exception e) {
            throw new GCPFileUploadException("Error occurred while uploading");
        }
    }
    public List<InputFile> uploadFiles(MultipartFile[] files) {

        List<InputFile> inputFiles = new ArrayList<>();

        Arrays.asList(files).forEach(file -> {
            String originalFileName = file.getOriginalFilename();
            if(originalFileName == null){
                throw new BadRequestException("Original file name is null");
            }

            try {
                FileDto fileDto = dataBucketUtil.uploadFile(file, originalFileName,"test");
                if (fileDto != null) {
                    inputFiles.add(new InputFile(fileDto.getFileName(), fileDto.getFileUrl()));
                }
            } catch (Exception e) {
                throw new GCPFileUploadException("Error occurred while uploading");
            }
        });
        fileRepository.saveAll(inputFiles);
        return inputFiles;
    }
}
