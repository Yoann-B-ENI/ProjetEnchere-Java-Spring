package fr.eni.projetEnchere.bll.member;


import fr.eni.projetEnchere.bll.CRUDService;
import fr.eni.projetEnchere.bo.Member;
import fr.eni.projetEnchere.exception.UserNameAlreadyExistsException;
import jakarta.servlet.http.HttpSession;

public interface MemberService extends CRUDService<Member>{
	public Member getByUserName(String userName);
	void save(Member member, HttpSession session) throws UserNameAlreadyExistsException;
}
