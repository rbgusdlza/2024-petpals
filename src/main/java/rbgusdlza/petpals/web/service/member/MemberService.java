package rbgusdlza.petpals.web.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.domain.member.Member;
import rbgusdlza.petpals.domain.member.MemberRepository;
import rbgusdlza.petpals.web.error.ErrorCode;
import rbgusdlza.petpals.web.error.PetPalsException;
import rbgusdlza.petpals.web.service.member.request.EmailServiceForm;
import rbgusdlza.petpals.web.service.member.request.LoginIdServiceForm;
import rbgusdlza.petpals.web.service.member.request.MemberJoinServiceRequest;
import rbgusdlza.petpals.web.service.member.request.NicknameServiceForm;
import rbgusdlza.petpals.web.service.member.response.EmailCheckResponse;
import rbgusdlza.petpals.web.service.member.response.LoginIdCheckResponse;
import rbgusdlza.petpals.web.service.member.response.NicknameCheckResponse;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void joinMember(MemberJoinServiceRequest request) {
        checkIfLoginIdDuplicate(request);
        checkIfNicknameDuplicate(request);
        checkIfEmailDuplicate(request);

        Member member = request.toEntity();
        memberRepository.save(member);
    }

    public LoginIdCheckResponse isLoginIdDuplicate(LoginIdServiceForm form) {
        String loginId = form.getLoginId();
        Member findMember = memberRepository.findByLoginId(loginId);
        return LoginIdCheckResponse.from(isDuplicated(findMember));
    }

    public NicknameCheckResponse isNicknameDuplicate(NicknameServiceForm form) {
        String nickname = form.getNickname();
        Member findMember = memberRepository.findByNickname(nickname);
        return NicknameCheckResponse.from(isDuplicated(findMember));
    }

    public EmailCheckResponse isEmailDuplicate(EmailServiceForm form) {
        String email = form.getEmail();
        Member findMember = memberRepository.findByEmail(email);
        return EmailCheckResponse.from(isDuplicated(findMember));
    }

    private void checkIfLoginIdDuplicate(MemberJoinServiceRequest request) {
        String loginId = request.getLoginId();
        Member findMember = memberRepository.findByLoginId(loginId);
        if (isDuplicated(findMember)) {
            throw new PetPalsException(ErrorCode.DUPLICATE_LOGIN_ID);
        }
    }

    private void checkIfNicknameDuplicate(MemberJoinServiceRequest request) {
        String nickname = request.getNickname();
        Member findMember = memberRepository.findByNickname(nickname);
        if (isDuplicated(findMember)) {
            throw new PetPalsException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }

    private void checkIfEmailDuplicate(MemberJoinServiceRequest request) {
        String email = request.getEmail();
        Member findMember = memberRepository.findByEmail(email);
        if (isDuplicated(findMember)) {
            throw new PetPalsException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    private boolean isDuplicated(Member findMember) {
        return findMember != null;
    }
}
