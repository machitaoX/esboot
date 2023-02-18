package com.mct.esboot.webapi;

import com.mct.esboot.demo.Demo1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @program: esboot
 * @description:
 * @author: machitao
 * @create: 2023-02-18 00:48
 **/

@RestController
@RequestMapping("/v1/demo")
public class Demo1Controller {

    @Autowired
    private Demo1 demo1;

    @GetMapping("/demo_1")
    public String get(HttpServletRequest request, String field, String val) throws IOException {
        demo1.testAddData();
        return demo1.testSearch(field, val).toString();
    }

    @GetMapping("/demo_1_delete")
    public String delete() throws IOException {
        demo1.testDeleteBeforeOptimize();
        return "ok";
    }
}
