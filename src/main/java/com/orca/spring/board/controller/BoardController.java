package com.orca.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.orca.spring.board.service.BoardService;

@Controller
public class BoardController {

	
	@Autowired
	private BoardService bService;
	
	@RequestMapping(value="/board/write.kh", method=RequestMethod.POST)
	public ModelAndView boardRegister(
			ModelAndView mv
			,@ModelAttribute Board board
			,@RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			,HttpServletRequest request
			) {
		try {
			if(uploadFile != null && !uploadFile.getOriginalFilename().equals("")) {
				//파일정보(이름, 리네임, 경로, 크기) 및 파일저장
				Map<String, Object>bMap = this.saveFile(request, uploadFile);
				board.setBoardFilename((String)bMap.get("fileName"));
				board.setBoardFileRename((String)bMap.get("fileRename"));
				board.setBoardFilepath((String)bMap.get("filePath"));
				board.setBoardFilelength((long)bMap.get("fileLength"));
				
			}
			int result = bService.insertBoard(board);
			mv.setViewName("redirect:/board/list.kh");
		} catch (Exception e) {
			mv.addObject("msg", "게시글이 등록되지 않았습니다.");
			mv.addObject("error", e.getMessage());
			mv.addObject("url",	"/board/write.kh");
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
	
	
}
