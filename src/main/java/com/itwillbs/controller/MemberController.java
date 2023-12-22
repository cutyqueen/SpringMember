package com.itwillbs.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itwillbs.domain.MemberVO;
import com.itwillbs.service.MemberService;

@Controller
@RequestMapping(value = "/members/*") // 컨트롤러를 구분하는 주소 매핑 ~.me, ~.bo
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	// 서비스 객체 주입
	@Inject
	private MemberService mService;
	
	//http://localhost:8088/controller/join
	//http://localhost:8088/controller/members/join
	
	//http://localhost:8088/members/join
	// -> memberJoinGET() 호출될거임 
	// 회원가입(정보입력/조회/출력)
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public void memberJoinGET() {
		logger.debug("memberJoinGET() 호출");
		// 연결된 뷰페이지로 이동
		logger.debug(" /views/members/join.jsp 페이지로 이동");
	}
	
	// 회원가입 (정보처리)
	@RequestMapping(value = "/join",method = RequestMethod.POST)
	public String memberJoinPOST(/*@ModelAttribute*/MemberVO vo) {
		logger.debug("  memberJoinPOST() 호출 ");
		//MemberVO vo = new MemberVO();
		//vo.setUserid(request.getParamater("userid"));
		// 한글처리(인코딩 설정) => 한글
		// 전달정보 저장
		logger.debug("vo: "+vo);
		
		// DB에 정보를 저장 => 서비스 객체 사용
		// new MemberDAO().method() 호출
		// => 생성된 객체를 주입받아서! logger 밑에 적음
		logger.debug("서비스 회원가입 동작 호출 - 시작");
		mService.memberJoin(vo);
		logger.debug("서비스 회원가입 동작 호출 - 끝");
		
		// 페이지 이동 (로그인페이지-/members/login)		
		return "redirect:/members/login";
	}
	/*************************************************************************/
	//http://localhost:8088/members/login
	// 로그인 - 정보입력(GET)
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void memberloginGET() {
		logger.debug("/members/login 호출 -> memberLoginGET() 실행");
		logger.debug("연결된 뷰페이지 (views/members/login.jsp) 이동");
	}
	
	// 로그인 - 정보 처리(POST)	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String memberLoginPOST(MemberVO vo,HttpSession session) {
		logger.debug(" /members/login.jsp post방식 호출 -> memberLoginPOST() 실행 ");
		
		// 전달정보 저장(파라메터-userid,userpw)
		logger.debug(" 전달정보 : "+vo);
		// 디비접근 -> 서비스접근 - 로그인 처리
		MemberVO resultVO = mService.memberLogin(vo);
		
		// 로그인 결과에 따른 페이지 이동
		if(resultVO != null) {
			// O -> /members/main 페이지 호출(리다이렉트), 세션 아이디정보 저장
			session.setAttribute("id", resultVO.getUserid());
			return "redirect:/members/main";
		}else {
			// X -> /members/login 페이지 호출(리다이렉트)		
			return "redirect:/members/login";
		}
	}
	/*************************************************************************/
	//http://localhost:8088/members/main
	// 메인페이지 - 정보입력(GET)
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public void memberMainGET() {
		logger.debug("/members/main 호출 -> memberMainGET() 실행");
		logger.debug("연결된 뷰페이지 (views/members/main.jsp) 이동");
	}
	/*************************************************************************/
	// 로그아웃 
	@RequestMapping(value = "/logout",method = RequestMethod.GET)
	public String memberLogoutGET(HttpSession session) {
		logger.debug("/members/logout 호출 -> memberLogoutGET() 실행");
		//세션정보 초기화
		session.invalidate();
		//페이지 이동(메인페이지)
		return "redirect:/members/main";
	}
	/*************************************************************************/
	//회원정보 조회
	@GetMapping(value = "/info")
	public void memberInfoGET(HttpSession session, Model model) {
		logger.debug("/members/info 호출 -> memberInfoGET() 실행");
		//1. id 정보 받아오기(세션영역)
		String id = (String)session.getAttribute("id"); //다운캐스팅
		logger.debug("아이디 정보: "+id);
		//2. 서비스 -> id를 사용해서 회원정보 모두 조회 동작
		MemberVO vo = mService.memberinfo(id);
		logger.debug("vo: "+vo);
		//3. DB에서 조회된 결과를 view페이지로 전달 => Model 객체 생성
		model.addAttribute("vo", vo);
		
		model.addAttribute(mService.memberinfo(id));
		//4. 페이지 이동(/members/info.jsp)
	}
	/*************************************************************************/
	//http://localhost:8088/members/main
	// 회원정보 수정 GET - 기존의 회원정보를 가져와서 출력
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public void memberUpdateGET(HttpSession session,Model model ) {
		logger.debug(" /members/update -> memberUpdateGET() ");
		
		// 아이디 정보 저장(세션영역)
		String id = (String) session.getAttribute("id");
		// 서비스 -> 아이디에 해당하는 회원정보 조회
		// 연결된 뷰페이지(/members/update.jsp)에 정보 전달
		model.addAttribute(mService.memberinfo(id));
		
	}
	// 회원정보 수정 POST - 수정된 회원정보를 디비에서 변경
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String memberUpdatePOST(MemberVO vo) {
		logger.debug(" /members/update -> memberUpdatePOST() ");
		// 전달정보 저장(폼태그-파라미터)
		logger.debug("수정할정보: "+vo);
		//서비스 - 회원정보 수정하는 동작
		mService.memberUpdate(vo);
		//메인페이지로 이동
		return "redirect:/members/main";
	}
	/*************************************************************************/
	// 회원정보 삭제 - GET
	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	public void memberDeleteGET() {
		logger.debug(" /members/delete -> memberDeleteGET()");
		logger.debug(" /members/delete.jsp 페이지 이동");			
	}
	
	// 회원정보 삭제 - POST
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public String memberDeletePOST(MemberVO vo ,HttpSession session) {
		logger.debug(" /members/delete -> memberDeletePOST()");
		// 전달정보 저장
		logger.debug(" 삭제할 정보 "+vo);
		//vo.setUserid((String)session.getAttribute("id"));
		
		// 서비스- 회원정보 삭제 (삭제 성공/실패)
		// 성공 -> 세션정보 초기화, 메인페이지 이동
		// 실패 -> 삭제 페이지(이전페이지)로 이동
		int result = mService.memberDelete(vo);
		if(result == 1) { // 삭제 성공
			session.invalidate();
			logger.debug(" /members/main.jsp 페이지 이동");
			return "redirect:/members/main";
		}else { // 삭제 실패
			
			logger.debug(" /members/delete.jsp 페이지 이동");
			return "redirect:/members/delete";
		}
		
	}
	/*************************************************************************/
	// 회원정보 조회(관리자)
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public void memberListGET(Model model) {
		logger.debug(" /members/list -> memberListGET()");
		
		// 서비스 -> 회원목록 모두조회 동작
		// 전달받은 정보를 view페이지로 전달(Model객체 생성)
		model.addAttribute("memberList", mService.memberList());
		//		model.addAttribute( mService.memberList());
		// => memberVOList (이름이 생략된 경우의 이름)
		
		// /members/list.jsp 페이지 이동
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
