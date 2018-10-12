package com.beadinventory.beadinventory.Repository.FinishedPiecesRepos;

import com.beadinventory.beadinventory.Domain.FinishedPieces.Necklace;
import com.beadinventory.beadinventory.Domain.Supplies.StringWire;
import com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.StringWireCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NecklaceRepo extends AllFinishedPiecesRepo<Necklace> {
//
//    List<Necklace> findAllByStringingMaterial(StringWire stringWire);

    List<Necklace> findAllByStringWireCategory(StringWireCategory category);

    List<Necklace> findNecklacesByHasNaturalStonesTrue();

    List<Necklace> findNecklacesByHasSwarovskiTrue();
}
