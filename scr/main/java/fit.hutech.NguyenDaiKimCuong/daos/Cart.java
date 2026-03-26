package fit.hutech.NguyenDaiKimCuong.daos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Cart {

    private List<Item> cartItems = new ArrayList<>();

    // Thêm sản phẩm vào giỏ
    public void addItems(Item item) {
        boolean isExist = cartItems.stream()
                .filter(i -> Objects.equals(i.getBookId(), item.getBookId()))
                .findFirst()
                .map(i -> {
                    i.setQuantity(i.getQuantity() + item.getQuantity());
                    return true;
                })
                .orElse(false);

        if (!isExist) {
            cartItems.add(item);
        }
    }

    // Xoá sản phẩm khỏi giỏ
    public void removeItems(Long bookId) {
        cartItems.removeIf(item ->
                Objects.equals(item.getBookId(), bookId));
    }

    // Cập nhật số lượng
    public void updateItems(Long bookId, int quantity) {
        cartItems.stream()
                .filter(item -> Objects.equals(item.getBookId(), bookId))
                .forEach(item -> item.setQuantity(quantity));
    }

    // Tổng số lượng sản phẩm
    public int getTotalQuantity() {
        return cartItems.stream()
                .mapToInt(Item::getQuantity)
                .sum();
    }

    // Tổng tiền
    public double getTotalPrice() {
        return cartItems.stream()
                .mapToDouble(item ->
                        item.getPrice() * item.getQuantity())
                .sum();
    }

    // Xoá toàn bộ giỏ hàng
    public void clear() {
        cartItems.clear();
    }
}
