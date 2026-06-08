package ntu.khoi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Service
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String phanRaCongViec(String tieuDe, String tenDuAn, String moTaDuAn) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=" + apiKey;        
        
        
     
        String prompt = "Bạn là một Tech Lead đang quản lý dự án mang tên '" + tenDuAn + "'. " +
                        "Mô tả tổng quan: '" + moTaDuAn + "'. " +
                        "Nhiệm vụ cần phân rã: '" + tieuDe + "'. " +
                        "YÊU CẦU TRÌNH BÀY GIAO DIỆN (RẤT QUAN TRỌNG): " +
                        "1. TUYỆT ĐỐI KHÔNG sử dụng ký tự định dạng Markdown (không dùng dấu ** hay *). " +
                        "2. CHỈ sử dụng Emoji làm icon ở đầu mỗi dòng (ví dụ: 🎯 cho mục tiêu, 🚀 cho bước thực hiện, 📌 cho lưu ý, ✅ cho nghiệm thu). " +
                        "3. Phải XUỐNG DÒNG (thêm \n) rõ ràng giữa các ý để dễ đọc. " +
                        "4. Nội dung bắt buộc bám sát 100% ngữ cảnh của dự án '" + tenDuAn + "'. " +
                        "Không chào hỏi, không giải thích, chỉ in ra bản mô tả công việc.";

        
        Map<String, Object> requestBody = Map.of(
            "contents", new Object[]{
                Map.of("parts", new Object[]{
                    Map.of("text", prompt)
                })
            }
        );

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            
            Map<String, Object> body = response.getBody();
            java.util.List<Map<String, Object>> candidates = (java.util.List<Map<String, Object>>) body.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            java.util.List<Map<String, Object>> parts = (java.util.List<Map<String, Object>>) content.get("parts");
            
            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            e.printStackTrace(); 
            return "Lỗi kết nối AI: " + e.getMessage();
        }
    }
}