package fr.eni.projetEnchere.bll.member;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.eni.projetEnchere.bo.Member;
import fr.eni.projetEnchere.dal.member.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService{
	
	Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	MemberRepository memberRepo;
	
	
	public MemberServiceImpl(MemberRepository memberRepo) {
		super();
		this.memberRepo = memberRepo;
	}

	@Override
	public void create(Member member) {
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		memberRepo.create(member);	
	}

	@Override
	public List<Member> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member getById(int id) {
		return memberRepo.getById(id).get();
	}

	@Override
	public void update(Member member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Member getByUserName(String userName) {
		return memberRepo.getByUserName(userName).get();
	}


}
