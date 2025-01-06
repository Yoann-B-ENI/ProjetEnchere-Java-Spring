package fr.eni.projetEnchere.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.eni.projetEnchere.bo.Member;
import fr.eni.projetEnchere.dal.member.MemberRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	MemberRepository memberRepo;
	Member member;
	
	public UserDetailsServiceImpl(MemberRepository memberRepo) {
		super();
		this.memberRepo = memberRepo;
	}

	@Override
	/*
	 * Est appelée à chaque connexion utilisateur
	 * username : login saisi par l'utilisateur
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDetails user =null;
		
		if(!memberRepo.getByUserName(username).isEmpty()) {
			member = memberRepo.getByUserName(username).get();
			
			 	user = User.builder()
			 		.username(member.getUserName())
			 		.password(member.getPassword())
			 		//.roles()
			 		.build();
			 	//logger.debug(user.toString());
				return user;
		}
		throw new UsernameNotFoundException(username + " not found.");
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
}
