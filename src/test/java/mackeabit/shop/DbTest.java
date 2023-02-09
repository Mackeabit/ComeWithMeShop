package mackeabit.shop;

import mackeabit.shop.Repository.MemberRepository;
import mackeabit.shop.mapper.MemberMapper;
import mackeabit.shop.vo.MembersVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DbTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberMapper memberMapper;


    @Test
    @DisplayName("테스트 프로필 작동 확인")
    void Ck_profile() {
    }

    @Test
    @DisplayName("모두 찾기")
    void findAll() {
        Assertions.assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("멤버 저장")
    void save() {
        MembersVO membersVO = new MembersVO();
        membersVO.setEmail("sddthdt222@naver.com");
        membersVO.setPwd("qo22730");
        memberRepository.save(membersVO);
        Assertions.assertThat(memberRepository.findAll().size()).isEqualTo(4);
    }



}
