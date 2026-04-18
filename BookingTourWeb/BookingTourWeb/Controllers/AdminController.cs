using BookingTourWeb.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;

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

            // 2. Tổng doanh thu (Cộng dồn cột TotalPrice)
            // Nếu chưa có đơn hàng nào thì trả về 0 để tránh lỗi
            ViewBag.TotalRevenue = _context.Bookings.Any() ? _context.Bookings.Sum(b => b.TotalPrice) : 0;

            // 3. Số khách hàng (Tạm tính theo số đơn hàng)
            ViewBag.TotalCustomers = _context.Bookings.Select(b => b.CustomerName).Distinct().Count();

            // 4. Tổng số Tour đang có
            ViewBag.TotalTours = _context.Tours.Count();

            return View();
        }
    }
}