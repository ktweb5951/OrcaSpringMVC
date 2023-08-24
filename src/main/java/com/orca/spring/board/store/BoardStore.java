package com.orca.spring.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.orca.spring.board.domain.Board;
import com.orca.spring.board.domain.PageInfo;

public interface BoardStore {

	/**
	 * 게시글 등록 Store
	 * @param sqlSession
	 * @param board
	 * @return
	 */
	int insertBoard(SqlSession sqlSession, Board board);

	/**
	 * 전체 게시물 개수 Store
	 * @param sqlSession
	 * @return
	 */
	int selectListCount(SqlSession sqlSession);

	/**
	 * 게시글 전체 조회 Store
	 * @param sqlSession
	 * @param pInfo
	 * @return
	 */
	List<Board> selectBoardList(SqlSession sqlSession, PageInfo pInfo);

	/**
	 * 게시글 상세 조회 Store
	 * @param sqlSession
	 * @param boardNo
	 * @return
	 */
	Board selectBoarByNo(SqlSession sqlSession, Integer boardNo);

}
