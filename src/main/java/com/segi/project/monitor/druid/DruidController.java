package com.segi.project.monitor.druid;

import com.segi.framework.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * druid 监控
 * 
 * @author tianshuo
 */
@Controller
@RequestMapping("/monitor/data")
public class DruidController extends BaseController
{
    private String prefix = "/monitor/druid";

    @GetMapping()
    public String index()
    {
        return redirect(prefix + "/index");
    }
}
