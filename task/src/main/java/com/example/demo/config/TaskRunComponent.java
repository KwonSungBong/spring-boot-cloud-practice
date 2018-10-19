package com.example.demo.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.listener.annotation.BeforeTask;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TaskRunComponent {

    private static final Log logger = LogFactory.getLog(TaskRunComponent.class);

    @Autowired
    private TaskRunRepository taskRunRepository;

    @BeforeTask
    public void init(TaskExecution taskExecution) {
        String execDate = new SimpleDateFormat().format(new Date());
        taskRunRepository.save(new TaskRunOutput("Executed at " + execDate));
        logger.info("Executed at : " + execDate);
    }

}
