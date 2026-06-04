package moe.prashast.repository;

import moe.prashast.entity.MstImpairmentQuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MstImpairmentQuestionOptionRepo extends JpaRepository<MstImpairmentQuestionOption,Integer> {
    List<MstImpairmentQuestionOption> findByQuestionIdIn(List<Integer> questionIds);
}
