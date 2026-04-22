using System.ComponentModel.DataAnnotations;

namespace BookingTourWeb.Models
{
    public class Account
    {
        [Key]
        public int Id { get; set; }

        [Required(ErrorMessage = "Vui lòng nhập tên đăng nhập")]
        [StringLength(50)]
        public string Username { get; set; }

        [Required(ErrorMessage = "Vui lòng nhập mật khẩu")]
        [StringLength(255)]
        public string Password { get; set; }

        [Required(ErrorMessage = "Vui lòng nhập họ tên")]
        [StringLength(100)]
        public string FullName { get; set; }

        [Required]
        public string Role { get; set; } // Phân quyền: Sẽ chứa chữ "Admin" hoặc "Staff"

        public bool IsActive { get; set; } = true; // Trạng thái: Đang hoạt động hay Bị khóa
    }
}