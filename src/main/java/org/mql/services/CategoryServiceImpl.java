package org.mql.services;

import java.util.List;

import org.mql.dao.CategoryRepository;
import org.mql.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	
	@Override
	public boolean existsById(Integer id) {
		return categoryRepository.existsById(id);
	}
}
