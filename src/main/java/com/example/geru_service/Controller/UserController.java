package com.example.geru_service.Controller;

import com.example.geru_service.DAO.UserDAO;
import com.example.geru_service.Entity.User;
import com.example.geru_service.Model.Message;
import com.example.geru_service.Model.UserModel;
import com.example.geru_service.Service.MailService;
import com.example.geru_service.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MailService mailService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getByUserId")
    public ResponseEntity getByUserId(@RequestParam("userId") String encryptedUserId) {
        try {
            return ResponseEntity.ok(userService.getByUserId(Long.valueOf(encryptedUserId)).getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/login")
    public ResponseEntity login(@RequestParam("username") String phoneNumber, @RequestParam("password") String password) {
        return ResponseEntity.ok(userService.login(phoneNumber, password));
    }

    @PostMapping("/createUser")
    public ResponseEntity createTenant(@RequestBody String encryptedUserModel) {
        try {
            UserModel userModel = objectMapper.readValue(encryptedUserModel, UserModel.class);
            Message message = userService.createUser(userModel);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getPassword")
    public void sendMail(
            @RequestParam("mail") String mailAddress){
        User user = userDAO.findAllByMailAddress(mailAddress);
        String pass = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String subject = "Нууц үг сэргээх";

        String body = "<html><head>\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Exo+2:wght@300;400;600&amp;display=swap\" rel=\"stylesheet\">\n" +
                "    <style>\n" +
                "        body { font-family: 'Exo 2', sans-serif; line-height: 1.6; color: #333; }\n" +
                "        .email-container { margin: 20px auto 0px auto; padding: 20px; border: 1px solid #e0b263; border-radius: 8px 8px 0px 0px; max-width: 600px; background-color: #f9f9f9; }\n" +
                "        .header { font-size: 24px; font-weight: bold; color: #e0b263; }\n" +
                "        .content { margin: 20px; }\n" +
                "        .footer { margin-top: 30px; font-size: 12px; color: #666; }\n" +
                "        .password { font-weight: bold; color: #e0b263; }\n" +
                "        .foot{\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            padding: 0px 20px;\n" +
                "            font-size: 20px;\n" +
                "            font-weight: bold;\n" +
                "            background: #1B1C30;\n" +
                "            color: white;\n" +
                "            margin: 0px auto;\n" +
                "            border: 1px solid #1B1C30;\n" +
                "            border-radius: 0px 0px 8px 8px;\n" +
                "            max-width: 600px\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"email-container\" style=\"\n" +
                "    /* margin-bottom: 0px; */\n" +
                "\">\n" +
                "    <div class=\"header\">Нууц үг сэргээх</div>\n" +
                "    <div class=\"content\">\n" +
                "        <p>Сайн байна уу, <strong>" + firstName + " " + lastName + "</strong>? Танд энэ өдрийн мэнд хүргэе.</p>\n" +
                "        <p>Таны нууц үг: <span class=\"password\">" + pass + "</span></p>\n" +
                "        <p>Та өдрийг сайхан өнгөрүүлээрэй. Таны итгэлт хамтрагч <strong>Гэрү</strong>-ээс нь &lt;3</p>\n" +
                "    </div>\n" +
                "    <div class=\"footer\">\n" +
                "        <p>Энэ мейл хаяг автомат мейл хаяг боловч Та нэмэлт мэдээлэл авахыг хүсвэл бидэнд мейл илгээж болно шүү.</p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "<div class=\"foot\">Geru Restaurant system</div>\n" +
                "\n" +
                "</body></html>";

        mailService.sendEmail(mailAddress, subject, body);
    }
}
