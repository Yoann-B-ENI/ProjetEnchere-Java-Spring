package fr.eni.projetEnchere.bll.removalpoint;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eni.projetEnchere.bo.RemovalPoint;
import fr.eni.projetEnchere.dal.removalpoint.RemovalPointRepository;

@Service
public class RemovalPointServiceImpl implements RemovalPointService{
	
	private RemovalPointRepository removalPointRepository;
	
	@Autowired
	public RemovalPointServiceImpl(RemovalPointRepository removalPointRepository) {
		super();
		this.removalPointRepository = removalPointRepository;
	}

	@Override
	public void create(RemovalPoint t) {
		this.removalPointRepository.create(t);
	}

	@Override
	public List<RemovalPoint> getAll() {
		return this.removalPointRepository.getAll();
	}

	@Override
	public RemovalPoint getById(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
	}

	@Override
	public void update(RemovalPoint t) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

	@Override
	public List<RemovalPoint> getAllByMemberId(int idMember) {
		return this.removalPointRepository.getAllByMemberId(idMember);
	}


}
