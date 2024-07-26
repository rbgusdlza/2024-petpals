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
        checkIfMemberIsValid(request);
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
        List<Member> findMembers = memberRepository.findAllByLoginId(loginId);
        return LoginIdCheckResponse.from(isDuplicated(findMembers));
    }

    public NicknameCheckResponse isNicknameDuplicate(NicknameServiceForm form) {
        String nickname = form.getNickname();
        List<Member> findMembers = memberRepository.findAllByNickname(nickname);
        return NicknameCheckResponse.from(isDuplicated(findMembers));
    }

    public EmailCheckResponse isEmailDuplicate(EmailServiceForm form) {
        String email = form.getEmail();
        List<Member> findMembers = memberRepository.findAllByEmail(email);
        return EmailCheckResponse.from(isDuplicated(findMembers));
    }

    private void checkIfMemberIsValid(MemberJoinServiceRequest request) {
        String loginId = request.getLoginId();
        String nickname = request.getNickname();
        String email = request.getEmail();

        if (hasDuplicatesForMember(loginId, nickname, email)) {
            throw new PetPalsException(MEMBER_JOIN_ERROR);
        }
    }

    private boolean hasDuplicatesForMember(String loginId, String nickname, String email) {
        long numberOfDuplicateMembers = memberRepository.countByLoginIdOrNicknameOrEmail(loginId, nickname, email);
        return numberOfDuplicateMembers > 0;
    }

    private boolean isDuplicated(List<Member> findMembers) {
        return !findMembers.isEmpty();
    }
}
