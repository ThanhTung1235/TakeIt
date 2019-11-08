package com.takeIt.service.category;

import com.takeIt.entity.Account;
import com.takeIt.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Category getCategory(long id);

    Page<Category> categories(int page, int limit);
}