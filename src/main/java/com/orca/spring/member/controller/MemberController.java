package com.orca.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.orca.spring.member.domain.Member;
import com.orca.spring.member.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService service;
	
	@RequestMapping(value="/member/register.kh", method=RequestMethod.GET)
	public String showRegisterform() {
		return "member/register";
	}
	
	@RequestMapping(value="/member/register.kh", method=RequestMethod.POST)
	public String registerMember(
//			@RequestParam("memberId") String memberId
			@ModelAttribute Member member
			, Model model) {
		try {
			int result = service.insertMember(member);
			if(result>0) {
				//성공하면 로그인페이지
				//home.jsp가 로그인할 수 있는 페이지가 되면됨
				return "redirect:/index.jsp";
			} else {
				//실패하면 에러페이지로 이동
				model.addAttribute("msg", "회원가입이 완료되지 않았습니다.");
				model.addAttribute("error", "회원가입 실패");
				model.addAttribute("url", "/member/register.kh");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/register.kh");
			return "common/errorPage";
		}
		
	}
	
	@RequestMapping(value="/member/login.kh", method=RequestMethod.POST)
	public String memberLoginCheck(
			@ModelAttribute Member member
			,HttpSession session
			,HttpServletRequest request
			,Model model) {
		
		//SELECT * FROM MEMBER_TBL WHERE MEMBER_ID=? AND MEMBER_PW=?
		
		try {
//			int result = service.checkMemberLogin(member);
//			if(result>0) {
//				session = request.getSession();
//				session.setAttribute("memberId", member.getMemberId());
//				session.setAttribute("memberName", member.getMemberName());
//				return "redirect:/index.jsp";
//			} else {
//				model.addAttribute("msg", "로그인이 완료되지 않았습니다.");
//				model.addAttribute("error", "로그인 실패");
//				model.addAttribute("url", "/member/register.kh");
//				return "common/errorPage";			
//			}
			Member mOne = service.checkMemberLogin(member);
			if(mOne !=null) {
				session = request.getSession();
				session.setAttribute("memberId", mOne.getMemberId());
				session.setAttribute("memberName", mOne.getMemberName());
				return "redirect:/index.jsp";
			} else {	
				model.addAttribute("msg", "로그인이 완료되지 않았습니다.");
				model.addAttribute("error", "로그인 실패");
				model.addAttribute("url", "/member/register.kh");
				return "common/errorPage";			
			}
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/register.kh");
			return "common/errorPage";
		}
	}

	@RequestMapping(value="/member/logout.kh", method=RequestMethod.GET)
	public String memberLogout(HttpSession session, Model model) {
		if(session != null) {
			session.invalidate();
			return "redirect:/index.jsp";
		} else {
			model.addAttribute("mgs", "로그아웃을 완료하지 못하였습니다.");
			model.addAttribute("error", "로그아웃 실패");
			model.addAttribute("url", "/index.jsp");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/mypage.kh", method= {RequestMethod.GET, RequestMethod.POST})
	public String memberInfo(Model model
			,HttpSession session
			) {
		//SELECT * FROM MEMBER_TBL WHERE MEMBER_ID
		try {
			String memberId = (String) session.getAttribute("memberId");
			Member mOne = null;
			if(memberId != "" & memberId !=null) {
				mOne = service.selectOneById(memberId);				
			}
			if(mOne!=null) {
				model.addAttribute("member", mOne);
				return "member/myPage";
			} else {
				model.addAttribute("mgs", "데이터가 조회되고 있지않습니다.");
				model.addAttribute("error", "마이페이지 조회 실패");
				return "common/errorPage";
			}
			
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/register.kh");
			return "common/errorPage";
		}
	}
	
	
	@RequestMapping(value="/member/modify.kh", method=RequestMethod.GET)
	public String showModifyMember(Model model
			,@RequestParam("memberId") String memberId) {
		try {
			Member mOne = service.selectOneById(memberId);
			if(mOne!=null) {
				model.addAttribute("member", mOne);
				return "member/modify";
			} else {
				model.addAttribute("mgs", "데이터가 조회되고 있지않습니다.");
				model.addAttribute("error", "마이페이지 조회 실패");
				return "common/errorPage";
			}		
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/register.kh");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/modify.kh", method=RequestMethod.POST)
	public String modifyMember(Model model
			, @ModelAttribute Member member) {
		
		try {
			int result = service.updateMember(member);
			if(result>0){
				return "member/myPage";
			} else {
				model.addAttribute("mgs", "데이터가 조회되고 있지않습니다.");
				model.addAttribute("error", "마이페이지 조회 실패");
				model.addAttribute("url", "/index.jsp");
				return "common/errorPage";
			}
					
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/register.kh");
			return "common/errorPage";
		}
		
	}
	
	@RequestMapping(value="/member/delete.kh", method=RequestMethod.GET)
	public String memberDelete(Model model
			,@ModelAttribute Member member) {
		//DELETE FROM MEMBER_TBL WHERE MEMBER_ID=?
		
		try {
			int result = service.deleteMember(member);
			
			if(result>0) {
				return "redirect:/member/logout.kh";
			} else {
				model.addAttribute("mgs", "회원탈퇴를 완료하지 못하였습니다.");
				model.addAttribute("error", "회원탈퇴 실패");
				model.addAttribute("url", "/index.jsp");
				return "common/errorPage";
			}
			
		} catch (Exception e) {
			model.addAttribute("msg", "관리자에게 문의해주세요.");
			model.addAttribute("error", e.getMessage());
			model.addAttribute("url", "/member/register.kh");
			return "common/errorPage";
		}
		
	}
	
}
