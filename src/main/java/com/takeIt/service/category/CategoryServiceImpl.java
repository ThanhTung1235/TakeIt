package com.takeIt.service.category;

import com.takeIt.entity.Account;
import com.takeIt.entity.Category;
import com.takeIt.repository.AccountRepository;
import com.takeIt.repository.CategoryRepository;
import com.takeIt.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category getCategory(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Category> categories(int page, int limit) {
        return categoryRepository.findAll(PageRequest.of(page - 1, limit));
    }
}
