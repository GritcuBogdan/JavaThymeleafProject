package org.example.service;


import org.example.domain.OutsourcedPart;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface OutsourcedPartService {
    public List<OutsourcedPart> findAll();
    public OutsourcedPart findById(int theId);
    public void save (OutsourcedPart thePart);
    public void deleteById(int theId);
}

