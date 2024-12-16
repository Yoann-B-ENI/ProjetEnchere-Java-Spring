package fr.eni.projetEnchere.dal;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T> {
	public void create();
	public List<T> getAll();
	public Optional<T> getById(int id);
	public void update(T t);
	public void delete(int id);
}
