package moe.prashast.service;

import moe.prashast.dto.ImpairmentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Part2QuestionService {
    List<ImpairmentDto> getImpairmentData(List<Integer> ids);
}
