package com.java.service;

import java.util.Optional;

import com.java.entity.Member;

public interface MemberService {

	Member findById(int memberId);
}
