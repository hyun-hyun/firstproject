package com.example.firstproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.MemberRepository;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
public class MemberController {
    @Autowired//저장소 의존성 주입(DI)
    private MemberRepository memberRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @GetMapping("/signup")
    public String newJoin() {
        return "members/new";
    }
    
    @PostMapping("/join")
    public String createMember(MemberForm form, Model model) {
        log.info(form.toString());//DTO담긴거(MemberForm) 확인
        Member member=form.toEntity();//DTO를 member 엔티티로 변환
        log.info(member.toString());//엔티티 변환(member) 확인
        Member saved=memberRepository.save(member);//엔티티를 레포지토리로 DB에 저장
        log.info(saved.toString());//DB저장확인(member)

        model.addAttribute("now_member", member);

        return "members/joined";
    }
    
    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model){
        Member memberEntity=memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);
        return "members/show";
    }
    @GetMapping("/members")
    public String index(Model model){
        ArrayList<Member> memberListEntity=memberRepository.findAll();
        model.addAttribute("memberList", memberListEntity);
        return "members/index";
    }
    @GetMapping("")
    public String home(Model model1, Model model2){
        ArrayList<Article> articleListEntity=articleRepository.findAll();
        model1.addAttribute("articleList", articleListEntity);
        ArrayList<Member> memberListEntity=memberRepository.findAll();
        model2.addAttribute("memberList", memberListEntity);
        return "members/main";
    }
    
    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Member memberEntity=memberRepository.findById(id).orElse(null);//DB에서 데이터 가져오기(entity)
        model.addAttribute("member", memberEntity);//모델에 등록
        return "members/edit";
    }
    @PostMapping("/members/update")
    public String update(MemberForm form){//내용물 DTO로받기
        log.info(form.toString());
        Member memberEntity=form.toEntity();//DTO->entity
        log.info(memberEntity.toString());
        Member target=memberRepository.findById(memberEntity.getId()).orElse(null);//entity의 id랑 같은 내용 가져오기
        if(target != null){
            memberRepository.save(memberEntity);//갱신
        }
        return "redirect:/members/"+memberEntity.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("회원 삭제요청");
        Member target=memberRepository.findById(id).orElse(null);
        log.info(target.toString());
        if(target != null){
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg", "회원이 삭제됐습니다.");
        }
        return "redirect:/members";
    }
}
