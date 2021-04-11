package com.account.serviceImpl;


import com.account.Mapper.MapperUtility;
import com.account.dto.UserDto;
import com.account.entity.Company;
import com.account.entity.ConfirmationToken;
import com.account.entity.User;
import com.account.exceptionHandler.InvalidTokenException;
import com.account.repository.ConfirmationTokenRepository;
import com.account.service.ConfirmationTokenService;
import com.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenImpl implements ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private MapperUtility mapperUtil;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserService userService;

    @Value("${spring.mail.username}")
    private String MAILTO;
    @Value("${app.local-url}")
    private String URL;




    @Override
    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.save(confirmationToken);
        return token;
    }

    @Override
    public ConfirmationToken readByToken(String token) throws InvalidTokenException {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(()-> new InvalidTokenException("Token not found"));
        if(confirmationToken.isTokenValid(confirmationToken.getExpiredDate()))
            throw new InvalidTokenException("Token expired");
        return confirmationToken;
    }


    @Override
    public void delete(ConfirmationToken confirmationToken) throws InvalidTokenException {
        ConfirmationToken token = confirmationTokenRepository.findByToken(confirmationToken.getToken()).orElseThrow(()->new InvalidTokenException("Token not found"));
        token.setIsDeleted(true);
        confirmationTokenRepository.save(token);
    }

    @Override
    public void sendEmail(User user) {

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        String emailTo = user.getEmail();
        String emailFrom = MAILTO;
        String url = URL;
        String token = confirmationToken.getToken();
        String subject = "User Confirm Registration";
        String message = "please click following url: "+url+"/user/confirmation?token="+token;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailTo);
        mailMessage.setFrom(emailFrom);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);

    }

    @Override
    public void sendEmail(Company company) {
        ConfirmationToken confirmationToken = new ConfirmationToken(company);

        confirmationTokenRepository.save(confirmationToken);

        String emailTo = company.getEmail();
        String emailFrom = MAILTO;
        String url = URL;
        String token = confirmationToken.getToken();
        String subject = "Company Confirm Registration";
        String message = "please click following url: "+url+"/company/confirmation?token="+token;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailTo);
        mailMessage.setFrom(emailFrom);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);
    }

    // transfer MailTo object to SimpleMailMessage object
    //http://localhost:9090/api/v1/user/confirmation?716433b1-3291-49d5-9e91-a39688e7177a
    //http://localhost:9090/api/v1/user/create-user
    //http://localhost:9090/api/v1/user/confirmation?1ce27147-c809-4818-a190-7a7f4d97f53b
    //http://localhost:9090/api/v1/user/confirmation?8c68966e-1670-4c1b-8ffd-bded248f4aea


}
