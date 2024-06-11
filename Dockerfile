# 镜像基础
FROM eclipse-temurin:17
# 将当前工作目录变更为workspace
WORKDIR workspace
# 构建参数，声明项目中jar文件位置
ARG JAR_FILE=build/libs/*.jar
# 将应用的jar文件从本地复制到镜像中
COPY ${JAR_FILE} catalog-service.jar
# 容器入口点
ENTRYPOINT ["java", "-jar", "catalog-service.jar"]
