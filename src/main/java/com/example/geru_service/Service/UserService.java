package com.example.geru_service.Service;

import com.example.geru_service.DAO.UserDAO;
import com.example.geru_service.Entity.User;
import com.example.geru_service.Model.Message;
import com.example.geru_service.Model.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class UserService {

    @Autowired
    UserDAO userDAO;
    @Autowired
    MailService mailService;
    public ResponseEntity<User> getByUserId(Long userId) {
        return ResponseEntity.ok(userDAO.findUserId(userId));
    }
    public User getUserByUserId(Long userId) {
        return userDAO.findUserId(userId);
    }

    public Message createUser(UserModel userModel) {
        Message msg = new Message();
        User user = userDAO.findByRegisterNumber(userModel.getPin());

        if(user != null){
            msg.setData(user);
            msg.setStatus(400L);
            msg.setMessage("Та бүртгэлтэй байна хэрэглэгчийн эрхээрээ нэвтэрнэ үү.");
            return msg;
        }

        //update hiih bolomjtoi bolgosn
        user = new User();
        user.setPhoneNumber(userModel.getPhoneNumber());
        user.setMailAddress(userModel.getEmail());
        user.setRegisterNumber(userModel.getPin());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPassword(userModel.getPassword());
        user.setBankId(userModel.getBankId());
        user.setBankAccountNumber(String.valueOf(userModel.getBankAccountNumber()));
        user = userDAO.save(user);

        String subject = "Geru System-д шинэ хэрэглэгч бүртгүүллээ.";
        String body = "Өдрийн мэнд андаа, " + user.getPhoneNumber() + " дугаартай хэрэглэгч шинэээр бүртгүүллээ.";
        mailService.sendEmail("bayrtsogt348@gmail.com", subject, body);

        String subject1 = "Гэрү хоолны газрын системд тавтай морил";
        String body1 = "<html><head>\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Exo+2:wght@300;400;600&amp;display=swap\" rel=\"stylesheet\">\n" +
                "    <style>\n" +
                "        body { font-family: 'Exo 2', sans-serif; line-height: 1.6; color: #333; }\n" +
                "        .email-container { margin: 20px auto 0px auto; padding: 20px; border: 1px solid #e0b263; border-radius: 8px 8px 0px 0px; max-width: 600px; background-color: #f9f9f9; }\n" +
                "        .header { font-size: 24px; font-weight: bold; color: #622406; }\n" +
                "        .content { margin: 20px; }\n" +
                "        .footer { margin-top: 30px; font-size: 12px; color: #666; }\n" +
                "        .password { font-weight: bold; color: #DA0615; }\n" +
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
                "    <div class=\"header\">Бүртгэл амжилттай</div>\n" +
                "    <div class=\"content\">\n" +
                "        <p>Сайн байна уу, <strong>" + user.getFirstName() + " " + user.getLastName() + "</strong>? Танд энэ өдрийн мэнд хүргэе.</p>\n" +
                "        <p>Биднийг сонгож үйлчлүүлж байгаад баярлалаа. Санал хүсэлт байвал хариу бичиж болно шүү. Зиа тэгээд өнөөдрийн ажилд нь амжилт хүсье</p>\n" +
                "    </div>\n" +
                "    <div class=\"footer\">\n" +
                "        <p>Энэ мейл хаяг автомат мейл хаяг боловч Та нэмэлт мэдээлэл авахыг хүсвэл бидэнд мейл илгээж болно шүү.</p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "<div class=\"foot\">GERU Restaurant system</div>\n" +
                "\n" +
                "</body></html>\n" +
                "\n" +
                "\n" +
                "\n";
        mailService.sendEmail(user.getMailAddress(), subject1, body1);

        msg.setData(user);
        msg.setMessage("Бүртгэл амжилттай");
        msg.setStatus(200L);
        return msg;
    }

    public Message login(String username, String password) {
        Message message = new Message();
        User user;
        if(username.contains("@")){
            user = userDAO.findAllByMailAddress(username);
        }
        else {
            user = userDAO.findAllByPhoneNumber(Long.valueOf(username));
        }
        if(user == null || user.getId() == null){
            message.setStatus(404L);
            message.setMessage("Хэрэглэгч олдонгүй. Та эхлээд бүртгүүлнэ үү.");
            return message;
        }
        if(Objects.equals(user.getPassword(), password)){
            message.setStatus(200L);
            message.setMessage("Амжилттай нэвтэрлээ");
            message.setData(user);
        }
        else {
            message.setStatus(500L);
            message.setMessage("Нэвтрэх нэр эсвэл нууц үг буруу байна.");
        }
        return message;

    }

}
