package com.java.service;

import com.java.entity.Member;

public interface MemberService {

	Member findByLoginId(String id);
}
