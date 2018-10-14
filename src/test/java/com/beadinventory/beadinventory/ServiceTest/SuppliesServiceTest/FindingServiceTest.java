package com.beadinventory.beadinventory.ServiceTest.SuppliesServiceTest;

import com.beadinventory.beadinventory.Domain.Supplies.Finding;
import com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.*;
import com.beadinventory.beadinventory.Repository.SuppliesRepos.FindingRepo;
import com.beadinventory.beadinventory.Service.SuppliesServices.FindingService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

import static com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.FindingCategory.*;
import static com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.Material.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
public class FindingServiceTest {

    @InjectMocks
    private FindingService mockFindingService;

    @Mock
    private FindingRepo mockFindingRepo;

    private TreeSet<String> brands;
    private Finding eyePin;
    private Finding headPin;
    private Finding lobsterClasp;
    private Finding lobsterClasp2;
    private Finding splitRing;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        brands = new TreeSet<>(Arrays.asList("Beadalon","bead landing"));

        eyePin = new Finding(EYE_PIN, BRIGHT_SILVER_PLATED,"thin",5.08,5.08,20,brands);
        headPin = new Finding(HEAD_PIN, BRIGHT_SILVER_PLATED,"thin",5.08,5.08,20,brands);
        lobsterClasp = new Finding(LOBSTER_CLASP,BRIGHT_SILVER_PLATED,"small",2,0.1,10,brands);
        lobsterClasp2 = new Finding(LOBSTER_CLASP,DULL_SILVER_PLATED,"medium",4,.1,10,brands);
        splitRing = new Finding(SPLIT_RING,BRASS,"smallest",1,.01,25,brands);
    }

    @Test
    public void getAllFindingsTest(){
        List<Finding> list = new ArrayList<>(Arrays.asList(eyePin,headPin,lobsterClasp,splitRing));
        given(mockFindingRepo.findAll()).willReturn(list);

        ResponseEntity<List<Finding>> expected = new ResponseEntity<>(list,OK);
        ResponseEntity<List<Finding>> actual = mockFindingService.getAllFindings();

        verify(mockFindingRepo).findAll();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getAllOfCategoryTest(){
        List<Finding> list = new ArrayList<>(Arrays.asList(eyePin));
        given(mockFindingRepo.findFindingsByCategory(EYE_PIN)).willReturn(list);

        ResponseEntity<List<Finding>> expected = new ResponseEntity<>(list,OK);
        ResponseEntity<List<Finding>> actual = mockFindingService.getAllOfCategory(EYE_PIN);

        verify(mockFindingRepo).findFindingsByCategory(any(FindingCategory.class));
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getAllOfMaterialTest(){
        List<Finding> list = new ArrayList<>(Arrays.asList(eyePin,headPin,lobsterClasp));
        given(mockFindingRepo.findFindingsByMaterial(BRIGHT_SILVER_PLATED)).willReturn(list);

        ResponseEntity<List<Finding>> expected = new ResponseEntity<>(list,OK);
        ResponseEntity<List<Finding>> actual = mockFindingService.getAllOfMaterial(BRIGHT_SILVER_PLATED);

        verify(mockFindingRepo).findFindingsByMaterial(any(Material.class));
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getAllOfCategoryAndMaterialTest(){
        List<Finding> list = new ArrayList<>(Arrays.asList(eyePin));
        given(mockFindingRepo.findFindingsByCategoryAndMaterial(EYE_PIN,BRIGHT_SILVER_PLATED)).willReturn(list);

        ResponseEntity<List<Finding>> expected = new ResponseEntity<>(list,OK);
        ResponseEntity<List<Finding>> actual = mockFindingService.getAllOfCategoryAndMaterial(EYE_PIN,BRIGHT_SILVER_PLATED);

        verify(mockFindingRepo).findFindingsByCategoryAndMaterial(any(FindingCategory.class),any(Material.class));
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void findByIdTest(){
        eyePin.setId(1L);
        given(mockFindingRepo.findById(anyLong())).willReturn(eyePin);

        ResponseEntity<Finding> expected = new ResponseEntity<>(eyePin,OK);
        ResponseEntity<Finding> actual = mockFindingService.findById(eyePin.getId());

        verify(mockFindingRepo).findById(anyLong());
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void createFindingTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        URI newAccountUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(eyePin.getId())
                .toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(newAccountUri);
        given(mockFindingRepo.save(eyePin)).willReturn(eyePin);

        ResponseEntity<Finding> expected = new ResponseEntity<>(eyePin,responseHeaders,CREATED);
        ResponseEntity<Finding> actual = mockFindingService.createFinding(eyePin);

        verify(mockFindingRepo).save(any(Finding.class));
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void updateFindingQuantityTest(){
        given(mockFindingRepo.findById(anyLong())).willReturn(headPin);
        given(mockFindingRepo.save(any(Finding.class))).willReturn(headPin);

        ResponseEntity<Finding> expected = new ResponseEntity<>(headPin,OK);
        ResponseEntity<Finding> actual = mockFindingService.updateFindingQuantity(headPin.getId(),15);

        verify(mockFindingRepo).findById(anyLong());
        verify(mockFindingRepo).save(any(Finding.class));
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void updateFindingTest(){
        given(mockFindingRepo.save(any(Finding.class))).willReturn(headPin);

        ResponseEntity<Finding> expected = new ResponseEntity<>(headPin,OK);
        ResponseEntity<Finding> actual = mockFindingService.updateFinding(headPin.getId(),headPin);

        verify(mockFindingRepo).save(any(Finding.class));
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void deleteFindingTest(){
        ResponseEntity expected = new ResponseEntity(OK);
        ResponseEntity actual = mockFindingService.deleteById(headPin.getId());

        verify(mockFindingRepo).deleteById(anyLong());
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void deleteFindingByIdTest(){
        ResponseEntity expected = new ResponseEntity(OK);
        ResponseEntity actual = mockFindingService.deleteFinding(lobsterClasp2);

        verify(mockFindingRepo).delete(any(Finding.class));
        Assert.assertEquals(expected,actual);
    }
}
