package com.example.libraryapp.controller;

import com.example.libraryapp.model.Member;
import com.example.libraryapp.repository.MemberRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return memberRepository.save(member);
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member updated) {
        return memberRepository.findById(id).map(member -> {
            member.setName(updated.getName());
            member.setEmail(updated.getEmail());
            member.setPhone(updated.getPhone());
            return memberRepository.save(member);
        }).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberRepository.deleteById(id);
    }

    @GetMapping("/search")
    public List<Member> searchMembers(@RequestParam String query) {
        return memberRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
    }
}
