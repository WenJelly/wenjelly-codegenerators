package com.wenjelly.generatorbackend.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.utils.IOUtils;
import com.wenjelly.generatorbackend.annotation.AuthCheck;
import com.wenjelly.generatorbackend.common.BaseResponse;
import com.wenjelly.generatorbackend.common.DeleteRequest;
import com.wenjelly.generatorbackend.common.ErrorCode;
import com.wenjelly.generatorbackend.common.ResultUtils;
import com.wenjelly.generatorbackend.constant.UserConstant;
import com.wenjelly.generatorbackend.exception.BusinessException;
import com.wenjelly.generatorbackend.exception.ThrowUtils;
import com.wenjelly.generatorbackend.manager.CacheManager;
import com.wenjelly.generatorbackend.manager.CosManager;
import com.wenjelly.generatorbackend.model.dto.generator.*;
import com.wenjelly.generatorbackend.model.entity.Generator;
import com.wenjelly.generatorbackend.model.entity.User;
import com.wenjelly.generatorbackend.model.vo.GeneratorVO;
import com.wenjelly.generatorbackend.service.GeneratorService;
import com.wenjelly.generatorbackend.service.UserService;
import com.wenjelly.makerplus.main.MainGeneratorTemplate;
import com.wenjelly.makerplus.main.ZipGenerator;
import com.wenjelly.makerplus.meta.Meta;
import com.wenjelly.makerplus.meta.MetaValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * 生成器接口
 */
@RestController
@RequestMapping("/generator")
@Slf4j
public class GeneratorController {

    @Resource
    private GeneratorService generatorService;

    @Resource
    private UserService userService;

    @Resource
    private CosManager cosManager;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    CacheManager cacheManager;

    // region 增删改查

    /**
     * 创建
     *
     * @param generatorAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addGenerator(@RequestBody GeneratorAddRequest generatorAddRequest, HttpServletRequest request) {
        if (generatorAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Meta.ModelConfigBean modelConfig = generatorAddRequest.getModelConfig();
        Meta.FileConfigBean fileConfig = generatorAddRequest.getFileConfig();

        String modelConfigStr = JSONUtil.toJsonStr(modelConfig);
        String fileConfigStr = JSONUtil.toJsonStr(fileConfig);

        Generator generator = new Generator();
        generator.setModelConfig(modelConfigStr);
        generator.setFileConfig(fileConfigStr);

        BeanUtils.copyProperties(generatorAddRequest, generator);
        List<String> tags = generatorAddRequest.getTags();
        if (tags != null) {
            generator.setTags(JSONUtil.toJsonStr(tags));
        }
        generatorService.validGenerator(generator, true);
        User loginUser = userService.getLoginUser(request);
        generator.setUserId(loginUser.getId());
        boolean result = generatorService.save(generator);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newGeneratorId = generator.getId();
        return ResultUtils.success(newGeneratorId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteGenerator(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Generator oldGenerator = generatorService.getById(id);
        ThrowUtils.throwIf(oldGenerator == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldGenerator.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = generatorService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param generatorUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateGenerator(@RequestBody GeneratorUpdateRequest generatorUpdateRequest) {
        if (generatorUpdateRequest == null || generatorUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Generator generator = new Generator();
        BeanUtils.copyProperties(generatorUpdateRequest, generator);
        List<String> tags = generatorUpdateRequest.getTags();
        if (tags != null) {
            generator.setTags(JSONUtil.toJsonStr(tags));
        }
        // 参数校验
        generatorService.validGenerator(generator, false);
        long id = generatorUpdateRequest.getId();
        // 判断是否存在
        Generator oldGenerator = generatorService.getById(id);
        ThrowUtils.throwIf(oldGenerator == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = generatorService.updateById(generator);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<GeneratorVO> getGeneratorVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Generator generator = generatorService.getById(id);
        if (generator == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(generatorService.getGeneratorVO(generator, request));
    }

    /**
     * 分页获取列表（仅管理员）
     *
     * @param generatorQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Generator>> listGeneratorByPage(@RequestBody GeneratorQueryRequest generatorQueryRequest) {
        long current = generatorQueryRequest.getCurrent();
        long size = generatorQueryRequest.getPageSize();
        Page<Generator> generatorPage = generatorService.page(new Page<>(current, size),
                generatorService.getQueryWrapper(generatorQueryRequest));
        return ResultUtils.success(generatorPage);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param generatorQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<GeneratorVO>> listGeneratorVOByPage(@RequestBody GeneratorQueryRequest generatorQueryRequest,
                                                                 HttpServletRequest request) {
        long current = generatorQueryRequest.getCurrent();
        long size = generatorQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Generator> generatorPage = generatorService.page(new Page<>(current, size),
                generatorService.getQueryWrapper(generatorQueryRequest));
        return ResultUtils.success(generatorService.getGeneratorVOPage(generatorPage, request));
    }

    /**
     * 快速分页获取列表（封装类）
     *
     * @param generatorQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo/fast")
    public BaseResponse<Page<GeneratorVO>> listGeneratorVOByPageFast(@RequestBody GeneratorQueryRequest generatorQueryRequest,
                                                                     HttpServletRequest request) {
        long current = generatorQueryRequest.getCurrent();
        long size = generatorQueryRequest.getPageSize();

        // 使用多级缓存  本地 --> Redis --> Mysql
        // 得到缓存key
        String cacheKey = getPageCacheKey(generatorQueryRequest);
        // 先从缓存中读取
        String cacheValue = cacheManager.get(cacheKey);
        if (cacheValue != null) {
            Page<GeneratorVO> generatorVOPage = JSONUtil.toBean(cacheValue,
                    new TypeReference<Page<GeneratorVO>>() {
                    },
                    false);
            return ResultUtils.success(generatorVOPage);
        }


        // 性能优化 之 使用Redis缓存
//        // 优先从Redis缓存里面读取
//        // 获取Redis缓存Key
//        String cacheKey = getPageCacheKey(generatorQueryRequest);
//        // 得到Redis容器（应该）
//        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
//        // 从Redis key里面获取缓存内容
//        String cacheValue = valueOperations.get(cacheKey);
//        if (StrUtil.isNotBlank(cacheValue)) {
//            // 说明缓存里面由内容
//            Page<GeneratorVO> generatorVOPage = JSONUtil.toBean(cacheValue,
//                    new TypeReference<Page<GeneratorVO>>() {
//
//                    },
//                    false);
//            return ResultUtils.success(generatorVOPage);
//        }

        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//         原始代码，未优化
//        Page<Generator> generatorPage = generatorService.page(new Page<>(current, size),
//                generatorService.getQueryWrapper(generatorQueryRequest));
//        Page<GeneratorVO> generatorVOPage = generatorService.getGeneratorVOPage(generatorPage, request);


        QueryWrapper<Generator> queryWrapper = generatorService.getQueryWrapper(generatorQueryRequest);
        // 优化查询信息，将不需要的给过滤掉
        queryWrapper.select("id", "name", "description", "tags", "picture", "status", "userId", "createTime", "updateTime");
        Page<Generator> generatorPage = generatorService.page(new Page<>(current, size), queryWrapper);
        Page<GeneratorVO> generatorVOPage = generatorService.getGeneratorVOPage(generatorPage, request);

        // 性能优化 之 将其设置为空值，减少返回给前端的文件大小以及提高性能，因为首页不需要展示这些信息
//        generatorPage.getRecords().forEach(generatorVO -> {
//            generatorVO.setFileConfig(null);
//            generatorVO.setModelConfig(null);
//        });


        // 如果缓存没有，则写入缓存
//        valueOperations.set(cacheKey, JSONUtil.toJsonStr(generatorVOPage), 100, TimeUnit.MINUTES);
//        return ResultUtils.success(generatorVOPage);

        // 如果缓存没有，则写入多级缓存
        cacheManager.put(cacheKey, JSONUtil.toJsonStr(generatorVOPage));
        return ResultUtils.success(generatorVOPage);
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param generatorQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<GeneratorVO>> listMyGeneratorVOByPage(@RequestBody GeneratorQueryRequest generatorQueryRequest,
                                                                   HttpServletRequest request) {
        if (generatorQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        generatorQueryRequest.setUserId(loginUser.getId());
        long current = generatorQueryRequest.getCurrent();
        long size = generatorQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Generator> generatorPage = generatorService.page(new Page<>(current, size),
                generatorService.getQueryWrapper(generatorQueryRequest));
        return ResultUtils.success(generatorService.getGeneratorVOPage(generatorPage, request));
    }


    /**
     * 编辑（用户）
     *
     * @param generatorEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editGenerator(@RequestBody GeneratorEditRequest generatorEditRequest, HttpServletRequest request) {
        if (generatorEditRequest == null || generatorEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Generator generator = new Generator();
        BeanUtils.copyProperties(generatorEditRequest, generator);
        List<String> tags = generatorEditRequest.getTags();
        if (tags != null) {
            generator.setTags(JSONUtil.toJsonStr(tags));
        }
        // 参数校验
        generatorService.validGenerator(generator, false);
        User loginUser = userService.getLoginUser(request);
        long id = generatorEditRequest.getId();
        // 判断是否存在
        Generator oldGenerator = generatorService.getById(id);
        ThrowUtils.throwIf(oldGenerator == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldGenerator.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = generatorService.updateById(generator);
        return ResultUtils.success(result);
    }

    /**
     * 文件下载
     *
     * @param id       生成器id
     * @param request
     * @param response
     */
    @GetMapping("/download")
    public void downloadGeneratorById(long id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 校验
        if (id < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 得到发起请求的用户id，如果未登录的话，这个方法就会报错
        User loginUser = userService.getLoginUser(request);
        // 得到所需的生成器
        Generator generator = generatorService.getById(id);
        // 校验
        if (generator == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 获取产物包路径，也就是在云存储上面的文件路径
        String distPath = generator.getDistPath();
        if (StrUtil.isBlank(distPath)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "产物包不存在");
        }

        // 设置响应头
        response.setContentType("application/octet-steam;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename = " + distPath);

        // 这里使用缓存优化
        // 先查看文件在不在缓存中
        String cacheKey = getCacheKey(id, distPath);
        if (FileUtil.exist(cacheKey)) {
            // 直接将缓存中的文件返回给前端
            Files.copy(Paths.get(cacheKey), response.getOutputStream());
            return;
        }
        // 追踪事件，可以知晓哪个用户下载了什么，防止流量超出
        log.info("用户 {} 下载了 {}", loginUser, distPath);

        // 如果文件不在服务器缓存中，就从对象存储中下载文件
        COSObjectInputStream cosObjectInput = null;
        try {
            // 参考腾讯云对象存储官方文档
            // 得到下载的文件
            COSObject cosObject = cosManager.getObject(distPath);
            // 转换成流
            cosObjectInput = cosObject.getObjectContent();

            /**
             * 这里使用流式传输来测试性能，结果发现下载速度更慢了，在数据的实时处理比较好
             */
//            // 设置响应头
//            response.setContentType("application/octet-steam;charset=UTF-8");
//            response.setHeader("Content-Disposition", "attachment;filename = " + filePath);
//            try(OutputStream out = response.getOutputStream()) {
//                // try里面获得输出流，用于输出到web
//                byte[] buffer = new byte[4096];
//                int bytesRead;
//
//                // 一边读，一边输出到前端
//                while((bytesRead = cosObjectInput.read(buffer)) != -1) {
//                    out.write(buffer,0,bytesRead);
//                }
//            }catch (Exception e) {
//                e.printStackTrace();
//            }

            // 处理下载到的流
            byte[] bytes = IOUtils.toByteArray(cosObjectInput);
            // 写入响应
            response.getOutputStream().write(bytes);
            // 刷新缓存
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("file download error, filepath = " + distPath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "下载失败");
        } finally {
            if (cosObjectInput != null) {
                cosObjectInput.close();
            }

        }

    }

    @PostMapping("/use")
    /**
     * 使用代码生成器
     *
     * @param generatorUseRequest
     * @param request
     * @param response
     */
    public void useGenerator(
            @RequestBody GeneratorUseRequest generatorUseRequest,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        Long id = generatorUseRequest.getId();
        Map<String, Object> dataModel = generatorUseRequest.getDataModel();

        // 判断是否登录
        User loginUser = userService.getLoginUser(request);
        // 得到需要使用的生成器
        Generator generator = generatorService.getById(id);
        if (generator == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 得到生成器存储路径
        String distPath = generator.getDistPath();
        if (StrUtil.isBlank(distPath)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "产物包不存在");
        }

        // 工作空间
        String projectPath = System.getProperty("user.dir");
        String tempDirPath = String.format("%s/.temp/use/%s", projectPath, id);
        // 下载完之后的文件存储路径
        String zipFilePath = tempDirPath + File.separator + "dist.zip";
        // 新建文件
        if (!FileUtil.exist(zipFilePath)) {
            // 创建
            FileUtil.touch(zipFilePath);
        }

        // 开始下载文件
        try {
            cosManager.download(distPath, zipFilePath);
        } catch (InterruptedException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "生成器下载失败");
        }

        // 解压从腾讯云下载过来的文件
        File unzipDistDir = ZipUtil.unzip(zipFilePath);

        // 将用户传递过来的数据模型写入到JSON文件中
        // 先设置json文件的存储路径在同目录下
        String dataModelFilePath = tempDirPath + File.separator + "meta.json";
        // 将数据模型转换成JSON字符串
        String jsonStr = JSONUtil.toJsonStr(dataModel);
        // 写入到文件中
        FileUtil.writeUtf8String(jsonStr, dataModelFilePath);

        // 开始执行脚本程序
        // 1.找到脚本程序所在位置，只递归俩层，正常来说脚本文件不会在太深的位置，windows系统的脚本文件是以.bat后缀
        File scriptFile = FileUtil.loopFiles(unzipDistDir, 2, null).stream()
                .filter(file -> file.isFile() && "generator".equals(file.getName()))
                .findFirst()
                .orElseThrow(RuntimeException::new);


        try {
            // 2.添加可执行权限，在windows上会抛操作不支持，但貌似已经修改了文件的权限了
            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
            Files.setPosixFilePermissions(scriptFile.toPath(), permissions);
        } catch (IOException e) {

        }

        // 3.创建脚本程序指令，并执行程序
        // 得到脚本文件的父目录位置
        File scriptDir = scriptFile.getParentFile();
        // 转换一下
        String scriptAbsolutePath = scriptDir.getAbsolutePath().replace("\\", "/");

        // 构造命令，这里如果是MAC或者LINUX要使用'./generator'
        String[] command = new String[]{scriptAbsolutePath, "./generator", "json-generate", "--file=" + dataModelFilePath};

        // 创建脚本执行类
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        // 脚本在scriptDir目录下执行
        processBuilder.directory(scriptDir);

        // 执行脚本
        try {
            Process process = processBuilder.start();

            // 读取命令的输出
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            if ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待命令执行完成，exitCode为退出代码
            int exitCode = process.waitFor();
            System.out.println("命令执行结束，退出代码为： " + exitCode);
        } catch (Exception e) {
            // 打印错误信息
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "执行生成器脚本错误");
        }

        // 找到脚本执行结束后，生成的文件夹，也就是generator文件夹，一般在脚本文件的父目录下
        String generatorPath = scriptDir.getAbsolutePath() + File.separator + "generator";
        // 压缩成压缩包后存放的路径
        String resultPath = tempDirPath + "/result.zip";
        // 压缩成压缩包
        File resultFile = ZipUtil.zip(generatorPath, resultPath);

        // 准备返回给前端
        // 设置响应头
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + resultFile.getName());

        // 写入响应
        Files.copy(resultFile.toPath(), response.getOutputStream());

        // 异步清理文件
        CompletableFuture.runAsync(() -> {
            FileUtil.del(tempDirPath);
        });

    }


    /**
     * 制作代码生成器
     *
     * @param generatorMakeRequest
     * @param request
     * @param response
     */
    @PostMapping("/make")
    public void makeGenerator(
            @RequestBody GeneratorMakeRequest generatorMakeRequest,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        // 1）输入参数
        // 文件在腾讯云的位置
        String zipFilePath = generatorMakeRequest.getZipFilePath();
        Meta meta = generatorMakeRequest.getMeta();

        // 需要登录
        User loginUser = userService.getLoginUser(request);
        // 2）创建独立工作空间，下载压缩包到本地
        if (StrUtil.isBlank(zipFilePath)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "压缩包文件不存在");
        }
        // 工作空间
        String property = System.getProperty("user.dir");
        // 随机id
        String id = IdUtil.getSnowflakeNextId() + RandomUtil.randomString(6);
        String tempDirPath = String.format("%s/.temp/make/%s", property, id);
        // 从腾讯云下载下来压缩包文件位置
        String localZipFilePath = tempDirPath + "/maker.zip";
        // 新建文件
        if (!FileUtil.exist(localZipFilePath)) {
            FileUtil.touch(localZipFilePath);
        }
        // 下载文件
        try {
            cosManager.download(zipFilePath, localZipFilePath);
        } catch (InterruptedException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "压缩包下载失败");
        }
        // 3）解压，得到项目模板文件
        File unzipDistDir = ZipUtil.unzip(localZipFilePath);
        // 4）构造 meta 对象和输出路径
        String sourceRootPath = unzipDistDir.getAbsolutePath();
        meta.getFileConfig().setSourceRootPath(sourceRootPath);
        // 校验meta
        MetaValidator.doValidAndFill(meta);
        String outputPath = String.format("%s/generated/%s", tempDirPath, meta.getName());
        // 5）调用 maker 方法制作生成器
        MainGeneratorTemplate zipGenerator = new ZipGenerator();
        try {
            zipGenerator.doGenerator(meta, outputPath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "制作失败");
        }
        // 6）下载压缩的产物包文件
        String suffix = "-dist.zip";
        String zipFileName = meta.getName() + suffix;
        String distZipFilePath = outputPath + suffix;
        // 下载文件
        // 设置响应头
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipFileName);
        // 写入响应
        Files.copy(Paths.get(distZipFilePath), response.getOutputStream());
        // 7）清理文件
        CompletableFuture.runAsync(() -> {
            FileUtil.del(tempDirPath);
        });
    }


    /**
     * 将所需的缓存文件下载到服务器本地
     *
     * @param generatorCacheRequest
     * @param request
     * @param response
     */
    @PostMapping("/cache")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public void downCacheGeneratorById(
            @RequestBody GeneratorCacheRequest generatorCacheRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        if (generatorCacheRequest == null || generatorCacheRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 获取需要缓存的生成器id
        Long id = generatorCacheRequest.getId();
        Generator generator = generatorService.getById(id);
        if (generator == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 获取存储地址
        String distPath = generator.getDistPath();
        if (StrUtil.isBlank(distPath)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "产物包不存在");
        }

        // 将产物包下载到这个缓存文件夹中
        String zipFilePath = getCacheKey(id, distPath);

        if (!FileUtil.exist(zipFilePath)) {
            FileUtil.touch(zipFilePath);
        }

        // 下载
        try {
            cosManager.download(distPath, zipFilePath);
        } catch (InterruptedException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "压缩包下载失败");
        }
    }

    /**
     * 获得缓存文件的存放位置
     *
     * @param id
     * @param distPath
     * @return
     */
    public String getCacheKey(Long id, String distPath) {
        String property = System.getProperty("user.dir");
        String tempPath = String.format("%s/.temp/cache/%s", property, id);
        String zipPath = String.format("%s/%s", tempPath, distPath);
        return zipPath;
    }


    /**
     * 获取分页缓存的Redis Key
     *
     * @param generatorQueryRequest
     * @return
     */
    public static String getPageCacheKey(GeneratorQueryRequest generatorQueryRequest) {
        String jsonStr = JSONUtil.toJsonStr(generatorQueryRequest);
        String base64 = Base64Encoder.encode(jsonStr);
        String key = "generator:page:" + base64;
        return key;
    }

}
