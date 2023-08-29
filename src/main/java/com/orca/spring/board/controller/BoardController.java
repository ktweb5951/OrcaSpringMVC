package com.orca.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.orca.spring.board.domain.Board;
import com.orca.spring.board.domain.PageInfo;
import com.orca.spring.board.domain.Reply;
import com.orca.spring.board.service.BoardService;
import com.orca.spring.board.service.ReplyService;

@Controller
public class BoardController {

	@Autowired
	private ReplyService rService;
	
	@Autowired
	private BoardService bService;
	
	@RequestMapping(value="/board/write.kh", method=RequestMethod.POST)
	public ModelAndView boardRegister(
			ModelAndView mv
			,@ModelAttribute Board board
			,@RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			,HttpServletRequest request
			,HttpSession session
			) {
		try {
			String boardWriter = (String)session.getAttribute("memberId");
			if(boardWriter!=null && !boardWriter.equals("")) {
				if(uploadFile != null && !uploadFile.getOriginalFilename().equals("")) {
					//파일정보(이름, 리네임, 경로, 크기) 및 파일저장
					Map<String, Object>bMap = this.saveFile(request, uploadFile);
					board.setBoardFilename((String)bMap.get("fileName"));
					board.setBoardFileRename((String)bMap.get("fileRename"));
					board.setBoardFilepath((String)bMap.get("filePath"));
					board.setBoardFilelength((long)bMap.get("fileLength"));			
					board.setBoardWriter(boardWriter);
					
				}
				int result = bService.insertBoard(board);
				mv.addObject("memberId", boardWriter);
				mv.setViewName("redirect:/board/list.kh");
				
			} else {
				mv.addObject("msg", "로그인 정보가 존재하지 않습니다.");
				mv.addObject("error", "로그인이 필요합니다.");
				mv.addObject("url",	"/index.jsp");
				mv.setViewName("common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", "게시글이 등록되지 않았습니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url",	"/board/write.kh");
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping(value="/board/modify.kh", method=RequestMethod.GET)
	public ModelAndView showModifyForm(ModelAndView mv
			,@RequestParam("boardNo") Integer boardNo) {
		Board boardOne = bService.selectBoardByNo(boardNo);
		mv.addObject("board", boardOne);
		mv.setViewName("board/modify");
		return mv;
	}
	
	@RequestMapping(value="/board/modify.kh", method=RequestMethod.POST)
	public ModelAndView updateBoard(ModelAndView mv
			, HttpSession session
			, @ModelAttribute Board board
			, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, HttpServletRequest request) {
		
		try {
			String memberId = (String)session.getAttribute("memberId");
			String boardWriter = board.getBoardWriter();
			String url = "";
			if(boardWriter !=null && boardWriter.equals(memberId)) {
				url = "/board/detail.kh?boardNo="+ board.getBoardNo();
				//수정이라는 과정은 대체하는 것, 대체하는 방법은 삭제 후 등록
				if(uploadFile != null && !uploadFile.isEmpty()) {
					String fileName = board.getBoardFileRename();
					if(fileName !=null) {
						this.deleteFile(request, fileName);
					}
					Map<String, Object> infoMap = this.saveFile(request, uploadFile);
					String boardFilename = (String)infoMap.get("fileName");
					String boardFileRename = (String)infoMap.get("fileRename");
					long boardFilelength = (long)infoMap.get("fileLength");
					String boardFilepath = (String)infoMap.get("filePath");
					board.setBoardFilename(boardFilename);
					board.setBoardFileRename(boardFileRename);
					board.setBoardFilepath(boardFilepath);
					board.setBoardFilelength(boardFilelength);	
				}
				
				int result = bService.updateBoard(board);
				mv.setViewName("redirect:"+url);
			} else {
				mv.addObject("msg", "자신의 게시글만 수정할 수 잇습니다.");
				mv.addObject("error", "게시글 수정 실패");
				mv.addObject("url",	url);
				mv.setViewName("common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url",	"/index.jsp");
			mv.setViewName("common/errorPage");
		}
		
		return mv;
	}
	
	@RequestMapping(value="/board/delete.kh", method=RequestMethod.GET)
	public ModelAndView deleteBoard(ModelAndView mv
			, HttpSession session
			, @ModelAttribute Board board) {
		
		try {
			String memberId = (String)session.getAttribute("memberId");
			String boardWriter = board.getBoardWriter();
			if(boardWriter != null && boardWriter.equals(memberId)) {
				int result = bService.deleteBoard(board);
				if(result>0) {
					mv.setViewName("redirect:/board/list.kh");
				} else {
					mv.addObject("msg", "게시글이 삭제되지 않았습니다.");
					mv.addObject("error", "게시글 삭제 실패");
					mv.addObject("url",	"/board/list.kh");
					mv.setViewName("common/errorPage");
				}
			} else {
				mv.addObject("msg", "자신의 게시글만 삭제할 수 잇습니다.");
				mv.addObject("error", "게시글 삭제 실패");
				mv.addObject("url",	"/board/list.kh");
				mv.setViewName("common/errorPage");
			}	
		} catch (Exception e) {
			mv.addObject("msg", "게시글이 삭제되지 않았습니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url",	"/board/list.kh");
			mv.setViewName("common/errorPage");
		}
		return mv;
	}

	@RequestMapping(value="/board/list.kh", method=RequestMethod.GET)
	public ModelAndView showBoardList(ModelAndView mv
			,@RequestParam(value="page", required=false, defaultValue="1") Integer currentPage
			) {
		try {
			int totalCount = bService.getListCount();
			PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
			List<Board> bList = bService.selectBoardList(pInfo);
			mv.addObject("bList", bList).addObject("pInfo", pInfo).setViewName("board/list");
		} catch (Exception e) {
			mv.addObject("msg", "데이터 조회가 완료되지 않았습니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url",	"/board/write.kh");
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping(value="/board/detail.kh", method=RequestMethod.GET)
	public ModelAndView showBoardDeail(ModelAndView mv
			,@RequestParam("boardNo") Integer boardNo) {
		
		try {
			Board boardOne = bService.selectBoardByNo(boardNo);
			if(boardOne!=null) {
				List<Reply> replyList = rService.selectReplyList(boardNo);
				if(replyList.size()>0) {
					mv.addObject("rList", replyList);
				}
				mv.addObject("board", boardOne);
				mv.setViewName("board/detail");
				
			}
			
		} catch (Exception e) {
			mv.addObject("msg", "게시글 조회가 완료되지 않았습니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url",	"/board/list.kh");
			mv.setViewName("common/errorPage");
		}
		return mv;
	}

	public PageInfo getPageInfo(Integer currentPage, Integer totalCount) {
		int recordCountPerPage =10;
		int naviCountPerPage =10;
		int naviTotalCount;
		
		naviTotalCount = (int)Math.ceil((double)totalCount/recordCountPerPage);
		int startNavi = ((int)((double)currentPage/naviCountPerPage+0.9)-1) * naviCountPerPage+1;
		int endNavi = startNavi + naviCountPerPage -1;
		if(endNavi>naviTotalCount) {
			endNavi=naviTotalCount;
		}
		PageInfo pInfo = new PageInfo(currentPage, totalCount, naviTotalCount, recordCountPerPage, naviCountPerPage, startNavi, endNavi);
		return pInfo;
	}
	
	@RequestMapping(value="/board/write.kh", method=RequestMethod.GET)
	public ModelAndView showWriteForm(ModelAndView mv) {
		mv.setViewName("board/write");
		return mv;
	}
	
	public Map<String, Object> saveFile(HttpServletRequest request, MultipartFile uploadFile) throws Exception, IOException{
		// fileLenth값이 long이기에 object
		Map<String, Object> fileMap = new HashMap<String, Object>();
		//resources 경로 구하기
		String root = request.getSession().getServletContext().getRealPath("resources");
		//파일 저장경로 구하기
		String savePath = root + "\\buploadFiles";
		//파일 이름 구하기
		String fileName = uploadFile.getOriginalFilename();
		//파일 확장자 구하기
		String extension = fileName.substring(fileName.lastIndexOf(".")+1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileRename = sdf.format(new Date(System.currentTimeMillis()))+"." +extension;
		//파일 저장 전 폴더만들기
		File saveFolder = new File(savePath);
		if(!saveFolder.exists()) {
			saveFolder.mkdir();
		}
		//파일저장
		File saveFile = new File(savePath+"\\"+fileRename);
		uploadFile.transferTo(saveFile);
		long fileLength = uploadFile.getSize();
		//파일정보리턴
		fileMap.put("fileName", fileName);
		fileMap.put("fileRename", fileRename);
		fileMap.put("filePath", "../resources/buploadFiles/"+fileRename);
		fileMap.put("fileLength", fileLength);
		return fileMap;
	}

	private void deleteFile(HttpServletRequest request, String fileName) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String delFilepath = root+"\\buploadFiles\\" + fileName;
		File file = new File(delFilepath);
		if(file.exists()) {
			file.delete();				
		}		
	}
	
	
}
