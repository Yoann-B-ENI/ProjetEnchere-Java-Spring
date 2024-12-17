package fr.eni.projetEnchere.bll.member;


import fr.eni.projetEnchere.bll.CRUDService;
import fr.eni.projetEnchere.bo.Member;

public interface MemberService extends CRUDService<Member>{
	public Member getByUserName(String userName);
}
