package com.itwillbs.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.itwillbs.domain.MemberVO;
import com.itwillbs.persistence.MemberDAO;

// @Service : 스프링(root-context.xml)에서 해당 객체를 서비스로 인식
//             => 서비스 객체(빈)으로 인식

@Service
public class MemberServiceImpl implements MemberService {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Inject
	private MemberDAO mdao;
	
	/* 회원가입 */
	@Override
	public void memberJoin(MemberVO vo) {
		// 컨트롤러가 전달해준 정보를 가지고 DAO를 처리
		// DAO 객체 생성 - 회원가입 처리 메서드 호출
		logger.debug(" DAO 회원가입 메서드 호출 - 시작");
		mdao.insertMember(vo);
		logger.debug(" DAO 회원가입 메서드 호출 - 끝");
	}

	/* 로그인 */
	@Override
	public MemberVO memberLogin(MemberVO vo) {
		logger.debug(" Service - 로그인 처리 memberLogin(MemberVO vo) ");
		return mdao.selectLoginMember(vo);
	}

	/* 회원정보 조회 */
	@Override
	public MemberVO memberinfo(String userid) {
		logger.debug(" Service - 회원정보 조회 memberinfo(String userid) ");
		//DAO객체 사용해서 해당 동작 처리하는 메서드호출
		return mdao.getMember(userid);
	}

	/* 회원정보 수정 */
	@Override
	public void memberUpdate(MemberVO vo) {
		logger.debug(" Service - 회원정보 수정 memberUpdate(MemberVO vo)");
		mdao.updateMember(vo);
	}

	/* 회원정보 삭제 */
	@Override
	public int memberDelete(MemberVO vo) {
		logger.debug(" memberDelete(MemberVO vo) ");
		return mdao.deleteMember(vo);
	}

	/* 회원목록 조회 */
	@Override
	public List<MemberVO> memberList() {
		logger.debug("  memberList() ");
		return mdao.getMemberList();
	}
	
	
	
	
	
	

	
	
	
	
	

	
	
	
}
