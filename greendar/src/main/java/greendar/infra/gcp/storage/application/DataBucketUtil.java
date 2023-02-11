package greendar.infra.gcp.storage.application;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BucketGetOption;
import com.google.cloud.storage.StorageOptions;
import greendar.infra.gcp.storage.dto.FileDto;
import greendar.infra.gcp.storage.exception.BadRequestException;
import greendar.infra.gcp.storage.exception.FileWriteException;
import greendar.infra.gcp.storage.exception.GCPFileUploadException;
import greendar.infra.gcp.storage.exception.InvalidFileTypeException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DataBucketUtil {

    @Value("${spring.cloud.gcp.credentials.location}")
    private String gcpConfigFile;
    @Value("${spring.cloud.gcp.project.id}")
    private String gcpProjectId;
    @Value("${spring.cloud.gcp.bucket.id}")
    private String gcpBucketId;

    public FileDto uploadFile(MultipartFile multipartFile, String fileName,String gcpDirectoryName) {

        try{
            byte [] fileData=multipartFile.getBytes();
            InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();
            StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream)).build();

            Storage storage = options.getService();
            Bucket bucket = storage.get(gcpBucketId, BucketGetOption.fields());

            RandomString id = new RandomString(6, ThreadLocalRandom.current());

            String contentType = "image";
            Blob blob = bucket.create(gcpDirectoryName + "/" + fileName + "-" + id.nextString() + checkFileExtension(fileName), fileData, contentType);

            if(blob != null){
                return new FileDto(blob.getName(),"https://storage.cloud.google.com/"+gcpBucketId+"/"+blob.getName());
            }

        }catch (Exception e){
            throw new GCPFileUploadException("An error occurred while storing data to GCS");
        }
        throw new GCPFileUploadException("An error occurred while storing data to GCS");
    }

    private File convertFile(MultipartFile file) {

        try{
            if(file.getOriginalFilename() == null){
                throw new BadRequestException("Original file name is null");
            }
            File convertedFile = new File(file.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();

            return convertedFile;
        }catch (Exception e){
            throw new FileWriteException("An error has occurred while converting the file");
        }
    }

    private String checkFileExtension(String fileName) {
        if(fileName != null && fileName.contains(".")){
            String[] extensionList = {".png", ".jpeg",".jpg",".JPG"};
            for(String extension: extensionList) {
                if (fileName.endsWith(extension)) {
                    return extension;
                }
            }
        }
        throw new InvalidFileTypeException("Not a permitted file type");
    }
}
