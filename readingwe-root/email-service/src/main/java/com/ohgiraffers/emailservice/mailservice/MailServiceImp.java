package com.ohgiraffers.emailservice.mailservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailServiceImp implements MailService {

    private final JavaMailSender mailSender;

    @Value("${mail.from}")
    private String from;

    private void sendPlainTextMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public void sendSignupVerificationMail(String to, String username, String verificationCode) {
        String subject = "[서비스명] 회원가입 인증 메일";
        String text = """
                %s님, 안녕하세요.

                아래 인증 코드를 입력해서 회원가입을 완료해주세요.

                인증 코드: %s
                """.formatted(username, verificationCode);
        sendPlainTextMail(to, subject, text);
    }

    @Override
    public void sendFindIdMail(String to, List<String> usernames) {
        String subject = "[서비스명] 아이디 찾기 안내";
        String joined = String.join(", ", usernames);
        String text = """
                요청하신 계정의 아이디는 다음과 같습니다.

                %s
                """.formatted(joined);
        sendPlainTextMail(to, subject, text);
    }

    @Override
    public void sendResetPasswordMail(String to, String resetToken) {
        String subject = "[서비스명] 비밀번호 재설정 안내";
        String text = """
                아래 링크를 통해 비밀번호를 재설정해주세요.

                https://example.com/reset-password?token=%s
                """.formatted(resetToken);
        sendPlainTextMail(to, subject, text);
    }

    @Override
    public void sendClubJoinRequestMail(String hostEmail, String clubName, String applicantName) {
        String subject = "[서비스명] 모임 가입 신청 알림";
        String text = """
                '%s' 님이 '%s' 모임에 가입 신청을 했습니다.
                """.formatted(applicantName, clubName);
        sendPlainTextMail(hostEmail, subject, text);
    }

    @Override
    public void sendClubJoinApproveMail(String to, String clubName) {
        String subject = "[서비스명] 모임 가입 승인 안내";
        String text = """
                신청하신 '%s' 모임 가입이 승인되었습니다.
                """.formatted(clubName);
        sendPlainTextMail(to, subject, text);
    }

    @Override
    public void sendClubJoinRejectMail(String to, String clubName, String reason) {
        String subject = "[서비스명] 모임 가입 거절 안내";
        String text = """
                신청하신 '%s' 모임 가입이 아래 사유로 거절되었습니다.

                사유: %s
                """.formatted(clubName, reason);
        sendPlainTextMail(to, subject, text);
    }

    @Override
    public void sendClubDisbandMail(List<String> memberEmails, String clubName) {
        String subject = "[서비스명] 모임 해산 안내";
        String text = """
                '%s' 모임이 해산되었습니다.
                그동안 참여해주셔서 감사합니다.
                """.formatted(clubName);

        for (String to : memberEmails) {
            sendPlainTextMail(to, subject, text);
        }
    }

    @Override
    public void sendClubMemberLeaveMail(String hostEmail, String clubName, String memberName) {
        String subject = "[서비스명] 모임 탈퇴 알림";
        String text = """
                '%s' 님이 '%s' 모임에서 탈퇴했습니다.
                """.formatted(memberName, clubName);
        sendPlainTextMail(hostEmail, subject, text);
    }
}
