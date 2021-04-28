package com.abdel.todo.repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.abdel.todo.domain.ToDo;

@Repository
public class ToDoRepository implements CommonRepository<ToDo> {
	
	private Map<String,ToDo> toDos = new HashMap<>();
	
	@Override
	public ToDo save(ToDo domain) {
		// TODO Auto-generated method stub
		ToDo result = toDos.get(domain.getId());
		if(result != null) {
			result.setModified(domain.getModified());
			result.setCompleted(domain.isCompleted());
			result.setDescription(domain.getDescription());
			
			domain = result;
		}
		 toDos.put(domain.getId(), domain);
		 
		 return toDos.get(domain.getId());
	}

	@Override
	public Iterable<ToDo> save(Collection<ToDo> domains) {
		
		domains.forEach(this::save);
		return findAll();
	}

	@Override
	public void delete(ToDo domain) {
		// TODO Auto-generated method stub
		toDos.remove(domain.getId());
	}

	@Override
	public ToDo findById(String id) {
		// TODO Auto-generated method stub
		return toDos.get(id);
	}

	@Override
	public Iterable<ToDo> findAll() {
		// TODO Auto-generated method stub
		return toDos.entrySet().stream().sorted(entryComparator).
				map(Map.Entry::getValue).collect(Collectors.toList());
	}
	
	private Comparator<Map.Entry<String,ToDo>> entryComparator = (Map.
			Entry<String, ToDo> o1, Map.Entry<String, ToDo> o2 ) ->{
				return o1.getValue().getCreated().compareTo(o2.getValue().getCreated());
			};

}
