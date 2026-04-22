using Microsoft.AspNetCore.Mvc;
using BookingTourWeb.Data;
using BookingTourWeb.Models;
using System;
using System.Threading.Tasks;

namespace BookingTourWeb.Controllers
{
    public class BookingController : Controller
    {
        private readonly ApplicationDbContext _context;

        public BookingController(ApplicationDbContext context)
        {
            _context = context;
        }

        // Hàm hứng dữ liệu từ Form của khách hàng gửi lên
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create(int TourId, DateTime DepartureDate, string CustomerName, string PhoneNumber, int NumberOfGuests)
        {
            // 1. Tìm cái Tour mà khách vừa đặt để lấy giá tiền
            var tour = await _context.Tours.FindAsync(TourId);
            if (tour == null)
            {
                return NotFound("Không tìm thấy Tour này!");
            }

            // 2. Tạo một tờ Đơn hàng mới (Booking)
            var booking = new Booking
            {
                TourId = TourId,
                CustomerName = CustomerName,
                PhoneNumber = PhoneNumber,
                DepartureDate = DepartureDate,
                NumberOfGuests = NumberOfGuests,
                // Tính tổng tiền: Giá tour * Số lượng khách
                TotalPrice = tour.Price * NumberOfGuests,
                BookingDate = DateTime.Now,
                Status = "Chờ xác nhận" // Trạng thái mặc định khi vừa đặt xong
            };

            // 3. Lưu vào Database (Sổ thu ngân)
            _context.Bookings.Add(booking);
            await _context.SaveChangesAsync();

            // 4. Lưu xong thì chuyển khách sang trang Báo thành công xịn sò!
            return RedirectToAction("Success");
        }

        // Trang hiển thị giao diện báo đặt tour thành công
        public IActionResult Success()
        {
            return View();
        }
    }
}