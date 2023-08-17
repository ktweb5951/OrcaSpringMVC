package com.orca.spring.notice.service;

import java.util.List;

import com.orca.spring.notice.domain.Notice;

public interface NoticeService {

	/**
	 * 공지사항 등록 Service
	 * @param notice
	 * @return int
	 */
	int insertNotice(Notice notice);

	List<Notice> selectNoticeList();

}
