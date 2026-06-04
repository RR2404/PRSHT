package moe.prashast.serviceImpl;

import moe.prashast.dto.ImpairmentDto;
import moe.prashast.dto.OptionDto;
import moe.prashast.dto.QuestionDto;
import moe.prashast.entity.MstImpairment;
import moe.prashast.entity.MstImpairmentQuestion;
import moe.prashast.entity.MstImpairmentQuestionOption;
import moe.prashast.repository.MstImpairmentQuestionOptionRepo;
import moe.prashast.repository.MstImpairmentQuestionRepo;
import moe.prashast.repository.MstImpairmentRepo;
import moe.prashast.service.Part2QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class Part2QuestionServiceImpl implements Part2QuestionService {
    @Autowired
    private MstImpairmentRepo mstImpairmentRepo;

    @Autowired
    private MstImpairmentQuestionRepo mstImpairmentQuestionRepo;

    @Autowired
    private MstImpairmentQuestionOptionRepo mstImpairmentQuestionOptionRepo;


    @Override
    public List<ImpairmentDto> getImpairmentData(List<Integer> impairmentIds) {

        List<MstImpairment> impairments = mstImpairmentRepo.findByImpairmentIdIn(impairmentIds);

        List<MstImpairmentQuestion> questions =mstImpairmentQuestionRepo.findByImpairmentIdIn(impairmentIds);

        // collect questionIds
        List<Integer> questionIds = new ArrayList<>();
        for (MstImpairmentQuestion q : questions) {
            questionIds.add(q.getImpairmentQuestionId());
        }

        List<MstImpairmentQuestionOption> options =mstImpairmentQuestionOptionRepo.findByQuestionIdIn(questionIds);

        // 🔥 Map options by questionId
        Map<Integer, List<MstImpairmentQuestionOption>> optionMap = new HashMap<>();

        for (MstImpairmentQuestionOption opt : options) {
            optionMap.computeIfAbsent(opt.getQuestionId(), k -> new ArrayList<>()).add(opt);
        }

        // 🔥 Map questions by impairmentId
        Map<Integer, List<QuestionDto>> questionMap = new HashMap<>();

        for (MstImpairmentQuestion q : questions) {

            QuestionDto qDto = new QuestionDto();
            qDto.setQuestionId(q.getImpairmentQuestionId());
            qDto.setQuestionText(q.getQuestionText());
            qDto.setAnswerType(q.getAnswerType());

            // if MULTI_SELECT → add options
            if ("MULTI_SELECT".equalsIgnoreCase(q.getAnswerType())) {

                List<OptionDto> optionDtos = new ArrayList<>();

                List<MstImpairmentQuestionOption> optList =
                        optionMap.get(q.getImpairmentQuestionId());

                if (optList != null) {
                    for (MstImpairmentQuestionOption o : optList) {
                        OptionDto oDto = new OptionDto();
                        oDto.setOptionCode(o.getOptionCode());
                        oDto.setOptionText(o.getOptionText());
                        optionDtos.add(oDto);
                    }
                }

                qDto.setOptions(optionDtos);
            }

            questionMap.computeIfAbsent(Integer.valueOf(q.getImpairmentId()), k -> new ArrayList<>())
                    .add(qDto);
        }

        // 🔥 Final Response Mapping
        List<ImpairmentDto> result = new ArrayList<>();

        for (MstImpairment imp : impairments) {

            ImpairmentDto dto = new ImpairmentDto();
            dto.setImpairmentId(imp.getImpairmentId());
            dto.setImpairmentName(imp.getImpairmentDesc());

            dto.setQuestions(questionMap.getOrDefault(
                    imp.getImpairmentId(), new ArrayList<>()
            ));

            result.add(dto);
        }

        return result;
    }
}
