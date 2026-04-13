using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BookingTourWeb.Models // Lưu ý: Đổi BookingTourWeb thành tên Project của bạn nếu khác
{
    public class Tour
    {
        [Key] // Đánh dấu đây là Khóa chính (Primary Key), tự động tăng
        public int Id { get; set; }

        [Required(ErrorMessage = "Vui lòng nhập tên tour")]
        [StringLength(200)]
        public string Name { get; set; }

        [Required(ErrorMessage = "Vui lòng nhập địa điểm")]
        public string Location { get; set; }

        [Required]
        public decimal Price { get; set; }

        public string? Image { get; set; } // Dấu ? nghĩa là có thể để trống (null)

        public string? Duration { get; set; } // Thời lượng (vd: 4 giờ)

        public string? GroupSize { get; set; } // Số lượng khách (vd: Tối đa 12 người)

        public double Rating { get; set; } = 5.0; // Mặc định 5 sao

        public int Reviews { get; set; } = 0; // Số lượt đánh giá

        // Mối quan hệ (1-Nhiều): 1 Tour có thể có nhiều người đặt (Booking)
        public ICollection<Booking>? Bookings { get; set; }
        [NotMapped] // Cờ này báo cho SQL Server biết: "Đừng tạo cột này trong Database nhé, đây chỉ là biến tạm thời thôi!"
        public IFormFile? ImageUpload { get; set; }
    }
}
