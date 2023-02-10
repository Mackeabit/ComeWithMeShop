package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import mackeabit.shop.Repository.MemberRepository;
import mackeabit.shop.vo.MembersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

//    @RequiredArgsConstructor 으로 autowired 자동 주입
//    public MemberService(MemberRepository repository) {
//        this.repository = repository;
//    }

    private final MemberRepository repository;

    public List<MembersVO> findAll() {

        return repository.findAll();

    }

    public int emailCheck(String email) {
        return repository.emailCheck(email);
    }


}
