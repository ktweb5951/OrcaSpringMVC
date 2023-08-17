package com.orca.spring.member.store;

import org.apache.ibatis.session.SqlSession;

import com.orca.spring.member.domain.Member;

public interface MemberStore {

	public int insertMember(SqlSession session, Member member);

	public Member checkMemberLogin(SqlSession session, Member member);

	public Member selectOneByid(SqlSession session, Member member);

	public int updateMember(SqlSession session, Member member);

	public int deleteMember(SqlSession session, Member member);

}
