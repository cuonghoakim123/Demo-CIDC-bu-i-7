package fit.hutech.NguyenDaiKimCuong.controllers;

import fit.hutech.NguyenDaiKimCuong.entities.Book;
import fit.hutech.NguyenDaiKimCuong.services.BookService;
import fit.hutech.NguyenDaiKimCuong.services.CartService;
import fit.hutech.NguyenDaiKimCuong.services.CategoryService;
import fit.hutech.NguyenDaiKimCuong.viewmodels.BookGetVm;
import fit.hutech.NguyenDaiKimCuong.viewmodels.BookPostVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ApiController {
    
    private final BookService bookService;
    private final CategoryService categoryService;
    private final CartService cartService;
    
    @GetMapping("/books")
    public ResponseEntity<List<BookGetVm>> getAllBooks(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        return ResponseEntity.ok(bookService.getAllBooks(
                pageNo == null ? 0 : pageNo,
                pageSize == null ? 20 : pageSize,
                sortBy == null ? "id" : sortBy)
                .stream()
                .map(BookGetVm::from)
                .toList());
    }
    
    @GetMapping("/books/{id}")
    public ResponseEntity<BookGetVm> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id)
                .map(BookGetVm::from)
                .orElse(null));
    }
    
    @PostMapping("/books")
    public ResponseEntity<BookGetVm> createBook(@Valid @RequestBody BookPostVm bookPostVm) {
        Book book = bookPostVm.toBook(categoryService);
        bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BookGetVm.from(book));
    }
    
    @PutMapping("/books/{id}")
    public ResponseEntity<BookGetVm> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookPostVm bookPostVm
    ) {
        Book book = bookPostVm.toBook(categoryService);
        book.setId(id);
        bookService.updateBook(book);
        return ResponseEntity.ok(bookService.getBookById(id)
                .map(BookGetVm::from)
                .orElse(null));
    }
    
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/books/search")
    public ResponseEntity<List<BookGetVm>> searchBooks(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(bookService.searchBook(keyword)
                .stream()
                .map(BookGetVm::from)
                .toList());
    }
}
