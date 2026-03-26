package fit.hutech.NguyenDaiKimCuong.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:uploads/books}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path resolvedUploadPath = resolveUploadPath();
        Path rootUploadPath = resolvedUploadPath.getParent() != null
            ? resolvedUploadPath.getParent()
            : resolvedUploadPath;
        String uploadAbsolutePath = rootUploadPath.toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadAbsolutePath);
    }

    private Path resolveUploadPath() {
        Path path = Paths.get(uploadDir);
        if (path.isAbsolute()) {
            return path;
        }
        return Paths.get(System.getProperty("user.dir")).resolve(path);
    }
}
