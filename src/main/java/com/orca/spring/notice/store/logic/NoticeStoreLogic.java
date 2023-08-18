package com.orca.spring.notice.store.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.orca.spring.notice.domain.Notice;
import com.orca.spring.notice.domain.PageInfo;
import com.orca.spring.notice.store.NoticeStore;

@Repository
public class NoticeStoreLogic implements NoticeStore {

	@Override
	public int insertNotice(SqlSession session, Notice notice) {
		int result = session.insert("NoticeMapper.insertNotice", notice);
		return result;
	}

	@Override
	public List<Notice> selectNoticeList(SqlSession session, PageInfo pInfo) {
		int limit = pInfo.getRecordCountPerPage(); // 가져오는 행의 갯수
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> nList = session.selectList("NoticeMapper.selectNoticeList", null, rowBounds);
		return nList;
	}


	@Override
	public int selectListCount(SqlSession session, Map<String, String> paramMap) {
		int result = session.selectOne("NoticeMapper.selectListByKeywordCount", paramMap);
		return result;
	}

	@Override
	public List<Notice> searchNoticesByTitle(SqlSession session, String searchKeyword, PageInfo pInfo) {	
		int limit = pInfo.getRecordCountPerPage(); // 가져오는 행의 갯수
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> searchList = session.selectList("NoticeMapper.searchNoticesByTitle", searchKeyword, rowBounds);
		return searchList;
	}

	@Override
	public List<Notice> searchNoticesByContent(SqlSession session, String searchKeyword, PageInfo pInfo) {
		int limit = pInfo.getRecordCountPerPage(); // 가져오는 행의 갯수
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> searchList = session.selectList("NoticeMapper.searchNoticesByContent", searchKeyword, rowBounds);
		return searchList;
	}

	@Override
	public List<Notice> searchNoticesByWriter(SqlSession session, String searchKeyword, PageInfo pInfo) {
		int limit = pInfo.getRecordCountPerPage(); // 가져오는 행의 갯수
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> searchList = session.selectList("NoticeMapper.searchNoticesByWriter", searchKeyword, rowBounds);
		return searchList;
	}

	@Override
	public List<Notice> searchNoticesAll(SqlSession session, String searchKeyword, PageInfo pInfo) {
		int limit = pInfo.getRecordCountPerPage(); // 가져오는 행의 갯수
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);List<Notice> searchList = session.selectList("NoticeMapper.searchNoticesAll", searchKeyword, rowBounds);
		return searchList;
	}

	@Override
	public List<Notice> searchNoticesByKeyword(SqlSession session, PageInfo pInfo,Map<String,String> paramMap) {
		int limit = pInfo.getRecordCountPerPage(); // 가져오는 행의 갯수
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> searchList = session.selectList("NoticeMapper.searchNoticesByKeyword", paramMap, rowBounds);
		return searchList;
	}

	@Override
	public int selectListCount(SqlSession session) {
		int result = session.selectOne("NoticeMapper.selectListCount");
		return result;
	}

	
}
