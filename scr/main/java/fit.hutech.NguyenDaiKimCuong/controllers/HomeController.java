package fit.hutech.NguyenDaiKimCuong.controllers;

import fit.hutech.NguyenDaiKimCuong.services.BookService;
import fit.hutech.NguyenDaiKimCuong.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model,
                      @RequestParam(defaultValue = "0") Integer pageNo,
                      @RequestParam(defaultValue = "12") Integer pageSize,
                      @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("books", bookService.getAllBooks(pageNo, pageSize, sortBy));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("currentPage", pageNo);
        return "home/index";
    }
    
    @GetMapping("/home")
    public String homePage(Model model,
                          @RequestParam(defaultValue = "0") Integer pageNo,
                          @RequestParam(defaultValue = "12") Integer pageSize,
                          @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("books", bookService.getAllBooks(pageNo, pageSize, sortBy));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("currentPage", pageNo);
        return "home/index";
    }
}
