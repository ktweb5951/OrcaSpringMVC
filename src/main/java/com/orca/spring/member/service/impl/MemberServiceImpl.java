package com.orca.spring.member.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orca.spring.member.domain.Member;
import com.orca.spring.member.service.MemberService;
import com.orca.spring.member.store.MemberStore;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private SqlSession session;
	
	@Autowired
	private MemberStore mStore;

	@Override
	public int insertMember(Member member) {
		int result = mStore.insertMember(session, member);
		if(result>0) {
			session.commit();
		} else {
			session.rollback();
		}
		session.close();
		return result;
	}

	@Override
	public int updateMember(Member member) {
		int result = mStore.updateMember(session, member);
		return result;
	}

	@Override
	public int deleteMember(Member member) {
		int result = mStore.deleteMember(session, member);
		return result;
	}

	@Override
	public Member checkMemberLogin(Member member) {
		Member mOne = mStore.checkMemberLogin(session, member);
		return mOne;
	}

	@Override
	public Member selectOneById(String memberId) {
		Member mOne = mStore.selectOneByid(session, memberId);
		return mOne;
	}
}
