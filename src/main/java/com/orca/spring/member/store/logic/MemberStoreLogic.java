package com.orca.spring.member.store.logic;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.orca.spring.member.domain.Member;
import com.orca.spring.member.store.MemberStore;

@Repository
public class MemberStoreLogic implements MemberStore{

	@Override
	public int insertMember(SqlSession session, Member member) {
		int result  = session.insert("MemberMapper.insertMember", member);
		return result;
	}

	@Override
	public int updateMember(SqlSession session, Member member) {
		int result = session.update("MemberMapper.updateMember", member);
		return result;
	}

	@Override
	public int deleteMember(SqlSession session, Member member) {
		int result = session.update("MemberMapper.deleteMember", member);
		return result;
	}

	@Override
	public Member checkMemberLogin(SqlSession session, Member member) {
		Member mOne = session.selectOne("MemberMapper.checkMemberLogin", member);
		return mOne;
	}

	@Override
	public Member selectOneByid(SqlSession session, Member member) {
		Member mOne = session.selectOne("MemberMapper.selectOneByid", member);
		return mOne;
	}
	
}
