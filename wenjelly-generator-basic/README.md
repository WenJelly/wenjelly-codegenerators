# Code-Basic
项目的第一阶段，用于直接生成目标代码，以ACM基础代码为例来生成ACM目标代码
在介绍该模块前，需要了解到如何去自定义生成代码？
>通过模板引擎来生成，实现原理就是：
> 
> 准备一个代码模板、数据模型。
> 
> 数据模型可以是一个对象，但是要与模板所需的内容一一对应，比如模板内容为：Author:${author}，则数据模型就需要有一个author的属性用于填充${anthor}
> 
> FreeMarker 模板引擎的语法可以参考：https://freemarker.apache.org/docs/index.html 官方文档
> 
> Picocli 开发教程可以参考：https://github.com/remkop/picocli

## 技术实现

- Picocli 命令行开发 
- FreeMarker 模板引擎
- 命令模式
- Hutool工具库

## 使用指南
这是个Demo模块，用于熟悉Picocli、FreeMarker的基础开发，是一个生成器模块，用于生成目标代码
1. 将你的源代码制作成模板，可以放在``resource/templates``目录下
2. 修改``DynamicAndStaticGenerator``类的模板输入路径和输出路径 
3. 准备好数据模型，可以参考``model``包下的``MainTemplateConfig``类 
4. 开发命令行，用户通过命令行输入参数信息，参考``cli``包下的类
5. 将参数信息封装到数据模型对象中
6. 将数据模型对象与模板进行结合生成目标代码，执行 ``DynamicAndStaticGenerator``类

> 作者：WenJelly
> 
> 日期：2024-3-23 20:36





