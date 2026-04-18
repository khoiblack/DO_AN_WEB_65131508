using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;

namespace BookingTourWeb.Controllers
{
    public class AccountController : Controller
    {
        // 1. Hiển thị form Đăng nhập
        [HttpGet]
        public IActionResult Login()
        {
            // Nếu đã đăng nhập rồi thì cho vô thẳng Admin luôn, khỏi bắt đăng nhập lại
            if (User.Identity.IsAuthenticated)
            {
                return RedirectToAction("Index", "Admin");
            }
            return View();
        }

        // 2. Xử lý khi người dùng bấm nút Đăng nhập
        [HttpPost]
        public async Task<IActionResult> Login(string username, string password)
        {
            // TẠM THỜI: Fix cứng tài khoản để test thử. 
            // Về sau bạn có thể đổi thành code tìm trong Database (_context.Users.FirstOrDefault...)
            if (username == "admin" && password == "123456")
            {
                // Tạo thông tin thẻ từ (Claims)
                var claims = new List<Claim>
                {
                    new Claim(ClaimTypes.Name, username),
                    new Claim(ClaimTypes.Role, "Administrator") // Cấp quyền Admin
                };

                var claimsIdentity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);

                // Dán thẻ từ (Cookie) vào trình duyệt của khách
                await HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, new ClaimsPrincipal(claimsIdentity));

                return RedirectToAction("Index", "Admin");
            }

            // Nếu sai tài khoản/mật khẩu
            ViewBag.ErrorMessage = "Tên đăng nhập hoặc mật khẩu không đúng!";
            return View();
        }

        // 3. Xử lý Đăng xuất
        public async Task<IActionResult> Logout()
        {
            await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
            return RedirectToAction("Index", "Home"); // Đăng xuất xong đuổi ra trang chủ
        }
    }
}