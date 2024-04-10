package com.shuking.judgeService;

import cn.hutool.json.JSONUtil;
import com.shuking.common.common.ErrorCode;
import com.shuking.common.exception.ThrowUtils;
import com.shuking.judgeService.codesandbox.CodeSandBoxProxy;
import com.shuking.judgeService.strategy.JudgeContext;
import com.shuking.model.codesandbox.ExecuteCodeRequest;
import com.shuking.model.codesandbox.ExecuteCodeResponse;
import com.shuking.model.codesandbox.JudgeInfo;
import com.shuking.model.dto.question.JudgeCase;
import com.shuking.model.entity.Question;
import com.shuking.model.entity.QuestionSubmit;
import com.shuking.model.enums.QuestionSubmitStatusEnum;
import com.shuking.model.vo.QuestionSubmitVO;
import com.shuking.service.QuestionFeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private CodeSandBoxProxy sandBoxProxy;

    @Resource
    private QuestionFeignClient questionFeignClient;

    /**
     * 执行判题逻辑
     *
     * @param submitId 执行判题的提交记录id
     * @return 提交记录VO
     */
    @Override
    @Transactional
    public QuestionSubmitVO doJudge(Long submitId) {
        QuestionSubmit submit = questionFeignClient.getQuestionSubmitById(submitId);

        String language = submit.getLanguage();
        String code = submit.getCode();
        Integer submitStatus = submit.getStatus();
        Long questionId = submit.getQuestionId();

        ThrowUtils.throwIf(questionId == null, ErrorCode.NOT_FOUND_ERROR, "提交记录不存在");
        ThrowUtils.throwIf(!submitStatus.equals(QuestionSubmitStatusEnum.WAITING.getValue()), ErrorCode.OPERATION_ERROR, "不得重复提交");
        // 设置提交状态为判题中
        submit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());

        // 将记录状态更改为判题中
        ThrowUtils.throwIf(!questionFeignClient.updateQuestionSubmitById(submit),
                ErrorCode.SYSTEM_ERROR, "更新提交记录状态失败");

        // 得到题目测试用例list
        Question question = questionFeignClient.getQuestionById(questionId);
        List<JudgeCase> caseList = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);

        // 执行沙箱得到结果
        List<String> inputList = caseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeResponse executeCodeResponse = sandBoxProxy.executeCode(ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputs(inputList).build());

        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();

        // 封装判题逻辑上下文
        JudgeContext judgeContext = JudgeContext.builder()
                .judgeInfo(judgeInfo).judgeCaseList(caseList)
                .inputList(inputList).outputList(outputList)
                .questionSubmit(submit).question(question).build();

        // 得到执行结果 是AC还是WA还是TLE...
        JudgeManager judgeManager = new JudgeManager();
        JudgeInfo judgeRes = judgeManager.doJudge(judgeContext);

        // 将记录状态更改为成功   并更新答题结果
        submit.setJudgeInfo(JSONUtil.toJsonStr(judgeRes));
        submit.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());

        ThrowUtils.throwIf(!questionFeignClient.updateQuestionSubmitById(submit),
                ErrorCode.SYSTEM_ERROR, "更新提交记录状态失败");

        return QuestionSubmitVO.objToVo(submit);
    }
}