package dev.vality.cm.repository;

import dev.vality.cm.model.ClaimModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<ClaimModel, Long>, JpaSpecificationExecutor<ClaimModel> {

}
