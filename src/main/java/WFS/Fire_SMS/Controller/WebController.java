package WFS.Fire_SMS.Controller;

import WFS.Fire_SMS.Model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

@Controller
public class WebController {

    public static final String ACCOUNT_SID = "ACc5f4102180097a897db68e094d1f1094";
    public static final String AUTH_TOKEN = "4a3f7c84bc46474f409f3bfda2f195ae";

    @Autowired
    private ClientRepository infoCRepository;

    @GetMapping("login")
    public String Get_login(Model model) {
        model.addAttribute("message",0);
        return "pages/samples/login";
    }

    @GetMapping("register")
    public String Get_register(Model model){
        model.addAttribute("message",0);
        return "pages/samples/register";
    }

    @PostMapping("loginAction")
    public String Post_login(HttpServletRequest request, Model model){
        String lpN=request.getParameter("LP");
        String pN=request.getParameter("PN");
        List<client> tmp = infoCRepository.findByLpnumberAndPnumber(lpN, pN);
        if(tmp.size() < 1){
            model.addAttribute("message",1);
            return "pages/samples/login";
        }
        else if(tmp.size() > 1){
            return "pages/samples/error-500";
        }
        else{
            model.addAttribute("User",lpN);
            return "main";
        }
    }

    @PostMapping("registerAction")
    public String Post_register(HttpServletRequest request, Model model){
        String lpN=request.getParameter("LP");
        String pN=request.getParameter("PN");
        String pNA=request.getParameter("PNA");
        List<client> tmp = infoCRepository.findByLpnumberAndPnumber(lpN, pN);
        List<client> tmp0 = infoCRepository.findAll();
        if(pN.equals(pNA)){
            if(tmp.size() < 1){
                String kornum = "+82";
                long id = tmp0.size();
                client buffer = new client(id,lpN, pN, 0, "0000",0);
                infoCRepository.save(buffer);
                model.addAttribute("message",0);
                model.addAttribute("data",pN);
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                Message message = Message.creator(
                                new com.twilio.type.PhoneNumber(kornum.concat(pN)),
                                new com.twilio.type.PhoneNumber("+12076906841"),
                                "WFS에서 보낸 인증번호[0000]입니다.")
                        .create();
                System.out.println(message.getSid());
                return "verification";
            }
            else{
                model.addAttribute("message",2);
                return "pages/samples/register";
            }
        }
        else{
            model.addAttribute("message", 1);
            return "pages/samples/register";
        }
    }

    @PostMapping("verificationAction")
    public String Post_verification(HttpServletRequest request, Model model){
        String verNum=request.getParameter("VN");
        String pN=request.getParameter("PNforV");
        List<client> tmp = infoCRepository.findByPnumber(pN);
        if(tmp.size() > 1){
            return "pages/samples/error-500";
        }
        else if(tmp.size() < 1){
            model.addAttribute("message",2);
            return "pages/samples/login";
        }
        else{
            if(verNum.equals(tmp.get(0).getVnumber())){
                return "confirm";
            }
            model.addAttribute("message",1);
            return "verification";
        }
    }

}
