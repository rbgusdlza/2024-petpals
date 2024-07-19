package rbgusdlza.petpals.web.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbgusdlza.petpals.domain.member.Member;
import rbgusdlza.petpals.domain.member.MemberRepository;
import rbgusdlza.petpals.web.service.member.request.LoginIdServiceForm;
import rbgusdlza.petpals.web.service.member.request.MemberJoinServiceRequest;
import rbgusdlza.petpals.web.service.member.request.NicknameServiceForm;
import rbgusdlza.petpals.web.service.member.response.LoginIdCheckResponse;
import rbgusdlza.petpals.web.service.member.response.NicknameCheckResponse;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void joinMember(MemberJoinServiceRequest request) {
        Member member = request.toEntity();
        System.out.println("member = " + member.getEncryptedPassword());
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

    private boolean isDuplicated(Member findMember) {
        return findMember != null;
    }
}
