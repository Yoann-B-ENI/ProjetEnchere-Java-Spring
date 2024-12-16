package fr.eni.projetEnchere.bll;

import java.util.List;
import java.util.Optional;

public interface CRUDService<T> {
		public void create();
		public List<T> getAll();
		public Optional<T> getById(int id);
		public void update(T t);
		public void delete(int id);

}
