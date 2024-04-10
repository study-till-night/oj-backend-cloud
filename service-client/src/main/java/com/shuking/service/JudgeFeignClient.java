package com.shuking.service;


import com.shuking.model.vo.QuestionSubmitVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "JudgeService", path = "/api/judge/inner")
@Component
public interface JudgeFeignClient {
    /**
     * 判题
     *
     * @param submitId  提交记录id
     * @return  提交记录VO对象
     */
    @PostMapping("/do")
    QuestionSubmitVO doJudge(@RequestParam("questionSubmitId") Long submitId);
}