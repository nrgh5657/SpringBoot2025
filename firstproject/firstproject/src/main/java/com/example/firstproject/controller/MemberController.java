package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/members/new")
    public String newMember() {
        return "members/new";
    }

    @PostMapping("/members/create")
    public String registerMember(MemberForm form){
        Member member = form.toEntity();

        Member saved = memberRepository.save(member);

        return "redirect:/members/" + saved.getId();
    }

    @GetMapping("members/{id}")
    public String showMember(@PathVariable("id") Long id, Model model) {
        Member memberEntity = memberRepository.findById(id).orElse(null);

        model.addAttribute("member", memberEntity);
        return "members/show";
    }

    @GetMapping("/members")
    public String showAllMembers(Model model) {
        List<Member> memberEntityList = memberRepository.findAll();
        memberEntityList.forEach(list->{log.info("List:{}", list);});
        model.addAttribute("memberList", memberEntityList);
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String updateMember(@PathVariable Long id, Model model) {
        Member memberEntity = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);
        return "members/edit";

    }

    @PostMapping("/members/{id}/edit")
    public String update(@PathVariable Long id, MemberForm form) {
        Member memberEntity = memberRepository.findById(id).orElse(null);
        if (memberEntity != null) {
            memberEntity.setEmail(form.getEmail());
            memberEntity.setPassword(form.getPassword());
            memberRepository.save(memberEntity);
        }
        return "redirect:/members/" + id;
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id) {
        Member targetMember = memberRepository.findById(id).orElse(null);
        if(targetMember != null) {
            memberRepository.delete(targetMember);
        }
        return "redirect:/members";
    }




}


