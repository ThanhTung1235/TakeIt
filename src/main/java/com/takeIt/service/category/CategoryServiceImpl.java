package com.takeIt.service.category;

import com.takeIt.entity.Account;
import com.takeIt.entity.Category;
import com.takeIt.repository.AccountRepository;
import com.takeIt.repository.CategoryRepository;
import com.takeIt.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category getCategory(long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
