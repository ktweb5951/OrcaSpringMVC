package com.orca.spring.notice.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orca.spring.notice.domain.Notice;
import com.orca.spring.notice.service.NoticeService;
import com.orca.spring.notice.store.NoticeStore;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private SqlSession session;
	
	@Autowired
	private NoticeStore nStore;
	
	
	@Override
	public int insertNotice(Notice notice) {
		int result = nStore.insertNotice(session, notice);
		return result;
	}


	@Override
	public List<Notice> selectNoticeList() {
		List<Notice> nList = nStore.selectNoticeList(session);
		return nList;
	}

	
}
