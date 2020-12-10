package com.hhproject.web.respository;


import com.hhproject.web.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
