package fit.hutech.NguyenDaiKimCuong.viewmodels;

import fit.hutech.NguyenDaiKimCuong.entities.Book;
import fit.hutech.NguyenDaiKimCuong.entities.Category;
import fit.hutech.NguyenDaiKimCuong.services.CategoryService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record BookPostVm(
        @NotBlank(message = "Tên sách là bắt buộc")
        String title,
        
        @NotBlank(message = "Tác giả là bắt buộc")
        String author,
        
        @NotNull(message = "Giá sách là bắt buộc")
        @Positive(message = "Giá sách phải lớn hơn 0")
        Double price,
        
        @NotNull(message = "Danh mục là bắt buộc")
        Long categoryId
) {
    public static BookPostVm from(@NotNull Book book) {
        return new BookPostVm(
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getCategory().getId()
        );
    }
    
    public Book toBook(CategoryService categoryService) {
        Category category = categoryService.getCategoryById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục với mã: " + categoryId));
        
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPrice(price);
        book.setCategory(category);
        
        return book;
    }
}
