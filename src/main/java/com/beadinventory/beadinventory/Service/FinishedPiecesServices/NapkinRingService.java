package com.beadinventory.beadinventory.Service.FinishedPiecesServices;

import com.beadinventory.beadinventory.Domain.FinishedPieces.NapkinRing;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NapkinRingService extends AllFinishedPiecesService<NapkinRing> {
    @Override
    public ResponseEntity<List<NapkinRing>> getAllItems() {
        return null;
    }

    @Override
    public ResponseEntity<NapkinRing> getItemById(long id) {
        return null;
    }

    public long getQuantity(long id) {
        return 0;
    }

    @Override
    public ResponseEntity<NapkinRing> createItem(NapkinRing item) {
        return null;
    }

    @Override
    public ResponseEntity<NapkinRing> updateItem(long id, NapkinRing item) {
        return null;
    }

    @Override
    public ResponseEntity<NapkinRing> updatePriceOfOne(long id, double price) {
        return null;
    }

    @Override
    public ResponseEntity<NapkinRing> updateDescription(long id, String description) {
        return null;
    }

    @Override
    public ResponseEntity deleteItem(NapkinRing item) {
        return null;
    }
}
