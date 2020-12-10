package com.hhproject.web.controller;


import com.hhproject.web.model.Album;
import com.hhproject.web.respository.AlbumRepository;
import com.hhproject.web.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/album")
@AllArgsConstructor
public class AlbumController {

    final private AlbumRepository albumRepository;
    final private AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums(){
        return ResponseEntity.status(HttpStatus.OK).body(albumService.getAllAlbums());
    }

    /*@PostMapping
        public ResponseEntity<String> createAlbum(@RequestBody Album album) {
        System.out.println(albumService.save(album));
        if(albumService.save(album)==1){
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        }else return ResponseEntity.status(HttpStatus.OK).body("Some fields must be filled");
    }*/

    @GetMapping (path = "{id}")
    public ResponseEntity<Optional<Album>> getAlbumById(@PathVariable(name = "id") long idUP){
        return ResponseEntity.status(HttpStatus.OK).body(albumService.getAlbumById(idUP));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Void> updateAlbum(@RequestBody Album album, @PathVariable(name = "id") long id) {
        albumService.update(id,album);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAlbum(@PathVariable("id") long id){
        albumService.deleteAlbum(id);
        return ResponseEntity.status(HttpStatus.OK).body("Album deleted");
    }

}
