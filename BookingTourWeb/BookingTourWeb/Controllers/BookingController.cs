using BookingTourWeb.Data;
using BookingTourWeb.Models;
using Microsoft.AspNetCore.Mvc;

namespace BookingTourWeb.Controllers
{
    public class BookingController : Controller
    {
        private readonly ApplicationDbContext _context;

        public BookingController(ApplicationDbContext context) { _context = context; }

        [HttpPost]
        public IActionResult Create(Booking booking)
        {
            if (ModelState.IsValid)
            {
                // Tính sơ bộ tổng tiền (Giá tour * số khách)
                var tour = _context.Tours.Find(booking.TourId);
                if (tour != null) booking.TotalPrice = tour.Price * booking.NumberOfGuests;

                _context.Bookings.Add(booking);
                _context.SaveChanges();

                // Trả về trang thông báo thành công
                return Content("Chúc mừng! Bạn đã đặt tour thành công. Chúng tôi sẽ gọi lại sớm nhất.");
            }
            return RedirectToAction("Detail", "Tour", new { id = booking.TourId });
        }
    }
}