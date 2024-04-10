package com.shuking.questionService.controller.inner;

import com.shuking.model.entity.Question;
import com.shuking.model.entity.QuestionSubmit;
import com.shuking.questionService.service.QuestionService;
import com.shuking.questionService.service.QuestionSubmitService;
import com.shuking.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    /**
     * 通过id获取问题
     *
     * @param questionId id
     * @return 问题对象
     */
    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    /**
     * 通过id获取提交记录
     *
     * @param questionSubmitId id
     * @return 提交记录对象
     */
    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    /**
     * 更新提交记录状态
     *
     * @param questionSubmit 提交对象
     * @return 是否成功
     */
    @PostMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }
}