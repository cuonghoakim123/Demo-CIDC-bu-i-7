package fit.hutech.NguyenDaiKimCuong.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class ImageStorageService {

    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024;
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp", "gif");

    @Value("${app.upload.dir:uploads/books}")
    private String uploadDir;

    public String saveBookImage(MultipartFile imageFile) {
        validateImage(imageFile);

        try {
            Path uploadPath = resolveUploadPath();
            Files.createDirectories(uploadPath);

            String extension = getFileExtension(imageFile.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID() + "." + extension;
            Path destination = uploadPath.resolve(uniqueFileName);

            Files.copy(imageFile.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/books/" + uniqueFileName;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Không thể lưu ảnh. Vui lòng thử lại.");
        }
    }

    private void validateImage(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn ảnh cần tải lên.");
        }

        if (imageFile.getSize() > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException("Ảnh vượt quá dung lượng 10MB.");
        }

        String extension = getFileExtension(imageFile.getOriginalFilename());
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Định dạng ảnh không hợp lệ. Chỉ hỗ trợ JPG, JPEG, PNG, WEBP, GIF.");
        }
    }

    private Path resolveUploadPath() {
        Path path = Paths.get(uploadDir);
        if (path.isAbsolute()) {
            return path;
        }
        return Paths.get(System.getProperty("user.dir")).resolve(path);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }

        return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    }
}
