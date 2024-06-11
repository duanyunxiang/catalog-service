# 镜像基础，第一阶段
FROM eclipse-temurin:17 AS builder
# 将当前工作目录变更为workspace
WORKDIR workspace
# 构建参数，声明项目中jar文件位置
ARG JAR_FILE=build/libs/*.jar
# 将应用的jar文件从本地复制到镜像中
COPY ${JAR_FILE} catalog-service.jar
# 使用分层jar模式的归档文件中抽取各个层
RUN java -Djarmode=layertools -jar catalog-service.jar extract

# 第二阶段
FROM eclipse-temurin:17
# 创建用户
RUN useradd spring
# 配置spring为当前用户
USER spring
WORKDIR workspace
# 分层jar模式将应用分为如下4层
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/spring-boot-loader/ ./
COPY --from=builder workspace/snapshot-dependencies/ ./
COPY --from=builder workspace/application/ ./
# 容器入口点，spring3该类更新
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
