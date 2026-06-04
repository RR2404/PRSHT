package moe.prashast.repository;

import moe.prashast.entity.MstImpairmentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MstImpairmentQuestionRepo extends JpaRepository<MstImpairmentQuestion,Integer> {
    List<MstImpairmentQuestion> findByImpairmentIdIn(List<Integer> impairmentIds);
}
