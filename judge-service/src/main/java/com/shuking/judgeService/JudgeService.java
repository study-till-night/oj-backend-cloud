package com.shuking.judgeService;


import com.shuking.model.vo.QuestionSubmitVO;

public interface JudgeService {

    QuestionSubmitVO doJudge(Long submitId);
}
