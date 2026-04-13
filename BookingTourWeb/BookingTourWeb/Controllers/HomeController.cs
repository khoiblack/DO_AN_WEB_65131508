using BookingTourWeb.Data;
using Microsoft.AspNetCore.Mvc;
using System.Linq;

namespace BookingTourWeb.Controllers
{
    public class HomeController : Controller
    {
        private readonly ApplicationDbContext _context;

        // Ðây là cách Controller "mý?n" Database t? h? th?ng
        public HomeController(ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult Index()
        {
            // L?nh này týõng ðýõng v?i: "L?y 4 Tour m?i nh?t t? b?ng Tours trong SQL Server"
            var tours = _context.Tours.Take(4).ToList();

            // G?i danh sách tour này sang trang Index.cshtml
            return View(tours);
        }
        public IActionResult About()
        {
            return View();
        }
        public IActionResult Contact()
        {
            return View();
        }
    }
}