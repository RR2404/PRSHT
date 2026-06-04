package moe.prashast.repository;

import moe.prashast.entity.MstImpairment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MstImpairmentRepo extends JpaRepository<MstImpairment,Integer> {
    List<MstImpairment> findByImpairmentIdIn(List<Integer> impairmentIds);
}
