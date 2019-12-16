package com.wsm.compose.compose_api.generator;

import com.alibaba.fastjson.JSON;
import com.wsm.compose.compose_base.annotation.LogAnnotation;
import com.wsm.compose.compose_service.generator.GeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Api(tags = {"代码生成器"})
@RequestMapping("/generator")
@RestController
public class GeneratorApi {

    @Autowired
    GeneratorService generatorService;

    @LogAnnotation
    @ApiOperation(value = "查看数据库所有表及结构")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    List<Map<String, Object>> list() {
        List<Map<String, Object>> list = generatorService.list();
        return list;
    }


    @LogAnnotation
    @ApiOperation(value = "生成一张表的代码")
    @RequestMapping(value = "/code/{tableName}", method = RequestMethod.GET)
    public void code(HttpServletRequest request, HttpServletResponse response,
                     @PathVariable("tableName") String tableName) throws IOException {
        String[] tableNames = new String[]{tableName};
        byte[] data = generatorService.generatorCode(tableNames);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"complie.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

    @LogAnnotation
    @ApiOperation(value = "生成多张表的代码")
    @RequestMapping(value = "/batchCode", method = RequestMethod.GET)
    public void batchCode(HttpServletRequest request, HttpServletResponse response, String tables) throws IOException {
        String[] tableNames = new String[]{};
        tableNames = JSON.parseArray(tables).toArray(tableNames);
        byte[] data = generatorService.generatorCode(tableNames);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"complie.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
