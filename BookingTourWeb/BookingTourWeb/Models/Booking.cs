using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BookingTourWeb.Models
{
    public class Booking
    {
        [Key]
        public int Id { get; set; }

        [Required(ErrorMessage = "Vui lòng nhập tên khách hàng")]
        public string CustomerName { get; set; } = "";

        [Required(ErrorMessage = "Vui lòng nhập số điện thoại")]
        public string PhoneNumber { get; set; }

        [Required]
        public DateTime DepartureDate { get; set; } // Ngày đi

        [Required]
        public int NumberOfGuests { get; set; } // Số lượng khách
        [Column(TypeName = "decimal(18,2)")]
        public decimal TotalPrice { get; set; } // Tổng tiền
                                                
        public DateTime BookingDate { get; set; } = DateTime.Now;

        public string Status { get; set; } = "Chờ duyệt"; // Trạng thái đơn (Chờ duyệt, Đã thanh toán, Đã hủy)

        // Khóa ngoại (Foreign Key) liên kết với bảng Tour
        public int TourId { get; set; }
        [ForeignKey("TourId")]
        public Tour? Tour { get; set; }
    }
}