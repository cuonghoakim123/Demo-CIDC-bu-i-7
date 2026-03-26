package fit.hutech.NguyenDaiKimCuong;

import fit.hutech.NguyenDaiKimCuong.entities.Book;
import fit.hutech.NguyenDaiKimCuong.entities.Category;
import fit.hutech.NguyenDaiKimCuong.repositories.IBookRepository;
import fit.hutech.NguyenDaiKimCuong.repositories.ICategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {

    @Bean
    public CommandLineRunner dataLoader(IBookRepository bookRepository,
                                        ICategoryRepository categoryRepository) {
        return args -> {
            Category technology = getOrCreateCategory(categoryRepository, "Technology");
            Category literature = getOrCreateCategory(categoryRepository, "Literature");
            Category science = getOrCreateCategory(categoryRepository, "Science");
            Category business = getOrCreateCategory(categoryRepository, "Business");
            Category children = getOrCreateCategory(categoryRepository, "Children");

            Map<String, Category> categoryMap = Map.of(
                    "Technology", technology,
                    "Literature", literature,
                    "Science", science,
                    "Business", business,
                    "Children", children
            );

            Map<String, SeedBook> seedBookMap = new LinkedHashMap<>();

            List<SeedBook> seedBooks = List.of(
                    new SeedBook(
                            "Spring Boot in Practice",
                            "Nguyen Anh",
                            29.99,
                            "Technology",
                            "https://picsum.photos/seed/book-01/600/900",
                            "Cuốn sách tập trung vào cách xây dựng ứng dụng Spring Boot thực tế, từ cấu trúc dự án, API, bảo mật đến triển khai. Nội dung phù hợp cho sinh viên và lập trình viên muốn có một lộ trình rõ ràng khi làm dự án Java web."
                    ),
                    new SeedBook(
                            "Mastering Java 17",
                            "Tran Cuong",
                            34.50,
                            "Technology",
                            "https://picsum.photos/seed/book-02/600/900",
                            "Tài liệu tổng hợp các điểm mới trong Java 17, từ cú pháp, record, sealed class đến các cải tiến về hiệu năng. Sách phù hợp cho người muốn nâng cấp nền tảng Java hiện đại và áp dụng vào hệ thống doanh nghiệp."
                    ),
                    new SeedBook(
                            "Frontend Design Patterns",
                            "Le Minh",
                            22.00,
                            "Technology",
                            "https://picsum.photos/seed/book-03/600/900",
                            "Giới thiệu các mẫu thiết kế giao diện phổ biến, cách tổ chức component và tối ưu trải nghiệm người dùng. Nội dung cân bằng giữa nguyên tắc thiết kế, bố cục trực quan và khả năng triển khai trong dự án thật."
                    ),
                    new SeedBook(
                            "Clean Architecture Handbook",
                            "Robert Kim",
                            27.75,
                            "Technology",
                            "https://picsum.photos/seed/book-04/600/900",
                            "Giải thích kiến trúc sạch qua ví dụ cụ thể, giúp tách biệt domain, application và hạ tầng. Cuốn sách hữu ích cho các nhóm muốn cải thiện khả năng bảo trì và mở rộng hệ thống lâu dài."
                    ),
                    new SeedBook(
                            "Data Structures Visual Guide",
                            "Phan Huy",
                            18.90,
                            "Technology",
                            "https://picsum.photos/seed/book-05/600/900",
                            "Mô tả các cấu trúc dữ liệu theo cách trực quan, dễ theo dõi bằng sơ đồ và tình huống minh họa. Đây là tài liệu phù hợp để ôn tập nền tảng giải thuật và chuẩn bị cho phỏng vấn kỹ thuật."
                    ),
                    new SeedBook(
                            "The Silent River",
                            "Mai Linh",
                            15.20,
                            "Literature",
                            "https://picsum.photos/seed/book-06/600/900",
                            "Một câu chuyện nhẹ nhàng về ký ức, gia đình và hành trình đi tìm sự bình yên bên trong. Tác phẩm nổi bật ở nhịp kể chậm, giàu cảm xúc và nhiều chi tiết đời thường."
                    ),
                    new SeedBook(
                            "Letters from Autumn",
                            "An Kha",
                            12.80,
                            "Literature",
                            "https://picsum.photos/seed/book-07/600/900",
                            "Tập truyện ngắn với những bức thư chưa từng gửi, phản chiếu các mối quan hệ mong manh giữa người với người. Văn phong trong trẻo, giàu hình ảnh và dễ chạm cảm xúc."
                    ),
                    new SeedBook(
                            "City of Memories",
                            "Bao Nguyen",
                            14.40,
                            "Literature",
                            "https://picsum.photos/seed/book-08/600/900",
                            "Khắc họa một đô thị qua những mảnh ký ức đan xen của nhiều nhân vật. Cuốn sách tạo chiều sâu bằng bối cảnh quen thuộc và góc nhìn rất điện ảnh."
                    ),
                    new SeedBook(
                            "Midnight Journal",
                            "Thanh Vu",
                            16.10,
                            "Literature",
                            "https://picsum.photos/seed/book-09/600/900",
                            "Nhật ký đêm khuya ghi lại những suy tư về tuổi trẻ, lựa chọn và nỗi cô đơn. Tác phẩm phù hợp với độc giả thích phong cách tự sự gần gũi, nhiều chiêm nghiệm."
                    ),
                    new SeedBook(
                            "Everyday Physics",
                            "Ha Pham",
                            19.30,
                            "Science",
                            "https://picsum.photos/seed/book-10/600/900",
                            "Biến các khái niệm vật lý quen thuộc thành ví dụ đời sống dễ hiểu, từ chuyển động, ánh sáng đến năng lượng. Nội dung thích hợp cho người học muốn hiểu bản chất thay vì chỉ ghi nhớ công thức."
                    ),
                    new SeedBook(
                            "Planet Earth Atlas",
                            "Quoc Dat",
                            23.00,
                            "Science",
                            "https://picsum.photos/seed/book-11/600/900",
                            "Tổng hợp kiến thức về địa chất, khí hậu và hệ sinh thái trên Trái Đất qua hình ảnh minh họa đẹp mắt. Đây là cuốn sách tham khảo tốt cho học tập và mở rộng hiểu biết tự nhiên."
                    ),
                    new SeedBook(
                            "Biology Made Simple",
                            "Lan Chi",
                            17.60,
                            "Science",
                            "https://picsum.photos/seed/book-12/600/900",
                            "Trình bày các nguyên lý sinh học nền tảng theo phong cách ngắn gọn, có ví dụ và sơ đồ hỗ trợ. Phù hợp với học sinh, sinh viên hoặc người cần ôn nhanh kiến thức cốt lõi."
                    ),
                    new SeedBook(
                            "Thinking in Numbers",
                            "Doan Kiet",
                            20.25,
                            "Science",
                            "https://picsum.photos/seed/book-13/600/900",
                            "Sách khai thác tư duy toán học trong những quyết định thường ngày và các bài toán thực tế. Nội dung giúp người đọc cải thiện suy luận logic và khả năng nhìn vấn đề có cấu trúc."
                    ),
                    new SeedBook(
                            "Startup from Zero",
                            "My Tran",
                            21.50,
                            "Business",
                            "https://picsum.photos/seed/book-14/600/900",
                            "Phân tích cách xây dựng một startup từ giai đoạn ý tưởng đến kiểm chứng thị trường. Cuốn sách chú trọng chiến lược thực thi, quản trị rủi ro và tối ưu nguồn lực ban đầu."
                    ),
                    new SeedBook(
                            "Small Team, Big Results",
                            "Long Nguyen",
                            24.00,
                            "Business",
                            "https://picsum.photos/seed/book-15/600/900",
                            "Tập trung vào cách vận hành đội nhóm nhỏ nhưng đạt hiệu quả cao thông qua mục tiêu, giao tiếp và đo lường rõ ràng. Phù hợp với trưởng nhóm, quản lý dự án và founder giai đoạn đầu."
                    ),
                    new SeedBook(
                            "Finance for Beginners",
                            "Khanh Duong",
                            18.30,
                            "Business",
                            "https://picsum.photos/seed/book-16/600/900",
                            "Giải thích các khái niệm tài chính cá nhân và doanh nghiệp theo cách dễ tiếp cận, từ ngân sách đến dòng tiền. Đây là điểm khởi đầu tốt cho người mới làm quen với quản lý tài chính."
                    ),
                    new SeedBook(
                            "Colorful Alphabet",
                            "Gia Han",
                            10.50,
                            "Children",
                            "https://picsum.photos/seed/book-17/600/900",
                            "Sách thiếu nhi với hình minh họa rực rỡ, giúp bé làm quen bảng chữ cái qua hình ảnh và ví dụ gần gũi. Nội dung ngắn, vui tươi và dễ đọc cùng phụ huynh."
                    ),
                    new SeedBook(
                            "Moonlight Fairy Tales",
                            "Quynh Nhu",
                            11.20,
                            "Children",
                            "https://picsum.photos/seed/book-18/600/900",
                            "Tuyển tập truyện cổ tích trước giờ đi ngủ với nhịp kể nhẹ nhàng và hình ảnh giàu tưởng tượng. Cuốn sách khuyến khích trí tưởng tượng và thói quen đọc sách cho trẻ nhỏ."
                    ),
                    new SeedBook(
                            "Adventures of Tiny Fox",
                            "Bao Ngoc",
                            9.99,
                            "Children",
                            "https://picsum.photos/seed/book-19/600/900",
                            "Hành trình phiêu lưu của chú cáo nhỏ qua nhiều thử thách nhỏ nhưng giàu bài học về lòng dũng cảm và tình bạn. Phù hợp với độc giả nhỏ tuổi yêu truyện tranh minh họa."
                    ),
                    new SeedBook(
                            "My First Science Lab",
                            "Duc Anh",
                            12.40,
                            "Children",
                            "https://picsum.photos/seed/book-20/600/900",
                            "Mang đến những thí nghiệm đơn giản, an toàn và thú vị để trẻ làm quen với khoa học. Nội dung khơi gợi sự tò mò và tạo nền tảng học tập qua trải nghiệm."
                    )
            );

            seedBooks.forEach(seedBook -> seedBookMap.put(seedBook.title(), seedBook));

            bookRepository.findAll().forEach(existingBook -> {
                SeedBook matchedSeedBook = seedBookMap.get(existingBook.getTitle());

                if (matchedSeedBook != null) {
                    if (isBlank(existingBook.getImageUrl())) {
                        existingBook.setImageUrl(matchedSeedBook.imageUrl());
                    }
                    if (isBlank(existingBook.getDescription())) {
                        existingBook.setDescription(matchedSeedBook.description());
                    }
                } else {
                    if (isBlank(existingBook.getImageUrl())) {
                        existingBook.setImageUrl("https://picsum.photos/seed/book-" + existingBook.getId() + "/600/900");
                    }
                    if (isBlank(existingBook.getDescription())) {
                                                existingBook.setDescription("\"" + existingBook.getTitle() + "\" là đầu sách của tác giả " + existingBook.getAuthor() + ". Nội dung này được tạo tự động để trang chi tiết có phần giới thiệu cơ bản cho đến khi quản trị viên cập nhật mô tả đầy đủ hơn.");
                    }
                }

                bookRepository.save(existingBook);
            });

            if (bookRepository.count() < 20) {
                Set<String> existingTitles = bookRepository.findAll().stream()
                        .map(Book::getTitle)
                        .collect(Collectors.toSet());

                seedBooks.stream()
                        .filter(seedBook -> !existingTitles.contains(seedBook.title()))
                        .forEach(seedBook -> bookRepository.save(
                                Book.builder()
                                        .title(seedBook.title())
                                        .author(seedBook.author())
                                        .price(seedBook.price())
                                        .category(categoryMap.get(seedBook.categoryName()))
                                        .imageUrl(seedBook.imageUrl())
                                        .description(seedBook.description())
                                        .build()
                        ));
            }
        };
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private Category getOrCreateCategory(ICategoryRepository categoryRepository, String categoryName) {
        return categoryRepository.findAll().stream()
                .filter(category -> categoryName.equalsIgnoreCase(category.getName()))
                .findFirst()
                .orElseGet(() -> categoryRepository.save(
                        Category.builder()
                                .name(categoryName)
                                .build()
                ));
    }

    private record SeedBook(String title,
                            String author,
                            Double price,
                            String categoryName,
                                                        String imageUrl,
                                                        String description) {
    }
}
