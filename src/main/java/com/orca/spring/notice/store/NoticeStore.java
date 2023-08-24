package com.orca.spring.notice.store;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.orca.spring.notice.domain.Notice;
import com.orca.spring.notice.domain.PageInfo;

public interface NoticeStore {

	
	/**
	 * 공지사항 등록 Store
	 * @param session
	 * @param notice
	 * @return int
	 */
	int insertNotice(SqlSession session, Notice notice);

	/**
	 * 공지사항 수정 Store
	 * @param session
	 * @param notice
	 * @return
	 */
	int updateNotice(SqlSession session, Notice notice);

	/**
	 * 공지사항 목록 조회 Store
	 * @param session
	 * @return list
	 */
	List<Notice> selectNoticeList(SqlSession session, PageInfo pInfo);

	
	/** 
	 * 공지사항 갯수 조회 Store
	 * @param session
	 * @param paramMap 
	 * @return
	 */
	int selectListCount(SqlSession session, Map<String, String> paramMap);

	/**
	 * 공지사항 제목으로 조회 Store
	 * @param session
	 * @param searchKeyword
	 * @param pInfo 
	 * @return
	 */
	List<Notice> searchNoticesByTitle(SqlSession session, String searchKeyword, PageInfo pInfo);

	List<Notice> searchNoticesByContent(SqlSession session, String searchKeyword, PageInfo pInfo);

	List<Notice> searchNoticesByWriter(SqlSession session, String searchKeyword, PageInfo pInfo);

	List<Notice> searchNoticesAll(SqlSession session, String searchKeyword, PageInfo pInfo);

	/**
	 * 공지사항 조건에 따라 키워드로 조회 Store
	 * @param session
	 * @param pInfo 
	 * @param searchKeyword
	 * @param searchCondition
	 * @return
	 */
	
	List<Notice> searchNoticesByKeyword(SqlSession session, PageInfo pInfo, Map<String, String> paramMap);

	/**
	 * 공지사항 검색 게시물 전체 개수 Store
	 * @param session
	 * @return
	 */
	int selectListCount(SqlSession session);

	
	/**
	 * 공지사항 번호로 조회 Store
	 * @param session
	 * @param noticeNo
	 * @return
	 */
	Notice selectNoticeByNo(SqlSession session, Integer noticeNo);
	
}
