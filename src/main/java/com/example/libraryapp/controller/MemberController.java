package com.example.libraryapp.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.libraryapp.model.Member;
import com.example.libraryapp.repository.MemberRepository;

@RestController
@RequestMapping("/api")
public class MemberController {
    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // GET: http://localhost:8080/api/members
    @GetMapping("/members")
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // POST: http://localhost:8080/api/add-member
    @PostMapping("/add-member")
    public Member createMember(@RequestBody Member member) {
        return memberRepository.save(member);
    }

    // PUT: http://localhost:8080/api/edit-member/{id}
    @PutMapping("/edit-member/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member updated) {
        return memberRepository.findById(id).map(member -> {
            member.setName(updated.getName());
            member.setEmail(updated.getEmail());
            member.setPhone(updated.getPhone());
            return memberRepository.save(member);
        }).orElseThrow();
    }

    // DELETE: http://localhost:8080/api/delete-member/{id}
    @DeleteMapping("/delete-member/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberRepository.deleteById(id);
    }

    // GET: http://localhost:8080/api/members/search?query=...
    @GetMapping("/members/search")
    public List<Member> searchMembers(@RequestParam String query) {
        return memberRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
    }
}
