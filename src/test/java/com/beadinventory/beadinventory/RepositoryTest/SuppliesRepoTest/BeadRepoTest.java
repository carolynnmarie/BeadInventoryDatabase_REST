package com.beadinventory.beadinventory.RepositoryTest.SuppliesRepoTest;


import com.beadinventory.beadinventory.Domain.Supplies.Bead;
import com.beadinventory.beadinventory.Repository.SuppliesRepos.BeadRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.Material.*;
import static com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.Shape.OVAL;
import static com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.Shape.RONDELLE;
import static com.beadinventory.beadinventory.Domain.Supplies.SupplyEnums.Shape.ROUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class BeadRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BeadRepo beadRepo;

    TreeSet<String> brands = new TreeSet<>(Arrays.asList("Bead Gallery"));
    private Bead bead1 = new Bead(AMETHYST, ROUND,"purple",4,"good",20,
            "translucent purple with some white", 0.2,brands);
    private Bead bead2 = new Bead(JASPER,ROUND, "black", 4, "good", 10, "",
            0.1,brands);
    private Bead bead3  = new Bead(STONE, RONDELLE,"tan",6,"ok",7,
            "with design cut into bead",0.05, brands);
    private Bead bead4 = new Bead(AMETHYST, ROUND,"purple",6,"good",15,
            "translucent purple with some white", 0.2,brands);
    private Bead bead5 = new Bead(AMETHYST, ROUND,"light purple",4,"poor",10,
            "translucent purple with some white", 0.2,brands);


    @Test
    public void findBeadsByMaterialTest() {
        entityManager.persist(bead1);
        entityManager.persist(bead2);
        entityManager.persist(bead3);
        entityManager.persist(bead4);
        entityManager.persist(bead5);
        entityManager.flush();

        List<Bead> list = beadRepo.findByMaterial(AMETHYST);

        assertThat(list).containsExactly(bead1,bead4,bead5);
    }

    @Test
    public void findBeadsByMaterialAndColor() {
        entityManager.persist(bead1);
        entityManager.persist(bead2);
        entityManager.persist(bead3);
        entityManager.persist(bead4);
        entityManager.persist(bead5);
        entityManager.flush();

        List<Bead> list = beadRepo.findByMaterialAndColor(AMETHYST, "purple");
        assertThat(list).containsExactly(bead1,bead4);

    }

    @Test
    public void findBeadsByMaterialAndSize() {
        entityManager.persist(bead1);
        entityManager.persist(bead2);
        entityManager.persist(bead3);
        entityManager.persist(bead4);
        entityManager.persist(bead5);
        entityManager.flush();

        List<Bead> list = beadRepo.findByMaterialAndSize(JASPER,4);
        assertThat(list).containsExactly(bead2);
    }

    @Test
    public void findBeadsByShape() {
        entityManager.persist(bead1);
        entityManager.persist(bead2);
        entityManager.persist(bead3);
        entityManager.persist(bead4);
        entityManager.persist(bead5);
        entityManager.flush();

        List<Bead> list = beadRepo.findByShape(ROUND);
        assertThat(list).containsExactly(bead1,bead2,bead4,bead5);
    }

    @Test
    public void findByQuantityIsLessThan() {
        entityManager.persist(bead1);
        entityManager.persist(bead2);
        entityManager.persist(bead3);
        entityManager.persist(bead4);
        entityManager.persist(bead5);
        entityManager.flush();

        List<Bead> list = beadRepo.findByQuantityIsLessThan(12);
        assertThat(list).containsExactly(bead2,bead3,bead5);

    }
}