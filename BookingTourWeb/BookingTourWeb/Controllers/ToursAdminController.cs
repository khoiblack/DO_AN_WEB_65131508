using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using BookingTourWeb.Data;
using BookingTourWeb.Models;

namespace BookingTourWeb.Controllers
{
    public class ToursAdminController : Controller
    {
        private readonly ApplicationDbContext _context;
        private readonly IWebHostEnvironment _env; // Thêm biến này

        // Cập nhật hàm này
        public ToursAdminController(ApplicationDbContext context, IWebHostEnvironment env)
        {
            _context = context;
            _env = env;
        }

        // GET: ToursAdmin
        public async Task<IActionResult> Index()
        {
            return View(await _context.Tours.ToListAsync());
        }

        // GET: ToursAdmin/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var tour = await _context.Tours
                .FirstOrDefaultAsync(m => m.Id == id);
            if (tour == null)
            {
                return NotFound();
            }

            return View(tour);
        }

        // GET: ToursAdmin/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: ToursAdmin/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create(Tour tour)
        {
            if (ModelState.IsValid)
            {
                // Kiểm tra xem người dùng có chọn ảnh không
                if (tour.ImageUpload != null)
                {
                    // 1. Đổi tên ảnh thành một chuỗi mã hóa ngẫu nhiên (tránh việc 2 ảnh trùng tên bị ghi đè lên nhau)
                    string fileName = Guid.NewGuid().ToString() + Path.GetExtension(tour.ImageUpload.FileName);

                    // 2. Xác định đường dẫn chính xác tới thư mục wwwroot/images
                    string uploadPath = Path.Combine(_env.WebRootPath, "images");

                    // 3. Đường dẫn lưu file cuối cùng
                    string filePath = Path.Combine(uploadPath, fileName);

                    // 4. Copy file vào thư mục wwwroot/images
                    using (var fileStream = new FileStream(filePath, FileMode.Create))
                    {
                        await tour.ImageUpload.CopyToAsync(fileStream);
                    }

                    // 5. Lưu đường dẫn này vào cột Image để Database lưu lại
                    tour.Image = "/images/" + fileName;
                }
                else
                {
                    // Nếu khách không chọn ảnh, gán một ảnh mặc định
                    tour.Image = "/images/default-tour.jpg";
                }

                // Lưu vào Database
                _context.Add(tour);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(tour);
        }

        // GET: ToursAdmin/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var tour = await _context.Tours.FindAsync(id);
            if (tour == null)
            {
                return NotFound();
            }
            return View(tour);
        }

        // POST: ToursAdmin/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,Name,Location,Price,Image,Duration,GroupSize,Rating,Reviews")] Tour tour)
        {
            if (id != tour.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(tour);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!TourExists(tour.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(tour);
        }

        // GET: ToursAdmin/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var tour = await _context.Tours
                .FirstOrDefaultAsync(m => m.Id == id);
            if (tour == null)
            {
                return NotFound();
            }

            return View(tour);
        }

        // POST: ToursAdmin/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var tour = await _context.Tours.FindAsync(id);
            if (tour != null)
            {
                _context.Tours.Remove(tour);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool TourExists(int id)
        {
            return _context.Tours.Any(e => e.Id == id);
        }
    }
}
