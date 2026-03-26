package fit.hutech.NguyenDaiKimCuong.entities;

import fit.hutech.NguyenDaiKimCuong.validators.annotations.ValidCategoryId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 50, nullable = false)
    @Size(min = 1, max = 50, message = "Tên sách phải từ 1 đến 50 ký tự")
    @NotBlank(message = "Tên sách không được để trống")
    private String title;

    @Column(name = "author", length = 50, nullable = false)
    @Size(min = 1, max = 50, message = "Tên tác giả phải từ 1 đến 50 ký tự")
    @NotBlank(message = "Tên tác giả không được để trống")
    private String author;

    @Column(name = "price")
    @Positive(message = "Giá sách phải lớn hơn 0")
    private Double price;

    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @Column(name = "description", length = 2000)
    @Size(max = 2000, message = "Mô tả sách không được vượt quá 2000 ký tự")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ValidCategoryId
    @ToString.Exclude
    private Category category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ItemInvoice> itemInvoices = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return getId() != null && Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
