package com.shuking.service;


import com.shuking.model.entity.Question;
import com.shuking.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "questionService", path = "/api/question/inner")
@Component
public interface QuestionFeignClient {
    /**
     * 通过id获取问题
     *
     * @param questionId id
     * @return 问题对象
     */
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    /**
     * 通过id获取提交记录
     *
     * @param questionSubmitId id
     * @return 提交记录对象
     */
    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionSubmitId);


    /**
     * 更新提交记录状态
     *
     * @param questionSubmit 提交对象
     * @return 是否成功
     */
    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);
}