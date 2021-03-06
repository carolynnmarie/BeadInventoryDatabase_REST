package com.beadinventory.beadinventory.Controller.FinishedPiecesControllers;

import com.beadinventory.beadinventory.Domain.FinishedPieces.Bookmark;
import com.beadinventory.beadinventory.Service.FinishedPiecesServices.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/bookmarks")
public class BookmarkController implements AllFinishedPiecesContInterface<Bookmark> {

    private BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService){
        this.bookmarkService = bookmarkService;
    }

    @Override
    @GetMapping()
    public List<Bookmark> findAllItems() {
        return bookmarkService.getAllItems().getBody();
    }

    @Override
    @GetMapping(value = "/{id}")
    public Bookmark findItemById(@PathVariable("id") long id) {
        return bookmarkService.getItemById(id).getBody();
    }

    @Override
    @PostMapping()
    public ResponseEntity<Bookmark> createItem(@RequestBody Bookmark item) {
        return bookmarkService.createItem(item);
    }

    @Override
    @PutMapping(value = "/{id}")
    public Bookmark updateItem(@PathVariable("id") long id, @RequestBody Bookmark item) {
        return bookmarkService.updateItem(id,item).getBody();
    }

    @Override
    @PutMapping(params = "price")
    public List<Bookmark> increaseAllPrices(@RequestAttribute(value = "price") double price) {
        return bookmarkService.increasePriceOfAll(price).getBody();
    }

    @Override
    @PutMapping(value = "/{id}", params = "description")
    public Bookmark updateDescription(@PathVariable("id") long id, @RequestAttribute(value = "description") String description) {
        return bookmarkService.updateDescription(id,description).getBody();
    }

    @Override
    @DeleteMapping()
    public ResponseEntity deleteItem(@RequestBody Bookmark item) {
        return bookmarkService.deleteItem(item);
    }


}
/*

 */