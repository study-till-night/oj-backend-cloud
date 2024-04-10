package com.shuking.judgeService.codesandbox;

import com.shuking.model.codesandbox.ExecuteCodeRequest;
import com.shuking.model.codesandbox.ExecuteCodeResponse;

@FunctionalInterface
public interface CodeSandBox {
    // 使用接口定义执行方法提高通用性
    ExecuteCodeResponse executeCode(ExecuteCodeRequest request);
}
