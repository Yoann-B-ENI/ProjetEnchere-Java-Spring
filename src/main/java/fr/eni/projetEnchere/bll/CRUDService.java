package fr.eni.projetEnchere.bll;

import java.util.List;
import java.util.Optional;

import org.postgresql.util.PSQLException;

import fr.eni.projetEnchere.exception.UserNameAlreadyExistsException;

public interface CRUDService<T> {
		public void create(T t);
		public List<T> getAll();
		public T getById(int id);
		public void update(T t);
		public void delete(int id);

}
