# 🚀 Hệ Thống Quản Lý Công Việc (Task Manager) Tích Hợp AI

![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)

> **Đồ án môn học:** Phát Triển Ứng Dụng Web (Web 2)
> 
> **Trường:** Đại học Nha Trang (NTU) - Khoa Công Nghệ Thông Tin

## 📖 Giới thiệu dự án
**Task Manager** là một nền tảng quản lý công việc và dự án trực tuyến được xây dựng dựa trên kiến trúc MVC với Java Spring Boot. Điểm nhấn đặc biệt của hệ thống là việc tích hợp **Trí tuệ nhân tạo (Google Gemini API)** giúp tự động hóa quá trình phân rã và viết mô tả chi tiết cho từng nhiệm vụ, giúp Leader tiết kiệm tối đa thời gian lập kế hoạch.

## ✨ Các tính năng nổi bật
Dự án được phân quyền chặt chẽ với 2 vai trò chính: **LEADER** (Quản lý) và **MEMBER** (Nhân viên).

### 👑 Dành cho Quản lý (Leader)
* **Quản lý Dự án & Nhân sự:** Thêm, sửa, xóa, đóng/mở dự án. Phân quyền và cấp tài khoản cho nhân viên mới.
* **✨ Tự động tạo mô tả bằng AI:** Tích hợp API AI để tự động sinh ra kịch bản và các bước thực hiện công việc chỉ từ một tiêu đề ngắn.
* **Smart Confirm:** Cảnh báo thông minh khi cố tình xóa một dự án đang có công việc dang dở.
* **Thống kê trực quan:** Biểu đồ (Doughnut Chart) thống kê tình trạng công việc (TODO, DOING, DONE) bằng Chart.js.

### 👤 Dành cho Nhân viên (Member)
* **Không gian làm việc cá nhân:** Xem danh sách các công việc được giao trong ngày/tháng.
* **Cập nhật tiến độ 1-chạm:** Đổi trạng thái công việc (TODO -> DOING -> DONE) siêu tốc qua công nghệ AJAX không cần tải lại trang.
* **Tương tác nhóm:** Tính năng bình luận (Comment) theo mốc thời gian thực trực tiếp bên trong từng nhiệm vụ.

## 🛠️ Công nghệ sử dụng
| Phân lớp | Công nghệ |
| :--- | :--- |
| **Backend** | Java Spring Boot, Hibernate (Spring Data JPA) |
| **Frontend** | HTML5, CSS3, JavaScript, Bootstrap 5, Chart.js |
| **Template Engine** | Thymeleaf |
| **Database** | MySQL (XAMPP) |
| **Bảo mật** | Mã hóa mật khẩu BCrypt, Session Management |
| **Third-party API**| Google Gemini API (Tạo văn bản AI) |

## ⚙️ Hướng dẫn cài đặt & Chạy dự án (Local)

**Bước 1: Clone dự án về máy**
```bash
git clone [https://github.com/khoiblack/DO_AN_WEB_65131508.git]
```
**Bước 2: Cài đặt Cơ sở dữ liệu (Database)**

Mở XAMPP, khởi động module Apache và MySQL.

Truy cập http://localhost/phpmyadmin/.

Tạo cơ sở dữ liệu mới với tên task_manager_db.

Import file task_manager_db (1).sql (nằm trong thư mục gốc của project) vào cơ sở dữ liệu vừa tạo.

**Bước 3: Cấu hình kết nối (application.properties)**
Mở file src/main/resources/application.properties và đảm bảo cấu hình khớp với MySQL của bạn:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/task_manager_db
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```
📸 Giao diện hệ thống (Screenshots)
