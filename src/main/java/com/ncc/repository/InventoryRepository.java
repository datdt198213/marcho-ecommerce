package com.ncc.repository;

import com.ncc.model.Color;
import com.ncc.model.Inventory;
import com.ncc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> getInventoryByProductAndColor (Product product, Color color);
}
