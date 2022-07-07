package com.ncc.repository;

import com.ncc.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color,Integer> {
    List<Color> getColorByStatusTrue();
}
