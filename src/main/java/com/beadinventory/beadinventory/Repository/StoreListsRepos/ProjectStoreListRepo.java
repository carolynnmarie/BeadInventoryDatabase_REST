package com.beadinventory.beadinventory.Repository.StoreListsRepos;

import com.beadinventory.beadinventory.Domain.StoreLists.ProjectStoreList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStoreListRepo extends CrudRepository<ProjectStoreList, Long> {
}
