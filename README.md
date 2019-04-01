# JYunCMS 后端服务

> 更多项目介绍与指南，参见 [JYunCMS 手册](https://github.com/JYunCMS/jyun-cms-doc/blob/master/README.md)
>
> 后端服务 API 请访问：[https://documenter.getpostman.com/view/2528586/S17wPmXn](https://documenter.getpostman.com/view/2528586/S17wPmXn)

![jyun-cms-web-new-article](https://github.com/JYunCMS/jyun-cms-doc/raw/master/assets/img/jyun-cms-web-new-article.gif)

## 安装部署

### 运行环境

- Java 1.8+
- MySQL 5.7+

### 工具

- Git
- Maven 3.0+

### 配置数据库

后端程序需要一个数据库，可以执行以下语句在 MySQL中创建所需的数据库

```shell
mysql -u <your-database-user-name> -p -e "CREATE DATABASE jyun_cms_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;"
```

### 获取主程序

通过 Git ，获取源代码

```shell
git clone -b master https://github.com/JYunCMS/jyun-cms-platform.git
```

按需修改 application.yml 配置文件

```shell
ls ./jyun_cms_platform/src/main/resources/
```

通过 Maven 工具，编译生成 jar 包， 结果将输出在 `./target` 文件夹下

```shell
mvn clean package -DskipTests=true
ls ./target/*.jar
```

### 运行

可通过 `java` 命令直接运行上一步编译生成的 jar 包程序

```shell
java -jar your-app.jar
```

如果控制台没有报错，可在浏览器中输入 `http://localhost:8080` 进行验证，若看到 **Welcome** 字样，表示服务启动成功！

## 开发指南

> 更多 **参与贡献** 说明请参阅  [JYunCMS 手册 第三章](https://github.com/JYunCMS/jyun-cms-doc/blob/master/3.1_contribution_method.md)

- 配置环境参考上方安装部署一节
- 推荐使用 [IntelliJ IDEA](https://www.jetbrains.com/idea/) 或 [VSCode](https://code.visualstudio.com/) 进行编码

