package com.hhproject.web.service;


import com.hhproject.web.exceptions.CustomExeption;
import com.hhproject.web.model.Album;
import com.hhproject.web.respository.AlbumRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlbumService {

    final private Album album;
    final private AlbumRepository albumRepository;

    @Transactional(readOnly = true)
    public List<Album> getAllAlbums() {
        return albumRepository.findAll().stream().collect(Collectors.toList());
    }


    public int save(Album album) {
            try {
                albumRepository.save(album);
            }catch (Exception e){
                new CustomExeption("Some fields connot be blank");
                return 0;
            }
            return 1;
    }
    @Transactional(readOnly = true)
    public Optional<Album> getAlbumById(long idUP) {
        return albumRepository.findById(idUP);
    }

    public void update(long id, Album album) {
        albumRepository.findById(id).ifPresent(u ->{
            album.setId(u.getId());
            albumRepository.save(album);
        });
    }


    public void deleteAlbum(long id) {
        albumRepository.findById(id).ifPresent(u->{
            albumRepository.delete(u);
        });
    }
}
