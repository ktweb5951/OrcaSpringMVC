package com.orca.spring.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.orca.spring.board.domain.Reply;

public interface ReplyStore {

	/**
	 * 게시글 댓글등록 Store
	 * @param sqlSession
	 * @param reply
	 * @return
	 */
	int insertReply(SqlSession sqlSession, Reply reply);

	/**
	 * 댓글 전체 조회 Store
	 * @param sqlSession
	 * @param refBoardNo
	 * @return
	 */
	List<Reply> selectReplyList(SqlSession sqlSession, int refBoardNo);

	/**
	 * 댓글 수정 Store
	 * @param sqlSession
	 * @param reply
	 * @return
	 */
	int updateReply(SqlSession sqlSession, Reply reply);

	/**
	 * 댓글 삭제 Store
	 * @param sqlSession
	 * @param reply
	 * @return
	 */
	int deleteReply(SqlSession sqlSession, Reply reply);

}
