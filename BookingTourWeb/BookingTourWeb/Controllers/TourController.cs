using BookingTourWeb.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace BookingTourWeb.Controllers
{
    public class TourController : Controller
    {
        private readonly ApplicationDbContext _context;

        public TourController(ApplicationDbContext context)
        {
            _context = context;
        }

        // Trang danh sách tất cả tour
        public IActionResult Index()
        {
            var allTours = _context.Tours.ToList();
            return View(allTours);
        }

        // Trang chi tiết tour dựa vào Id
        public IActionResult Detail(int id)
        {
            // Tìm tour trong database theo Id
            var tour = _context.Tours.FirstOrDefault(t => t.Id == id);

            if (tour == null) return NotFound(); // Nếu không thấy tour thì báo lỗi 404

            return View(tour);
        }
    }
}