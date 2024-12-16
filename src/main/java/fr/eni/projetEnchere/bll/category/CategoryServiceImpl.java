package fr.eni.projetEnchere.bll.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eni.projetEnchere.bo.Category;
import fr.eni.projetEnchere.dal.category.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	private CategoryRepository categoryRepo;
	
	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepo) {
		super();
		this.categoryRepo = categoryRepo;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

	@Override
	public List<Category> getAll() {
		return this.categoryRepo.getAll();
	}

	@Override
	public Optional<Category> getById(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
	}

	@Override
	public void update(Category t) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented yet");
		
	}

}
