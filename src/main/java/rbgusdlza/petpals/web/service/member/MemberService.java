package rbgusdlza.petpals.web.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.domain.member.Member;
import rbgusdlza.petpals.domain.member.MemberRepository;
import rbgusdlza.petpals.web.error.PetPalsException;
import rbgusdlza.petpals.web.service.member.request.*;
import rbgusdlza.petpals.web.service.member.response.EmailCheckResponse;
import rbgusdlza.petpals.web.service.member.response.LoginIdCheckResponse;
import rbgusdlza.petpals.web.service.member.response.MemberResponse;
import rbgusdlza.petpals.web.service.member.response.NicknameCheckResponse;

import java.util.List;

import static rbgusdlza.petpals.web.error.ErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse join(MemberJoinServiceRequest request) {
        checkIfLoginIdDuplicate(request);
        checkIfNicknameDuplicate(request);
        checkIfEmailDuplicate(request);

        Member member = request.toEntity();
        memberRepository.save(member);
        return MemberResponse.of(member);
    }

    public MemberResponse login(MemberLoginServiceRequest request) {
        String loginId = request.getLoginId();
        String encryptedPassword = request.getEncryptedPassword();
        Member findMember = memberRepository.findByLoginIdAndEncryptedPassword(loginId, encryptedPassword)
                .orElseThrow(() -> new PetPalsException(MEMBER_LOGIN_ERROR));
        return MemberResponse.of(findMember);
    }

    public LoginIdCheckResponse isLoginIdDuplicate(LoginIdServiceForm form) {
        String loginId = form.getLoginId();
        List<Member> findMembers = memberRepository.findByLoginId(loginId);
        return LoginIdCheckResponse.from(isDuplicated(findMembers));
    }

    public NicknameCheckResponse isNicknameDuplicate(NicknameServiceForm form) {
        String nickname = form.getNickname();
        List<Member> findMembers = memberRepository.findByNickname(nickname);
        return NicknameCheckResponse.from(isDuplicated(findMembers));
    }

    public EmailCheckResponse isEmailDuplicate(EmailServiceForm form) {
        String email = form.getEmail();
        List<Member> findMembers = memberRepository.findByEmail(email);
        return EmailCheckResponse.from(isDuplicated(findMembers));
    }

    private void checkIfLoginIdDuplicate(MemberJoinServiceRequest request) {
        String loginId = request.getLoginId();
        List<Member> findMembers = memberRepository.findByLoginId(loginId);
        if (isDuplicated(findMembers)) {
            throw new PetPalsException(DUPLICATE_LOGIN_ID);
        }
    }

    private void checkIfNicknameDuplicate(MemberJoinServiceRequest request) {
        String nickname = request.getNickname();
        List<Member> findMembers = memberRepository.findByNickname(nickname);
        if (isDuplicated(findMembers)) {
            throw new PetPalsException(DUPLICATE_NICKNAME);
        }
    }

    private void checkIfEmailDuplicate(MemberJoinServiceRequest request) {
        String email = request.getEmail();
        List<Member> findMembers = memberRepository.findByEmail(email);
        if (isDuplicated(findMembers)) {
            throw new PetPalsException(DUPLICATE_EMAIL);
        }
    }

    private boolean isDuplicated(List<Member> findMembers) {
        return !findMembers.isEmpty();
    }
}
