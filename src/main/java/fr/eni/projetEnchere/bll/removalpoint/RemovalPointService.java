package fr.eni.projetEnchere.bll.removalpoint;

import java.util.List;

import fr.eni.projetEnchere.bll.CRUDService;
import fr.eni.projetEnchere.bo.RemovalPoint;

public interface RemovalPointService extends CRUDService<RemovalPoint>{

	List<RemovalPoint> getAllByMemberId(int idMember);

}
