package com.beadinventory.beadinventory.ServiceTest.FinishedPiecesServiceTest;

import com.beadinventory.beadinventory.Domain.Supplies.Bead;
import com.beadinventory.beadinventory.Domain.Supplies.Finding;
import com.beadinventory.beadinventory.Domain.FinishedPieces.NapkinRingSet;
import com.beadinventory.beadinventory.Domain.Supplies.StringWire;
import com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.FindingCategory;
import com.beadinventory.beadinventory.Repository.FinishedPiecesRepos.NapkinRingSetRepo;
import com.beadinventory.beadinventory.Service.FinishedPiecesServices.NapkinRingService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.Material.*;
import static com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.Shape.*;
import static com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.StringWireCategory.BEADING_WIRE;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class NapkinRingSetServiceTest {

    @InjectMocks
    NapkinRingService mockService;

    @Mock
    NapkinRingSetRepo mockRepo;

    private Bead bead;
    private Bead bead1;
    private Finding crimp;
    private HashMap<Bead, Integer> beads;
    private HashMap<Finding, Integer> findings;
    private NapkinRingSet napkinRingSet;
    private StringWire beadingWire;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        List<String> brands = new ArrayList<>(Arrays.asList("Bead Gallery"));
        this.bead = new Bead(SEED,SEED_E_LARGE,"black",0,"good",100,"good black large seed beads",.001,brands);
        this.bead1 = new Bead(AMETHYST, ROUND,"purple",4,"good",20,"translucent purple with some white", 0.2,brands);
        this.crimp = new Finding(FindingCategory.CRIMP_BEAD,BRIGHT_SILVER_PLATED,"",.02,.01,10,brands);
        beadingWire = new StringWire(BEADING_WIRE,BRIGHT_SILVER_PLATED,"silver",".5 mm",7,"good",.5,"Beadalon");
        this.beads = new HashMap<>();
        beads.put(bead,25);
        beads.put(bead1,4);
        this.findings = new HashMap<>();
        findings.put(crimp,2);
        this.napkinRingSet = new NapkinRingSet(beads,findings,20,"",beadingWire,"purple and black",4);
    }

    @Test
    public void getQuantityOfItemsInSetTest(){
        given(mockRepo.findById(napkinRingSet.getId())).willReturn(napkinRingSet);

        int expected = 4;
        int actual = mockService.getQuantityOfItemsInSet(napkinRingSet.getId());

        verify(mockRepo).findById(anyLong());
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void updateQuantityOfItemsInSetTest(){
        given(mockRepo.findById(napkinRingSet.getId())).willReturn(napkinRingSet);
        given(mockRepo.save(napkinRingSet)).willReturn(napkinRingSet);

        int expected = 8;
        ResponseEntity<NapkinRingSet> actualResponse = mockService.updateQuantityOfItemsInSet(napkinRingSet.getId(),8);
        int actual = actualResponse.getBody().getQuantity();

        verify(mockRepo).save(any(NapkinRingSet.class));
        verify(mockRepo).findById(anyLong());
        Assert.assertEquals(expected,actual);
    }
}