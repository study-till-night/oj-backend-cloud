package com.shuking.judgeService.controller.inner;


import com.shuking.judgeService.JudgeService;
import com.shuking.model.vo.QuestionSubmitVO;
import com.shuking.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 该服务仅内部调用，不是给前端的
 */
@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient{

    @Resource
    private JudgeService judgeService;

    /**
     * 判题
     *
     * @param submitId  提交记录id
     * @return  提交记录VO对象
     */
    @Override
    public QuestionSubmitVO doJudge(@RequestParam("questionSubmitId") Long submitId) {
        return judgeService.doJudge(submitId);
    }
}
