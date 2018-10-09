package com.segi.project.monitor.job.controller;

import com.segi.common.utils.poi.ExcelUtil;
import com.segi.framework.aspectj.lang.annotation.Log;
import com.segi.framework.aspectj.lang.enums.BusinessType;
import com.segi.framework.web.controller.BaseController;
import com.segi.framework.web.domain.AjaxResult;
import com.segi.framework.web.page.TableDataInfo;
import com.segi.project.monitor.job.domain.Job;
import com.segi.project.monitor.job.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调度任务信息操作处理
 * 
 * @author tianshuo
 */
@Controller
@RequestMapping("/monitor/job")
public class JobController extends BaseController
{
    private String prefix = "monitor/job";

    @Autowired
    private IJobService jobService;

    @GetMapping()
    public String job()
    {
        return prefix + "/job";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Job job)
    {
        startPage();
        List<Job> list = jobService.selectJobList(job);
        return getDataTable(list);
    }

    @Log(title = "定时任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Job job)
    {
        List<Job> list = jobService.selectJobList(job);
        ExcelUtil<Job> util = new ExcelUtil<Job>(Job.class);
        return util.exportExcel(list, "job");
    }

    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            jobService.deleteJobByIds(ids);
            return success();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return error(e.getMessage());
        }
    }

    /**
     * 任务调度状态修改
     */
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Job job)
    {
        return toAjax(jobService.changeStatus(job));
    }

    /**
     * 任务调度立即执行一次
     */
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PostMapping("/run")
    @ResponseBody
    public AjaxResult run(Job job)
    {
        return toAjax(jobService.run(job));
    }

    /**
     * 新增调度
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存调度
     */
    @Log(title = "定时任务", businessType = BusinessType.INSERT)
    //@RequiresPermissions("monitor:job:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Job job)
    {
        return toAjax(jobService.insertJobCron(job));
    }

    /**
     * 修改调度
     */
    @GetMapping("/edit/{jobId}")
    public String edit(@PathVariable("jobId") Long jobId, ModelMap mmap)
    {
        mmap.put("job", jobService.selectJobById(jobId));
        return prefix + "/edit";
    }

    /**
     * 修改保存调度
     */
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    //@RequiresPermissions("monitor:job:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Job job)
    {
        return toAjax(jobService.updateJobCron(job));
    }
}
