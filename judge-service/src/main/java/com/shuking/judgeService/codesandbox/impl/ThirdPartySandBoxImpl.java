package com.shuking.judgeService.codesandbox.impl;

import com.shuking.judgeService.codesandbox.CodeSandBox;
import com.shuking.model.codesandbox.ExecuteCodeRequest;
import com.shuking.model.codesandbox.ExecuteCodeResponse;
import com.shuking.model.codesandbox.JudgeInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 调用第三方代码沙箱
 */
@Component("thirdPartySandBox")
public class ThirdPartySandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        System.out.println("third");
        return new ExecuteCodeResponse(new ArrayList<String>(),"",0,new JudgeInfo());
    }
}
