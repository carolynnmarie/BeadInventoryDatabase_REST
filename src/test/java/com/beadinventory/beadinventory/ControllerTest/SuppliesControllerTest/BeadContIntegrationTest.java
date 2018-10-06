package com.beadinventory.beadinventory.ControllerTest.SuppliesControllerTest;

import com.beadinventory.beadinventory.Controller.SuppliesControllers.BeadController;
import com.beadinventory.beadinventory.Domain.Supplies.Bead;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

import static com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.Material.*;
import static com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.Shape.ROUND;
import static org.springframework.http.HttpStatus.*;

@SuppressWarnings("unchecked")
@RunWith(SpringRunner.class)
@WebMvcTest(BeadController.class)
public class BeadContIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeadController mockBeadController;

    TreeSet<String> brands = new TreeSet<>(Arrays.asList("Bead Gallery"));
    private Bead bead1 = new Bead(AMETHYST, ROUND, "purple", 4, "good", 20,
            "translucent purple with some white", 0.2, brands);
    private Bead bead2 = new Bead(JASPER, ROUND, "black", 4, "good", 10, "",
            0.1, brands);
    private Bead bead3 = new Bead(STONE, ROUND, "tan", 6, "ok", 7,
            "with design cut into bead", 0.05, brands);
    private Bead bead4 = new Bead(AMETHYST, ROUND,"purple",6,"good",15,"translucent purple with some white",
            0.2,brands);
    private Bead bead5 = new Bead(AMETHYST, ROUND,"purple",4,"poor",10,"translucent purple with some white",
            0.2,brands);

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp(){
        bead1.setId(1L);
    }

    @Test
    public void findAllBeadsIntegTest() throws Exception{
        List<Bead> list = new ArrayList<>(Arrays.asList(bead1,bead2,bead3,bead4,bead5));
        ResponseEntity<List<Bead>> expected = new ResponseEntity<>(list, OK);
        given(mockBeadController.findAllBeads()).willReturn(expected);

        mockMvc.perform(get("/beads")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllOfMaterialIntegTest() throws Exception{
        List<Bead> list = new ArrayList<>(Arrays.asList(bead1,bead4,bead5));
        ResponseEntity<List<Bead>> expected = new ResponseEntity<>(list,OK);
        given(mockBeadController.findAllOfMaterial(AMETHYST)).willReturn(expected);

        mockMvc.perform(get("/beads")
                .param("material",AMETHYST.toString())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllOfMaterialAndColorIntegTest() throws Exception{
        List<Bead> list = new ArrayList<>(Arrays.asList(bead1,bead4,bead5));
        ResponseEntity<List<Bead>> expected = new ResponseEntity<>(list,OK);
        given(mockBeadController.findAllOfMaterialAndColor(AMETHYST,"purple")).willReturn(expected);

        mockMvc.perform(get("/beads")
                .param("material",AMETHYST.toString())
                .param("color","purple")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllOfMaterialAndSizeIntegTest() throws Exception{
        List<Bead> list = new ArrayList<>(Arrays.asList(bead1,bead5));
        ResponseEntity<List<Bead>> expected = new ResponseEntity<>(list,OK);
        given(mockBeadController.findAllOfMaterialAndSize(AMETHYST,4)).willReturn(expected);

        mockMvc.perform(get("/beads")
                .param("material",AMETHYST.toString())
                .param("size", String.valueOf(4))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllWithQuantityLessThanIntegTest() throws Exception{
        List<Bead> list = new ArrayList<>(Arrays.asList(bead2,bead3,bead5));
        ResponseEntity<List<Bead>> expected = new ResponseEntity<>(list,OK);
        given(mockBeadController.findAllWithQuantityLessThan(12L)).willReturn(expected);

        mockMvc.perform(get("/beads")
                .param("quantity",String.valueOf(12L))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getBeadByIdIntegTest() throws Exception{
        Optional<Bead> oBead = Optional.of(bead1);
        ResponseEntity<Optional<Bead>> expected = new ResponseEntity<>(oBead,OK);
        given(mockBeadController.findBeadById(1L)).willReturn(expected);

        mockMvc.perform(get("/beads/{id}",1L)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateBeadIntegTest() throws Exception{
        ResponseEntity<Bead> expected = new ResponseEntity<>(bead1,OK);
        given(mockBeadController.updateBead(bead1.getId(),bead1)).willReturn(expected);

        String body = mapper.writeValueAsString(bead1);
        mockMvc.perform(put("/beads/{id}",bead1.getId())
                .content(body)
                .characterEncoding("utf-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createBeadIntegTest() throws Exception{
        ResponseEntity<Bead> expected = new ResponseEntity<>(bead1,CREATED);
        given(mockBeadController.createBead(bead1)).willReturn(mock(ResponseEntity.class));

        String body = mapper.writeValueAsString(bead1);
        mockMvc.perform(post("/beads")
                .content(body)
                .characterEncoding("utf-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBeadByIdIntegTest() throws Exception{
        given(mockBeadController.deleteBeadById(bead1.getId())).willReturn(new ResponseEntity(OK));

        mockMvc.perform(delete("/beads/{id}",bead1.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBeadIntegTest() throws Exception{
        given(mockBeadController.deleteBead(bead1)).willReturn(new ResponseEntity(OK));

        String body = mapper.writeValueAsString(bead1);
        mockMvc.perform(delete("/beads")
                .content(body)
                .characterEncoding("utf-8")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}