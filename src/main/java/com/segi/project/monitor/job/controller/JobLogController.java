package com.segi.project.monitor.job.controller;

import com.segi.common.utils.poi.ExcelUtil;
import com.segi.framework.aspectj.lang.annotation.Log;
import com.segi.framework.aspectj.lang.enums.BusinessType;
import com.segi.framework.web.controller.BaseController;
import com.segi.framework.web.domain.AjaxResult;
import com.segi.framework.web.page.TableDataInfo;
import com.segi.project.monitor.job.domain.JobLog;
import com.segi.project.monitor.job.service.IJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 调度日志操作处理
 * 
 * @author tianshuo
 */
@Controller
@RequestMapping("/monitor/jobLog")
public class JobLogController extends BaseController
{
    private String prefix = "monitor/job";

    @Autowired
    private IJobLogService jobLogService;

    @GetMapping()
    public String jobLog()
    {
        return prefix + "/jobLog";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(JobLog jobLog)
    {
        startPage();
        List<JobLog> list = jobLogService.selectJobLogList(jobLog);
        return getDataTable(list);
    }

    @Log(title = "调度日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(JobLog jobLog)
    {
        List<JobLog> list = jobLogService.selectJobLogList(jobLog);
        ExcelUtil<JobLog> util = new ExcelUtil<JobLog>(JobLog.class);
        return util.exportExcel(list, "jobLog");
    }

    @Log(title = "调度日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(jobLogService.deleteJobLogByIds(ids));
    }
}
