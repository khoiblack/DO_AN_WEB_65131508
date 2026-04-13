using Microsoft.EntityFrameworkCore;
using BookingTourWeb.Models; // Đổi tên project của bạn nếu cần

namespace BookingTourWeb.Data
{
    // Kế thừa từ DbContext của Entity Framework
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options)
        {
        }

        // Khai báo 2 bảng sẽ được tạo trong SQL Server
        public DbSet<Tour> Tours { get; set; }
        public DbSet<Booking> Bookings { get; set; }
    }
}