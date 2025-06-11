package com.example.libraryapp.repository;

import com.example.libraryapp.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
}