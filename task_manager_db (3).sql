-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 10, 2026 lúc 05:22 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `task_manager_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `binh_luan`
--

CREATE TABLE `binh_luan` (
  `id` int(11) NOT NULL,
  `noi_dung` text DEFAULT NULL,
  `thoi_gian_tao` datetime(6) DEFAULT NULL,
  `nguoi_dung_id` int(11) DEFAULT NULL,
  `nhiem_vu_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `binh_luan`
--

INSERT INTO `binh_luan` (`id`, `noi_dung`, `thoi_gian_tao`, `nguoi_dung_id`, `nhiem_vu_id`) VALUES
(1, 'áddasd', '2026-06-06 21:41:40.000000', 1, 3),
(2, 'việc này chưa ổn ', '2026-06-06 21:41:49.000000', 1, 3),
(3, 'oke sếp', '2026-06-06 21:42:20.000000', 3, 3),
(4, 'đáĐÂSD', '2026-06-06 22:51:39.000000', 1, 8),
(5, 'DSADASDASD', '2026-06-06 22:52:07.000000', 2, 8),
(6, 'khánh ', '2026-06-08 22:11:00.000000', 1, 13);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `du_an`
--

CREATE TABLE `du_an` (
  `id` int(11) NOT NULL,
  `ten_du_an` varchar(255) NOT NULL,
  `mo_ta` varchar(255) DEFAULT NULL,
  `trang_thai` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `du_an`
--

INSERT INTO `du_an` (`id`, `ten_du_an`, `mo_ta`, `trang_thai`) VALUES
(1, 'Phát triển Website OceanWave', 'Dự án cuối kỳ môn Java Spring Boot', 'OPEN'),
(2, 'Nghiên cứu Thị trường Thủy sản', 'Khảo sát nhu cầu cá ngừ tại Nha Trang', 'OPEN'),
(3, 'Phát Triển Ứng Dụng Web', 'Dự án cuối kỳ môn web2', NULL),
(5, 'Xây dựng website đọc truyện', '', 'OPEN');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nguoi_dung`
--

CREATE TABLE `nguoi_dung` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `ho_ten` varchar(255) DEFAULT NULL,
  `vai_tro` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `nguoi_dung`
--

INSERT INTO `nguoi_dung` (`id`, `username`, `password`, `ho_ten`, `vai_tro`) VALUES
(1, 'admin', '123456', 'Cái Trần Đăng Khôi ', 'LEADER'),
(2, 'nhanvien1', '123456', 'Nguyễn Quốc Khánh', 'MEMBER'),
(3, 'nhanvien2', '123456', 'Huỳnh Đức Nghĩa', 'MEMBER'),
(5, 'nhanvien4', '123456', 'Trần Văn B', 'MEMBER');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhiem_vu`
--

CREATE TABLE `nhiem_vu` (
  `id` int(11) NOT NULL,
  `tieu_de` varchar(255) NOT NULL,
  `noi_dung` text DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `trang_thai` varchar(255) DEFAULT NULL,
  `du_an_id` int(11) DEFAULT NULL,
  `nguoi_thuc_hien_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `nhiem_vu`
--

INSERT INTO `nhiem_vu` (`id`, `tieu_de`, `noi_dung`, `deadline`, `trang_thai`, `du_an_id`, `nguoi_thuc_hien_id`) VALUES
(2, 'Làm slide', 'Sử dụng Session để quản lý ádasdasd', '2024-06-22', 'DONE', 1, 2),
(3, 'Làm giao diện Bootstrap', 'Thiết kế Dashboard cho Admin', '2024-06-15', 'DOING', 1, 3),
(4, 'Thiết kế bản nháp giao diện của web2', 'tìm các template mẫu', '2026-05-14', 'DONE', 3, 2),
(6, 'âS', 'ĐÂZ', '2026-05-02', 'TODO', 1, 1),
(7, 'Làm slide', 'sadasfwsdqwedqw', '2026-06-06', 'DONE', 2, 2),
(8, 'Làm slide', 'đâsd', '2026-06-07', 'DONE', 2, NULL),
(9, 'thiết kế api', 'Thiết kế API (API Design) là một bước cực kỳ quan trọng trong quy trình phát triển phần mềm, đóng vai trò như bản vẽ kiến trúc trước khi xây nhà. Một API tốt cần đảm bảo tính **dễ dùng (usability), hiệu năng (performance), bảo mật (security) và khả năng mở rộng (scalability)**.\r\n\r\nDưới đây là bảng phân rã chi tiết công việc \"Thiết kế API\" thành 9 bước cụ thể:\r\n\r\n---\r\n\r\n### BƯỚC 1: Xác định yêu cầu nghiệp vụ (Requirements Gathering)\r\nTrước khi đặt bút thiết kế, bạn cần hiểu rõ API này giải quyết bài toán gì.\r\n*   **Xác định đối tượng sử dụng:** API này dành cho nội bộ (Internal - Frontend/Mobile kết nối với Backend) hay cho bên thứ ba (External - đối tác tích hợp)?\r\n*   **Xác định các Actor (Tác nhân):** Ai hoặc hệ thống nào sẽ gọi API này?\r\n*   **Liệt kê các Use Case (Kịch bản sử dụng):** Ví dụ: \"Người dùng đăng nhập\", \"Thêm sản phẩm vào giỏ hàng\", \"Lấy danh sách lịch sử giao dịch\".\r\n*   **Xác định luồng dữ liệu (Data Flow):** Dữ liệu đi từ đâu đến đâu, cần những thông tin đầu vào (Input) nào và trả về kết quả (Output) gì?\r\n\r\n### BƯỚC 2: Lựa chọn kiến trúc và giao thức (Architecture & Protocol Selection)\r\nChọn \"ngôn ngữ chung\" cho hệ thống của bạn.\r\n*   **Lựa chọn phong cách kiến trúc:**\r\n    *   **RESTful API:** Phổ biến nhất, dễ dùng, dựa trên tài nguyên (Resources).\r\n    *   **GraphQL:** Phù hợp khi client cần truy vấn dữ liệu linh hoạt, tránh thừa/thiếu dữ liệu (over/under-fetching).\r\n    *   **gRPC:** Phù hợp cho giao tiếp hiệu năng cao giữa các Microservices (gọi nội bộ).\r\n    *   **WebSockets / Webhooks:** Cho các tác vụ thời gian thực (real-time).\r\n*   **Định dạng dữ liệu:** JSON (phổ biến nhất), XML hoặc Protocol Buffers (cho gRPC).\r\n\r\n### BƯỚC 3: Thiết kế mô hình dữ liệu và tài nguyên (Resource Modeling)\r\n(Áp dụng chủ yếu cho REST)\r\n*   **Xác định các thực thể (Entities/Resources):** Ví dụ: `User`, `Product`, `Order`, `Category`.\r\n*   **Xác định mối quan hệ giữa các tài nguyên:** Ví dụ: Một `Order` (Đơn hàng) chứa nhiều `Product` (Sản phẩm), thuộc về một `User` (Khách hàng).\r\n\r\n### BƯỚC 4: Thiết kế các Endpoint và Phương thức HTTP (URI & HTTP Methods)\r\nĐây là phần cốt lõi của việc thiết kế REST API.\r\n*   **Đặt tên Endpoint (Danh từ số nhiều, không dùng động từ):**\r\n    *   Đúng: `/api/v1/users`, `/api/v1/products`\r\n    *   Sai: `/api/v1/getUsers`, `/api/v1/deleteProduct`\r\n*   **Áp dụng các phương thức HTTP (HTTP Methods) chuẩn:**\r\n    *   `GET`: Lấy thông tin (Ví dụ: `GET /users` - Lấy danh sách, `GET /users/12 chế` - Lấy chi tiết user 12).\r\n    *   `POST`: Tạo mới tài nguyên (Ví dụ: `POST /users`).\r\n    *   `PUT`: Cập nhật toàn bộ tài nguyên (thay thế hoàn toàn).\r\n    *   `PATCH`: Cập nhật một phần tài nguyên (chỉ sửa 1-2 trường).\r\n    *   `DELETE`: Xóa tài nguyên (Ví dụ: `DELETE /users/12`).\r\n*   **Thiết kế Request Body (Payload):** Cấu trúc JSON gửi lên phải rõ ràng, nhất quán (ví dụ: dùng camelCase hay snake_case).\r\n\r\n### BƯỚC 5: Thiết kế cấu trúc Phản hồi (Response) và Quản lý Lỗi (Error Handling)\r\nĐảm bảo Client luôn hiểu chuyện gì đang xảy ra.\r\n*   **Sử dụng HTTP Status Code chuẩn:**\r\n    *   `2xx` (Thành công): `200 OK`, `201 Created`, `204 No Content`.\r\n    *   `4xx` (Lỗi phía Client): `400 Bad Request` (Dữ liệu gửi lên sai), `401 Unauthorized` (Chưa đăng nhập), `403 Forbidden` (Không có quyền), `404 Not Found` (Không tìm thấy tài nguyên).\r\n    *   `5xx` (Lỗi phía Server): `500 Internal Server Error`.\r\n*   **Thiết kế cấu trúc lỗi chuẩn (Error Response):** Khi có lỗi, phải trả về thông tin chi tiết để Dev Frontend dễ debug.\r\n    ```json\r\n    {\r\n      \"success\": false,\r\n      \"error\": {\r\n        \"code\": \"USER_NOT_FOUND\",\r\n        \"message\": \"Không tìm thấy người dùng với ID đã cung cấp.\",\r\n        \"details\": null\r\n      }\r\n    }\r\n    ```\r\n\r\n### BƯỚC 6: Thiết kế cơ chế Bảo mật (API Security)\r\nBảo vệ API khỏi các cuộc tấn công và truy cập trái phép.\r\n*   **Xác thực (Authentication):** Xác định danh tính người gọi API.\r\n    *   Sử dụng: **JWT (JSON Web Token)**, **OAuth2** (cho bên thứ 3), hoặc **API Key**.\r\n*   **Phân quyền (Authorization):** Xác định người dùng có quyền thực hiện hành động đó không (Role-based access control - RBAC).\r\n*   **Mã hóa dữ liệu:** Bắt buộc sử dụng **HTTPS** để mã hóa dữ liệu trên đường truyền.\r\n*   **Cơ chế bảo mật bổ sung:** Chống tấn công DDoS/Brute-force bằng **Rate Limiting** (giới hạn số lượt gọi API trong 1 phút từ một IP).\r\n\r\n### BƯỚC 7: Thiết kế các tính năng nâng cao (Hiệu năng & Tiện ích)\r\nĐể API hoạt động mượt mà khi dữ liệu lớn.\r\n*   **Phân trang (Pagination):** Với các API lấy danh sách lớn, bắt buộc phải phân trang.\r\n    *   Ví dụ: `GET /products?page=1&limit=20` hoặc sử dụng Cursor-based pagination.\r\n*   **Lọc (Filtering), Sắp xếp (Sorting), Tìm kiếm (Searching):**\r\n    *   Ví dụ: `GET /products?category=electronics&sort=price_desc&search=iphone`\r\n*   **Bộ nhớ đệm (Caching):** Xác định API nào có dữ liệu ít thay đổi để thiết kế cache (sử dụng Header `Cache-Control` hoặc Redis ở backend) nhằm tăng tốc độ phản hồi.\r\n\r\n### BƯỚC 8: Quản lý phiên bản API (API Versioning)\r\nTránh làm lỗi (break) các ứng dụng đang chạy khi bạn cập nhật API mới.\r\n*   Lựa chọn cách đánh version:\r\n    *   **URL-based (Phổ biến nhất):** `/api/v1/products`\r\n    *   **Header-based:** `Accept: application/vnd.company.v1+json`\r\n\r\n### BƯỚC 9: Viết Tài liệu API và Tạo Mock API (Documentation & Mocking)\r\nBước cuối cùng nhưng cực kỳ quan trọng để chuyển giao cho đội phát triển (Frontend/Mobile/Tester).\r\n*   **Viết tài liệu (API Spec):** Sử dụng tiêu chuẩn **OpenAPI / Swagger** để viết đặc tả chi tiết (các endpoint, param, response...).\r\n*   **Tạo Mock API:** Sử dụng công cụ như **Postman**, **Stoplight**, hoặc **Swagger Mock** để giả lập API chạy thử. Giúp Frontend có thể code song song với Backend mà không cần đợi Backend viết code xong.\r\n\r\n---\r\n**Tóm lại:** Quy trình này dịch chuyển từ **Hiểu nghiệp vụ** -> **Định hình khung kỹ thuật** -> **Chi tiết hóa các Endpoint** -> **Bảo mật & Tối ưu** -> **Tài liệu hóa**.', '2026-06-07', 'TODO', 3, NULL),
(11, 'thiết kế api', 'Dưới đây là bảng phân rã chi tiết công việc \"Thiết kế API\" cho dự án cuối kỳ môn Web 2 (\"Phát Triển Ứng Dụng Web\"), đảm bảo đáp ứng chuẩn học thuật, khả năng tích hợp Frontend-Backend và tiêu chí chấm điểm của giảng viên:\r\n\r\n*   **Bước 1: Xác định tài nguyên (Resources) và cấu trúc cơ sở dữ liệu**\r\n    *   Phân tích sơ đồ quan hệ Entity-Relationship (ERD) của bài tập lớn để xác định các tài nguyên chính: Người dùng (`Users`), Sản phẩm (`Products`), Danh mục (`Categories`), Giỏ hàng (`Carts`), Đơn hàng (`Orders`).\r\n    *   Định nghĩa rõ ràng cấu trúc kiểu dữ liệu (Data Schema) cho từng tài nguyên để chuẩn bị cho việc mapping dữ liệu lên API.\r\n\r\n*   **Bước 2: Thiết kế URI chuẩn RESTful API**\r\n    *   Thiết kế hệ thống đường dẫn (Endpoints) nhất quán sử dụng danh từ số nhiều và tiền tố phiên bản (vập nhật theo chuẩn môn Web 2): `/api/v1/...`\r\n    *   Định nghĩa các Endpoint cơ bản phục vụ CRUD:\r\n        *   `GET /api/v1/products` (Lấy danh sách sản phẩm)\r\n        *   `GET /api/v1/products/:id` (Lấy chi tiết sản phẩm)\r\n        *   `POST /api/v1/products` (Thêm sản phẩm mới - quyền Admin)\r\n        *   `PUT /api/v1/products/:id` (Cập nhật sản phẩm - quyền Admin)\r\n        *   `DELETE /api/v1/products/:id` (Xóa sản phẩm - quyền Admin)\r\n    *   Thiết kế Endpoint cho chức năng nghiệp vụ phức tạp:\r\n        *   Đăng ký/Đăng nhập: `POST /api/v1/auth/register`, `POST /api/v1/auth/login`\r\n        *   Giỏ hàng: `POST /api/v1/cart/add`, `DELETE /api/v1/cart/remove`\r\n        *   Thanh toán/Đơn hàng: `POST /api/v1/orders`\r\n\r\n*   **Bước 3: Định nghĩa cấu trúc dữ liệu Request và Response (JSON Payload)**\r\n    *   **Request Body:** Xác định rõ các trường dữ liệu bắt buộc (Required Fields) và định dạng của chúng (String, Number, Array, Boolean) khi Frontend gửi lên.\r\n    *   **Response Body:** Chuẩn hóa cấu trúc dữ liệu trả về thống nhất cho toàn bộ dự án:\r\n        *   *Trường hợp thành công:* `{ \"success\": true, \"data\": { ... }, \"message\": \"...\" }`\r\n        *   *Trường hợp thất bại:* `{ \"success\": false, \"error\": { \"code\": \"...\", \"message\": \"...\" } }`\r\n    *   Thiết kế giải thuật phân trang (Pagination) bắt buộc cho API lấy danh sách: Query params gồm `?page=...&limit=...`, dữ liệu trả về phải có thông tin tổng số trang (`totalPages`) và tổng số bản ghi (`totalItems`).\r\n\r\n*   **Bước 4: Thiết kế cơ chế Xác thực (Authentication) và Phân quyền (Authorization)**\r\n    *   Sử dụng cơ chế Token-based Authentication với **JWT (JSON Web Token)** để phù hợp với kiến trúc Client-Server của môn Web 2.\r\n    *   Xác định cấu trúc JWT Payload (chứa `userId` và `role`).\r\n    *   Thiết kế các Middleware để bảo mật API:\r\n        *   `authMiddleware`: Kiểm tra tính hợp lệ của JWT trong HTTP Header (`Authorization: Bearer <token>`).\r\n        *   `adminMiddleware`: Kiểm tra quyền truy cập (Role Admin) đối với các API quản trị hệ thống.\r\n\r\n*   **Bước 5: Thiết kế bộ mã lỗi (Error Handling) và HTTP Status Codes**\r\n    *   Sử dụng đúng các mã trạng thái HTTP tiêu chuẩn phục vụ cho việc chấm điểm lý thuyết Web 2:\r\n        *   `200 OK` / `201 Created`: Yêu cầu thực hiện thành công.\r\n        *   `400 Bad Request`: Lỗi dữ liệu đầu vào không hợp lệ (Validation error).\r\n        *   `401 Unauthorized`: Chưa đăng nhập hoặc Token không hợp lệ.\r\n        *   `403 Forbidden`: Đăng nhập thành công nhưng không có quyền truy cập.\r\n        *   `404 Not Found`: Không tìm thấy tài nguyên yêu cầu.\r\n        *   `500 Internal Server Error`: Lỗi hệ thống phía Backend.\r\n\r\n*   **Bước 6: Viết tài liệu đặc tả API (API Documentation)**\r\n    *   Xây dựng tài liệu hướng dẫn tích hợp để bàn giao cho thành viên làm Frontend của nhóm.\r\n    *   Sử dụng công cụ **Postman** (tạo Workspace và Collection chung cho nhóm) hoặc viết mã nguồn tự động sinh tài liệu bằng **Swagger UI**.\r\n    *   Mỗi API trong tài liệu phải có đầy đủ: Mô tả chức năng, Endpoint, HTTP Method, Headers, Request Body mẫu, và Response mẫu (Success/Error).', '2026-06-21', 'TODO', 3, NULL),
(13, 'làm video demo dự án', '🎯 Mục tiêu dự án: Tạo video demo trực quan hóa kết quả khảo sát nhu cầu tiêu thụ cá ngừ đại dương tại thị trường Nha Trang.\r\n<br>\r\n🚀 Bước 1: Biên soạn kịch bản chi tiết tập trung vào phương pháp tiếp cận các vựa hải sản Nha Trang và kết quả phân tích số liệu cá ngừ.\r\n<br>\r\n🚀 Bước 2: Chuẩn bị tư liệu hình ảnh thực tế từ cảng cá Hòn Rớ Nha Trang và các biểu đồ trực quan hóa dữ liệu nhu cầu tiêu thụ thủy sản.\r\n<br>\r\n🚀 Bước 3: Thu âm lời thoại thuyết minh rõ ràng và tiến hành quay dựng video demo thời lượng từ 3 đến 5 phút.\r\n<br>\r\n🚀 Bước 4: Lồng ghép hiệu ứng, nhạc nền và chèn phụ đề phân tích các phân khúc khách hàng mua cá ngừ tại Nha Trang.\r\n<br>\r\n📌 Lưu ý: Video cần làm nổi bật được sự khác biệt giữa nhu cầu cá ngừ xuất khẩu và tiêu thụ nội địa tại Nha Trang.\r\n<br>\r\n✅ Nghiệm thu: File video hoàn chỉnh định dạng MP4 chất lượng tối thiểu Full HD 1080p truyền tải đúng tinh thần dự án Nghiên cứu Thị trường Thủy sản.\r\n<br>\r\n✅ Nghiệm thu: Nội dung video được phê duyệt bởi Tech Lead và bộ phận phân tích dữ liệu thị trường thủy sản.', '2026-06-09', 'TODO', 2, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhiem_vu_nguoi_dung`
--

CREATE TABLE `nhiem_vu_nguoi_dung` (
  `nhiem_vu_id` int(11) NOT NULL,
  `nguoi_dung_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `nhiem_vu_nguoi_dung`
--

INSERT INTO `nhiem_vu_nguoi_dung` (`nhiem_vu_id`, `nguoi_dung_id`) VALUES
(2, 2),
(3, 3),
(9, 2),
(11, 2),
(4, 2),
(6, 1),
(7, 2),
(13, 2);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `binh_luan`
--
ALTER TABLE `binh_luan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9t033bi499huul59bresxl05o` (`nguoi_dung_id`),
  ADD KEY `FKam5ao96aigjkfgtk2m25drj0p` (`nhiem_vu_id`);

--
-- Chỉ mục cho bảng `du_an`
--
ALTER TABLE `du_an`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `nguoi_dung`
--
ALTER TABLE `nguoi_dung`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Chỉ mục cho bảng `nhiem_vu`
--
ALTER TABLE `nhiem_vu`
  ADD PRIMARY KEY (`id`),
  ADD KEY `du_an_id` (`du_an_id`),
  ADD KEY `nguoi_thuc_hien_id` (`nguoi_thuc_hien_id`);

--
-- Chỉ mục cho bảng `nhiem_vu_nguoi_dung`
--
ALTER TABLE `nhiem_vu_nguoi_dung`
  ADD KEY `FK53h8w92l4kmyueunc9sbc1ajg` (`nguoi_dung_id`),
  ADD KEY `FKrm33o3f020ksem0pl4am5by1b` (`nhiem_vu_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `binh_luan`
--
ALTER TABLE `binh_luan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `du_an`
--
ALTER TABLE `du_an`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `nguoi_dung`
--
ALTER TABLE `nguoi_dung`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `nhiem_vu`
--
ALTER TABLE `nhiem_vu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `binh_luan`
--
ALTER TABLE `binh_luan`
  ADD CONSTRAINT `FK9t033bi499huul59bresxl05o` FOREIGN KEY (`nguoi_dung_id`) REFERENCES `nguoi_dung` (`id`),
  ADD CONSTRAINT `FKam5ao96aigjkfgtk2m25drj0p` FOREIGN KEY (`nhiem_vu_id`) REFERENCES `nhiem_vu` (`id`);

--
-- Các ràng buộc cho bảng `nhiem_vu`
--
ALTER TABLE `nhiem_vu`
  ADD CONSTRAINT `nhiem_vu_ibfk_1` FOREIGN KEY (`du_an_id`) REFERENCES `du_an` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `nhiem_vu_ibfk_2` FOREIGN KEY (`nguoi_thuc_hien_id`) REFERENCES `nguoi_dung` (`id`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `nhiem_vu_nguoi_dung`
--
ALTER TABLE `nhiem_vu_nguoi_dung`
  ADD CONSTRAINT `FK53h8w92l4kmyueunc9sbc1ajg` FOREIGN KEY (`nguoi_dung_id`) REFERENCES `nguoi_dung` (`id`),
  ADD CONSTRAINT `FKrm33o3f020ksem0pl4am5by1b` FOREIGN KEY (`nhiem_vu_id`) REFERENCES `nhiem_vu` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
