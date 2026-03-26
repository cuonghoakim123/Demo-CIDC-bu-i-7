-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.4.3 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping data for table cuongstore.book: ~25 rows (approximately)
INSERT INTO `book` (`id`, `author`, `price`, `title`, `category_id`, `image_url`, `description`) VALUES
	(1, 'Ánh Nguyễn', 29.99, 'Lập trình Web Spring Framework', 1, 'https://i.pinimg.com/736x/e1/b7/59/e1b759622f0961aad75ad23a9bb74019.jpg', 'Lập trình Web Spring Framework là đầu sách của tác giả Ánh Nguyễn. Nội dung này được tạo tự động để trang chi tiết có phần giới thiệu cơ bản cho đến khi quản trị viên cập nhật mô tả đầy đủ hơn.'),
	(2, 'Huy Cường', 45.5, 'Lập trình ứng dụng Java', 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4cZSC4WC8e8QQhFcGx0jgBNXlApCTsRmARg&s', '"Lập trình ứng dụng Java" là đầu sách của tác giả Huy Cường. Nội dung này được tạo tự động để trang chi tiết có phần giới thiệu cơ bản cho đến khi quản trị viên cập nhật mô tả đầy đủ hơn.'),
	(3, 'Xuân Nhân', 12, 'Lập trình Web Spring Boot', 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQhEz7KejbHr-t4_BWuYzR_9Tzly79Eeu3yw&s', 'Lập trình Web Spring Boot là đầu sách của tác giả Xuân Nhân. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(4, 'Ánh Nguyễn', 0.12, 'Lập trình Web Spring MVC', 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZ0bQRnoXhY661oh0ZtSaF2cb9f3oq6hGkIg&s', 'Lập trình Web Spring MVC là đầu sách của tác giả Ánh Nguyễn. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(10, 'Nguyen Anh', 29.99, 'Spring Boot in Practice', 7, 'https://picsum.photos/seed/book-01/600/900', 'Spring Boot in Practice là đầu sách của tác giả Nguyen Anh. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(11, 'Tran Cuong', 34.5, 'Mastering Java 17', 7, 'https://picsum.photos/seed/book-02/600/900', 'Mastering Java 17 là đầu sách của tác giả Tran Cuong. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(12, 'Le Minh', 22, 'Frontend Design Patterns', 7, 'https://picsum.photos/seed/book-03/600/900', 'Frontend Design Patterns là đầu sách của tác giả Le Minh. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(13, 'Diamond', 30.3, 'Clean Architecture Handbook Diamond', 8, 'https://picsum.photos/seed/book-04/600/900', 'Clean Architecture Handbook là đầu sách của tác giả Robert Kim. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(14, 'Phan Huy', 18.9, 'Data Structures Visual Guide', 7, 'https://picsum.photos/seed/book-05/600/900', 'Data Structures Visual Guide là đầu sách của tác giả Phan Huy. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(15, 'Mai Linh', 15.2, 'The Silent River', 9, 'https://picsum.photos/seed/book-06/600/900', 'The Silent River là đầu sách của tác giả Mai Linh. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(16, 'An Kha', 12.8, 'Letters from Autumn', 9, 'https://picsum.photos/seed/book-07/600/900', 'Letters from Autumn là đầu sách của tác giả An Kha. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(17, 'Bao Nguyen', 14.4, 'City of Memories', 9, 'https://picsum.photos/seed/book-08/600/900', 'City of Memories là đầu sách của tác giả Bao Nguyen. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(18, 'Thanh Vu', 16.1, 'Midnight Journal', 9, 'https://picsum.photos/seed/book-09/600/900', 'Midnight Journal là đầu sách của tác giả Thanh Vu. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(19, 'Ha Pham', 19.3, 'Everyday Physics', 6, 'https://picsum.photos/seed/book-10/600/900', 'Everyday Physics là đầu sách của tác giả Ha Pham. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(20, 'Quoc Dat', 23, 'Planet Earth Atlas', 6, 'https://picsum.photos/seed/book-11/600/900', 'Planet Earth Atlas là đầu sách của tác giả Quoc Dat. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(21, 'Lan Chi', 17.6, 'Biology Made Simple', 6, 'https://picsum.photos/seed/book-12/600/900', 'Biology Made Simple là đầu sách của tác giả Lan Chi. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(22, 'Doan Kiet', 20.25, 'Thinking in Numbers', 6, 'https://picsum.photos/seed/book-13/600/900', 'Thinking in Numbers là đầu sách của tác giả Doan Kiet. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(23, 'My Tran', 21.5, 'Startup from Zero', 10, 'https://picsum.photos/seed/book-14/600/900', 'Startup from Zero là đầu sách của tác giả My Tran. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(24, 'Long Nguyen', 24, 'Small Team, Big Results', 10, 'https://picsum.photos/seed/book-15/600/900', 'Small Team, Big Results là đầu sách của tác giả Long Nguyen. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(25, 'Khanh Duong', 18.3, 'Finance for Beginners', 10, 'https://picsum.photos/seed/book-16/600/900', 'Finance for Beginners là đầu sách của tác giả Khanh Duong. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(26, 'Gia Han', 10.5, 'Colorful Alphabet', 11, 'https://picsum.photos/seed/book-17/600/900', 'Colorful Alphabet là đầu sách của tác giả Gia Han. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(27, 'Quynh Nhu', 11.2, 'Moonlight Fairy Tales', 11, 'https://picsum.photos/seed/book-18/600/900', 'Moonlight Fairy Tales là đầu sách của tác giả Quynh Nhu. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(28, 'Bao Ngoc', 9.99, 'Adventures of Tiny Fox', 11, 'https://picsum.photos/seed/book-19/600/900', 'Adventures of Tiny Fox là đầu sách của tác giả Bao Ngoc. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(29, 'Duc Anh', 12.4, 'My First Science Lab', 11, 'https://picsum.photos/seed/book-20/600/900', 'My First Science Lab là đầu sách của tác giả Duc Anh. Nội dung giới thiệu đang được cập nhật để hiển thị đầy đủ và đúng tiếng Việt.'),
	(30, 'Diamont', 100000, 'Sách mới', 2, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShTqAmBYbEvkNcbzJgxGrqMZqDFWpF7f6Cvw&s', 'ABCD');

-- Dumping data for table cuongstore.category: ~11 rows (approximately)
INSERT INTO `category` (`id`, `name`) VALUES
	(1, 'Công nghệ thông tin'),
	(2, 'Văn học'),
	(3, 'Khoa học'),
	(4, 'Fiction'),
	(5, 'Non-Fiction'),
	(6, 'Science'),
	(7, 'Technology'),
	(8, 'History'),
	(9, 'Literature'),
	(10, 'Business'),
	(11, 'Children');

-- Dumping data for table cuongstore.invoice: ~0 rows (approximately)

-- Dumping data for table cuongstore.invoices: ~4 rows (approximately)
INSERT INTO `invoices` (`id`, `invoice_date`, `total`, `user_id`, `customer_email`, `customer_name`, `customer_phone`, `order_number`, `payment_method`, `shipping_address`, `status`) VALUES
	(4, '2026-02-07 07:33:23.840000', 50.193000000000005, 1, 'linhheocute1704@gmail.com', 'Nguyen Cuong', '0999999998', 'ORD-1770449603832', 'Cash on Delivery', 'dsad, TP. H??? Ch?? Minh 700000', 'PROCESSING'),
	(5, '2026-02-07 08:08:19.255000', 65.97800000000001, 16, 'linhhaeocute1704@gmail.com', 'nguyen cuong', '0901234567', 'ORD-1770451699247', 'Cash on Delivery', 'dsad2, TP. H??? Ch?? Minh 700000', 'CANCELLED'),
	(10, '2026-03-05 02:59:04.921000', 32.989000000000004, 28, 'linhheocute1704@gmail.com', 'zzzzzz', '0356012250', 'ORD-1772679544916', 'Credit/Debit Card', 'dsad, TP. H??? Ch?? Minh 700000', 'PROCESSING'),
	(12, '2026-03-12 03:48:52.772000', 105.91, 28, 'cdsao@gmail.com', 'cuonghoakim123321', '0999999924', NULL, NULL, NULL, 'CART');

-- Dumping data for table cuongstore.item_invoice: ~7 rows (approximately)
INSERT INTO `item_invoice` (`id`, `quantity`, `book_id`, `invoice_id`) VALUES
	(4, 1, 2, 4),
	(5, 2, 1, 5),
	(10, 1, 1, 10),
	(23, 1, 2, 12),
	(24, 1, 4, 12),
	(25, 1, 10, 12),
	(26, 1, 13, 12);

-- Dumping data for table cuongstore.role: ~2 rows (approximately)
INSERT INTO `role` (`id`, `description`, `name`) VALUES
	(1, 'Administrator role with full access', 'ADMIN'),
	(2, 'Regular user role', 'USER');

-- Dumping data for table cuongstore.user: ~5 rows (approximately)
INSERT INTO `user` (`id`, `username`, `password`, `email`, `phone`, `provider`) VALUES
	(1, 'cuonghoakim123', '$2a$10$AESGoIZXQwC/J4Fur7eOpepQapQKNQipljOcuExevq6.EK/mB0BMC', 'cuonghotran17022004@gmail.com', '0356012250', NULL),
	(3, 'khanhtrinh1233@gmail.com', 'OAUTH2_USER', 'khanhtrinh1233@gmail.com', NULL, 'Google'),
	(12, 'hutech', '$2a$10$HdPnmBVJGMsZ4hsAqqzHKOjf/A63HASK690I/ILK71TIjD9ZpxI02', 'hutech@bookstore.com', NULL, 'Local'),
	(16, 'cuongg@gmail.com', '$2a$10$tHEMDnNeXmSGEPKwSc1kTeX1G5BFC6xSQOwSaKmYR4U8JEM95Tyym', 'cuongg@gmail.com', '0999999998', 'Local'),
	(28, 'cuonghoakim123321', '$2a$10$wJc7qCbLnuuKtazOFW/GVO7SzykE4cXFOj9eH3RTvmnnOpzB1qs6C', 'cdsao@gmail.com', '0999999924', 'Local');

-- Dumping data for table cuongstore.user_role: ~7 rows (approximately)
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
	(1, 1),
	(12, 1),
	(16, 1),
	(1, 2),
	(3, 2),
	(16, 2),
	(28, 2);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
