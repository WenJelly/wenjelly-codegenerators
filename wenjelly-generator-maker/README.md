# Generator-Maker
这是一个demo模块，在对生成器进行开发之前的一个测试，通过开发一款生成器来实现对Spring-Boot项目的生成
本项目采用循序渐进的方式进行开发，首先先开发一个能够生成基础Java代码的生成器，然后在此基础上优化成能够开发
Spring-Boot项目的生成器

## 开发步骤
- 第一步：编写生成器源代码，实现对ACM基础代码的生成
- 第二步：对生成器源代码进行扩展，使其能够生成一个基础的Spring-Boot项目
- 第三步：编写生成器模板代码
- 第四步：通过生成器模板代码生成生成器目标代码

## 项目功能


## 目录解释
``cli``目录：使用Picocli命令行开发，用于接收用户在终端输入的参数，然后将参数赋值给Meta对象，得到数据模型。

- ConfigCommand：配置命令，用户输入 ./脚本名称 config 即可查看配置信息，目前实现的是查看 脚本支持哪些字段以及字段的类型
- GenerateCommand：主要用于接收用户输入的参数，提示用户生成器所需的数据模型有哪些，然后接收用户输入的参数
- ListCommand：查看文件列表，与Linux的 -l 命令相似
- TestArgGroupCommand：GenerateCommand的升级版，可以实现嵌套指令，比如指令A询问是否开启MySql，如果用户输入True，即可继续让用户输入关于MySql的信息，如果用户输入False，则不会再提示用户输入MySql信息
- CommandExecutor：用于执行指令后的行为，例如将参数封装到Meta对象里面，得到数据模型

``generator``目录：用于制作目标代码

- DynamicFileGenerator：执行动态模板的生成，对模板上需要修改的值按照数据模型的参数进行填充
- StaticFileGenerator：执行静态文件的生成，原理是直接对文件进行复制
- FileGenerator：调用动态+静态的方法，生成全部目标代码
- JarGenerator：将生成器打包成jar包
- ScriptGenerator：使用程序来封装脚本，让用户直接通过脚本运行程序

``main``目录：用于制作生成器目标代码

- MainGeneratorTemplate：通过生成器模板代码制作生成器目标代码，生成器模板代码全部存放在``resource``资源目录下，本类使用了模板设计模式，将每一步骤进行了提取，方便子类重写对应的方法
- DistMainGenerator：用于生成简易版生成器目标代码文件，继承``MainGeneratorTemplate``类，可以通过重写``doDistDir``方法来实现生成效果

``meta``目录：用于存放数据模型的各种方法
- ``enums``目录：存放需要使用到的枚举类，规范开发方式
- Meta：数据模型，与meta.json一一对应，通过Meta数据模型生成生成器目标代码
- MetaException：异常类，用于提示生成过程中的异常
- MetaManager：数据模型管理，采用了双检锁单例设计模式来对Meta进行生成，减少内存消耗
- MetaValidator：对Meta数据模型进行检验，如：不为空判断，赋初值等。

``model``目录：存放生成目标代码的数据模型
- DataModel：目标代码生成所需的数据模型

``resiources``目录：资源目录
- ``templates``目录：存放生成器模板代码，通过上面的目录来制作生成器模板代码
- meta.json：生成器模板代码所需要的元信息，也就是数据模型

## 运行指南
1. 修改``MetaManager``类的数据模型的地址，该数据模型是生成器模板代码所需要的数据模型
2. 执行``DistMainGenerator``类即可生成一款生成器
3. 进入刚生成好的生成器代码里面，找到``FileGenerator``类即可生成目标代码

## 版本
**Version：1.0**

> 作者:WenJelly
> 
> 日期：2024-3-23 20：07


