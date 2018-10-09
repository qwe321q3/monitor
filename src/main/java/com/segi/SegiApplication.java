package com.segi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author tianshuo
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.segi.project.*.*.mapper")
public class SegiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(SegiApplication.class, args);
        System.out.println("monitor启动成功\n" +
                "       (_\\\n" +
                "      / \\\n" +
                " `== / /\\=,_\n" +
                "  ;--==\\\\  \\\\o\n" +
                "  /____//__/__\\\n" +
                "@=`(0)     (0) ");
    }
}