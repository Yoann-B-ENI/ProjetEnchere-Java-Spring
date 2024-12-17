package fr.eni.projetEnchere.dal.removalpoint;

import java.util.List;

import fr.eni.projetEnchere.bo.RemovalPoint;
import fr.eni.projetEnchere.dal.CRUDRepository;

public interface RemovalPointRepository extends CRUDRepository<RemovalPoint>{

	List<RemovalPoint> getAllByMemberId(int idMember);

}
