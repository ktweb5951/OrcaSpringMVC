package com.orca.spring.member.service;

import com.orca.spring.member.domain.Member;

public interface MemberService {

	/**
	 * 회원가입 service
	 * @param member
	 * @return int
	 */
	public int insertMember(Member member);

	/**
	 * 로그인 service
	 * @param member
	 * @return Member
	 */
	public Member checkMemberLogin(Member member);

	public Member selectOneById(String memberId);

	public int updateMember(Member member);

	public int deleteMember(Member member);
	
}
