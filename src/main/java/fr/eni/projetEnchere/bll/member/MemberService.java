package fr.eni.projetEnchere.bll.member;


import fr.eni.projetEnchere.bll.CRUDService;
import fr.eni.projetEnchere.bo.Member;
import fr.eni.projetEnchere.exception.UserNameAlreadyExistsException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public interface MemberService extends CRUDService<Member>{
	public Member getByUserName(String userName);
//	public void save(@Valid Member member, Member loggedMember) throws UserNameAlreadyExistsException;
	Member save(Member member, Member loggedMember) throws UserNameAlreadyExistsException;
}
