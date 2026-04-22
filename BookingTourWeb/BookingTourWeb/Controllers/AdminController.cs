using BookingTourWeb.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using System.Linq; // Bổ sung thư viện LINQ để dùng hàm Where

namespace BookingTourWeb.Controllers
{
    [Authorize]
    public class AdminController : Controller
    {
        private readonly ApplicationDbContext _context;

        public AdminController(ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult Index()
        {
            // 1. Tổng số đơn hàng
            ViewBag.TotalBookings = _context.Bookings.Count();

            // 2. TỔNG DOANH THU THỰC TẾ (Chỉ tính những đơn đã thu tiền)
            var paidBookings = _context.Bookings.Where(b => b.Status == "Đã thanh toán" || b.Status == "Hoàn tất");
            ViewBag.TotalRevenue = paidBookings.Any() ? paidBookings.Sum(b => b.TotalPrice) : 0;

            // BỔ SUNG: Doanh thu chờ (Những đơn khách mới đặt, chưa chuyển khoản)
            var pendingBookings = _context.Bookings.Where(b => b.Status == "Chờ xác nhận");
            ViewBag.PendingRevenue = pendingBookings.Any() ? pendingBookings.Sum(b => b.TotalPrice) : 0;

            // 3. Số khách hàng (Tạm tính theo tên khách hàng duy nhất)
            ViewBag.TotalCustomers = _context.Bookings.Select(b => b.CustomerName).Distinct().Count();

            // 4. Tổng số Tour đang có
            ViewBag.TotalTours = _context.Tours.Count();

            return View();
        }
    }
}